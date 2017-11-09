package com.mybatis.generator.plugins;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 自定义实体类serialVersionUID的值
 * @author luoyong
 */
public class DefSerializablePlugin extends PluginAdapter {

	private FullyQualifiedJavaType serializable;
	private FullyQualifiedJavaType gwtSerializable;
	private boolean addGWTInterface;
	private boolean suppressJavaInterface;

	public DefSerializablePlugin() {
		super();
		serializable = new FullyQualifiedJavaType("java.io.Serializable");
		gwtSerializable = new FullyQualifiedJavaType("com.google.gwt.user.client.rpc.IsSerializable");
	}

	@Override
	public boolean validate(List<String> warnings) {
		// this plugin is always valid
		return true;
	}

	@Override
	public void setProperties(Properties properties) {
		super.setProperties(properties);
		addGWTInterface = Boolean.valueOf(properties.getProperty("addGWTInterface"));
		suppressJavaInterface = Boolean.valueOf(properties.getProperty("suppressJavaInterface"));
	}

	@Override
	public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		makeSerializable(topLevelClass, introspectedTable);
		return true;
	}

	@Override
	public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		makeSerializable(topLevelClass, introspectedTable);
		return true;
	}

	@Override
	public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		makeSerializable(topLevelClass, introspectedTable);
		return true;
	}

	protected void makeSerializable(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		if (addGWTInterface) {
			topLevelClass.addImportedType(gwtSerializable);
			topLevelClass.addSuperInterface(gwtSerializable);
		}
		if (!suppressJavaInterface) {
			topLevelClass.addImportedType(serializable);
			topLevelClass.addSuperInterface(serializable);
			Field field = new Field();
			field.setFinal(true);
			field.setInitializationString(randomUid());
			field.setName("serialVersionUID");
			field.setStatic(true);
			field.setType(new FullyQualifiedJavaType("long"));
			field.setVisibility(JavaVisibility.PRIVATE);
			context.getCommentGenerator().addFieldComment(field, introspectedTable);
			topLevelClass.addField(field);
		}
	}

	private String randomUid() {
		StringBuilder bud = new StringBuilder(20);
		ThreadLocalRandom random = ThreadLocalRandom.current();
		for (int i = 0; i < 18; i++) {
			if (i == 0) {
				if (nextInt(0, 100, random) % 2 == 1) {
					bud.append(nextInt(-9, -1, random));
				} else {
					bud.append(nextInt(1, 9, random));
				}
			} else {
				bud.append(nextInt(0, 9, random));
			}
		}
		bud.append("L");
		return bud.toString();
	}

	private int nextInt(final int min, final int max, ThreadLocalRandom random) {
		if (min == max) {
			return min;
		}
		return min + random.nextInt(max - min);
	}
}
