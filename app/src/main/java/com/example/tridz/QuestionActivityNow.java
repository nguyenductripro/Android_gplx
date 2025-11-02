package com.example.tridz;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tridz.dbclass.Question;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class QuestionActivityNow extends QuestionActivityBase {
    private Button next;
    private TextView explain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_question_now);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        String topic=intent.getStringExtra("name");
        if(id.equals("topic")) topicname.setText(topic);
        else topicname.setText("Hạng "+topic);
        backSetup(QuestionActivityNow.this);
        setting(QuestionActivityNow.this);
        submitSetup(QuestionActivityNow.this);
    }
    @Override
    protected void init(){
        super.init();
        find_view();
    }
    private void find_view() {
        topicname=findViewById(R.id.txtTopicQAN);
        content=findViewById(R.id.txtQANcontent);
        a=findViewById(R.id.radiobtnQANa);
        b=findViewById(R.id.radiobtnQANb);
        c=findViewById(R.id.radiobtnQANc);
        d=findViewById(R.id.radiobtnQANd);
        next=findViewById(R.id.btnnextQAN);
        radioGroup=findViewById(R.id.radioBtnQAN);
        imgQuestion=findViewById(R.id.imgQAN);
        explain=findViewById(R.id.txtQANexplain);
        submit=findViewById(R.id.btnQAN_submit);
        back=findViewById(R.id.btnBackQAN);
    }
    @Override
    protected void setting(Context context) {
        super.setting(context);
        set_content(listQuestion.get(0), context);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=radioGroup.getCheckedRadioButtonId();
                if(id==-1){
                    Toast.makeText(QuestionActivityNow.this, "Hãy chọn đáp án trước", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(next.getText().toString().equals("Kiểm tra")){
                    explain.setText("Giải thích: "+explaination);
                    next.setText("Câu tiếp theo");
                    setCheckColor(listQuestion.get(anInt),QuestionActivityNow.this);
                    return;
                }
                anInt++;
                if(anInt>=listQuestion.size()) {
                    return;
                }
                else{
                    explain.setText("");
                    next.setText("Kiểm tra");
                    resetCheckColor();
                    set_content(listQuestion.get(anInt),context);

                }
            }
        });

    }
    @Override
    protected void set_content(Question question, Context context) {
        super.set_content(question,context);
        radioGroup.clearCheck();
        question.setUserChoice(null);
        listQuestion.get(anInt).setUserChoice(null);
    }
    private void setCheckColor(Question question,Context context){
        String ans=question.getAnswer();
        if(ans.equals(question.getA())){
            a.setBackground(context.getDrawable(R.drawable.bg_true));
        }
        else if(ans.equals(question.getB())){
            b.setBackground(context.getDrawable(R.drawable.bg_true));
        }
        if(ans.equals(question.getC())){
            c.setBackground(context.getDrawable(R.drawable.bg_true));
        }
        if(ans.equals(question.getD())){
            d.setBackground(context.getDrawable(R.drawable.bg_true));
        }
        if(question.getUserChoice()!=null){
            if(!ans.equals(question.getUserChoice())){
                if(question.getUserChoice().equals(question.getA())){
                    a.setBackground(context.getDrawable(R.drawable.bg_false));
                }
                else if(question.getUserChoice().equals(question.getB())){
                    b.setBackground(context.getDrawable(R.drawable.bg_false));
                }
                if(question.getC()!=null&&question.getUserChoice().equals(question.getC())){
                    c.setBackground(context.getDrawable(R.drawable.bg_false));
                }
                if(question.getD()!=null&&question.getUserChoice().equals(question.getD())){
                    d.setBackground(context.getDrawable(R.drawable.bg_false));
                }
            }
        }
    }
    private  void resetCheckColor(){
        a.setBackgroundColor(Color.WHITE);
        b.setBackgroundColor(Color.WHITE);
        c.setBackgroundColor(Color.WHITE);
        d.setBackgroundColor(Color.WHITE);
    }
}