package com.wangjie;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.CaseFormat;
import com.wangjie.mapper.ColumnMapper;
import com.wangjie.mapper.TableMapper;
import com.wangjie.model.config.ConfigJson;
import com.wangjie.model.config.Table;
import com.wangjie.service.ProjectGeneratorService;
import com.wangjie.service.dto.JavaDTO;
import com.wangjie.service.impl.DeYiProjectGeneratorServiceImpl;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Mojo;

import javax.sql.DataSource;
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


    private String getTableNote(String tableName, ConfigJson configJson) {
        DataSource dataSource = null;
        dataSource = new PooledDataSource("com.mysql.cj.jdbc.Driver",
                configJson.getJdbc().getUrl(),
                configJson.getJdbc().getUsername(),
                configJson.getJdbc().getPassword());

        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(TableMapper.class);
        configuration.addMapper(ColumnMapper.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        try (SqlSession session = sqlSessionFactory.openSession()) {
            TableMapper tableMapper = session.getMapper(TableMapper.class);
            com.wangjie.model.db.Table table = tableMapper.queryTable(tableName);
            return table.getTableComment();
        }
    }


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
                String paramClass = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, table.getTableName());
                String className = Character.toUpperCase(paramClass.charAt(0)) + paramClass.substring(1);
                JavaDTO javaDTO = JavaDTO.builder()
                        .paramName(paramClass)
                        .className(className)
                        .note(this.getTableNote(table.getTableName(), configJson))
                        .author(configJson.getAuthor())
                        .email(configJson.getEmail())
                        .dateTime(new Date())
                        .packagePrefix(configJson.getPackagePrefix())
                        .tableName(table.getTableName())
                        .build();

                // 生成指定的文件，并放到指定的位置
                ProjectGeneratorService service = new DeYiProjectGeneratorServiceImpl(configJson);
                try {
                    if (needGeneratorController(configJson)) {
                        service.generatorController(javaDTO);
                    }
                    if (needGeneratorService(configJson)) {
                        service.generatorService(javaDTO);
                    }
                    if (needGeneratorManager(configJson)) {
                        service.generatorManager(javaDTO);
                    }
                    if (needGeneratorEntity(configJson)) {
                        service.generatorEntity(javaDTO);
                    }
                    if (needGeneratorDTO(configJson)) {
                        service.generatorDTO(javaDTO);
                    }
                    if (needGeneratorMapperJava(configJson)) {
                        service.generatorMapperJava(javaDTO);
                    }
                    if (needGeneratorMapperXml(configJson)) {
                        service.generatorMapperXml(javaDTO);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private boolean needGeneratorMapperXml(ConfigJson configJson) {
        if (Objects.nonNull(configJson.getProjectConfig()) && Objects.nonNull(configJson.getProjectConfig().getMapperX())){
            return configJson.getProjectConfig().getMapperX().isGenerator();
        }
        return true;
    }

    private boolean needGeneratorMapperJava(ConfigJson configJson) {
        if (Objects.nonNull(configJson.getProjectConfig()) && Objects.nonNull(configJson.getProjectConfig().getMapperJ())){
            return configJson.getProjectConfig().getMapperJ().isGenerator();
        }
        return true;
    }

    private boolean needGeneratorDTO(ConfigJson configJson) {
        if (Objects.nonNull(configJson.getProjectConfig()) && Objects.nonNull(configJson.getProjectConfig().getDto())){
            return configJson.getProjectConfig().getDto().isGenerator();
        }
        return true;
    }

    private boolean needGeneratorEntity(ConfigJson configJson) {
        if (Objects.nonNull(configJson.getProjectConfig()) && Objects.nonNull(configJson.getProjectConfig().getEntity())){
            return configJson.getProjectConfig().getEntity().isGenerator();
        }
        return true;
    }

    private boolean needGeneratorManager(ConfigJson configJson) {
        if (Objects.nonNull(configJson.getProjectConfig()) && Objects.nonNull(configJson.getProjectConfig().getManager())){
            return configJson.getProjectConfig().getManager().isGenerator();
        }
        return true;
    }

    private boolean needGeneratorService(ConfigJson configJson) {
        if (Objects.nonNull(configJson.getProjectConfig()) && Objects.nonNull(configJson.getProjectConfig().getService())){
            return configJson.getProjectConfig().getService().isGenerator();
        }
        return true;
    }

    private boolean needGeneratorController(ConfigJson configJson) {
        if (Objects.nonNull(configJson.getProjectConfig()) && Objects.nonNull(configJson.getProjectConfig().getController())){
            return configJson.getProjectConfig().getController().isGenerator();
        }
        return true;
    }


    public static void main(String[] args) {
        new GeneratorMojo().execute();
    }
}

