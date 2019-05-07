package com.forte.qqrobot.component.forlemoc;


import com.forte.qqrobot.BaseConfiguration;
import com.forte.qqrobot.component.forlemoc.socket.QQWebSocketClient;

/**
 * 连接前的配置类
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 15:24
 * @since JDK1.8
 **/
public final class LinkConfiguration extends BaseConfiguration {

    /** 连接地址，默认为localhost */
    private String linkIp = "localhost";

    /** 连接端口号，默认为25303 */
    private Integer port = 25303;


    /* ———————— 动态交互的参数为静态参数，保证参数存在 */

    /** 动态交互 HTTP API插件的监听端口 */
    private static Integer HTTP_API_port = 8877;

    /** 向http api请求数据的访问地址，默认为'/' */
    private static String HTTP_API_path = "/";

    /** HTTP API 插件的ip地址 */
    private static String HTTP_API_ip = "localhost";

    /** 获取HTTP API请求地址 */
    public static String getHttpRequestUrl(){
        String ip = HTTP_API_ip;
        Integer port = HTTP_API_port;
        String path = HTTP_API_path.startsWith("/") ? HTTP_API_path.length() > 1 ? HTTP_API_path : "" : "/" + HTTP_API_path;
        return "http://" + ip + ":" + port + path;
    }


    /** 连接用的客户端class对象，默认为{@link QQWebSocketClient} */
    private Class<? extends QQWebSocketClient> socketClient = QQWebSocketClient.class;

    /*  ————————————————  参数配置 ———————————————— */

    /** 获取连接的完整地址 */
    String getLinkUrl(){
        return "ws://"+ linkIp +":" + port;
    }



    /* —————————————— getter & setter —————————————— */

    public String getLinkIp() {
        return linkIp;
    }

    public void setLinkIp(String linkIp) {
        this.linkIp = linkIp;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    /** 获取连接实例，一般只会在Application启动器中使用 */
    Class<? extends QQWebSocketClient> getSocketClient() {
        return socketClient;
    }

    public void setSocketClient(Class<? extends QQWebSocketClient> socketClient) {
        this.socketClient = socketClient;
    }

    public static Integer getHTTP_API_port() {
        return HTTP_API_port;
    }

    public void setHTTP_API_port(Integer HTTP_API_port) {
        LinkConfiguration.HTTP_API_port = HTTP_API_port;
    }

    public static String getHTTP_API_ip() {
        return HTTP_API_ip;
    }

    public void setHTTP_API_ip(String HTTP_API_ip) {
        LinkConfiguration.HTTP_API_ip = HTTP_API_ip;
    }

    public static String getHTTP_API_path() {
        return HTTP_API_path;
    }

    public void setHTTP_API_path(String HTTP_API_path) {
        LinkConfiguration.HTTP_API_path = HTTP_API_path;
    }

}
