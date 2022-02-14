package cn.techoc.his.service.impl;

import cn.techoc.his.entity.Hospital;
import cn.techoc.his.mapper.HospitalMapper;
import cn.techoc.his.service.HospitalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author techoc
 */
@Service
public class HospitalServiceImpl extends ServiceImpl<HospitalMapper, Hospital> implements HospitalService {
}
