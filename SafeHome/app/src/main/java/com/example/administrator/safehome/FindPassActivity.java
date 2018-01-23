package com.example.administrator.safehome;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FindPassActivity extends AppCompatActivity {
    EditText answer1,answer2,answer3;
    private  ArrayList<String> mInAnswer;
    private VerifyQuesTask mAuthTask = null;
    TextView quesText1;
    TextView quesText2;
    TextView quesText3;
    ArrayList<String> mQuestion = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pass);
        Intent intent = getIntent();
        mQuestion = intent.getStringArrayListExtra("questions");
        quesText1 = (TextView)findViewById(R.id.ques1);
        quesText2 = (TextView)findViewById(R.id.ques2);
        quesText3 = (TextView)findViewById(R.id.ques3);
        quesText1.setText(mQuestion.get(0));
        quesText2.setText(mQuestion.get(1));
        quesText3.setText(mQuestion.get(2));
    }
    public void submitAnswer(View view){
        mInAnswer = new ArrayList<String>();
        answer1 = (EditText)findViewById(R.id.answer1);
        answer2 = (EditText)findViewById(R.id.answer2);
        answer3 = (EditText)findViewById(R.id.answer3);
        mInAnswer.add(answer1.getText().toString());
        mInAnswer.add(answer2.getText().toString());
        mInAnswer.add(answer3.getText().toString());
        mAuthTask = new VerifyQuesTask(mInAnswer);
        mAuthTask.execute((String) null);
    }
    public class VerifyQuesTask extends AsyncTask<String, Void, Boolean> {

        ArrayList<String> mAnswer = new ArrayList<String>();
        ArrayList<String> mInAnswer = new ArrayList<String>();
        boolean flag[]=new boolean[3];
        VerifyQuesTask(ArrayList<String> a) {
            mInAnswer = a;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO: attempt authentication against a network service.
            Conn connection = new Conn();
            connection.getCon();
            connection.getAns(mAnswer);

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }
            for (int i=0;i<3;i++){
                if(mInAnswer.get(i).equals(mAnswer.get(i))){
                    flag[i] = true;
                }
            }
            if (flag[1]&&flag[2]&&flag[0])
                return true;
            return false;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            if (success){
                Intent intent = new Intent();
                intent.setClass(FindPassActivity.this,SetNewPassActivity.class);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(FindPassActivity.this,"Sorry,wrong answers!",Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}
