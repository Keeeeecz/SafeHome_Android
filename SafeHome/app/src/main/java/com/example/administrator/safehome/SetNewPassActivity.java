package com.example.administrator.safehome;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SetNewPassActivity extends AppCompatActivity {

    private VerifyEmailTask mAuthTask = null;
    EditText getInputString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_pass);
    }
    public void clickSubmit(View view){
        getInputString = (EditText) findViewById(R.id.set_new_pass);
        if(getInputString.getText().toString().length()>4){
            User.setPassword(getInputString.getText().toString());
            mAuthTask = new VerifyEmailTask(getInputString.getText().toString());
            mAuthTask.execute((String) null);
        }else {
            Toast.makeText(SetNewPassActivity.this,"Your new password is to short",Toast.LENGTH_SHORT).show();
        }

    }
    public class VerifyEmailTask extends AsyncTask<String, Void, Boolean> {
        String inputString;

        VerifyEmailTask(String s) {
            inputString = s;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO: attempt authentication against a network service.
            Conn connection = new Conn();
            connection.getCon();
            connection.postNewPass();
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
                Toast.makeText(SetNewPassActivity.this,"Modify accepted",Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(SetNewPassActivity.this,"connect failed",Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}
