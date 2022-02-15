package cn.techoc.his.controller;

import cn.techoc.his.entity.Hospital;
import cn.techoc.his.service.HospitalService;
import cn.techoc.his.utils.VOUtils;
import cn.techoc.his.vo.HospitalVO;
import cn.techoc.his.vo.Resp;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author techoc
 */
@Controller
@Api(tags = "医院接口")
@RequestMapping("/hospital")
@ResponseBody
public class HospitalController {

    @Resource
    private HospitalService hospitalService;

    @GetMapping("/getAll")
    @ApiOperation(value = "获取医院列表", notes = "获取医院列表")
    public Resp getHospitalList() {
        LambdaQueryWrapper<Hospital> queryWrapper = new LambdaQueryWrapper<>();
        List<Hospital> hospitalList = hospitalService.list(queryWrapper.eq(Hospital::getEnable, 1));
        List<HospitalVO> hospitalVOList = VOUtils.convert(hospitalList, HospitalVO.class);
        return Resp.success("获取医院列表成功", hospitalVOList);
    }

    @PostMapping("/add")
    @ApiOperation(value = "添加医院", notes = "添加医院")
    public Resp addHospital(HospitalVO hospitalVO) {
        Hospital hospital = VOUtils.convert(hospitalVO, Hospital.class);
        if (hospitalService.addHospital(hospital)) {
            return Resp.success("添加医院成功");
        }
        return Resp.error("添加医院失败");
    }

    @GetMapping("/getDistance")
    @ApiOperation(value = "获取医院距离", notes = "获取医院距离")
    public Resp getDistance(String address, String location) {
        //TODO 从当前登录用户中获取用户经纬度信息
        return Resp.success("获取医院距离成功", hospitalService.getHospitalDistance(address, location));
    }
}
