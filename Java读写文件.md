## 一.字符流：读写纯文本（txt，csv等）



### 1 字符流写文件主要用：FileWriter，BufferedWriter，PrintWriter

#### 1.1 测试 FileWriter 写入

```java
private void writeFileWriter() throws IOException {
    try (FileWriter fw = new FileWriter(basicPath + "writeUnicode_FileWriter.txt")) {
        fw.write("测试 FileWriter 写入。");
    }
}
```

#### 1.2 测试 BufferedWriter 写入

```java
private void writeBufferedWriter() throws IOException {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(basicPath + "writeUnicode_BufferedWriter.txt"))) {
        bw.write("测试 BufferedWriter 写入。");
    }
}
```

#### 1.3 测试 PrintWriter 写入

```java
private void writePrintWriter() throws IOException {
    try (PrintWriter pw = new PrintWriter(new FileWriter(basicPath + "writeUnicode_PrintWriter.txt"))) {
        pw.write("测试 PrintWriter 写入。");
    }
}
```

### 2 字符流读文件主要用：FileReader，BufferedReader

#### 2.1 测试 FileReader 读取

```java
private void readFileReader() throws IOException {
    // 方式1：一个一个char读取 （不推荐）
    try (FileReader fr = new FileReader(basicPath + "writeUnicode_FileWriter.txt")) {
        int ch;
        while ((ch = fr.read()) != -1) {
            System.out.print((char) ch);
        }
    }

    // 方式2：数组自定长度一次性读取
    try (FileReader fr = new FileReader(basicPath + "writeUnicode_FileWriter.txt")) {
        char[] buf = new char[1024];
        int length;
        while ((length = fr.read(buf)) != -1) {
            String str = new String(buf, 0, length);
            System.out.print(str);
        }
    }
}
```

#### 2.2测试 BufferedReader 读取

```java
private void readBufferedReader() throws IOException {
    // 方式1：一个一个char读取 （不推荐）
    try (BufferedReader br = new BufferedReader(new FileReader(basicPath + "writeUnicode_BufferedWriter.txt"))) {
        int c;
        while ((c = br.read()) != -1) {
            System.out.print((char) c);
        }
    }

    // 方式2：数组自定长度一次性读取
    try (BufferedReader br = new BufferedReader(new FileReader(basicPath + "writeUnicode_BufferedWriter.txt"))) {
        char[] buf = new char[1024];
        int length;
        while ((length = br.read(buf)) != -1) {
            String str = new String(buf, 0, length);
            System.out.print(str);
        }
    }

    // 方式3：bufferedReader.readLine()读取
    try (BufferedReader br = new BufferedReader(new FileReader(basicPath + "writeUnicode_BufferedWriter.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            System.out.print(line);
        }
    }
}
```

 

## 二.字节流：读取视频，音频，二进制文件等，（文本文件也可以但不推荐，以下仅为测试用）

### 1 字节流写文件主要用：FileOutputStream，BufferedOutputStream[#](https://www.cnblogs.com/convict/p/14177729.html#1-字节流写文件主要用：fileoutputstream，bufferedoutputstream)

#### 1.1 测试 FileOutputStream 写入

```java
private void writeFileOutputStream() throws IOException {
    try (FileOutputStream fos = new FileOutputStream(basicPath + "writeByte_FileOutputStream.txt")) {
        String content = "测试 FileOutputStream 写入。";
        byte[] bytes = content.getBytes();
        fos.write(bytes);
    }
}
```



#### 1.2 测试 BufferedOutputStream 写入

```java
private void writeBufferedOutputStream() throws IOException {
    try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(basicPath + "writeByte_BufferedOutputStream.txt"))) {
        String content = "测试 BufferedOutputStream 写入。";
        byte[] bytes = content.getBytes();
        bos.write(bytes);
    }
}
```



### 2 字节流读文件主要用：FileInputStream，BufferedInputStream

#### 2.1 测试 FileInputStream 读取

```java
private void readFileInputStream() throws IOException {
    // 方式1：一个一个char读取 （不推荐，且中文占2个字节，此方式读中文文件会造成乱码）
    try (FileInputStream fis = new FileInputStream(basicPath + "writeByte_FileOutputStream.txt")) {
        int ch;
        while ((ch = fis.read()) != -1) {
            System.out.print((char) ch);
        }
    }

    // 方式2：数组自定长度一次性读取
    try (FileInputStream fis = new FileInputStream(basicPath + "writeByte_FileOutputStream.txt")) {
        byte[] buf = new byte[1024];
        int length;
        while ((length = fis.read(buf)) != -1) {
            String str = new String(buf, 0, length);
            System.out.print(str);
        }
    }
}
```

#### 2.2 测试 BufferedInputStream 读取

