import javax.naming.Context;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class CC {
    public static void main(String[] args) {

        try{

//使用域名创建对象

            InetAddress inet1 = InetAddress.getByName("www.163.com");

            System.out.println(inet1);

//使用IP创建对象

            InetAddress inet2 = InetAddress.getByName("127.0.0.1");

            System.out.println("asdf  "+ inet2);

//获得本机地址对象

            InetAddress inet3 = InetAddress.getLocalHost();

            System.out.println(inet3);

//获得对象中存储的域名

            String host = inet3.getHostName();

            System.out.println("域名：" + host);

//获得对象中存储的IP

            String ip = inet3.getHostAddress();

            System.out.println("IP:" + ip);

        }catch(Exception e){}

    }
}
