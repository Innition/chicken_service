package cn.lazylhxzzy.save_upload.service.impl;

import cn.lazylhxzzy.save_upload.mapper.AppMapper;
import cn.lazylhxzzy.save_upload.pojo.App;
import cn.lazylhxzzy.save_upload.service.AppService;
import cn.lazylhxzzy.save_upload.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class AppServiceImpl implements AppService {
    @Value("${folder.path}")
    private String folderPath;

    @Value("${folder.version-history-path}")
    private String folderVersionHistoryPath;

    @Value("${app.name}")
    private String appName;

    @Autowired
    private AppMapper appMapper;

    @Autowired
    private AliOssUtil aliOssUtil;

    @Override
    public List<App> findAll() {
        return appMapper.findAll();
    }

    @Override
    public App getLatestVersion() {
        return appMapper.findLatestApp();
    }

    @Override
    public String uploadApp(MultipartFile file, String description){
        App latestApp = appMapper.findLatestApp();
        System.out.println("--------------------------");
        if (file == null || file.isEmpty()) {
            return null;
        }
        String url;
        String newVersion;
        String objectOnlyName;
        if(file.getName().contains("-")) {
            String part1 = file.getName().split("-")[1];
            newVersion = part1.substring(0, part1.lastIndexOf("."));
            objectOnlyName = file.getName();
        }
        else {
            if (latestApp == null || latestApp.getVersion() == null) {
                newVersion = "1.0.0";
            } else {
                // 简单的version号自增逻辑，实际可根据需求修改
                newVersion = incrementVersion(latestApp.getVersion());
            }
            String originalFilename = file.getOriginalFilename();
            String[] parts = originalFilename.split("\\.");
            objectOnlyName = parts[0] + "-" + newVersion + "." + parts[1];
        }
        int newId = (latestApp == null) ? 1 : latestApp.getId() + 1;
        LocalDateTime now = LocalDateTime.now();

        try {
            //构造新文件名称与路径
            String objectName = "apps/" + objectOnlyName;
            //文件的请求路径
            /*然后，调用aliOssUtil.upload方法将文件上传到OSS，并获取文件的请求路径。*/
            url = aliOssUtil.upload(file.getBytes(), objectName);
            //保存文件信息到数据库
            //创建UserFile对象
            App newApp = new App();
            newApp.setId(newId);
            newApp.setVersion(newVersion);
            newApp.setName(objectOnlyName);
            newApp.setCommitTime(now);
            newApp.setUrl(url);
            newApp.setDescription(description);
            appMapper.insertApp(newApp);
            return url;
            /*最后，返回一个Result对象，其中包含上传文件的请求路径。*/
        } catch (IOException e) {
            log.error("文件上传失败：{}", e);
            return null;
        }
        // 创建新的文件名： appName-version

    }

    @Override
    public String findUrlByVersion(String version) {
        App app = appMapper.findByVersion(version);
        if (app == null) {
            return null;
        }
        return app.getUrl();
    }
    private String incrementVersion(String version) {
        // 假设版本号格式：x.y.z
        // 简单处理：将z+1
        String[] parts = version.split("\\.");
        if (parts.length != 3) {
            return "1.0.0";
        }
        int major = Integer.parseInt(parts[0]);
        int minor = Integer.parseInt(parts[1]);
        int patch = Integer.parseInt(parts[2]);
        patch++;
        return major + "." + minor + "." + patch;
    }
}
