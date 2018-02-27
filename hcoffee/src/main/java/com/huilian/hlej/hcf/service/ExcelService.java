package com.huilian.hlej.hcf.service;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelService {

	/** 
     * 读取excel中的数据,生成list 
     */  
    String readExcelFile( MultipartFile file);
}
