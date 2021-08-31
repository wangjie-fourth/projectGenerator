package com.github.wangjie.fourth.generator.impl;

import com.github.wangjie.fourth.context.GeneratorContext;
import com.github.wangjie.fourth.generator.AbstractGeneratorService;

import java.io.IOException;

public class MapperJGeneratorServiceImpl extends AbstractGeneratorService {
    @Override
    public GeneratorContext generator(GeneratorContext generatorContext) throws IOException {
        String mapperName = generatorContext.getJavaDTO().getClassName() + "Mapper.java";
        context.put("java", generatorContext.getJavaDTO());
        String content = generatorModel("template/deyi/Mapper.java.vm");
        stringToFile(generatorContext.getProjectMetaInfo().getProjectDirector(),
                generatorContext.getMapperJPrefix(), mapperName, content);
        return generatorContext;
    }
}
