package org.lpw.tephra.weixin;

import java.util.Map;

/**
 * 微信监听服务。
 *
 * @author lpw
 */
public interface WeixinService {
    /**
     * 微信URI地址。
     */
    String URI = "/tephra/weixin/";

    /**
     * 验证echo信息是否正确。
     *
     * @param appId     微信公众号AppID。
     * @param signature 签名。
     * @param timestamp 时间戳。
     * @param nonce     随机数。
     * @return 如果验证成功则返回true；否则返回false。
     */
    boolean echo(String appId, String signature, String timestamp, String nonce);

    /**
     * 获取重定向参数。
     * 主要用于获取微信用户信息。
     *
     * @param appId 微信公众号AppID。
     * @param code  认证码。
     */
    void redirect(String appId, String code);

    /**
     * 处理接收到的XML数据。
     *
     * @param appId 微信公众号AppID。
     * @param xml   XML数据。
     * @return 处理结果。
     */
    String xml(String appId, String xml);

    /**
     * 处理充值回调。
     *
     * @param parameters 参数集。
     * @return 结果。
     */
    String pay(Map<String, String> parameters);

    /**
     * 获取当前微信用户的OpenID。
     *
     * @return 当前微信用户的OpenID；如果不存在则返回null。
     */
    String getOpenId();

    /**
     * 获取当前微信用户昵称。
     *
     * @param openId 微信用户OpenID。
     * @return 当前微信用户昵称；如果不存在则返回null。
     */
    String getNickname(String openId);
}
