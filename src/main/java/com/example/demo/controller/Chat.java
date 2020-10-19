package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/chat/{sendUser}")
public class Chat{
    // 用户在线数
    private static int onLineCount = 0;
    // 当前的websocket对象
    private static ConcurrentHashMap<String, Chat> webSocketMap = new ConcurrentHashMap<String, Chat>();
    // 当前会话,属于websocket的session
    private Session session;
    // 聊天信息
    private String sendUser;// 当前用户
    private String toUser;// 接收人
    private String message;// 聊天信息
    /**
     * 连接建立成功调用的方法
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     * @PathParam("sendUser") 表示可以在访问的时候带上参数，参数：sendUser为  @ServerEndpoint("/chatDemo/{sendUser}")
     * @throws IOException
     */
    @OnOpen
    public void onOpen(@PathParam("sendUser") String sendUser, Session session) throws IOException {
        this.sendUser = sendUser;
        this.session = session;
        addOnlineCount();
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount() + " 当前session是" + session.hashCode());

        webSocketMap.put(sendUser, this);//当前用户的websocket
        // 刷新在线人数
        for (Chat chat : webSocketMap.values()) {
            //使用if判断是要统计人数还是发送消息
            //chat.sendMessage("count",getOnlineCount() + "");
            System.out.println("count--->"+getOnlineCount() + "");
        }
    }

    /**
     * 连接关闭所调用的方法
     *
     * @return
     * @throws IOException
     */
    @OnClose
    public void onClose() throws IOException {
        // 在线数减1
        subOnlineCount();

        for (Chat chat : webSocketMap.values()) {
            //说明当前的session已经被关闭
            if(chat.session != this.session){
                //chat.sendMessage("count", getOnlineCount() + "");
            }
        }
        webSocketMap.remove(sendUser);
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }


    @OnMessage
    public void onMessage(String jsonMsg, Session session) throws IOException {
        JSONObject jsonOject = JSONObject.parseObject(jsonMsg);
        sendUser = jsonOject.getString("sendUser");
        toUser = jsonOject.getString("toUser");
        message = jsonOject.getString("message");

        // 得到接收人
        Chat user = webSocketMap.get(toUser);
        if (user == null) {//如果接收人不存在则保持到数据库
            System.out.println("信息已保存到数据库");
            return;
        }
        user.sendMessage("send",message);
    }
    /**
     * 发成错误所调用的方法
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }


    public void sendMessage(String type,String message) throws IOException {
        if(type.equals("count")){
            this.session.getBasicRemote().sendText("count:" + message);//在jsp判断是否包含count
        }else{
            this.session.getBasicRemote().sendText(message);//提供阻塞式的消息发送方式
            // this.session.getAsyncRemote().sendText(message);//提供非阻塞式的消息传输方式。
        }
    }

    // 获得当前在线人数
    public static synchronized int getOnlineCount() {
        return onLineCount;
    }

    // 新用户
    public static synchronized void addOnlineCount() {
        Chat.onLineCount++;
    }

    // 移除退出用户
    public static synchronized void subOnlineCount() {
        Chat.onLineCount--;
    }
}
