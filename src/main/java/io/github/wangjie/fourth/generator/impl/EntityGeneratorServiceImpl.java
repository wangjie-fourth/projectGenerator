package io.github.wangjie.fourth.generator.impl;

import io.github.wangjie.fourth.context.GeneratorContext;
import io.github.wangjie.fourth.generator.AbstractGeneratorService;

import java.io.IOException;

public class EntityGeneratorServiceImpl extends AbstractGeneratorService {
    @Override
    public GeneratorContext generator(GeneratorContext generatorContext) throws IOException {
        String entityName = generatorContext.getJavaDTO().getClassName() + ".java";
        context.put("java", generatorContext.getJavaDTO());
        String content = generatorModel("template/deyi/Bean.java.vm");
        stringToFile(generatorContext.getProjectMetaInfo().getProjectDirector(),
                generatorContext.getEntityPrefix(), entityName, content);
        return generatorContext;
    }
}
