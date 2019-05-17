package com.forte.qqrobot.component.forlemoc;

import com.forte.qqrobot.ResourceDispatchCenter;
import com.forte.qqrobot.component.forlemoc.socket.QQJSONMsgCreator;
import com.forte.qqrobot.component.forlemoc.socket.QQWebSocketInfoReturnManager;
import com.forte.qqrobot.utils.SingleFactory;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/4/15 17:06
 * @since JDK1.8
 **/
public class SocketResourceDispatchCenter extends ResourceDispatchCenter {

    /**
     * 储存一个QQWebSocketMsgCreator对象到单例工厂
     * @param qqJSONMsgCreator 单例对象
     */
    static void saveQQWebSocketMsgCreator(QQJSONMsgCreator qqJSONMsgCreator){
        //将CQCodeUtil放入单例工厂
        save(qqJSONMsgCreator);
    }

    /**
     * 获得一个QQWebSocketMsgCreator单例对象
     * @return  QQWebSocketMsgCreator单例对象
     */
    public static QQJSONMsgCreator getQQJSONMsgCreator(){
        return get(QQJSONMsgCreator.class);
    }



    /**
     * 储存一个configuration对象到单例工厂
     * @param configuration 单例对象
     */
    static void saveLinkConfiguration(LinkConfiguration configuration){
        ResourceDispatchCenter.saveConfiguration(configuration);
    }

    /**
     * 获取一个LinkConfiguration单例对象
     * @return LinkConfiguration单例对象
     */
    public static LinkConfiguration getLinkConfiguration(){
        return get(LinkConfiguration.class);
    }


    /**
     * 储存一个消息响应器
     * @param qqWebSocketInfoReturnManager 消息响应器
     */
    static void saveQQWebSocketInfoReturnManager(QQWebSocketInfoReturnManager qqWebSocketInfoReturnManager){
        save(qqWebSocketInfoReturnManager);
    }

    /**
     * 获取一个QQWebSocketInfoReturnManager单例对象
     * @return  QQWebSocketInfoReturnManager单例对象
     */
    public static QQWebSocketInfoReturnManager getQQWebSocketInfoReturnManager(){
        return get(QQWebSocketInfoReturnManager.class);
    }






}
