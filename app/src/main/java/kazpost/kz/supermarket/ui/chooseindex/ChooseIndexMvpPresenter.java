package kazpost.kz.supermarket.ui.chooseindex;

import kazpost.kz.supermarket.di.PerActivity;
import kazpost.kz.supermarket.ui.base.MvpPresenter;

/**
 * Created by root on 5/11/17.
 */

@PerActivity
public interface ChooseIndexMvpPresenter<V extends ChooseIndexMvpView> extends MvpPresenter<V> {

    void loadTechIndexList();

    void setSpinnerSelection();

    void savePostIndexToPrefs(int position);
}
