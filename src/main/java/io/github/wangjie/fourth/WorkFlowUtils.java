package io.github.wangjie.fourth;

import io.github.wangjie.fourth.model.config.ConfigJson;

import java.util.Objects;

/**
 * @ClassName WorkFlowUtils
 * @Description
 * @Author wangjie
 * @Date 2020/6/10 6:43 下午
 * @Email wangjie_fourth@163.com
 **/
public class WorkFlowUtils {


    private static final String xmlPrefix = "/src/main/resources";

    public static String getMapperXmlPrefix(ConfigJson configJson) {
        String mapperXmlPrefix = "/src/main/resources/mapper/";
        if (Objects.nonNull(configJson.getProjectConfig())
                && Objects.nonNull(configJson.getProjectConfig().getMapperX())
                && Objects.nonNull(configJson.getProjectConfig().getMapperX().getPrefix())
                && !configJson.getProjectConfig().getMapperX().getPrefix().trim().equals("")) {
            mapperXmlPrefix = configJson.getProjectConfig().getMapperX().getPrefix();
            mapperXmlPrefix = String.join("/", mapperXmlPrefix.split("\\."));
            mapperXmlPrefix = xmlPrefix + "/" + mapperXmlPrefix + "/";
        }
        return mapperXmlPrefix;
    }


    public static String getConfigPath(String basePrefix, ConfigJson configJson, String specialPath, int i) {
        switch (i) {

            case 4: {
                if (Objects.nonNull(configJson.getProjectConfig())) {
                    String path = String.join("/", configJson.getProjectConfig().getEntity().getPrefix().split("\\."));
                    basePrefix = basePrefix + "/" + path + "/";
                    return basePrefix;
                }
                break;
            }
            case 6: {
                if (Objects.nonNull(configJson.getProjectConfig())) {
                    String path = String.join("/", configJson.getProjectConfig().getMapperJ().getPrefix().split("\\."));
                    basePrefix = basePrefix + "/" + path + "/";
                    return basePrefix;
                }
                break;
            }
        }

        throw new RuntimeException("请配置路径信息");
    }

    public static boolean needGeneratorMapperXml(ConfigJson configJson) {
        if (Objects.nonNull(configJson.getProjectConfig()) && Objects.nonNull(configJson.getProjectConfig().getMapperX())) {
            return configJson.getProjectConfig().getMapperX().isGenerator();
        }
        return true;
    }

    public static boolean needGeneratorMapperJava(ConfigJson configJson) {
        if (Objects.nonNull(configJson.getProjectConfig()) && Objects.nonNull(configJson.getProjectConfig().getMapperJ())) {
            return configJson.getProjectConfig().getMapperJ().isGenerator();
        }
        return true;
    }


    public static boolean needGeneratorEntity(ConfigJson configJson) {
        if (Objects.nonNull(configJson.getProjectConfig()) && Objects.nonNull(configJson.getProjectConfig().getEntity())) {
            return configJson.getProjectConfig().getEntity().isGenerator();
        }
        return true;
    }


}
