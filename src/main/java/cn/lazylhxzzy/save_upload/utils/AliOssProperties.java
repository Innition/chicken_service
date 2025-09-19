package cn.lazylhxzzy.save_upload.utils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 * @author: Zhuzhiyu
 * @description: 阿里云配置实体类
 * @date: 2024/12/5 16:53
 */

@Component
@ConfigurationProperties(prefix = "aliyun.oss")
@Data
public class AliOssProperties {
    private String endpoint; //表示OSS服务的访问域名。
    private String accessKeyId; //表示访问OSS服务所需的Access Key ID。
    private String accessKeySecret; //表示访问OSS服务所需的Access Key Secret。
    private String bucketName; //表示要操作的存储桶名称。
}
