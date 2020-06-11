package io.vincent.learning.stack.javacore.mp3;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Vincent on 2019/4/26.
 *
 * @author Vincent
 * @since 1.0, 2019/4/26
 */
@Slf4j
public class FileUtil {

    public static void traverseFolder2(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                log.warn("文件夹是空的!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        log.info("文件夹:" + file2.getAbsolutePath());
                        traverseFolder2(file2.getAbsolutePath());
                    } else {
                        log.info("文件:" + file2.getAbsolutePath());
                    }
                }
            }
        } else {
            log.warn("文件不存在!");
        }
    }

    static List<File> getFileList(String strPath) {
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        List<File> fileList = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();
                if (file.isDirectory()) { // 判断是文件还是文件夹
                    getFileList(file.getAbsolutePath()); // 获取文件绝对路径
                } else if (fileName.endsWith(".mp3")) { // 判断文件名是否以.avi结尾
                    String strFileName = file.getAbsolutePath();
                    log.info("---" + strFileName);
                    fileList.add(file);
                }
            }

        }
        return fileList;
    }

    static void rename(File file, String newName) {
        // 开始重命名
        // 检查要重命名的文件是否存在
        if (!file.exists()) {
            log.warn("File does not exist: ");
            return;
        }
        // 修改文件名
        File newFile = new File(newName);
        if (file.renameTo(newFile)) {
            log.info("File rename success!!.");
        } else {
            log.warn("Error renmaing file");
        }

    }
}
