package cn.techoc.his.vo;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * 返回对象封装
 *
 * @author techoc
 */
@Data
public class Resp {
    private int state;
    private String message;
    private Object obj;

    public static Resp success(String message, Object obj) {
        Resp resp = new Resp();
        resp.setState(HttpStatus.OK.value());
        resp.setMessage(message);
        resp.setObj(obj);
        return resp;
    }

    public static Resp success(String message) {
        return success(message, null);
    }

    public static Resp success() {
        return success(null, null);
    }

    public static Resp error(String message) {
        Resp resp = new Resp();
        resp.setState(HttpStatus.INTERNAL_SERVER_ERROR.value());
        resp.setMessage(message);
        return resp;
    }
}
