package com.wangjie;

import com.google.common.base.CaseFormat;
import com.wangjie.mapper.ColumnMapper;
import com.wangjie.mapper.TableMapper;
import com.wangjie.model.config.ConfigJson;
import com.wangjie.model.db.Column;
import com.wangjie.service.dto.AttrDTO;
import com.wangjie.service.dto.JavaDTO;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DdDataUtils {


    public static JavaDTO readDataFromDB(String tableName, ConfigJson configJson) {
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

        String paramName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName);
        String className = Character.toUpperCase(paramName.charAt(0)) + paramName.substring(1);
        JavaDTO javaDTO = JavaDTO.builder()
                .packagePrefix(configJson.getPackagePrefix())
                .className(className)
                .paramName(paramName)
                .tableName(tableName)
                .author(configJson.getAuthor())
                .email(configJson.getEmail())
                .dateTime(new Date())
                .build();


        try (SqlSession session = sqlSessionFactory.openSession()) {
            // 获取备注信息
            TableMapper tableMapper = session.getMapper(TableMapper.class);
            com.wangjie.model.db.Table table = tableMapper.queryTable(tableName);
            javaDTO.setNote(table.getTableComment());

            // 获取字段信息
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
                    case "decimal":
                        attr.setAttrType("BigDecimal");
                        attr.setJdbcType("DECIMAL");
                        break;
                    default:
                        break;
                }
                // 列名转属性名
                String attrName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, column.getColumnName());
                attr.setAttrName(attrName);
                attr.setAttrNote(column.getColumnComment());
                attr.setColumnName(column.getColumnName());

                // 判断是否为主键
                attr.setPk(column.getColumnName().trim().equals("id"));
                attrsMapList.add(attr);
            }
            javaDTO.setAttrs(attrsMapList);

            return javaDTO;
        }
    }

}
