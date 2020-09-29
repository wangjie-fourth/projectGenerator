package com.wangjie;

import com.wangjie.model.config.ConfigJson;

import java.util.Objects;

/**
 * @ClassName WorkFlowUtils
 * @Description
 * @Author wangjie
 * @Date 2020/6/10 6:43 下午
 * @Email wangjie_fourth@163.com
 **/
public class WorkFlowUtils {

    private static final String javaPrefix = "/src/main/java";

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
            case 1: {
                if (Objects.nonNull(configJson.getProjectConfig())
                        && Objects.nonNull(configJson.getProjectConfig().getController())
                        && Objects.nonNull(configJson.getProjectConfig().getController().getPrefix())) {
                    String path = String.join("/", configJson.getProjectConfig().getController().getPrefix().split("\\."));
                    basePrefix = basePrefix + "/" + path + "/";
                    return basePrefix;
                }
                break;
            }
            case 2: {
                if (Objects.nonNull(configJson.getProjectConfig())
                        && Objects.nonNull(configJson.getProjectConfig().getController())
                        && Objects.nonNull(configJson.getProjectConfig().getController().getPrefix())) {
                    String path = String.join("/", configJson.getProjectConfig().getService().getPrefix().split("\\."));
                    basePrefix = basePrefix + "/" + path + "/";
                    return basePrefix;
                }
                break;
            }
            case 4: {
                if (Objects.nonNull(configJson.getProjectConfig())
                        && Objects.nonNull(configJson.getProjectConfig().getController())
                        && Objects.nonNull(configJson.getProjectConfig().getController().getPrefix())) {
                    String path = String.join("/", configJson.getProjectConfig().getEntity().getPrefix().split("\\."));
                    basePrefix = basePrefix + "/" + path + "/";
                    return basePrefix;
                }
                break;
            }
            case 6: {
                if (Objects.nonNull(configJson.getProjectConfig())
                        && Objects.nonNull(configJson.getProjectConfig().getController())
                        && Objects.nonNull(configJson.getProjectConfig().getController().getPrefix())) {
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

    public static boolean needGeneratorService(ConfigJson configJson) {
        if (Objects.nonNull(configJson.getProjectConfig()) && Objects.nonNull(configJson.getProjectConfig().getService())) {
            return configJson.getProjectConfig().getService().isGenerator();
        }
        return true;
    }

    public static boolean needGeneratorController(ConfigJson configJson) {
        if (Objects.nonNull(configJson.getProjectConfig()) && Objects.nonNull(configJson.getProjectConfig().getController())) {
            return configJson.getProjectConfig().getController().isGenerator();
        }
        return true;
    }
}
