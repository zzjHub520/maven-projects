import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    /**
     * 运行shell脚本
     *
     * @param shell
     */
    public static void execShell(String shell) {
        try {
            Runtime.getRuntime().exec(shell);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 运行shell脚本 new String[]方式
     *
     * @param shell
     */
    public static void execShellBin(String shell) {
        try {
            Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", shell}, null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 运行shell并获得结果，注意：如果sh中含有awk，一定要按 new String[]{"/bin/sh", "-c", shStr}写，才可以获得流
     *
     * @param shStr
     * @return
     */
    public static List<String> runLinuxShell(String shStr) {
        List<String> strList = new ArrayList<>();
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", shStr}, null, null);
            LineNumberReader input = new LineNumberReader(new InputStreamReader(process.getInputStream()));
            String line;
            process.waitFor();
            while ((line = input.readLine()) != null) {
                strList.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strList;
    }

    private static void writeFileWriter(String fileName, String contents) throws IOException {
        try (FileWriter fw = new FileWriter(fileName)) {
            fw.write(contents);
        }
    }

    public static void main(String[] args) throws IOException {
        String sb = "#!/bin/bash\n" +
                "echo \"hhhhhhhhhhffff\" \n";
        writeFileWriter("./test.sh", sb);
        System.out.println(runShell("sh ./test.sh").get(0));
        runShell("rm -rf ./test.sh ");
    }

    public static List<String> runShell(String shStr) {
        List<String> strList = null;

        String os = System.getProperty("os.name");
        //Windows操作系统
        if (os != null && os.toLowerCase().startsWith("windows")) {
            System.out.println(String.format("当前系统版本是:%s", os));
            strList = runGitShell(shStr);
        } else if (os != null && os.toLowerCase().startsWith("linux")) {//Linux操作系统
            System.out.println(String.format("当前系统版本是:%s", os));
            strList = runLinuxShell(shStr);
        } else { //其它操作系统
            System.out.println(String.format("当前系统版本是:%s, runShell不支持该系统", os));
            System.exit(1);
        }
        return strList;
    }

    public static List<String> runGitShell(String command) {
        List<String> strList = new ArrayList<>();
        String path_bash = "C:/Program Files/Git/git-bash.exe";
        String path_file_output_git_bash = "./Output.txt";
        command += " | awk '{print $0}' > " + path_file_output_git_bash;
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command(path_bash, "-c", command);
            Process process = processBuilder.start();
            process.waitFor();
            File myObj = new File(path_file_output_git_bash);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                strList.add(myReader.nextLine());
            }
            myReader.close();
        } catch (IOException | InterruptedException e) {
            System.out.println(" --- Interruption in RunCommand: " + e);
            // Restore interrupted state
            Thread.currentThread().interrupt();
        }
        return strList;
    }
}
