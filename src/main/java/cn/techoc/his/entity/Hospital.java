package cn.techoc.his.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 医院实体类
 *
 * @author techoc
 */
@Data
@ApiModel(value = "医院实体类")
@TableName("hospital")
public class Hospital {
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 医院名称
     */
    @ApiModelProperty(value = "医院名称")
    private String name;

    /**
     * 医院具体位置信息
     */
    @ApiModelProperty(value = "医院具体位置信息")
    private String address;

    /**
     * 医院所在地区
     */
    @ApiModelProperty(value = "医院所在地区")
    private String location;

    /**
     * 医院电话
     */
    @ApiModelProperty(value = "医院电话")
    private String phone;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private String latitude;

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private String longitude;
}
