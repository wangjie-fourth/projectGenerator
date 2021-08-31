package com.github.wangjie.fourth.generator;

import com.github.wangjie.fourth.context.GeneratorContext;

import java.io.IOException;

/**
 * 生成模型的接口
 */
public interface GeneratorService {

     GeneratorContext generator(GeneratorContext generatorContext) throws IOException;
}
