package kazpost.kz.supermarket.ui.chooseindex;

import java.util.List;

import kazpost.kz.supermarket.data.network.model.TechIndex;
import kazpost.kz.supermarket.ui.base.MvpView;

/**
 * Created by root on 5/11/17.
 */

public interface ChooseIndexMvpView extends MvpView {

    void showTechIndexList(List<TechIndex> techIndexList);


    void setSpinnerSelectionInView(int position);
}
