package com.wangjie.service;


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

    void generatorController(JavaDTO javaDTO) throws IOException;

    void generatorService(JavaDTO javaDTO) throws IOException;

    void generatorEntity(JavaDTO javaDTO) throws IOException;

    void generatorMapperJava(JavaDTO javaDTO) throws IOException;

    void generatorMapperXml(JavaDTO javaDTO) throws IOException;

    void generatorManager(JavaDTO javaDTO) throws IOException;

    void generatorDTO(JavaDTO generatorVo) throws IOException;
}
