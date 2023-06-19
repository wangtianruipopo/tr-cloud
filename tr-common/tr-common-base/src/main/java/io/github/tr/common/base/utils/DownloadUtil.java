package io.github.tr.common.base.utils;

import lombok.SneakyThrows;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.function.Consumer;

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
}
