package com.user.bkashcheckout;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Feed {

    private String transcationid,orderid, category,bkashno,date;
    private @ServerTimestamp
    Date timestamp;

    public Feed() {
    }

    public String getTranscationid() {
        return transcationid;
    }

    public void setTranscationid(String transcationid) {
        this.transcationid = transcationid;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBkashno() {
        return bkashno;
    }

    public void setBkashno(String bkashno) {
        this.bkashno = bkashno;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
