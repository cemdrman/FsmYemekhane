package cemdirman.com.yemekhane.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import cemdirman.com.yemekhane.R;

/**
 * Created by aydog on 10.12.2017.
 */

public class NotificationReceiver extends BroadcastReceiver {
    static public NotificationCompat.Builder builder;
    static public NotificationManager notificationManager;
    SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
    static public RemoteViews remoteViews;


    @Override
    public void onReceive(Context context, Intent intent) {
        Date currentTime;
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+03:00"));

        currentTime = Calendar.getInstance().getTime();
        String time = sdf.format(currentTime);
        System.out.println(time + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        if (!time.equals("Cumartesi") && !time.equals("Pazar")) {
            if (!time.equals("Saturday") && !time.equals("Sunday")) {
                notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                remoteViews = new RemoteViews(context.getPackageName(), R.layout.custom_notification);
                Intent notificationIntent = new Intent(context, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
                builder = new NotificationCompat.Builder(context);
                builder.setSmallIcon(R.mipmap.ic_launcher).setAutoCancel(true).setCustomContentView(remoteViews).setContentIntent(pendingIntent);
                notificationManager.notify(0, builder.build());
            }
        }


    }

}