package br.edu.utfpr.rogerio.inspalarm.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import br.edu.utfpr.rogerio.inspalarm.AlarmReceiver;
import br.edu.utfpr.rogerio.inspalarm.DB.SchedulesDB;
import br.edu.utfpr.rogerio.inspalarm.R;
import br.edu.utfpr.rogerio.inspalarm.model.Schedule;

public class NewScheduleActivity extends AppCompatActivity {

    private static final String COLOR = "COLOR";

    private static NewScheduleActivity instance;

    private Calendar calendar = null;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TimePicker timePicker;  //O time picker foi pego de um xml já existente, parece que esse componente não aparece mais na paleta de elementos.
    private ToggleButton toggleButton;

    public static NewScheduleActivity getInstance() {
        return instance;
    }

    @Override
    public void onStart() {
        super.onStart();
        instance = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_schedule);
        setTitle("New Alarm");

        //Guardando referencias para o os elementos usados
        timePicker = findViewById(R.id.timePicker);
        toggleButton = findViewById(R.id.toggleButton);

        //Coloca a cor anteriormente selecionada, caso nenhuma tenha sido selecionada, é amarelo
        getSavedColor();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_schedule_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.set_color_red:
                setAndSaveColor(Color.RED);
                break;

            case R.id.set_color_blue:
                setAndSaveColor(Color.BLUE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getSavedColor(){
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(this);
        setBackgroundColor(shared.getInt(COLOR,Color.YELLOW));
    }

    private void setAndSaveColor(int color){

        setBackgroundColor(color);
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = shared.edit();
        editor.putInt(COLOR, color);
        editor.commit();
    }

    private void setBackgroundColor(int color)
    {
        timePicker.setBackgroundColor(color);
    }

    public void onToggleClicked(View view) {
        ToggleAlarm();
    }

    private void ToggleAlarm() {
        //Caso o estado do botão após ser clicado seja true
        if (toggleButton.isChecked()) {

            //Guardando a instancia do calendario para ser manipulado.
            calendar = Calendar.getInstance();

            //Setando para hora escolhida no TimePicker na variavel criada.
            calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
            calendar.set(Calendar.MINUTE, timePicker.getMinute());
            calendar.set(Calendar.SECOND, 0);   //Uma coisa tão simples dando tanto trabalho.


            //Criando um intent para setar o alarme
            Intent myIntent = new Intent(NewScheduleActivity.this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, myIntent, 0);

            //Setamos o alarme para tocar na hora escolhida
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            //Coloca o novo alarme criado no BD
            CreateSchedule(timePicker.getHour(), timePicker.getMinute());

            //Da um feedback que o alarme foi criado
            MakeToast(1, timePicker.getHour(), timePicker.getMinute());

            Log.d(getString(R.string.user_action), getString(R.string.log_alarm_on));

        } else {

            //Cancela o alarme antes setado.
            alarmManager.cancel(pendingIntent);

            //Retira o BD
            DeleteSchedule(timePicker.getHour(), timePicker.getMinute());

            //Da um feedback que o alarme foi removido
            MakeToast(2, timePicker.getHour(), timePicker.getMinute());

            Log.d(getString(R.string.user_action), getString(R.string.log_alarm_off));
        }
    }

    public void MakeToast(int type, int hour, int minute)
    {
        String msg;
        switch(type)
        {
            case 1:
                msg = getBaseContext().getString(R.string.schedule_created);
                if(minute > 10)
                    Toast.makeText(instance,  msg + "\n" + Integer.toString(hour) + ":" + Integer.toString(minute) , Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(instance, msg + "\n" + Integer.toString(hour) + ":0" + Integer.toString(minute) , Toast.LENGTH_SHORT).show();
                break;
            case 2:
                msg = getBaseContext().getString(R.string.schedule_deleted);
                if(minute > 10)
                    Toast.makeText(instance,  msg + "\n" + Integer.toString(hour) + ":" + Integer.toString(minute) , Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(instance, msg + "\n" + Integer.toString(hour) + ":0" + Integer.toString(minute) , Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    //Os argumentos precisam ser final para serem usados nesses metodos
    public void CreateSchedule(final int hour, final int minute)
    {
        //Random de [0,3]
        final int random = new Random().nextInt(3) + 1;

        new Thread(new Runnable() {
            @Override
            public void run() {
                Schedule newSchedule = new Schedule(hour, minute, random);
                SchedulesDB.getDatabase(NewScheduleActivity.this).scheduleDAO().insert(newSchedule);
            }
        }) .start();
    }

    public void DeleteSchedule(final int hour, final int minute)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Schedule> schedules = SchedulesDB.getDatabase(NewScheduleActivity.this).scheduleDAO().queryAll();

                for (Schedule schedule : schedules)
                {
                    if(schedule.getScheduleHour() == hour && schedule.getScheduleMinute() == minute)
                        SchedulesDB.getDatabase(NewScheduleActivity.this).scheduleDAO().delete(schedule);
                }
            }
        }) .start();
    }

    public void UpdateSchedule(final int hour, final int minute, final int finalHour, final int finalMinute)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Schedule> schedules = SchedulesDB.getDatabase(NewScheduleActivity.this).scheduleDAO().queryAll();

                for (Schedule schedule : schedules)
                {
                    if(schedule.getScheduleHour() == hour && schedule.getScheduleMinute() == minute)
                    {
                        schedule.setScheduleHour(finalHour);
                        schedule.setScheduleMinute(finalMinute);
                        SchedulesDB.getDatabase(NewScheduleActivity.this).scheduleDAO().update(schedule);
                    }
                }
            }
        }) .start();
    }

    public void DeleteAll()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Schedule> schedules = SchedulesDB.getDatabase(NewScheduleActivity.this).scheduleDAO().queryAll();

                for (Schedule schedule : schedules)
                {
                    SchedulesDB.getDatabase(NewScheduleActivity.this).scheduleDAO().delete(schedule);
                }
            }
        }) .start();
    }

}