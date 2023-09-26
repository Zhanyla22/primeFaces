package org.com.jsfspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.com.jsfspring.dto.request.AddDelayRequest;
import org.com.jsfspring.dto.request.UpdateAttendanceRequest;
import org.com.jsfspring.dto.response.AddAttendResponse;
import org.com.jsfspring.dto.response.AllAttendanceResponse;
import org.com.jsfspring.dto.response.LoanHistoryResponse;
import org.com.jsfspring.dto.response.TokenValidResponse;
import org.com.jsfspring.entity.AttendRecord;
import org.com.jsfspring.entity.UserEntity;
import org.com.jsfspring.enums.Status;
import org.com.jsfspring.exceptions.BaseException;
import org.com.jsfspring.mapper.AttendRecordMapper;
import org.com.jsfspring.repo.AttendanceRepo;
import org.com.jsfspring.repo.PaymentRepo;
import org.com.jsfspring.repo.UserRepo;
import org.com.jsfspring.security.jwt.JWTService;
import org.com.jsfspring.service.AttendRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Реализация методов attendRecord
 */
@RequiredArgsConstructor
@Service
public class AttendRecordServiceImpl implements AttendRecordService {

    private final AttendanceRepo attendanceRepo;
    private final UserRepo userRepo;
    private final PaymentRepo paymentRepo;
    private final JWTService jwtService;

    /**
     * Добавления новой записи опоздания сотрудника
     *
     * @param addDelayDto, userId - id сотрудника;
     *                     date -  дата опоздания (формат - YYYY-MM-DD);
     *                     delayInMin - опоздание в минутах - (должна быть выше 0)
     * @return userId - Id сотрудника;
     * attendDate - дата опоздания;
     * delayInMin - задержка в минутах;
     * streak - количество подряд опозданий;
     * money - задолженность в сомах;
     * uuid - uuid
     */
    @Override
    public AddAttendResponse addDelayMin(AddDelayRequest addDelayDto) {
        if (!attendanceRepo.existsByAttendDate(addDelayDto.getDate())) {
            if (addDelayDto.getDelayInMin() > 0) {
                AttendRecord attendRecord1 = new AttendRecord();
                attendRecord1.setUserEntity(userRepo.findById(addDelayDto.getUserId()).orElseThrow(() ->
                        new BaseException("пользователь с id =" + addDelayDto.getUserId() + " не найден", HttpStatus.NOT_FOUND)));
                attendRecord1.setDelayInMin(addDelayDto.getDelayInMin());
                attendRecord1.setAttendDate(addDelayDto.getDate());
                attendRecord1.setUuid(UUID.randomUUID());

                /**
                 * находим последний запись опоздания сотрудника для проверки дни недели
                 */
                AttendRecord attendRecord = attendanceRepo.findLast(addDelayDto.getUserId()).orElse(null);

                /**
                 * 1-case: Если выбранная дата суббота или воскресенье
                 */
                if (addDelayDto.getDate().getDayOfWeek().getValue() == 6 || addDelayDto.getDate().getDayOfWeek().getValue() == 7) {
                    throw new BaseException("В субботу и воскресенье отдыхайте", HttpStatus.NOT_FOUND);
                }

                /**
                 * 2-case: Если это 1-опоздание сотрудника(1й запись в бд)
                 */
                else if (attendRecord == null) {
                    attendRecord1.setStreak(1);
                    attendRecord1.setMoney((long) (addDelayDto.getDelayInMin()));
                }

                /**
                 * 3-case: Если последнее опоздание было в пятницу а след в понедельник,
                 * в таком случае тоже будет считаться что сотрудник подряд опоздал(streak)
                 */
                else if (attendRecord.getAttendDate().getDayOfWeek().getValue() == 5 && addDelayDto.getDate().getDayOfWeek().getValue() == 1
                        || attendRecord.getAttendDate().plusDays(1).compareTo(addDelayDto.getDate()) == 0) {
                    attendRecord1.setStreak(attendRecord.getStreak() + 1);
                    attendRecord1.setMoney(attendRecord.getMoney() + (long) attendRecord1.getStreak() * attendRecord1.getDelayInMin());
                }

                /**
                 * 4-case: Просто любой другой день опоздал - начало streak'a(1й стрик сотрудника)
                 */
                else {
                    attendRecord1.setStreak(1);
                    attendRecord1.setMoney((long) (addDelayDto.getDelayInMin()));
                }
                attendRecord1.setStatus(Status.ACTIVE);
                attendanceRepo.save(attendRecord1);
                return AttendRecordMapper.entityToAttendResponse(attendRecord1);
            } else throw new BaseException("ВНИМАНИЕ: минута опоздания должна быть выше 0", HttpStatus.NOT_ACCEPTABLE);
        } else
            throw new BaseException("ВНИМАНИЕ: в этот день сотрудник уже опаздывал, выберите другой день", HttpStatus.NOT_ACCEPTABLE);
    }

