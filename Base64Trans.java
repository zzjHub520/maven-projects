import java.io.IOException;
import java.nio.file.*;
import java.util.Base64;

import static java.lang.System.out;

public class Base64Trans {

    /**
     * 从图片文件中读取内容。
     * @param path 图片文件的路径。
     * @return 二进制图片内容的byte数组。
     *
     */
    private byte[] readFile(Path path) {
        byte[] imageContents = null;

        try {
            imageContents = Files.readAllBytes(path);
        } catch (IOException e) {
            out.println("读取文件出错了...~zZ");
        }

        return imageContents;
    }

    /**
     * 编码图片文件，编码内容输出为{@code String}格式。
     * @param imageContents 二进制图片内容的byte数组。
     * @return {@code String}格式的编码内容。
     */
    private String base64Encoding(byte[] imageContents) {
        if(imageContents != null)
            return Base64.getEncoder().encodeToString(imageContents);
        else return null;
    }

    /**
     * 解码图片文件。
     * @param imageContents 待解码的图片文件的字符串格式。
     * @return 解码后图片文件的二进制内容。
     */
    private byte[] base64Decoding(String imageContents) {
        if(imageContents != null)
            return Base64.getDecoder().decode(imageContents);
        else return null;
    }

    /**
     * 将解码后的二进制内容写入文件中。
     * @param path 写入的路径。
     * @param imageContents 解码后的二进制内容。
     */
    private void writeFile(Path path, byte[] imageContents) {
        if(imageContents != null)
            try {
                Files.write(path, imageContents, StandardOpenOption.CREATE);
            } catch (IOException e) {
                out.println("写入文件出错了...~zZ");
            }
    }

    public String imageToBase64(String filePath){
        byte[] bytes = readFile(Paths.get(filePath));
        return base64Encoding(bytes);
    }

    public void base64ToImage(String filePath, String encodingString){
        writeFile(Paths.get(filePath),base64Decoding(encodingString));
    }

    public static void main(String[] args) {
        Base64Trans bt = new Base64Trans();
        String encodingString = bt.imageToBase64("111.png");

        out.println("二进制图片文件Base64码：" + encodingString);

        bt.base64ToImage("222.png",encodingString);

        out.println("任务结束...");
    }
}