package com.kevadiyak.rxfilepicker.adapter;

import android.support.v7.widget.RecyclerView;

import com.kevadiyak.rxfilepicker.PickerManager;
import com.kevadiyak.rxfilepicker.model.BaseFile;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Selectable adapter.
 *
 * @param <VH> the type parameter
 * @param <T>  the type parameter
 */
public abstract class SelectableAdapter<VH extends RecyclerView.ViewHolder, T extends BaseFile>
        extends RecyclerView.Adapter<VH> implements Selectable<T> {

    private static final String TAG = SelectableAdapter.class.getSimpleName();
    private List<T> items;

    /**
     * The Selected photos.
     */
    protected List<T> selectedPhotos;

    /**
     * Instantiates a new Selectable adapter.
     *
     * @param items         the items
     * @param selectedPaths the selected paths
     */
    public SelectableAdapter(List<T> items, List<String> selectedPaths) {
        this.items = items;
        selectedPhotos = new ArrayList<>();

        addPathsToSelections(selectedPaths);
    }

    private void addPathsToSelections(List<String> selectedPaths) {
        if (selectedPaths == null)
            return;

        for (int index = 0; index < items.size(); index++) {
            for (int jindex = 0; jindex < selectedPaths.size(); jindex++) {
                if (items.get(index).getPath().equals(selectedPaths.get(jindex))) {
                    selectedPhotos.add(items.get(index));
                    PickerManager.getInstance().add(items.get(index));
                }
            }
        }
    }


    /**
     * Indicates if the item at position where is selected
     *
     * @param photo Photo of the item to check
     * @return true if the item is selected, false otherwise
     */
    @Override
    public boolean isSelected(T photo) {
        return selectedPhotos.contains(photo);
    }


    /**
     * Toggle the selection status of the item at a given position
     *
     * @param photo Photo of the item to toggle the selection status for
     */
    @Override
    public void toggleSelection(T photo) {
        if (selectedPhotos.contains(photo)) {
            selectedPhotos.remove(photo);
        } else {
            selectedPhotos.add(photo);
        }
    }


    /**
     * Clear the selection status for all items
     */
    @Override
    public void clearSelection() {
        selectedPhotos.clear();
    }

    @Override
    public int getSelectedItemCount() {
        return selectedPhotos.size();
    }

    /**
     * Sets data.
     *
     * @param items the items
     */
    public void setData(List<T> items) {
        this.items = items;
    }

    /**
     * Gets items.
     *
     * @return the items
     */
    public List<T> getItems() {
        return items;
    }

}