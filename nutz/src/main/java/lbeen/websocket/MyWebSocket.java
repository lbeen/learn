package lbeen.websocket;

import lbeen.common.redis.RedisClient;
import lbeen.sys.user.bean.UserInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.plugins.mvc.websocket.NutWsConfigurator;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import javax.servlet.http.HttpSession;
import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息websocket
 *
 * @author 李斌
 */
@ServerEndpoint(value = "/websocket", configurator = NutWsConfigurator.class)
@IocBean(create = "onCreate", depose = "onDespose")
public class MyWebSocket extends Endpoint {
    /**
     * redis发布订阅频道
     */
    private static String CHANNEL = "msg";
    /**
     * 用户id->websocket session 映射
     */
    private ConcurrentHashMap<String, Set<Session>> USERID_SESSIONIDS = new ConcurrentHashMap<>();
    /**
     * jedis池
     */
    private JedisPool jedisPool;
    /**
     * 订阅的jedis对象
     */
    private Jedis listenRedis;
    /**
     * 订阅监听器
     */
    private JedisPubSub lisener;

    /**
     * 创建时开始订阅redis
     */
    public void onCreate() {
        lisener = new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                Msg msg = Json.fromJson(Msg.class, message);
                Set<Session> sessions = USERID_SESSIONIDS.get(msg.getReceiverId());
                if (sessions != null) {
                    sessions.forEach(session -> {
                        try {
                            session.getBasicRemote().sendText(msg.getContent());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        };
        jedisPool = RedisClient.getClient().getJedisPool();
        listenRedis = jedisPool.getResource();
        new Thread(() -> listenRedis.subscribe(lisener, CHANNEL)).start();
    }

    /**
     * 销毁时取消订阅redis并关闭redis链接
     */
    public void onDepose() {
        if (lisener != null) {
            lisener.unsubscribe();
        }
        listenRedis.close();
    }

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        HttpSession httpSession = (HttpSession) config.getUserProperties().get("HttpSession");
        PrincipalCollection object = (PrincipalCollection) httpSession.getAttribute("org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY");
        UserInfo userInfo = (UserInfo) object.getPrimaryPrincipal();
        String userId = userInfo.getId();
        Set<Session> sessions = USERID_SESSIONIDS.get(userId);
        if (sessions == null) {
            sessions = new HashSet<>(2);
            USERID_SESSIONIDS.put(userId, sessions);
        }
        sessions.add(session);
        MessageHandler handler = new Myhandler(this, userInfo);
        session.addMessageHandler(handler);
    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        Set<MessageHandler> handlers = session.getMessageHandlers();
        if (!Lang.isEmpty(handlers)) {
            Myhandler handler = (Myhandler) handlers.iterator().next();
            String userId = handler.getUserInfo().getId();
            if (StringUtils.isNotBlank(userId)) {
                Set<Session> sessions = USERID_SESSIONIDS.get(userId);
                if (sessions != null) {
                    sessions.remove(session);
                    if (sessions.isEmpty()) {
                        USERID_SESSIONIDS.remove(userId);
                    }
                }
            }
        }
    }

    @Override
    public void onError(Session session, Throwable thr) {
        onClose(session, null);
    }

    /**
     * 收到信息发布到redis
     */
    void publishMsg(String message) {
        Jedis jedis = jedisPool.getResource();
        jedis.publish(CHANNEL, message);
        jedis.close();
    }
}

