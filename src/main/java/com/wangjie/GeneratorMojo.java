package com.wangjie;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.CaseFormat;
import com.wangjie.model.config.ConfigJson;
import com.wangjie.model.config.Table;
import com.wangjie.service.ProjectGeneratorService;
import com.wangjie.service.dto.JavaDTO;
import com.wangjie.service.impl.DeYiProjectGeneratorServiceImpl;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName com.wangjie.GeneratorMojo
 * @Description
 * @Author wangjie
 * @Date 2020/6/4 11:49 上午
 * @Email wangjie_fourth@163.com
 **/
@Mojo(name = "generator")
@SuppressFBWarnings({"MS_CANNOT_BE_FINAL", "ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD"})
public class GeneratorMojo extends AbstractMojo {
    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();
    public static String projectDirector = "";


    public void execute() {
        projectDirector = System.getProperty("user.dir");

        getLog().info("读取配置文件：项目根目录下的projectGenerator.json");
        ConfigJson configJson = null;
        // 读取配置文件：项目根目录下的projectGenerator.json
        try {
            File file = new File("projectGenerator.json");
            StringBuilder configInfo = new StringBuilder();
            List<String> configList = IOUtils.readLines(new FileInputStream(file), Charset.defaultCharset());
            configList.forEach(configInfo::append);
            configJson = MAPPER.readValue(configInfo.toString(), ConfigJson.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 按照表
        getLog().info("生成java文件");
        if (Objects.isNull(configJson)
                || Objects.isNull(configJson.getTables())
                || configJson.getTables().size() == 0) {
            getLog().info("无需要生成的表信息");
        } else {
            for (Table table : configJson.getTables()) {
                String paramClass = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "table_info");
                String className = Character.toUpperCase(paramClass.charAt(0)) + paramClass.substring(1);
                JavaDTO javaDTO = JavaDTO.builder()
                        .paramName(paramClass)
                        .className(className)
                        .note("test")
                        .author(configJson.getAuthor())
                        .email(configJson.getEmail())
                        .dateTime(new Date())
                        .packagePrefix(configJson.getPackagePrefix())
                        .tableName(table.getTableName())
                        .build();

                // 生成指定的文件，并放到指定的位置
                ProjectGeneratorService service = new DeYiProjectGeneratorServiceImpl(configJson);
                try {
                    service.generatorController(javaDTO);
                    service.generatorService(javaDTO);
                    service.generatorManager(javaDTO);
                    service.generatorEntity(javaDTO);
                    service.generatorDTO(javaDTO);
                    service.generatorMapperJava(javaDTO);
                    service.generatorMapperXml(javaDTO);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static void main(String[] args) {
        new GeneratorMojo().execute();
        ;
    }
}

