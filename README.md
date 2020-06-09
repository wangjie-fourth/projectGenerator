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
新添加的功能：
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
      "className": "",//指定生成class名称
      "generatorEnum": false // 是否生成枚举字段
    }
  ],
  // 指定生成的类结构
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