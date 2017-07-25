package kazpost.kz.supermarket.ui.scanner;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baozi.Zxing.CaptureActivity;
import com.baozi.Zxing.ZXingConstants;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import kazpost.kz.supermarket.R;
import kazpost.kz.supermarket.ui.base.BaseActivity;
import kazpost.kz.supermarket.ui.chooseindex.ChooseIndexActivity;

public class ScanActivity extends BaseActivity implements ScanMvpView {

    @Inject
    ScanMvpPresenter<ScanMvpView> presenter;

    @BindView(R.id.et_postcode)
    EditText etPostCode;
    @BindView(R.id.et_row)
    EditText etScanRow;
    @BindView(R.id.tv_current_post_index)
    TextView tvCurrentPostIndex;

    @BindString(R.string.current_post_index)
    String currentPostIndexLabel;
    String cell, row, barcode;

    @BindString(R.string.nonvalid_data)
    String nonvalidData;
    @BindView(R.id.btn_scan)
    Button btnScan;
    @BindView(R.id.btn_send)
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);

        getActivityComponent().inject(this);
        presenter.onAttach(ScanActivity.this);

        presenter.checkIfPostIndexExist();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.showCurrentTechIndex();
    }


    @OnTextChanged(R.id.et_postcode)
    public void onPostcodeChange() {
        setStrings(etPostCode.getText().toString());
    }

    @OnTextChanged(R.id.et_row)
    public void onRowChanged() {
        setStrings(etScanRow.getText().toString());
    }

    private void setStrings(String value) {

        if (value.length() >= 4 && value.length() <= 5 ) {
            row = value.substring(0, value.length() - 3);
            cell = value.substring(value.length() - 3);
        }

//        if (value.length() == 13 && isBarcode(value)) {
//            barcode = value;
//        }

        if (value.length() > 6) {
            barcode = value;
        }
    }


    private boolean isBarcode(String value) {
        Pattern mPatternBar = Pattern.compile("^([A-Z]{2}[0-9]{9}[A-Z]{2})$");
        Matcher matcher = mPatternBar.matcher(value);

        return matcher.find();
    }

    private boolean isRow(String value) {
        Pattern mPatternRow = Pattern.compile("^([0-9]{4,5})$");
        Matcher matcher = mPatternRow.matcher(value);

        return matcher.find();
    }


    @Override
    public void showCurrentTechIndex(String currentTechIndex) {
        tvCurrentPostIndex.setText(currentPostIndexLabel + " " + currentTechIndex);
    }

    @Override
    public void startChooseIndexActivity() {
        startActivity(this, new ChooseIndexActivity());
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

                    setStrings(result);

                    if (etPostCode.hasFocus()) {

                        etPostCode.setText(result);
                        etScanRow.requestFocus();
                        break;
                    }

                    if (etScanRow.hasFocus()) {

                        etScanRow.setText(result);
                        break;
                    }


//                } else if (resultCode == ZXingConstants.ScanHistoryResultCode) {
//                    String resultHistory = data.getStringExtra(ZXingConstants.ScanHistoryResult);
//                    if (!TextUtils.isEmpty(resultHistory)) {
//                        startActivity(new Intent(MainActivity.this,HistoryActivity.class));
//                    }
                }
                break;
        }
    }

    @OnClick({R.id.btn_scan, R.id.btn_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_scan:
                RxPermissions rxPermissions = new RxPermissions(this); // where this is an Activity instance
                // Must be done during an initialization phase like onCreate
                rxPermissions
                        .request(Manifest.permission.CAMERA)
                        .subscribe(granted -> {
                            if (granted) { // Always true pre-M
                                // I can control the camera now
//                        Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(this, CaptureActivity.class);
                                intent.putExtra(ZXingConstants.ScanIsShowHistory, true);
                                startActivityForResult(intent, ZXingConstants.ScanRequestCode);
                            } else {
                                // Oups permission denied
                                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            case R.id.btn_send:
                if (presenter.checkIfPostIndexExist()) {

//                    if (barcode == null) barcode = etPostCode.getText().toString();
//                    if (row == null) row = etScanRow.getText().toString();

                    if (checkValues()) {
                        presenter.sendData(barcode, row, cell);
                    }
                } else {
                    onErrorToast("Не выбран почтовый индекс");
                }

                break;
        }
    }


    private boolean checkValues() {
        Pattern mPatternRow = Pattern.compile("^([0-9]{4,5})$");
        Pattern mPatternBar = Pattern.compile("^([A-Z]{2}[0-9]{9}[A-Z]{2})$");
        Matcher matcher = mPatternBar.matcher(barcode);
        Matcher matcherRow = mPatternRow.matcher(row + cell);

        if (matcher.find() && matcherRow.find()) {
            return true;
        } else {

            onErrorToast(nonvalidData);
            return false;
        }

    }

    @Override
    public void clearEditText() {
        etPostCode.setText("");
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
