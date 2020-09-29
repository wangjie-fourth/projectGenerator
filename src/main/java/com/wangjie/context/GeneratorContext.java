package com.wangjie.context;


import com.wangjie.model.config.ConfigJson;
import com.wangjie.model.config.ProjectMetaInfo;
import com.wangjie.service.dto.JavaDTO;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeneratorContext {
    private ProjectMetaInfo projectMetaInfo;
    private ConfigJson configJson;

    // 按单个表做记录
    private JavaDTO javaDTO;

    private String controllerPrefix;
    private String servicePrefix;
    private String mapperJPrefix;
    private String mapperXPrefix;
    private String entityPrefix;
}
