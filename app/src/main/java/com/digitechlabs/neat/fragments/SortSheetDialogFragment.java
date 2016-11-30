package com.digitechlabs.neat.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dgtech.neat.R;

/**
 * Created by Digitech-labs on 11/28/2016.
 */

public class SortSheetDialogFragment extends BottomSheetDialogFragment {

    String mString;

    public static SortSheetDialogFragment newInstance(String string) {
        SortSheetDialogFragment f = new SortSheetDialogFragment();
        Bundle args = new Bundle();
        args.putString("string", string);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mString = getArguments().getString("string");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sort_sheet, container, false);



        return v;
    }
}

