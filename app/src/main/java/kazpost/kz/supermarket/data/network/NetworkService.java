package kazpost.kz.supermarket.data.network;

import java.util.List;
import java.util.Map;

import kazpost.kz.supermarket.data.network.model.Response;
import kazpost.kz.supermarket.data.network.model.SendData;
import kazpost.kz.supermarket.data.network.model.TechIndex;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by root on 4/18/17.
 */

public interface NetworkService {


    @GET("https://bot.post.kz/api/supermarkets")
    Observable<List<TechIndex>> getTechIndexList();


    @GET("http://pls-test.post.kz/api/mobile/save-supermarket-cell")
    Observable<Response> sendData(
            @QueryMap Map<String, String> params);

}
