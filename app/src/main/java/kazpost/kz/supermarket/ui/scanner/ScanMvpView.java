package kazpost.kz.supermarket.ui.scanner;

import kazpost.kz.supermarket.ui.base.MvpView;

/**
 * Created by root on 4/14/17.
 */

public interface ScanMvpView extends MvpView {

    void clearEditText();

    void showCurrentTechIndex(String currentTechIndex);


    void startChooseIndexActivity();

    void onResponse();
}
