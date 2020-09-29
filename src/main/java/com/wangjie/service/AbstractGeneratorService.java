package com.wangjie.service;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public abstract class AbstractGeneratorService implements GeneratorService {

    protected static final VelocityContext context;

    static {
        // 模版引擎
        Properties p = new Properties();
        p.setProperty("resource.loader", "class");
        p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(p);
        context = new VelocityContext();
    }

    protected String generatorModel(String templateVm) {
        // 生成模板
        Template template = null;
        try {
            template = Velocity.getTemplate(templateVm, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        StringWriter sw = new StringWriter();
        template.merge(context, sw);
        return sw.toString();
    }

    @SuppressFBWarnings(value = "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE", justification = "there is no way to produce null")
    protected void stringToFile(String projectDirector, String filePrefixDir, String fileName, String content) throws IOException {
        Path pathToFile = Paths.get(projectDirector + filePrefixDir + fileName);
        Files.createDirectories(pathToFile.getParent());
        Files.createFile(pathToFile);
        PrintWriter out = new PrintWriter(new File(projectDirector + filePrefixDir + fileName), "UTF_8");
        out.println(content);
        out.close();
    }


}
