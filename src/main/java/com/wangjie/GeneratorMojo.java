package com.wangjie;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wangjie.model.config.ConfigJson;
import com.wangjie.context.GeneratorContext;
import com.wangjie.model.config.ProjectMetaInfo;
import com.wangjie.model.config.TableConfig;
import com.wangjie.service.GeneratorService;
import com.wangjie.service.dto.JavaDTO;
import com.wangjie.service.impl.*;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
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


    public void execute() {
        GeneratorContext generatorContext = new GeneratorContext();
        checkForNecessarySystemProperties(generatorContext);

        ConfigJson configJson = readConfigFile();
        generatorContext.setConfigJson(configJson);

        // 判断是否有必要生成数据
        getLog().info("生成java文件");
        if (Objects.isNull(generatorContext.getConfigJson())
                || Objects.isNull(generatorContext.getConfigJson().getTableConfigs())
                || generatorContext.getConfigJson().getTableConfigs().size() == 0) {
            getLog().info("无需要生成的表信息");
            return;
        }

        for (TableConfig tableConfig : configJson.getTableConfigs()) {
            JavaDTO javaDTO = DdDataUtils.readDataFromDB(tableConfig.getTableName(), configJson);
            javaDTO.setControllerPrefix(generatorContext.getConfigJson().getProjectConfig().getController().getPrefix());
            javaDTO.setServicePrefix(generatorContext.getConfigJson().getProjectConfig().getService().getPrefix());
            javaDTO.setBeanPrefix(generatorContext.getConfigJson().getProjectConfig().getEntity().getPrefix());
            javaDTO.setMapperJPrefix(generatorContext.getConfigJson().getProjectConfig().getMapperJ().getPrefix());

            generatorContext.setJavaDTO(javaDTO);

            // 生成指定的文件，并放到指定的位置
            GeneratorService service = null;
            try {
                if (needGeneratorController(generatorContext.getConfigJson())) {
                    service = new ControllerGeneratorServiceImpl();
                    String controllerPrefix = getConfigPath("/src/main/java/", generatorContext.getConfigJson(), "/web/controller/", 1);
                    generatorContext.setControllerPrefix(controllerPrefix);
                    service.generator(generatorContext);
                }
                if (needGeneratorService(generatorContext.getConfigJson())) {
                    service = new ServiceGeneratorServiceImpl();
                    String servicePrefix = getConfigPath("/src/main/java/", generatorContext.getConfigJson(), "/service/", 2);
                    generatorContext.setServicePrefix(servicePrefix);
                    service.generator(generatorContext);
                }
                if (needGeneratorEntity(generatorContext.getConfigJson())) {
                    service = new EntityGeneratorServiceImpl();
                    String entityPrefix = getConfigPath("/src/main/java/", generatorContext.getConfigJson(), "/bean/db/", 4);
                    generatorContext.setEntityPrefix(entityPrefix);
                    service.generator(generatorContext);
                }
                if (needGeneratorMapperJava(generatorContext.getConfigJson())) {
                    service = new MapperJGeneratorServiceImpl();
                    String mapperJavaPrefix = getConfigPath("/src/main/java/", generatorContext.getConfigJson(), "/mapper/", 6);
                    generatorContext.setMapperJPrefix(mapperJavaPrefix);
                    service.generator(generatorContext);
                }
                if (needGeneratorMapperXml(generatorContext.getConfigJson())) {
                    service = new MapperXGeneratorServiceImpl();
                    String mapperXmlPrefix = getMapperXmlPrefix(generatorContext.getConfigJson());
                    generatorContext.setMapperXPrefix(mapperXmlPrefix);
                    service.generator(generatorContext);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void checkForNecessarySystemProperties(GeneratorContext generatorContext) {
        ProjectMetaInfo projectMetaInfo = new ProjectMetaInfo();

        String projectDirector = System.getProperty("user.dir");
        if (projectDirector.isEmpty()) {
            throw new RuntimeException("缺少必要的系统属性user.dir");
        }
        projectMetaInfo.setProjectDirector(projectDirector);

        generatorContext.setProjectMetaInfo(projectMetaInfo);
    }

    private ConfigJson readConfigFile() {
        getLog().info("读取配置文件：项目根目录下的projectGenerator.json");
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
        BigDecimal a = new BigDecimal("0.00");
        if (a.equals(BigDecimal.ZERO)) {
            System.out.println("a = " + a);
        }
        new GeneratorMojo().execute();
    }
}

