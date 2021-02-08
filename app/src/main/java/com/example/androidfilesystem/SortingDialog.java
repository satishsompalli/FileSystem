package com.example.androidfilesystem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class SortingDialog implements DialogInterface.OnClickListener {
    private final AlertDialog.Builder mAlert;
    private OnSortingSelectedListener mOnSortingSelectedListener;

    public SortingDialog(Context context) {
        mAlert = new AlertDialog.Builder(context);
        mAlert.setItems(context.getResources().getStringArray(R.array.efp__sorting_types), this);
    }

    public void setOnSortingSelectedListener(OnSortingSelectedListener listener) {
        mOnSortingSelectedListener = listener;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        SortingType sortingType = SortingType.ALPHABETICAL;
        switch (which) {
            case 0:
                sortingType = SortingType.ALPHABETICAL;
                break;
            case 1:
                sortingType = SortingType.EXTENSION;
                break;
            case 2:
                sortingType = SortingType.CHRONOLOGICAL;
                break;
        }
        mOnSortingSelectedListener.onSortingSelected(sortingType);
    }

    public void show() {
        mAlert.show();
    }

    public interface OnSortingSelectedListener {
        void onSortingSelected(SortingType sortingType);
    }

    public enum SortingType {
        ALPHABETICAL, CHRONOLOGICAL, EXTENSION
    }
}
