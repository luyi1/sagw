package com.ge.digital.schedule.controller;

import com.ge.digital.gearbox.common.response.BizErrorResponse;
import com.ge.digital.gearbox.common.response.NormalResponse;
import com.ge.digital.gearbox.common.response.RestResponseCode;
import com.ge.digital.schedule.service.ScheduleLineStopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

@Api("产线停机计划管理")
@RestController
@RequestMapping("/api/scheduleLineStop")
public class ScheduleLineStopController {
    private static final Logger log = LoggerFactory.getLogger(ScheduleLineStopController.class);
    @Autowired
    ScheduleLineStopService scheduleLineStopService;

    @ApiOperation("产线停机列表查询")
    @RequestMapping(value = "/findScheduleLineStop", method = RequestMethod.GET)
    public Object findScheduleLineStopList(@RequestParam(required = false) String line, @RequestParam(required = false) String stopReason,
                                           @RequestParam(required = false) Date stopStartTime,@RequestParam(required = false) Date stopEndTime) {
        if(!(ObjectUtils.isEmpty(stopStartTime) && ObjectUtils.isEmpty(stopEndTime))) {
            if (stopEndTime.getTime() <= stopStartTime.getTime()) {
                return new BizErrorResponse(RestResponseCode.QUALITY_STOVE_DATE_NOTMATCH_EXCEPTION);
            }
        }
        NormalResponse rsp = new NormalResponse();
        rsp.setResData(scheduleLineStopService.findScheduleLineStopList(line,stopReason,stopStartTime,stopEndTime));
        return rsp;
    }

    @ApiOperation("产线停机详情")
    @RequestMapping(value = "/findScheduleLineStop/{id}", method = RequestMethod.GET)
    public Object findScheduleLineStop(@PathVariable(name = "id") long id) {
        NormalResponse rsp = new NormalResponse();
        rsp.setResData(scheduleLineStopService.findScheduleLineStop(id));
        return rsp;
    }

    @ApiOperation("产线停机添加")
    @RequestMapping(value = "/addScheduleLineStop", method = RequestMethod.POST)
    public Object addScheduleLineStop(@RequestParam String line, @RequestParam String stopReason,
                                      @RequestParam Date stopStartTime,@RequestParam Date stopEndTime,
                                      @RequestParam String remark) throws InterruptedException, ExecutionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (stopEndTime.getTime() <= stopStartTime.getTime()) {
            return new BizErrorResponse(RestResponseCode.QUALITY_STOVE_DATE_NOTMATCH_EXCEPTION);
        }
        NormalResponse rsp = new NormalResponse();
        scheduleLineStopService.addScheduleLineStop(line,stopReason,stopStartTime,stopEndTime,remark);
        return rsp;
    }

    @ApiOperation("产线停机修改")
    @RequestMapping(value = "/updateScheduleLineStop", method = RequestMethod.PUT)
    public Object updateScheduleLineStop(@RequestParam Long id, @RequestParam String stopReason,
                                      @RequestParam Date stopStartTime,@RequestParam Date stopEndTime,
                                      @RequestParam String remark) throws InterruptedException, ExecutionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (stopEndTime.getTime() <= stopStartTime.getTime()) {
            return new BizErrorResponse(RestResponseCode.QUALITY_STOVE_DATE_NOTMATCH_EXCEPTION);
        }
        NormalResponse rsp = new NormalResponse();
        scheduleLineStopService.updateScheduleLineStop(id,stopReason,stopStartTime,stopEndTime,remark);
        return rsp;
    }

    @ApiOperation("产线停机取消")
    @RequestMapping(value = "/cancelScheduleLineStop", method = RequestMethod.PUT)
    public Object cancelScheduleLineStop(@RequestParam long id) throws InterruptedException, ExecutionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        NormalResponse rsp = new NormalResponse();
        scheduleLineStopService.cancelScheduleLineStop(id);
        return rsp;
    }

}