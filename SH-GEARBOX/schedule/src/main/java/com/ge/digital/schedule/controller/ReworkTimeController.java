package com.ge.digital.schedule.controller;

import com.ge.digital.gearbox.common.response.NormalResponse;
import com.ge.digital.schedule.entity.ReworkTime;
import com.ge.digital.schedule.service.ReworkTimeService;
import com.ge.digital.schedule.vo.ReworkTimeRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@Api("特殊工艺时间管理")
@RestController
@RequestMapping("/api/reworkTime")
public class ReworkTimeController {
    private static final Logger log = LoggerFactory.getLogger(ReworkTimeController.class);

    @Autowired
    ReworkTimeService reworkTimeService;

    @ApiOperation("特殊工艺时间列表查询")
    @RequestMapping(value = "/findReworkTime", method = RequestMethod.GET)
    public Object findReworkTimeList(@RequestParam(required = false) String reworkBatch) {
        NormalResponse rsp = new NormalResponse();
        rsp.setResData(reworkTimeService.findReworkTimeList(reworkBatch));
        return rsp;
    }

    @ApiOperation("特殊工艺时间查询")
    @RequestMapping(value = "/findReworkTime/{id}", method = RequestMethod.GET)
    public Object findReworkTime(@PathVariable(name = "id") long id) {
        NormalResponse rsp = new NormalResponse();
        rsp.setResData(reworkTimeService.findReworkTime(id));
        return rsp;
    }

    @ApiOperation("验证特殊工艺时间是否存在相同的流转卡和产线")
    @RequestMapping(value = "/validateReworkTime", method = RequestMethod.GET)
    public Object findReworkTime(@RequestParam String reworkbatch,@RequestParam String reworkline) {
        NormalResponse rsp = new NormalResponse();
        ReworkTime reworkTime = reworkTimeService.findReworkTime(reworkbatch,reworkline);
        if(ObjectUtils.isEmpty(reworkTime)){
            rsp.setResData(false);
        }else{
            rsp.setResData(true);
        }
        return rsp;
    }

    @ApiOperation("添加特殊工艺时间")
    @RequestMapping(value = "/addReworkTime", method = RequestMethod.POST)
    public Object addReworkTime(@RequestBody ReworkTimeRequest reworkTimeRequest) {
        NormalResponse rsp = new NormalResponse();
        reworkTimeService.addReworkTime(reworkTimeRequest);
        return rsp;
    }

    @ApiOperation("修改特殊工艺时间")
    @RequestMapping(value = "/updateReworkTime", method = RequestMethod.PUT)
    public Object updateReworkTime(@RequestBody ReworkTimeRequest reworkTimeRequest) {
        NormalResponse rsp = new NormalResponse();
        reworkTimeService.updateReworkTime(reworkTimeRequest);
        return rsp;
    }

    @ApiOperation("删除特殊工艺时间")
    @RequestMapping(value = "/deleteReworkTime", method = RequestMethod.DELETE)
    public Object deleteReworkTime(@RequestParam long id) {
        NormalResponse rsp = new NormalResponse();
        reworkTimeService.deleteReworkTime(id);
        return rsp;
    }
}
