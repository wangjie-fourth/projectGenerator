package com.wangjie.generator.impl;

import com.wangjie.context.GeneratorContext;
import com.wangjie.generator.AbstractGeneratorService;

import java.io.IOException;

public class ControllerGeneratorServiceImpl extends AbstractGeneratorService {
    @Override
    public GeneratorContext generator(GeneratorContext generatorContext) throws IOException {
        String controllerName = generatorContext.getJavaDTO().getClassName() + "Controller.java";
        context.put("java", generatorContext.getJavaDTO());
        String content = generatorModel("template/deyi/Controller.java.vm");
        stringToFile(generatorContext.getProjectMetaInfo().getProjectDirector(), generatorContext.getControllerPrefix(), controllerName, content);
        return generatorContext;
    }
}
