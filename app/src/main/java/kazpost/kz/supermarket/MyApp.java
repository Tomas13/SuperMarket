package kazpost.kz.supermarket;

import android.app.Application;

import com.facebook.stetho.Stetho;

import javax.inject.Inject;

import kazpost.kz.supermarket.data.DataManager;
import kazpost.kz.supermarket.di.component.ApplicationComponent;
import kazpost.kz.supermarket.di.component.DaggerApplicationComponent;
import kazpost.kz.supermarket.di.module.ApplicationModule;

/**
 * Created by root on 4/11/17.
 */

public class MyApp extends Application{


    @Inject
    DataManager mDataManager;

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);

        Stetho.initializeWithDefaults(this);
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

}
