package com.example.androidfilesystem;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.Toolbar;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class FilesListToolbar extends Toolbar {
    private boolean mQuitButtonEnabled;
    private CharSequence mTitle;

    public FilesListToolbar( Context context) {
        super(context);
    }

    public FilesListToolbar( Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    public FilesListToolbar( Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setQuitButtonEnabled(boolean enabled) {
        mQuitButtonEnabled = enabled;
    }

    public void setMultiChoiceModeEnabled(boolean enabled) {
        getMenu().clear();
        if (enabled) {
            inflateMenu(R.menu.files_list_multi_choice);
            mTitle = getTitle();
            setTitle(null);
            setNavigationIcon(Utils.attrToResId(getContext(), R.attr.efp__ic_action_cancel));
        } else {
            inflateMenu(R.menu.files_list_single_choice);
            if (!TextUtils.isEmpty(mTitle)) {
                setTitle(mTitle);
            }
            if (mQuitButtonEnabled) {
                setNavigationIcon(Utils.attrToResId(getContext(), R.attr.efp__ic_action_cancel));
            } else {
                setNavigationIcon(null);
            }
        }
    }
}
