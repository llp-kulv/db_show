package com.lingrui.db_show.ui.activity;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lingrui.db_show.BaseActivity;
import com.lingrui.db_show.R;
import com.lingrui.db_show.contant.PermissionsConstant;
import com.lingrui.db_show.excel.ExcelUtils;
import com.lingrui.db_show.util.FileUtil;
import com.lingrui.db_show.util.Flog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@SuppressLint("Registered")
@ContentView(R.layout.activity_export)
public class ExportActivity extends BaseActivity {
    private static final String TAG = "ExportActivity";

    @ViewInject(R.id.export_file_path_tv)
    private TextView export_file_path_tv;

    @ViewInject(R.id.export_file_tv)
    private TextView export_file_tv;

    @ViewInject(R.id.start_export_btn)
    private Button start_export_btn;

    @ViewInject(R.id.export_type_spinner)
    private Spinner export_type_spinner;

    private String currentExportType = null;
    private String[] typeArr = null;

    public void createView() {
        initAddAdapter();
        export_file_path_tv.setVisibility(View.INVISIBLE);
    }

    private void initAddAdapter() {
        typeArr = getResources().getStringArray(R.array.data_type_list);

        //适配器
        final ArrayAdapter arr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, typeArr);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        export_type_spinner.setAdapter(arr_adapter);
        export_type_spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    currentExportType = (String) arr_adapter.getItem(position);
                }
                Flog.d(TAG, "position:" + position + " currentExportType:" + currentExportType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Flog.d(TAG, "Nothing");
            }
        });
    }

    @Event(R.id.start_export_btn)
    private void clickExportBtn(View view) {
        if (TextUtils.isEmpty(currentExportType)) {
            Toast.makeText(this, R.string.need_select_type, Toast.LENGTH_SHORT).show();
            return;
        }

        if (checkPermission()) {
            // 开始到处文件
            handleExportFile();
            return;
        }

        // request permission
        ActivityCompat.requestPermissions(this, PermissionsConstant.STORAGE, PermissionsConstant.REQUEST_PERMISSIONS_WRITE);
    }

    private boolean checkPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, PermissionsConstant.STORAGE_WRITE);
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionsConstant.REQUEST_PERMISSIONS_WRITE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                handleExportFile();
            }
        }
    }

    private void handleExportFile() {
        try {
            final String filePath = FileUtil.getAvailableFilePath();
            if (TextUtils.isEmpty(filePath)) {
                Toast.makeText(this, R.string.export_fail, Toast.LENGTH_SHORT).show();
                export_file_tv.setText(R.string.export_fail);
                return;
            }

            // ExcelUtils.writeObjListToExcel(null, filePath, getApplication());
            tipSuccess(filePath);
        } catch (Exception e) {
            tipFail();
        }
    }

    private void tipFail() {
        Toast.makeText(this, R.string.export_fail, Toast.LENGTH_SHORT).show();
        export_file_tv.setText(R.string.export_fail);
    }

    private void tipSuccess(String path) {
        export_file_tv.setText(R.string.export_success);
        export_file_path_tv.setText(path);
        Toast.makeText(this, R.string.export_success, Toast.LENGTH_SHORT).show();

    }
}
