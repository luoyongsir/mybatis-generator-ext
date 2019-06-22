
# 基于mybatis-generator-core的扩展，生成更合适的代码

使用方法如下：

<br/>
1.添加配置文件generatorConfig.xml

    配置方法请参照 resources 中的 generatorConfig-mysql.xml 和 generatorConfig-oracle.xml

<br/>
2.pom.xml 的 plugins 添加配置

/*注意区分mysql和oracle*/

	<plugins>
        ...

        <!-- generator插件 -->
        <plugin>
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator-maven-plugin</artifactId>
            <version>1.3.7</version>
            <configuration>
                <overwrite>true</overwrite>
                <verbose>true</verbose>
            </configuration>

            <dependencies>
                <!-- <dependency>
                    <groupId>mysql</groupId>
                    <artifactId>mysql-connector-java</artifactId>
                    <version>your.version</version>
                </dependency> -->

                <!-- <dependency>
                    <groupId>com.oracle</groupId>
                    <artifactId>ojdbc6</artifactId>
                    <version>your.version</version>
                </dependency> -->

                <!-- 自定义插件 -->
                <dependency>
                    <groupId>com.mybatis.generator</groupId>
                    <artifactId>mybatis-generator-ext</artifactId>
                    <version>1.0.1-RELEASE</version>
                </dependency>
            </dependencies>
        </plugin>
	</plugins>

<br/>
3.执行 Maven 命令

    mvn mybatis-generator:generate

    mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
