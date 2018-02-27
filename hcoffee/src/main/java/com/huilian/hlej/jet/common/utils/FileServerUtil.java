package com.huilian.hlej.jet.common.utils;

import javax.servlet.ServletContext;
import com.huilian.hlej.jet.common.config.Global;

public class FileServerUtil {
	
	private static final String SLASH = "/";
	private static ServletContext context = SpringContextHolder.getBean(ServletContext.class);

	/**
	 * 获取文件对应服务器全路径
	 */
	public static String getFileServerPath(String filePath) {
		StringBuilder fileServerPath = new StringBuilder(Global.getFileServer(filePath));
		if (filePath.startsWith("N") || filePath.startsWith("/N")) {
			fileServerPath.insert(0,SLASH);
			fileServerPath.insert(0,Global.getAdminPath());
			fileServerPath.insert(0, SLASH.equals(context.getContextPath()) ? "" : context.getContextPath());
		} else {}
		fileServerPath.append(SLASH).append(filePath);
		return fileServerPath.toString();
	}
}
