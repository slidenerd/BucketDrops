package slidenerd.vivz.bucketdrops.extras;

import android.view.View;

import java.util.List;

/**
 * Created by vivz on 31/12/15.
 */
public class Util {
    public static void showViews(List<View> views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void hideViews(List<View> views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }
}
