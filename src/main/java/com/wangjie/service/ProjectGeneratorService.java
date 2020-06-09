package com.wangjie.service;


import com.wangjie.service.dto.JavaDTO;

import javax.annotation.Nullable;
import java.io.IOException;

/**
 * @ClassName ProjectGeneratorService
 * @Description
 * @Author wangjie
 * @Date 2020/5/18 5:01 下午
 * @Email wangjie_fourth@163.com
 **/

public interface ProjectGeneratorService {

    void generatorController(JavaDTO javaDTO, @Nullable String controllerPrefix) throws IOException;

    void generatorService(JavaDTO javaDTO, @Nullable String servicePrefix) throws IOException;

    void generatorEntity(JavaDTO javaDTO, @Nullable String entityPrefix) throws IOException;

    void generatorMapperJava(JavaDTO javaDTO, @Nullable String mapperJavaPrefix) throws IOException;

    void generatorMapperXml(JavaDTO javaDTO, @Nullable String mapperXmlPrefix) throws IOException;

    void generatorManager(JavaDTO javaDTO, @Nullable String managerPrefix) throws IOException;

    void generatorDTO(JavaDTO generatorVo, @Nullable String dtoPrefix) throws IOException;
}
