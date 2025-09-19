package cn.lazylhxzzy.save_upload.controller;


import cn.lazylhxzzy.save_upload.mapper.AppMapper;
import cn.lazylhxzzy.save_upload.pojo.App;
import cn.lazylhxzzy.save_upload.service.impl.AppServiceImpl;
import cn.lazylhxzzy.save_upload.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.lazylhxzzy.save_upload.utils.ControllerUtil.calculateRelativeTime;
import static cn.lazylhxzzy.save_upload.utils.ControllerUtil.getFileType;

@Controller
@RequestMapping("/app")
public class AppController {
    @Autowired
    private AppServiceImpl appService;

    @Autowired
    private AppMapper appMapper;

    @GetMapping("/")
    public String appPage(HttpServletRequest request, Model model){
        List<App> appList = appService.findAll();
        List<Object> processedList = appList.stream().map(app -> {
                    String relativeTime = calculateRelativeTime(app.getCommitTime());
                    String fileType = getFileType(app.getName());
                    return new Object[]{app.getName(), app.getCommitTime(), app.getUrl(), fileType, relativeTime};
                }).sorted((a, b) -> ((LocalDateTime) b[1]).compareTo((LocalDateTime) a[1])) // Sort by date descending
                .collect(Collectors.toList());

        model.addAttribute("title", "小鸡app");
        model.addAttribute("fileList", processedList);
        return "filePages/app"; // HTML template name
    }
    @ResponseBody
    @RequestMapping("/latest-version")
    public Result<Map<String, Object>> getVersion() {
        App latestApp = appService.getLatestVersion();
        Map<String, Object> map = new HashMap<>();
        map.put("version", latestApp.getVersion());
        map.put("description", latestApp.getDescription());
        return Result.isOk(map);
    }

    @PostMapping("/web-upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("description") String description, RedirectAttributes redirectAttributes) {
        try {
            appService.uploadApp(file, description);
            redirectAttributes.addFlashAttribute("message", "File uploaded successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Upload failed: " + e.getMessage());
            return "redirect:/filePages/app";
        }
        return "redirect:/filePages/app"; // Refresh the page
    }

    @GetMapping("/SaveUpload-{version}.exe")
    public void getUrl( HttpServletResponse response, @PathVariable("version") String version) throws Exception {
        String url = appService.findUrlByVersion(version);
        response.sendRedirect(url);
    }
}
