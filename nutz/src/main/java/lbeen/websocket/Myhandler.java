package lbeen.websocket;

import lbeen.sys.user.bean.UserInfo;
import org.nutz.json.Json;

import javax.websocket.MessageHandler;

/**
 * @author 李斌
 */
public class Myhandler implements MessageHandler.Whole<String> {
    private MyWebSocket myWebSocket;
    private UserInfo userInfo;

    Myhandler(MyWebSocket myWebSocket, UserInfo userInfo) {
        this.myWebSocket = myWebSocket;
        this.userInfo = userInfo;
    }

    /**
     * 处理消息, 将其转为NutMap,然后找对应的处理方法.
     */
    @Override
    public void onMessage(String message) {
        Msg msg = Json.fromJson(Msg.class, message);
        msg.setSenderId(userInfo.getId());
        send(Json.toJson(msg));
    }

    private void send(String message) {
        myWebSocket.publishMsg(message);
    }

    UserInfo getUserInfo() {
        return userInfo;
    }
}
