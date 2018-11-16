package br.edu.utfpr.rogerio.inspalarm;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;
import java.util.Calendar;

public class NewScheduleActivity extends AppCompatActivity {

    private static final String COLOR = "COLOR";

    private static NewScheduleActivity instance;

    private Calendar calendar = null;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TimePicker timePicker;  //O time picker foi pego de um xml já existente, parece que esse componente não aparece mais na paleta de elementos.
    private TextView alarmText;
    private ToggleButton toggleButton;
    private TextView activeAlarm;
    private ImageView alarmImage;

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

        //Guardando referencias para o os elementos usados
        timePicker = findViewById(R.id.timePicker);
        alarmText = findViewById(R.id.alarmText);
        toggleButton = findViewById(R.id.toggleButton);
        activeAlarm = findViewById(R.id.activeAlarm);
        alarmImage = findViewById(R.id.imageView);

        //Coloca a cor anteriormente selecionada, caso nenhuma tenha sido selecionada, é amarelo
        getSavedColor();

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
                //Va para activity do time picker, a ser implementada na fase 2
                break;

            case R.id.credits_button:
                Intent intent = new Intent(this, CreditsActivity.class);
                startActivity(intent);
                break;

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

    public void setAlarmText(String text) {
        this.alarmText.setText(text);
    }

    public void setActiveAlarmText(String text) {
        this.activeAlarm.setText(text);
    }

    public void setAlarmImageVisibility(boolean state)
    {
        if(state)
            alarmImage.setVisibility(View.VISIBLE);
        else
            alarmImage.setVisibility(View.INVISIBLE);
    }

    public void onToggleClicked(View view) {
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

            //Mudando o texto do alarme ativo
            activeAlarm.setText(getString(R.string.alarm_set) + " " + timePicker.getHour() + ":" + timePicker.getMinute());

            Log.d(getString(R.string.user_action), getString(R.string.log_alarm_on));






            //Coloca o novo alarme criado no BD
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Schedule newSchedule = new Schedule(timePicker.getHour(), timePicker.getMinute(), 1);
                    SchedulesDB.getDatabase(NewScheduleActivity.this).scheduleDAO().insert(newSchedule);
                }
            }) .start();






        } else {

            //Cancela o alarme antes setado.
            alarmManager.cancel(pendingIntent);
            setAlarmText(getString(R.string.blank));
            setActiveAlarmText(getString(R.string.no_alarms));
            setAlarmImageVisibility(false);

            //Se o alarme ja tiver sido criado uma vez e estiver tocando, para.
            if(AlarmReceiver.ringtone != null && AlarmReceiver.ringtone.isPlaying())
                AlarmReceiver.ringtone.stop();

            Log.d(getString(R.string.user_action), getString(R.string.log_alarm_off));
        }

    }


}