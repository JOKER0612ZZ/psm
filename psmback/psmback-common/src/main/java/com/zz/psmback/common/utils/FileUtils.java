package com.zz.psmback.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

@Component
@Slf4j
public class FileUtils {
    @Value("${file-storage.upload-dir}")
    private String dir;
    public boolean createFolder(String path) {
        Path filePath = Paths.get(dir,path);
        if(!Files.exists(filePath)){
            try {
                Files.createDirectories(filePath);
                log.info("Creating folder:"+true);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }else{
            return false;
        }
    }
    public boolean deleteFolder(String path) {
        Path filePath = Paths.get(dir, path);
        if (Files.exists(filePath)) {
            try {
                Files.walk(filePath)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
                log.info("删除文件夹：" + path);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            log.warn("文件夹不存在：" + path);
            return false;
        }
    }

    public String subLastPath(String path){
        String[] paths = path.split("/");
        return paths[paths.length-1];
    }

    public boolean deleteFile(Path path) {
        try {
            // 使用 Files.delete() 方法删除文件
            Files.delete(path);
            log.info("文件删除成功：" + path);
            return true;
        } catch (IOException e) {
            System.err.println("删除文件时出错：" + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
