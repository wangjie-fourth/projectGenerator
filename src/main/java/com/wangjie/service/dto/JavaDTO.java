package com.wangjie.service.dto;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.*;

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
@AllArgsConstructor
@NoArgsConstructor
public class JavaDTO {
    // 包前缀
    private String packagePrefix;
    private String email;
    private Date dateTime;
    private String author;

    private String className;
    private String paramName;
    private String tableName;


    // 表备注
    private String note;
    private List<AttrDTO> attrs;
}
