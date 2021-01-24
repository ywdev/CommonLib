package com.ywcommon.common.util.file;

import android.os.Environment;
import android.text.TextUtils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class AMFileUtils {

    /**
     * 判断sd卡是否存在
     *
     * @return
     * @throws IllegalArgumentException 抛出异常，sd卡没安装
     */
    public static boolean isSdCardExist() {
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (!sdCardExist) {
            throw new IllegalArgumentException(
                    "Root path is empty.Please check whether the insert SD card！");
        }
        return sdCardExist;
    }


    /**
     * 创建文件
     *
     * @param file 文件对象
     * @throws IOException
     */
    public static void createFile(File file) throws IOException {
        if (!file.exists() && !file.isDirectory()) {
            file.createNewFile();
        }
    }


    /**
     * 创建文件夹
     *
     * @param file 文件对象
     */
    public static void createDirectory(File file) {
        makeIfNotExistFileDir(file);
    }

    /**
     * 创建文件夹
     *
     * @param dirName 文件夹路径
     */
    public static void createDirectory(String dirName) {
        makeIfNotExistDir(dirName);
    }

    /**
     * 创建文件夹
     *
     * @param dirName
     * @return
     */
    private static File makeIfNotExistDir(String dirName) {
        final File file = new File(dirName);
        if (!file.exists()) {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                file.mkdirs();
            }
        }
        return file;
    }

    /**
     * 创建文件对象的文件夹
     *
     * @param file
     */
    private static void makeIfNotExistFileDir(File file) {
        try {
            String fileName = file.getAbsolutePath();
            String fileDirName = fileName.substring(0,
                    fileName.lastIndexOf('/'));
            File dirFile = new File(fileDirName);
            if (!dirFile.exists()) {
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    dirFile.mkdirs();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断文件是否存在，否则创建文件
     *
     * @param filePath
     * @return
     */
    public static boolean isFileExists(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            throw new NullPointerException(
                    "Parameter 'filepath' is null or empty");
        }
        File file = new File(filePath);

        if (file.exists()) {
            return true;
        } else {
            String fileName = filePath;
            String fileDirName = fileName.substring(0,
                    fileName.lastIndexOf('/'));
            if (isFolderExists(fileDirName)) {
                try {
                    return (file.createNewFile() || file.isFile());
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }

        }
        return false;
    }

    /**
     * 判断文件是否存在，否则创建文件
     *
     * @param file
     * @return
     */
    public static boolean isFileExists(File file) {
        if (file.exists()) {
            return true;
        } else {
            try {
                return (file.createNewFile() || file.isFile());
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    /**
     * 判断文件夹是否存在，否则创建文件夹
     *
     * @param folderPath
     * @return
     */
    public static boolean isFolderExists(String folderPath) {
        if (TextUtils.isEmpty(folderPath)) {
            throw new NullPointerException(
                    "Parameter 'folderPath' is null or empty");
        }
        if (isSdCardExist()) {
            File file = new File(folderPath);
            if (file.exists()) {
                return true;
            } else {
                return (file.mkdirs() || file.isDirectory());
            }
        }
        return false;
    }

    /**
     * 获取文件完整路径
     *
     * @param file 文件对象
     * @return
     * @throws IOException
     */
    public static String getFilePath(File file) throws Exception {
        if (!file.exists()) {
            throw new IllegalArgumentException("文件不存在");
        }
        return file.getAbsolutePath();
    }


    /**
     * 获取文件名
     *
     * @param file
     * @return
     */
    public static String getFileName(File file) {
        if (!file.exists()) {
            throw new IllegalArgumentException("文件不存在");
        }
        return file.getName();
    }

    /**
     * 通过文件路径获取文件名称
     *
     * @param filePath 文件路径
     * @return 文件名
     */
    public static String getFileName(String filePath) {
        filePath = filePath.trim();
        return filePath.substring(filePath.lastIndexOf("/") + 1);
    }

    /**
     * 删除文件，如果是文件夹，就把文件下面所有文件删除
     *
     * @param strFilePath
     * @return
     */
    public static boolean deleteFile(String strFilePath) {
        if (TextUtils.isEmpty(strFilePath)) {
            throw new NullPointerException(
                    "Parameter 'strFilePath' is null or empty");
        }
        boolean result = false;
        File file = new File(strFilePath);
        try {
            FileUtils.forceDelete(file);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
            return result;
        }
        return result;
    }

    /**
     * 复制文件，源文件复制到目标文件
     *
     * @param srcFilePath
     * @param destFilePath
     * @throws IOException
     */
    public static void copyFile(String srcFilePath, String destFilePath)
            throws IOException {
        File srcFile = new File(srcFilePath);
        File destFile = new File(destFilePath);
        FileUtils.copyFile(srcFile, destFile);
    }

    /**
     * 将文件拷贝到指定文件夹下面
     *
     * @param srcFile
     * @param destDir
     * @throws IOException
     */
    public static void copyFileToDirectory(File srcFile, File destDir) throws IOException {
        FileUtils.copyFileToDirectory(srcFile, destDir);
    }

}
