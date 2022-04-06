package com.mybatis.generator.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.StringUtility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 自定义接口、实体类、属性的注释
 *
 * @author luoyong
 */
public class DefCommentPlugin extends PluginAdapter {

    private String author;
    private String dateFormat;

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        super.initialized(introspectedTable);
        author = getProperties().getProperty("author");
        dateFormat = getProperties().getProperty("dateFormat");
        if (!StringUtility.stringHasValue(dateFormat)) {
            dateFormat = "yyyy/MM/dd HH:mm:ss";
        }
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                       IntrospectedTable introspectedTable, Plugin.ModelClassType modelClassType) {
        // 属性注释
        String remarks = introspectedColumn.getRemarks();
        if (StringUtility.stringHasValue(remarks)) {
            String[] remarkLines = remarks.split(System.getProperty("line.separator"));
            for (String remarkLine : remarkLines) {
                field.addJavaDocLine("/** " + remarkLine + " */");
            }
        }

        // 属性swagger
        field.addAnnotation("@Schema(name = \"" + (StringUtility.stringHasValue(remarks) ? remarks : field.getName()) + "\")");
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return modelClassGenerated(topLevelClass, introspectedTable, true);
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return modelClassGenerated(topLevelClass, introspectedTable, false);
    }

    /**
     * 使用lombok
     */
    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    /**
     * 使用lombok
     */
    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        String table = introspectedTable.getFullyQualifiedTable().toString();
        // 接口注释
        interfaze.addJavaDocLine("/**");
        interfaze.addJavaDocLine(" * This interface corresponds to the database table " + table);
        interfaze.addJavaDocLine(" * @author " + author);
        interfaze.addJavaDocLine(" * @date " + getDateString());
        interfaze.addJavaDocLine(" */");

        return true;
    }

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        addJavaDoc(method, "根据主键删除数据库记录", "");
        return super.clientDeleteByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable);
    }

    @Override
    public boolean clientInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        addJavaDoc(method, "新增记录", "");
        return super.clientInsertMethodGenerated(method, interfaze, introspectedTable);
    }

    @Override
    public boolean clientInsertSelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        addJavaDoc(method, "新增记录", "");
        return super.clientInsertSelectiveMethodGenerated(method, interfaze, introspectedTable);
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        addJavaDoc(method, "根据主键查询记录", "");
        return super.clientSelectByPrimaryKeyMethodGenerated(method, interfaze, introspectedTable);
    }

    @Override
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        addJavaDoc(method, "根据主键修改数据库记录", "");
        return super.clientUpdateByPrimaryKeySelectiveMethodGenerated(method, interfaze, introspectedTable);
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        addJavaDoc(method, "根据主键修改数据库记录", "");
        return super.clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(method, interfaze, introspectedTable);
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        addJavaDoc(method, "根据主键修改数据库记录", "");
        return super.clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(method, interfaze, introspectedTable);
    }

    private void addJavaDoc(Method method, String desc, String res) {
        method.addJavaDocLine("/**");
        method.addJavaDocLine(" * " + desc);
        method.addJavaDocLine(" * @author " + author);
        method.addJavaDocLine(" * @date " + getDateString());
        method.addJavaDocLine(" * @param " + method.getParameters().get(0).getName());
        method.addJavaDocLine(" * @return " + res);
        method.addJavaDocLine(" */");
    }

    private String getDateString() {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(new Date());
    }

    private boolean modelClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, boolean needClassAnnotations) {
        String table = introspectedTable.getFullyQualifiedTable().toString();
        // 实体类注释
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * This class corresponds to the database table " + table);

        String tableRemarks = introspectedTable.getRemarks();
        if (StringUtility.stringHasValue(tableRemarks)) {
            topLevelClass.addJavaDocLine(" * Database Table Remarks:");
            String[] remarkLines = tableRemarks.split(System.getProperty("line.separator"));
            for (String remarkLine : remarkLines) {
                topLevelClass.addJavaDocLine(" *   " + remarkLine);
            }
            topLevelClass.addJavaDocLine(" *");
        }
        topLevelClass.addJavaDocLine(" * @author " + author);
        topLevelClass.addJavaDocLine(" * @date " + getDateString());
        topLevelClass.addJavaDocLine(" */");

        if (needClassAnnotations) {
            addModelClassAnnotations(topLevelClass, StringUtility.stringHasValue(tableRemarks) ? tableRemarks : table);
        }
        return true;
    }

    private void addModelClassAnnotations(TopLevelClass topLevelClass, String tableRemarks) {
        // 添加实体的import
        topLevelClass.addImportedType("io.swagger.v3.oas.annotations.media.Schema");
        topLevelClass.addImportedType("lombok.Getter");
        topLevelClass.addImportedType("lombok.Setter");
        topLevelClass.addImportedType("lombok.ToString");
        // 添加实体的注解
        topLevelClass.addAnnotation("@Getter");
        topLevelClass.addAnnotation("@Setter");
        topLevelClass.addAnnotation("@ToString");
        topLevelClass.addAnnotation("@Schema(name = \"" + tableRemarks + "\")");
    }

}
