package cn.lazylhxzzy.save_upload.controller;

import cn.lazylhxzzy.save_upload.pojo.Save;
import cn.lazylhxzzy.save_upload.service.impl.SaveServiceImpl;
import cn.lazylhxzzy.save_upload.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static cn.lazylhxzzy.save_upload.utils.ControllerUtil.*;


@Controller
@RequestMapping("/save")
@Slf4j
public class SaveController {
    @Autowired
    private SaveServiceImpl saveService;

    // Whitelist for IPs
    @Value("${whitelist.ips}")
    private String[] ipWhitelist;

    @Value("${download.default-url}")
    private String defaultDownloadUrl;

    @GetMapping("/")
    public String savePage(HttpServletRequest request, Model model) {
//                String clientIp = getClientIp(request);
//        System.out.println("client ip: " + clientIp);
        // Check if IP is whitelisted
//        if (!Arrays.asList(ipWhitelist).contains(clientIp)) {
//            return "error/403"; // Return a 403 error page if not in whitelist
//        }

        // Fetch and process data from the database
        List<Save> saveList = saveService.getSaveList();
        List<Object> processedList = saveList.stream().map(save -> {
                    String relativeTime = calculateRelativeTime(save.getDate());
                    String fileType = getFileType(save.getName());
                    return new Object[]{save.getName(), save.getDate(), save.getUrl(), fileType, relativeTime};
                }).sorted((a, b) -> ((LocalDateTime) b[1]).compareTo((LocalDateTime) a[1])) // Sort by date descending
                .collect(Collectors.toList());

        model.addAttribute("title", "小鸡存档");
        model.addAttribute("fileList", processedList);
        model.addAttribute("defaultDownloadUrl", defaultDownloadUrl);
        return "filePages/save"; // HTML template name
    }
    @ResponseBody
    @GetMapping("/save-list")
    public Result<List<Save>> getSaveList() {
        List<Save> saveList = saveService.getSaveList();
        return Result.isOk(saveList);
    }
    @GetMapping("/latest-save.zip")
    public void getUrl( HttpServletResponse response) throws Exception {
        Integer id = saveService.getNewestId();
        String url = saveService.downloadSave(id).getUrl();
        log.info("download url get, url:{}", url);
        response.sendRedirect(url);
        }

    @ResponseBody
    @PostMapping("/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) {
        String url = saveService.uploadFile(file);
        saveService.uploadDefaultFile(file);
        log.info("upload url get, url:{}", url);
        return Result.isOk(url);
    }
    @PostMapping("/web-upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            saveService.uploadFile(file);
            saveService.uploadDefaultFile(file); // Call saveService to handle the upload
            redirectAttributes.addFlashAttribute("message", "File uploaded successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Upload failed: " + e.getMessage());
        }
        return "redirect:/save/"; // Refresh the page
    }

}
