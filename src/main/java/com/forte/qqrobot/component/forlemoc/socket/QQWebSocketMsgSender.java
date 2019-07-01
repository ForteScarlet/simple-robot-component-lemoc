package com.forte.qqrobot.component.forlemoc.socket;


import com.forte.qqrobot.beans.messages.result.*;
import com.forte.qqrobot.beans.messages.types.GroupAddRequestType;
import com.forte.qqrobot.component.forlemoc.SocketResourceDispatchCenter;
import com.forte.qqrobot.component.forlemoc.beans.inforeturn.*;
import com.forte.qqrobot.component.forlemoc.exception.LemocException;
import com.forte.qqrobot.sender.senderlist.RootSenderList;

import java.util.function.Supplier;

/**
 * QQWebsocket信息发送器
 * 被标记了@Deprecated注解的方法为LEMOC插件无法实现的接口
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/8 18:32
 * @since JDK1.8
 **/
public class QQWebSocketMsgSender implements RootSenderList {

    /**
     * 客户端连接client对象
     */
    private final QQWebSocketClient client;

    /**
     * 响应信息字符串生成器 MsgCreator
     */
    private final QQJSONMsgCreator creator;

    /**
     * 私有构造
     */
    QQWebSocketMsgSender(QQWebSocketClient client, QQJSONMsgCreator creator) {
        this.client = client;
        this.creator = creator;
    }

    /**
     * 工厂方法
     *
     * @return QQWebSocketMsgSender实例对象
     */
    public static QQWebSocketMsgSender build(QQWebSocketClient client) {
        return new QQWebSocketMsgSender(client, SocketResourceDispatchCenter.getQQJSONMsgCreator());
    }

    /* ———————————————— 信息发送方法 ———————————————— */

    /**
     * 讨论组消息
     *
     * @param discussid 讨论组
     * @param msg       消息
     */
    @Override
    public boolean sendDiscussMsg(String discussid, String msg) {
        try{
            send(creator.getResponseJson_sendDisGroupMsg(discussid, msg));
            return true;
        }catch (Exception e){ return false; }

    }

    /**
     * 送花
     *
     * @param group 群号
     * @param QQ    QQ号
     */
    @Override
    @Deprecated
    public boolean sendFlower(String group, String QQ) {
        throw LemocException.notSupportApi();
    }

    /**
     * 群消息
     *
     * @param groupid 群号
     * @param msg     消息
     */
    @Override
    public boolean sendGroupMsg(String groupid, String msg) {
        try{
            send(creator.getResponseJson_sendGroupMsg(groupid, msg));
            return true;
        }catch (Exception e){ return false; }
    }

    /**
     * 私信消息
     *
     * @param QQID qq号
     * @param msg  消息
     */
    @Override
    public boolean sendPrivateMsg(String QQID, String msg) {
        try{
            send(creator.getResponseJson_sendMsgPrivate(QQID, msg));
            return true;
        }catch (Exception e){ return false; }
    }

    /**
     * 赞
     * @param QQ qq号
     */
    @Override
    public boolean sendLike(String QQ, int times) {
        try{
            send(creator.getResponseJson_sendPraise(QQ));
            return true;
        }catch (Exception e){ return false; }
    }

    /**
     * 设置全群禁言
     *
     * @param groupid   群号
     * @param enableban 是否开启禁言
     */
    @Override
    public boolean setGroupWholeBan(String groupid, boolean enableban) {
        try{
            send(creator.getResponseJson_setAllGroupBanned(null, groupid, String.valueOf(enableban)));
            return true;
        }catch (Exception e){ return false; }
    }

    /**
     * 似乎只需要一个消息ID即可
     * 需要pro
     *
     * @param flag 消息标识
     */
    @Override
    @Deprecated
    public boolean setMsgRecall(String flag) {
        throw LemocException.notSupportApi();
    }

    /**
     * 打卡
     */
    @Override
    @Deprecated
    public boolean setSign() {
        throw LemocException.notSupportApi();
    }

    /**
     * 设置匿名群员禁言
     *
     * @param groupid   群号
     * @param anonymous 匿名群员名称，大概
     * @param duration  时间，单位 秒
     */
    @Override
    public boolean setGroupAnonymousBan(String groupid, String anonymous, long duration) {
        try{
            send(creator.getResponseJson_setAnoGroupMemberBanned(groupid, duration, anonymous));
            return true;
        }catch (Exception e){ return false; }
    }

