package com.wangjie.service;


import com.wangjie.context.GeneratorContext;
import com.wangjie.service.dto.JavaDTO;

import java.io.IOException;

/**
 * @ClassName ProjectGeneratorService
 * @Description
 * @Author wangjie
 * @Date 2020/5/18 5:01 下午
 * @Email wangjie_fourth@163.com
 **/

public interface ProjectGeneratorService {

    void generatorController(JavaDTO javaDTO,  String controllerPrefix) throws IOException;

    void generatorService(JavaDTO javaDTO,  String servicePrefix) throws IOException;

    void generatorEntity(JavaDTO javaDTO,  String entityPrefix) throws IOException;

    void generatorMapperJava(JavaDTO javaDTO,  String mapperJavaPrefix) throws IOException;

    void generatorMapperXml(JavaDTO javaDTO,  String mapperXmlPrefix) throws IOException;

    void generator(GeneratorContext generatorContext) throws Exception;
}
