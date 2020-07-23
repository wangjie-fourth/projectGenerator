package com.wangjie;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.List;
import java.util.Objects;

import static com.wangjie.WorkFlowUtils.*;

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

        ConfigJson configJson = readConfigFile();

        // 按照表
        getLog().info("生成java文件");
        if (Objects.isNull(configJson)
                || Objects.isNull(configJson.getTables())
                || configJson.getTables().size() == 0) {
            getLog().info("无需要生成的表信息");
            return;
        }
        for (Table table : configJson.getTables()) {
            JavaDTO javaDTO = DdDataUtils.readDataFromDB(table.getTableName(), configJson);

            // 生成指定的文件，并放到指定的位置
            ProjectGeneratorService service = new DeYiProjectGeneratorServiceImpl();
            try {
                if (needGeneratorController(configJson)) {
                    String controllerPrefix = getConfigPath("/src/main/java/", configJson, "/web/controller/", 1);
                    service.generatorController(javaDTO, controllerPrefix);
                }
                if (needGeneratorService(configJson)) {
                    String servicePrefix = getConfigPath("/src/main/java/", configJson, "/service/", 2);
                    service.generatorService(javaDTO, servicePrefix);
                }
                if (needGeneratorManager(configJson)) {
                    String managerPrefix = getConfigPath("/src/main/java/", configJson, "/manager/", 3);
                    service.generatorManager(javaDTO, managerPrefix);
                }
                if (needGeneratorEntity(configJson)) {
                    String entityPrefix = getConfigPath("/src/main/java/", configJson, "/bean/db/", 4);
                    service.generatorEntity(javaDTO, entityPrefix);
                }
                if (needGeneratorDTO(configJson)) {
                    String dtoPrefix = getConfigPath("/src/main/java/", configJson, "/bean/dto/", 5);
                    service.generatorDTO(javaDTO, dtoPrefix);
                }
                if (needGeneratorMapperJava(configJson)) {
                    String mapperJavaPrefix = getConfigPath("/src/main/java/", configJson, "/mapper/", 6);
                    service.generatorMapperJava(javaDTO, mapperJavaPrefix);
                }
                if (needGeneratorMapperXml(configJson)) {
                    String mapperXmlPrefix = getMapperXmlPrefix(configJson);
                    service.generatorMapperXml(javaDTO, mapperXmlPrefix);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private ConfigJson readConfigFile() {
        getLog().info("读取配置文件：项目根目录下的projectGenerator.json");
        // 读取配置文件：项目根目录下的projectGenerator.json
        try {
            File file = new File("projectGenerator.json");
            StringBuilder configInfo = new StringBuilder();
            List<String> configList = IOUtils.readLines(new FileInputStream(file), Charset.defaultCharset());
            configList.forEach(configInfo::append);
            return MAPPER.readValue(configInfo.toString(), ConfigJson.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        new GeneratorMojo().execute();
    }
}

