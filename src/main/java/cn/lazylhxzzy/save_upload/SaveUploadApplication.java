package cn.lazylhxzzy.save_upload;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("cn.lazylhxzzy.save_upload.mapper")
@EnableConfigurationProperties
public class SaveUploadApplication {
    public static void main(String[] args) {
        SpringApplication.run(SaveUploadApplication.class, args);
    }
}