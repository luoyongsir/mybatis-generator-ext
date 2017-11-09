package com.mybatis.generator.internal;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

import java.math.BigDecimal;
import java.sql.Types;

/**
 * 自定义数据库数据类型对应java数据类型
 *
 * @author luoyong
 */
public class DefJavaTypeResolver extends JavaTypeResolverDefaultImpl {

	@Override
	public FullyQualifiedJavaType calculateJavaType(IntrospectedColumn column) {
		FullyQualifiedJavaType answer = null;
		JdbcTypeInformation jdbcTypeInformation = typeMap.get(column.getJdbcType());
		if (jdbcTypeInformation == null) {
			switch (column.getJdbcType()) {
				case Types.DECIMAL:
				case Types.NUMERIC:
					if (column.getScale() > 0 || column.getLength() > 18 || forceBigDecimals) {
						answer = new FullyQualifiedJavaType(BigDecimal.class.getName());
					} else if (column.getLength() > 9) {
						answer = new FullyQualifiedJavaType(Long.class.getName());
					} else if (column.getLength() > 4) {
						answer = new FullyQualifiedJavaType(Integer.class.getName());
					} else {
						answer = new FullyQualifiedJavaType(Short.class.getName());
					}
					break;
				default:
					break;
			}
		} else {
			answer = jdbcTypeInformation.getFullyQualifiedJavaType();
			answer = overrideDefaultType(column, answer);
		}
		return answer;
	}
}
