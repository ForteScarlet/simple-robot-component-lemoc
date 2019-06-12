package com.forte.qqrobot.component.forlemoc.beans.inforeturn;

import com.forte.qqrobot.beans.messages.result.InfoResult;

/**
 * 请求JSON数据的返回值
 * @author ForteScarlet <[163邮箱地址]ForteScarlet@163.com>
 * @date Created in 2019/3/14 10:47
 * @since JDK1.8
 **/
public interface InfoReturn extends InfoResult {

    /** 事件编码, 固定为0 */
    Integer act = 0;

    default Integer getAct(){
        return act;
    }

    /** 返回类型编码 */
    Integer getReturn();
    /** 获取异常 */
    Integer getError();
    @Override
    String toString();

}
