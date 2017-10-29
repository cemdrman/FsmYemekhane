package cemdirman.com.yemekhane.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Iterator;

import cemdirman.com.yemekhane.R;
import cemdirman.com.yemekhane.model.Yemek;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private MaterialCalendarView calendarView;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int day = date.getDay();
                int mounth = date.getMonth();
                int year = date.getYear();
                String selectedDay =  day +" " + mounth +" " + year;
                selectedDay = selectedDay.replace(" ",""); 
                readData(selectedDay);
            }
        });

    }

    private void init(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calendarView = findViewById(R.id.calendarView);

        firebaseConnection();
    }

    private void firebaseConnection(){
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    private void readData(String nodeName){
        final DatabaseReference reference = firebaseDatabase.getReference(nodeName);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot anayemek1 = dataSnapshot.child("anayemek1");
                DataSnapshot anayemek2 = dataSnapshot.child("anayemek2");
                DataSnapshot corba = dataSnapshot.child("corba");
                DataSnapshot tatlı = dataSnapshot.child("tatlı");
                DataSnapshot salata = dataSnapshot.child("salata");

                System.out.println(anayemek1.child("adı").getValue());
                System.out.println(anayemek1.child("fiyat").getValue());
                System.out.println(anayemek1.child("kalori").getValue());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
            Intent intent = new Intent(this,InfoActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
