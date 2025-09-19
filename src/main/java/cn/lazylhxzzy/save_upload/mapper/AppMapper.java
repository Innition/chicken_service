package cn.lazylhxzzy.save_upload.mapper;

import cn.lazylhxzzy.save_upload.pojo.App;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppMapper {

    public App findLatestApp();

    public App findByVersion(String version);

    public void insertApp(App app);

    public List<App> findAll();
}

