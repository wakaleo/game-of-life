package com.huilian.hlej.hlej.file.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.huilian.hlej.hlej.file.vo.FastdfsConstant;
import com.huilian.hlej.hlej.file.vo.FileInfoVo;
import com.huilian.hlej.jet.common.utils.UniqId;

@Service
public class FileHandler {

    private static String CONFIG_PATH        = null;

    private static String FDFS_CLIENT_NAME   = "fdfs_client.conf";

    private static String FILE_NAME          = "file:";

    private static String NULL_NAME          = "";

    private static String SERATIOR_LINE      = "/";

    private static int    FARMAT_MAX_LENGTHI = 6;

    public static String  POINT              = ".";
    
    private static String NULL_STRING = "NULL";
    /**
     * 由于业务图片需登录访问，所有改为 默认暂 上传到N1组无需改动业务代码
     * 若上传图片可随意访问，需指定 组名 为 G1 @see FileInfoVo.fileGroupId
     * 若无需指定 组名 参数 值为  NULL 字符串 ，不推荐，预留值  @see FileHandler.NULL_STRING
     */
    private static String DEFAULT_GTROUP = "G1";

    // private static final String JAR_NAME = "jar:";
    private String        fileGroup;
    private String        fileModule;
    private static Logger logger             = LoggerFactory.getLogger(FileHandler.class);

