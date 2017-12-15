package com.fjsdfx.yyd.patternlockview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;


public class MainActivity extends AppCompatActivity {

    private SwitchCompat mSwitch;
    private RelativeLayout mRelativeLayout;
    private DBUtils mDBUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDBUtils = new DBUtils(this);
        initView();
    }

    public void initView(){
        mSwitch = findViewById(R.id.patternLock_switch);
        mRelativeLayout = findViewById(R.id.modify);

        if(mDBUtils.Query().getCount() != 0){
            mSwitch.setChecked(true);
        }

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Intent intent = new Intent(MainActivity.this, ModifyPatternLockActivity.class);
                    startActivity(intent);
                }else {
                    mDBUtils.delete(1);
                }
            }
        });

        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UpdatePatternLockActivity.class);
                startActivity(intent);
            }
        });

    }
}
