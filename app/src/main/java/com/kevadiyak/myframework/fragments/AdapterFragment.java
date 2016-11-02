package com.kevadiyak.myframework.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.kevadiyak.commonutils.common.Logs;
import com.kevadiyak.mvvmarchitecture.MvvmFragment;
import com.kevadiyak.mvvmarchitecture.common.BindingConfig;
import com.kevadiyak.myframework.BR;
import com.kevadiyak.myframework.R;
import com.kevadiyak.myframework.databinding.FragmentAdapterBinding;
import com.kevadiyak.myframework.other.adapter.Data;
import com.kevadiyak.myframework.other.adapter.Header;
import com.kevadiyak.myframework.other.adapter.Point;
import com.kevadiyak.myframework.viewmodels.AdapterFragmentViewModel;
import com.kevadiyak.recycleadapter.CustomBindAdapter;

public class AdapterFragment extends MvvmFragment<FragmentAdapterBinding, AdapterFragmentViewModel>
        /*implements CustomBindAdapter.LayoutHandler,
        CustomBindAdapter.OnBindListener,
        CustomBindAdapter.OnClickListener,
        CustomBindAdapter.OnLongClickListener*/ {

    private Data data;

    @NonNull
    @Override
    public AdapterFragmentViewModel createViewModel() {
        return new AdapterFragmentViewModel(getActivity(), Logs.getInstance(getActivity()));
    }

    @NonNull
    @Override
    public BindingConfig getBindingConfig() {
        return new BindingConfig(R.layout.fragment_adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        data = new Data();

        CustomBindAdapter.with(data.getItems(), BR.item)
                .map(Header.class, R.layout.item_header)
                .map(Point.class, R.layout.item_point)
                //.onBindListener(this)
                /*.onClickListener(new CustomBindAdapter.OnClickListener() {
                    @Override
                    public void onClick(Object item, View view, int type, int position) {
                        if(item instanceof Point) {
                            Point point = (Point) item;
                            Log.e("Krunal", "x->" + point.getX() + " | y->" + point.getY());
                            Toast.makeText(v.getContext(), "Click on Header " + text, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, R.id.pointX)*/
                //.onLongClickListener(this) //Toast.makeText(v.getContext(), "Click on Point(" + x + "," + y + ")", Toast.LENGTH_SHORT).show();
                /*.onLoadMoreListener(new CustomBindAdapter.OnLoadMoreListener() {
                    @Override
                    public boolean onLoadMore(int size) {
                        data.setMoreData();
                        return false;
                    }
                })*/
                .onSwipMenuListener(R.id.view_main_content, R.id.view_start, R.id.view_end)
                //.onSwipMenuListener(R.id.view_main_content, 0, R.id.view_end)
                .into(getBinding().list, new LinearLayoutManager(getContext()));

        /*CustomBindAdapter.with(new Data().getItems(), BR.item)
                //.map(Header.class, R.layout.item_header)
                //.map(Point.class, R.layout.item_point)
                .layoutHandler(this)
                .onBindListener(this)
                .onClickListener(this)
                .onLongClickListener(this)
                .into(list);*/
    }

    /*@Override
    public int getItemLayout(@NotNull Object item, int position) {
        if (item instanceof Header) {
            if (position == 0)
                return R.layout.item_header_first;
            else
                return R.layout.item_header;
        } else
            return R.layout.item_point;
    }

    @Override
    public void onBind(@NotNull Object item, @NotNull View view, int type, int position) {
        switch (type) {
            case R.layout.item_header_first:
                ItemHeaderFirstBinding headerFirstBinding = DataBindingUtil.getBinding(view);
                headerFirstBinding.headerFirstText.setTag("firstHeader");
                break;
            case R.layout.item_header:
                ItemHeaderBinding headerBinding = DataBindingUtil.getBinding(view);
                Header header = (Header) item;
                headerBinding.headerText.setTag("header" + header.getText());
                break;
            case R.layout.item_point:
                ItemPointBinding pointBinding = DataBindingUtil.getBinding(view);
                Point point = (Point) item;
                pointBinding.pointX.setTag("X:" + point.getX());
                pointBinding.pointY.setTag("Y:" + point.getY());
                break;
        }
    }

    @Override
    public void onClick(@NotNull Object item, @NotNull View view, int type, int position) {
        Toast.makeText(this, "onClick position " +position +": " +item, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongClick(@NotNull Object item, @NotNull View view, int type, int position) {
        Toast.makeText(this, "onLongClick position " +position +": " +item, Toast.LENGTH_SHORT).show();
    }*/
}
