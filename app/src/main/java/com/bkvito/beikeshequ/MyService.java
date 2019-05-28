package com.bkvito.beikeshequ;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.memo.deep.openmyeye.R;
import com.memo.deep.openmyeye.ui.activity.TestActivity;

import java.util.ArrayList;
import java.util.List;

public class MyService extends Service {
    public static final String TAG = "MyService";
    public static final String PACKAGE_SAYHI = "com.example.test";
    private NotificationManager mNotificationManager;
    private boolean mCanRun = true;
    private List<Student> mStudents = new ArrayList<>();


    private final IMyService.Stub mBinder = new IMyService.Stub() {
        @Override
        public List<Student> getStudent() throws RemoteException {
            synchronized (mStudents) {
                return mStudents;
            }
        }

        @Override
        public void addStudent(Student student) throws RemoteException {
            synchronized (mStudents) {
                if (!mStudents.contains(student)) {
                    mStudents.add(student);
                }
            }
        }

    };

    @Override
    public void onCreate() {
        Thread thr = new Thread(null, new ServiceWorker(), "BackgroundService");
        thr.start();
        synchronized (mStudents) {
            for (int i = 1; i < 6; i++) {
                Student student = new Student();
                student.name = "student#" + i;
                student.age = i * 5;
                mStudents.add(student);
            }
        }
        mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE) ;
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("===>"+TAG,"=====-=>"+String.format("on bind,intent = %s", intent.toString()));
        displayNotificationMessage("服务已启动");
        return mBinder;
    }

    @Override
    public void onDestroy() {
        mCanRun = false;
        super.onDestroy();
    }

    private void displayNotificationMessage(String message){
        Notification notification = new Notification(R.mipmap.ic_launcher, message, System.currentTimeMillis());
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.defaults|= Notification.DEFAULT_ALL;
        PendingIntent.getActivity(this,0,new Intent(this, TestActivity.class),0);
        mNotificationManager.notify(1,notification);
    }

    class ServiceWorker implements Runnable {
        long counter = 0;

        @Override
        public void run() {
            while (mCanRun) {
                Log.i("===>", "===scott==-=>" + counter);
                counter++;
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
