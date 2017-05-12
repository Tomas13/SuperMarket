package kazpost.kz.supermarket.data.network;

import java.util.List;

import kazpost.kz.supermarket.data.network.model.TechIndex;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by root on 4/18/17.
 */

public interface NetworkService {



    @GET("supermarkets")
    Observable<List<TechIndex>> getTechIndexList();

}