```java
private void readBufferedInputStream() throws IOException {
    // 方式1：一个一个char读取 （不推荐，且中文占2个字节，此方式读中文文件会造成乱码）
    try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(basicPath + "writeByte_BufferedOutputStream.txt"))) {
        int c;
        while ((c = bis.read()) != -1) {
            System.out.print((char) c);
        }
    }

    // 方式2：数组自定长度一次性读取
    try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(basicPath + "writeByte_BufferedOutputStream.txt"))) {
        byte[] buf = new byte[1024];
        int length;
        while ((length = bis.read(buf)) != -1) {
            String str = new String(buf, 0, length);
            System.out.print(str);
        }
    }
}
```

 

## 三.通过 Files类读写文件

### 1 测试 Files类写入

```java
private void writeFiles() throws IOException {
    String content = "测试 Files 类写入。\n第二行";
    Files.write(Paths.get(basicPath + "writeFiles.txt"), content.getBytes());
}
```

### 2 测试 Files类读取

```java
private void readFiles() throws IOException {
    // 方式1 （文件特大时会占满内存）
    byte[] bytes = Files.readAllBytes(Paths.get(basicPath + "writeFiles.txt"));
    String srcStr1 = new String(bytes);
    System.out.println(srcStr1);

    // 方式2 （文件特大时会占满内存）
    List<String> lines = Files.readAllLines(Paths.get(basicPath + "writeFiles.txt"));
    StringBuilder sb = new StringBuilder();
    for (String line : lines) {
        // 此时line最后没有换行，因为readAllLines以换行分隔了所有行，可以用System.out.print 看到效果
        sb.append(line).append("\n");
    }
    String srcStr2 = sb.toString();
    System.out.println(srcStr2);

    // 方式3 JDK8的Stream流，边消费边读取
    String srcStr3 = Files.lines(Paths.get(basicPath + "writeFiles.txt")).reduce((s1, s2) -> s1 + s2).get();
    System.out.println(srcStr3);
}
```



## 四.源码

### 1 字符流读写

```java
package com.writefiles;

import java.io.*;

/**
 * @author: Convict.Yellow
 * @date: 2020/12/22 16:11
 * @description: 字符流的基本单位为 Unicode，大小为两个字节（Byte），主要用来处理文本数据。
 * 字符流有两个基类：Reader（输入字符流）和 Writer（输出字符流）。
 * <p>
 * 字符流写文件主要用：FileWriter，BufferedWriter，PrintWriter
 * 字符流读文件主要用：FileReader，BufferedReader
 */
public class WriteAndReadByUnicode {

    private static final String basicPath = "D:/ztest/";

    public static void main(String[] args) throws IOException {
        WriteAndReadByUnicode entrance = new WriteAndReadByUnicode();

        // 测试 FileWriter 写入
        entrance.writeFileWriter();
        // 测试 BufferedWriter 写入
        entrance.writeBufferedWriter();
        // 测试 PrintWriter 写入
        entrance.writePrintWriter();

        // 测试 FileReader 读取
        entrance.readFileReader();
        // 测试 BufferedReader 读取
        entrance.readBufferedReader();
    }

    private void writeFileWriter() throws IOException {
        try (FileWriter fw = new FileWriter(basicPath + "writeUnicode_FileWriter.txt")) {
            fw.write("测试 FileWriter 写入。");
        }
    }

    private void writeBufferedWriter() throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(basicPath + "writeUnicode_BufferedWriter.txt"))) {
            bw.write("测试 BufferedWriter 写入。");
        }
    }

    private void writePrintWriter() throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(basicPath + "writeUnicode_PrintWriter.txt"))) {
            pw.write("测试 PrintWriter 写入。");
        }
    }

    private void readFileReader() throws IOException {
        // 方式1：一个一个char读取 （不推荐）
        try (FileReader fr = new FileReader(basicPath + "writeUnicode_FileWriter.txt")) {
            int ch;
            while ((ch = fr.read()) != -1) {
                System.out.print((char) ch);
            }
        }

        // 方式2：数组自定长度一次性读取
        try (FileReader fr = new FileReader(basicPath + "writeUnicode_FileWriter.txt")) {
            char[] buf = new char[1024];
            int length;
            while ((length = fr.read(buf)) != -1) {
                String str = new String(buf, 0, length);
                System.out.print(str);
            }
        }
    }

    private void readBufferedReader() throws IOException {
        // 方式1：一个一个char读取 （不推荐）
        try (BufferedReader br = new BufferedReader(new FileReader(basicPath + "writeUnicode_BufferedWriter.txt"))) {
            int c;
            while ((c = br.read()) != -1) {
                System.out.print((char) c);
            }
        }

        // 方式2：数组自定长度一次性读取
        try (BufferedReader br = new BufferedReader(new FileReader(basicPath + "writeUnicode_BufferedWriter.txt"))) {
            char[] buf = new char[1024];
            int length;
            while ((length = br.read(buf)) != -1) {
                String str = new String(buf, 0, length);
                System.out.print(str);
            }
        }

        // 方式3：bufferedReader.readLine()读取
        try (BufferedReader br = new BufferedReader(new FileReader(basicPath + "writeUnicode_BufferedWriter.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.print(line);
            }
        }
    }

}
```



