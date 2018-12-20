package com.ge.digital.schedule.mapper;

import com.ge.digital.schedule.entity.LineStopSchedule;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

@Component
public class ViewLineStopScheduleRepository {
    private static final Logger log = LoggerFactory.getLogger(ViewLineStopScheduleRepository.class);
    @Autowired
    EntityManager entityManager;
    public List<LineStopSchedule> findLineSotpScheduleList(String line, String stopReason, Date stopStartTime, Date stopEndTime){
        String dateForamt = "yyyy-MM-dd HH:mm:ss";
        StringBuffer sql = new StringBuffer();
        sql.append("select * from PR_Schedule.sh_LineStopSchedule lss   where 1=1   ");
        if(!StringUtils.isEmpty(line)){
            sql.append("and lss.Line= '"+line+"'"   );
        }
        if(!StringUtils.isEmpty(stopReason)){
            sql.append("and lss.StopReason= '"+stopReason+"'    ");
        }
        if(!ObjectUtils.isEmpty(stopStartTime)){
            sql.append("and lss.ScheduleStopStartTime >='"+ DateFormatUtils.format(stopStartTime, dateForamt)+"' ");
        }
        if(!ObjectUtils.isEmpty(stopEndTime)){
            sql.append("and lss.ScheduleStopEndTime <='"+DateFormatUtils.format(stopEndTime, dateForamt)+"' ");
        }
        sql.append("and lss.CancelTime is null  ");
        Query query = entityManager.createNativeQuery(sql.toString(),LineStopSchedule.class);
        return query.getResultList();
    }
}
