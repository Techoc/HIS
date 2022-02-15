package cn.techoc.his.utils;

import cn.hutool.core.bean.BeanUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * VO工具类
 * @author techoc
 */
public class VOUtils {

    /**
     * 将对象转换为VO,只支持单个对象
     *
     * @param clazz VO类型
     * @param obj   对象
     * @param <T>   VO类型
     * @return VO
     */
    public static <T> T convert(Object obj, Class<T> clazz) {
        return BeanUtil.copyProperties(obj, clazz);
    }

    /**
     * 将对象列表转换为VO列表
     *
     * @param clazz VO类型
     * @param list  对象列表
     * @param <T>   VO类型
     * @return VO列表
     */
    public static <T> List<T> convert(List<?> list, Class<T> clazz) {
        List<T> result = new ArrayList<>();
        for (Object t : list) {
            result.add(BeanUtil.copyProperties(t, clazz));
        }
        return result;
    }
}
