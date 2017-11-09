package com.mybatis.generator.api.dom;

import org.mybatis.generator.api.XmlFormatter;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.config.Context;

/**
 * This class is the default formatter for generated XML. This class will use
 * the built in formatting of the DOM classes directly.
 * @author luoyong
 */
public class DefXmlFormatter implements XmlFormatter {

	/** The Constant lineSeparator. */
	private static String lineSeparator;
	static {
		String ls = System.getProperty("line.separator");
		if (ls == null) {
			ls = "\n";
		}
		lineSeparator = ls;
	}
	protected Context context;

	@Override
	public String getFormattedContent(Document document) {
		String res = document.getFormattedContent();
		String[] arr = res.split(lineSeparator);
		StringBuilder sb = new StringBuilder(res.length() * 2);
		for (int i = 0; i < arr.length; i++) {
			if (i > 0) {
				sb.append(lineSeparator);
				// 每行左边的空格翻倍
				int temp1 = arr[i].length();
				int temp2 = arr[i].replaceAll("^[ ]+", "").length();
				for (int j = 0; j < temp1 - temp2; j++) {
					sb.append(" ");
				}
			}
			sb.append(arr[i]);
		}
		return sb.toString();
	}

	@Override
	public void setContext(Context context) {
		this.context = context;
	}
}
