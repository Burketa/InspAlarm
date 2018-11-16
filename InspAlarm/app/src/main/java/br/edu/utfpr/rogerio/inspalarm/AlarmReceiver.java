package br.edu.utfpr.rogerio.inspalarm;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v4.content.WakefulBroadcastReceiver;

public class AlarmReceiver extends WakefulBroadcastReceiver {

    static Ringtone ringtone = null;

    @Override
    public void onReceive(final Context context, Intent intent) {
        MainActivity inst = MainActivity.getInstance();

        //Troca o texto acima do botão.
        inst.setAlarmText(inst.getResources().getString(R.string.move_text));

        /*
            Essa parte foi colocada, mas ainda sem entendimento completo do funcionamento do Uri
        */
        //Aqui colocamos um alarme para tocar para ter um feedback sonoro.
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();

        //Mostra a nota musical, caso o dispositivo esteja sem som
        inst.setAlarmImageVisibility(true);
        Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        for(int i = 0; i < 30; i++)
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
    }
}