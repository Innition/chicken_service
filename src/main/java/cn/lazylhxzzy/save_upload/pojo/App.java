package cn.lazylhxzzy.save_upload.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class App {
    private String version;
    private String name;
    private int id;
    private LocalDateTime commitTime;
    private String url;
    private String description;
}