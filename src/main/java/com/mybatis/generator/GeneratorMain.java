package com.mybatis.generator;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 运行main方法生成对应表的代码
 * <p>
 * 代码生成后在output目录
 *
 * @author luoyong
 * @date 2021/11/28 12:42
 */
public class GeneratorMain {

	private static final Logger LOG = Logger.getLogger(GeneratorMain.class.getName());
	private static final String TARGET_PACKAGE = "targetPackage";
	private static final String TARGET_PROJECT = "targetProject";

	public static void main(String[] args) {
		try (InputStream is = getDefaultClassLoader().getResourceAsStream("generatorConfig-mysql.xml")) {
			List<String> warnings = new ArrayList<>();
			ConfigurationParser cp = new ConfigurationParser(warnings);
			Configuration config = cp.parseConfiguration(is);
			setTarget(config);
			DefaultShellCallback callback = new DefaultShellCallback(true);
			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
			myBatisGenerator.generate(null);
		} catch (Exception e) {
			LOG.log(Level.WARNING, "IP 数据库加载出错：", e);
		}
	}

	private static void setTarget(Configuration config) throws IOException {
		Context con = config.getContexts().get(0);
		String targetPackage = con.getProperty(TARGET_PACKAGE);
		String targetProject = con.getProperty(TARGET_PROJECT);
		if (targetProject != null && targetProject.contains(":\\")) {
			Path dirPath = Paths.get(targetProject);
			Files.createDirectories(dirPath);
		}
		for (Context context : config.getContexts()) {
			SqlMapGeneratorConfiguration sql = context.getSqlMapGeneratorConfiguration();
			sql.setTargetPackage(sql.getTargetPackage().replace("${" + TARGET_PACKAGE + "}", targetPackage));
			sql.setTargetProject(sql.getTargetProject().replace("${" + TARGET_PROJECT + "}", targetProject));

			JavaModelGeneratorConfiguration javaModel = context.getJavaModelGeneratorConfiguration();
			javaModel.setTargetPackage(
					javaModel.getTargetPackage().replace("${" + TARGET_PACKAGE + "}", targetPackage));
			javaModel.setTargetProject(
					javaModel.getTargetProject().replace("${" + TARGET_PROJECT + "}", targetProject));

			JavaClientGeneratorConfiguration javaClient = context.getJavaClientGeneratorConfiguration();
			javaClient.setTargetPackage(
					javaClient.getTargetPackage().replace("${" + TARGET_PACKAGE + "}", targetPackage));
			javaClient.setTargetProject(
					javaClient.getTargetProject().replace("${" + TARGET_PROJECT + "}", targetProject));
		}
	}

	/**
	 * copy from org.springframework.util.ClassUtils
	 */
	private static ClassLoader getDefaultClassLoader() {
		ClassLoader cl;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		} catch (Exception ex) {
			throw new RuntimeException(" Thread.currentThread() 获取 ClassLoader 出错：", ex);
		}
		if (cl == null) {
			cl = GeneratorMain.class.getClassLoader();
			if (cl == null) {
				try {
					cl = ClassLoader.getSystemClassLoader();
				} catch (Exception ex) {
					throw new RuntimeException(" ClassLoader.getSystemClassLoader() 获取 ClassLoader 出错：", ex);
				}
			}
		}
		return cl;
	}
}
