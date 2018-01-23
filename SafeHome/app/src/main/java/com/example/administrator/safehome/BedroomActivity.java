package com.example.administrator.safehome;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class BedroomActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private static String mSensor1;
    private static String mSensor2;
    public static String pagecount;
    private static BedroomActivity.getSensorTask mAuthTask = null;
    public static ArrayList<Room> rooms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bedroom);
        Intent inte = getIntent();
        pagecount = inte.getStringExtra("pagecount");

        rooms = new ArrayList<Room>();
        for(int i=0;i<4;i++){
            rooms.add(new Room());
        }
        mAuthTask = new getSensorTask();
        mAuthTask.execute((String) null);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        // tabLayout使用viewPager接收的mSectionsPagerAdapter里设置的title
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                // 指定开启系统摄像机的Action
                intent.setAction("android.media.action.VIDEO_CAPTURE");
                intent.addCategory("android.intent.category.DEFAULT");

                // 保存录像到指定的路径
                File file = new File("/sdcard/1/video.mp4");
                Uri uri = Uri.fromFile(file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            return;
        }
    }
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static PostStateTask mPostTask = null;
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View roomView=null;
            TextView tempView;
            final TextView doorView;
            final TextView windowView;
            Button door;
            Button window;
            final int currentNum = getArguments().getInt(ARG_SECTION_NUMBER);

            ArrayList<View> views = new ArrayList<View>();
            View roomView1 = inflater.inflate(R.layout.fragment_bedroom, container, false);
            View roomView2 = inflater.inflate(R.layout.fragment_livingroom, container, false);
            View roomView3 = inflater.inflate(R.layout.fragment_kitchen, container, false);
            View roomView4 = inflater.inflate(R.layout.fragment_other, container, false);
            views.add(roomView1);
            views.add(roomView2);
            views.add(roomView3);
            views.add(roomView4);
            String[] getRooms = {"bedroom","livingroom","kitchen","other"};
            for (int i=0;i<4;i++){
                if(getRooms[i].equals(pagecount)){
                    View temp = views.get(0);
                    views.set(0,views.get(i));
                    views.set(i,temp);
                    Room tempr = rooms.get(0);
                    rooms.set(0,rooms.get(i));
                    rooms.set(i,tempr);
                }
            }
            for(int i=0;i<4;i++) {
                if(views.get(i)==roomView1)
                    rooms.get(i).setTemprature(mSensor1);
                if(views.get(i)==roomView2)
                    rooms.get(i).setTemprature(mSensor2);
            }
            switch (currentNum){
                case 1:
                    roomView = views.get(currentNum-1);
                    tempView = (TextView) roomView.findViewById(R.id.show_temprature);
                    tempView.setText("current temprature: "+rooms.get(currentNum-1).getTemprature());
                    doorView = (TextView) roomView.findViewById(R.id.show_doorOpened);
                    doorView.setText("door opened: "+String.valueOf(rooms.get(currentNum-1).getDoorState()));
                    windowView = (TextView) roomView.findViewById(R.id.show_windowOpened);
                    windowView.setText("window opened: "+String.valueOf(rooms.get(currentNum-1).getWindowState()));
                    door = (Button) roomView.findViewById(R.id.door_op);
                    door.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            rooms.get(currentNum-1).setDoorState(!rooms.get(currentNum-1).getDoorState());
                            mPostTask = new PostStateTask();
                            mPostTask.execute((String)null);
                            doorView.setText("door opened: "+String.valueOf(rooms.get(currentNum-1).getDoorState()));
                            if(rooms.get(currentNum-1).getDoorState()){doorView.setTextColor(getResources().getColor(android.R.color.holo_red_light));}
                            if(!rooms.get(currentNum-1).getDoorState()){doorView.setTextColor(getResources().getColor(android.R.color.darker_gray));}
                        }
                    });
                    window = (Button) roomView.findViewById(R.id.window_op);
                    window.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            rooms.get(currentNum-1).setWindowState(!rooms.get(currentNum-1).getWindowState());
                            mPostTask = new PostStateTask();
                            mPostTask.execute((String)null);
                            windowView.setText("window opened: "+String.valueOf(rooms.get(currentNum-1).getWindowState()));
                            if(rooms.get(currentNum-1).getWindowState()){windowView.setTextColor(getResources().getColor(android.R.color.holo_red_light));}
                            if(!rooms.get(currentNum-1).getWindowState()){windowView.setTextColor(getResources().getColor(android.R.color.darker_gray));}
                        }
                    });
                    if(rooms.get(currentNum-1).getDoorState()){doorView.setTextColor(getResources().getColor(android.R.color.holo_red_light));}
                    if(!rooms.get(currentNum-1).getDoorState()){doorView.setTextColor(getResources().getColor(android.R.color.darker_gray));}
                    if(rooms.get(currentNum-1).getWindowState()){windowView.setTextColor(getResources().getColor(android.R.color.holo_red_light));}
                    if(!rooms.get(currentNum-1).getWindowState()){windowView.setTextColor(getResources().getColor(android.R.color.darker_gray));}
                    try {
                        if(Double.valueOf(rooms.get(currentNum-1).getTemprature())>40){tempView.setTextColor(getResources().getColor(android.R.color.holo_red_light));}
                        if(Double.valueOf(rooms.get(currentNum-1).getTemprature())<=40){tempView.setTextColor(getResources().getColor(android.R.color.black));}
                    } catch (NumberFormatException e){
                        e.printStackTrace();
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    roomView = views.get(currentNum-1);
                    tempView = (TextView) roomView.findViewById(R.id.show_temprature);
                    tempView.setText("current temprature: "+rooms.get(currentNum-1).getTemprature());
                    doorView = (TextView) roomView.findViewById(R.id.show_doorOpened);
                    doorView.setText("door opened: "+String.valueOf(rooms.get(currentNum-1).getDoorState()));
                    windowView = (TextView) roomView.findViewById(R.id.show_windowOpened);
                    windowView.setText("window opened: "+String.valueOf(rooms.get(currentNum-1).getWindowState()));
                    door = (Button) roomView.findViewById(R.id.door_op);
                    door.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            rooms.get(currentNum-1).setDoorState(!rooms.get(currentNum-1).getDoorState());
                            mPostTask = new PostStateTask();
                            mPostTask.execute((String)null);
                            doorView.setText("door opened: "+String.valueOf(rooms.get(currentNum-1).getDoorState()));
                            if(rooms.get(currentNum-1).getDoorState()){doorView.setTextColor(getResources().getColor(android.R.color.holo_red_light));}
                            if(!rooms.get(currentNum-1).getDoorState()){doorView.setTextColor(getResources().getColor(android.R.color.darker_gray));}
                        }
                    });
                    window = (Button) roomView.findViewById(R.id.window_op);
                    window.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            rooms.get(currentNum-1).setWindowState(!rooms.get(currentNum-1).getWindowState());
                            mPostTask = new PostStateTask();
                            mPostTask.execute((String)null);
                            windowView.setText("window opened: "+String.valueOf(rooms.get(currentNum-1).getWindowState()));
                            if(rooms.get(currentNum-1).getWindowState()){windowView.setTextColor(getResources().getColor(android.R.color.holo_red_light));}
                            if(!rooms.get(currentNum-1).getWindowState()){windowView.setTextColor(getResources().getColor(android.R.color.darker_gray));}
                        }
                    });
                    if(rooms.get(currentNum-1).getDoorState()){doorView.setTextColor(getResources().getColor(android.R.color.holo_red_light));}
                    if(!rooms.get(currentNum-1).getDoorState()){doorView.setTextColor(getResources().getColor(android.R.color.darker_gray));}
                    if(rooms.get(currentNum-1).getWindowState()){windowView.setTextColor(getResources().getColor(android.R.color.holo_red_light));}
                    if(!rooms.get(currentNum-1).getWindowState()){windowView.setTextColor(getResources().getColor(android.R.color.darker_gray));}
                    try {
                        if(Double.valueOf(rooms.get(currentNum-1).getTemprature())>40){tempView.setTextColor(getResources().getColor(android.R.color.holo_red_light));}
                        if(Double.valueOf(rooms.get(currentNum-1).getTemprature())<=40){tempView.setTextColor(getResources().getColor(android.R.color.black));}
                    } catch (NumberFormatException e){
                        e.printStackTrace();
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    roomView = views.get(currentNum-1);
                    tempView = (TextView) roomView.findViewById(R.id.show_temprature);
                    tempView.setText("current temprature: "+rooms.get(currentNum-1).getTemprature());
                    doorView = (TextView) roomView.findViewById(R.id.show_doorOpened);
                    doorView.setText("door opened: "+String.valueOf(rooms.get(currentNum-1).getDoorState()));
                    windowView = (TextView) roomView.findViewById(R.id.show_windowOpened);
                    windowView.setText("window opened: "+String.valueOf(rooms.get(currentNum-1).getWindowState()));
                    door = (Button) roomView.findViewById(R.id.door_op);
                    door.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            rooms.get(currentNum-1).setDoorState(!rooms.get(currentNum-1).getDoorState());
                            mPostTask = new PostStateTask();
                            mPostTask.execute((String)null);
                            doorView.setText("door opened: "+String.valueOf(rooms.get(currentNum-1).getDoorState()));
                            if(rooms.get(currentNum-1).getDoorState()){doorView.setTextColor(getResources().getColor(android.R.color.holo_red_light));}
                            if(!rooms.get(currentNum-1).getDoorState()){doorView.setTextColor(getResources().getColor(android.R.color.darker_gray));}
                        }
                    });
                    window = (Button) roomView.findViewById(R.id.window_op);
                    window.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            rooms.get(currentNum-1).setWindowState(!rooms.get(currentNum-1).getWindowState());
                            mPostTask = new PostStateTask();
                            mPostTask.execute((String)null);
                            windowView.setText("window opened: "+String.valueOf(rooms.get(currentNum-1).getWindowState()));
                            if(rooms.get(currentNum-1).getWindowState()){windowView.setTextColor(getResources().getColor(android.R.color.holo_red_light));}
                            if(!rooms.get(currentNum-1).getWindowState()){windowView.setTextColor(getResources().getColor(android.R.color.darker_gray));}
                        }
                    });
                    if(rooms.get(currentNum-1).getDoorState()){doorView.setTextColor(getResources().getColor(android.R.color.holo_red_light));}
                    if(!rooms.get(currentNum-1).getDoorState()){doorView.setTextColor(getResources().getColor(android.R.color.darker_gray));}
                    if(rooms.get(currentNum-1).getWindowState()){windowView.setTextColor(getResources().getColor(android.R.color.holo_red_light));}
                    if(!rooms.get(currentNum-1).getWindowState()){windowView.setTextColor(getResources().getColor(android.R.color.darker_gray));}
                    try {
                        if(Double.valueOf(rooms.get(currentNum-1).getTemprature())>40){tempView.setTextColor(getResources().getColor(android.R.color.holo_red_light));}
                        if(Double.valueOf(rooms.get(currentNum-1).getTemprature())<=40){tempView.setTextColor(getResources().getColor(android.R.color.black));}
                    } catch (NumberFormatException e){
                        e.printStackTrace();
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    roomView = views.get(currentNum-1);
                    tempView = (TextView) roomView.findViewById(R.id.show_temprature);
                    tempView.setText("current temprature: "+rooms.get(currentNum-1).getTemprature());
                    doorView = (TextView) roomView.findViewById(R.id.show_doorOpened);
                    doorView.setText("door opened: "+String.valueOf(rooms.get(currentNum-1).getDoorState()));
                    windowView = (TextView) roomView.findViewById(R.id.show_windowOpened);
                    windowView.setText("window opened: "+String.valueOf(rooms.get(currentNum-1).getWindowState()));
                    door = (Button) roomView.findViewById(R.id.door_op);
                    door.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            rooms.get(currentNum-1).setDoorState(!rooms.get(currentNum-1).getDoorState());
                            mPostTask = new PostStateTask();
                            mPostTask.execute((String)null);
                            doorView.setText("door opened: "+String.valueOf(rooms.get(currentNum-1).getDoorState()));
                            if(rooms.get(currentNum-1).getDoorState()){doorView.setTextColor(getResources().getColor(android.R.color.holo_red_light));}
                            if(!rooms.get(currentNum-1).getDoorState()){doorView.setTextColor(getResources().getColor(android.R.color.darker_gray));}
                        }
                    });
                    window = (Button) roomView.findViewById(R.id.window_op);
                    window.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            rooms.get(currentNum-1).setWindowState(!rooms.get(currentNum-1).getWindowState());
                            mPostTask = new PostStateTask();
                            mPostTask.execute((String)null);
                            windowView.setText("window opened: "+String.valueOf(rooms.get(currentNum-1).getWindowState()));
                            if(rooms.get(currentNum-1).getWindowState()){windowView.setTextColor(getResources().getColor(android.R.color.holo_red_light));}
                            if(!rooms.get(currentNum-1).getWindowState()){windowView.setTextColor(getResources().getColor(android.R.color.darker_gray));}
                        }
                    });
                    if(rooms.get(currentNum-1).getDoorState()){doorView.setTextColor(getResources().getColor(android.R.color.holo_red_light));}
                    if(!rooms.get(currentNum-1).getDoorState()){doorView.setTextColor(getResources().getColor(android.R.color.darker_gray));}
                    if(rooms.get(currentNum-1).getWindowState()){windowView.setTextColor(getResources().getColor(android.R.color.holo_red_light));}
                    if(!rooms.get(currentNum-1).getWindowState()){windowView.setTextColor(getResources().getColor(android.R.color.darker_gray));}
                    try {
                        if(Double.valueOf(rooms.get(currentNum-1).getTemprature())>40){tempView.setTextColor(getResources().getColor(android.R.color.holo_red_light));}
                        if(Double.valueOf(rooms.get(currentNum-1).getTemprature())<=40){tempView.setTextColor(getResources().getColor(android.R.color.black));}
                    } catch (NumberFormatException e){
                        e.printStackTrace();
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                    break;
            }
            return roomView;
        }
        public class PostStateTask extends AsyncTask<String, Void, Boolean> {
            protected Boolean doInBackground(String... params) {
                // TODO: attempt authentication against a network service.
                Conn connection = new Conn();
                connection.getCon();
                connection.postState();
                try {
                    // Simulate network access.
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return false;
                }

                return true;
            }

            @Override
            protected void onPostExecute(final Boolean success) {
                mPostTask = null;
                if (success) {
                    System.out.print("\n\nsuccess\n\n");
                } else {
                    System.out.print("\n\nfailed\n\n");
                }
            }

            @Override
            protected void onCancelled() {
                mPostTask = null;
            }

        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            String title = "Section";
            String temp;
            String[] getRoomsName = {"bedroom","livingroom","kitchen","other"};
            if(pagecount.equals("bedroom")){

            }else if (pagecount.equals("livingroom")){
               temp = getRoomsName[0];
               getRoomsName[0] = getRoomsName[1];
               getRoomsName[1] = temp;
            }else if (pagecount.equals("kitchen")){
                temp = getRoomsName[0];
                getRoomsName[0] = getRoomsName[2];
                getRoomsName[2] = temp;
            }else if (pagecount.equals("other")){
                temp = getRoomsName[0];
                getRoomsName[0] = getRoomsName[3];
                getRoomsName[3] = temp;
            }
            title = getRoomsName[position];
            return title;
        }
    }
    public class getSensorTask extends AsyncTask<String, Void, Boolean> {

        ArrayList<String> tmp1 = new ArrayList<String>();
        ArrayList<String> tmp2 = new ArrayList<String>();
        ArrayList<String> windows = new ArrayList<String>();
        ArrayList<String> doors = new ArrayList<String>();
        boolean bdoor[] = new boolean[4];
        boolean bwindow[] = new boolean[4];
        getSensorTask(){
            for(int i=0;i<4;i++){
                doors.add(new String());
                windows.add(new String());
            }
        }

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO: attempt authentication against a network service.
            Conn connection = new Conn();
            connection.getCon();
            connection.getTmp(tmp1,tmp2);
            connection.getDW(doors,windows);
            mSensor1 = tmp1.get(0);
            mSensor2 = tmp2.get(0);

            for(int i=0;i<4;i++){
                if(doors.get(i).equals("closed")){bdoor[i]=false;}
                else {bdoor[i]=true;}
            }
            for (int i=0;i<4;i++){
                if(windows.get(i).equals("closed")){bwindow[i]=false;}
                else {bwindow[i]=true;}
            }
            for (int i=0;i<4;i++){
                rooms.get(i).setDoorState(bdoor[i]);
                rooms.get(i).setWindowState(bwindow[i]);
            }

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
//                Toast.makeText(BedroomActivity.this, "connected", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(BedroomActivity.this, "connect failed", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}
