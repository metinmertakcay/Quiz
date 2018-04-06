package com.example.user.hw1;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static com.example.user.hw1.AllQuestions.DOGRU;
import static com.example.user.hw1.AllQuestions.YANLIS;

/**
 * Created by User on 30.03.2018.
 */

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {

    private List<QuestionDetail> questions;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView soru, cevap;
        public ImageView sonuc;

        public MyViewHolder(View view)
        {
            super(view);
            soru = (TextView)view.findViewById(R.id.soru);
            cevap = (TextView)view.findViewById(R.id.cevap);
            sonuc = (ImageView) view.findViewById(R.id.sonuc);
        }
    }

    public QuestionAdapter(List<QuestionDetail> questions)
    {
        this.questions = questions;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_question_list_row,parent,false);
        return new MyViewHolder(itemView);
    }

    public void onBindViewHolder(MyViewHolder holder,int position)
    {
        QuestionDetail questionDetail = questions.get(position);
        holder.soru.setText( (position + 1) + ") " + questionDetail.getSoru());
        holder.cevap.setText(questionDetail.getUserAnswer());

        if(questionDetail.getStatus() != -1) {
            if (questionDetail.getStatus() == DOGRU) {
                holder.sonuc.setImageResource(R.mipmap.tik);
            } else if (questionDetail.getStatus() == YANLIS) {
                holder.sonuc.setImageResource(R.mipmap.hata);
            }
            else {
                holder.sonuc.setImageResource(R.mipmap.sorun);
            }
        }
        else {
            holder.sonuc.setImageResource(R.mipmap.bos);
        }
    }

    public int getItemCount(){
        return questions.size();
    }
}