package cn.techoc.his.service;

import cn.techoc.his.entity.Hospital;
import cn.techoc.his.vo.HospitalVO;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Map;

/**
 * @author techoc
 */
public interface HospitalService extends IService<Hospital> {

    /**
     * 添加医院
     *
     * @param hospital 医院
     * @return 添加结果
     */
    Boolean addHospital(Hospital hospital);

    /**
     * 获取用户与医院的距离
     *
     * @param address  用户地址经纬度
     * @param location 医院所在地区
     * @return 集合
     */
    Map<HospitalVO, Integer> getHospitalDistance(String address, String location);
}
