package com.kuma.im.arrtibute;

import com.kuma.im.session.Session;
import io.netty.util.AttributeKey;

/**
 * 业务流程中传递的全局属性
 *
 * @author kuma 2021-02-26
 */
public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
