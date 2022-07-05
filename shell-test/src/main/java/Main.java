import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    /**
     * 运行shell脚本
     * @param shell
     */
    public static void execShell(String shell){
        try {
            Runtime.getRuntime().exec(shell);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 运行shell脚本 new String[]方式
     * @param shell
     */
    public static void execShellBin(String shell){
        try {
            Runtime.getRuntime().exec(new String[]{"/bin/sh","-c",shell},null,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 运行shell并获得结果，注意：如果sh中含有awk，一定要按 new String[]{"/bin/sh", "-c", shStr}写，才可以获得流
     * @param shStr
     * @return
     */
    public static List<String> runShell(String shStr){
        List<String> strList=new ArrayList<>();
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", shStr}, null, null);
            LineNumberReader input = new LineNumberReader(new InputStreamReader(process.getInputStream()));
            String line;
            process.waitFor();
            while ((line=input.readLine())!=null){
                strList.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strList;
    }

    public static void main(String[] args) {
        System.out.println(runShell("echo `hostname`"));
    }
}
