package appcourse.vivz.bucketdrops;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import appcourse.vivz.bucketdrops.adapters.Filter;

/**
 * Created by vivz on 30/12/15.
 * TODO change to custom fonts wherever appropriate
 */
public class AppBucketDrops extends Application {

    static String FILTER_OPTION = "filter";

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration configuration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(configuration);
    }

    public static void save(Context context, int filterOption) {

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(FILTER_OPTION, filterOption);
        editor.apply();
    }

    public static int load(Context context) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        int filterOption = pref.getInt(FILTER_OPTION, Filter.NONE);
        return filterOption;
    }

    public static void setRalewayRegular(Context context, TextView textView) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/raleway_thin.ttf");
        textView.setTypeface(typeface);
    }

    public static void setRalewayRegular(Context context, TextView... textViews) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/raleway_thin.ttf");
        for (TextView textView : textViews) {
            textView.setTypeface(typeface);
        }
    }
}
