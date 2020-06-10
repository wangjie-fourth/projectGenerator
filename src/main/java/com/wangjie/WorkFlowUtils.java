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
        String mapperXmlPrefix = null;
        if (Objects.nonNull(configJson.getProjectConfig().getMapperX().getPrefix()) && !configJson.getProjectConfig().getMapperX().getPrefix().trim().equals("")) {
            mapperXmlPrefix = configJson.getProjectConfig().getMapperX().getPrefix();
            mapperXmlPrefix = String.join("/", mapperXmlPrefix.split("\\."));
            mapperXmlPrefix = xmlPrefix + "/" + mapperXmlPrefix + "/";
        }
        return mapperXmlPrefix;
    }

    public static String getMapperJavaPrefix(ConfigJson configJson) {
        String mapperJavaPrefix = null;
        if (Objects.nonNull(configJson.getProjectConfig().getMapperJ().getPrefix()) && !configJson.getProjectConfig().getMapperJ().getPrefix().trim().equals("")) {
            mapperJavaPrefix = configJson.getProjectConfig().getMapperJ().getPrefix();
            mapperJavaPrefix = String.join("/", mapperJavaPrefix.split("\\."));
            mapperJavaPrefix = javaPrefix + "/" + mapperJavaPrefix + "/";
        }
        return mapperJavaPrefix;
    }

    public static String getDtoPrefix(ConfigJson configJson) {
        String dtoPrefix = null;
        if (Objects.nonNull(configJson.getProjectConfig().getDto().getPrefix()) && !configJson.getProjectConfig().getDto().getPrefix().trim().equals("")) {
            dtoPrefix = configJson.getProjectConfig().getEntity().getPrefix();
            dtoPrefix = String.join("/", dtoPrefix.split("\\."));
            dtoPrefix = javaPrefix + "/" + dtoPrefix + "/";
        }
        return dtoPrefix;
    }


    public static String getEntityPrefix(ConfigJson configJson) {
        String entityPrefix = null;
        if (Objects.nonNull(configJson.getProjectConfig().getEntity().getPrefix()) && !configJson.getProjectConfig().getEntity().getPrefix().trim().equals("")) {
            entityPrefix = configJson.getProjectConfig().getEntity().getPrefix();
            entityPrefix = String.join("/", entityPrefix.split("\\."));
            entityPrefix = javaPrefix + "/" + entityPrefix + "/";
        }
        return entityPrefix;
    }


    public static String getManagerPrefix(ConfigJson configJson) {
        String managerPrefix = null;
        if (Objects.nonNull(configJson.getProjectConfig().getManager().getPrefix()) && !configJson.getProjectConfig().getManager().getPrefix().trim().equals("")) {
            managerPrefix = configJson.getProjectConfig().getService().getPrefix();
            managerPrefix = String.join("/", managerPrefix.split("\\."));
            managerPrefix = javaPrefix + "/" + managerPrefix + "/";
        }
        return managerPrefix;
    }


    public static String getServicePrefix(ConfigJson configJson) {
        String servicePrefix = null;
        if (Objects.nonNull(configJson.getProjectConfig().getService().getPrefix()) && !configJson.getProjectConfig().getService().getPrefix().trim().equals("")) {
            servicePrefix = configJson.getProjectConfig().getService().getPrefix();
            servicePrefix = String.join("/", servicePrefix.split("\\."));
            servicePrefix = javaPrefix + "/" + servicePrefix + "/";
        }
        return servicePrefix;
    }

    public static String getControllerPrefix(ConfigJson configJson) {
        String controllerPrefix = null;
        if (Objects.nonNull(configJson.getProjectConfig().getController().getPrefix()) && !configJson.getProjectConfig().getController().getPrefix().trim().equals("")) {
            controllerPrefix = configJson.getProjectConfig().getController().getPrefix();
            controllerPrefix = String.join("/", controllerPrefix.split("\\."));
            controllerPrefix = javaPrefix + "/" + controllerPrefix + "/";
        }
        return controllerPrefix;
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

    public static boolean needGeneratorDTO(ConfigJson configJson) {
        if (Objects.nonNull(configJson.getProjectConfig()) && Objects.nonNull(configJson.getProjectConfig().getDto())) {
            return configJson.getProjectConfig().getDto().isGenerator();
        }
        return true;
    }

    public static boolean needGeneratorEntity(ConfigJson configJson) {
        if (Objects.nonNull(configJson.getProjectConfig()) && Objects.nonNull(configJson.getProjectConfig().getEntity())) {
            return configJson.getProjectConfig().getEntity().isGenerator();
        }
        return true;
    }

    public static boolean needGeneratorManager(ConfigJson configJson) {
        if (Objects.nonNull(configJson.getProjectConfig()) && Objects.nonNull(configJson.getProjectConfig().getManager())) {
            return configJson.getProjectConfig().getManager().isGenerator();
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
