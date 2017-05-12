package kazpost.kz.supermarket.ui.scanner;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.baozi.Zxing.CaptureActivity;
import com.baozi.Zxing.ZXingConstants;
import com.tbruyelle.rxpermissions.RxPermissions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import kazpost.kz.supermarket.R;
import kazpost.kz.supermarket.ui.base.BaseActivity;
import kazpost.kz.supermarket.ui.chooseindex.ChooseIndexActivity;

public class ScanActivity extends BaseActivity implements ScanMvpView {

    @Inject
    ScanMvpPresenter<ScanMvpView> presenter;

    @BindView(R.id.et_scan_activity)
    EditText etScanActivity;
    @BindView(R.id.et_scan_bag)
    EditText etScanBag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);

        getActivityComponent().inject(this);
        presenter.onAttach(ScanActivity.this);


        RxPermissions rxPermissions = new RxPermissions(this); // where this is an Activity instance
        // Must be done during an initialization phase like onCreate
        rxPermissions
                .request(Manifest.permission.CAMERA)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                        Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(this, CaptureActivity.class);
                        intent.putExtra(ZXingConstants.ScanIsShowHistory, true);
                        startActivityForResult(intent, ZXingConstants.ScanRequestCode);
                    } else {
                        // Oups permission denied
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    @OnTextChanged(R.id.et_scan_activity)
    public void onScan() {
//        presenter.onScan(etScanActivity.getText().toString());
    }

    @Override
    public void clearEditText() {
        etScanActivity.setText("");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case ZXingConstants.ScanRequestCode:
                if (resultCode == ZXingConstants.ScanRequestCode) {
                    String result = data.getStringExtra(ZXingConstants.ScanResult);
                    etScanActivity.setText(result);

                    etScanBag.setVisibility(View.VISIBLE);
                    etScanBag.requestFocus();


                } else if (resultCode == ZXingConstants.ScanHistoryResultCode) {
                    String resultHistory = data.getStringExtra(ZXingConstants.ScanHistoryResult);
//                    if (!TextUtils.isEmpty(resultHistory)) {
//                        startActivity(new Intent(MainActivity.this,HistoryActivity.class));
//                    }
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.scan_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_item) {
            startActivity(this, new ChooseIndexActivity());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
