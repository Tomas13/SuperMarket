package kazpost.kz.supermarket.ui.chooseindex;

import java.util.List;

import javax.inject.Inject;

import kazpost.kz.supermarket.data.DataManager;
import kazpost.kz.supermarket.data.network.model.TechIndex;
import kazpost.kz.supermarket.ui.base.BasePresenter;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by root on 5/11/17.
 */

public class ChooseIndexPresenter<V extends ChooseIndexMvpView> extends BasePresenter<V> implements ChooseIndexMvpPresenter<V> {

    private List<TechIndex> techIndexListVar;

    @Inject
    public ChooseIndexPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void loadTechIndexList() {

        Observable<List<TechIndex>> observable = getDataManager().getTechIndexList();

        getMvpView().showLoading();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(techIndexList -> {


                            techIndexListVar = techIndexList;

                            getMvpView().showTechIndexList(techIndexList);

                            getMvpView().hideLoading();
                        },
                        throwable -> {

                            if (throwable.getMessage() != null) {
                                getMvpView().onErrorToast(throwable.getMessage());
                            }

                            getMvpView().hideLoading();

                        }
                );
    }

    @Override
    public void setSpinnerSelection() {
        getMvpView().setSpinnerSelectionInView(getDataManager().getSpinnerPosition());
    }


    @Override
    public void savePostIndexToPrefs(int position) {
        getDataManager().savePostIndex(techIndexListVar.get(position).getPostcode());


        getDataManager().saveSpinnerPosition(position);
    }
}
