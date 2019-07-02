package com.lingrui.db_show.contant;

import android.Manifest;

public interface PermissionsConstant {
    String STORAGE_READ = Manifest.permission.READ_EXTERNAL_STORAGE;
    String STORAGE_WRITE = Manifest.permission.READ_EXTERNAL_STORAGE;

    String[] STORAGE = new String[]{STORAGE_READ, STORAGE_WRITE};

    int REQUEST_PERMISSIONS_READ = 10;
    int REQUEST_PERMISSIONS_WRITE = REQUEST_PERMISSIONS_READ + 1;
}
