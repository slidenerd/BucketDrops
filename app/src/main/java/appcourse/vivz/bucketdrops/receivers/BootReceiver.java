package appcourse.vivz.bucketdrops.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import appcourse.vivz.bucketdrops.extras.Util;

public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Util.scheduleAlarm(context);
    }
}
