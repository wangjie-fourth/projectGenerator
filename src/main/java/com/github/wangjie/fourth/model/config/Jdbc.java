package com.github.wangjie.fourth.model.config;

import lombok.*;

/**
 * @ClassName Jdbc
 * @Description
 * @Author wangjie
 * @Date 2020/6/5 5:49 下午
 * @Email wangjie_fourth@163.com
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Jdbc {

    private String url;

    private String username;

    private String password;

    private String driverClassName;

}
