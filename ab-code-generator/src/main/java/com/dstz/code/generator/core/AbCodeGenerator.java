package com.dstz.code.generator.core;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.dstz.code.generator.AbCodeGeneratorProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;


/**
 * 代码生成器
 *
 * @author wacxhs
 */
@Component
public class AbCodeGenerator {

	private final DataSourceProperties dataSourceProperties;

	private final AbCodeGeneratorProperties codeGeneratorProperties;

	public AbCodeGenerator(DataSourceProperties dataSourceProperties, AbCodeGeneratorProperties codeGeneratorProperties) {
		this.dataSourceProperties = dataSourceProperties;
		this.codeGeneratorProperties = codeGeneratorProperties;
	}


	public void run(AbCodeGeneratorModel codeGeneratorModel) {
		new Runner(codeGeneratorModel).run();
	}


	private final class Runner implements Runnable {

		private final AbCodeGeneratorModel codeGeneratorModel;

		public Runner(AbCodeGeneratorModel codeGeneratorModel) {
			this.codeGeneratorModel = codeGeneratorModel;
		}

		@Override
		public void run() {
			AutoGenerator autoGenerator = new AutoGenerator(dataSourceConfig());
			// 全局配置
			autoGenerator.global(globalConfig());
			// 模板配置
			autoGenerator.template(templateConfig());
			// 包信息
			autoGenerator.packageInfo(packageConfig(codeGeneratorModel.getPackageName()));

			// 策略配置
			StrategyConfig.Builder strategyConfigBuilder = strategyConfig();
			if (ArrayUtil.isNotEmpty(codeGeneratorModel.getIncludeTable())) {
				strategyConfigBuilder.addInclude(codeGeneratorModel.getIncludeTable());
			}
			if (ArrayUtil.isNotEmpty(codeGeneratorModel.getExcludeTable())) {
				strategyConfigBuilder.addExclude(codeGeneratorModel.getExcludeTable());
			}

			//忽略表前缀配置
			strategyConfigBuilder.addTablePrefix("");

			autoGenerator.strategy(strategyConfigBuilder.build());

			// 生成执行
			autoGenerator.execute(new FreemarkerTemplateEngine());

			moveMapperXml(autoGenerator.getConfig().getPathInfo());
		}

		private void moveMapperXml(Map<OutputFile, String> pathInfo) {
			final String searchStr = StrUtil.join(File.separator, "src", "main", "java");
			String mapperXmlPath = pathInfo.get(OutputFile.mapperXml);
			int index = mapperXmlPath.lastIndexOf(searchStr);
			File mapperXmlResourceDir = new File(mapperXmlPath.substring(0, index) + searchStr.replace("java", "resources") + mapperXmlPath.substring(index + searchStr.length()));
			if (!mapperXmlResourceDir.exists()) {
				mapperXmlResourceDir.mkdirs();
			}

			// 迁移文件
			for (File file : new File(mapperXmlPath).listFiles((dir, name) -> name.endsWith(".xml"))) {
				FileUtil.move(file, mapperXmlResourceDir, true);
			}
		}

		GlobalConfig globalConfig() {
			GlobalConfig.Builder builder = new GlobalConfig.Builder();
			// 覆盖已生成文件
			builder.fileOverride();
			// 禁止打开输出目录
			builder.disableOpenDir();
			// 作者名
			builder.author(codeGeneratorModel.getAuthor());
			// 时间策略
			builder.dateType(DateType.ONLY_DATE);
			// 注释日期
			builder.commentDate("yyyy-MM-dd");

			File rootDir;
			if (StrUtil.isNotBlank(codeGeneratorModel.getOutputDir())) {
				rootDir = new File(codeGeneratorModel.getOutputDir());
			} else {
				rootDir = new File(getClass().getResource("/").getFile()).getParentFile();
			}

			// 指定输出目录
			File outputDir = new File(rootDir, StrUtil.join(File.separator, "code-generator", "src", "main", "java"));
			if (!outputDir.exists()) {
				outputDir.mkdirs();
			}
			builder.outputDir(outputDir.getAbsolutePath());
			return builder.build();
		}

