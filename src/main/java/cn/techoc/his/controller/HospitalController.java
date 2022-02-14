package cn.techoc.his.controller;

import cn.techoc.his.entity.Hospital;
import cn.techoc.his.service.HospitalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author techoc
 */
@Controller
@Api(tags = "医院接口")
public class HospitalController {

    @Resource
    private HospitalService hospitalService;

    @GetMapping("/hospitals")
    @ResponseBody
    @ApiOperation(value = "获取医院列表", notes = "获取医院列表")
    public List<Hospital> getHospitalList() {
        return hospitalService.list();
    }
}
