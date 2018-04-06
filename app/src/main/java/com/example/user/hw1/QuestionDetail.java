package com.example.user.hw1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by User on 30.03.2018.
 */

@SuppressWarnings("serial")
public class QuestionDetail implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("soru")
    @Expose
    private String soru;
    @SerializedName("a")
    @Expose
    private String a;
    @SerializedName("b")
    @Expose
    private String b;
    @SerializedName("c")
    @Expose
    private String c;
    @SerializedName("d")
    @Expose
    private String d;
    @SerializedName("cevap")
    @Expose
    private String cevap;

    private String userAnswer = null;
    private int status = -1; /*0 boş bırakılmış soru, 1 doğru cevap verilmiş, 2 yanlış cevap verilmiş*/

    public String getCevap() {
        return cevap;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSoru() {
        return soru;
    }

    public void setSoru(String soru) {
        this.soru = soru;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public String getC() {
        return c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public void setCevap(String cevap) {
        this.cevap = cevap;
    }
}
