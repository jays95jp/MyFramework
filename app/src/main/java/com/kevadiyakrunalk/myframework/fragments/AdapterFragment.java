package com.kevadiyakrunalk.myframework.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.kevadiyakrunalk.commonutils.common.Logs;
import com.kevadiyakrunalk.mvvmarchitecture.MvvmFragment;
import com.kevadiyakrunalk.mvvmarchitecture.common.BindingConfig;
import com.kevadiyakrunalk.myframework.BR;
import com.kevadiyakrunalk.myframework.R;
import com.kevadiyakrunalk.myframework.databinding.FragmentAdapterBinding;
import com.kevadiyakrunalk.myframework.other.adapter.Header;
import com.kevadiyakrunalk.myframework.other.adapter.Items;
import com.kevadiyakrunalk.myframework.viewmodels.AdapterFragmentViewModel;
import com.kevadiyakrunalk.recycleadapter.RxGenericsAdapter;
import com.kevadiyakrunalk.recycleadapter.RxGenericsDataSource;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AdapterFragment extends MvvmFragment<FragmentAdapterBinding, AdapterFragmentViewModel> {

    private List<Object> mData;
    RxGenericsDataSource<Object> rxDataSource;

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
        setData1();

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.item_text, null);

        rxDataSource = new RxGenericsDataSource<>(mData);
        rxDataSource.repeat(1)
        .<RxGenericsAdapter.MyBaseViewHolder>bindRecyclerView(
                 RxGenericsAdapter.with(mData, BR.item)
                .map(Header.class, R.layout.item_header)
                .map(Items.class, R.layout.item_text)
                //.onSwapMenuListener(R.id.container, -0.8f, 0.8f)
                .onDragListener(R.id.drag_handle)
                .onExpandListener(R.id.indicator, savedInstanceState)
                .onClickListener(new RxGenericsAdapter.OnClickListener() {
                    @Override
                    public void onClick(RxGenericsAdapter.ItemViewTypePosition detail) {

                    }
                })
                .into(view.getMeasuredHeight(), getBinding().list, new LinearLayoutManager(getActivity())))
        .subscribe(viewHolder -> {

        });
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
    }*/

    public void setData1() {
        final String groupItems = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        mData = new LinkedList<>();
        int sectionCount = 1;
        for (int i = 0; i < groupItems.length(); i++) {
            final long groupId = i;
            final boolean isSection = (groupItems.charAt(i) == '|');
            final String groupText = isSection ? ("Section " + sectionCount) : Character.toString(groupItems.charAt(i));
            final Header group = new Header(groupId, isSection, groupText);
            mData.add(group);
        }
    }

    public void setData() {
        //final String groupItems = "|ABC|DEF|GHI|JKL|MNO|PQR|STU|VWX|YZ";
        final String groupItems = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String childItems = "abc";

        mData = new LinkedList<>();
        int sectionCount = 1;
        for (int i = 0; i < groupItems.length(); i++) {
            final long groupId = i;
            final boolean isSection = (groupItems.charAt(i) == '|');
            final String groupText = isSection ? ("Section " + sectionCount) : Character.toString(groupItems.charAt(i));
            final Header group = new Header(groupId, isSection, groupText);
            final List<Object> children = new ArrayList<>();

            if (isSection) {
                sectionCount += 1;
            } else {
                for (int j = 0; j < childItems.length(); j++) {
                    final long childId = group.generateNewChildId();
                    final String childText = Character.toString(childItems.charAt(j));
                    children.add(new Items(childId, childText));
                }
            }

            mData.add(new Pair<Object, List<Object>>(group, children));
        }
    }
}
