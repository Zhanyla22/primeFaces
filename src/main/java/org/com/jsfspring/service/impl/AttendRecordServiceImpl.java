package org.com.jsfspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.com.jsfspring.dto.request.AddDelayDto;
import org.com.jsfspring.dto.request.UpdateAttendance;
import org.com.jsfspring.dto.response.AddAttendResponse;
import org.com.jsfspring.dto.response.AllAttendance;
import org.com.jsfspring.dto.response.LoanHistory;
import org.com.jsfspring.entity.AttendRecord;
import org.com.jsfspring.entity.UserEntity;
import org.com.jsfspring.enums.Status;
import org.com.jsfspring.exceptions.BaseException;
import org.com.jsfspring.mapper.AttendRecordMapper;
import org.com.jsfspring.repo.AttendanceRepo;
import org.com.jsfspring.repo.PaymentRepo;
import org.com.jsfspring.repo.UserRepo;
import org.com.jsfspring.security.jwt.JWTService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.com.jsfspring.service.AttendRecordService;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AttendRecordServiceImpl implements AttendRecordService {

    private final AttendanceRepo attendanceRepo;
    private final UserRepo userRepo;
    private final PaymentRepo paymentRepo;
    private final UserDetailServiceImpl userDetailService;
    private final JWTService jwtService;

    @Override
    public AddAttendResponse addDelayMin(AddDelayDto addDelayDto) {
        if (addDelayDto.getDelayInMin() != 0) { //TODO: поставить валидация на дату- если день 234 или месяц 132 итд, на формат YYYY-MM-DD
            AttendRecord attendRecord1 = new AttendRecord();
            attendRecord1.setUserEntity(userRepo.findById(addDelayDto.getUserId()).orElseThrow(() ->
                    new BaseException("пользователь с id =" + addDelayDto.getUserId() + " не найден", HttpStatus.NOT_FOUND)));
            attendRecord1.setDelayInMin(addDelayDto.getDelayInMin());
            attendRecord1.setAttendDate(addDelayDto.getDate());
            attendRecord1.setUuid(UUID.randomUUID());

            AttendRecord attendRecord = attendanceRepo.findLast(addDelayDto.getUserId()).orElse(null);
            //Выбранная дата суббота или воскресенье
            if (addDelayDto.getDate().getDayOfWeek().getValue() == 6 || addDelayDto.getDate().getDayOfWeek().getValue() == 7) {
                throw new BaseException("В субботу и воскресенье отдыхайте", HttpStatus.NOT_FOUND);
            }
            //1-опоздание
            else if (attendRecord == null) {
                attendRecord1.setStreak(1);
                attendRecord1.setMoney((long) (addDelayDto.getDelayInMin()));
            }
            //1 case : если последнее опоздание было в пятницу а след в понедельник
            //2 case : чтобы определить streak
            else if (attendRecord.getAttendDate().getDayOfWeek().getValue() == 5 && addDelayDto.getDate().getDayOfWeek().getValue() == 1
                    || attendRecord.getAttendDate().plusDays(1).compareTo(addDelayDto.getDate()) == 0) {
                attendRecord1.setStreak(attendRecord.getStreak() + 1);
                attendRecord1.setMoney(attendRecord.getMoney() + (long) attendRecord1.getStreak() * attendRecord1.getDelayInMin());
            }
            //если просто в любой день опоздал - начало streak'a
            else {
                attendRecord1.setStreak(1);
                attendRecord1.setMoney((long) (addDelayDto.getDelayInMin()));
            }
            attendRecord1.setStatus(Status.ACTIVE);
            attendanceRepo.save(attendRecord1);
            return AttendRecordMapper.entityToAttendResponse(attendRecord1);
        } else throw new BaseException("В этот день уже опаздывали, выберите другой день", HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    public List<AllAttendance> getAllAttendance(Status status) {
        return AttendRecordMapper.entityListToDtoList(attendanceRepo.findAttendRecordsByStatusOrderByAttendDateDesc(status));
    }

    @Override
    public String deleteById(UUID uuid) {
        AttendRecord attendRecord = attendanceRepo.findByUuid(uuid).orElseThrow(
                () -> new BaseException("пользователь с uuid = " + uuid + " не найден", HttpStatus.NOT_FOUND));
        attendRecord.setStatus(Status.DELETED);
        attendanceRepo.save(attendRecord);
        return null;
    }

    @Override
    public UpdateAttendance updateAttendanceById(UpdateAttendance updateAttendance) {
        AttendRecord attendRecord = attendanceRepo.findById(updateAttendance.getAttendanceId()).orElseThrow(
                () -> new BaseException("запись с id = " + updateAttendance.getAttendanceId() + " не найден", HttpStatus.NOT_FOUND)
        );
        attendRecord.setMoney((attendRecord.getMoney() - ((long) attendRecord.getStreak() * attendRecord.getDelayInMin())
                + (long) attendRecord.getStreak() * updateAttendance.getMin()));
        attendRecord.setDelayInMin(updateAttendance.getMin());
        attendanceRepo.save(attendRecord);
        return updateAttendance;
    }

    @Override
    public List<AllAttendance> getAllAttendanceByUser(String jwt) {
        String token = jwt.substring(7); //substring проверка
        String userName = jwtService.extractUserName(token);
        if (!jwtService.isTokenExpired(token)) {
            UserEntity userEntity = userRepo.findByUserName(userName).orElseThrow(
                    ()-> new BaseException("пользователь с UserName "+ userName+" не найден", HttpStatus.NOT_FOUND)
            );
            return AttendRecordMapper.entityListToDtoList(attendanceRepo.findAttendRecordsByUserEntityIdAndStatusOrderByAttendDateDesc(userEntity.getId()));
        }
        else return null;
    }

    @Override
    public Long getAllSumByUser(String jwt) {
        String token = jwt.substring(7); //substring проверка
        String userName = jwtService.extractUserName(token);
        UserEntity userEntity = userRepo.findByUserName(userName).orElseThrow(
                ()-> new BaseException("пользователь с UserName ="+userName+" не найден",HttpStatus.NOT_FOUND)
        );
        if (!jwtService.isTokenExpired(token)) {
            return attendanceRepo.sumOfMoneyByUserIdAndStatus(userEntity.getId());
        }
        else return null;
    }

    @Override
    public Long getAllSum() {
        return attendanceRepo.getAllSum();
    }


    @Override
    public Long allBalanceSum() {
        return paymentRepo.getAllBalance();
    }

    @Override
    public Long getBalanceByUser(String jwt) {
        String token = jwt.substring(7); //substring проверка
        String userName = jwtService.extractUserName(token);
        if (!jwtService.isTokenExpired(token)) {
            UserEntity userEntity = userRepo.findByUserName(userName).orElseThrow(
                    ()-> new BaseException("пользователь с username "+ userName+" Не найден", HttpStatus.NOT_FOUND)
            );
            return paymentRepo.getBalanceByUserId(userEntity.getId());
        }
        else return null;
    }

    @Override
    public List<LoanHistory> getAllLoanHistory() {
        return AttendRecordMapper.entityToListLoan(attendanceRepo.getJoinedDatas());
    }

    @Override
    public Long getSumRestLeft() {
        return getAllSum() - allBalanceSum();
    }

    @Override
    public Long getCurrentUsersLeft(String jwt) {
        String token = jwt.substring(7); //substring проверка
        if (!jwtService.isTokenExpired(token)) {
            return getAllSumByUser(jwt) - getBalanceByUser(jwt);
        }
        else return null;
    }
}
