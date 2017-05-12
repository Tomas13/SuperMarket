package kazpost.kz.supermarket.ui.closecell;

import javax.inject.Inject;

import kazpost.kz.supermarket.data.DataManager;
import kazpost.kz.supermarket.ui.base.BasePresenter;

/**
 * Created by root on 4/14/17.
 */

public class CloseCellPresenter<V extends CloseCellMvpView> extends BasePresenter<V> implements CloseCellMvpPresenter<V>{

    @Inject
    public CloseCellPresenter(DataManager dataManager) {
        super(dataManager);
    }


    @Override
    public void openPrintActivity() {
        getMvpView().openPrintActivity();
    }
}
