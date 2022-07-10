import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BB {

    public static void main(String args[]){
        try {
            String name = getHostNameByNbtstat("192.168.12.12");
            System.out.println("name="+name);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //	 得到遠程IP的機器名
    public static String getHostNameByNbtstat(String clientIP){
        String s = "";
        String sb = clientIP.trim().substring(0, 3);
        //System.out.println("clientIP="+clientIP+"\t"+"截取字符串為："+sb);
        if(sb.equals("127")){
            //System.out.println("是127.0.0.1");
            s = getHostName();
        }else{
            //System.out.println("不是本地ip");
            try {
                String s1 = "nbtstat -a "+clientIP;
                Process process = Runtime.getRuntime().exec(s1);
                BufferedReader bufferedreader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()));
                String nextLine;
                int y = 0;
                int x = 0;
                for (String line = bufferedreader.readLine(); line != null; line = nextLine) {
                    nextLine = bufferedreader.readLine();
                    //System.out.println("y= "+y+" nextLine="+nextLine);
                    if(y==13){
                        //System.out.println("此行：-----------------");
                        //System.out.println(nextLine.indexOf("<00>")+"--------");
                        s = (nextLine.substring(0, (nextLine.indexOf("<00>")))).toLowerCase();//截取字符串
                    }
                    y++;
                }
                bufferedreader.close();
                process.waitFor();
            }catch(Exception exception) {
                s = "";
            }
        }
        return s.trim();
    }
    //	 得到本地IP的機器名
    public static String getHostName() {
        String s = "";
        try {
            String s1 = "ipconfig /all";
            Process process = Runtime.getRuntime().exec(s1);
            BufferedReader bufferedreader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String nextLine;
            for (String line = bufferedreader.readLine(); line != null; line = nextLine) {
                nextLine = bufferedreader.readLine();
                if (line.indexOf("Host Name") <= 0) {
                    continue;
                }
                int i = line.indexOf("Host Name") + 36;
                s = line.substring(i);
                break;
            }
            bufferedreader.close();
            process.waitFor();
        } catch (Exception exception) {
            s = "";
        }
        return s.trim();
    }

}
