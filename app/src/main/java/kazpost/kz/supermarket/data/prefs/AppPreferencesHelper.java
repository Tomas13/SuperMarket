package kazpost.kz.supermarket.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import kazpost.kz.supermarket.di.ApplicationContext;
import kazpost.kz.supermarket.di.PreferenceInfo;

/**
 * Created by root on 4/12/17.
 */

@Singleton
public class AppPreferencesHelper implements PreferencesHelper {

    private final SharedPreferences mPrefs;

    private static final String PREF_KEY_POST_INDEX = "PREF_KEY_POST_INDEX";
    private static final String PREF_KEY_SPINNER_POSITION = "PREF_KEY_SPINNER_POSITION";


    @Inject
    public AppPreferencesHelper(@ApplicationContext Context context,
                                @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public void savePostIndex(String postIndex) {
        mPrefs.edit().putString(PREF_KEY_POST_INDEX, postIndex).apply();
    }

    @Override
    public String getPostIndex() {
        return mPrefs.getString(PREF_KEY_POST_INDEX, "Не выбрано");
    }

    @Override
    public void saveSpinnerPosition(int position) {
        mPrefs.edit().putInt(PREF_KEY_SPINNER_POSITION, position).apply();
    }

    @Override
    public int getSpinnerPosition() {
        return mPrefs.getInt(PREF_KEY_SPINNER_POSITION, -1);
    }

}
