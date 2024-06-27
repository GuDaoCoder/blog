package com.blog.generate;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.blog.common.base.entity.BaseEntity;
import com.blog.common.base.mapper.IBaseMapper;
import com.blog.common.base.service.IBaseCrudService;
import com.blog.common.base.service.impl.BaseCrudServiceImpl;

public class MybatisPlusGenerator {

    private static final String[] tables = new String[]{"t_task"};

    public static void main(String[] args) {
        String url = System.getenv().get("url");
        String username = System.getenv().get("username");
        String password = System.getenv().get("password");
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    // 作者
                    builder.author("zane.zou")
                            // 开启Springdoc
                            .enableSpringdoc()
                            // 指定代码生成的输出目录
                            .outputDir("/Users/zane.zou/Projects/blog/blog-backend/src/main/java")
                            // 禁止自动打开输出目录
                            .disableOpenDir();
                })
                .packageConfig(builder ->
                        // 父包名
                        builder.parent("com.blog.biz")
                                .entity("model.entity")
                                .mapper("mapper")
                                .service("service.crud")
                                .serviceImpl("service.crud.impl"))
                .strategyConfig(builder ->
                        // 设置需要生成的表名
                        builder.addInclude(tables)
                                .addTablePrefix("t_")
                                // 实体策略配置
                                .entityBuilder()
                                // 修改实体类文件名
                                .convertFileName(tableName -> tableName + "Entity")
                                // 使用lombok
                                .enableLombok()
                                // 设置父类
                                .superClass(BaseEntity.class)
                                // 开启 Boolean 类型字段移除 is 前缀
                                .enableRemoveIsPrefix()
                                // 添加父类公共字段
                                .addSuperEntityColumns("createBy", "createTime", "updateBy", "updateTime")
                                // Mapper策略配置
                                .mapperBuilder()
                                // 修改mapper文件名
                                .convertMapperFileName(tableName -> tableName + "Mapper")
                                // 设置父类
                                .superClass(IBaseMapper.class)
                                // Service 策略配置
                                .serviceBuilder()
                                // 设置 Service 接口父类
                                .superServiceClass(IBaseCrudService.class)
                                // 转换 Service 接口文件名称
                                .convertServiceFileName(tableName -> tableName + "CrudService")
                                // 设置 Service 实现类父类
                                .superServiceImplClass(BaseCrudServiceImpl.class)
                                // 转换 Service 实现类文件名称
                                .convertServiceImplFileName(tableName -> tableName + "CrudServiceImpl"))
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
        ;
    }
}
