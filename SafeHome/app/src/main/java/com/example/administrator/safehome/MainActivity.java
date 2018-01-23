package com.example.administrator.safehome;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    long lastBack = 0;
    TextView showEmail;
    TextView showPhoneNum;
    TextView showUsername;
    private  NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityManager.OnCreateActivity(MainActivity.this); //添加到ActivityManager中
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
        showUsername = (TextView)headerLayout.findViewById(R.id.showUser);
        showPhoneNum = (TextView)headerLayout.findViewById(R.id.showPhone);
        showEmail = (TextView)headerLayout.findViewById(R.id.showEmail);
        showUsername.setText(User.getUserName());
        showPhoneNum.setText(String.valueOf(User.getPhoneNum()));
        showEmail.setText(User.getEmail());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onDestroy() {
        ActivityManager.OnDestroyActivity(MainActivity.this); //从ActivityManager中移除
        super.onDestroy();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (lastBack == 0 || System.currentTimeMillis() - lastBack > 2000) {
                Toast.makeText(MainActivity.this, "press back again to quit", Toast.LENGTH_SHORT).show();
                lastBack = System.currentTimeMillis();
                return;
            }
            List<Activity> list = ActivityManager.GetActivityList();
            for (int position = 0; position < list.size(); position++) {
                list.get(position).finish();
            }//关闭了其余所有的activity
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_editInfo) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,EditInfoActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_resetPassword) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,ResetPassActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_egy) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+User.getEmergencyNum()));
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "don't have permission to call", Toast.LENGTH_SHORT).show();
            }
            startActivity(intent);

        }else if (id == R.id.nav_aboutUs) {
            new AlertDialog.Builder(this)
                    .setTitle("About us")
                    .setIcon(R.drawable.favorites)
                    .setMessage("This app developed in 12/2017\n" +
                            "If you hava some suggestions or problems,please tell us\n\nemail: 1065486204@qq.com").create().show();
        } else if (id == R.id.nav_version) {
            new AlertDialog.Builder(this)
                    .setTitle("version")
                    .setIcon(R.drawable.information)
                    .setMessage("\n    version 3.5.3").create().show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void clickBedroom(View view){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,BedroomActivity.class);
        intent.putExtra("pagecount","bedroom");
        startActivity(intent);
    }
    public void clickLivingRoom(View view){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,BedroomActivity.class);
        intent.putExtra("pagecount","livingroom");
        startActivity(intent);
    }
    public void clickKitchen(View view){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,BedroomActivity.class);
        intent.putExtra("pagecount","kitchen");
        startActivity(intent);
    }
    public void clickOther(View view){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,BedroomActivity.class);
        intent.putExtra("pagecount","other");
        startActivity(intent);
    }

}
