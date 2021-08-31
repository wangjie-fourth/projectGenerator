package com.github.wangjie.fourth.context;


import com.github.wangjie.fourth.model.config.ConfigJson;
import com.github.wangjie.fourth.model.config.ProjectMetaInfo;
import com.github.wangjie.fourth.model.dto.JavaDTO;
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

    private String mapperJPrefix;
    private String mapperXPrefix;
    private String entityPrefix;
}
