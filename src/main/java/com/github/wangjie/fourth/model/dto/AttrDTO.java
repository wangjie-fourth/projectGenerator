package com.github.wangjie.fourth.model.dto;


import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName AttrDTO
 * @Description
 * @Author wangjie
 * @Date 2020/6/1 11:44 上午
 * @Email wangjie_fourth@163.com
 **/
@Getter
@Setter
public class AttrDTO {

    private String attrName;
    private String attrType;
    private String attrNote;


    private boolean pk;
    private String jdbcType;
    private String columnName;

    /**
     * 该字段是否生成。因为有些字段会以继承的方式获取
     */
    private boolean generator = true;
}
