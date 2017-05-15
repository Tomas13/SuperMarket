package kazpost.kz.supermarket.data.network;

import java.util.List;

import kazpost.kz.supermarket.data.network.model.SendData;
import kazpost.kz.supermarket.data.network.model.TechIndex;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by root on 4/12/17.
 */

public interface ApiHelper {

    Observable<List<TechIndex>> getTechIndexList();

    Observable<ResponseBody> sendData(SendData sendData);
}
