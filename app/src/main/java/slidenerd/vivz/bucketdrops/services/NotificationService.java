package slidenerd.vivz.bucketdrops.services;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;

import br.com.goncalves.pugnotification.notification.PugNotification;
import io.realm.Realm;
import io.realm.RealmResults;
import slidenerd.vivz.bucketdrops.ActivityMain;
import slidenerd.vivz.bucketdrops.R;
import slidenerd.vivz.bucketdrops.beans.Drop;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class NotificationService extends IntentService {

    public NotificationService() {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            RealmResults<Drop> results = realm.where(Drop.class).equalTo("completed", false).findAll();
            for (Drop current : results) {
                if (isNotificationNeeded(current.getAdded(), current.getWhen())) {
                    fireNotification(current);
                }
            }

        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }

    private void fireNotification(Drop drop) {
        String message = getString(R.string.notif_message) + "\"" + drop.getWhat() + "\"";
        PugNotification.with(this)
                .load()
                .title(R.string.notif_title)
                .message(message)
                .bigTextStyle(R.string.notif_long_message)
                .smallIcon(R.drawable.ic_drop)
                .largeIcon(R.drawable.ic_drop)
                .flags(Notification.DEFAULT_ALL)
                .autoCancel(true)
                .click(ActivityMain.class)
                .simple()
                .build();
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