    /**
     * 讨论组退出
     *
     * @param discussid 讨论组id
     */
    @Override
    @Deprecated
    public boolean setDiscussLeave(String discussid) {
        try{
            send(creator.getResponseJson_setDisGroupExit(discussid));
            return true;
        }catch (Exception e){ return false; }
    }

    /**
     * 好友添加请求
     * @param responseoperation
     * @param remark
     * @param responseflag
     */
    @Override
    public boolean setFriendAddRequest(String responseflag, String remark, boolean responseoperation) {
        try{
            send(creator.getResponseJson_setFriendAddRequest(String.valueOf(responseoperation), remark, responseflag));
            return true;
        }catch (Exception e){ return false; }
    }

    /**
     * 设置群管理员
     * @param QQID     qq号
     * @param groupid  群号
     * @param setadmin 是否设置为管理员
     */
    @Override
    public boolean setGroupAdmin(String groupid, String QQID, boolean setadmin) {
        try{
            send(creator.getResponseJson_setGroupAdmin(QQID, groupid, setadmin));
            return true;
        }catch (Exception e){ return false; }
    }

    /**
     * 群匿名设置
     *
     * @param groupid         群号
     * @param enableanomymous 是否开启群匿名
     */
    @Override
    public boolean setGroupAnonymous(String groupid, boolean enableanomymous) {
        try{
            send(creator.getResponseJson_setGroupAno(groupid, enableanomymous));
            return true;
        }catch (Exception e){ return false; }
    }

    /**
     * 置群退出(出于安全起见，该权限没有开启)
     * @param groupid   群号
     */
    @Deprecated
    @Override
    public boolean setGroupLeave(String groupid) {
        try{
            send(creator.getResponseJson_setGroupExit(groupid, true));
            return true;
        }catch (Exception e){ return false; }
    }

    /**
     * 置群添加请求
     * 参数意义暂且不明
     *
     * @param requestType
     * @param aggree
     * @param reason
     * @param responseflag
     */
    @Override
    public boolean setGroupAddRequest(String responseflag, GroupAddRequestType requestType, boolean aggree, String reason) {
        try{
            //假设主动添加为0，邀请为1
            String type = requestType.isAdd() ? "0" : "1";
            send(creator.getResponseJson_setGroupJoinResquest(type, String.valueOf(aggree), reason, responseflag));
            return true;
        }catch (Exception e){ return false; }
    }

