package br.edu.utfpr.rogerio.inspalarm.Activities;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import br.edu.utfpr.rogerio.inspalarm.R;

public class AlarmRingingActivity extends AppCompatActivity {

    static Ringtone ringtone = null;
    private TextView quote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_ringing);
        setTitle("The time has come !");

        //Referencias
        quote = findViewById(R.id.quote_text);

        //Troca o texto para alguma frase com a tag
        quote.setText("EAE MALUCOS");

        //Coloca o alarme para tocar
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), alarmUri);
        ringtone.play();
    }

    @Override
    public void onPause() {
        super.onPause();

        //Se o alarme existir e estiver tocando, para de tocar
        if(ringtone != null && ringtone.isPlaying())
            ringtone.stop();
    }
}
