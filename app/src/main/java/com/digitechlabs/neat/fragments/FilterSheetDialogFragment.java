package com.digitechlabs.neat.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.BottomSheetDialogFragment;

import com.dgtech.neat.R;

/**
 * Created by Digitech-labs on 11/27/2016.
 */

public class FilterSheetDialogFragment extends BottomSheetDialogFragment {

    String mString;

    public static FilterSheetDialogFragment newInstance(String string) {
        FilterSheetDialogFragment f = new FilterSheetDialogFragment();
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
        View v = inflater.inflate(R.layout.filter_sheet, container, false);



        return v;
    }
}
