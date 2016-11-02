package com.kevadiyak.rxfilepicker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.internal.util.Predicate;
import com.kevadiyak.rxfilepicker.R;
import com.kevadiyak.rxfilepicker.PickerManager;
import com.kevadiyak.rxfilepicker.adapter.SectionsPagerAdapter;
import com.kevadiyak.rxfilepicker.cursors.loadercallbacks.FileResultCallback;
import com.kevadiyak.rxfilepicker.model.Document;
import com.kevadiyak.rxfilepicker.model.FileType;
import com.kevadiyak.rxfilepicker.util.MediaStoreHelper;
import com.kevadiyak.rxfilepicker.util.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * The type Doc picker fragment.
 */
public class DocPickerFragment extends BaseFragment {
    /**
     * The Tab layout.
     */
    TabLayout tabLayout;
    /**
     * The View pager.
     */
    ViewPager viewPager;
    private ArrayList<String> selectedPaths;
    private ProgressBar progressBar;

    /**
     * Instantiates a new Doc picker fragment.
     */
    public DocPickerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.file_fragment, container, false);
    }

    /**
     * New instance doc picker fragment.
     *
     * @param selectedPaths the selected paths
     * @return the doc picker fragment
     */
    public static DocPickerFragment newInstance(ArrayList<String> selectedPaths) {
        DocPickerFragment docPickerFragment = new DocPickerFragment();
        docPickerFragment.selectedPaths = selectedPaths;
        return docPickerFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setViews(view);
        initView();
    }

    private void initView() {
        setUpViewPager();
        setData();
    }

    private void setViews(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
    }

    private void setData() {
        MediaStoreHelper.getDocs(files -> {
            progressBar.setVisibility(View.GONE);
            setDataOnFragments(files);
        });
    }

    private void setDataOnFragments(List<Document> files) {
        SectionsPagerAdapter sectionsPagerAdapter = (SectionsPagerAdapter) viewPager.getAdapter();
        if (sectionsPagerAdapter != null) {
            for (int index = 0; index < sectionsPagerAdapter.getCount(); index++) {
                DocFragment docFragment = (DocFragment) sectionsPagerAdapter.getItem(index);
                if (docFragment != null) {
                    docFragment.updateList(filterDocuments(sectionsPagerAdapter.getPageTitle(index), files));
                }
            }
        }
    }

    private void setUpViewPager() {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getChildFragmentManager());
        ArrayList<FileType> fileTypes = PickerManager.getInstance().getFilesType();
        for(FileType s : fileTypes) {
           adapter.addFragment(DocFragment.newInstance(selectedPaths), s.getGroupTitle());
        }
        viewPager.setOffscreenPageLimit(fileTypes.size());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private ArrayList<Document> filterDocuments(final CharSequence type, List<Document> documents) {
        final Predicate<Document> docType = document -> document.isThisType(type);

        return new ArrayList<>(Utils.filter(new HashSet<>(documents), docType));
    }
}
