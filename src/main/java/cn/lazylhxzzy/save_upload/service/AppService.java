package cn.lazylhxzzy.save_upload.service;

import cn.lazylhxzzy.save_upload.pojo.App;
import cn.lazylhxzzy.save_upload.pojo.Save;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AppService {
    List<App> findAll();

    App getLatestVersion();

    String uploadApp(MultipartFile file, String description);

    String findUrlByVersion(String version);
}
