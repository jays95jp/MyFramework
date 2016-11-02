package com.kevadiyak.recycleadapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableList;
import android.databinding.OnRebindCallback;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.kevadiyak.recycleadapter.swipmenu.Extension;
import com.kevadiyak.recycleadapter.swipmenu.ItemTouchHelperCallback;
import com.kevadiyak.recycleadapter.swipmenu.ItemTouchHelperExtension;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Func1;

public class CustomBindAdapter extends RecyclerView.Adapter<CustomBindAdapter.ViewHolder> {
    private int variable;
    private List<Object> list;
    private Map<Class<?>, Integer> map;
    private LayoutHandler layoutHandler;
    private OnBindListener onBindListener;
    private OnClickListener onClickListener;
    private OnLongClickListener onLongClickListener;

    private int[] onClickResId;
    private int[] onLongClickResId;
    private Object DATA_INVALIDATION;
    private RecyclerView recyclerView;
    private LayoutInflater inflater;

    private WeakReferenceOnListChangedCallback onChange;
    private ItemTouchHelperExtension helper;
    private int contentResId, startContentResId, endContentResId, dragContentResId;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup view, int type) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, type, view, false);
        ViewHolder holder = new ViewHolder(binding);
        addOnRebindCallback(binding, recyclerView, holder.getAdapterPosition());
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindTo(list.get(position));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        if (isForDataBinding(payloads))
            holder.getBinding().executePendingBindings();
        else
            onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return layoutHandler != null ? layoutHandler.getItemLayout(list.get(position), position) :
                map.get(list.get(position).getClass());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onAttachedToRecyclerView(RecyclerView rv) {
        if (recyclerView == null && list instanceof ObservableList)
            ((ObservableList) list).addOnListChangedCallback(onChange);
        recyclerView = rv;
        inflater = LayoutInflater.from(rv.getContext());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onDetachedFromRecyclerView(RecyclerView rv) {
        if (recyclerView != null && list instanceof ObservableList)
            ((ObservableList) list).removeOnListChangedCallback(onChange);
        recyclerView = null;
    }

    private void addOnRebindCallback(ViewDataBinding b, final RecyclerView rv, final int pos) {
        b.addOnRebindCallback(new OnRebindCallback() {
            @Override
            public boolean onPreBind(ViewDataBinding binding) {
                return rv != null && rv.isComputingLayout();
            }

            @Override
            public void onCanceled(ViewDataBinding binding) {
                if (rv == null || rv.isComputingLayout())
                    return;
                if (pos != RecyclerView.NO_POSITION)
                    notifyItemChanged(pos, DATA_INVALIDATION);
            }
        });
    }

    private boolean isForDataBinding(List<Object> payloads) {
        if (payloads == null || payloads.size() == 0)
            return false;
        for (Object it : payloads) {
            if (it == DATA_INVALIDATION)
                return false;
        }
        return true;
    }

    public void move(int from, int to) {
        Collections.swap(list, from, to);
        //notifyItemMoved(from, to);
    }

    private CustomBindAdapter(List<Object> list, int variable, Map<Class<?>, Integer> map,
                              LayoutHandler layoutHandler, OnBindListener onBindListener,
                              OnClickListener onClickListener, int[] onClickResId,
                              OnLongClickListener onLongClickListener, int[] onLongClickResId,
                              ItemTouchHelperExtension helper,
                              int contentResId, int startContentResId, int endContentResId,
                              int dragContentResId) {
        this.list = list;
        this.map = map;

        this.variable = variable;
        this.onClickResId = onClickResId;
        this.onLongClickResId = onLongClickResId;

        this.layoutHandler = layoutHandler;
        this.onBindListener = onBindListener;
        this.onClickListener = onClickListener;
        this.onLongClickListener = onLongClickListener;

        this.helper = helper;
        this.contentResId = contentResId;
        this.startContentResId = startContentResId;
        this.endContentResId = endContentResId;
        this.dragContentResId = dragContentResId;

        DATA_INVALIDATION = new Object();
        onChange = new WeakReferenceOnListChangedCallback(this);
    }

    public static Builder with(List<Object> list, int variable) {
        return (new Builder(list, variable));
    }

    public static class Builder {
        private int variable;
        private List<Object> list;
        private int[] onClickResIds;
        private int[] onLongClickResIds;

        private Map<Class<?>, Integer> map;
        private LayoutHandler handler = null;
        private OnBindListener onBind = null;
        private OnClickListener onClick = null;
        private OnLoadMoreListener onLoadMore = null;
        private OnLongClickListener onLongClick = null;

        private int swipDirection, dragDirection;
        private int contentResId, startContentResId, endContentResId, dragContentResId;
        private ItemTouchHelperExtension helper;

        public Builder map(Class<?> clazz, int layout) {
            map.put(clazz, layout);
            return this;
        }

        public <T> Builder map(int layout) {
            return map(Object.class, layout);
        }

        public Builder layoutHandler(LayoutHandler layoutHandler) {
            handler = layoutHandler;
            return this;
        }

        public Builder layout(Func1<? super ItemPosition, Integer> func1) {
            return layoutHandler((item, position) -> func1.call(new ItemPosition(item, position)).intValue());
        }

        public Builder onBindListener(OnBindListener listener) {
            onBind = listener;
            return this;
        }

        public Builder onBind(Func1<? super ItemViewTypePosition, Void> func1) {
            return onBindListener((item, view, type, position) -> func1.call(new ItemViewTypePosition(item, view, type, position)));
        }

        public Builder onClickListener(OnClickListener listener, int... resIds) {
            onClick = listener;
            onClickResIds = resIds;
            return this;
        }

        public Builder onClick(Func1<? super ItemViewTypePosition, Void> func1, int... resIds) {
            return onClickListener((item, view, type, position) -> func1.call(new ItemViewTypePosition(item, view, type, position)), resIds);
        }

        public Builder onLongClickListener(OnLongClickListener listener, int... resIds) {
            onLongClick = listener;
            onLongClickResIds = resIds;
            return this;
        }

        public Builder onLongClick(Func1<? super ItemViewTypePosition, Void> func1, int... resIds) {
            return onLongClickListener((item, view, type, position) -> func1.call(new ItemViewTypePosition(item, view, type, position)), resIds);
        }

        public Builder onLoadMoreListener(OnLoadMoreListener listener) {
            onLoadMore = listener;
            return this;
        }

        public Builder onLoadMore(Func1<? super Integer, Boolean> func1) {
            return onLoadMoreListener(size -> func1.call(size));
        }

        public Builder onSwipMenuListener(int contentViewResId, int startViewResId, int endViewResId) {
            contentResId = contentViewResId;
            startContentResId = startViewResId;
            endContentResId = endViewResId;
            if(startViewResId != 0 && endViewResId != 0)
                swipDirection = ItemTouchHelper.START | ItemTouchHelper.END;
            else if(startViewResId != 0)
                swipDirection = ItemTouchHelper.END;
            else if(endViewResId != 0)
                swipDirection = ItemTouchHelper.START;
            else
                swipDirection = 0;
            return this;
        }

        /*public Builder ondragListener(int dragViewResId) {
            dragDirection = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            dragContentResId = dragViewResId;
            return this;
        }*/

        public CustomBindAdapter into(RecyclerView recyclerView, RecyclerView.LayoutManager... layoutManager) {
            helper = new ItemTouchHelperExtension(new ItemTouchHelperCallback(dragDirection, swipDirection));
            CustomBindAdapter adapter = build();
            if(layoutManager != null && layoutManager.length > 0) {
                recyclerView.setHasFixedSize(false);
                layoutManager[0].setAutoMeasureEnabled(true);
                recyclerView.setLayoutManager(layoutManager[0]);
            }
            recyclerView.setAdapter(adapter);

            if((swipDirection != 0 || dragDirection != 0) && contentResId != 0)
                helper.attachToRecyclerView(recyclerView);
            if(onLoadMore != null)
                setScrolling(recyclerView);

            return adapter;
        }

        public void setScrolling(RecyclerView recyclerView) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                private boolean loading = true;
                private int previousTotal = 0;

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if(onLoadMore != null) {
                        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                            if(manager.getOrientation() == LinearLayoutManager.VERTICAL) {
                                if (!recyclerView.canScrollVertically(1)) {
                                    int totalItemCount = manager.getItemCount();
                                    int lastVisibleItem = manager.findLastVisibleItemPosition();

                                    if (!loading && totalItemCount <= (lastVisibleItem + 2))
                                        loading = onLoadMore.onLoadMore(totalItemCount);
                                }
                            } else {
                                int visibleItemCount = recyclerView.getChildCount();
                                int totalItemCount = manager.getItemCount();
                                int firstVisibleItem = manager.findFirstVisibleItemPosition();

                                if (loading) {
                                    if (totalItemCount > previousTotal) {
                                        loading = false;
                                        previousTotal = totalItemCount;
                                    }
                                }

                                if (!loading && (totalItemCount - visibleItemCount) <= firstVisibleItem) {
                                    loading = onLoadMore.onLoadMore(totalItemCount);
                                }
                            }
                        } else if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                            GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
                            int visibleItemCount = recyclerView.getChildCount();
                            int totalItemCount = manager.getItemCount();
                            int firstVisibleItem = manager.findFirstVisibleItemPosition();

                            if (loading) {
                                if (totalItemCount > previousTotal) {
                                    loading = false;
                                    previousTotal = totalItemCount;
                                }
                            }

                            if (!loading && (totalItemCount - visibleItemCount) <= firstVisibleItem) {
                                loading = onLoadMore.onLoadMore(totalItemCount);
                            }
                        }
                    }
                }
            });
        }

        public CustomBindAdapter build() {
            return new CustomBindAdapter(list, variable, map, handler, onBind,
                    onClick, onClickResIds,
                    onLongClick, onLongClickResIds,
                    helper,
                    contentResId, startContentResId, endContentResId,
                    dragContentResId);
        }

        public Builder(List<Object> list, int variable) {
            super();
            this.list = list;
            this.variable = variable;
            map = new HashMap<>();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements Extension {
        private ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void bindTo(Object item) {
            binding.setVariable(variable, item);
            binding.executePendingBindings();

            if (onClickListener != null) {
                if(onClickResId != null && onClickResId.length > 0) {
                    for (int ids : onClickResId) {
                        if(itemView.findViewById(ids) != null) {
                            itemView.findViewById(ids).setOnClickListener(v -> onClickListener.onClick(item, v, getItemViewType(), getAdapterPosition()));
                        }
                    }
                } else {
                    itemView.setOnClickListener(v -> onClickListener.onClick(item, itemView, getItemViewType(), getAdapterPosition()));
                }
            }

            if (onLongClickListener != null) {
                if(onLongClickResId != null && onLongClickResId.length > 0) {
                    for(int ids : onLongClickResId) {
                        if(itemView.findViewById(ids) != null) {
                            itemView.findViewById(ids).setOnLongClickListener(v -> {
                                onLongClickListener.onLongClick(item, itemView, getItemViewType(), getAdapterPosition());
                                return true;
                            });
                        }
                    }
                } else {
                    itemView.setOnLongClickListener(v -> {
                        onLongClickListener.onLongClick(item, itemView, getItemViewType(), getAdapterPosition());
                        return true;
                    });
                }
            }

            if(dragContentResId != 0 && itemView.findViewById(dragContentResId) != null) {
                itemView.findViewById(dragContentResId).setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                            helper.startDrag(ViewHolder.this);
                        }
                        return true;
                    }
                });
            }

            if(onBindListener != null) {
                onBindListener.onBind(item, itemView, getItemViewType(), getAdapterPosition());
            }
        }

        public View getContentView() {
            if(contentResId != 0 && binding.getRoot().findViewById(contentResId) != null)
                return binding.getRoot().findViewById(contentResId);
            else
                return null;
        }

        public View getStartContentView() {
            if(endContentResId != 0 && binding.getRoot().findViewById(endContentResId) != null)
                return binding.getRoot().findViewById(endContentResId);
            else
                return null;
        }

        public View getEndContentView() {
            if(startContentResId != 0 && binding.getRoot().findViewById(startContentResId) != null)
                return binding.getRoot().findViewById(startContentResId);
            else
                return null;
        }

        @Override
        public float getStartContentWidth() {
            if(endContentResId != 0 && binding.getRoot().findViewById(endContentResId) != null) {
                //if(startContentResId != 0 && binding.getRoot().findViewById(startContentResId) != null)
                //    binding.getRoot().findViewById(startContentResId).setVisibility(View.INVISIBLE);
                binding.getRoot().findViewById(endContentResId).setVisibility(View.VISIBLE);
                return binding.getRoot().findViewById(endContentResId).getMeasuredWidth();
            } else
                return 0;
        }

        @Override
        public float getEndContentWidth() {
            if(startContentResId != 0 && binding.getRoot().findViewById(startContentResId) != null) {
                //if(endContentResId != 0 && binding.getRoot().findViewById(endContentResId) != null)
                //    binding.getRoot().findViewById(endContentResId).setVisibility(View.INVISIBLE);
                binding.getRoot().findViewById(startContentResId).setVisibility(View.VISIBLE);
                return binding.getRoot().findViewById(startContentResId).getMeasuredWidth();
            } else
                return 0;
        }
    }

    public interface LayoutHandler {
        int getItemLayout(Object item, int position);
    }

    public interface OnBindListener {
        void onBind(Object item, View view, int type, int position);
    }

    public interface OnClickListener {
        void onClick(Object item, View view, int type, int position);
    }

    public interface OnLongClickListener {
        void onLongClick(Object item, View view, int type, int position);
    }

    public interface OnLoadMoreListener {
        boolean onLoadMore(int size);
    }

    public static class ItemPosition {
        private Object item;
        private int position;

        public ItemPosition(Object item, int position) {
            this.item = item;
            this.position = position;
        }

        public Object getItem() {
            return item;
        }

        public int getPosition() {
            return position;
        }
    }

    public static class ItemViewTypePosition {
        private Object item;
        private View view;
        private int type;
        private int position;

        public ItemViewTypePosition(Object item, View view, int type, int position) {
            this.item = item;
            this.view = view;
            this.type = type;
            this.position = position;
        }

        public Object getItem() {
            return item;
        }

        public View getView() {
            return view;
        }

        public int getType() {
            return type;
        }

        public int getPosition() {
            return position;
        }
    }
}
