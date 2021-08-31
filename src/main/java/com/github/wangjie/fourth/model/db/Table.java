package com.github.wangjie.fourth.model.db;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.*;

import java.util.Date;

/**
 * @ClassName Table
 * @Description
 * @Author wangjie
 * @Date 2020/5/21 1:31 下午
 * @Email wangjie_fourth@163.com
 **/
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuppressFBWarnings("EI_EXPOSE_REP")
public class Table {

    private String engine;

    private Date createTime;

    private String tableComment;

    private String tableName;


}
