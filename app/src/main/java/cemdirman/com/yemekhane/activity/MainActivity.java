package cemdirman.com.yemekhane.activity;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import cemdirman.com.yemekhane.R;


public class MainActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private MaterialCalendarView calendarView;
    private FirebaseDatabase firebaseDatabase;
    private String[][] menu;
    private TimePickerDialog timePickerDialog;
TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        getAyet(); //her çalıştığında rastgele Ayet getiriyor.
        setAlarm();//notification için alarm kuruluyor


        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int day = date.getDay();
                int mounth = date.getMonth();
                int year = date.getYear();
                String selectedDay = day + " " + mounth + " " + year;
                selectedDay = selectedDay.replace(" ", "");
                showMenu(selectedDay);
            }
        });

    }

    private void getAyet() {
        String[] Ayetler = new String[5];
        Ayetler[0] = "'Asmalı ve asmasız (üzüm) bahçeleri, hurmaları, ürünleri çeşit çeşit ekinleri, zeytinleri ve narları, birbirine benzer ve benzemez biçimde yaratan O'dur. Her biri meyve verince meyvesinden yiyin, hasat günü de hakkını (zekat ve sadakasını) verin; ama israf etmeyin, çünkü O, israf edenleri sevmez.'-6:141";
        Ayetler[1] = "'Ey Âdemoğulları! Her mescide gidişinizde güzel giysilerinizi giyin ve yiyin, için, fakat israf etmeyin, Çünkü Allah israf edenleri sevmez.'-7:31 ";
        Ayetler[2] = "'Akrabaya, yoksula ve yolda kalmışa hakkını ver. Bununla beraber malını saçıp savurma.'-17:26";
        Ayetler[3] ="'Çünkü (malını) saçıp savuranlar, şeytanların kardeşleridir. Şeytan ise Rabbine karşı çok nankördür.'-17:27 ";
        Ayetler[4] ="'Ve onlar ki, harcadıklarında ne israf ne de cimrilik ederler; ikisi arasında orta bir yol tutarlar.'-25:67";
        Random r = new Random();
        int i = r.nextInt(5);
        t = findViewById(R.id.txtAyet);
        t.setText(Ayetler[i]);

    }

    private void setAlarm() {

        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();

        calSet.set(Calendar.HOUR_OF_DAY, 12);
        calSet.set(Calendar.MINUTE, 0);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);

        if (calSet.compareTo(calNow) <= 0) {

            calSet.add(Calendar.DATE, 1);
        }

        Intent intent = new Intent(getBaseContext(), NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //alarmManager.set(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);


    }

    private void showMenu(String nodeName) {
        LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popup = inflater.inflate(R.layout.popup_yemek, null);
        float density = MainActivity.this.getResources().getDisplayMetrics().density;
        final PopupWindow pw = new PopupWindow(popup, (int) density * 350, (int) density * 500, true);

        final DatabaseReference reference = firebaseDatabase.getReference(nodeName);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() == null) {
                    // pw.dismiss();
                    ((TextView) popup.findViewById(R.id.txtAnayemek1)).setText("Bugün yemek yoktur!");
                    Toast.makeText(getApplicationContext(), "Bugün yemek yok :)!", Toast.LENGTH_SHORT).show();

                } else {
                    DataSnapshot anayemek1 = dataSnapshot.child("anayemek1");
                    DataSnapshot anayemek2 = dataSnapshot.child("anayemek2");
                    DataSnapshot corba = dataSnapshot.child("corba");
                    DataSnapshot tatlı = dataSnapshot.child("tatlı");
                    DataSnapshot salata = dataSnapshot.child("salata");
                    ((TextView) popup.findViewById(R.id.txtAnayemek1)).setText(String.valueOf(anayemek1.child("adı").getValue()) + " " + String.valueOf(anayemek1.child("kalori").getValue()) + " cal " + anayemek1.child("fiyat").getValue() + " TL");
                    ((TextView) popup.findViewById(R.id.txtAnayemek2)).setText(String.valueOf(anayemek2.child("adı").getValue()) + " " + String.valueOf(anayemek2.child("kalori").getValue()) + " cal " + anayemek2.child("fiyat").getValue() + " TL");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

        pw.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    pw.dismiss();
                    return true;
                }
                return false;
            }
        });
        pw.setOutsideTouchable(true);
        // display the pop-up in the center
        pw.showAtLocation(popup, Gravity.CENTER, 0, 0);
    }


    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calendarView = findViewById(R.id.calendarView);

        firebaseConnection();
    }

    private void firebaseConnection() {
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, InfoActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
