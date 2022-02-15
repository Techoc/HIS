package cn.techoc.his.utils;

import org.springframework.util.StringUtils;

/**
 * 距离转换器
 *
 * @author techoc
 */
public class Convert {


    public static Integer distanceConverter(String distance) {
        if (StringUtils.isEmpty(distance)) {
            return null;
        }
        String kilometre = "公里";
        String meter = "米";
        if (distance.endsWith(kilometre)) {
            double v = Double.parseDouble(distance.substring(0, distance.length() - 2));
            return (int) (v * 1000);
        }
        if (distance.endsWith(meter)) {
            double v = Double.parseDouble(distance.substring(0, distance.length() - 1));
            return (int) (v * 1000);
        }
        return null;
    }
}

