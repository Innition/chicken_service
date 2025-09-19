package cn.lazylhxzzy.save_upload.utils;
import cn.lazylhxzzy.save_upload.listener.UploadProgressListener;
import com.aliyun.oss.*;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.oss.event.ProgressListener;
import com.aliyun.oss.model.PutObjectRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.io.ByteArrayInputStream;
import java.io.File;

/**
 * @author: Zhuzhiyu
 * @description: AliOssUtil类是一个包含文件上传功能的工具类。
 * @date: 2024/12/5 17:01
 */

@Data
@AllArgsConstructor
@Slf4j
public class AliOssUtil{

    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
    /**
     * 文件上传
     *
     * @param bytes
     * @param objectName
     * @return
     */
    public String upload(byte[] bytes, String objectName) {

        // 创建OSSClient实例。
        /*在upload方法中，首先创建了一个OSSClient实例，用于与OSS服务进行交互。*/
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 创建PutObject请求。
            /*然后，通过调用ossClient.putObject方法将文件上传到指定的存储桶中。*/
            ossClient.putObject(new PutObjectRequest(bucketName, objectName, new ByteArrayInputStream(bytes)).<PutObjectRequest>withProgressListener(new UploadProgressListener((long) bytes.length)));
            /*在上传过程中，通过捕获OSSException和ClientException来处理可能出现的异常情况，并输出相应的错误信息。*/
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        //文件访问路径规则 https://BucketName.Endpoint/ObjectName
        /*最后，构建文件的访问路径，并使用日志记录上传文件的路径。*/
        StringBuilder stringBuilder = new StringBuilder("https://");
        stringBuilder
                .append(bucketName)
                .append(".")
                .append(endpoint)
                .append("/")
                .append(objectName);
        log.info("文件上传到:{}", stringBuilder.toString());
        return stringBuilder.toString();
    }

    public String upload(File file, String objectName) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            // 创建PutObject请求。
            /*然后，通过调用ossClient.putObject方法将文件上传到指定的存储桶中。*/
            ossClient.putObject(new PutObjectRequest(bucketName, objectName, file).<PutObjectRequest>withProgressListener(new UploadProgressListener()));
            /*在上传过程中，通过捕获OSSException和ClientException来处理可能出现的异常情况，并输出相应的错误信息。*/
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        //文件访问路径规则 https://BucketName.Endpoint/ObjectName
        /*最后，构建文件的访问路径，并使用日志记录上传文件的路径。*/
        StringBuilder stringBuilder = new StringBuilder("https://");
        stringBuilder
                .append(bucketName)
                .append(".")
                .append(endpoint)
                .append("/")
                .append(objectName);
        log.info("文件上传到:{}", stringBuilder.toString());
        return stringBuilder.toString();
    }
}

