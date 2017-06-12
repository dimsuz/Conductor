package com.bluelinelabs.conductor.demo.controllers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.VerticalChangeHandler;
import com.bluelinelabs.conductor.demo.R;
import com.bluelinelabs.conductor.demo.controllers.base.BaseController;
import com.bluelinelabs.conductor.demo.util.BundleBuilder;

import butterknife.BindView;

public class ChildController extends BaseController {

    private static final String KEY_TITLE = "ChildController.title";
    private static final String KEY_BG_COLOR = "ChildController.bgColor";
    private static final String KEY_COLOR_IS_RES = "ChildController.colorIsResId";

    @BindView(R.id.tv_title) TextView tvTitle;

    public ChildController(String title, int backgroundColor, boolean colorIsResId) {
        this(new BundleBuilder(new Bundle())
                .putString(KEY_TITLE, title)
                .putInt(KEY_BG_COLOR, backgroundColor)
                .putBoolean(KEY_COLOR_IS_RES, colorIsResId)
                .build());
    }

    public ChildController(Bundle args) {
        super(args);
    }

    @NonNull
    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_child, container, false);
    }

    private String getLogTag() {
        return "Child Controller " + getArgs().getString(KEY_TITLE);
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        Log.d(getLogTag(), "onCreateView()");
        return super.onCreateView(inflater, container);
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        Log.d(getLogTag(), "onAttach()");
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);
        Log.d(getLogTag(), "onDetach()");
    }

    @Override
    protected void onDestroyView(@NonNull View view) {
        super.onDestroyView(view);
        Log.d(getLogTag(), "onDestroyView()");
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);

        tvTitle.setText(getArgs().getString(KEY_TITLE));
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RouterTransaction transaction = RouterTransaction.with(new TextController("Hello"))
                        .pushChangeHandler(new VerticalChangeHandler());
                getParentController().getRouter().pushController(transaction);
            }
        });

        int bgColor = getArgs().getInt(KEY_BG_COLOR);
        if (getArgs().getBoolean(KEY_COLOR_IS_RES)) {
            bgColor = ContextCompat.getColor(getActivity(), bgColor);
        }
        view.setBackgroundColor(bgColor);
    }
}
