package org.example;

import java.io.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.printf("%04d%n", 11);
        String fileNameInput = "C:\\Users\\zzj\\Documents\\workspace\\Java\\maven-projects\\bilibili-list-test\\input\\MyBatis-list.txt";
        String fileNameOutput = "C:\\Users\\zzj\\Documents\\workspace\\Java\\maven-projects\\bilibili-list-test\\input\\MyBatis-list-out.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(fileNameInput))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.charAt(0) == 'P') {
                    String str3 = br.readLine();
                    String str2 = br.readLine();
                    //writeFileAppend(fileNameOutput, line + " - " + str2 + " - " + str3 + "\n");
                    writeFileAppend(fileNameOutput, line + "\t- " + str2 + " - " + str3 );
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public static void writeFileAppend(String fileName, String conent) {
        FileWriter fw = null;
        try {
//如果文件存在，则追加内容；如果文件不存在，则创建文件
            File f = new File(fileName);
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert fw != null;
        PrintWriter pw = new PrintWriter(fw);
        pw.println(conent);
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}