### 2 字节流读写

```java
package com.writefiles;

import java.io.*;

/**
 * @author: Convict.Yellow
 * @date: 2020/12/22 15:27
 * @description: 字节流的基本单位为字节（Byte），一个字节为8位（bit），主要是用来处理二进制（数据）。
 * 字节流有两个基类：InputStream（输入字节流）和 OutputStream（输出字节流）。
 * <p>
 * 字节流写文件主要用：FileOutputStream，BufferedOutputStream
 * 字节流读文件主要用：FileInputStream，BufferedInputStream
 */
public class WriteAndReadByByte {

    private static final String basicPath = "D:/ztest/";

    public static void main(String[] args) throws IOException {
        WriteAndReadByByte entrance = new WriteAndReadByByte();

        // 测试 FileOutputStream 写入
        entrance.writeFileOutputStream();
        // 测试 BufferedOutputStream 写入
        entrance.writeBufferedOutputStream();

        // 测试 FileInputStream 读取
        entrance.readFileInputStream();
        // 测试 BufferedInputStream 读取
        entrance.readBufferedInputStream();

    }

    private void writeFileOutputStream() throws IOException {
        try (FileOutputStream fos = new FileOutputStream(basicPath + "writeByte_FileOutputStream.txt")) {
            String content = "测试 FileOutputStream 写入。";
            byte[] bytes = content.getBytes();
            fos.write(bytes);
        }
    }

    private void writeBufferedOutputStream() throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(basicPath + "writeByte_BufferedOutputStream.txt"))) {
            String content = "测试 BufferedOutputStream 写入。";
            byte[] bytes = content.getBytes();
            bos.write(bytes);
        }
    }

    private void readFileInputStream() throws IOException {
        // 方式1：一个一个char读取 （不推荐，且中文占2个字节，此方式读中文文件会造成乱码）
        try (FileInputStream fis = new FileInputStream(basicPath + "writeByte_FileOutputStream.txt")) {
            int ch;
            while ((ch = fis.read()) != -1) {
                System.out.print((char) ch);
            }
        }

        // 方式2：数组自定长度一次性读取
        try (FileInputStream fis = new FileInputStream(basicPath + "writeByte_FileOutputStream.txt")) {
            byte[] buf = new byte[1024];
            int length;
            while ((length = fis.read(buf)) != -1) {
                String str = new String(buf, 0, length);
                System.out.print(str);
            }
        }
    }

    private void readBufferedInputStream() throws IOException {
        // 方式1：一个一个char读取 （不推荐，且中文占2个字节，此方式读中文文件会造成乱码）
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(basicPath + "writeByte_BufferedOutputStream.txt"))) {
            int c;
            while ((c = bis.read()) != -1) {
                System.out.print((char) c);
            }
        }

        // 方式2：数组自定长度一次性读取
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(basicPath + "writeByte_BufferedOutputStream.txt"))) {
            byte[] buf = new byte[1024];
            int length;
            while ((length = bis.read(buf)) != -1) {
                String str = new String(buf, 0, length);
                System.out.print(str);
            }
        }
    }


}
```



### 3 Files类读写

```java
package com.writefiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author: Convict.Yellow
 * @date: 2020/12/22 16:11
 * @description:
 *
 */
public class WriteAndReadByFiles {

    private static final String basicPath = "D:/ztest/";

    public static void main(String[] args) throws IOException {
        WriteAndReadByFiles entrance = new WriteAndReadByFiles();

        // 测试 Files类写入
        entrance.writeFiles();

        // 测试 Files类读取
        entrance.readFiles();
    }

    private void writeFiles() throws IOException {
        String content = "测试 Files 类写入。\n第二行";
        Files.write(Paths.get(basicPath + "writeFiles.txt"), content.getBytes());
    }

    private void readFiles() throws IOException {
        // 方式1 （文件特大时会占满内存）
        byte[] bytes = Files.readAllBytes(Paths.get(basicPath + "writeFiles.txt"));
        String srcStr1 = new String(bytes);
        System.out.println(srcStr1);

        // 方式2 （文件特大时会占满内存）
        List<String> lines = Files.readAllLines(Paths.get(basicPath + "writeFiles.txt"));
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            // 此时line最后没有换行，因为readAllLines以换行分隔了所有行，可以用System.out.print 看到效果
            sb.append(line).append("\n");
        }
        String srcStr2 = sb.toString();
        System.out.println(srcStr2);

        // 方式3 JDK8的Stream流，边消费边读取
        String srcStr3 = Files.lines(Paths.get(basicPath + "writeFiles.txt")).reduce((s1, s2) -> s1 + s2).get();
        System.out.println(srcStr3);
    }

}
```

