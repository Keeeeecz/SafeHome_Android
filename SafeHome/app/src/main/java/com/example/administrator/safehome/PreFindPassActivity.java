package com.example.administrator.safehome;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class PreFindPassActivity extends AppCompatActivity {

    private VerifyEmailTask mAuthTask = null;
    EditText getInputString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_find_pass);
    }
    public void clickSubmit(View view){
        getInputString = (EditText) findViewById(R.id.verify_email);
        String s = getInputString.getText().toString();
        mAuthTask = new VerifyEmailTask(s);
        mAuthTask.execute((String) null);
    }
    public class VerifyEmailTask extends AsyncTask<String, Void, Boolean> {
        ArrayList<String> mEmail = new ArrayList<String>();
        ArrayList<String> mQuestion = new ArrayList<String>();
        String inputString;

        VerifyEmailTask(String s) {
            inputString = s;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO: attempt authentication against a network service.
            Conn connection = new Conn();
            connection.getCon();
            connection.getEmail(mEmail);

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }
            for (String s:mEmail){
                if(s.equals(inputString)){
                    User.setEmail(s);
                    connection.getQues(mQuestion);
                    return true;
                }
            }
            return false;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            if (success){
                Intent intent = new Intent();
                intent.setClass(PreFindPassActivity.this,FindPassActivity.class);
                intent.putStringArrayListExtra("questions",mQuestion);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(PreFindPassActivity.this,"error email",Toast.LENGTH_SHORT).show();}
        }
        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}
