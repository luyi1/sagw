package com.ge.digital.schedule.service;

import com.ge.digital.schedule.entity.ReworkTime;
import com.ge.digital.schedule.mapper.ReworkTimeRepository;
import com.ge.digital.schedule.mapper.ViewReworkTimeRepository;
import com.ge.digital.schedule.vo.ReworkTimeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReworkTimeService {
    private static final Logger log = LoggerFactory.getLogger(ReworkTimeService.class);

    @Autowired
    ReworkTimeRepository reworkTimeRepository;

    @Autowired
    ViewReworkTimeRepository viewReworkTimeRepository;

    /**
     * 特殊工艺时间列表
     * @param reworkBatch
     * @return
     */
    public List<ReworkTime> findReworkTimeList(String reworkBatch) {
        return viewReworkTimeRepository.findReworkTimeList(reworkBatch);
    }

    /**
     * 特殊工艺详情
     * @param id
     * @return
     */
    public ReworkTime findReworkTime(long id) {
        return reworkTimeRepository.findById(id);
    }

    /**
     * 根据流转卡号和产线获取特殊工艺详情
     */
    public ReworkTime findReworkTime(String reworkbatch,String reworkline){
        return reworkTimeRepository.findByReworkBatchAndReworkLine(reworkbatch,reworkline);
    }

    /**
     * 添加特殊工艺
     * @param reworkTimeRequest
     */
    public void addReworkTime(ReworkTimeRequest reworkTimeRequest) {
        ReworkTime reworkTime = new ReworkTime();
        BeanUtils.copyProperties(reworkTimeRequest,reworkTime);
        reworkTimeRepository.save(reworkTime);
    }

    /**
     * 修改特殊工艺
     * @param reworkTimeRequest
     */
    public void updateReworkTime(ReworkTimeRequest reworkTimeRequest) {
        ReworkTime reworkTime = reworkTimeRepository.findByReworkBatchAndReworkLine(reworkTimeRequest.getReworkBatch(),
                reworkTimeRequest.getReworkLine());
        reworkTime.setReworkProcessNo(reworkTimeRequest.getReworkProcessNo());
        reworkTime.setOP10(reworkTimeRequest.getOP10());
        reworkTime.setOP20(reworkTimeRequest.getOP20());
        reworkTime.setOP30(reworkTimeRequest.getOP30());
        reworkTime.setOP40(reworkTimeRequest.getOP40());
        reworkTime.setOP50(reworkTimeRequest.getOP50());
        reworkTime.setOP60(reworkTimeRequest.getOP60());
        reworkTimeRepository.save(reworkTime);
    }

    /**
     * 删除特殊工艺
     * @param id
     */
    public void deleteReworkTime(long id) {
        reworkTimeRepository.delete(id);
    }
}
