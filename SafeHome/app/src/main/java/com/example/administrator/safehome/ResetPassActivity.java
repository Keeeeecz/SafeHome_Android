package com.example.administrator.safehome;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class ResetPassActivity extends AppCompatActivity {
    EditText oldP;
    EditText newP;
    private ResetPassActivity.ResetPassTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        ActivityManager.OnCreateActivity(ResetPassActivity.this); //添加到ActivityManager中
        oldP = (EditText)findViewById(R.id.old_input);
        newP = (EditText)findViewById(R.id.new_input);
    }
    @Override
    protected void onDestroy() {
        ActivityManager.OnDestroyActivity(ResetPassActivity.this); //从ActivityManager中移除
        super.onDestroy();
    }
    public void clickButton(View view){
        String getOldPass = oldP.getText().toString();
        String getNewPass = newP.getText().toString();
        if(getOldPass.equals(User.getPassword())){
            if(getOldPass.equals(getNewPass)){
                Toast.makeText(ResetPassActivity.this, "new password is the same as old password", Toast.LENGTH_SHORT).show();
            }else {
                if(getNewPass.length()>4) {
                    User.setPassword(getNewPass);
                    mAuthTask = new ResetPassTask();
                    mAuthTask.execute((String) null);
                    Toast.makeText(ResetPassActivity.this, "success", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ResetPassActivity.this, "new password is too short", Toast.LENGTH_SHORT).show();
                }
            }
        }else {
            Toast.makeText(ResetPassActivity.this, "old password is incorrect", Toast.LENGTH_SHORT).show();
        }
    }

    public class ResetPassTask extends AsyncTask<String, Void, Boolean> {


        @Override
        protected Boolean doInBackground(String... params) {
            // TODO: attempt authentication against a network service.
            Conn connection = new Conn();
            connection.getCon();
            connection.postNewPass();

            try {
                // Simulate network access.
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return false;
            }

            if(TextUtils.isEmpty(User.getPassword())) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                Intent intent = new Intent();
                intent.setClass(ResetPassActivity.this,LoginActivity.class);
                startActivity(intent);
                List<Activity> list = ActivityManager.GetActivityList();
                for (int position = 0; position < list.size(); position++) {
                    list.get(position).finish();
                }//关闭了其余所有的activity
                finish();//关闭当前activity
            } else {
                Toast.makeText(ResetPassActivity.this, "connect failed", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}
