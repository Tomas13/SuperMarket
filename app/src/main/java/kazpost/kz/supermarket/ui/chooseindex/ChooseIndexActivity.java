package kazpost.kz.supermarket.ui.chooseindex;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.widget.ArrayAdapter;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kazpost.kz.supermarket.R;
import kazpost.kz.supermarket.data.network.model.TechIndex;
import kazpost.kz.supermarket.ui.base.BaseActivity;

public class ChooseIndexActivity extends BaseActivity implements ChooseIndexMvpView {

    @Inject
    ChooseIndexPresenter<ChooseIndexMvpView> mPresenter;
    @BindView(R.id.spinner_tech_index)
    SearchableSpinner spinnerTechIndex;
    
    @BindString(R.string.spinner_dialog_title)
    String spinnerTitle;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_index);
        ButterKnife.bind(this);

        getActivityComponent().inject(this);
        mPresenter.onAttach(ChooseIndexActivity.this);

        mPresenter.loadTechIndexList();

        spinnerTechIndex.setTitle(spinnerTitle);
        spinnerTechIndex.setPositiveButton("Ок");

    }

    @Override
    public void showTechIndexList(List<TechIndex> techIndexList) {
        List<String> list = new ArrayList<>();
        for (TechIndex techIndex : techIndexList) {
            list.add(techIndex.getPostcode() + " [ " + techIndex.getTitle() + " ]");
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTechIndex.setAdapter(dataAdapter);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @OnClick(R.id.floatingActionButton)
    public void onViewClicked() {
        super.onBackPressed();
    }
}
