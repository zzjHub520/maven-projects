package org.example;

public class Main {
    public static void main(String[] args) {
//        String sync = OkHttpUtils.builder().url("http://localhost:8080/hello")
//                .addHeader("token", "xxxxxxx")
//                .addParam("name", "xxx").addParam("pass", "xxx")
//                .get()
//                .sync();
//        System.out.println(sync);
        String sync = OkHttpUtils.builder().url("https://www.baidu.com")
//                .addHeader("token", "xxxxxxx")
//                .addParam("name", "xxx").addParam("pass", "xxx")
                .get()
                .sync();
        System.out.println(sync);

    }
}