package com.huilian.hlej.hlej.file.vo;

public class FastdfsConstant
{
  public static enum FileInfoStatus
  {
    UPLOADING(0, "文件上传中"), 
    FILE_DELETED(-1, "文件已删除"), 
    UPLOAD_FAIL(-2, "文件上传失败"), 
    UPLOAD_SUCCESS_DATA_FAILE(-3, "文件上传成功，但保存上传数据失败"), 
    ERROR(-4, "内部错误,文件上传状态未知"), 
    UPLAOD_SUCCESS(1, "文件上传成功");

    private int code;
    private String msg;

    private FileInfoStatus(int code, String msg) { this.code = code;
      this.msg = msg;
    }

    public int getCode()
    {
      return this.code;
    }

    public String getMsg()
    {
      return this.msg;
    }
    public static FileInfoStatus getFileInfoStatusByCode(int code) {
      FileInfoStatus[] fileInfoStatuses = values();
      for (FileInfoStatus fileInfoStatus : fileInfoStatuses) {
        int curCode = fileInfoStatus.getCode();
        if (curCode == code) {
          return fileInfoStatus;
        }
      }
      return null;
    }
  }
}