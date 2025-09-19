package cn.lazylhxzzy.save_upload.mapper;

import cn.lazylhxzzy.save_upload.pojo.Save;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface SaveMapper {
    public void addSave(Save save);
    public void deleteSave(String name);
    public void updateSave(Save save);
    public Save getSaveById(Integer id);
    public List<Integer> getSaveIds();
    public List<Save> getSaveList();
}
