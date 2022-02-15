package cn.techoc.his.service.impl;

import cn.techoc.his.entity.Hospital;
import cn.techoc.his.mapper.HospitalMapper;
import cn.techoc.his.service.HospitalService;
import cn.techoc.his.utils.BaiDuMapUtils;
import cn.techoc.his.utils.Convert;
import cn.techoc.his.utils.VOUtils;
import cn.techoc.his.vo.HospitalVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author techoc
 */
@Service
public class HospitalServiceImpl extends ServiceImpl<HospitalMapper, Hospital>
    implements HospitalService {
    @Resource
    private BaiDuMapUtils baiDuMapUtils;

    @Resource
    private HospitalMapper hospitalMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addHospital(Hospital hospital) {
        // 判断是否已经存在
        Hospital one = this.getOne(
            new LambdaQueryWrapper<Hospital>().eq(Hospital::getName, hospital.getName())
                .eq(Hospital::getEnable, 1)
        );
        if (one != null) {
            return false;
        }

        // 获取经纬度
        String geocoder = baiDuMapUtils.getGeocoder(hospital.getAddress());
        //切割经度和纬度
        String[] split = geocoder.split(",");
        hospital.setLatitude(split[0]);
        hospital.setLongitude(split[1]);
        hospital.setEnable(1);
        return this.save(hospital);
    }

    @Override
    public Map<HospitalVO, Integer> getHospitalDistance(String address, String location) {
        //查询当地的医院经纬度
        List<Hospital> hospitalList = hospitalMapper.selectList(
            new LambdaQueryWrapper<Hospital>().eq(Hospital::getLocation, location)
                .eq(Hospital::getEnable, 1)
        );
        List<String> destinations = new ArrayList<>();
        for (Hospital hospital : hospitalList) {
            destinations.add(hospital.getLatitude() + "," + hospital.getLongitude());
        }
        //查询用户地址与医院的距离
        System.out.println(address);
        System.out.println(destinations);
        HashMap<String, String> distance = baiDuMapUtils.getRideDistance(address, destinations);

        //获取医院信息
        HashMap<HospitalVO, Integer> distances = new HashMap<>(hospitalList.size());
        distance.forEach((k, v) -> {
            String[] split = k.split(",");
            Hospital hospital = hospitalMapper.selectOne(
                new LambdaQueryWrapper<Hospital>().eq(Hospital::getLatitude, split[0])
                    .eq(Hospital::getLongitude, split[1])
            );
            distances.put(VOUtils.convert(hospital, HospitalVO.class),
                Convert.distanceConverter(v));
        });
        return distances;
    }
}
