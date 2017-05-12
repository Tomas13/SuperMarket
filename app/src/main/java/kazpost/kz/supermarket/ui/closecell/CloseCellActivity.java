package kazpost.kz.supermarket.ui.closecell;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kazpost.kz.supermarket.R;
import kazpost.kz.supermarket.ui.base.BaseActivity;
import kazpost.kz.supermarket.ui.print.PrintActivity;

public class CloseCellActivity extends BaseActivity implements CloseCellMvpView {

//    @Inject
//    CloseCellMvpPresenter<CloseCellMvpView> presenter;

    @BindView(R.id.et_code_bag)
    EditText etCodeBag;
    @BindView(R.id.et_number_seal)
    EditText etNumberSeal;
    @BindView(R.id.et_weight)
    EditText etWeight;
    @BindView(R.id.btn_back_closecell)
    Button btnBackClosecell;
    @BindView(R.id.btn_next_closecell)
    Button btnNextClosecell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_cell);
        ButterKnife.bind(this);

//        getActivityComponent().inject(this);
//        presenter.onAttach(CloseCellActivity.this);
    }

    @OnClick({R.id.btn_back_closecell, R.id.btn_next_closecell})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back_closecell:
                super.onBackPressed();
                break;
            case R.id.btn_next_closecell:
                startActivity(this, new PrintActivity());
//                presenter.openPrintActivity();
                break;
        }
    }

    @Override
    public void openPrintActivity() {
        startActivity(this, new PrintActivity());
    }
}
