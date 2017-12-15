package com.fjsdfx.yyd.material_dialogs;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.StackingBehavior;
import com.afollestad.materialdialogs.Theme;
import com.afollestad.materialdialogs.color.CircleView;
import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.afollestad.materialdialogs.folderselector.FileChooserDialog;
import com.afollestad.materialdialogs.folderselector.FolderChooserDialog;
import com.afollestad.materialdialogs.internal.MDTintHelper;
import com.afollestad.materialdialogs.internal.ThemeSingleton;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListAdapter;
import com.afollestad.materialdialogs.simplelist.MaterialSimpleListItem;
import com.afollestad.materialdialogs.util.DialogUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements FolderChooserDialog.FolderCallback,
        FileChooserDialog.FileCallback, ColorChooserDialog.ColorCallback{
    private static final int STORAGE_PERMISSION_RC = 69;
    private Handler handler;
    // color chooser dialog
    private int primaryPreselect;
    // UTILITY METHODS
    private int accentPreselect;
    private Toast toast;
    static int index = 0;
    // Custom View Dialog
    private EditText passwordInput;
    private View positiveAction;
    private Thread thread;
    private int chooserDialog;

    @BindView(R.id.basicNoTitle)
    Button basicNoTitle;
    @BindView(R.id.basic)
    Button basic;
    @BindView(R.id.basicLongContent)
    Button basicLongContent;
    @BindView(R.id.basicIcon)
    Button basicIcon;
    @BindView(R.id.basicCheckPrompt)
    Button basicCheckPrompt;
    @BindView(R.id.stacked)
    Button stacked;
    @BindView(R.id.neutral)
    Button neutral;
    @BindView(R.id.callbacks)
    Button callbacks;
    @BindView(R.id.listNoTitle)
    Button listNoTitle;
    @BindView(R.id.list)
    Button list;
    @BindView(R.id.longList)
    Button longList;
    @BindView(R.id.list_longItems)
    Button listLongItems;
    @BindView(R.id.list_checkPrompt)
    Button listCheckPrompt;
    @BindView(R.id.list_longPress)
    Button listLongPress;
    @BindView(R.id.singleChoice)
    Button singleChoice;
    @BindView(R.id.singleChoice_longItems)
    Button singleChoiceLongItems;
    @BindView(R.id.multiChoice)
    Button multiChoice;
    @BindView(R.id.multiChoiceLimited)
    Button multiChoiceLimited;
    @BindView(R.id.multiChoiceLimitedMin)
    Button multiChoiceLimitedMin;
    @BindView(R.id.multiChoice_longItems)
    Button multiChoiceLongItems;
    @BindView(R.id.multiChoice_disabledItems)
    Button multiChoiceDisabledItems;
    @BindView(R.id.simpleList)
    Button simpleList;
    @BindView(R.id.customListItems)
    Button customListItems;
    @BindView(R.id.customView)
    Button customView;
    @BindView(R.id.customView_webView)
    Button customViewWebView;
    @BindView(R.id.customView_datePicker)
    Button customViewDatePicker;
    @BindView(R.id.colorChooser_primary)
    Button colorChooserPrimary;
    @BindView(R.id.colorChooser_accent)
    Button colorChooserAccent;
    @BindView(R.id.colorChooser_customColors)
    Button colorChooserCustomColors;
    @BindView(R.id.colorChooser_customColorsNoSub)
    Button colorChooserCustomColorsNoSub;
    @BindView(R.id.input)
    Button input;
    @BindView(R.id.input_custominvalidation)
    Button inputCustominvalidation;
    @BindView(R.id.input_checkPrompt)
    Button inputCheckPrompt;
    @BindView(R.id.progress1)
    Button progress1;
    @BindView(R.id.progress2)
    Button progress2;
    @BindView(R.id.progress3)
    Button progress3;
    @BindView(R.id.preference_dialogs)
    Button preferenceDialogs;
    @BindView(R.id.themed)
    Button themed;
    @BindView(R.id.showCancelDismiss)
    Button showCancelDismiss;
    @BindView(R.id.file_chooser)
    Button fileChooser;
    @BindView(R.id.folder_chooser)
    Button folderChooser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        handler = new Handler();
        primaryPreselect = DialogUtils.resolveColor(this, R.attr.colorPrimary);
        accentPreselect = DialogUtils.resolveColor(this, R.attr.colorAccent);
    }

    @OnClick({R.id.basicNoTitle, R.id.basic, R.id.basicLongContent, R.id.basicIcon, R.id.basicCheckPrompt, R.id.stacked, R.id.neutral, R.id.callbacks, R.id.listNoTitle, R.id.list, R.id.longList, R.id.list_longItems, R.id.list_checkPrompt, R.id.list_longPress, R.id.singleChoice, R.id.singleChoice_longItems, R.id.multiChoice, R.id.multiChoiceLimited, R.id.multiChoiceLimitedMin, R.id.multiChoice_longItems, R.id.multiChoice_disabledItems, R.id.simpleList, R.id.customListItems, R.id.customView, R.id.customView_webView, R.id.customView_datePicker, R.id.colorChooser_primary, R.id.colorChooser_accent, R.id.colorChooser_customColors, R.id.colorChooser_customColorsNoSub, R.id.input, R.id.input_custominvalidation, R.id.input_checkPrompt, R.id.progress1, R.id.progress2, R.id.progress3, R.id.preference_dialogs, R.id.themed, R.id.showCancelDismiss, R.id.file_chooser, R.id.folder_chooser})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.basicNoTitle: //Basic 没有标题
                new MaterialDialog.Builder(this)
                        .content(R.string.shareLocationPrompt) //提示内容
                        .positiveText(R.string.agree)  //确认按钮上的text
                        .negativeText(R.string.disagree) //取消按钮上的text
                        .show();  //显示
                break;
            case R.id.basic:  //Basic 有标题
                new MaterialDialog.Builder(this)
                        .title(R.string.useGoogleLocationServices) //title
                        .content(R.string.useGoogleLocationServicesPrompt, true) //包含html标签的内容
                        .positiveText(R.string.agree)
                        .negativeText(R.string.disagree)
                        .show();
                break;
            case R.id.basicLongContent: //Basic 长内容
                //类似法律声明
                new MaterialDialog.Builder(this)
                        .title(R.string.useGoogleLocationServices)
                        .content(R.string.loremIpsum)
                        .positiveText(R.string.agree)
                        .negativeText(R.string.disagree)
                        .checkBoxPrompt("Hello, world!", true, null)
                        .show();
                break;
            case R.id.basicIcon: //basic 有图标
                new MaterialDialog.Builder(this)
                        .iconRes(R.mipmap.ic_launcher)
                        .limitIconToDefaultSize() // limits the displayed icon size to 48dp  //限制图标的大小
                        .title(R.string.useGoogleLocationServices)
                        .content(R.string.useGoogleLocationServicesPrompt, true)
                        .positiveText(R.string.agree)
                        .negativeText(R.string.disagree)
                        .show();
                break;
            case R.id.basicCheckPrompt:
                new MaterialDialog.Builder(this)
                        .iconRes(R.mipmap.ic_launcher)
                        .limitIconToDefaultSize()
                        .title(Html.fromHtml(getString(R.string.permissionSample, getString(R.string.app_name))))
                        .positiveText(R.string.allow)
                        .negativeText(R.string.deny)
                        //监听按钮，根据which来判断是哪个按钮被按下
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Toast.makeText(MainActivity.this , "Prompt checked? " + dialog.isPromptCheckBoxChecked(), Toast.LENGTH_SHORT).show();
                            }
                        })
                        .checkBoxPromptRes(R.string.dont_ask_again, false, null) //提示复选框
                        .show();
                break;
            case R.id.stacked: //当按钮并排时太宽时就垂直排列
                new MaterialDialog.Builder(this)
                        .title(R.string.useGoogleLocationServices)
                        .content(R.string.useGoogleLocationServicesPrompt, true)
                        .positiveText(R.string.speedBoost)
                        .negativeText(R.string.noThanks)
                        .btnStackedGravity(GravityEnum.END)
                        .stackingBehavior(  //自动换行，为了测试才使用StackingBehavior.ALWAYS来强制垂直排列
                                StackingBehavior
                                        .ALWAYS) // this generally should not be forced, but is used for demo purposes
                        .show();
                break;
            case R.id.neutral:
                new MaterialDialog.Builder(this)
                        .title(R.string.useGoogleLocationServices)
                        .content(R.string.useGoogleLocationServicesPrompt, true)
                        .positiveText(R.string.agree)
                        .negativeText(R.string.disagree)
                        .neutralText(R.string.more_info)  //显示中立文本在左边
                        .show();
                break;
            case R.id.callbacks:  //添加回调，监听点击按钮
                new MaterialDialog.Builder(this)
                        .title(R.string.useGoogleLocationServices)
                        .content(R.string.useGoogleLocationServicesPrompt, true)
                        .positiveText(R.string.agree)
                        .negativeText(R.string.disagree)
                        .neutralText(R.string.more_info)
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Toast.makeText(MainActivity.this , which.name() + "!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                break;
            case R.id.listNoTitle:
                new MaterialDialog.Builder(this)
                        .items(R.array.socialNetworks)  //list（列表）item
                        .itemsCallback(new MaterialDialog.ListCallback() { //list点击监听
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                Toast.makeText(MainActivity.this , position + ": " + text, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                break;
            case R.id.list:
                new MaterialDialog.Builder(this)
                        .title(R.string.socialNetworks)
                        .items(R.array.socialNetworks)  //list（列表）item
                        .itemsCallback(new MaterialDialog.ListCallback() { //list点击监听
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                Toast.makeText(MainActivity.this , position + ": " + text, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                break;
            case R.id.longList: //长list
                new MaterialDialog.Builder(this)
                        .title(R.string.states)
                        .items(R.array.states)
                        .itemsCallback(new MaterialDialog.ListCallback() { //list点击监听
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                Toast.makeText(MainActivity.this, position + ": " + text, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .positiveText(android.R.string.cancel)
                        .show();
                break;
            case R.id.list_longItems: //list中长item
                new MaterialDialog.Builder(this)
                        .title(R.string.socialNetworks)
                        .items(R.array.socialNetworks_longItems)
                        .itemsCallback(new MaterialDialog.ListCallback() { //list点击监听
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                Toast.makeText(MainActivity.this, position + ": " + text, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                break;
            case R.id.list_checkPrompt:
                new MaterialDialog.Builder(this)
                        .title(R.string.socialNetworks)
                        .items(R.array.socialNetworks)
                        .itemsCallback(new MaterialDialog.ListCallback() { //list点击监听
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                Toast.makeText(MainActivity.this, position + ": " + text, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .checkBoxPromptRes(R.string.example_prompt, true, null)
                        .negativeText(android.R.string.cancel)
                        .show();
                break;
            case R.id.list_longPress:
                index = 0;
                new MaterialDialog.Builder(this)
                        .title(R.string.socialNetworks)
                        .items(R.array.socialNetworks)
                        .itemsCallback(new MaterialDialog.ListCallback() { //点击item
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                Toast.makeText(MainActivity.this, position + ": " + text, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .autoDismiss(false) //不自动消失
                        .itemsLongCallback(  //长按item
                                new MaterialDialog.ListLongCallback() {
                                    @Override
                                    public boolean onLongSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                        dialog.getItems().remove(position); //移除item
                                        dialog.notifyItemsChanged();
                                        return false;
                                    }
                                })
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                index++;
                                dialog.getItems().add("Item " + index); //添加item
                                dialog.notifyItemInserted(dialog.getItems().size() - 1);
                            }
                        })
                        .neutralText(R.string.add_item)
                        .show();
                break;
            case R.id.singleChoice: //单选按钮
                new MaterialDialog.Builder(this)
                        .title(R.string.socialNetworks)
                        .items(R.array.socialNetworks)
                        .itemsDisabledIndices(1, 3) //不使能位置1和3的选项
                        .itemsCallbackSingleChoice(
                                2,  //默认选项
                                new MaterialDialog.ListCallbackSingleChoice() {
                                    @Override
                                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                        Toast.makeText(MainActivity.this, which + ": " + text, Toast.LENGTH_SHORT).show();
                                        return true;
                                    }
                                })
                        .positiveText(R.string.md_choose_label)
                        .show();
                break;
            case R.id.singleChoice_longItems:
                new MaterialDialog.Builder(this)
                        .title(R.string.socialNetworks)
                        .items(R.array.socialNetworks_longItems)
                        .itemsCallbackSingleChoice(
                                2,
                                new MaterialDialog.ListCallbackSingleChoice() {
                                    @Override
                                    public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                        Toast.makeText(MainActivity.this, which + ": " + text, Toast.LENGTH_SHORT).show();
                                        return true;
                                    }
                                })
                        .positiveText(R.string.md_choose_label)
                        .show();
                break;
            case R.id.multiChoice:
                new MaterialDialog.Builder(this)
                        .title(R.string.socialNetworks)
                        .items(R.array.socialNetworks)
                        .itemsCallbackMultiChoice(
                                new Integer[]{1, 3}, //默认被选中的选项
                                new MaterialDialog.ListCallbackMultiChoice() {
                                    @Override
                                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                        StringBuilder str = new StringBuilder();
                                        for (int i = 0; i < which.length; i++) {
                                            if (i > 0) {
                                                str.append('\n');
                                            }
                                            str.append(which[i]);
                                            str.append(": ");
                                            str.append(text[i]);
                                        }
                                        Toast.makeText(MainActivity.this, str.toString()+"" , Toast.LENGTH_SHORT).show();
                                        return true; // allow selection
                                    }
                                })
                        .onNeutral((dialog, which) -> dialog.clearSelectedIndices()) //使用Java8 lambda表达式
                        .onPositive((dialog, which) -> dialog.dismiss())
                        .alwaysCallMultiChoiceCallback() //每次选中或取消都会回调onSelection方法，如果注释掉，要按了positive按钮才回调onSelection方法
                        .positiveText(R.string.md_choose_label)
                        .autoDismiss(false)
                        .neutralText(R.string.clear_selection)
                        .show();
                break;
            case R.id.multiChoiceLimited: //限制选中的项数
                new MaterialDialog.Builder(this)
                        .title(R.string.socialNetworks)
                        .items(R.array.socialNetworks)
                        .itemsCallbackMultiChoice(
                                new Integer[]{1},
                                new MaterialDialog.ListCallbackMultiChoice() {
                                    @Override
                                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                        boolean allowSelectionChange =
                                                which.length
                                                        <= 2; // limit selection to 2, the new (un)selection is included in the which array
                                        if (!allowSelectionChange) {
                                            Toast.makeText(MainActivity.this,getString(R.string.selection_limit_reached),Toast.LENGTH_SHORT).show();
                                        }
                                        return allowSelectionChange; //返回false则不允许被选中
                                    }
                                })
                        .positiveText(R.string.dismiss)
                        .alwaysCallMultiChoiceCallback() // the callback will always be called, to check if (un)selection is still allowed
                        .show();
                break;
            case R.id.multiChoiceLimitedMin: //限制至少选中项数
                new MaterialDialog.Builder(this)
                        .title(R.string.socialNetworks)
                        .items(R.array.socialNetworks)
                        .itemsCallbackMultiChoice(
                                new Integer[]{1},
                                new MaterialDialog.ListCallbackMultiChoice() {
                                    @Override
                                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                        boolean allowSelectionChange =
                                                which.length
                                                        >= 1; // limit selection to 2, the new (un)selection is included in the which array
                                        if (!allowSelectionChange) {
                                            Toast.makeText(MainActivity.this,getString(R.string.selection_min_limit_reached),Toast.LENGTH_SHORT).show();
                                        }
                                        return allowSelectionChange; //返回false则不允许被选中
                                    }
                                })
                        .positiveText(R.string.dismiss)
                        .alwaysCallMultiChoiceCallback() // the callback will always be called, to check if (un)selection is still allowed
                        .show();
                break;
            case R.id.multiChoice_longItems:
                new MaterialDialog.Builder(this)
                        .title(R.string.socialNetworks)
                        .items(R.array.socialNetworks_longItems)
                        .itemsCallbackMultiChoice(
                                new Integer[] {1, 3},
                                (dialog, which, text) -> {
                                    StringBuilder str = new StringBuilder();
                                    for (int i = 0; i < which.length; i++) {
                                        if (i > 0) {
                                            str.append('\n');
                                        }
                                        str.append(which[i]);
                                        str.append(": ");
                                        str.append(text[i]);
                                    }
                                    Toast.makeText(MainActivity.this,str.toString(),Toast.LENGTH_SHORT).show();
                                    return true; // allow selection
                                })
                        .positiveText(R.string.md_choose_label)
                        .show();
                break;
            case R.id.multiChoice_disabledItems:
                new MaterialDialog.Builder(this)
                        .title(R.string.socialNetworks)
                        .items(R.array.socialNetworks)
                        .itemsCallbackMultiChoice(
                                new Integer[] {0, 1, 2},
                                (dialog, which, text) -> {
                                    StringBuilder str = new StringBuilder();
                                    for (int i = 0; i < which.length; i++) {
                                        if (i > 0) {
                                            str.append('\n');
                                        }
                                        str.append(which[i]);
                                        str.append(": ");
                                        str.append(text[i]);
                                    }
                                    Toast.makeText(MainActivity.this,str.toString(),Toast.LENGTH_SHORT).show();
                                    return true; // allow selection
                                })
                        .onNeutral((dialog, which) -> dialog.clearSelectedIndices())
                        .alwaysCallMultiChoiceCallback()
                        .positiveText(R.string.md_choose_label)
                        .autoDismiss(false)
                        .neutralText(R.string.clear_selection)
                        .itemsDisabledIndices(0, 1)  //不允许选择的选项
                        .show();
                break;
            case R.id.simpleList:  //adapter list
                final MaterialSimpleListAdapter adapter =
                        new MaterialSimpleListAdapter(
                                new MaterialSimpleListAdapter.Callback() {
                                    @Override
                                    public void onMaterialListItemSelected(MaterialDialog dialog, int index, MaterialSimpleListItem item) {
                                        Toast.makeText(MainActivity.this, item.getContent().toString(),Toast.LENGTH_SHORT).show();
                                    }
                                });
                adapter.add(
                        new MaterialSimpleListItem.Builder(this)
                                .content("username@gmail.com")      //内容
                                .icon(R.drawable.ic_account_circle) //图标
                                .backgroundColor(Color.WHITE)       //背景色
                                .build());
                adapter.add(
                        new MaterialSimpleListItem.Builder(this)
                                .content("user02@gmail.com")
                                .icon(R.drawable.ic_account_circle)
                                .backgroundColor(Color.WHITE)
                                .build());
                adapter.add(
                        new MaterialSimpleListItem.Builder(this)
                                .content(R.string.add_account)
                                .icon(R.drawable.ic_content_add)
                                .iconPaddingDp(8)
                                .build());

                new MaterialDialog.Builder(this).title(R.string.set_backup).adapter(adapter, null).show();
                break;
            case R.id.customListItems:
                final ButtonItemAdapter customAdapter = new ButtonItemAdapter(this, R.array.socialNetworks);
                customAdapter.setCallbacks(
                        new ButtonItemAdapter.ItemCallback() {
                            @Override
                            public void onItemClicked(int itemIndex) {
                                Toast.makeText(MainActivity.this, "Item clicked: " + itemIndex,Toast.LENGTH_SHORT).show();
                            }
                        },
                        new ButtonItemAdapter.ButtonCallback() {
                            @Override
                            public void onButtonClicked(int buttonIndex) {
                                Toast.makeText(MainActivity.this,"Button clicked: " + buttonIndex,Toast.LENGTH_SHORT).show();
                            }
                        });
                new MaterialDialog.Builder(this).title(R.string.socialNetworks).adapter(customAdapter, null).show();
                break;
            case R.id.customView:
                MaterialDialog dialog =
                        new MaterialDialog.Builder(this)
                                .title(R.string.googleWifi)
                                .customView(R.layout.dialog_customview, true) //可以滚动
                                .positiveText(R.string.connect)
                                .negativeText(android.R.string.cancel)
                                .onPositive(
                                        new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                Toast.makeText(MainActivity.this, "Password: " + passwordInput.getText().toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                .build();

                positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
                //noinspection ConstantConditions
                passwordInput = dialog.getCustomView().findViewById(R.id.password);
                passwordInput.addTextChangedListener(
                        new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                positiveAction.setEnabled(s.toString().trim().length() > 0);
                            }

                            @Override
                            public void afterTextChanged(Editable s) {}
                        });

                // Toggling the show password CheckBox will mask or unmask the password input EditText
                CheckBox checkbox = dialog.getCustomView().findViewById(R.id.showPassword);
                checkbox.setOnCheckedChangeListener(
                        (buttonView, isChecked) -> {
                            passwordInput.setInputType(
                                    !isChecked ? InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_CLASS_TEXT);
                            passwordInput.setTransformationMethod(
                                    !isChecked ? PasswordTransformationMethod.getInstance() : null);
                        });

                int widgetColor = ThemeSingleton.get().widgetColor;
                MDTintHelper.setTint(
                        checkbox, widgetColor == 0 ? ContextCompat.getColor(this, R.color.accent) : widgetColor);

                MDTintHelper.setTint(
                        passwordInput,
                        widgetColor == 0 ? ContextCompat.getColor(this, R.color.accent) : widgetColor);

                dialog.show();
                positiveAction.setEnabled(false); // disabled by default
                break;
            case R.id.customView_webView:
                int accentColor = ThemeSingleton.get().widgetColor;
                if (accentColor == 0) {
                    accentColor = ContextCompat.getColor(this, R.color.accent);
                }
                ChangelogDialog.create(false, accentColor).show(getSupportFragmentManager(), "changelog");
                break;
            case R.id.customView_datePicker:
                new MaterialDialog.Builder(this)
                        .title(R.string.date_picker)
                        .customView(R.layout.dialog_datepicker, false) //不能滚动
                        .positiveText(android.R.string.ok)
                        .negativeText(android.R.string.cancel)
                        .show();
                break;
            case R.id.colorChooser_primary:
                new ColorChooserDialog.Builder(this, R.string.color_palette)
                        .titleSub(R.string.colors) //子菜单标题
                        .preselect(primaryPreselect)  //预选的颜色
                        .show(this);
                break;
            case R.id.colorChooser_accent:
                new ColorChooserDialog.Builder(this, R.string.color_palette)
                        .titleSub(R.string.colors)
                        .accentMode(true)  //使用accent，默认使用primary
                        .preselect(accentPreselect)
                        .show(this);
                break;
            case R.id.colorChooser_customColors:
                int[][] subColors =
                        new int[][] {
                                new int[] {
                                        Color.parseColor("#EF5350"), Color.parseColor("#F44336"), Color.parseColor("#E53935")
                                },
                                new int[] {
                                        Color.parseColor("#EC407A"), Color.parseColor("#E91E63"), Color.parseColor("#D81B60")
                                },
                                new int[] {
                                        Color.parseColor("#AB47BC"), Color.parseColor("#9C27B0"), Color.parseColor("#8E24AA")
                                },
                                new int[] {
                                        Color.parseColor("#7E57C2"), Color.parseColor("#673AB7"), Color.parseColor("#5E35B1")
                                },
                                new int[] {
                                        Color.parseColor("#5C6BC0"), Color.parseColor("#3F51B5"), Color.parseColor("#3949AB")
                                },
                                new int[] {
                                        Color.parseColor("#42A5F5"), Color.parseColor("#2196F3"), Color.parseColor("#1E88E5")
                                }
                        };

                new ColorChooserDialog.Builder(this, R.string.color_palette)
                        .titleSub(R.string.colors)
                        .preselect(primaryPreselect)
                        .customColors(R.array.custom_colors, subColors) //自定义主颜色和次菜单的颜色
                        .show(this);
                break;
            case R.id.colorChooser_customColorsNoSub:
                new ColorChooserDialog.Builder(this, R.string.color_palette)
                        .titleSub(R.string.colors)
                        .preselect(primaryPreselect)
                        .customColors(R.array.custom_colors, null) //没有次菜单
                        .show(this);
                break;
            case R.id.input:
                new MaterialDialog.Builder(this)
                        .title(R.string.input)
                        .content(R.string.input_content)
                        .inputType(
                                InputType.TYPE_CLASS_TEXT
                                        | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                        | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                        .inputRange(2, 16)  //对输入的字符数进行限制
                        .positiveText(R.string.submit)
                        .input(
                                R.string.input_hint,   //提示内容
                                R.string.input_hint,  //预填充
                                false, //不允许输入空值
                                new MaterialDialog.InputCallback() {
                                    @Override
                                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                        Toast.makeText(MainActivity.this, "Hello, " + input.toString() + "!",Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .show();
                break;
            case R.id.input_custominvalidation:
                new MaterialDialog.Builder(this)
                        .title(R.string.input)
                        .content(R.string.input_content_custominvalidation)
                        .inputType(
                                InputType.TYPE_CLASS_TEXT
                                        | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                        | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                        .positiveText(R.string.submit)
                        .alwaysCallInputCallback() // this forces the callback to be invoked with every input change
                        .input(
                                R.string.input_hint,
                                0,
                                false,
                                new MaterialDialog.InputCallback() {
                                    @Override
                                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                        if (input.toString().equalsIgnoreCase("hello")) {
                                            dialog.setContent("我不是告诉你不要输入hello了!");
                                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
                                        } else {
                                            dialog.setContent(R.string.input_content_custominvalidation);
                                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
                                        }
                                    }
                                })
                        .show();
                break;
            case R.id.input_checkPrompt:
                new MaterialDialog.Builder(this)
                        .title(R.string.input)
                        .content(R.string.input_content)
                        .inputType(
                                InputType.TYPE_CLASS_TEXT
                                        | InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                                        | InputType.TYPE_TEXT_FLAG_CAP_WORDS)
                        .inputRange(2, 16)
                        .positiveText(R.string.submit)
                        .input(
                                R.string.input_hint,
                                R.string.input_hint,
                                false,
                                new MaterialDialog.InputCallback() {
                                    @Override
                                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                        Toast.makeText(MainActivity.this, "Hello, " + input.toString() + "!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .checkBoxPromptRes(R.string.example_prompt, true, null)
                        .show();
                break;
            case R.id.progress1:
                new MaterialDialog.Builder(this)
                        .title(R.string.progress_dialog)
                        .content(R.string.please_wait)
                        .contentGravity(GravityEnum.CENTER)
                        //第一个参数：是否是明确的进度条(不明确的就是转圈圈，明确的就是数值进度条)
                        //第二个参数：最大值
                        //是否显示最大和最小值    右边会显示min/max
                        .progress(false, 150, true)
                        //取消按钮监听器
                        .cancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialogInterface) {
                                if (thread != null) {
                                    thread.interrupt();
                                }
                            }
                        })
                        .showListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialogInterface) {
                                final MaterialDialog dialog = (MaterialDialog) dialogInterface;
                                startThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        while (dialog.getCurrentProgress() != dialog.getMaxProgress()
                                                && !Thread.currentThread().isInterrupted()) {
                                            if (dialog.isCancelled()) {
                                                break;
                                            }
                                            try {
                                                Thread.sleep(50);
                                            } catch (InterruptedException e) {
                                                break;
                                            }
                                            dialog.incrementProgress(1);
                                        }
                                        runOnUiThread(  //更新主UI界面，该方法是Activity的
                                                new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        thread = null;
                                                        dialog.setContent(getString(R.string.md_done_label));
                                                    }
                                                });
                                    }
                                });
                            }
                        })
                        .show();
                break;
            case R.id.progress2:
                showIndeterminateProgressDialog(false);  //转圈圈
                break;
            case R.id.progress3:
                showIndeterminateProgressDialog(true);  //水平进度条(无数值)
                break;
            case R.id.preference_dialogs:
                startActivity(new Intent(getApplicationContext(), PreferenceActivity.class));
                break;
            case R.id.themed:
                new MaterialDialog.Builder(this)
                        .title(R.string.useGoogleLocationServices)
                        .content(R.string.useGoogleLocationServicesPrompt, true)
                        .positiveText(R.string.agree)
                        .negativeText(R.string.disagree)
                        .positiveColorRes(R.color.material_red_400)
                        .negativeColorRes(R.color.material_red_400)
                        .titleGravity(GravityEnum.CENTER)  //标题位置
                        .titleColorRes(R.color.material_red_400) //标题颜色
                        .contentColorRes(android.R.color.white)  //内容颜色
                        .backgroundColorRes(R.color.material_blue_grey_800) //背景颜色
                        .dividerColorRes(R.color.accent) //分割线颜色
                        .btnSelector(R.drawable.md_btn_selector_custom, DialogAction.POSITIVE) //默认选中的button
                        .positiveColor(Color.WHITE)
                        .negativeColorAttr(android.R.attr.textColorSecondaryInverse)
                        .theme(Theme.DARK)  //主题
                        .show();
                break;
            case R.id.showCancelDismiss: //各种监听事件
                new MaterialDialog.Builder(this)
                        .title(R.string.useGoogleLocationServices)
                        .content(R.string.useGoogleLocationServicesPrompt)
                        .positiveText(R.string.agree)
                        .negativeText(R.string.disagree)
                        .neutralText(R.string.more_info)
                        .showListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialogInterface) {
                                Toast.makeText(MainActivity.this, "onShow", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .cancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialogInterface) {
                                Toast.makeText(MainActivity.this, "onCancel", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .dismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialogInterface) {
                                Toast.makeText(MainActivity.this, "onDismiss", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                break;
            case R.id.file_chooser:
                chooserDialog = R.id.file_chooser;
                //判断是否有读取外部存储的权限
                if (ActivityCompat.checkSelfPermission(
                        MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            MainActivity.this,
                            new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                            STORAGE_PERMISSION_RC);
                    return;
                }
                new FileChooserDialog.Builder(this).show(this);
                break;
            case R.id.folder_chooser:
                chooserDialog = R.id.folder_chooser;
                if (ActivityCompat.checkSelfPermission(
                        MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            MainActivity.this,
                            new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            STORAGE_PERMISSION_RC);
                    return;
                }
                new FolderChooserDialog.Builder(MainActivity.this)
                        .chooseButton(R.string.md_choose_label)
                        .allowNewFolder(true, 0)
                        .show(this);
                break;
        }
    }

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, int selectedColor) {
        if (dialog.isAccentMode()) {
            accentPreselect = selectedColor;
            ThemeSingleton.get().positiveColor = DialogUtils.getActionTextStateList(this, selectedColor);
            ThemeSingleton.get().neutralColor = DialogUtils.getActionTextStateList(this, selectedColor);
            ThemeSingleton.get().negativeColor = DialogUtils.getActionTextStateList(this, selectedColor);
            ThemeSingleton.get().widgetColor = selectedColor;
        } else {
            primaryPreselect = selectedColor;
            if (getSupportActionBar() != null) {
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(selectedColor));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(CircleView.shiftColorDown(selectedColor));
                getWindow().setNavigationBarColor(selectedColor);
            }
        }
    }

    @Override
    public void onColorChooserDismissed(@NonNull ColorChooserDialog dialog) {
        Toast.makeText(MainActivity.this, "Color chooser dismissed!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFileSelection(@NonNull FileChooserDialog dialog, @NonNull File file) {
        Toast.makeText(MainActivity.this, ""+file.getAbsolutePath(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFileChooserDismissed(@NonNull FileChooserDialog dialog) {
        Toast.makeText(MainActivity.this, ""+"File chooser dismissed!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFolderSelection(@NonNull FolderChooserDialog dialog, @NonNull File folder) {
        Toast.makeText(MainActivity.this, ""+folder.getAbsolutePath(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFolderChooserDismissed(@NonNull FolderChooserDialog dialog) {
        Toast.makeText(MainActivity.this, ""+"Folder chooser dismissed!",Toast.LENGTH_SHORT).show();
    }

    private void startThread(Runnable run) {
        if (thread != null) {
            thread.interrupt();
        }
        thread = new Thread(run);
        thread.start();
    }

    private void showIndeterminateProgressDialog(boolean horizontal) {
        new MaterialDialog.Builder(this)
                .title(R.string.progress_dialog)
                .content(R.string.please_wait)
                .progress(true, 0) //不明确的进度条(转圈圈)
                .progressIndeterminateStyle(horizontal)
                .show();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_RC) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                handler.postDelayed(() -> findViewById(chooserDialog).performClick(), 1000);
            } else {
                Toast.makeText(
                        this,
                        "The folder or file chooser will not work without "
                                + "permission to read external storage.",
                        Toast.LENGTH_LONG)
                        .show();
            }
        }
    }
}
