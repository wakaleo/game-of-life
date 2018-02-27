package com.huilian.hlej.hlej.file.vo;

import java.io.Serializable;

public class FileInfoVo  implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -4344607390336690662L;
    private int id;
    private String fileNo;
    private String fileGroupId;
    private String fileName;
    private String filePath;
    private String fileFormat;
    private String dataSource;
    
    public String getDataSource()
    {
        return dataSource;
    }
    public void setDataSource(String dataSource)
    {
        this.dataSource = dataSource;
    }
    private FastdfsConstant.FileInfoStatus dataStatus;
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public String getFileNo()
    {
        return fileNo;
    }
    public void setFileNo(String fileNo)
    {
        this.fileNo = fileNo;
    }
    public String getFileGroupId()
    {
        return fileGroupId;
    }
    public void setFileGroupId(String fileGroupId)
    {
        this.fileGroupId = fileGroupId;
    }
    public String getFileName()
    {
        return fileName;
    }
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
    public String getFilePath()
    {
        return filePath;
    }
    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }
    public String getFileFormat()
    {
        return fileFormat;
    }
    public void setFileFormat(String fileFormat)
    {
        this.fileFormat = fileFormat;
    }
    public FastdfsConstant.FileInfoStatus getDataStatus()
    {
        return dataStatus;
    }
    public void setDataStatus(FastdfsConstant.FileInfoStatus dataStatus)
    {
        this.dataStatus = dataStatus;
    }
      
  
}