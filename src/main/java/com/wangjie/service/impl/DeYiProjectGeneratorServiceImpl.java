package com.wangjie.service.impl;

import com.google.common.base.CaseFormat;
import com.wangjie.GeneratorMojo;
import com.wangjie.mapper.ColumnMapper;
import com.wangjie.mapper.TableMapper;
import com.wangjie.model.config.ConfigJson;
import com.wangjie.model.db.Column;
import com.wangjie.service.ProjectGeneratorService;
import com.wangjie.service.dto.AttrDTO;
import com.wangjie.service.dto.JavaDTO;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @ClassName DeYiProjectGeneratorServiceImpl
 * @Description
 * @Author wangjie
 * @Date 2020/5/18 5:02 下午
 * @Email wangjie_fourth@163.com
 **/
@SuppressFBWarnings({"ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD", "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE"})
public class DeYiProjectGeneratorServiceImpl implements ProjectGeneratorService {

    private final ConfigJson configJson;
    private static SqlSessionFactory sqlSessionFactory;
    private static final VelocityContext context;
    private static String packagePrefix;

    static {
        // 模版引擎
        Properties p = new Properties();
        p.setProperty("resource.loader", "class");
        p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(p);
        context = new VelocityContext();
    }

    public DeYiProjectGeneratorServiceImpl(ConfigJson configJson) {
        this.configJson = configJson;
        {
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
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

            String[] packagePrefixList = configJson.getPackagePrefix().split("\\.");
            packagePrefix = String.join("/", packagePrefixList);
        }
    }

    @Override
    public void generatorManager(JavaDTO javaDTO) throws IOException {
        String managerName = javaDTO.getClassName() + "Manager.java";
        context.put("java", javaDTO);
        String content = generatorModel(context, "template/deyi/Manager.java.vm");
        stringToFile("/src/main/java/" + packagePrefix + "/manager/", managerName, content);
    }

    @Override
    public void generatorDTO(JavaDTO javaDTO) throws IOException {
        String dtoName = javaDTO.getClassName() + "DTO.java";
        context.put("java", javaDTO);
        String content = generatorModel(context, "template/deyi/DTO.java.vm");
        stringToFile("/src/main/java/" + packagePrefix + "/bean/dto/", dtoName, content);
    }


    @Override
    public void generatorMapperJava(JavaDTO javaDTO) throws IOException {
        String mapperName = javaDTO.getClassName() + "Mapper.java";
        // 生成属性
        setAttrs(javaDTO);
        context.put("java", javaDTO);
        String content = generatorModel(context, "template/deyi/Mapper.java.vm");
        stringToFile("/src/main/java/" + packagePrefix + "/mapper/", mapperName, content);
    }

    @Override
    public void generatorMapperXml(JavaDTO javaDTO) throws IOException {
        String mapperName = javaDTO.getClassName() + "Mapper.xml";
        // 生成属性
        setAttrs(javaDTO);
        context.put("java", javaDTO);
        String content = generatorModel(context, "template/deyi/Mapper.xml.vm");
        stringToFile("/src/main/resources/mapper/", mapperName, content);
    }

    @Override
    public void generatorEntity(JavaDTO javaDTO) throws IOException {
        String entityName = javaDTO.getClassName() + ".java";
        // 生成属性
        setAttrs(javaDTO);
        context.put("java", javaDTO);
        String content = generatorModel(context, "template/deyi/Bean.java.vm");
        stringToFile("/src/main/java/" + packagePrefix + "bean/db/", entityName, content);
    }

    private void setAttrs(JavaDTO javaDTO) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            ColumnMapper columnMapper = session.getMapper(ColumnMapper.class);
            List<Column> result = columnMapper.queryColumns(javaDTO.getTableName());
            // 设置attrs
            List<AttrDTO> attrsMapList = new ArrayList<>();
            // 列表模型 转 实体模型
            for (Column column : result) {
                AttrDTO attr = new AttrDTO();
                // 数据类型转换
                switch (column.getDataType()) {
                    case "bigint":
                        attr.setAttrType("Long");
                        attr.setJdbcType("BIGINT");
                        break;
                    case "int":
                    case "tinyint":
                        attr.setAttrType("Integer");
                        attr.setJdbcType("INTEGER");
                        break;
                    case "timestamp":
                    case "date":
                        attr.setAttrType("Date");
                        attr.setJdbcType("DATE");
                        break;
                    case "varchar":
                        attr.setAttrType("String");
                        attr.setJdbcType("VARCHAR");
                        break;
                    default:
                        break;
                }
                // 列名转属性名
                String attrName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, column.getColumnName());
                attr.setAttrName(attrName);
                attr.setAttrNote(column.getColumnComment());
                attr.setColumnName(column.getColumnName());

                // 默认不生成的字段
                if (attrName.equals("id") || attrName.equals("modified") || attrName.equals("created")) {
                    attr.setGenerator(false);
                }

                // 判断是否为主键
                attr.setPK(column.getColumnName().equals("id"));
                attrsMapList.add(attr);
            }
            context.put("attrs", attrsMapList);
        }
    }


    @Override
    public void generatorController(JavaDTO javaDTO) throws IOException {
        String controllerName = javaDTO.getClassName() + "Controller.java";
        context.put("java", javaDTO);
        String content = generatorModel(context, "template/deyi/Controller.java.vm");
        stringToFile("/src/main/java/" + packagePrefix + "/web/controller/", controllerName, content);
    }

    private void stringToFile(String filePrefixDir, String fileName, String content) throws IOException {

        System.out.println(GeneratorMojo.projectDirector + filePrefixDir + fileName);

        Path pathToFile = Paths.get(GeneratorMojo.projectDirector + filePrefixDir + fileName);
        Files.createDirectories(pathToFile.getParent());
        Files.createFile(pathToFile);
        PrintWriter out = new PrintWriter(GeneratorMojo.projectDirector + filePrefixDir + fileName, "UTF-8T");
        out.println(content);
        out.close();

    }

    @Override
    public void generatorService(JavaDTO javaDTO) throws IOException {
        String serviceName = javaDTO.getClassName() + "Service.java";
        context.put("java", javaDTO);
        String content = generatorModel(context, "template/deyi/Service.java.vm");
        stringToFile("/src/main/java/" + packagePrefix + "/service/", serviceName, content);
    }


    private String generatorModel(VelocityContext context, String templateVm) throws IOException {
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
