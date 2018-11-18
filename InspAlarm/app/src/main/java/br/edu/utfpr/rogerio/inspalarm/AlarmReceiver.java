package br.edu.utfpr.rogerio.inspalarm;

import android.content.Context;
import android.content.Intent;

import android.support.v4.content.WakefulBroadcastReceiver;

import br.edu.utfpr.rogerio.inspalarm.Activities.AlarmRingingActivity;

public class AlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {

        Intent quoteIntent = new Intent(context, AlarmRingingActivity.class);
        context.startActivity(quoteIntent);
    }
}