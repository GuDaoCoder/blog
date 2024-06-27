package com.blog.generate;

import com.blog.biz.model.request.AdminUserRequest;

import java.lang.reflect.Field;
import java.util.List;

public class GenerateTypescriptInterface {

    private static final List<String> ingoredFileds = List.of("serialVersionUID", "createTime", "updateTime");

    public static void main(String[] args) {
        Class<?> clazz = AdminUserRequest.class;
        Field[] fields = clazz.getDeclaredFields();
        System.out.println("export interface " + clazz.getSimpleName() + " {");
        for (Field field : fields) {
            if (!ingoredFileds.contains(field.getName())) {
                System.out
                    .println("    " + field.getName() + ": " + convertType(field.getType().getSimpleName()) + ",");
            }
        }
        System.out.println("}");
    }

    private static String convertType(String type) {
        switch (type) {
            case "Integer", "Long":
                return "number";
            case "Boolean":
                return "boolean";
            default:
                return "string";
        }
    }

}
