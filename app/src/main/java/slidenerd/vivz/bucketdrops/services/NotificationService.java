package slidenerd.vivz.bucketdrops.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmResults;
import slidenerd.vivz.bucketdrops.beans.Drop;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class NotificationService extends IntentService {
    public static final String TAG = "VIVZ";

    public NotificationService() {
        super("NotificationService");
        Log.d(TAG, "NotificationService: ");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: ");
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<Drop> results = realm.where(Drop.class).equalTo("completed", false).findAll();
            for (Drop current : results) {
                if (isNotificationNeeded(current.getAdded(), current.getWhen())) {
                    Log.d(TAG, "onHandleIntent: notifcation needed");
                }
            }

        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    private boolean isNotificationNeeded(long added, long when) {
        long now = System.currentTimeMillis();
        if (now > when) {
            return false;
        } else {
            long difference90 = (long) (0.9 * (when - added));
            return (now > (added + difference90)) ? true : false;
        }
    }
}
