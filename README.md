一个用于生成项目模板的`maven`插件。

---
## 使用步骤
1、在`pom.xml`添加插件
```xml
<plugin>
    <groupId>com.wangjie</groupId>
    <artifactId>projectGenerator-maven-plugin</artifactId>
    <version>1.0-SNAPSHOT</version>
</plugin>
```

2、在根目录下，新建`projectGenerator.json`
```json
{
  "author": "wangjie",
  "email": "wangjie@dycjr.com",
  "packagePrefix": "com.wangjie",

  "jdbc": {
    "url": "jdbc:mysql://localhost:3306/generator_test?useUnicode=true&characterEncoding=utf-8&useSSL=false",
    "username": "root",
    "password": "root",
    "driverClassName": "com.mysql.cj.jdbc.Driver"
  },
  "tables": [
    {
      "tableName": "table_info",
      "className": "",
      "generatorEnum": false
    }
  ]
}
```

3、执行命令，生成模板
```shell script
mvn projectGenerator:generator
```

---
## 功能描述
1、基本功能
```json
{
  "author": "wangjie",
  "email": "wangjie@dycjr.com",
  "packagePrefix": "com.wangjie",

  "jdbc": {
    "url": "jdbc:mysql://localhost:3306/generator_test?useUnicode=true&characterEncoding=utf-8&useSSL=false",
    "username": "root",
    "password": "root",
    "driverClassName": "com.mysql.cj.jdbc.Driver"
  },
  "tables": [
    {
      "tableName": "table_info"
    }
  ]
}
```
按照表名生成Mapper.xml、Mapper.java、Manager、Service、Controller层代码模板

2、按需生成模板
```json
{ 
  "projectConfig":{
    "controller": {
      "generator": true,
      "prefix": ""
    },
    "service": {
       "generator": true,
       "prefix": ""
    },
    "manager": {
       "generator": true,
       "prefix": ""
    },
    "mapperJ": {
       "generator": true,
       "prefix": ""
    },
    "mapperX": {
       "generator": true,
       "prefix": ""
    },
    "entity": {
       "generator": true,
       "prefix": ""
    },
    "dto": {
        "generator": true,
        "prefix": ""
    }
  }
}
```
- 按需生成模板
- 可以指定模板所在的具体包名，会覆盖之前的`packagePrefix`

3、指定表名所对应的`Class`名称
```json
{
  "tables": [
    {
      "tableName": "table_info",
      "className": ""
    }
  ]
}
```

---
## 想添加的功能
1、`packagePrefix`配置似乎有点无用，不如删除？
2、假设在一个表中添加字段，如何更新Mapper.xml文件？
- 只在where条件、resultMap中添加字段，
