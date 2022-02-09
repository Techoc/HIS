package cn.techoc.his;

import cn.techoc.his.utils.BaiDuMapUtils;
import com.sun.tools.javac.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Collections;

@SpringBootTest
class HisApplicationTests {
    @Resource
    private BaiDuMapUtils baiDuMapUtils;

    @Test
    void contextLoads() {
        String loaction = baiDuMapUtils.getGeocoder("武汉市东港科技园");
        String location1 = baiDuMapUtils.getGeocoder("武汉市荣华苑");
        String location2 = baiDuMapUtils.getGeocoder("武汉市第一医院");
        String location3 = baiDuMapUtils.getGeocoder("武汉市同济医院");
        System.out.println(baiDuMapUtils.getRideDistance(loaction, List.of(location1, location2,location3)));
//        System.out.println(baiDuMapUtils.getRideDistance(loaction, Collections.singletonList(location2)));
    }

}
