package cool.likeu.bulk.generator.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;

import cool.likeu.bulk.generator.utils.Underline2Camel;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class AnnotationPluginConfig extends PluginAdapter {

	private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@Override
	public boolean validate(List<String> list) {
		return true;
	}

	/**
	 * <p>添加 Lombok 扩展</p>
	 * <p>这里的扩展不包含父类实体，但是需要单独处理 Lombok带来的BUG</p>
	 *
	 * @param topLevelClass
	 * @param introspectedTable
	 * @return null
	 */
	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		boolean present = isPresent(introspectedTable);
		String entityFullName = Underline2Camel.underline2Camel(introspectedTable.getFullyQualifiedTable().toString(), true);
		if (present) {
			boolean assignableFrom = entityFullName.getClass().isAssignableFrom((entityFullName + "Key").getClass());
			if (assignableFrom) {
				// 这个注解主要是处理 Lombok 的继承父类实体BUG
				topLevelClass.addImportedType("lombok.EqualsAndHashCode");
				topLevelClass.addAnnotation("@EqualsAndHashCode(callSuper = true)");
			}
		}
		return true;
	}

	/**
	 * 判断索引字段
	 *
	 * @param introspectedTable
	 * @return null
	 */
	private static boolean isPresent(IntrospectedTable introspectedTable) {
		return introspectedTable.getPrimaryKeyColumns().size() > 1;
	}

	/**
	 * 判断索引父类是否存在
	 *
	 * @param name
	 * @return null
	 */
	private static boolean isPresents(String name) {
		try {
			Thread.currentThread().getContextClassLoader().loadClass(name);
			return true;
		}
		catch (ClassNotFoundException e) {
			return false;
		}
	}

	/**
	 * 给 DAO 一个明明白白的注释
	 *
	 * @param interfaze
	 * @param introspectedTable
	 * @return null
	 */
	@Override
	public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
		interfaze.addJavaDocLine("/**");
		interfaze.addJavaDocLine("* 表" + introspectedTable.getFullyQualifiedTable() +
				" -> " + introspectedTable.getRemarks() + " 的基本功能实现");
		interfaze.addJavaDocLine("*");
		try {
			interfaze.addJavaDocLine("* @author " + InetAddress.getLocalHost().getHostName());
		}
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
		interfaze.addJavaDocLine("* @date " + DATE_TIME_FORMATTER.format(Instant.now()));
		interfaze.addJavaDocLine("*/");
		return true;
	}

	/**
	 * 取消 GETTER
	 *
	 * @param method
	 * @param topLevelClass
	 * @param introspectedColumn
	 * @param introspectedTable
	 * @param modelClassType
	 * @return null
	 */
	@Override
	public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
		return false;
	}

	/**
	 * 取消 SETTER
	 *
	 * @param method
	 * @param topLevelClass
	 * @param introspectedColumn
	 * @param introspectedTable
	 * @param modelClassType
	 * @return null
	 */
	@Override
	public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
		return false;
	}

}
