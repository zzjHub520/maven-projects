### java http token请求代码实例

> 这篇文章主要介绍了java http token请求，文中通过示例代码介绍的非常详细，对大家的学习或者工作具有一定的参考学习价值，需要的朋友们下面随着小编来一起学习学习吧

本文实例为大家分享了java http token的具体代码，供大家参考，具体内容如下

```java
package com.monitoring.common.util;
 
import java.io.bufferedinputstream;
import java.io.bufferedreader;
import java.io.bytearrayoutputstream;
import java.io.ioexception;
import java.io.inputstream;
import java.io.inputstreamreader;
import java.io.outputstream;
import java.net.httpurlconnection;
import java.net.url;
import java.net.urlencoder;
import java.util.hashmap;
import java.util.iterator;
import java.util.list;
import java.util.map;
import java.util.set;
import java.util.regex.matcher;
import java.util.regex.pattern;
 
import org.apache.commons.httpclient.httpclient;
import org.apache.commons.httpclient.multithreadedhttpconnectionmanager;
import org.apache.commons.httpclient.namevaluepair;
import org.apache.commons.httpclient.methods.getmethod;
import org.apache.commons.httpclient.methods.postmethod;
import org.apache.commons.lang.stringutils;
import org.apache.log4j.logger;
import org.codehaus.jackson.jsonparseexception;
import org.codehaus.jackson.map.jsonmappingexception;
 
/**
 * 和http相关的操作
 *
 * @author
 *
 */
public class httputils {
 
    private static final logger logger = logger.getlogger(httputils.class);
    private final static multithreadedhttpconnectionmanager manager = new multithreadedhttpconnectionmanager();
    // 支持重复连接
    private static httpclient client = new httpclient(manager);
 
    /**
     *
     * @title: dopost
     * @description: post请求
     * @param requrl
     * @param parameters
     * @return string
     */
    public static string dopost(string requrl, map<string, string> parameters, string token) {
        httpurlconnection urlconn = null;
        try {
            urlconn = _sendpost(requrl, parameters, token);
            string responsecontent = _getcontent(urlconn);
            return responsecontent.trim();
        } finally {
            if (urlconn != null) {
                urlconn.disconnect();
            }
        }
    }
 
    /**
     *
     * @title: douploadfile
     * @description: 从网络上下载文件
     * @param requrl
     * @param parameters
     * @param fileparamname
     * @param filename
     * @param contenttype
     * @param data
     * @return string
     */
    public static string douploadfile(string requrl, map<string, string> parameters, string fileparamname,
            string filename, string contenttype, byte[] data) {
        httpurlconnection urlconn = null;
        try {
            urlconn = _sendformdata(requrl, parameters, fileparamname, filename, contenttype, data);
            string responsecontent = new string(_getbytes(urlconn));
            return responsecontent.trim();
        } catch (exception e) {
            throw new runtimeexception(e.getmessage(), e);
        } finally {
            if (urlconn != null) {
                urlconn.disconnect();
            }
        }
    }
 
    private static httpurlconnection _sendformdata(string requrl, map<string, string> parameters, string fileparamname,
            string filename, string contenttype, byte[] data) {
        httpurlconnection urlconn = null;
        try {
            url url = new url(requrl);
            urlconn = (httpurlconnection) url.openconnection();
            urlconn.setrequestmethod("post");
            urlconn.setconnecttimeout(5000);// （单位：毫秒）jdk
            urlconn.setreadtimeout(5000);// （单位：毫秒）jdk 1.5换成这个,读操作超时
            urlconn.setdooutput(true);
 
            urlconn.setrequestproperty("connection", "keep-alive");
 
            string boundary = "-----------------------------114975832116442893661388290519"; // 分隔符
            urlconn.setrequestproperty("content-type", "multipart/form-data; boundary=" + boundary);
 
            boundary = "--" + boundary;
            stringbuffer params = new stringbuffer();
            if (parameters != null) {
                for (iterator<string> iter = parameters.keyset().iterator(); iter.hasnext();) {
                    string name = iter.next();
                    string value = parameters.get(name);
                    params.append(boundary + "\r\n");
                    params.append("content-disposition: form-data; name=\"" + name + "\"\r\n\r\n");
                    // params.append(urlencoder.encode(value, "utf-8"));
                    params.append(value);
                    params.append("\r\n");
                }
            }
 
            stringbuilder sb = new stringbuilder();
            // sb.append("\r\n");
            sb.append(boundary);
            sb.append("\r\n");
            sb.append("content-disposition: form-data; name=\"" + fileparamname + "\"; filename=\"" + filename
                    + "\"\r\n");
            sb.append("content-type: " + contenttype + "\r\n\r\n");
            byte[] filediv = sb.tostring().getbytes();
            byte[] enddata = ("\r\n" + boundary + "--\r\n").getbytes();
            byte[] ps = params.tostring().getbytes();
 
            outputstream os = urlconn.getoutputstream();
            os.write(ps);
            os.write(filediv);
            os.write(data);
            os.write(enddata);
 
            os.flush();
            os.close();
        } catch (exception e) {
            throw new runtimeexception(e.getmessage(), e);
        }
        return urlconn;
    }
 
    private static string _getcontent(httpurlconnection urlconn) {
        try {
            string responsecontent = null;
            inputstream in = urlconn.getinputstream();
            bufferedreader rd = new bufferedreader(new inputstreamreader(in, "utf-8"));
            string templine = rd.readline();
            stringbuffer tempstr = new stringbuffer();
            string crlf = system.getproperty("line.separator");
            while (templine != null) {
                tempstr.append(templine);
                tempstr.append(crlf);
                templine = rd.readline();
            }
            responsecontent = tempstr.tostring();
            rd.close();
            in.close();
            return responsecontent;
        } catch (exception e) {
            throw new runtimeexception(e.getmessage(), e);
        }
    }
 
    private static byte[] _getbytes(httpurlconnection urlconn) {
        try {
            inputstream in = urlconn.getinputstream();
            bytearrayoutputstream os = new bytearrayoutputstream();
            byte[] buf = new byte[1024];
            for (int i = 0; (i = in.read(buf)) > 0;)
                os.write(buf, 0, i);
            in.close();
            return os.tobytearray();
        } catch (exception e) {
            throw new runtimeexception(e.getmessage(), e);
        }
    }
 
    private static httpurlconnection _sendpost(string requrl, map<string, string> parameters, string token) {
        httpurlconnection urlconn = null;
        try {
            stringbuffer params = new stringbuffer();
            if (parameters != null) {
                for (iterator<string> iter = parameters.keyset().iterator(); iter.hasnext();) {
                    string name = iter.next();
                    string value = parameters.get(name);
                    params.append(name + "=");
                    params.append(urlencoder.encode(value, "utf-8"));
                    if (iter.hasnext())
                        params.append("&");
                }
            }
 
            url url = new url(requrl);
            urlconn = (httpurlconnection) url.openconnection();
            
            if(stringutils.isnotblank(token)) {
                urlconn.addrequestproperty("token", token);
            }
            
            urlconn.setrequestmethod("post");
            urlconn.setconnecttimeout(5000);// （单位：毫秒）jdk
            urlconn.setreadtimeout(5000);// （单位：毫秒）jdk 1.5换成这个,读操作超时
            urlconn.setdooutput(true);
            byte[] b = params.tostring().getbytes();
            urlconn.getoutputstream().write(b, 0, b.length);
            urlconn.getoutputstream().flush();
            urlconn.getoutputstream().close();
        } catch (exception e) {
            throw new runtimeexception(e.getmessage(), e);
        }
        return urlconn;
    }
 
    /**
     * 发送get请求
     *
     * @param link
     * @param charset
     * @return
     */
    public static string doget(string link, string charset, string token) {
        httpurlconnection conn = null;
        try {
            url url = new url(link);
            conn = (httpurlconnection) url.openconnection();
            
            
            conn.setrequestmethod("get");
            
            conn.setrequestproperty("user-agent", "mozilla/5.0");
            
            if(stringutils.isnotempty(token)) {
                conn.addrequestproperty("token", token);
            }
            
            bufferedinputstream in = new bufferedinputstream(conn.getinputstream());
            bytearrayoutputstream out = new bytearrayoutputstream();
            byte[] buf = new byte[1024];
            for (int i = 0; (i = in.read(buf)) > 0;) {
                out.write(buf, 0, i);
            }
            out.flush();
            out.close();
            string s = new string(out.tobytearray(), charset);
            return s;
        } catch (exception e) {
            throw new runtimeexception(e.getmessage(), e);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
 
    /**
     * utf-8编码
     *
     * @param link
     * @return
     */
    public static string doget(string link, string token) {
        return doget(link, "utf-8", token);
    }
 
    /**
     *
     * @title: getintresponse
     * @description: 发送get请求
     * @param link
     * @return int
     */
    public static int getintresponse(string link, string token) {
        string str = doget(link, token);
        return integer.parseint(str.trim());
    }
 
    public static long ip2long(string strip) {
        long[] ip = new long[4];
        // 先找到ip地址字符串中.的位置
        int position1 = strip.indexof(".");
        int position2 = strip.indexof(".", position1 + 1);
        int position3 = strip.indexof(".", position2 + 1);
        // 将每个.之间的字符串转换成整型
        ip[0] = long.parselong(strip.substring(0, position1));
        ip[1] = long.parselong(strip.substring(position1 + 1, position2));
        ip[2] = long.parselong(strip.substring(position2 + 1, position3));
        ip[3] = long.parselong(strip.substring(position3 + 1));
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }
 
    // 将10进制整数形式转换成127.0.0.1形式的ip地址
    public static string long2ip(long longip) {
        stringbuffer sb = new stringbuffer("");
        // 直接右移24位
        sb.append(string.valueof(longip >>> 24));
        sb.append(".");
        // 将高8位置0，然后右移16位
        sb.append(string.valueof((longip & 0x00ffffff) >>> 16));
        sb.append(".");
        sb.append(string.valueof((longip & 0x0000ffff) >>> 8));
        sb.append(".");
        sb.append(string.valueof(longip & 0x000000ff));
        return sb.tostring();
    }
 
    /**
     *
     * urlparse:url解析. <br/>
     *
     * @author majun
     * @param arrlist
     * @param url
     * @return
     * @since jdk 1.6
     */
    public static map<string, string> urlparse(list<string> arrlist, string url) {
 
        map<string, string> vaulesmap = new hashmap<string, string>();
        for (string s : arrlist) {
            pattern pattern = pattern.compile(s + "=([^&]*)(&|$)");
            matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                string[] arr = matcher.group(1).split("'");
                vaulesmap.put(s, arr[1]);
            }
        }
        return vaulesmap;
 
    }
 
    /***
     *
     * http_dopost: httpclient发送post 请求. <br/>
     *
     * @author majun
     * @version 创建时间：2016年6月22日 下午6:15:59
     * @since jdk 1.6
     */
    public static string http_dopost(string requrl, map<string, string> parameters, string usertoken) {
 
        try {
 
            // multithreadedhttpconnectionmanager manager = new multithreadedhttpconnectionmanager();
            // 支持重复连接
            // httpclient client = new httpclient(manager);
 
            postmethod post = new postmethod(requrl);
            post.setrequestheader("connection", "keep-alive");
            post.setrequestheader("content-type", "application/x-www-form-urlencoded");
 
            namevaluepair[] params = new namevaluepair[parameters.size()];
            set<string> keys = parameters.keyset();
            int index = 0;
            for (string key : keys) {
 
                params[index] = new namevaluepair(key, parameters.get(key));
                index++;
            }
 
            post.setquerystring(params);
 
            if (stringutils.isnotblank(usertoken)) {
                post.setrequestheader("usertoken", usertoken);
            }
 
            integer status = client.executemethod(post);
            logger.info("loginstatus:" + status);
 
            string body = post.getresponsebodyasstring();
 
            return body;
 
        } catch (jsonparseexception e) {
            e.printstacktrace();
        } catch (jsonmappingexception e) {
            e.printstacktrace();
        } catch (ioexception e) {
            e.printstacktrace();
        }
        return null;
    }
 
    /***
     *
     * http_doget: httpclient发送get请求. <br/>
     *
     * @author majun
     * @version 创建时间：2016年6月22日 下午6:25:10
     * @since jdk 1.6
     */
    public static string http_doget(string requrl, map<string, string> parameters, string usertoken) {
 
        try {
 
            // multithreadedhttpconnectionmanager manager = new multithreadedhttpconnectionmanager();
            // 支持重复连接
            // httpclient client = new httpclient(manager);
            getmethod get = new getmethod(requrl);
 
            namevaluepair[] params = new namevaluepair[parameters.size()];
            set<string> keys = parameters.keyset();
            int index = 0;
            for (string key : keys) {
 
                params[index] = new namevaluepair(key, parameters.get(key));
                index++;
            }
 
            get.setquerystring(params);
 
            if (stringutils.isnotblank(usertoken)) {
                get.setrequestheader("usertoken", usertoken);
            }
 
            integer status = client.executemethod(get);
            logger.info("http_doget==>status:" + status);
            string body = get.getresponsebodyasstring();
            return body;
 
        } catch (exception e) {
            e.printstacktrace();
        }
 
        return null;
    }
}
```

以上所述是小编给大家介绍的java http token请求详解整合，希望对大家有所帮助，如果大家有任何疑问请给我留言，小编会及时回复大家的。在此也非常感谢大家对服务器之家网站的支持！

原文链接：https://blog.csdn.net/qq_33212500/article/details/79229897