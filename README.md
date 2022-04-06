
# 基于mybatis-generator-core 1.4.0 版本的扩展，生成更合适的代码

使用方法如下：

<br/>
1. 修改配置文件 generatorConfig-mysql.xml 或 generatorConfig-oracle.xml 中的配置

```
    <property name="targetPackage" value="com.mybatis.output"/>
    <property name="targetProject" value="./src/main/java"/>
    <plugin type="com.mybatis.generator.plugins.DefCommentPlugin" >
        <property name="author" value="luoyong" />
        <property name="dateFormat" value="yyyy/MM/dd HH:mm" />
    </plugin>

    <jdbcConnection driverClass="com.mysql.cj.jdbc.Driver"
                    connectionURL="jdbc:mysql://xxx.xxx.xxx.xxx:3306/dbName" userId="userId" password="password" >
        <property name="useInformationSchema" value="true" />
    </jdbcConnection>
    
    <table tableName="tableNamePre%" enableCountByExample="false" enableDeleteByExample="false"
           enableSelectByExample="false" enableUpdateByExample="fasle" selectByExampleQueryId="false">
        <generatedKey column="ID" sqlStatement="JDBC"/>
    </table>

```

<br/>
2. 运行 GeneratorMain 里的 main 方法

    生成代码后会输出在 targetProject + targetPackage 目录
    生成的代码包含 lombok 注解和 swagger 注解
