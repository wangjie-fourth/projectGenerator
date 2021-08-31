package io.github.wangjie.fourth.model.config;

import lombok.*;

import java.util.List;

/**
 * @ClassName ConfigJson
 * @Description
 * @Author wangjie
 * @Date 2020/6/5 5:47 下午
 * @Email wangjie_fourth@163.com
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfigJson {

    private String author;

    private String email;

    private Jdbc jdbc;

    private List<TableConfig> tableConfigs;

    private ProjectConfig projectConfig;
}
