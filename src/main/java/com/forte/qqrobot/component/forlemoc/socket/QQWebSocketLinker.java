package com.forte.qqrobot.component.forlemoc.socket;

import com.forte.qqrobot.component.forlemoc.LinkConfiguration;
import com.forte.qqrobot.component.forlemoc.SocketResourceDispatchCenter;
import com.forte.qqrobot.exception.RobotRuntionException;
import com.forte.qqrobot.listener.invoker.ListenerManager;
import com.forte.qqrobot.log.QQLog;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

/**
 * QQWebSocket连接器
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 10:07
 * @since JDK1.8
 **/
public class QQWebSocketLinker {


    private static final String LOCAL_IP_WITH_HEAD = "ws://localhost:";

    /**
     * 连接qqwebsocket
     *
     * @param url       连接地址
     * @param retryTime 重试次数，如果小于0则视为无限循环
     * @return
     */
    public static QQWebSocketClient link(Class<? extends QQWebSocketClient> client, ListenerManager manager, String url, int retryTime) {
        QQWebSocketClient cc = null;

        //获取连接配置
        LinkConfiguration linkConfiguration = SocketResourceDispatchCenter.getLinkConfiguration();
        int times = 0;
//        boolean localB = false;
        boolean localB = false;
        //是否连接成功
        boolean success = false;
        //连接的时候先尝试一次本地连接
        try {
            QQLog.info("尝试本地连接...");
            //准备参数
            Object[] localParams = new Object[]{
                    new URI(LOCAL_IP_WITH_HEAD + linkConfiguration.getPort()),
                    manager
            };
            cc = client.getConstructor(URI.class, ListenerManager.class).newInstance(localParams);
            localB = cc.connectBlocking();
        } catch (Exception e) {
            QQLog.debug("本地连接失败：" + e.getMessage());
        }

        // 如果本地连接成功，直接返回
        if (cc != null || localB) {
            QQLog.info("本地连接成功");
            return cc;
        }

        //参数需要：URI、QQWebSocketMsgSender sender
        Object[] params;
        try {
            params = new Object[]{
                    new URI(url),
                    manager
            };
        } catch (URISyntaxException e) {
            throw new RobotRuntionException("连接参数构建出现异常！", e);
        }

        //如果本地连接失败，正常连接
        while (true) {
            try {

                cc = client.getConstructor(URI.class, ListenerManager.class).newInstance(params);
                QQLog.info("连接阻塞中...");
                success = cc.connectBlocking();
                QQLog.info(success ? "连接成功" : "连接失败");
                if (success) {
                    //如果成功，跳出无限连接循环
                    break;
                } else {
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                QQLog.debug("连接出现异常");
                e.printStackTrace();
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException ignore) {
                }
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                //这里是利用反射创建实例的错误，当出现这个错误的时候不能再循环了，直接抛出异常
                throw new RuntimeException(e);
            }
                times++;
                //如果重试次数超过设定次数，跳出循环
                //只有重试次数不为负数的时候生效
                if (retryTime >= 0 && times >= retryTime) {
                    break;
                }
        }

        //循环结束判断是否连接成功
        if(localB || success){
            //如果成功，调用成功回调并返回连接
            linkSuccess();
            return cc;
        }else{
            //如果失败，抛出异常
            throw new RobotRuntionException("连接失败");
        }

    }

    /**
     * 连接qqwebsocket,无限循环连接
     *
     * @param url 连接地址
     * @return
     */
    public static QQWebSocketClient link(Class<? extends QQWebSocketClient> client, ListenerManager manager, String url) {
        return link(client, manager, url, -1);
    }

    /**
     * 连接qqwebsocket,无限循环连接
     *
     * @param url 连接地址
     * @return
     */
    public static QQWebSocketClient link(ListenerManager manager, String url) {
        return link(QQWebSocketClient.class, manager, url, -1);
    }


    /**
     * *****************************************
     * **        连接成功回调方法
     * **
     * ******************************************
     *
     * @date 2019/3/26
     * @author ForteScarlet
     * ****************************************
     */
    private static void linkSuccess() {


    }


}
