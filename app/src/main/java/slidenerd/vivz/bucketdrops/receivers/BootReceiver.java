package slidenerd.vivz.bucketdrops.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import slidenerd.vivz.bucketdrops.extras.Util;

public class BootReceiver extends BroadcastReceiver {
    public BootReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Util.scheduleAlarm(context);
    }
}