    static {
        try {
            File file = new File(FileHandler.class.getResource("/").getFile());
            CONFIG_PATH = file.getCanonicalPath();
            CONFIG_PATH = CONFIG_PATH + File.separator + FDFS_CLIENT_NAME;
            if (!new File(CONFIG_PATH).exists()) {
                CONFIG_PATH = FileHandler.class.getClassLoader().getResource(FDFS_CLIENT_NAME).getFile().replace("jar:", NULL_NAME).replace(FILE_NAME, NULL_NAME);
            }
            ClientGlobal.init(CONFIG_PATH);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("the fdfs_client.conf can't found.");
        } catch (MyException e) {
            logger.error("inital fdfsclient fail.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        String filePath = "C:\\Users\\LX\\Pictures\\Saved Pictures\\ice-registry.png";
        FileHandler util = new FileHandler();
        File file = new File(filePath);
        FileInfoVo fileInfo = new FileInfoVo();
        fileInfo.setFileFormat("png");
        fileInfo.setFileName("fileInfoModelName");
        fileInfo.setFileNo(UniqId.getInstance().getUniqID());
        fileInfo = util.upload(file, fileInfo);
        filePath = "G1/M00/00/02/wKgeGValzYGAWhjiAAHclIMSA7k365.jpg";
        int data = util.deleteFileForFile(filePath);
        System.out.println("fileInfo ： " + data);
        System.out.println("fileInfo ： " + fileInfo);
    }

    public FileInfoVo upload(File file, FileInfoVo fileInfo) throws Exception {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte[] file_buff = null;
            if (fis != null) {
                int len = fis.available();
                file_buff = new byte[len];
                fis.read(file_buff);
            }
            fileInfo = upload(file_buff, fileInfo);
        } finally {
            if (fis != null) fis.close();
        }
        return fileInfo;
    }

    public FileInfoVo upload(FileInputStream fis, FileInfoVo fileInfo) throws Exception {
        byte[] file_buff = null;
        if (fis != null) {
            int len = fis.available();
            file_buff = new byte[len];
            fis.read(file_buff);
        }
        return upload(file_buff, fileInfo);
    }

    public static String getFileFormat(String filePath) {
        int pointIndex = filePath.lastIndexOf(POINT);
        String fileFarmat = NULL_NAME;
        if (StringUtils.isEmpty(fileFarmat)) {
            if ((pointIndex > 0) && (filePath.length() - pointIndex < FARMAT_MAX_LENGTHI)) {
                fileFarmat = filePath.substring(pointIndex + 1);
            } else {
                fileFarmat = "";
                logger.error("upload file fail, the file format unknow ,file name : " + filePath);
            }
        }
        return fileFarmat;
    }

    public FileInfoVo upload(byte[] file_buff, FileInfoVo fileInfo) throws Exception {
        String group_name = null;
        String[] results = null;
        if (fileInfo == null) {
            return null;
        }
        if (file_buff == null) {
            return null;
        }
        String fileName = fileInfo.getFileName();
        if (StringUtils.isEmpty(fileName)) {
            return null;
        }
        String fileFarmat = fileInfo.getFileFormat();
        String filePath = null;
        int pointIndex = fileName.lastIndexOf(POINT);
        if (StringUtils.isEmpty(fileFarmat)) if ((pointIndex > 0) && (fileName.length() - pointIndex < FARMAT_MAX_LENGTHI)) {
            fileFarmat = fileName.substring(pointIndex + 1);
        } else {
            fileFarmat = "";
            logger.error("upload file fail, the file format unknow ,file name : " + fileName);
        }
        try {
            fileInfo.setFileFormat(fileFarmat);
        } catch (Exception e) {
            logger.error("hlej fastdfs client. invoke fastdfsService fail " + fileName);
            e.printStackTrace();
        }

        try {
            StorageClient storageClient = getFastStorageClient();
            if(StringUtils.isEmpty(fileInfo.getFileGroupId())){
            	if(StringUtils.isEmpty(fileGroup)){
            		fileGroup = DEFAULT_GTROUP;
            	}
            	group_name = fileGroup;
            }else if(StringUtils.equalsIgnoreCase(fileInfo.getFileGroupId(), NULL_STRING)){
            }else{
            	group_name = fileInfo.getFileGroupId();
            }
            results = storageClient.upload_file(group_name, file_buff, fileFarmat, null);
            if (results == null) {
                logger.error("upload file fail, error code: " + storageClient.getErrorCode() + ",file name : " + fileName);
                fileInfo.setDataStatus(FastdfsConstant.FileInfoStatus.UPLOAD_FAIL);
            } else {
                group_name = results[0];
                filePath = results[0].concat("/").concat(results[1]);
                if (StringUtils.isEmpty(filePath)) {
                    logger.error("upload file fail, results: " + results + ",file name : " + fileName);
                    fileInfo.setDataStatus(FastdfsConstant.FileInfoStatus.UPLOAD_FAIL);
                } else {
                    fileInfo.setDataStatus(FastdfsConstant.FileInfoStatus.UPLAOD_SUCCESS);
                    fileInfo.setFilePath(filePath);
                }
            }
        } catch (IOException e) {
            fileInfo.setDataStatus(FastdfsConstant.FileInfoStatus.ERROR);
            e.printStackTrace();
            logger.error("upload file fail, error code: " + e);
            throw new IOException(e);
        }
        int ret = 0;
        try {
            ret = 1;
        } catch (Exception e) {
            logger.error("hlej fastdfs client. invoke fastdfsService fail " + fileName);
            e.printStackTrace();
        }
        if (ret == 0) {
            fileInfo.setDataStatus(FastdfsConstant.FileInfoStatus.UPLOAD_SUCCESS_DATA_FAILE);
        }
        return fileInfo;
    }

    private StorageClient getFastStorageClient() throws IOException {
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        StorageServer storageServer = null;
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        return storageClient;
    }

    /**
     * 物理删除文件
     * 
     * @param filePath
     * @return
     * @throws Exception
     */
    public int deleteFileForFile(String filePath) throws Exception {
        String groupName = null;
        if (filePath.startsWith(SERATIOR_LINE)) {
            filePath = filePath.substring(1);
        }
        int fristIndex = filePath.indexOf(SERATIOR_LINE);
        if (fristIndex <= 0) {
            logger.error("download file fail, the filePath not right . " + filePath);
            return -1;
        }
        groupName = filePath.substring(0, fristIndex);
        filePath = filePath.substring(fristIndex + 1);
        if (StringUtils.isEmpty(filePath)) {
            logger.error("download file fail, the filePath not right . " + filePath);
            return -1;
        }
        StorageClient storageClient = getFastStorageClient();
        return storageClient.delete_file(groupName, filePath);
    }

    public byte[] downloadFile(String filePath) throws Exception {
        if (StringUtils.isEmpty(filePath)) {
            return null;
        }
        if (filePath.startsWith(SERATIOR_LINE)) {
            filePath = filePath.substring(1);
        }
        int fristIndex = filePath.indexOf(SERATIOR_LINE);
        if (fristIndex <= 0) {
            logger.error("download file fail, the filePath not right . " + filePath);
            return null;
        }
        String groupName = filePath.substring(0, fristIndex);
        filePath = filePath.substring(fristIndex + 1);
        if (StringUtils.isEmpty(filePath)) {
            logger.error("download file fail, the filePath not right . " + filePath);
            return null;
        }
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        StorageServer storageServer = null;
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        byte[] fileByte = storageClient.download_file(groupName, filePath);
        return fileByte;
    }

    public String getFileGroup() {
        return this.fileGroup;
    }

    public void setFileGroup(String fileGroup) {
        this.fileGroup = fileGroup;
    }

    public String getFileModule() {
        return this.fileModule;
    }

    public void setFileModule(String fileModule) {
        this.fileModule = fileModule;
    }
}
