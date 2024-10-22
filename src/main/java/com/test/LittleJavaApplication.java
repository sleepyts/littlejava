
package com.test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class LittleJavaApplication {

    public static void removeTextFromFile(File file, String textToRemove) {
        try {
            // 读取文件内容
            Path path = file.toPath();
            String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);

            // 检查并删除指定文本
            if (content.contains(textToRemove)) {
                content = content.replace(textToRemove, "");

                // 将删除文本后的内容重新写入文件
                Files.write(path, content.getBytes(StandardCharsets.UTF_8));
                System.out.println("更新文件: " + file.getName());
            } else {
                System.out.println("未找到匹配文本: " + file.getName());
            }
        } catch (IOException e) {
            System.err.println("处理文件时出错: " + file.getName());
            e.printStackTrace();
        }
    }

    // 遍历文件夹中的所有文件，并对每个文件执行文本检查和删除操作
    public static void traverseFolderAndRemoveText(File folder, String textToRemove) {
        // 检查路径是否为文件夹
        if (!folder.isDirectory()) {
            System.err.println("路径不是文件夹: " + folder.getAbsolutePath());
            return;
        }

        // 遍历文件夹中的文件和子文件夹
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 如果是文件夹，递归遍历子文件夹
                    traverseFolderAndRemoveText(file, textToRemove);
                } else if (file.isFile()) {
                    // 如果是文件，处理文件内容
                    removeTextFromFile(file, textToRemove);
                }
            }
        } else {
            System.err.println("无法访问文件夹内容: " + folder.getAbsolutePath());
        }
    }

    public static void main(String[] args) {
        // 示例文件夹路径和要删除的文本
        String folderPath = "D:/IdeaProjects/shortlink"; // 指定文件夹路径
        String textToRemove = "公众号：马丁玩编程，回复：加群，添加马哥微信（备注：link）获取项目资料"; // 指定要删除的文本

        // 创建文件夹对象
        File folder = new File(folderPath);

        // 调用函数遍历文件夹并删除文本
        traverseFolderAndRemoveText(folder, textToRemove);
    }
}
