package io.github.wangtianruipopo.trcoregen.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.tr.common.base.utils.DownloadUtil;
import io.github.tr.common.web.service.impl.WrapperServiceImpl;
import io.github.wangtianruipopo.trcoregen.entity.*;
import io.github.wangtianruipopo.trcoregen.mapper.ColumnsMapper;
import io.github.wangtianruipopo.trcoregen.mapper.TablesMapper;
import io.github.wangtianruipopo.trcoregen.service.ITablesService;
import io.github.wangtianruipopo.trcoregen.utils.VelocityInitializer;
import io.github.wangtianruipopo.trcoregen.utils.VelocityUtils;
import io.github.wangtianruipopo.trcoregen.utils.ZipUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public abstract class AbsTablesServiceImpl extends WrapperServiceImpl<TablesMapper, Tables> implements ITablesService {

    @Resource
    protected ColumnsMapper columnsMapper;

    @Override
    public IPage<?> queryMapper(Page<Tables> page, Map<String, Object> p) {
        return dynamicQuery(page, p);
    }

    @Override
    public void createCode(CreateCodeFilter filter, HttpServletResponse response) {
        List<Tables> tableList = filter.getTableList();
        String uuid = "TEMP_" + IdUtil.fastSimpleUUID();
        tableList.forEach(table -> {
            List<Columns> columns = this.listColumn(table.getTableSchema(), table.getTableName());
            System.out.println(columns);
            // 生成实体类
            createCode(filter, table, columns, uuid);
        });
        String zipRoot = System.getProperty("user.home");
        String fileRoot = zipRoot + File.separator + uuid;
        try {
            ZipUtil.fileToZip(fileRoot);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        byte[] bytes = new byte[0];
        InputStream zis = null;
        try {
            zis = Files.newInputStream(new File(zipRoot + File.separator + uuid + ".zip").toPath());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = zis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }

            bytes = bos.toByteArray();
        } catch (IOException e) {
            log.error("zip读取异常！" , e);
        } finally {
            try {
                if (zis != null) zis.close();
            } catch (IOException e) {
                log.error("zip关闭异常！" , e);
            }
        }
        DownloadUtil.downloadFile(response, "code.zip" , bytes);
        // 删除生成的文件
        File file = new File(fileRoot);
        File zipFile = new File(fileRoot + ".zip");
        deleteFolder(file);
        zipFile.delete();
    }

    private static void deleteFolder(File folder) {
        if (folder != null && folder.isDirectory()) {
            for (File file : Objects.requireNonNull(folder.listFiles())) {
                if (file.isDirectory()) {
                    deleteFolder(file); // 递归删除子文件夹
                } else {
                    file.delete(); // 直接删除文件
                }
            }

            folder.delete(); // 最后删除当前文件夹本身
        }
    }

    @SneakyThrows
    private void createCode(CreateCodeFilter filter, Tables table, List<Columns> columns, String rootPath) {
        table.addColumns(columns);
        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table, filter);

        List<TemplateFile> templates = new ArrayList<>();
        templates.add(TemplateFile.builder().prefix("entity").templatePath("vm/java/entity.java.vm").func(tableName -> tableName).suffix("java").build());
        templates.add(TemplateFile.builder().prefix("mapper").templatePath("vm/java/mapper.java.vm").func(tableName -> tableName + "Mapper").suffix("java").build());
        templates.add(TemplateFile.builder().prefix("xml").templatePath("vm/xml/mapper.xml.vm").func(tableName -> tableName + "Mapper").suffix("xml").build());
        templates.add(TemplateFile.builder().prefix("service").templatePath("vm/java/service.java.vm").func(tableName -> "I" + tableName + "Service").suffix("java").build());
        templates.add(TemplateFile.builder().prefix("service" + File.separator + "impl").templatePath("vm/java/serviceImpl.java.vm").func(tableName -> tableName + "ServiceImpl").suffix("java").build());
        templates.add(TemplateFile.builder().prefix("controller").templatePath("vm/java/controller.java.vm").func(tableName -> tableName + "Controller").suffix("java").build());

        String zipRoot = System.getProperty("user.home");
        String fileRoot = zipRoot + File.separator + rootPath;

        for (TemplateFile template : templates) {
            String filePath = fileRoot + File.separator + template.getPrefix();
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template.getTemplatePath(), "UTF-8");
            tpl.merge(context, sw);
            String fileName = template.getName(table.getClassName()) + "." + template.getSuffix();
            // 添加到zip
            try {
                FileUtil.mkdir(filePath);
                IOUtils.write(sw.toString(), Files.newOutputStream(Paths.get(filePath + File.separator + fileName)), "UTF-8");
            } catch (IOException e) {
                log.error("模板生成代码异常！" , e);
            }
        }
    }

    @Override
    public List<Schema> listSchema() {
        return this.baseMapper.listSchema();
    }

    public abstract IPage<?> dynamicQuery(Page<Tables> page, Map<String, Object> p);

    public abstract List<Columns> listColumn(String schema, String tableName);
}
