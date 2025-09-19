package cn.lazylhxzzy.save_upload.service.impl;

import cn.lazylhxzzy.save_upload.mapper.SaveMapper;
import cn.lazylhxzzy.save_upload.pojo.Save;
import cn.lazylhxzzy.save_upload.service.SaveService;
import cn.lazylhxzzy.save_upload.utils.AliOssUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static cn.lazylhxzzy.save_upload.utils.ControllerUtil.calculateRelativeTime;
import static cn.lazylhxzzy.save_upload.utils.ControllerUtil.getFileType;

@Slf4j
@Service
public class SaveServiceImpl implements SaveService {
    @Resource
    private AliOssUtil aliOssUtil;
    @Autowired
    private SaveMapper saveMapper;

    @Override
    public Save downloadSave(Integer id){
        Save save = saveMapper.getSaveById(id);
        if (save == null) {
            return null;
        }
        return save;
    }
    @Override
    public void uploadDefaultFile(MultipartFile file){
        System.out.println("--------------------------");
        try {
            //原始文件名
            /*首先通过file.getOriginalFilename()获取原始文件名*/
            String originalFilename = file.getOriginalFilename();
            //截取原始文件名的后缀   dfdfdf.png
            /*然后通过originalFilename.lastIndexOf(".")获取文件名的后缀。*/
            //构造新文件名称与路径
            String objectName = "saves/" + originalFilename;
            //文件的请求路径
            /*然后，调用aliOssUtil.upload方法将文件上传到OSS，并获取文件的请求路径。*/
            aliOssUtil.upload(file.getBytes(), objectName);
            LocalDateTime now = LocalDateTime.now();
            //保存文件信息到数据库
            //创建UserFile对象
            /*最后，返回一个Result对象，其中包含上传文件的请求路径。*/
        } catch (IOException e) {
            log.error("文件上传失败：{}", e);
        }
    }
    @Override
    public String uploadFile(MultipartFile file) {
        System.out.println("--------------------------");
        if (file == null || file.isEmpty()) {
            return null;
        }
        String url;
        Integer newestId = getNewestId();
        Integer oldestId = getOldestId();
        List<Integer> ids =saveMapper.getSaveIds();
        Boolean rewrite = false;
        if (newestId == null){
            newestId = oldestId;
        }
        else if (ids.size() >= 8){
            newestId = oldestId;
            rewrite = true;
        }
        else{
            newestId++;
        }
        try {
            //原始文件名
            /*首先通过file.getOriginalFilename()获取原始文件名*/
            String originalFilename = file.getOriginalFilename();
            //截取原始文件名的后缀   dfdfdf.png
            /*然后通过originalFilename.lastIndexOf(".")获取文件名的后缀。*/
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String objectOnlyName = newestId +"_" + originalFilename;

            //构造新文件名称与路径
            String objectName = "saves/" + objectOnlyName;

            //文件的请求路径
            /*然后，调用aliOssUtil.upload方法将文件上传到OSS，并获取文件的请求路径。*/
            url = aliOssUtil.upload(file.getBytes(), objectName);
            LocalDateTime now = LocalDateTime.now();
            //保存文件信息到数据库
            //创建UserFile对象
            Save save = new Save(newestId, url, now, objectOnlyName);
            if (rewrite) {
                saveMapper.updateSave(save);
            }
            else {
                saveMapper.addSave(save);
            }
            /*最后，返回一个Result对象，其中包含上传文件的请求路径。*/
            return url;
        } catch (IOException e) {
            log.error("文件上传失败：{}", e);
            return null;
        }
    }

    @Override
    public Integer getNewestId() {
        List<Save> saveList = saveMapper.getSaveList();
        if (saveList == null || saveList.size() == 0) {
            return null;
        }
        List<LocalDateTime> dates = saveList.stream().map(Save::getDate).sorted().toList();
        Save newestSave = saveList.stream().filter(save -> save.getDate().equals(dates.get(dates.size() - 1))).findFirst().get();
        return newestSave.getId();
    }

    @Override
    public Integer getOldestId() {
        List<Save> saveList = saveMapper.getSaveList();
        if (saveList == null || saveList.size() == 0) {
            return null;
        }
        List<LocalDateTime> dates = saveList.stream().map(Save::getDate).sorted().toList();
        Save oldestSave = saveList.stream().filter(save -> save.getDate().equals(dates.get(0))).findFirst().get();
        return oldestSave.getId();
    }

    @Override
    public List<Save> getSaveList() {
        List<Save> saveList = saveMapper.getSaveList();
        return saveList;
    }
}
