package com.forte.qqrobot.component.forlemoc;

import com.forte.qqrobot.component.forlemoc.socket.QQWebSocketClient;
import com.forte.qqrobot.component.forlemoc.socket.QQWebSocketMsgSender;

/**
 * Lemoc组件的特殊API
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
public class LemocSpecialApi {

    /**
     * 送信器，将会在连接成功后的after方法中用于构建MsgSender
     */
    private final QQWebSocketMsgSender sender;

    /** 与服务端的连接 */
    private final QQWebSocketClient qqWebSocketClient;

    public LemocSpecialApi(QQWebSocketMsgSender sender, QQWebSocketClient qqWebSocketClient) {
        this.sender = sender;
        this.qqWebSocketClient = qqWebSocketClient;
    }

    public QQWebSocketMsgSender getSender() {
        return sender;
    }

    public QQWebSocketClient getQqWebSocketClient() {
        return qqWebSocketClient;
    }
}