    /**
     * 置群员禁言
     *
     * @param QQID     qq号
     * @param groupid  群号
     * @param duration 禁言时间，单位为秒
     */
    @Override
    public boolean setGroupBan(String groupid, String QQID, long duration) {
        try{
            String responseJson_setGroupMemberBanned = creator.getResponseJson_setGroupMemberBanned(QQID, groupid, duration);
            send(responseJson_setGroupMemberBanned);
            System.out.println(responseJson_setGroupMemberBanned);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 置群成员名片
     *
     * @param QQID    qq号
     * @param groupid 群号
     * @param newcard 新名片
     */
    @Override
    public boolean setGroupCard(String groupid, String QQID, String newcard) {
        try{
            send(creator.getResponseJson_setGroupMemberCard(QQID, groupid, newcard));
            return true;
        }catch (Exception e){ return false; }
    }

    /**
     * 删除群文件<br>
     * ! 此接口可能不成熟
     *
     * @param group 群号
     * @param flag  一般应该会有个标识
     */
    @Override
    public boolean setGroupFileDelete(String group, String flag) {
        throw LemocException.notSupportApi();
    }

    /**
     * 置群员移除
     *
     * @param QQID             移除的qq号
     * @param groupid          群号
     * @param rejectaddrequest 是否拒绝添加请求
     */
    @Override
    public boolean setGroupMemberKick(String groupid, String QQID, boolean rejectaddrequest) {
        try{
            send(creator.getResponseJson_setGroupMemberRemove(QQID, groupid, rejectaddrequest));
            return true;
        }catch (Exception e){ return false; }
    }

    /**
     * 群签到
     *
     * @param group 群号
     */
    @Override
    public boolean setGroupSign(String group) {
        throw LemocException.notSupportApi();
    }

    /**
     * 置群成员专属头衔
     *
     * @param QQID            qq号
     * @param groupid         群号
     * @param duration        设置时间，单位大概是秒
     * @param newspecialtitle 专属头衔
     */
    @Override
    public boolean setGroupExclusiveTitle(String groupid, String QQID, String newspecialtitle, long duration) {
        try{
            send(creator.getResponseJson_setGroupMemberSpecialTitle(QQID, groupid, duration, newspecialtitle));
            return true;
        }catch (Exception e){ return false; }
    }


    //**************************************
    //*         获取信息-LEMOC socket
    //**************************************


    /**
     * 取匿名成员信息
     * 一般是使用匿名标识来获取
     *
     * @param flag
     * @return 匿名成员信息
     */
    @Override
    @Deprecated
    public AnonInfo getAnonInfo(String flag) {
        throw LemocException.notSupportApi();
    }

    /**
     * 获取权限信息
     * 一般不需要参数
     *
     * @return 权限信息
     */
    @Override
    @Deprecated
    public AuthInfo getAuthInfo() {
        throw LemocException.notSupportApi();
    }

    /**
     * 获取封禁成员列表
     *
     * @param group 群号
     * @return 封禁列表
     */
    @Override
    @Deprecated
    public BanList getBanList(String group) {
        throw LemocException.notSupportApi();
    }

    /**
     * 获取群文件信息
     *
     * @param flag 文件标识
     * @return 群文件信息
     */
    @Override
    @Deprecated
    public FileInfo getFileInfo(String flag) {
        throw LemocException.notSupportApi();
    }

    /**
     * 获取好友列表
     *
     * @return 好友列表
     */
    @Override
    @Deprecated
    public FriendList getFriendList() {
        throw LemocException.notSupportApi();
    }

    /**
     * 取群作业列表
     *
     * @param group 群号
     * @return 群作业列表
     */
    @Override
    @Deprecated
    public GroupHomeworkList getGroupHomeworkList(String group) {
        throw LemocException.notSupportApi();
    }

    /**
     * 取群作业列表
     *
     * @param group  群号
     * @param number 获取数量
     * @return 群作业列表
     */
    @Override
    public GroupHomeworkList getGroupHomeworkList(String group, int number) {
        throw LemocException.notSupportApi();
    }

    /**
     * 取群信息
     *
     * @param group 群号
     * @return 群信息
     */
    @Override
    @Deprecated
    public GroupInfo getGroupInfo(String group) {
        throw LemocException.notSupportApi();
    }

    /**
     * 取群信息
     *
     * @param group 群号
     * @param cache 是否使用缓存
     * @return 群信息
     */
    @Override
    public GroupInfo getGroupInfo(String group, boolean cache) {
        throw LemocException.notSupportApi();
    }

    /**
     * 取群连接列表
     *
     * @param groupList 群号
     * @return 群链接
     */
    @Override
    @Deprecated
    public GroupLinkList getGroupLinkList(String groupList) {
        throw LemocException.notSupportApi();
    }

    /**
     * 取群链接列表
     *
     * @param group  群号
     * @param number 获取数量
     * @return 群链接
     */
    @Override
    public GroupLinkList getGroupLinkList(String group, int number) {
        throw LemocException.notSupportApi();
    }

    /**
     * 获取全部群信息
     *
     * @return 群信息的列表
     */
    @Override
    public ReturnLoginInGroups getGroupList() {
        return get(25305, () -> creator.getResponseJson_InfoForLoginInGroups("1145154"), ReturnLoginInGroups.class);
    }

    /**
     * 获取某个群员的信息
     *
     * @param groupid 群id
     * @param QQID    qq号
     * @return 某个群员的信息
     */
    @Override
    public ReturnGroupMember getGroupMemberInfo(String groupid, String QQID) {
        return get(25303, () -> creator.getResponseJson_InfoGroupMember(groupid, QQID, "true"), ReturnGroupMember.class);

    }

    /**
     * 取群成员信息
     *
     * @param group 群号
     * @param QQ    QQ号
     * @param cache 是否使用缓存
     * @return 群成员信息
     */
    @Override
    public GroupMemberInfo getGroupMemberInfo(String group, String QQ, boolean cache) {
        throw LemocException.notSupportApi();
    }

    /**
     * 取群成员列表
     *
     * @param group 群号
     * @return 成员列表
     */
    @Override
    @Deprecated
    public GroupMemberList getGroupMemberList(String group) {
        throw LemocException.notSupportApi();
    }

    /**
     * 取群公告列表
     *
     * @param group 群号
     * @return 群公告列表
     */
    @Override
    @Deprecated
    public GroupNoteList getGroupNoteList(String group) {
        throw LemocException.notSupportApi();
    }

    /**
     * 取群公告列表
     *
     * @param group  群号
     * @param number 数量
     * @return 群公告列表
     */
    @Override
    public GroupNoteList getGroupNoteList(String group, int number) {
        throw LemocException.notSupportApi();
    }

    /**
     * 取置顶群公告
     *
     * @param group 群号
     * @return 置顶群公告
     */
    @Override
    @Deprecated
    public GroupTopNote getGroupTopNote(String group) {
        throw LemocException.notSupportApi();
    }

    /**
     * 获取图片信息
     *
     * @param flag 图片文件名或标识
     * @return 图片信息
     */
    @Override
    @Deprecated
    public ImageInfo getImageInfo(String flag) {
        throw LemocException.notSupportApi();
    }

    /**
     * 获取登录账号的昵称
     *
     * @return 昵称
     */
    @Override
    public LoginQQInfo getLoginQQInfo() {
        ReturnLoginNick loginQQNick = getLoginQQNick();
        ReturnLoginQQ loginQQCode = getLoginQQCode();
        return new LoginQQInfo() {
            /**
             * 获取原本的数据 originalData
             */
            @Override
            public String getOriginalData() {
                return "{here has nothing}";
            }

            @Override
            public String getName() {
                return loginQQNick.getName();
            }

            @Override
            public String getQQ() {
                return loginQQCode.getQQ();
            }
            @Override
            public String getHeadUrl() {
                return null;
            }
            @Override
            public Integer getLevel() {
                return null;
            }
        };
    }

    /**
     * 获取群共享文件列表
     *
     * @param group
     * @return 共享文件列表
     */
    @Override
    @Deprecated
    public ShareList getShareList(String group) {
        throw LemocException.notSupportApi();
    }

    /**
     * 获取当前登录账号的qq号
     *
     * @return 当前登录账号的qq号
     */
    public ReturnLoginQQ getLoginQQCode() {
        return get(25301, creator::getResponseJson_InfoLoginQQ, ReturnLoginQQ.class);
    }
    /**
     * 获取登录账号的昵称
     *
     * @return 昵称
     */
    public ReturnLoginNick getLoginQQNick() {
        return get(25302, creator::getResponseJson_InfoLoginNick, ReturnLoginNick.class);
    }

    /**
     * 获取陌生人信息
     *
     * @return
     */
    @Override
    public ReturnStranger getStrangerInfo(String QQID) {
        return get(25304, () -> creator.getResponseJson_InfoStranger(QQID, "true"), ReturnStranger.class);
    }

    /**
     * 取陌生人信息
     *
     * @param QQ    陌生人的QQ号
     * @param cache 是否使用缓存
     * @return
     */
    @Override
    public StrangerInfo getStrangerInfo(String QQ, boolean cache) {
        throw LemocException.notSupportApi();
    }


    //**************************************
    //*          消息发送等方法
    //**************************************

    /**
     * 发送消息 - 在一条新的线程中发送消息
     */
    void send(String text) {
        client.send(text);
//        ResourceDispatchCenter.getThreadPool().execute(() -> client.send(text));
    }

    /**
     * 获取信息 - 对消息的获取统一管理
     */
    public <T extends InfoReturn> T get(int act, Supplier<String> sendStr, Class<T> clazz) {
        //发送消息，并阻塞获取消息
        send(sendStr.get());
        //获取返回值
        QQWebSocketInfoReturnManager manager = getSocketInfoReturnManager();
        manager.send(act);
        return manager.get(act, clazz);
    }


    /**
     * 获取socket信息获取管理器
     *
     * @return socket信息获取管理器
     */
    private QQWebSocketInfoReturnManager getSocketInfoReturnManager() {
        return SocketResourceDispatchCenter.getQQWebSocketInfoReturnManager();
    }


}
