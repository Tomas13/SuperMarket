package kazpost.kz.supermarket.ui.scanner;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import kazpost.kz.supermarket.data.DataManager;
import kazpost.kz.supermarket.data.network.model.Response;
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
    public boolean checkIfPostIndexExist() {

        if (getDataManager().getSpinnerPosition() == -1){
            getMvpView().startChooseIndexActivity();
            return false;
        }else{
            return true;
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

        Map<String, String> params = new HashMap<>();
        params.put("access", "939ae3ec-a906-487c-a5d5-dabd0c3a52c3");
        params.put("barcode", barcode);
        params.put("row", row);
        params.put("cell", cell);
        params.put("index", getDataManager().getPostIndex());


        Observable<Response> sendDataObservable = getDataManager().sendData(params);

        sendDataObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                            getMvpView().onErrorToast(responseBody.getMessage());


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