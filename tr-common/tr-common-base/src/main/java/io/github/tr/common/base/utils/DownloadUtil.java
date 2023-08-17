package io.github.tr.common.base.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.function.Consumer;

@Slf4j
public class DownloadUtil {
    @SneakyThrows
    public static void downExcel(HttpServletResponse response, String fileName, Consumer<HttpServletResponse> consumer) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        consumer.accept(response);
    }

    public static void downloadFile(HttpServletResponse response, String fileName, InputStream inputStream) {
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            log.error("文件名修改失败!", e);
        }
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        try {
            OutputStream os = response.getOutputStream();
            int i = 0;
            byte[] buffer = new byte[1024];
            while ((i = bis.read(buffer)) != -1) {
                os.write(buffer, 0, i);
            }
        } catch (IOException e) {
            log.error("文件流获取异常!", e);
        } finally {
            try {
                bis.close();
            } catch (IOException e) {
                log.error("文件流关闭异常!", e);
            }
        }
    }
}
