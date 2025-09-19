package cn.lazylhxzzy.save_upload;

import cn.lazylhxzzy.save_upload.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootTest
public class Test {
    @Autowired
    AppService appService;
    @org.junit.jupiter.api.Test
    public void test1() throws IOException {
        File file = new File("D:\\D_desktop\\Package\\SaveUpload.exe");
        long size = file.length();
        byte[] bytes = new byte[(int) size];
        String description = "This is a test file";
        FileInputStream fis = new FileInputStream(file);
        fis.read(bytes);
        MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "application/octet-stream", bytes);
        appService.uploadApp(multipartFile, description);
    }
}
