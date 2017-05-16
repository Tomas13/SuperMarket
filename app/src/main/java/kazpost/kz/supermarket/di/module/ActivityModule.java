/*
 * Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://mindorks.com/license/apache-v2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package kazpost.kz.supermarket.di.module;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import kazpost.kz.supermarket.di.ActivityContext;
import kazpost.kz.supermarket.di.PerActivity;
import kazpost.kz.supermarket.ui.chooseindex.ChooseIndexMvpPresenter;
import kazpost.kz.supermarket.ui.chooseindex.ChooseIndexMvpView;
import kazpost.kz.supermarket.ui.chooseindex.ChooseIndexPresenter;
import kazpost.kz.supermarket.ui.scanner.ScanMvpPresenter;
import kazpost.kz.supermarket.ui.scanner.ScanMvpView;
import kazpost.kz.supermarket.ui.scanner.ScanPresenter;

/**
 * Created by janisharali on 27/01/17.
 */

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @PerActivity
    ScanMvpPresenter<ScanMvpView> provideScanPresenter(ScanPresenter<ScanMvpView> presenter) {
        return presenter;
    }


    @Provides
    @PerActivity
    ChooseIndexMvpPresenter<ChooseIndexMvpView> provideChooseIndexPresenter(ChooseIndexPresenter<ChooseIndexMvpView> presenter) {
        return presenter;
    }
}
