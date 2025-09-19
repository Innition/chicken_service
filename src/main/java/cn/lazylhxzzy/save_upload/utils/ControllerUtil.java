package cn.lazylhxzzy.save_upload.utils;

import jakarta.servlet.http.HttpServletRequest;

import java.time.Duration;
import java.time.LocalDateTime;

public class ControllerUtil {
    public static String calculateRelativeTime(LocalDateTime fileTime) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(fileTime, now);

        long seconds = duration.getSeconds();
        if (seconds < 60) return seconds + "秒前";
        long minutes = seconds / 60;
        if (minutes < 60) return minutes + "分钟前";
        long hours = minutes / 60;
        if (hours < 24) return hours + "小时前";
        long days = hours / 24;
        if (days <= 7) return days + "天前";
        return fileTime.toLocalDate().toString();
    }

    // Helper to determine file type based on name
    public static String getFileType(String fileName) {
        if (fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".gif")) {
            return "image";
        } else if (fileName.endsWith(".mp4") || fileName.endsWith(".avi")) {
            return "video";
        } else if (fileName.endsWith(".zip") || fileName.endsWith(".rar")) {
            return "compressed";
        } else if (fileName.endsWith(".exe")){
            return "executable";
        }
        return "unknown";
    }

    public static String getClientIp(HttpServletRequest request) {
        String[] headers = {
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP",
                "HTTP_X_FORWARDED_FOR"
        };
        for (String header : headers) {
            String ip = request.getHeader(header);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                return ip.split(",")[0].trim(); // 处理多个 IP 的情况
            }
        }
        return request.getRemoteAddr();
    }
}
