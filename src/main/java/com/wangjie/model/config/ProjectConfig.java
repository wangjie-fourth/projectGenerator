package com.wangjie.model.config;

import lombok.*;

/**
 * @ClassName ProjectConfig
 * @Description
 * @Author wangjie
 * @Date 2020/6/9 3:01 下午
 * @Email wangjie_fourth@163.com
 **/

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectConfig {



    private GeneratorConfig mapperJ;

    private GeneratorConfig mapperX;

    private GeneratorConfig entity;
}
