package com.kevadiyakrunalk.rxfilepicker.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kevadiyakrunalk.rxfilepicker.R;
import com.kevadiyakrunalk.rxfilepicker.adapter.FileListAdapter;
import com.kevadiyakrunalk.rxfilepicker.model.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Doc fragment.
 */
public class DocFragment extends BaseFragment {

    private static final String TAG = DocFragment.class.getSimpleName();
    /**
     * The Recycler view.
     */
    RecyclerView recyclerView;

    /**
     * The Empty view.
     */
    TextView emptyView;

    private PhotoPickerFragmentListener mListener;
    private FileListAdapter fileListAdapter;
    private ArrayList<String> selectedPaths;

    /**
     * Instantiates a new Doc fragment.
     */
    public DocFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.file_picker, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PhotoPickerFragmentListener) {
            mListener = (PhotoPickerFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement PhotoPickerFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * New instance doc fragment.
     *
     * @param selectedPaths the selected paths
     * @return the doc fragment
     */
    public static DocFragment newInstance(ArrayList<String> selectedPaths) {
        DocFragment photoPickerFragment = new DocFragment();
        photoPickerFragment.selectedPaths = selectedPaths;
        return photoPickerFragment;
    }

    /**
     * The interface Photo picker fragment listener.
     */
    public interface PhotoPickerFragmentListener {
        // TODO: Update argument type and name
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setView(view);
        initView();
    }

    private void setView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        emptyView = (TextView) view.findViewById(R.id.empty_view);
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setVisibility(View.GONE);
    }

    /**
     * Update list.
     *
     * @param dirs the dirs
     */
    public void updateList(List<Document> dirs) {
        if (getView() == null)
            return;

        if (dirs.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);

            FileListAdapter fileListAdapter = (FileListAdapter) recyclerView.getAdapter();
            if (fileListAdapter == null) {
                fileListAdapter = new FileListAdapter(getActivity(), dirs, selectedPaths);

                recyclerView.setAdapter(fileListAdapter);
            } else {
                fileListAdapter.setData(dirs);
                fileListAdapter.notifyDataSetChanged();
            }
        } else {
            recyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
    }

}
