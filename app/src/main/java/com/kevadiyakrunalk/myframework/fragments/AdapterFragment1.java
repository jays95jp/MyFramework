package com.kevadiyakrunalk.myframework.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.kevadiyakrunalk.commonutils.common.Logs;
import com.kevadiyakrunalk.mvvmarchitecture.MvvmFragment;
import com.kevadiyakrunalk.mvvmarchitecture.common.BindingConfig;
import com.kevadiyakrunalk.myframework.BR;
import com.kevadiyakrunalk.myframework.R;
import com.kevadiyakrunalk.myframework.databinding.FragmentAdapterBinding;
import com.kevadiyakrunalk.myframework.databinding.ItemHeaderBinding;
import com.kevadiyakrunalk.myframework.databinding.ItemHeaderFirstBinding;
import com.kevadiyakrunalk.myframework.databinding.ItemTextBinding;
import com.kevadiyakrunalk.myframework.other.adapter.Data;
import com.kevadiyakrunalk.myframework.other.adapter.Header;
import com.kevadiyakrunalk.myframework.other.adapter.Items;
import com.kevadiyakrunalk.myframework.viewmodels.AdapterFragmentViewModel;
import com.kevadiyakrunalk.recycleadapter.RxBinderAdapter;
import com.kevadiyakrunalk.recycleadapter.RxDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

public class AdapterFragment1 extends MvvmFragment<FragmentAdapterBinding, AdapterFragmentViewModel>
        /*implements RxBinderAdapter.LayoutHandler,
        RxBinderAdapter.OnBindListener,
        RxBinderAdapter.OnClickListener,
        RxBinderAdapter.OnLongClickListener,
        RxBinderAdapter.OnLoadMoreListener*/ {

    Data data;
    List<Object> dataSet;
    RxDataSource rxDataSource;

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
        setHasOptionsMenu(true);
        data = new Data();
        dataSet = new ArrayList<>();
        dataSet.addAll(data.getItems());

        /*rxDataSource = new RxDataSource(dataSet);
        rxDataSource.repeat(1)
        .<RxBinderAdapter.ViewHolder>bindRecyclerView(
                RxBinderAdapter.with(data.getItems(), BR.item)
                    .map(Header.class, R.layout.item_header)
                    .map(Items.class, R.layout.item_text)
                    //.layoutHandler(this)
                    .onBindListener(this)
                    //.onClickListener(this, R.id.btn_drag)
                    //.onLongClickListener(this, R.id.btn_drag)
                    .onClickListener(this)
                    .onLongClickListener(this)
                    .onLoadMoreListener(this)
                    //.onSwipMenuListener(R.id.view_main_content, R.id.view_start, R.id.view_end)
                    //.onSwipMenuListener(R.id.view_main_content, 0, R.id.view_end)
                    .into(getBinding().list, new LinearLayoutManager(getActivity())))
        .subscribe(viewHolder -> {
            *//*ItemLayoutBinding b = viewHolder.getViewDataBinding();
            String item = ((String) viewHolder.getItem());
            b.textViewItem.setText(String.valueOf(item));*//*
        });

        rxDataSource.filter(new Func1<Object, Boolean>() {
            @Override
            public Boolean call(Object s) {
                if(s instanceof Items)
                    return ((Items) s).getText().length() > 0;
                else if(s instanceof Header)
                    return ((Header) s).getText().length() > 0;
                else
                    return false;
            }
        }).map(new Func1<Object, Object>() {
            @Override
            public Object call(Object s) {
                if(s instanceof Items)
                    ((Items) s).setText(((Items) s).getText().toLowerCase());
                return s;
            }
        }).updateAdapter();*/
    }

    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem item = menu.add("Search");
        item.setIcon(android.R.drawable.ic_menu_search); // sets icon
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        SearchView sv = new SearchView(getActivity());

        // implementing the listener
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                rxDataSource.updateDataSet(dataSet) //base items should remain the same
                        .filter(new Func1<Object, Boolean>() {
                            @Override
                            public Boolean call(Object s) {
                                if (s instanceof Items) {
                                    return ((Items) s).getText().toLowerCase().contains(newText);
                                } else if (s instanceof Header) {
                                    return ((Header) s).getText().toLowerCase().contains(newText);
                                } else
                                    return true;
                            }
                        }).updateAdapter();
                return false;
            }
        });
        item.setActionView(sv);
    }

    @Override
    public int getItemLayout(Object item, int position) {
        if (item instanceof Header) {
            if (position == 0)
                return R.layout.item_header_first;
            else
                return R.layout.item_header;
        } else
            return R.layout.item_text;
    }

    @Override
    public void onBind(Object item, View view, int type, int position) {
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
            case R.layout.item_text:
                ItemTextBinding pointBinding = DataBindingUtil.getBinding(view);
                Items point = (Items) item;
                pointBinding.tvItems.setTag("Item:" + point.getText());
                break;
        }
    }

    @Override
    public void onClick(Object item, View view, int type, int position) {
        Toast.makeText(getActivity(), "onClick position " +position +": " +item, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongClick(Object item, View view, int type, int position) {
        Toast.makeText(getActivity(), "onLongClick position " +position +": " +item, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLoadMore(int size) {
        Observable.just("")
                .delay(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        dataSet.addAll(data.getMoreData());
                        rxDataSource.updateDataSet(dataSet).updateAdapter();
                    }
                });
        return false;
    }*/
}
