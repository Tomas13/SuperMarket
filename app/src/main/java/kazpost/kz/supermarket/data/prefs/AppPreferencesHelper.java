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

    private static final String PREF_KEY_SESSION_ID = "PREF_KEY_SESSION_ID";


    @Inject
    public AppPreferencesHelper(@ApplicationContext Context context,
                                @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public void saveSessionId(String sessionId) {
        mPrefs.edit().putString(PREF_KEY_SESSION_ID, sessionId).apply();
    }
}
