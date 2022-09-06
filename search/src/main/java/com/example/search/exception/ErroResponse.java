package com.example.search.exception;

import java.util.Date;

public class ErroResponse {
    private Date date;
    private String msg;

    public ErroResponse(String msg) {
        this.msg = msg;
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}