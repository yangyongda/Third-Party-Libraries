package com.fjsdfx.yyd.patternlockview;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.andrognito.patternlockview.utils.ResourceUtils;
import com.andrognito.rxpatternlockview.RxPatternLockView;
import com.andrognito.rxpatternlockview.events.PatternLockCompleteEvent;
import com.andrognito.rxpatternlockview.events.PatternLockCompoundEvent;

import java.util.List;

import io.reactivex.functions.Consumer;

public class PatternLockActivity extends AppCompatActivity {

    private PatternLockView mPatternLockView;
    private DBUtils mDBUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏显示
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_pattern_lock);

        mDBUtils = new DBUtils(this);
        Cursor cursor  = mDBUtils.Query();
        Log.i("yang", cursor.getCount()+" ");

        if(cursor.getCount() != 0)
            //初始化
            initView();
        else {
            startActivity(new Intent(PatternLockActivity.this, MainActivity.class));
            finish();
        }
    }

    public void initView(){

        mPatternLockView = (PatternLockView) findViewById(R.id.patter_lock_view);
        mPatternLockView.setDotCount(3);    // 设置为3*3的图形锁
        mPatternLockView.setDotNormalSize((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_dot_size)); //正常显示时点的大小
        mPatternLockView.setDotSelectedSize((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_dot_selected_size)); //选中时点的大小
        mPatternLockView.setPathWidth((int) ResourceUtils.getDimensionInPx(this, R.dimen.pattern_lock_path_width)); //设置点的间距
        mPatternLockView.setAspectRatioEnabled(true);//是否自定义外观比例
        mPatternLockView.setAspectRatio(PatternLockView.AspectRatio.ASPECT_RATIO_HEIGHT_BIAS);//偏高
        mPatternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
        mPatternLockView.setDotAnimationDuration(150); //点动画周期时间
        mPatternLockView.setPathEndAnimationDuration(100); //路径结束动画周期时间
        mPatternLockView.setCorrectStateColor(ResourceUtils.getColor(this, R.color.white)); //图案正确时的颜色
        mPatternLockView.setWrongStateColor(ResourceUtils.getColor(this, R.color.red));
        mPatternLockView.setInStealthMode(false); //设置隐藏模式，即画图像时不会显示路径(更新图形密码时用到)
        mPatternLockView.setTactileFeedbackEnabled(true); //震动反馈
        mPatternLockView.setInputEnabled(true); //使能输入
        mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {
                Log.d(getClass().getName(), "Pattern drawing started");
            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {
                Log.d(getClass().getName(), "Pattern progress: " +
                        PatternLockUtils.patternToString(mPatternLockView, progressPattern));
            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                Log.d(getClass().getName(), "Pattern complete: " +
                        PatternLockUtils.patternToString(mPatternLockView, pattern));

                Cursor mCursor = mDBUtils.Query();
                mCursor.moveToFirst();
                String DataBasepattern = mCursor.getString(mCursor.getColumnIndex("dot"));
                if( DataBasepattern != null && !DataBasepattern.equals("")){
                    if(DataBasepattern.equals(PatternLockUtils.patternToString(mPatternLockView, pattern))){
                        mPatternLockView.clearPattern();
                        startActivity(new Intent(PatternLockActivity.this, MainActivity.class));
                        finish();
                    }else{
                        mPatternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                        //mPatternLockView.clearPattern();
                    }
                }
            }

            @Override
            public void onCleared() {
                Log.d(getClass().getName(), "Pattern has been cleared");
            }
        });

        RxPatternLockView.patternComplete(mPatternLockView)
                .subscribe(new Consumer<PatternLockCompleteEvent>() {
                    @Override
                    public void accept(PatternLockCompleteEvent patternLockCompleteEvent) throws Exception {
                        Log.d(getClass().getName(), "Complete: " + patternLockCompleteEvent.getPattern().toString());
                    }
                });

        RxPatternLockView.patternChanges(mPatternLockView)
                .subscribe(new Consumer<PatternLockCompoundEvent>() {
                    @Override
                    public void accept(PatternLockCompoundEvent event) throws Exception {
                        if (event.getEventType() == PatternLockCompoundEvent.EventType.PATTERN_STARTED) {
                            Log.d(getClass().getName(), "Pattern drawing started");
                        } else if (event.getEventType() == PatternLockCompoundEvent.EventType.PATTERN_PROGRESS) {
                            Log.d(getClass().getName(), "Pattern progress: " +
                                    PatternLockUtils.patternToString(mPatternLockView, event.getPattern()));
                        } else if (event.getEventType() == PatternLockCompoundEvent.EventType.PATTERN_COMPLETE) {
                            Log.d(getClass().getName(), "Pattern complete: " +
                                    PatternLockUtils.patternToString(mPatternLockView, event.getPattern()));
                        } else if (event.getEventType() == PatternLockCompoundEvent.EventType.PATTERN_CLEARED) {
                            Log.d(getClass().getName(), "Pattern has been cleared");
                        }
                    }
                });

    }
}
