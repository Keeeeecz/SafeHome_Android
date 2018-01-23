package com.example.administrator.safehome;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditInfoActivity extends AppCompatActivity {

    private editInfoTask mAuthTask = null;
    EditText name;
    EditText email;
    EditText phone;
    EditText address;
    EditText emergency;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        ActivityManager.OnCreateActivity(EditInfoActivity.this); //添加到ActivityManager中
        name = (EditText)findViewById(R.id.edit_name);
        email = (EditText)findViewById(R.id.edit_email);
        phone = (EditText)findViewById(R.id.edit_phone);
        address = (EditText)findViewById(R.id.edit_address);
        emergency = (EditText)findViewById(R.id.edit_emyNum);
        name.setText(User.getUserName());
        email.setText(User.getEmail());
        phone.setText(String.valueOf(User.getPhoneNum()));
        address.setText(User.getAddress());
        emergency.setText(String.valueOf(User.getEmergencyNum()));
    }
    protected void onDestroy() {
        ActivityManager.OnDestroyActivity(EditInfoActivity.this); //从ActivityManager中移除
        super.onDestroy();
    }
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setClass(this,MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
    protected void clickApply(View view){
        User.setUserName(name.getText().toString());
        User.setEmail(email.getText().toString());
        try {
            User.setPhoneNum(Integer.parseInt(phone.getText().toString()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        User.setEmergencyNum(emergency.getText().toString());
        User.setAddress(address.getText().toString());

        mAuthTask = new editInfoTask();
        mAuthTask.execute((String) null);

    }
    public class editInfoTask extends AsyncTask<String, Void, Boolean> {

        editInfoTask() {
        }

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO: attempt authentication against a network service.
            Conn connection = new Conn();
            connection.getCon();
            connection.postNewInfo();

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }
            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            if (success){
                Toast.makeText(EditInfoActivity.this,"modify accepted",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(EditInfoActivity.this,"modify failed",Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}
