package org.xtremebiker.jsfspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.xtremebiker.jsfspring.dto.request.AddDelayDto;
import org.xtremebiker.jsfspring.dto.request.UpdateAttendance;
import org.xtremebiker.jsfspring.dto.response.AddAttendResponse;
import org.xtremebiker.jsfspring.dto.response.AllAttendance;
import org.xtremebiker.jsfspring.dto.response.DelayUserDto;
import org.xtremebiker.jsfspring.entity.AttendRecord;
import org.xtremebiker.jsfspring.entity.UserEntity;
import org.xtremebiker.jsfspring.enums.Status;
import org.xtremebiker.jsfspring.exceptions.BaseException;
import org.xtremebiker.jsfspring.mapper.AttendRecordMapper;
import org.xtremebiker.jsfspring.repo.AttendanceRepo;
import org.xtremebiker.jsfspring.repo.UserRepo;
import org.xtremebiker.jsfspring.service.AttendRecordService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendRecordServiceImpl implements AttendRecordService {

    private final AttendanceRepo attendanceRepo;

    private final UserRepo userRepo;

    @Override
    public AddAttendResponse addDelayMin(AddDelayDto addDelayDto) {
        if (!attendanceRepo.existsByUserEntityIdAndAttendDate(addDelayDto.getUserId(), addDelayDto.getDate()) && addDelayDto.getDelayInMin() != 0) {
            AttendRecord attendRecord1 = new AttendRecord();
            attendRecord1.setUserEntity(userRepo.findById(addDelayDto.getUserId()).orElseThrow(() ->
                    new BaseException("такого юзер с айди =" + addDelayDto.getUserId() + " нет", HttpStatus.NOT_FOUND)));
            attendRecord1.setDelayInMin(addDelayDto.getDelayInMin());
            attendRecord1.setAttendDate(addDelayDto.getDate());

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
    public List<DelayUserDto> getAllByUser(String userName) {
        return AttendRecordMapper
                .entityListToDelayUserDtoList(attendanceRepo.getAttendRecordsByUserEntityUserNameOrderByAttendDateDesc(userName));
    }

    @Override
    public List<AllAttendance> getAllAttendance(Status status) {
        return AttendRecordMapper.entityListToDtoList(attendanceRepo.findAttendRecordsByStatusOrderByAttendDateDesc(status));
    }

    @Override
    public void deleteById(Long id) {
        AttendRecord attendRecord = attendanceRepo.findById(id).get();
        attendRecord.setStatus(Status.DELETED);
        attendanceRepo.save(attendRecord);
    }

    @Override
    public UpdateAttendance updateAttendanceById(UpdateAttendance updateAttendance) {
        AttendRecord attendRecord = attendanceRepo.findById(updateAttendance.getAttendanceId()).orElseThrow(
                () -> new BaseException("запись с id = " + updateAttendance.getAttendanceId() +" не найден", HttpStatus.NOT_FOUND)
        );
        attendRecord.setDelayInMin(updateAttendance.getMin());
        attendRecord.setMoney((long) (updateAttendance.getMin() * attendRecord.getStreak()));
        attendanceRepo.save(attendRecord);
        return updateAttendance;
    }

    @Override
    public List<AllAttendance> getAllAttendanceByUserId(Long userId) {
        return AttendRecordMapper.entityListToDtoList(attendanceRepo.findAttendRecordsByUserEntityIdAndStatus(userId, Status.ACTIVE));
    }

    @Override
    public Long getAllSumByUserId(Long userId) {
        return attendanceRepo.sumOfMoneyByUserIdAndStatus(userId);
    }

    @Override
    public Long getLoanByUserId(Long userId) {
        UserEntity userEntity = userRepo.findById(userId).orElseThrow(
                ()->new BaseException("не найден", HttpStatus.NOT_FOUND)
        );
        long loan = getAllSumByUserId(userId) - userEntity.getMoneyOnBalance();
        return loan;
    }

    @Override
    public Long getAllSum() {
        return attendanceRepo.getAllSum();
    }

    @Override
    public Long getAllLoan() {
        return getAllSum() - allBalanceSum();
    }

    @Override
    public Long allBalanceSum() {
        return userRepo.getAllBalance();
    }
}
