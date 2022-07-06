package com.example.whatsappclone.Models;

public class Messages {
    String uId,msg;
    Long timeStamp;
    String msgId;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Messages() {
    }

    public Messages(String uId, String msg) {
        this.uId = uId;
        this.msg = msg;
    }

    public Messages(String uId, String msg, Long timeStamp) {
        this.uId = uId;
        this.msg = msg;
        this.timeStamp = timeStamp;
    }
}
