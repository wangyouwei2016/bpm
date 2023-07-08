package com.dstz.code.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 生成器配置
 *
 * @author wacxhs
 */
@ConfigurationProperties(prefix = "ab.code-generator")
public class AbCodeGeneratorProperties {

	/**
	 * 实体配置
	 */
	private final EntityProperties entity = new EntityProperties();
	/**
	 * Mapper 配置
	 */
	private final MapperProperties mapper = new MapperProperties();
	/**
	 * Manager 配置
	 */
	private final ManagerProperties manager = new ManagerProperties();
	/**
	 * Controller 配置
	 */
	private final ControllerProperties controller = new ControllerProperties();
	/**
	 * 启用GUI
	 */
	private Boolean enableGui = Boolean.FALSE;

	public Boolean getEnableGui() {
		return enableGui;
	}

	public void setEnableGui(Boolean enableGui) {
		this.enableGui = enableGui;
	}

	public EntityProperties getEntity() {
		return entity;
	}

	public MapperProperties getMapper() {
		return mapper;
	}

	public ManagerProperties getManager() {
		return manager;
	}

	public ControllerProperties getController() {
		return controller;
	}

	/**
	 * Entity 配置
	 */
	public static final class EntityProperties {

		/**
		 * 基类
		 */
		private String superClass = "com.dstz.base.entity.AbModel";

		/**
		 * 乐观锁列名（数据库）
		 */
		private String versionColumnName = "rev_";

		/**
		 * 乐观锁属性名（实体）
		 */
		private String versionPropertyName = "rev";

		/**
		 * 主键类型
		 */
		private IdType idType = IdType.ASSIGN_ID;

		public String getSuperClass() {
			return superClass;
		}

		public void setSuperClass(String superClass) {
			this.superClass = superClass;
		}

		public String getVersionColumnName() {
			return versionColumnName;
		}

		public void setVersionColumnName(String versionColumnName) {
			this.versionColumnName = versionColumnName;
		}

		public String getVersionPropertyName() {
			return versionPropertyName;
		}

		public void setVersionPropertyName(String versionPropertyName) {
			this.versionPropertyName = versionPropertyName;
		}

		public IdType getIdType() {
			return idType;
		}

		public void setIdType(IdType idType) {
			this.idType = idType;
		}
	}

	/**
	 * Mapper 配置
	 */
	public static final class MapperProperties {

		/**
		 * 基类
		 */
		private String superClass = "com.dstz.base.mapper.AbBaseMapper";

		public String getSuperClass() {
			return superClass;
		}

		public void setSuperClass(String superClass) {
			this.superClass = superClass;
		}
	}

	/**
	 * Manager 配置
	 */
	public static final class ManagerProperties {

		/**
		 * 通用接口类
		 */
		private String interfaceClass = "com.dstz.base.manager.AbBaseManager";

		/**
		 * 通用接口实现类
		 */
		private String interfaceImplClass = "com.dstz.base.manager.impl.AbBaseManagerImpl";

		/**
		 * 接口文件名格式化，%s：实体名
		 */
		private String interfaceFileNameFormat = "%sManager";

		/**
		 * 接口实现文件名格式化，%s：实体名
		 */
		private String interfaceImplFileNameFormat = "%sManagerImpl";

		public String getInterfaceClass() {
			return interfaceClass;
		}

		public void setInterfaceClass(String interfaceClass) {
			this.interfaceClass = interfaceClass;
		}

		public String getInterfaceImplClass() {
			return interfaceImplClass;
		}

		public void setInterfaceImplClass(String interfaceImplClass) {
			this.interfaceImplClass = interfaceImplClass;
		}

		public String getInterfaceFileNameFormat() {
			return interfaceFileNameFormat;
		}

		public void setInterfaceFileNameFormat(String interfaceFileNameFormat) {
			this.interfaceFileNameFormat = interfaceFileNameFormat;
		}

		public String getInterfaceImplFileNameFormat() {
			return interfaceImplFileNameFormat;
		}

		public void setInterfaceImplFileNameFormat(String interfaceImplFileNameFormat) {
			this.interfaceImplFileNameFormat = interfaceImplFileNameFormat;
		}
	}

	/**
	 * Controller 配置
	 */
	public static final class ControllerProperties {

		/**
		 * 基类
		 */
		private String superClass = "com.dstz.base.web.controller.AbCrudController";

		/**
		 * 开启 @RestController 注解
		 */
		private Boolean enableRestStyle = Boolean.TRUE;

		public String getSuperClass() {
			return superClass;
		}

		public void setSuperClass(String superClass) {
			this.superClass = superClass;
		}

		public Boolean getEnableRestStyle() {
			return enableRestStyle;
		}

		public void setEnableRestStyle(Boolean enableRestStyle) {
			this.enableRestStyle = enableRestStyle;
		}
	}
}
