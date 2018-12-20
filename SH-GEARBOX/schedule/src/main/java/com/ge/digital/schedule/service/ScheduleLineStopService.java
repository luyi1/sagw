package com.ge.digital.schedule.service;

import com.ge.digital.gearbox.common.exception.BizI18NTransactionException;
import com.ge.digital.gearbox.common.response.RestResponseCode;
import com.ge.digital.schedule.entity.LineStopSchedule;
import com.ge.digital.schedule.enumtions.ScheduleTypeEnum;
import com.ge.digital.schedule.mapper.LineStopScheduleRepository;
import com.ge.digital.schedule.mapper.ViewLineStopScheduleRepository;
import com.ge.digital.schedule.vo.ScheduleInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ScheduleLineStopService {
    private static final Logger log = LoggerFactory.getLogger(ScheduleLineStopService.class);

    @Autowired
    LineStopScheduleRepository lineStopScheduleRepository;
    @Autowired
    ViewLineStopScheduleRepository viewLineStopScheduleRepository;
    @Autowired
    ScheduleCoreService scheduleCoreService;

    /**
     * 产线停机计划列表
     * @param line
     * @param stopReason
     * @param stopStartTime
     * @param stopEndTime
     * @return
     */
    public List<LineStopSchedule> findScheduleLineStopList(String line,String stopReason,Date stopStartTime,Date stopEndTime) {
        return viewLineStopScheduleRepository.findLineSotpScheduleList(line,stopReason,stopStartTime,stopEndTime);
    }

    /**
     * 产线停机计划详情
     * @param id
     * @return
     */
    public LineStopSchedule findScheduleLineStop(long id) {
       return lineStopScheduleRepository.findById(id);
    }

    /**
     * 添加产线停机
     * @param line
     * @param stopReason
     * @param stopStartTime
     * @param stopEndTime
     * @param remark
     */
    @Transactional
    public void addScheduleLineStop(String line,String stopReason,Date stopStartTime,Date stopEndTime,String remark) throws InterruptedException, ExecutionException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        LineStopSchedule lineStopSchedule = new LineStopSchedule();
        lineStopSchedule.setLine(line);
        lineStopSchedule.setStopReason(stopReason);
        lineStopSchedule.setScheduleStopStartTime(stopStartTime);
        lineStopSchedule.setScheduleStopEndTime(stopEndTime);
        lineStopSchedule.setRemark(remark);
        lineStopScheduleRepository.save(lineStopSchedule);

        buildAnewSchedule(stopStartTime);
    }

    private void buildAnewSchedule(Date stopStartTime) throws InterruptedException, ExecutionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ScheduleInput scheduleInput = new ScheduleInput();
        scheduleInput.setScheduleTime(new Date());
        scheduleInput.setLockupDays(3);
        scheduleInput.setScheduleType(ScheduleTypeEnum.LINESTOP_CHANGE.getCode());
        Long getLockDate = scheduleCoreService.getLockDate(scheduleInput).getTime();
        Long getStopStartTime = stopStartTime.getTime();
        if(getStopStartTime < getLockDate){
            ScheduleInput newScheduleInput = new ScheduleInput();
            newScheduleInput.setScheduleTime(new Date());
            newScheduleInput.setLockupDays(3);
            newScheduleInput.setScheduleType(ScheduleTypeEnum.LINESTOP_CHANGE.getCode());
            scheduleCoreService.fireScheduleCore(newScheduleInput);
        }
    }

    /**
     * 修改产线停机
     * @param id
     * @param stopReason
     * @param stopStartTime
     * @param stopEndTime
     * @param remark
     */
    public void updateScheduleLineStop(long id,String stopReason,Date stopStartTime,Date stopEndTime,
                                       String remark) throws InterruptedException, ExecutionException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        LineStopSchedule lineStopSchedule = lineStopScheduleRepository.findById(id);
        lineStopSchedule.setStopReason(stopReason);
        lineStopSchedule.setScheduleStopStartTime(stopStartTime);
        lineStopSchedule.setScheduleStopEndTime(stopEndTime);
        lineStopSchedule.setRemark(remark);
        lineStopScheduleRepository.save(lineStopSchedule);

        buildAnewSchedule(stopStartTime);
    }

    /**
     * 产线停机取消
     * @param id
     */
    public void cancelScheduleLineStop(long id) throws InterruptedException, ExecutionException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        LineStopSchedule lineStopSchedule =  lineStopScheduleRepository.findById(id);
        if(!ObjectUtils.isEmpty(lineStopSchedule.getActualStopStartTime())){
            throw new BizI18NTransactionException(RestResponseCode.SCHEDULE_LINE_STOP_ACTUAL_START_TIME);
        }
        Long stopEndTime = lineStopSchedule.getScheduleStopEndTime().getTime();
        Long getTime = new Date().getTime();
        if(stopEndTime <= getTime){
            throw new BizI18NTransactionException(RestResponseCode.SCHEDULE_LINE_STOP_SCHEDULE_ENDTIME);
        }
        lineStopSchedule.setCancelTime(new Date());
        lineStopScheduleRepository.save(lineStopSchedule);

        buildAnewSchedule(lineStopSchedule.getScheduleStopStartTime());
    }

    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date.getTime());
    }
}
