package kazpost.kz.supermarket.data.network;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import kazpost.kz.supermarket.data.network.model.SendData;
import kazpost.kz.supermarket.data.network.model.TechIndex;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by root on 4/12/17.
 */

@Singleton
public class AppApiHelper implements ApiHelper {

    @Inject
    NetworkService networkService;


    @Inject
    public AppApiHelper() {
    }

    @Override
    public Observable<List<TechIndex>> getTechIndexList() {
        return networkService.getTechIndexList();
    }

    @Override
    public Observable<ResponseBody> sendData(SendData sendData) {
        return networkService.sendData(sendData);
    }
}
