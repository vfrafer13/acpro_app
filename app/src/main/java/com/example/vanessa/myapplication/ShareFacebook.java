package com.example.vanessa.myapplication;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;

public class ShareFacebook extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_facebook);

        toolbar = (Toolbar) findViewById(R.id.toolbar_share);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TypedValue typedValueColorPrimaryDark = new TypedValue();
        ShareFacebook.this.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValueColorPrimaryDark, true);
        final int colorPrimaryDark = typedValueColorPrimaryDark.data;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(colorPrimaryDark);
        }

        setTitle("Compartir");
    }

    public void onClick(View view) {
        shareFacebook();
    }

    private void shareFacebook(){

    }
}
