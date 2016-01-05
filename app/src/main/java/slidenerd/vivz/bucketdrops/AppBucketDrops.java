package slidenerd.vivz.bucketdrops;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by vivz on 30/12/15.
 * TODO change to custom fonts wherever appropriate
 */
public class AppBucketDrops extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration configuration=new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(configuration);
    }
}
