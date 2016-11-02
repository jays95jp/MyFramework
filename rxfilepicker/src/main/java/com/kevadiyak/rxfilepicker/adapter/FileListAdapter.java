package com.kevadiyak.rxfilepicker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kevadiyak.rxfilepicker.R;
import com.kevadiyak.rxfilepicker.PickerManager;
import com.kevadiyak.rxfilepicker.model.Document;
import com.kevadiyak.rxfilepicker.views.SmoothCheckBox;

import java.util.List;

/**
 * The type File list adapter.
 */
public class FileListAdapter extends SelectableAdapter<FileListAdapter.FileViewHolder, Document> {

    private final Context context;

    /**
     * Instantiates a new File list adapter.
     *
     * @param context       the context
     * @param items         the items
     * @param selectedPaths the selected paths
     */
    public FileListAdapter(Context context, List<Document> items, List<String> selectedPaths) {
        super(items, selectedPaths);
        this.context = context;
    }

    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_file, parent, false);

        return new FileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FileViewHolder holder, int position) {
        final Document document = getItems().get(position);

        holder.imageView.setImageResource(document.getTypeDrawable());
        holder.fileNameTextView.setText(document.getTitle());
        holder.fileSizeTextView.setText(Formatter.formatShortFileSize(context, Long.parseLong(document.getSize())));

        holder.itemView.setOnClickListener(v -> {
            if (holder.checkBox.isChecked() || PickerManager.getInstance().shouldAdd()) {
                holder.checkBox.setChecked(!holder.checkBox.isChecked(), true);
            }
        });

        //in some cases, it will prevent unwanted situations
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setOnClickListener(view -> {
            if (holder.checkBox.isChecked() || PickerManager.getInstance().shouldAdd()) {
                holder.checkBox.setChecked(!holder.checkBox.isChecked(), true);
            }
        });

        //if true, your checkbox will be selected, else unselected
        holder.checkBox.setChecked(isSelected(document));

        holder.itemView.setBackgroundResource(isSelected(document) ? R.color.bg_gray : android.R.color.white);

        holder.checkBox.setOnCheckedChangeListener((checkBox, isChecked) -> {
            toggleSelection(document);
            holder.itemView.setBackgroundResource(isChecked ? R.color.bg_gray : android.R.color.white);

            if (isChecked)
                PickerManager.getInstance().add(document);
            else
                PickerManager.getInstance().remove(document);
        });
    }

    @Override
    public int getItemCount() {
        return getItems().size();
    }

    /**
     * The type File view holder.
     */
    public static class FileViewHolder extends RecyclerView.ViewHolder {
        /**
         * The Check box.
         */
        SmoothCheckBox checkBox;
        /**
         * The Image view.
         */
        ImageView imageView;
        /**
         * The File name text view.
         */
        TextView fileNameTextView;
        /**
         * The File size text view.
         */
        TextView fileSizeTextView;

        /**
         * Instantiates a new File view holder.
         *
         * @param itemView the item view
         */
        public FileViewHolder(View itemView) {
            super(itemView);
            checkBox = (SmoothCheckBox) itemView.findViewById(R.id.checkbox);
            imageView = (ImageView) itemView.findViewById(R.id.file_iv);
            fileNameTextView = (TextView) itemView.findViewById(R.id.file_name_tv);
            fileSizeTextView = (TextView) itemView.findViewById(R.id.file_size_tv);
        }
    }
}
