package kazpost.kz.supermarket.ui.scanner;

import kazpost.kz.supermarket.di.PerActivity;
import kazpost.kz.supermarket.ui.base.MvpPresenter;

/**
 * Created by root on 4/14/17.
 */

@PerActivity
public interface ScanMvpPresenter<V extends ScanMvpView> extends MvpPresenter<V> {

    boolean checkIfPostIndexExist();

    void sendData(String barcode, String row, String cell);

    void showCurrentTechIndex();

}
