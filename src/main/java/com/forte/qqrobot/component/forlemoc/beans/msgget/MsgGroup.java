package com.forte.qqrobot.component.forlemoc.beans.msgget;

import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.types.GroupMsgType;

/**
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/7 11:09
 * @since JDK1.8
 **/
public class MsgGroup implements GroupMsg, MsgGet {
    /*
    {
         "username":        "泰兰德",
         "nick":            "泰奶奶",
         "sex":             "0",
         "age":             "12000",
         "error":           "0",
         "act":             "2",
         "fromGroup":       "1234",
         "fromGroupName":   "月之女祭司",
         "fromQQ":          "1234",
         "subType":         "1",
         "sendTime":        "1481481775",
         "fromAnonymous":   "",
         "msg":             "谁看到玛法里奥了？",
         "font":            "7141560"
     }
     */
            /** 用户名 */
            private String username;
            /** 群昵称 */
            private String nick;
            /** 性别，0：男，1：女 */
            private String sex;
            /** 年龄 */
            private String age;
            /** 消息类型，群消息一般为2 */
            private Integer act;
            /** 群号 */
            private String fromGroup;
            /** 群昵称 */
            private String fromGroupName;
            /** qq号，发消息的人 */
            private String fromQQ;
            /** 子类型 */
            private Integer subType;
            /** 发送时间 */
            private Long sendTime;
            /** 如果是匿名应该会有匿名名称 */
            private String fromAnonymous;
            /** 消息正文 */
            private String msg;
            /** 字体 */
            private String font;
            /** 错误编码，无错为0 */
            private String error;
    /** 原生数据字符串 */
    private String originalData;
            /* —————————— getter & setter —————————— */

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getAct() {
        return act;
    }

    public void setAct(Integer act) {
        this.act = act;
    }

    public String getFromGroup() {
        return fromGroup;
    }

    public void setFromGroup(String fromGroup) {
        this.fromGroup = fromGroup;
    }

    public String getFromGroupName() {
        return fromGroupName;
    }

    public void setFromGroupName(String fromGroupName) {
        this.fromGroupName = fromGroupName;
    }

    public String getFromQQ() {
        return fromQQ;
    }

    public void setFromQQ(String fromQQ) {
        this.fromQQ = fromQQ;
    }

    public Integer getSubType() {
        return subType;
    }

    public void setSubType(Integer subType) {
        this.subType = subType;
    }

    public Long getSendTime() {
        return sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }

    public String getFromAnonymous() {
        return fromAnonymous;
    }

    public void setFromAnonymous(String fromAnonymous) {
        this.fromAnonymous = fromAnonymous;
    }

    /* ———————— 统一接口 —————————— */

    @Override
    public String getOriginalData() {
        return originalData;
    }

    public void setOriginalData(String originalData) {
        this.originalData = originalData;
    }

    /**
     * 获取ID，如果没有此参数推荐使用UUID等来代替
     */
    @Override
    public String getId() {
        return act+"";
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 获取群消息发送人的qq号
     */
    @Override
    public String getQQ() {
        return fromQQ;
    }

    /**
     * 获取群消息的群号
     */
    @Override
    public String getGroup() {
        return fromGroup;
    }

    @Override
    public String getFont() {
        return font;
    }

    /**
     * 获取到的时间, 代表某一时间的秒值。注意是秒值！如果类型不对请自行转化
     */
    @Override
    public long getTime() {
        return sendTime;
    }

    /**
     * 获取消息类型
     */
    @Override
    public GroupMsgType getType() {
        return (fromAnonymous == null || fromAnonymous.trim().length() <= 0) ? GroupMsgType.NORMAL_MSG : GroupMsgType.ANON_MSG;
    }

    /**
     * 获取群名称
     */
    public String getName() {
        return fromGroupName;
    }

    public void setFont(String font) {
        this.font = font;
    }

}
