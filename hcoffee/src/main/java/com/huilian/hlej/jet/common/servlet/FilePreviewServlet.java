package com.huilian.hlej.jet.common.servlet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.util.UriUtils;
import com.huilian.hlej.hlej.file.service.FileHandler;

/**
 * 查看Fastdfs上传的图片
 *
 * @author hlej
 * @version 2014-06-25
 */
public class FilePreviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private final String PREVIEW = "preview";
	@Autowired
	FileHandler fileHandler;

	@Override
	public void init(ServletConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
		         config.getServletContext());
	}
	public void fileOutputStream(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String filePath = req.getRequestURI();
		int index = filePath.indexOf(PREVIEW);
		if (index >= 0) {
			filePath = filePath.substring(index + PREVIEW.length());
		}
		try {
			filePath = UriUtils.decode(filePath, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			logger.error(String.format("解释文件路径失败，URL地址为%s", filePath), e1);
		}
		if (StringUtils.isEmpty(filePath)) {
			return;
		}
		OutputStream os = null;
		InputStream inputStream = null;
		try {
			os = resp.getOutputStream();
			byte[] b = fileHandler.downloadFile(filePath);
			if(b != null){
				os.write(b, 0, b.length);
				os.flush();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 这里主要关闭。
			if (os != null)
				os.close();
			if (inputStream != null)
				inputStream.close();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		fileOutputStream(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		fileOutputStream(req, resp);
	}
}
