# mybatis-generator-ext
基于mybatis-generator-core的扩展，生成更合适的代码

generatorConfig.xml 的配置

<!-- <?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN" "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration> -->
	<!-- 数据库驱动包位置 配置在pom.xml文件中 -->
	<context id="mysql" targetRuntime="MyBatis3" defaultModelType="flat">
		<!-- 自定义xml格式 -->
		<property name="xmlFormatter" value="com.mybatis.generator.api.dom.DefXmlFormatter" />
		<!-- 自定义序列化id -->
		<plugin type="com.mybatis.generator.plugins.DefSerializablePlugin"></plugin>
		<plugin type="org.mybatis.generator.plugins.ToStringPlugin"></plugin>

		<!-- 自定义实体类注释 -->
		<commentGenerator type="com.mybatis.generator.internal.DefCommentGenerator">
			<!-- 是否去除自动生成的注释 true：是 ： false:否 -->
			<property name="suppressAllComments" value="true" />
		</commentGenerator>

		<!-- 数据库链接URL、用户名、密码 -->
		<jdbcConnection driverClass="com.mysql.jdbc.Driver"
						connectionURL="jdbc:mysql://xxx.xxx.xxx.xxx:3306/dbName" userId="userId" password="password" />

		<!-- false，把JDBC DECIMAL 和 NUMERIC 类型解析为 Short,Integer,Long -->
		<!--  true，把JDBC DECIMAL 和 NUMERIC 类型解析为 java.math.BigDecimal -->
		<javaTypeResolver>
			<property name="forceBigDecimals" value="false" />
		</javaTypeResolver>

		<!-- 生成模型的包名和位置 -->
		<javaModelGenerator targetPackage="com.mybatis.entity" targetProject="MBGTestProject/src/main/java">
			<property name="enableSubPackages" value="true" />
			<!-- 从数据库返回的值被清理前后的空格  -->
			<property name="trimStrings" value="false" />
		</javaModelGenerator>
		<!-- 生成的映射文件包名和位置 -->
		<sqlMapGenerator targetPackage="com.mybatis.mapper.mysql" targetProject="./src/main/java">
			<property name="enableSubPackages" value="true" />
		</sqlMapGenerator>
		<!-- 生成Mapper的包名和位置 -->
		<javaClientGenerator type="XMLMAPPER" targetPackage="com.mybatis.mapper" targetProject="./src/main/java">
			<property name="enableSubPackages" value="true" />
		</javaClientGenerator>

		<!-- 要生成那些表(更改tableName和domainObjectName就可以) -->
		<!-- <table tableName="tableName" domainObjectName="domainObjectName" enableCountByExample="false"
			   enableDeleteByExample="false" enableSelectByExample="false" enableUpdateByExample="fasle" selectByExampleQueryId="false">
			<generatedKey column="ID" sqlStatement="JDBC"/>
		</table> -->

		<table tableName="tableNamePre%" enableCountByExample="false" enableDeleteByExample="false"
			enableSelectByExample="false" enableUpdateByExample="fasle" selectByExampleQueryId="false">
			<generatedKey column="ID" sqlStatement="JDBC"/>
		</table>

	</context>
<!-- </generatorConfiguration> -->


pom.xml 中的配置

	<plugins>
		...

		<!-- generator插件 -->
		<plugin>
			<groupId>org.mybatis.generator</groupId>
			<artifactId>mybatis-generator-maven-plugin</artifactId>
			<version>1.3.5</version>
			<configuration>
				<overwrite>true</overwrite>
				<verbose>true</verbose>
			</configuration>

			<dependencies>
				<dependency>
					<groupId>mysql</groupId>
					<artifactId>mysql-connector-java</artifactId>
					<version>5.1.42</version>
				</dependency>

				<!-- 自定义插件 -->
				<dependency>
					<groupId>com.mybatis.generator</groupId>
                    <artifactId>mybatis-generator-ext</artifactId>
                    <version>1.0.0-RELEASE</version>
				</dependency>
			</dependencies>
		</plugin>
	</plugins>


maven 执行命令

mvn mybatis-generator:generate
