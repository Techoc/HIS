package cn.techoc.his.utils;

import com.ejlchina.data.Mapper;
import com.ejlchina.okhttps.OkHttps;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * 百度地图API工具类
 *
 * @author techoc
 */
@Component
@Slf4j
public class BaiDuMapUtils {

    @Value("${baidu.map.ak}")
    private String appKey;

    @Value("${baidu.map.comprehension}")
    private Integer comprehension;

    /**
     * 获取百度地图API的地理编码接口
     *
     * @param address 地址
     * @return 返回经纬度
     */
    public String getGeocoder(String address) {
        String statusMessage;
        Mapper mapper = OkHttps.async("https://api.map.baidu.com/geocoding/v3/")
                .addUrlPara("address", address)
                .addUrlPara("output", "json")
                .addUrlPara("ak", appKey)
                .setOnException((IOException e) -> {
                    // 这里处理请求异常
                    log.error("请求百度地图API的地理编码接口异常：{}", e.getMessage());
                })
                .get().getResult().getBody().toMapper();

        int status = mapper.getInt("status");
        if (status == 0) {
            int getComprehension = mapper.getMapper("result").getInt("comprehension");
            //判断描述地址理解程度。分值范围0-100，分值越大，服务对地址理解程度越高
            if (getComprehension >= comprehension) {
                String lng = mapper.getMapper("result").getMapper("location").getString("lng");
                String lat = mapper.getMapper("result").getMapper("location").getString("lat");
                String location = lat + "," + lng;
                log.info("{}获取经纬度成功：{},地址理解程度为{}", address, location, getComprehension);
                return location;
            } else {
                log.error("{}地址理解程度为：{},未达到{}预期标准", address, getComprehension, comprehension);
                return null;
            }
        }
        switch (mapper.getInt("status")) {
            case 0:
                statusMessage = "请求成功";
                break;
            case 1:
                statusMessage = "服务器内部错误";
                break;
            case 2:
                statusMessage = "请求参数非法";
                break;
            case 3:
                statusMessage = "权限校验失败";
                break;
            case 4:
                statusMessage = "配额校验失败";
                break;
            case 5:
                statusMessage = "ak不存在或者非法";
                break;
            case 101:
                statusMessage = "服务禁用";
                break;
            case 102:
                statusMessage = "不通过白名单或者安全码不对";
                break;
            case 200:
                statusMessage = "无权限";
                break;
            case 300:
                statusMessage = "配额错误";
                break;
            default:
                statusMessage = "未知错误";
        }
        log.error("获取经纬度失败：{}", statusMessage);
        return null;
    }

    /**
     * 批量查询，终点不能超过50个
     * 通过两地经纬度获取两地之间的步行距离
     *
     * @param destinations 目的地经纬度数组
     * @param origin       出发地经纬度
     * @return 返回两地之间的步行距离
     */
    public HashMap<String, String> getWalkDistance(String origin, List<String> destinations) {
        //转化成需要的格式
        String destination = StringUtils.join(destinations, '|');
        Mapper mapper = OkHttps.async("https://api.map.baidu.com/routematrix/v2/walking")
                .addUrlPara("origins", origin)
                .addUrlPara("destinations", destination)
                .addUrlPara("output", "json")
                .addUrlPara("ak", appKey)
                .setOnException((IOException e) -> {
                    // 这里处理请求异常
                    log.error("请求百度地图API的步行距离接口异常：{}", e.getMessage());
                })
                .get().getResult().getBody().toMapper();
        int status = mapper.getInt("status");
        if (status == 0) {
            // 将获得的数据转化HashMap返回
            HashMap<String, String> distance = formatDistanceToMap(destinations, mapper);
            log.info("获取步行距离成功：{}", distance);
            return distance;
        }
        log.error("获取步行距离失败：{}", mapper.getString("message"));
        return null;
    }

    /**
     * 批量查询，终点不能超过50个
     * 通过两地经纬度获取两地之间的驾车距离
     *
     * @param destinations 目的地经纬度数组
     * @param origin       出发地经纬度
     * @return 返回两地之间的驾车距离
     */
    public HashMap<String, String> getCarDistance(String origin, List<String> destinations) {
        //转化成需要的格式
        String destination = StringUtils.join(destinations, '|');
        Mapper mapper = OkHttps.async("https://api.map.baidu.com/routematrix/v2/driving")
                .addUrlPara("origins", origin)
                .addUrlPara("destinations", destination)
                .addUrlPara("output", "json")
                .addUrlPara("ak", appKey)
                .setOnException((IOException e) -> {
                    // 这里处理请求异常
                    log.error("请求百度地图API的驾车距离接口异常：{}", e.getMessage());
                })
                .get().getResult().getBody().toMapper();
        int status = mapper.getInt("status");
        if (status == 0) {
            // 将获得的数据转化HashMap返回
            HashMap<String, String> distance = formatDistanceToMap(destinations, mapper);
            log.info("获取驾车距离成功：{}", distance);
            return distance;
        }
        log.error("获取驾车距离失败：{}", mapper.getString("message"));
        return null;
    }

    /**
     * 批量查询，终点不能超过50个
     * 通过两地经纬度获取两地之间的骑行距离
     *
     * @param origin       出发地经纬度
     * @param destinations 目的地经纬度数组
     * @return 返回两地之间的骑行距离
     */
    public HashMap<String, String> getRideDistance(String origin, List<String> destinations) {
        //转化成需要的格式
        String destination = StringUtils.join(destinations, '|');
        Mapper mapper = OkHttps.async("http://api.map.baidu.com/routematrix/v2/riding")
                .addUrlPara("origins", origin)
                .addUrlPara("destinations", destination)
                .addUrlPara("output", "json")
                .addUrlPara("ak", appKey)
                .setOnException((IOException e) -> {
                    // 这里处理请求异常
                    log.error("请求百度地图API的骑行距离接口异常：{}", e.getMessage());
                })
                .get().getResult().getBody().toMapper();
        int status = mapper.getInt("status");
        if (status == 0) {
            // 将获得的数据转化HashMap返回
            HashMap<String, String> distance = formatDistanceToMap(destinations, mapper);
            log.info("获取骑行距离成功：{}", distance);
            return distance;
        }
        log.error("获取驾车距离失败：{}", mapper.getString("message"));
        return null;
    }

    /**
     * 将获得的数据转化HashMap返回
     *
     * @param destinations 传入的目的地
     * @param mapper       获取的mapper对象
     * @return 返回Map集合 {"30.585719709239196,114.26720634050604":"24.8公里"}
     */
    private HashMap<String, String> formatDistanceToMap(List<String> destinations, Mapper mapper) {
        HashMap<String, String> distance = new HashMap<>(16);
        mapper.getArray("result").forEach(((index, data) -> distance.put(destinations.get(index), data.toMapper().getMapper("distance").getString("text"))));
        return distance;
    }

}