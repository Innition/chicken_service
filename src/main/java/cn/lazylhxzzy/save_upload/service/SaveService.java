package cn.lazylhxzzy.save_upload.service;

import cn.lazylhxzzy.save_upload.pojo.Save;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SaveService {

    Save downloadSave(Integer id);

    void uploadDefaultFile(MultipartFile file);

    String uploadFile(MultipartFile file);

    Integer getNewestId();

    Integer getOldestId();

    List<Save> getSaveList();
}
