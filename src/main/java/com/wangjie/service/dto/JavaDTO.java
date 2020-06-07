package com.wangjie.service.dto;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @ClassName EntityDTO
 * @Description 编码中表对应的实体类
 * @Author wangjie
 * @Date 2020/5/29 4:44 下午
 * @Email wangjie_fourth@163.com
 **/
@Getter
@Setter
@Builder
@SuppressFBWarnings("EI_EXPOSE_REP")
public class JavaDTO {
    private String className;
    private String paramName;
    // 表备注
    private String note;
    private String author;
    private String email;
    private Date dateTime;
    // 包前缀
    private String packagePrefix;

    private List<AttrDTO> attrs;

    private String tableName;
}
