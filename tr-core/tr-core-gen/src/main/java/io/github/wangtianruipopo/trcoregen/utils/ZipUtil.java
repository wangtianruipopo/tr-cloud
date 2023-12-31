package io.github.wangtianruipopo.trcoregen.utils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * ZipUtils
 *
 * @author shuai
 * @date 2021/7/30
 */
public class ZipUtil {

    /**
     * sourceFile一定要是文件夹
     * 默认会在同目录下生成zip文件
     *
     * @param sourceFilePath
     * @throws Exception
     */
    public static void fileToZip(String sourceFilePath) throws Exception {
        fileToZip(new File(sourceFilePath));
    }

    /**
     * sourceFile一定要是文件夹
     * 默认会在同目录下生成zip文件
     *
     * @param sourceFile
     * @throws Exception
     */
    public static void fileToZip(File sourceFile) throws Exception {

        if (!sourceFile.exists()) {
            throw new RuntimeException("不存在");
        }
        if (!sourceFile.isDirectory()) {
            throw new RuntimeException("不是文件夹");
        }
        //zip文件生成位置
        File zipFile = new File(sourceFile.getAbsolutePath() + ".zip");
        FileOutputStream fos = new FileOutputStream(zipFile);
        ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(fos));
        fileToZip(zos, sourceFile, "");
        zos.close();
        fos.close();
    }


    private static void fileToZip(ZipOutputStream zos, File sourceFile, String path) throws Exception {

        System.out.println(sourceFile.getAbsolutePath());

        //如果是文件夹只创建zip实体即可，如果是文件，创建zip实体后还要读取文件内容并写入
        if (sourceFile.isDirectory()) {
            path = path + sourceFile.getName() + "/";
            ZipEntry zipEntry = new ZipEntry(path);
            zos.putNextEntry(zipEntry);
            for (File file : sourceFile.listFiles()) {
                fileToZip(zos, file, path);
            }
        } else {
            //创建ZIP实体，并添加进压缩包
            ZipEntry zipEntry = new ZipEntry(path + sourceFile.getName());
            zos.putNextEntry(zipEntry);
            byte[] bufs = new byte[1024 * 10];
            //读取待压缩的文件并写进压缩包里
            FileInputStream fis = new FileInputStream(sourceFile);
            BufferedInputStream bis = new BufferedInputStream(fis, 1024 * 10);
            int read = 0;
            while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                zos.write(bufs, 0, read);
            }
            bis.close();
            fis.close();
        }
    }
}