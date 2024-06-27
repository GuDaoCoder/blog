package com.blog.generate;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.blog.common.base.entity.BaseEntity;
import com.blog.common.base.mapper.IBaseMapper;
import com.blog.common.base.service.IBaseCrudService;
import com.blog.common.base.service.impl.BaseCrudServiceImpl;

import java.util.Collections;

public class MybatisPlusGenerator {

	private static final String[] tables = new String[] { "t_task" };

	public static void main(String[] args) {
		String url = System.getenv().get("url");
		String username = System.getenv().get("username");
		String password = System.getenv().get("password");
		FastAutoGenerator.create(url, username, password).globalConfig(builder -> {
			// 作者
			builder.author("zane.zou")
				// 开启Springdoc
				.enableSpringdoc()
				// 指定代码生成的输出目录
				.outputDir("/Users/zane.zou/Projects/blog/blog-backend/src/main/java")
				// 禁止自动打开输出目录
				.disableOpenDir();
		})
			.packageConfig(builder -> builder.parent("com.blog.biz")
				.controller("controller.admin")
				.entity("model.entity")
				.mapper("mapper")
				.service("service.crud")
				.serviceImpl("service.crud.impl")
				// 不输出mapper.xml
				.pathInfo(Collections.singletonMap(OutputFile.xml, null)))
			.strategyConfig(builder ->
			// 设置需要生成的表名
			builder.addInclude(tables)
				.addTablePrefix("t_")
				// Controller 策略配置
				.controllerBuilder()
				// 格式化文件名称
				.formatFileName("%sAdminController")
				// 开启生成@RestController 控制器
				.enableRestStyle()
				// 实体策略配置
				.entityBuilder()
				// 格式化文件名称
				.formatFileName("%sEntity")
				// 使用lombok
				.enableLombok()
				// 开启链式模型
				.enableChainModel()
				// 设置父类
				.superClass(BaseEntity.class)
				// 开启 Boolean 类型字段移除 is 前缀
				.enableRemoveIsPrefix()
				// 添加忽略字段
				.addIgnoreColumns("create_by", "create_time", "update_by", "update_time")
				// Mapper策略配置
				.mapperBuilder()
				// 格式化文件名称
				.formatMapperFileName("%sMapper")
				// 设置父类
				.superClass(IBaseMapper.class)
				// Service 策略配置
				.serviceBuilder()
				// 设置 Service 接口父类
				.superServiceClass(IBaseCrudService.class)
				// 格式化文件名称
				.formatServiceFileName("%sCrudService")
				// 设置 Service 实现类父类
				.superServiceImplClass(BaseCrudServiceImpl.class)
				// 格式化文件名称
				.formatServiceImplFileName("%sCrudServiceImpl"))
			.injectionConfig(builder -> builder.customFile(customFile -> customFile.packageName("model.request")
				.fileName("Request.java")
				.formatNameFunction(tableInfo -> {
					String entityName = tableInfo.getEntityName();
					return entityName.replace("Entity", "");
				})
				.templatePath("/templates/entityRequest.java.ftl"))
				.customFile(customFile -> customFile.packageName("model.response")
					.fileName("Response.java")
					.formatNameFunction(tableInfo -> {
						String entityName = tableInfo.getEntityName();
						return entityName.replace("Entity", "");
					})
					.templatePath("/templates/entityResponse.java.ftl")))
			// 使用Freemarker引擎模板，默认的是Velocity引擎模板
			.templateEngine(new FreemarkerTemplateEngine())
			.execute();
		;
	}

}