    /**
     * Получить все записи опозданий сотрудников
     *
     * @param status
     * @return лист AllAttendanceResponse - все записи опоздания
     */
    @Override
    public List<AllAttendanceResponse> getAllAttendance(Status status) {
        return AttendRecordMapper.entityListToDtoList(attendanceRepo.findAttendRecordsByStatusOrderByAttendDateDesc(status));
    }

    /**
     * удаление записи по uuid, Статус Записи обновляется на DELETED
     *
     * @param uuid
     * @return ничего
     */
    @Override
    public String deleteById(UUID uuid) {
        AttendRecord attendRecord = attendanceRepo.findByUuid(uuid).orElseThrow(
                () -> new BaseException("пользователь с uuid = " + uuid + " не найден", HttpStatus.NOT_FOUND));
        attendRecord.setStatus(Status.DELETED);
        attendanceRepo.save(attendRecord);
        return null;
    }

    /**
     * Обновление записи опоздания, нельзя обновить все поля,
     * так как в таком случае рушится логика стриков и вычисление задолженностей.
     * обновлению подлежит только минута опоздания, при обновлении минуты обновляется и задолженность автоматически
     *
     * @param updateAttendanceRequest
     * @return
     */
    @Override //todo: возвращаемый тип переделать
    public UpdateAttendanceRequest updateAttendanceById(UpdateAttendanceRequest updateAttendanceRequest) {
        AttendRecord attendRecord = attendanceRepo.findById(updateAttendanceRequest.getAttendanceId()).orElseThrow(
                () -> new BaseException("запись с id = " + updateAttendanceRequest.getAttendanceId() + " не найден", HttpStatus.NOT_FOUND)
        );

        /**
         * формула для нахождения первоначальной суммы
         */
        attendRecord.setMoney((attendRecord.getMoney() - ((long) attendRecord.getStreak() * attendRecord.getDelayInMin())
                + (long) attendRecord.getStreak() * updateAttendanceRequest.getMin()));
        attendRecord.setDelayInMin(updateAttendanceRequest.getMin());
        attendanceRepo.save(attendRecord);
        return updateAttendanceRequest;
    }

    /**
     * получение всех опозданий сотрудника
     *
     * @param jwt
     * @return лист из AllAttendanceResponse
     */
    @Override
    public List<AllAttendanceResponse> getAllAttendanceByUser(String jwt) {
        TokenValidResponse token = jwtService.validToken(jwt);
        UserEntity userEntity = userRepo.findByUserName(token.getUserName()).orElseThrow(
                () -> new BaseException("пользователь с UserName " + token.getUserName() + " не найден", HttpStatus.NOT_FOUND)
        );
        return AttendRecordMapper.entityListToDtoList(attendanceRepo.findAttendRecordsByUserEntityIdAndStatusOrderByAttendDateDesc(userEntity.getId()));
    }

    /**
     * Сумма задолженностней сотрудника
     *
     * @param jwt
     * @return сумма Long
     */
    @Override
    public Long getAllSumByUser(String jwt) {
        TokenValidResponse token = jwtService.validToken(jwt);
        UserEntity userEntity = userRepo.findByUserName(token.getUserName()).orElseThrow(
                () -> new BaseException("пользователь с UserName =" + token.getUserName() + " не найден", HttpStatus.NOT_FOUND)
        );
        return attendanceRepo.sumOfMoneyByUserIdAndStatus(userEntity.getId());
    }

    /**
     * Сумма задолженностней всех сотрудников
     *
     * @return сумма Long
     */
    @Override
    public Long getAllSum() {
        return attendanceRepo.getAllSum();
    }


    /**
     * сумма оплаты всех сотрудников
     *
     * @return сумма Long
     */
    @Override
    public Long allBalanceSum() {
        return paymentRepo.getAllBalance();
    }

    /**
     * сумма оплаты одного сотрудников по jwt
     *
     * @param jwt
     * @return
     */
    @Override
    public Long getBalanceByUser(String jwt) {
        TokenValidResponse token = jwtService.validToken(jwt);
        UserEntity userEntity = userRepo.findByUserName(token.getUserName()).orElseThrow(
                () -> new BaseException("пользователь с username " + token.getUserName() + " Не найден", HttpStatus.NOT_FOUND)
        );
        return paymentRepo.getBalanceByUserId(userEntity.getId());
    }

    /**
     * получение списка задолженностей и оплат
     *
     * @return userId - id сотрудника;
     * userName - имя сотрудника;
     * loanSum - общяя задолженность;
     * balanceSum - оплаченная сумма;
     * left - оставщяяся сумма;
     */
    @Override
    public List<LoanHistoryResponse> getAllLoanHistory() {
        return AttendRecordMapper.entityToListLoan(attendanceRepo.getJoinedDatas());
    }

    /**
     * сумма остаток задолженностей всех сотрудников
     *
     * @return сумма Long
     */
    @Override
    public Long getSumRestLeft() {
        return getAllSum() - allBalanceSum();
    }

    /**
     * сумма остаток задолженностей Одного сотрудника по jwt
     *
     * @return сумма Long
     */
    @Override
    public Long getCurrentUsersLeft(String jwt) {
        return getAllSumByUser(jwt) - getBalanceByUser(jwt);
    }
}
