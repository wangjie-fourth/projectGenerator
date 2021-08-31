package com.github.wangjie.fourth.model.config;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.*;

/**
 * @ClassName Table
 * @Description
 * @Author wangjie
 * @Date 2020/6/5 5:50 下午
 * @Email wangjie_fourth@163.com
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SuppressFBWarnings("EI_EXPOSE_REP")
public class TableConfig {
    private String tableName;
}
