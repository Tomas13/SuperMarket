package kazpost.kz.supermarket.ui.scanner;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import kazpost.kz.supermarket.data.DataManager;
import kazpost.kz.supermarket.data.network.model.SendData;
import kazpost.kz.supermarket.ui.base.BasePresenter;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by root on 4/17/17.
 */

public class ScanPresenter<V extends ScanMvpView> extends BasePresenter<V> implements ScanMvpPresenter<V> {

    @Inject
    public ScanPresenter(DataManager dataManager) {
        super(dataManager);
    }


    @Override
    public void checkIfPostIndexExist() {

        if (getDataManager().getSpinnerPosition() == -1){
            getMvpView().startChooseIndexActivity();
        }
    }

    @Override
    public void sendData(String barcode, String row, String cell) {

        getMvpView().showLoading();

        SendData sendData = new SendData();
        sendData.setBarcode(barcode);
        sendData.setRow(row);
        sendData.setCell(cell);
        sendData.setIndex(getDataManager().getPostIndex());


        Observable<ResponseBody> sendDataObservable = getDataManager().sendData(sendData);

        sendDataObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {

                            Log.d("ScanPresenter", responseBody.toString());
                            getMvpView().hideLoading();

                        },
                        throwable -> {
                            getMvpView().onErrorToast(throwable.getMessage());
                            getMvpView().hideLoading();
                        }
                );
    }

    @Override
    public void showCurrentTechIndex() {
        getMvpView().showCurrentTechIndex(getDataManager().getPostIndex());
    }

}