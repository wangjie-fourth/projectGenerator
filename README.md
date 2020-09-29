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
---
## 功能描述
1、平常情况
通常新业务过来的时候，我们需要新建表，然后在代码中建议对应的模型类、以及相关结构代码。比如：Entity、Service、Controller等等
所以配置信息如下：
```json
{
  "author": "wangjie",
  "email": "wangjie@dycjr.com",
  "packagePrefix": "com.wangjie",
  "xmlPrefix": "mapper/",   

  "jdbc": {
    "url": "jdbc:mysql://localhost:3306/generator_test?useUnicode=true&characterEncoding=utf-8&useSSL=false",
    "username": "root",
    "password": "root",
    "driverClassName": "com.mysql.cj.jdbc.Driver"
  },

  "tableConfigs": [
    {
      "tableName": "table_info"
    }
  ]
}
```

2、特殊情况
有时候，我们不需要生成一整套结构代码，可能我们仅需要生成Entity。这时候配置如下：
```json
{
  "projectConfig": {
    "controller": {
      "generator": false,
      "prefix": "cn.victorplus.finance.product.controller"
    },
    "service": {
      "generator": false,
      "prefix": "cn.victorplus.finance.product.service.impl.product"
    },
    "manager": {
      "generator": false,
      "prefix": "cn.victorplus.finance.product.manager"
    },
    "mapperJ": {
      "generator": false,
      "prefix": "cn.victorplus.finance.product.mapper.product"
    },
    "mapperX": {
      "generator": false,
      "prefix": "/mapper/"
    },
    "entity": {
      "generator": false,
      "prefix": "cn.victorplus.finance.product.bean.db.product"
    },
    "dto": {
      "generator": true,
      "prefix": "com.wangjie.test"
    }
  }
}
```
这里含有配置，并且这里的配置信息优先级最高。

# 相关约定
- 表名以下划线风格命名


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
  "tableConfigs": [
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
  "tableConfigs": [
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

---
1、不要过度设计，刚开始只要做出来基础功能
我在做的时候，把DTO、Manager类都生成出来，但其实这些并不是需要经常生成出来，这样我每次改错时，还要再改他们；
配置类设计的时候，我又考虑如何缩减配置，这也让后来修改增加难度。
这些东西完全可以等基础功能做好之后，再去弥补


