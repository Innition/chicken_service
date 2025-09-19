package cn.lazylhxzzy.save_upload.pojo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
@Data
public class Save {
    private int id;
    private String url;
    private LocalDateTime date;
    private String name;

    public Save(Integer id, String url, LocalDateTime date, String name) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.date = date;
    }
}
