package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {
    /**
     * 传入txt路径读取txt文件
     *
     * @param txtPath
     * @return 返回读取到的内容
     */
    public static List<String> readTxt(String txtPath) {
        List<String> strList = new ArrayList<>();
        File file = new File(txtPath);
        if (file.isFile() && file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                //StringBuffer sb = new StringBuffer();
                String text = null;
                while ((text = bufferedReader.readLine()) != null) {
                    //sb.append(text+"\n");
                    strList.add(text);
                }
                return strList;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 使用FileOutputStream来写入txt文件
     *
     * @param txtPath txt文件路径
     * @param content 需要写入的文本
     */
    public static void writeTxt(String txtPath, String content) {
        FileOutputStream fileOutputStream = null;
        File file = new File(txtPath);
        try {
            if (file.exists()) {
                //判断文件是否存在，如果不存在就新建一个txt
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String fileNameStr = "C:\\Users\\zzj\\Documents\\workspace\\bigdata\\Java\\MyBatis-course\\尚硅谷2022版MyBatis教程\\MyBatis-list.txt";

        StringBuffer sb = new StringBuffer();
        File file = new File(fileNameStr);
        if (file.isFile() && file.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String text = null;
                while ((text = bufferedReader.readLine()) != null) {
                    System.out.println(text);
                    if (text.charAt(0) == 'P') {
                        String str1 = text;
                        String str3 = bufferedReader.readLine();
                        String str2 = bufferedReader.readLine();
                        sb.append(str1 + " - " + str2 + " - " + str3 + "\n");
                        System.out.println("----------------");
                        System.out.println(sb);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //System.out.println(sb);
    }
}