package com.huilian.hlej.hlej.file.web;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import com.huilian.hlej.hlej.file.service.FileHandler;
import com.huilian.hlej.hlej.file.vo.FileInfoVo;
import com.huilian.hlej.jet.common.web.BaseController;


@Controller
@RequestMapping(value = "${adminPath}/file")
public class FileCommonController extends BaseController{

	@Autowired
	FileHandler fileHandler;

	@RequestMapping(value = {"upload", ""},method = RequestMethod.POST )
	@ResponseBody
	public Map<String,String> uploadFile(MultipartRequest request,FileInfoVo fileInfo){
		Map<String,String> map=new HashMap<>();
		try{
		    MultipartFile file=request.getFile("uploadedFile");
			if (file==null||file.getSize()==0){
				map.put("code" ,"-1");
				map.put("msg","文件不能为空");
				map.put("path" ,"");
				map.put("fileName" ,"");
				return map;
			}
			if(fileInfo == null){
			    fileInfo = new FileInfoVo(); 
			}
			String fileName = fileInfo.getFileName();
			if(StringUtils.isEmpty(fileName)){
			    fileName = file.getOriginalFilename();
			    fileInfo.setFileName(fileName);
			}
            if (StringUtils.isEmpty(fileName)){
                map.put("code" ,"-2");
                map.put("msg","文件名不能为空");
                return map;
            }
            String dataSource = fileInfo.getDataSource();
            if(StringUtils.isEmpty(dataSource)){
                fileInfo.setDataSource("hlej");
            }
            
            fileInfo = fileHandler.upload(file.getBytes(), fileInfo);
            String name = fileInfo.getFilePath();
            if (name != null) {
                map.put("code", "1");
                map.put("msg", "成功");
                map.put("path", name);
                map.put("fileName" ,file.getOriginalFilename());
            } else {
                map.put("code", "-1");
                map.put("msg", "失败");
                map.put("path", "");
                map.put("fileName" ,"");
            }
		}catch (Exception e){
		    e.printStackTrace();
			map.put("code" ,"-1");
			map.put("msg","失败");
			map.put("path" ,"");
			map.put("fileName" ,"");
		}
		return map;
	}
	
    @RequestMapping("download")
    public void download(HttpServletResponse response,FileInfoVo fileInfo) throws IOException {
           response.setCharacterEncoding("utf-8");
           response.setContentType("multipart/form-data");
           String filePath = fileInfo.getFilePath();
           String fileName = fileInfo.getFileName();
           if(StringUtils.isEmpty(fileName)){
               String fileFormat = FileHandler.getFileFormat(filePath);
               if(StringUtils.isNotEmpty(fileFormat)){
                   fileFormat = FileHandler.POINT + fileFormat;
               }
               fileName = Long.toString(System.currentTimeMillis()) + fileFormat;
           }
           if(StringUtils.isEmpty(filePath)){
              return;
           }
           response.setHeader("Content-Disposition", "attachment;fileName="
               +  java.net.URLEncoder.encode(fileName, "UTF-8"));
           OutputStream os = null;
           InputStream inputStream = null;
           try {
               os = response.getOutputStream();
               byte[] b = fileHandler.downloadFile(filePath);
               os.write(b, 0, b.length);
           } catch (FileNotFoundException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally{
        	   // 这里主要关闭。
        	   if(os != null)
                os.close();
        	   if(inputStream != null)
               inputStream.close();
           }
     }
    

    
    /**
     * 图片登陆预览
     * @param response
     * @param fileInfo
     * @throws IOException
     */
    @RequestMapping("preview")
    public void preview(HttpServletResponse response,FileInfoVo fileInfo) throws IOException {
           String filePath = fileInfo.getFilePath();
           if(StringUtils.isEmpty(filePath)){
               return;
            }
           OutputStream os = null;
           InputStream inputStream = null;
           try {
               os = response.getOutputStream();
               byte[] b = fileHandler.downloadFile(filePath);
               if(b != null){
            	   os.write(b, 0, b.length);
            	   os.flush();
               }
           } catch (FileNotFoundException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally{
        	   // 这里主要关闭。
        	   if(os != null)
                os.close();
        	   if(inputStream != null)
               inputStream.close();
           }
     }
    
  	@RequestMapping(value = {"auth"} )
	@ResponseBody
	public Map<String,String> uploadFile(){
		Map<String,String> map=new HashMap<>();
		map.put("code" ,"0");
		map.put("msg","success");
		return map;
	}
    
   
    
    @RequestMapping(value = {"deleteFile", ""},method = RequestMethod.POST )
    @ResponseBody
    public Map<String, String> deleteFile(HttpServletRequest request) throws IOException {
        Map<String, String> resultMap = new HashMap<>();
        try {
            String fileUrl = request.getParameter("filePath");// 真实地址
            if (StringUtils.isEmpty(fileUrl)) {
                resultMap.put("code", "-2");
                resultMap.put("msg", "删除失败，文件的地址为空！");
                return resultMap;
            }
            fileHandler.deleteFileForFile(fileUrl);
            resultMap.put("code", "1");
            resultMap.put("msg", "删除文件成功！");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("code", "-3");
            resultMap.put("msg", "删除文件失败,系统内部报错！");
        }
        return resultMap;
     }
}
