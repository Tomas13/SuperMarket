package kazpost.kz.supermarket.ui.scanner;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import static kazpost.kz.supermarket.utils.CommonUtils.isBarcode;
import static kazpost.kz.supermarket.utils.CommonUtils.isRow;

public class ScanActivity extends BaseActivity implements ScanMvpView {

    @Inject
    ScanMvpPresenter<ScanMvpView> presenter;

    @BindView(R.id.et_postcode)
    EditText etPostCode;
    @BindView(R.id.et_row)
    EditText etRow;
    @BindView(R.id.tv_current_post_index)
    TextView tvCurrentPostIndex;

    @BindString(R.string.current_post_index)
    String currentPostIndexLabel;
    private String cell, row, barcode;

    public String getCell() {
        return cell;
    }

    public String getRow() {
        return row;
    }

    public String getBarcode() {
        return barcode;
    }

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
        setStrings(etRow.getText().toString());
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

                    if (etPostCode.hasFocus()) {
                        etPostCode.setText(result);
                        etRow.requestFocus();
                        break;
                    }

                    if (etRow.hasFocus()) {
                        etRow.setText(result);
                        etPostCode.requestFocus();
                        break;
                    }

                    setStrings(result);
                }
                break;
        }
    }

    @OnClick({R.id.btn_scan, R.id.btn_send, R.id.btn_clean})
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
//                    if (row == null) row = etRow.getText().toString();

                    if (checkValues()) {
                        presenter.sendData(barcode, row, cell);
                    }
                } else {
                    onErrorToast("Не выбран почтовый индекс");
                }

                break;

            case R.id.btn_clean:

                etPostCode.getText().clear();
                etRow.getText().clear();
                etPostCode.requestFocus();
                barcode = null;
                row = null;
                cell = null;
                break;
        }
    }


    private boolean checkValues() {
        Pattern mPatternRow = Pattern.compile("^([0-9]{4,5})$");
        Pattern mPatternBar = Pattern.compile("^([A-Z]{2}[0-9]{9}[A-Z]{2})$");

        Log.d("setString", "bar " + barcode + "row " + row + "cell " + cell);
        if (barcode != null && row != null && cell != null) {
            Matcher matcher = mPatternBar.matcher(barcode);
            Matcher matcherRow = mPatternRow.matcher(row + cell);
            if (matcher.find() && matcherRow.find()) {
                return true;
            } else {
                onErrorToast(nonvalidData);
                return false;
            }
        } else {
            Toast.makeText(this, R.string.nonvalid_data, Toast.LENGTH_SHORT).show();
        }

        return false;
    }


    public void setStrings(String value) {

        if (isBarcode(value)) {
            setBarcode(value);
            Log.d("setString", "setBarcode. Barcode is " + barcode);
        }

        if (isRow(value)) {
            setRow(value);
            Log.d("setString", "setRow. Row+cell is " + row + cell);
        }

    }

    public void setRow(String value) {
        if (value.length() >= 4 && value.length() <= 5) {
            row = value.substring(0, value.length() - 3);
            cell = value.substring(value.length() - 3);
        } else {
            row = null;
            cell = null;
        }
    }

    public void setBarcode(String value) {
        if (value.length() == 13) {
            barcode = value;
        } else {
            barcode = null;
        }
    }


    @Override
    public void clearEditText() {
        etPostCode.setText("");
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
    public void onResponse() {
       /* barcode = null;
        row = null;
        cell = null;*/
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
