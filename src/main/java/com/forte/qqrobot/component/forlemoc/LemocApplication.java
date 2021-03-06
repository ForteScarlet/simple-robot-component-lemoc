package com.forte.qqrobot.component.forlemoc;

import com.forte.qqrobot.BaseApplication;
import com.forte.qqrobot.component.forlemoc.socket.*;
import com.forte.qqrobot.depend.DependCenter;
import com.forte.qqrobot.listener.invoker.ListenerManager;
import com.forte.qqrobot.log.QQLogBack;
import com.forte.qqrobot.sender.senderlist.SenderGetList;
import com.forte.qqrobot.sender.senderlist.SenderSendList;
import com.forte.qqrobot.sender.senderlist.SenderSetList;

import java.io.IOException;

/**
 * LEMOC 连接抽象类
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/6 18:10
 * @since JDK1.8
 **/
public class LemocApplication extends BaseApplication<LinkConfiguration, LemocSpecialApi> {

    /**
     * 送信器，将会在连接成功后的after方法中用于构建MsgSender
     */
    private QQWebSocketMsgSender sender;

    /** 与服务端的连接 */
    private QQWebSocketClient qqWebSocketClient;

    /** 特殊API对象 */
    private LemocSpecialApi spApi;

    @Override
    protected void resourceInit(LinkConfiguration linkConfiguration) {
    }

    /**
     * 资源初始化
     */
    @Override
    protected void resourceInit(){
        //将连接时的配置对象加入资源调度中心
        SocketResourceDispatchCenter.saveLinkConfiguration(new LinkConfiguration());
        //将QQWebSocketMsgCreator放入资源调度中心
        SocketResourceDispatchCenter.saveQQWebSocketMsgCreator(new QQJSONMsgCreator());

        //将QQWebSocketInfoReturnManager放入资源调度中心
        SocketResourceDispatchCenter.saveQQWebSocketInfoReturnManager(new QQWebSocketInfoReturnManager());
    }

    /**
     * 获取消息发送接口, 将会在连接成功后使用
     */
    @Override
    protected SenderSendList getSender() {
        return sender;
    }

    /**
     * 获取事件设置接口, 将会在连接成功后使用
     */
    @Override
    protected SenderSetList getSetter() {
        return sender;
    }

    /**
     * 获取资源获取接口, 将会在连接成功后使用
     */
    @Override
    protected SenderGetList getGetter() {
        return sender;
    }

    @Override
    public LemocSpecialApi getSpecialApi() {
        return spApi;
    }


    /**
     * 开发者实现的开始连接的方法
     */
    @Override
    protected String start(DependCenter dependCenter, ListenerManager manager) {
        // 连接socket
        qqWebSocketClient = linkSocket(manager);
        //保存送信器
        sender = qqWebSocketClient.getSocketSender();

        spApi = new LemocSpecialApi(sender, qqWebSocketClient);

        return "LEMOC连接";
    }

    /**
     * 获取Config的方法实例
     */
    @Override
    protected LinkConfiguration getConfiguration() {
        return SocketResourceDispatchCenter.getLinkConfiguration();
    }

    /**
     * 连接socket
     */
    private QQWebSocketClient linkSocket(ListenerManager manager){
        //连接配置对象
        LinkConfiguration configuration = getConfiguration();
        //主要的socket连接，如果无法结果精准获取请求后的返回值的话，后期会同时连接多个socket
        //由于这里用到了config对象，需要
        return QQWebSocketLinker.link(configuration.getSocketClient(),
                manager,
                configuration.getLinkUrl(),
                configuration.getRetryTime()
                );
    }


    /**
     * 关闭连接
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void close() {
        if(qqWebSocketClient == null){
            throw new NullPointerException("尚未连接！");
        }else{
            qqWebSocketClient.close();
        }
    }


    //**************** 构造 ****************//


    /**
     * 无参构造
     */
    public LemocApplication() {
    }

    /**
     * 日志拦截构造
     *
     * @param qqLogBack
     */
    public LemocApplication(QQLogBack qqLogBack) {
        super(qqLogBack);
    }
}
