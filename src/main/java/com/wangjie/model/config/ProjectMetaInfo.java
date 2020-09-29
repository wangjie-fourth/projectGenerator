package com.wangjie.model.config;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMetaInfo {

    /**
     * 项目根目录的绝对路径
     */
    private String projectDirector;

}
