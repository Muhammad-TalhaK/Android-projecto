package com.example.whatsappclone.Models;

import android.net.Uri;

public class User {

    String profilePic,uName,uId,uPwd,uMail,lastMsg,uAbout;

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuPwd() {
        return uPwd;
    }

    public void setuPwd(String uPwd) {
        this.uPwd = uPwd;
    }

    public String getuMail() {
        return uMail;
    }

    public void setuMail(String uMail) {
        this.uMail = uMail;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    //Const for Storing user in firebase database
    public User(String uName,  String uMail,String uPwd) {
        this.uName = uName;
        this.uPwd = uPwd;
        this.uMail = uMail;
    }

    public User(String uName, String profilePic) {
        this.uName = uName;
        this.profilePic = profilePic;
    }

    public User() {
    }

    public String getuAbout() {
        return uAbout;
    }

    public void setuAbout(String uAbout) {
        this.uAbout = uAbout;
    }

    public User(String profilePic, String uName, String uId, String uPwd, String uMail, String lastMsg, String uAbout) {
        this.profilePic = profilePic;
        this.uName = uName;
        this.uId = uId;
        this.uPwd = uPwd;
        this.uMail = uMail;
        this.lastMsg = lastMsg;
        this.uAbout = uAbout;
    }
}
