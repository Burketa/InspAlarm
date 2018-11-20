package br.edu.utfpr.rogerio.inspalarm.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.edu.utfpr.rogerio.inspalarm.DB.SchedulesDB;
import br.edu.utfpr.rogerio.inspalarm.R;
import br.edu.utfpr.rogerio.inspalarm.model.Schedule;

public class MainActivity extends AppCompatActivity {

    private ListView scheduleListView;
    private ArrayAdapter<Schedule> listAdapter;
    private List<Schedule> list;

    @Override
    public void onStart() {
        super.onStart();
        scheduleListView = findViewById(R.id.schedule_list_view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Popula o listView com os Schedules do BD
        ListSchedules();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.alarm_new:
                Intent newAlarmIntent = new Intent(this, NewScheduleActivity.class);
                startActivity(newAlarmIntent);
                break;

            case R.id.quote_new:
                Intent newQuoteIntent = new Intent(this, QuoteActivity.class);
                startActivity(newQuoteIntent);
                break;

            case R.id.credits_button:
                Intent creditsIntent = new Intent(this, CreditsActivity.class);
                startActivity(creditsIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ListSchedules() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                SchedulesDB db = SchedulesDB.getDatabase(MainActivity.this);

                list = db.scheduleDAO().queryAll();

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listAdapter = new ArrayAdapter<>(MainActivity.this,
                                android.R.layout.simple_list_item_1,
                                list);

                        scheduleListView.setAdapter(listAdapter);
                    }
                });
            }
        });
    }
}