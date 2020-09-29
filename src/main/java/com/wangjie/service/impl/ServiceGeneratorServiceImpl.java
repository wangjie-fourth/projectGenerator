package com.wangjie.service.impl;

import com.wangjie.context.GeneratorContext;
import com.wangjie.service.AbstractGeneratorService;

import java.io.IOException;

public class ServiceGeneratorServiceImpl extends AbstractGeneratorService {
    @Override
    public GeneratorContext generator(GeneratorContext generatorContext) throws IOException {
        String serviceName = generatorContext.getJavaDTO().getClassName() + "Service.java";
        context.put("java", generatorContext.getJavaDTO());
        String content = generatorModel("template/deyi/Service.java.vm");
        stringToFile(generatorContext.getProjectMetaInfo().getProjectDirector(),
                generatorContext.getServicePrefix(), serviceName, content);
        return null;
    }
}
