package com.example.user.hw1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 30.03.2018.
 */

public class QuestionList {

    @SerializedName("questionDetail")
    @Expose
    private List<QuestionDetail> questionDetail = null;

    public List<QuestionDetail> getQuestionDetail() {
        return questionDetail;
    }

    public void setQuestionDetail(List<QuestionDetail> questionDetail) {
        this.questionDetail = questionDetail;
    }
}
