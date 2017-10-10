package com.maomishen.hidemessage;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by lunagao on 2017/8/10.
 * Project for HideMessage.
 */

public class SMS extends RealmObject {
    private String sendId;
    private String phoneNumber;
    private Date createTime = new Date();
    private boolean isSended = false;
    private Date sendedTime;
    private boolean isDelivered = false;
    private Date deliveredTime;
    private String errorMessage;
    private Date requestTime;
    private String requestAddress;
    private String requestUser;
    private String requestId;
    private int server_message_id;
    private boolean isError = false;

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isSended() {
        return isSended;
    }

    public void setSended(boolean sended) {
        isSended = sended;
    }

    public Date getSendedTime() {
        return sendedTime;
    }

    public void setSendedTime(Date sendedTime) {
        this.sendedTime = sendedTime;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public Date getDeliveredTime() {
        return deliveredTime;
    }

    public void setDeliveredTime(Date deliveredTime) {
        this.deliveredTime = deliveredTime;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public String getRequestAddress() {
        return requestAddress;
    }

    public void setRequestAddress(String requestAddress) {
        this.requestAddress = requestAddress;
    }

    public String getRequestUser() {
        return requestUser;
    }

    public void setRequestUser(String requestUser) {
        this.requestUser = requestUser;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getServer_message_id() {
        return server_message_id;
    }

    public void setServer_message_id(int server_message_id) {
        this.server_message_id = server_message_id;
    }
}