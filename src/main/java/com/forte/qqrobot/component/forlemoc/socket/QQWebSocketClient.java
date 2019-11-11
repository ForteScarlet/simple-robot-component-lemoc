package com.forte.qqrobot.component.forlemoc.socket;


import com.alibaba.fastjson.JSONObject;
import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.component.forlemoc.SocketResourceDispatchCenter;
import com.forte.qqrobot.component.forlemoc.beans.inforeturn.InfoReturn;
import com.forte.qqrobot.component.forlemoc.types.InfoReturnTypes;
import com.forte.qqrobot.component.forlemoc.types.LemocMsgGetTypes;
import com.forte.qqrobot.listener.invoker.ListenerManager;
import com.forte.qqrobot.log.QQLog;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * socket客户端连接
 *
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/6 18:08
 * @since JDK1.8
 **/
public class QQWebSocketClient extends WebSocketClient {

    //    /** socket送信器 */
    private final QQWebSocketMsgSender socketSender;

//    /** http送信器 */
//    private QQHttpMsgSender httpSender;

    /**
     * 监听函数管理器
     */
    private final ListenerManager manager;


    /**
     * 构造
     *
     * @param serverURI 父类{@link WebSocketClient}所需参数，用于连接
     */
    public QQWebSocketClient(URI serverURI, ListenerManager manager) {
        super(serverURI);
        this.manager = manager;
        this.socketSender = QQWebSocketMsgSender.build(this);
    }

    public QQWebSocketMsgSender getSocketSender(){
        return socketSender;
    }

    /**
     * 连接成功
     */
    @Override
    public final void onOpen(ServerHandshake serverHandshake) {
        onOpened(serverHandshake);
    }

    /**
     * 可重写的连接成功回调, 此时已经加载完初始化监听器
     */
    public void onOpened(ServerHandshake serverHandshake) {
    }


    /**
     * 接收消息，不可重写
     *
     * @param s 接收的消息
     */
    @Override
    public final void onMessage(String s) {
        //多线程接收消息
        ResourceDispatchCenter.getThreadPool().execute(() -> onMessaged(s));
    }

    /**
     * 处理接收到的消息
     *
     * @param s
     */
    private void onMessaged(String s) {
        //由于是socket连接，是LEMOC插件，接收消息必然存在act参数
        //接收到了消息，获取act编号
        Integer act = JSONObject.parseObject(s).getInteger("act");
//        String msg = JSONObject.parseObject(s).getString("msg");

        //判断信息类型
        if (act != 0) {
            //接收到的消息的封装类
            MsgGet msgGet = (MsgGet) LemocMsgGetTypes.getByAct(act).getBeanForJson(s);
            //组装参数
//            Object[] params = getParams(msgGet, msg);
//            boolean at = (boolean) params[2];
            /*
                获取封装类后，一，分发给监听器
            */
            manager.onMsg(msgGet, socketSender);
        } else {

            //如果act为0则说明这个消息是响应消息,转化并增加原生数据
            JSONObject returnInfoJson = JSONObject.parseObject(s).fluentPut("originalData", s);
            //获取返回码
            Integer returnCode = returnInfoJson.getInteger("return");
            Class<? extends InfoReturn> typeClass = InfoReturnTypes.getInfoReturnTypesByReturn(returnCode).getReturnClass();

            //封装为对象
            InfoReturn infoReturnBean = returnInfoJson.toJavaObject(typeClass);
            //更新对象
            SocketResourceDispatchCenter.getQQWebSocketInfoReturnManager().update(returnCode, infoReturnBean);
        }
    }

    /**
     * 连接关闭
     */
    @Override
    public final void onClose(int i, String s, boolean b) {
        //连接关闭回调
        onClosed(i, s, b);
    }

    /**
     * 可重写的连接关闭回调方法
     */
    public void onClosed(int i, String s, boolean b) {
        System.out.println("连接关闭！[" + i + "] " + s);
    }

    /**
     * 出现异常
     *
     * @param e 异常
     */
    @Override
    public final void onError(Exception e) {
        onErrored(e);
        //出现异常后尝试关闭连接
        try {
            this.close();
        } catch (Exception ignored) {
        }
    }

    /**
     * 可以重写的异常处理方法
     *
     * @param e 异常
     */
    public void onErrored(Exception e) {
        QQLog.debug("出现异常！");
        e.printStackTrace();
    }

    /**
     * 创建没有参数的送信器
     *
     * @return
     */
    private MsgSender createNoMethodSender() {
        return MsgSender.build(this.socketSender);
    }


}
