package com.wangjie.generator.impl;

import com.wangjie.context.GeneratorContext;
import com.wangjie.generator.AbstractGeneratorService;

import java.io.IOException;

public class MapperXGeneratorServiceImpl extends AbstractGeneratorService {
    @Override
    public GeneratorContext generator(GeneratorContext generatorContext) throws IOException {
        String mapperName = generatorContext.getJavaDTO().getClassName() + "Mapper.xml";
        context.put("java", generatorContext.getJavaDTO());
        String content = generatorModel("template/deyi/Mapper.xml.vm");
        stringToFile(generatorContext.getProjectMetaInfo().getProjectDirector(),
                generatorContext.getMapperXPrefix(), mapperName, content);
        return generatorContext;
    }
}
