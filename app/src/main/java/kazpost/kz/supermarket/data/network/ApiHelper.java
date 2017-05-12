package kazpost.kz.supermarket.data.network;

import java.util.List;

import kazpost.kz.supermarket.data.network.model.TechIndex;
import rx.Observable;

/**
 * Created by root on 4/12/17.
 */

public interface ApiHelper {

    Observable<List<TechIndex>> getTechIndexList();

}
