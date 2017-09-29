package kazpost.kz.supermarket.ui.scanner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import kazpost.kz.supermarket.data.DataManager;
import kazpost.kz.supermarket.data.network.model.Response;
import kazpost.kz.supermarket.data.network.model.SendData;
import kazpost.kz.supermarket.ui.base.BasePresenter;
import kazpost.kz.supermarket.utils.EspressoIdlingResource;
import retrofit2.Call;
import retrofit2.Callback;

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

        if (getDataManager().getSpinnerPosition() == -1) {
            getMvpView().startChooseIndexActivity();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void sendData(String barcode, String row, String cell) {

        // The network request might be handled in a different thread so make sure Espresso knows
        // that the app is busy until the response is handled.
        EspressoIdlingResource.increment(); // App is busy until further notice

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


        Call<Response> responseCall = getDataManager().sendCallData(params);

        responseCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {


                if (response.code() == 200) {
                    /*String value = null;
                    value = response.body().getMessage();
                    value = value.substring(1, value.length() - 1);           //remove curly brackets
                    String[] keyValuePairs = value.split(",");              //split the string to creat key-value pairs
                    Map<String, String> map = new HashMap<>();

                    for (String pair : keyValuePairs)                        //iterate over the pairs
                    {
                        String[] entry = pair.split(":");                   //split the pairs to get key and value
                        map.put(entry[0].trim(), entry[1].trim());          //add them to the hashmap and trim whitespaces
                    }
*/

                    getMvpView().onErrorToast(response.body().getMessage());
                    getMvpView().onResponse();
                    getMvpView().hideLoading();

                } else {
                    String value = null;
                    try {
                        value = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    value = value.substring(1, value.length() - 1);           //remove curly brackets
                    String[] keyValuePairs = value.split(",");              //split the string to creat key-value pairs
                    Map<String, String> map = new HashMap<>();

                    for (String pair : keyValuePairs)                        //iterate over the pairs
                    {
                        String[] entry = pair.split(":");                   //split the pairs to get key and value
                        map.put(entry[0].trim(), entry[1].trim());          //add them to the hashmap and trim whitespaces
                    }


                    getMvpView().onErrorToast(map.get("\"message\""));
                    getMvpView().onResponse();
                    getMvpView().hideLoading();

                }

                EspressoIdlingResource.decrement(); // Set app as idle.

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                getMvpView().onErrorToast("Status " + t.getMessage());
                getMvpView().hideLoading();

                EspressoIdlingResource.decrement(); // Set app as idle.


            }
        });

    }

    @Override
    public void showCurrentTechIndex() {
        getMvpView().showCurrentTechIndex(getDataManager().getPostIndex());
    }

}