package kazpost.kz.supermarket.ui.closecell;

import kazpost.kz.supermarket.di.PerActivity;
import kazpost.kz.supermarket.ui.base.MvpPresenter;

/**
 * Created by root on 4/14/17.
 */

@PerActivity
public interface CloseCellMvpPresenter<V extends CloseCellMvpView> extends MvpPresenter<V> {

    void openPrintActivity();
}
