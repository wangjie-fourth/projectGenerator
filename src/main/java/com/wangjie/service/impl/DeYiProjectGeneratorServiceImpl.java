package com.wangjie.service.impl;

import com.wangjie.GeneratorMojo;
import com.wangjie.service.ProjectGeneratorService;
import com.wangjie.service.dto.JavaDTO;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @ClassName DeYiProjectGeneratorServiceImpl
 * @Description
 * @Author wangjie
 * @Date 2020/5/18 5:02 下午
 * @Email wangjie_fourth@163.com
 **/
@SuppressFBWarnings({"ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD", "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE", "DM_DEFAULT_ENCODING"})
public class DeYiProjectGeneratorServiceImpl implements ProjectGeneratorService {

    private static final VelocityContext context;

    static {
        // 模版引擎
        Properties p = new Properties();
        p.setProperty("resource.loader", "class");
        p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(p);
        context = new VelocityContext();
    }



    @Override
    public void generatorManager(JavaDTO javaDTO,  String managerPrefix) throws IOException {
        String managerName = javaDTO.getClassName() + "Manager.java";
        context.put("java", javaDTO);
        String content = generatorModel("template/deyi/Manager.java.vm");
        stringToFile(managerPrefix, managerName, content);
    }

    @Override
    public void generatorDTO(JavaDTO javaDTO,  String dtoPrefix) throws IOException {
        String dtoName = javaDTO.getClassName() + "DTO.java";
        context.put("java", javaDTO);
        String content = generatorModel("template/deyi/DTO.java.vm");
        stringToFile(dtoPrefix, dtoName, content);
    }


    @Override
    public void generatorMapperJava(JavaDTO javaDTO,  String mapperJavaPrefix) throws IOException {
        String mapperName = javaDTO.getClassName() + "Mapper.java";
        context.put("java", javaDTO);
        String content = generatorModel("template/deyi/Mapper.java.vm");
        stringToFile(mapperJavaPrefix, mapperName, content);
    }

    @Override
    public void generatorMapperXml(JavaDTO javaDTO,  String mapperXmlPrefix) throws IOException {
        String mapperName = javaDTO.getClassName() + "Mapper.xml";
        context.put("java", javaDTO);
        String content = generatorModel("template/deyi/Mapper.xml.vm");
        stringToFile(mapperXmlPrefix, mapperName, content);
    }

    @Override
    public void generatorEntity(JavaDTO javaDTO,  String entityPrefix) throws IOException {
        String entityName = javaDTO.getClassName() + ".java";
        // 生成属性
        context.put("java", javaDTO);
        String content = generatorModel("template/deyi/Bean.java.vm");
        stringToFile(entityPrefix, entityName, content);
    }



    @Override
    public void generatorController(JavaDTO javaDTO,  String controllerPrefix) throws IOException {
        String controllerName = javaDTO.getClassName() + "Controller.java";
        context.put("java", javaDTO);
        String content = generatorModel("template/deyi/Controller.java.vm");
        stringToFile(controllerPrefix, controllerName, content);
    }

    private void stringToFile(String filePrefixDir, String fileName, String content) throws IOException {

        System.out.println(GeneratorMojo.projectDirector + filePrefixDir + fileName);

        Path pathToFile = Paths.get(GeneratorMojo.projectDirector + filePrefixDir + fileName);
        Files.createDirectories(pathToFile.getParent());
        Files.createFile(pathToFile);
        PrintWriter out = new PrintWriter(GeneratorMojo.projectDirector + filePrefixDir + fileName);
        out.println(content);
        out.close();

    }

    @Override
    public void generatorService(JavaDTO javaDTO,  String servicePrefix) throws IOException {
        String serviceName = javaDTO.getClassName() + "Service.java";
        context.put("java", javaDTO);
        String content = generatorModel("template/deyi/Service.java.vm");
        stringToFile(servicePrefix, serviceName, content);
    }


    private String generatorModel(String templateVm) {
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


}
