package com.example.user.asynctaskrotate;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {

    TextView textView;
    MyTask myTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.tv);

        myTask = (MyTask) getLastNonConfigurationInstance();
        if(myTask == null) {
            myTask = new MyTask();
            myTask.execute();
        }
        if(myTask.s != null || myTask.s != ""){
            textView.setText(myTask.s);
        }
        myTask.link(this);
        Log.d("qwe", "create MyTask: " + myTask.hashCode());
    }

    public Object onRetainNonConfigurationInstance(){
        myTask.unlink();
        return myTask;
    }

    static class MyTask extends AsyncTask<String, Integer, Void>{

        MainActivity activity;
        String s;

        void unlink(){
            activity = null;
        }

        void link(MainActivity act){
            activity = act;
        }

        @Override
        protected Void doInBackground(String... params) {
            try{
                for(int i = 1; i < 11; i++) {
                    TimeUnit.SECONDS.sleep(1);
                    publishProgress(i);
                    Log.d("qwe", "i = " + i + ", MyTask: " + this.hashCode()
                        + ", MainActivity: " + activity.hashCode());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... params){
            super.onProgressUpdate(params);
            if(activity == null) {
                s = "i = " + params[0];
            }
            activity.textView.setText("i = " + params[0]);
        }
    }
}
