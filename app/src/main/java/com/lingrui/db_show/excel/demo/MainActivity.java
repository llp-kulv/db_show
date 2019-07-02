package com.lingrui.db_show.excel.demo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<ArrayList<String>> recordList;
    private List<Student> students;
    private static String[] title = {"编号", "姓名", "性别", "年龄", "班级", "数学", "英语", "语文", "其他"};
    private File file;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        //模拟数据集合
        students = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            students.add(new Student("小红" + i, "女", "12", "1" + i, "一班", "85", "77", "98", "其他"+i));
            students.add(new Student("小明" + i, "男", "14", "2" + i, "二班", "65", "57", "100", "其他"+i));
        }
    }

    private final String[] sdcardPermissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private final int WRITE_PERMISSIONS = 1;
    private final int READ_PERMISSIONS = 2;

    /**
     * 导出excel
     *
     * @param view
     */
    public void exportExcel(View view) {
        int permissionCheck = ContextCompat.checkSelfPermission(this, sdcardPermissions[1]);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            // Toast.makeText(this, "hava this permission", Toast.LENGTH_SHORT).show();
            handleExportExcel();
        } else {
            // Toast.makeText(this, "no this permission", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, sdcardPermissions, WRITE_PERMISSIONS);
        }
    }

    public void readIn(View view){
        int permissionCheck = ContextCompat.checkSelfPermission(this, sdcardPermissions[0]);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            // Toast.makeText(this, "hava this permission", Toast.LENGTH_SHORT).show();
            handleReadExcel();
        } else {
            // Toast.makeText(this, "no this permission", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, sdcardPermissions, READ_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == WRITE_PERMISSIONS) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                handleExportExcel();
            }
        }
    }

    private void handleExportExcel() {
        file = new File(getSDPath() + "/Record");
        makeDir(file);
//        ExcelUtils.initExcel(file.toString() + "/成绩表.xls", title);
        fileName = getSDPath() + "/Record/成绩表.xls";
//        ExcelUtils.writeObjListToExcel(getRecordData(), fileName, this);

        Log.d("ljc", "ljc file path:" + file.getAbsolutePath());
    }

    private void handleReadExcel() {
        file = new File(getSDPath() + "/Record");
        fileName = getSDPath() + "/Record/成绩表.xls";
        File distFile = new File(fileName);
        if (!distFile.exists() || distFile.length() <= 0) {
            return;
        }

//        ExcelUtils.read2DB(distFile, getApplication());
    }

    /**
     * 将数据集合 转化成ArrayList<ArrayList<String>>
     *
     * @return
     */
    private ArrayList<ArrayList<String>> getRecordData() {
        recordList = new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            ArrayList<String> beanList = new ArrayList<String>();
            beanList.add(student.id);
            beanList.add(student.name);
            beanList.add(student.sex);
            beanList.add(student.age);
            beanList.add(student.classNo);
            beanList.add(student.math);
            beanList.add(student.english);
            beanList.add(student.chinese);
            beanList.add(student.other);
            recordList.add(beanList);
        }
        return recordList;
    }

    private String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        String dir = sdDir.toString();
        return dir;
    }

    public void makeDir(File dir) {
        if (!dir.getParentFile().exists()) {
            makeDir(dir.getParentFile());
        }
        dir.mkdir();
    }
}