		DataSourceConfig dataSourceConfig() {
			DataSourceConfig.Builder builder = new DataSourceConfig.Builder(dataSourceProperties.getUrl(), dataSourceProperties.getUsername(), dataSourceProperties.getPassword());
			// 数据库查询
			//        builder.dbQuery();
			// 数据库 schema(部分数据库适用)
			//        builder.schema();
			// 数据库类型转换器
			//        builder.typeConvert();
			// 数据库关键字处理器
			//        builder.keyWordsHandler();
			return builder.build();
		}

		TemplateConfig templateConfig() {
			TemplateConfig.Builder builder = new TemplateConfig.Builder();
			builder.service("/templates/manager.java");
			builder.serviceImpl("/templates/managerImpl.java");
			return builder.build();
		}

		PackageConfig packageConfig(String packageName) {
			PackageConfig.Builder builder = new PackageConfig.Builder();
			// 上级包名
			builder.parent(packageName);
			// manger
			builder.service("manager");
			builder.serviceImpl("manager.impl");
			builder.xml("mapper");
			return builder.build();
		}

		StrategyConfig.Builder strategyConfig() {
			StrategyConfig.Builder builder = new StrategyConfig.Builder();

			// 开启跳过视图
			builder.enableSkipView();

			// 实体字段填充
			Column[] entityTableFills = new Column[]{
					new Column("create_by_", FieldFill.INSERT),
					new Column("creator_", FieldFill.INSERT),
					new Column("create_org_id_", FieldFill.INSERT),
					new Column("create_time_", FieldFill.INSERT),
					new Column("update_by_", FieldFill.INSERT_UPDATE),
					new Column("updater_", FieldFill.INSERT_UPDATE),
					new Column("update_time_", FieldFill.INSERT_UPDATE),
					new Column("rev_", FieldFill.INSERT)
			};

			builder

					// 实体策略配置
					.entityBuilder()
					// 禁用生成 serialVersionUID
					.disableSerialVersionUID()
					// 开启链式模型
					//                .enableChainModel()
					// 开启 Boolean 类型字段移除 is 前缀
					.enableRemoveIsPrefix()
					// 开启生成实体时生成字段注解
					.enableTableFieldAnnotation()
					// 乐观锁字段名(数据库)
					.versionColumnName(codeGeneratorProperties.getEntity().getVersionColumnName())
					// 乐观锁属性名(实体)
					.versionPropertyName(codeGeneratorProperties.getEntity().getVersionPropertyName())
					// 开启生成字段常量
					//                .enableColumnConstant()
					// 开启 ActiveRecord 模型
					.enableActiveRecord()
					// 设置父类
					.superClass(codeGeneratorProperties.getEntity().getSuperClass())
					// 添加父类公共字段
					//                .addSuperEntityColumns("id_", "create_by_", "create_time_", "update_by_", "updater_", "update_time_", "rev")
					// 添加表字段填充
					.addTableFills(entityTableFills)
					// 全局主键类型
					.idType(IdType.ASSIGN_ID);

			// manager 策略配置
			builder.serviceBuilder()
			       // 设置 manager 接口父类
			       .superServiceClass(codeGeneratorProperties.getManager().getInterfaceClass())
			       // 设置 manager 实现类父类
			       .superServiceImplClass(codeGeneratorProperties.getManager().getInterfaceImplClass())
			       .formatServiceFileName(codeGeneratorProperties.getManager().getInterfaceFileNameFormat())
			       .formatServiceImplFileName(codeGeneratorProperties.getManager().getInterfaceImplFileNameFormat());

			// Mapper 策略配置
			builder.mapperBuilder()
			       // 设置父类
			       .superClass(codeGeneratorProperties.getMapper().getSuperClass())
			       // 开启 @Mapper 注解
			       .enableMapperAnnotation()
			       .enableBaseResultMap();

			// Controller 策略配置
			builder.controllerBuilder()
			       // 开启驼峰转连字符
			       //                .enableHyphenStyle()
			       // 开启生成@RestController 控制器
			       .enableRestStyle()
			       .superClass(codeGeneratorProperties.getController().getSuperClass());

			return builder;
		}
	}
}
