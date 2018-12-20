package com.ge.digital.schedule.mapper;

import com.ge.digital.schedule.entity.ReworkTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Component
public class ViewReworkTimeRepository {
    private static final Logger log = LoggerFactory.getLogger(ViewReworkTimeRepository.class);
    @Autowired
    EntityManager entityManager;

    /**
     * 特殊工艺时间列表
     * @param reworkBatch
     * @return
     */
    public List<ReworkTime> findReworkTimeList(String reworkBatch){
        StringBuffer sql = new StringBuffer();
        sql.append("select * from PR_Schedule.sh_ReworkTime rt where 1=1   ");
        if(!StringUtils.isEmpty(reworkBatch)){
            sql.append("and rt.ReworkBatch = '"+reworkBatch+"'"   );
        }
        Query query = entityManager.createNativeQuery(sql.toString(),ReworkTime.class);
        return query.getResultList();
    }
}
