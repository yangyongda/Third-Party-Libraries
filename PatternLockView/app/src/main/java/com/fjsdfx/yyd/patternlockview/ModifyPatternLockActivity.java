package com.fjsdfx.yyd.patternlockview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.andrognito.patternlockview.utils.ResourceUtils;
import com.andrognito.rxpatternlockview.RxPatternLockView;
import com.andrognito.rxpatternlockview.events.PatternLockCompleteEvent;
import com.andrognito.rxpatternlockview.events.PatternLockCompoundEvent;

import java.util.List;

import io.reactivex.functions.Consumer;

public class ModifyPatternLockActivity extends AppCompatActivity {

    private PatternLockView mPatternLockView;
    private DBUtils mDBUtils;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pattern_lock);

        mDBUtils  = new DBUtils(this);
        initView();
    }

    public void initView() {
            mPatternLockView = findViewById(R.id.patter_lock_view);
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
            mPatternLockView.setInStealthMode(true); //设置隐藏模式，即画图像时不会显示路径(更新图形密码时用到)
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
                    if(PatternLockUtils.patternToString(mPatternLockView, pattern).length() > 1) {
                        if(count == 1) {
                            mDBUtils.insert(1, PatternLockUtils.patternToString(mPatternLockView, pattern));
                            Toast.makeText(ModifyPatternLockActivity.this, "图形密码添加完成",Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            count++;
                            Toast.makeText(ModifyPatternLockActivity.this, "请再绘制一次",Toast.LENGTH_SHORT).show();
                        }
                        mPatternLockView.clearPattern();
                    }else{
                        Toast.makeText(ModifyPatternLockActivity.this, "长度必须大于2",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCleared() {
                    Log.d(getClass().getName(), "Pattern has been cleared");
                }
            });
        }
    }
