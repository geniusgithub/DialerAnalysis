package com.geniusgithub.dialer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean hasStartPermissionActivity =RequestPermissionsActivity.startPermissionActivity(this);

        setContentView(R.layout.activity_main);
    }
}
