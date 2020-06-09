package com.wangjie.model.config;

import lombok.*;

/**
 * @ClassName GeneratorConfig
 * @Description 
 * @Author wangjie
 * @Date 2020/6/9 3:02 下午
 * @Email wangjie_fourth@163.com
 **/

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeneratorConfig {

    private boolean generator;

    private String prefix;
}
