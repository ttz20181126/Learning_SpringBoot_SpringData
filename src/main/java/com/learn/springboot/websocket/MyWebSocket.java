package com.learn.springboot.websocket;

import net.sf.json.JSONObject;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Hashtable;


@ServerEndpoint("/myWebSocket")
public class MyWebSocket {

    //定义一个静态容量，存放所有连接实例
    private static Hashtable<String,MyWebSocket> wsTable = new Hashtable<>();

    private Session session;

    private String userId;


    @OnOpen
    public void onOpen(Session session){
        //连接成功后，会调用此方法，session是会话实例
        this.session = session;
        //会话的各种设置,可参考官网文档，如设置超时时间
        this.session.setMaxIdleTimeout(60*10000);
    }

    @OnClose
    public void onClose() throws Exception{
        //连接关闭时触发此方法
        wsTable.remove(this.userId);
    }

    @OnError
    public void onError(Session session,Throwable errors){
        //连接出现错误时会触发此方法
    }

    @OnMessage
    public void onMessage(String message,Session session) throws Exception{
        //message就是我们提到的发送消息体
        JSONObject jsonObject = JSONObject.fromObject(message);
        //根据需要自定义的消息类型，示列:01 登录  02聊天
        String msgType = jsonObject.getString("msg_type");
        if("01".equals(msgType)){
            doLogin(jsonObject,session);
        }else if("02".equals(msgType))
            doChat(jsonObject);
    }

    //向对方发消息的逻辑
    private void doChat(JSONObject jsonObject) throws IOException {
        //假设消息体中我们用toID记录接收方的userId
        String toID = jsonObject.getString("toID");
        //假设消息体中我们用text记录发送的文本（这个文本就是接收消息体）
        String text = jsonObject.getString("text");
        //取消接收方的websocket链接实例
        MyWebSocket myWebSocket = wsTable.get(toID);
        //调用接收方法链接实例的成员方法将消息发送至其浏览器
        myWebSocket.sendMessage(text);

    }

    //服务器向浏览器发消息
    private void sendMessage(String text) throws IOException{
        this.session.getBasicRemote().sendText(text);
    }

    //登录逻辑
    private void doLogin(JSONObject jsonObject, Session session) {
        this.userId = jsonObject.getString("userId");
        wsTable.put("userId",this);//把该链接实例加入到容器
    }




}