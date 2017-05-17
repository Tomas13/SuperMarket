package kazpost.kz.supermarket.data;

import android.content.Context;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import kazpost.kz.supermarket.data.network.ApiHelper;
import kazpost.kz.supermarket.data.network.model.SendData;
import kazpost.kz.supermarket.data.network.model.TechIndex;
import kazpost.kz.supermarket.data.prefs.PreferencesHelper;
import kazpost.kz.supermarket.di.ApplicationContext;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by root on 4/12/17.
 */

@Singleton
public class AppDataManager implements DataManager {

    private static final String TAG = "AppDataManager";

    private final Context mContext;
    private final PreferencesHelper mPreferencesHelper;
    private final ApiHelper mApiHelper;


    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          PreferencesHelper preferencesHelper,
                          ApiHelper apiHelper) {
        mContext = context;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }


    public Context getmContext() {
        return mContext;
    }


    public PreferencesHelper getmPreferencesHelper() {
        return mPreferencesHelper;
    }


    @Override
    public Observable<List<TechIndex>> getTechIndexList() {
        return mApiHelper.getTechIndexList();
    }

    @Override
    public Observable<ResponseBody> sendData(Map<String, String> stringMap) {
        return mApiHelper.sendData(stringMap);
    }

    @Override
    public void savePostIndex(String postIndex) {
        mPreferencesHelper.savePostIndex(postIndex);
    }

    @Override
    public void saveSpinnerPosition(int position) {
        mPreferencesHelper.saveSpinnerPosition(position);
    }

    @Override
    public int getSpinnerPosition() {
        return mPreferencesHelper.getSpinnerPosition();
    }

    @Override
    public String getPostIndex() {
        return mPreferencesHelper.getPostIndex();
    }
}
