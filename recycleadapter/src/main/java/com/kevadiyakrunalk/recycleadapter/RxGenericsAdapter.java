package com.kevadiyakrunalk.recycleadapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.kevadiyakrunalk.recycleadapter.animator.GeneralItemAnimator;
import com.kevadiyakrunalk.recycleadapter.animator.SwipeDismissItemAnimator;
import com.kevadiyakrunalk.recycleadapter.draggable.DraggableItemConstants;
import com.kevadiyakrunalk.recycleadapter.draggable.ItemDraggableRange;
import com.kevadiyakrunalk.recycleadapter.draggable.RecyclerViewDragDropManager;
import com.kevadiyakrunalk.recycleadapter.expandable.ExpandableDraggableItemAdapter;
import com.kevadiyakrunalk.recycleadapter.expandable.ExpandableItemConstants;
import com.kevadiyakrunalk.recycleadapter.expandable.ExpandableItemViewHolder;
import com.kevadiyakrunalk.recycleadapter.expandable.ExpandableSwipeableItemAdapter;
import com.kevadiyakrunalk.recycleadapter.expandable.RecyclerViewExpandableItemManager;
import com.kevadiyakrunalk.recycleadapter.swipeable.RecyclerViewSwipeManager;
import com.kevadiyakrunalk.recycleadapter.swipeable.SwipeableItemConstants;
import com.kevadiyakrunalk.recycleadapter.swipeable.action.SwipeResultAction;
import com.kevadiyakrunalk.recycleadapter.swipeable.action.SwipeResultActionDefault;
import com.kevadiyakrunalk.recycleadapter.swipeable.action.SwipeResultActionMoveToSwipedDirection;
import com.kevadiyakrunalk.recycleadapter.swipeable.action.SwipeResultActionRemoveItem;
import com.kevadiyakrunalk.recycleadapter.touchguard.RecyclerViewTouchActionGuardManager;
import com.kevadiyakrunalk.recycleadapter.utility.ChildData;
import com.kevadiyakrunalk.recycleadapter.utility.DataUtils;
import com.kevadiyakrunalk.recycleadapter.utility.DrawableUtils;
import com.kevadiyakrunalk.recycleadapter.utility.GroupData;
import com.kevadiyakrunalk.recycleadapter.utility.ViewUtils;
import com.kevadiyakrunalk.recycleadapter.utils.AbstractDraggableSwipeableItemViewHolder;
import com.kevadiyakrunalk.recycleadapter.utils.AbstractExpandableItemAdapter;
import com.kevadiyakrunalk.recycleadapter.widget.ExpandableItemIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.functions.Func1;
import rx.subjects.PublishSubject;

/**
 * Created by KevadiyaKrunalK on 07-01-2017.
 */
public class RxGenericsAdapter<DataType>
        extends AbstractExpandableItemAdapter<RxGenericsAdapter.MyGroupViewHolder, RxGenericsAdapter.MyChildViewHolder>
        implements ExpandableDraggableItemAdapter<RxGenericsAdapter.MyGroupViewHolder, RxGenericsAdapter.MyChildViewHolder>,
        ExpandableSwipeableItemAdapter<RxGenericsAdapter.MyGroupViewHolder, RxGenericsAdapter.MyChildViewHolder>
{
    private static final String SAVED_STATE_EXPANDABLE_ITEM_MANAGER = "RecyclerViewExpandableItemManager";
    private Integer variable;
    private List<DataType> mProvider;

    private Map<Class<?>, Object> map;
    private PublishSubject<RxGenericsAdapter.MyBaseViewHolder> mPublishSubject;
    private Float intMaxLeftSwipeAmount, intMaxRightSwipeAmount;
    private Integer contentResId, expandIndicatorResId, dragHandleResId;

    private LayoutHandler layoutHandler;
    private OnClickListener onClickListener;
    private OnLongClickListener onLongClickListener;

    private Integer[] onClickResId;
    private Integer[] onLongClickResId;
    private Object DATA_INVALIDATION;
    private LayoutInflater inflater;
    private RecyclerViewExpandableItemManager mExpandableItemManager;

    public RxGenericsAdapter(List<DataType> list, Integer variable, Map<Class<?>, Object> map,
                             LayoutHandler layoutHandler, Float intMaxLeftSwipeAmount, Float intMaxRightSwipeAmount,
                             RecyclerViewExpandableItemManager mExpandableItemManager,
                             OnClickListener onClickListener, Integer[] onClickResId,
                             OnLongClickListener onLongClickListener, Integer[] onLongClickResId,
                             Integer contentResId, Integer expandIndicatorResId, Integer dragHandleResId) {
        this.mProvider = list;
        this.map = map;

        this.intMaxLeftSwipeAmount = intMaxLeftSwipeAmount;
        this.intMaxRightSwipeAmount = intMaxRightSwipeAmount;
        this.mExpandableItemManager = mExpandableItemManager;

        this.variable = variable;
        this.onClickResId = onClickResId;
        this.onLongClickResId = onLongClickResId;

        this.layoutHandler = layoutHandler;
        this.onClickListener = onClickListener;
        this.onLongClickListener = onLongClickListener;

        this.contentResId = contentResId;
        this.dragHandleResId = dragHandleResId;
        this.expandIndicatorResId = expandIndicatorResId;

        DATA_INVALIDATION = new Object();
        mPublishSubject = PublishSubject.create();

        setHasStableIds(true);
    }

    @Override
    public int getGroupCount() {
        return mProvider.size();
    }

    @Override
    public int getChildCount(int groupPosition) {
        if(mProvider.get(groupPosition) instanceof Pair) {
            if(((Pair) mProvider.get(groupPosition)).second instanceof List)
                return ((List) ((Pair) mProvider.get(groupPosition)).second).size();
            else if(((Pair) mProvider.get(groupPosition)).second instanceof ArrayList)
                return ((ArrayList) ((Pair) mProvider.get(groupPosition)).second).size();
            else
                return 0;
        } else
            return 0;
    }

    @Override
    public long getGroupId(int groupPosition) {
        if(mProvider.get(groupPosition) instanceof Pair)
            return ((GroupData) ((Pair) mProvider.get(groupPosition)).first).getGroupId();
        else
            return ((GroupData) mProvider.get(groupPosition)).getGroupId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        if(mProvider.get(groupPosition) instanceof Pair) {
            if(((Pair) mProvider.get(groupPosition)).second instanceof List)
                return ((ChildData) ((List) ((Pair) mProvider.get(groupPosition)).second).get(childPosition)).getChildId();
            else if(((Pair) mProvider.get(groupPosition)).second instanceof ArrayList)
                return ((ChildData) ((ArrayList) ((Pair) mProvider.get(groupPosition)).second).get(childPosition)).getChildId();
            else
                return 0;
        } else
            return ((ChildData) mProvider.get(groupPosition)).getChildId();
    }

    @Override
    public int getGroupItemViewType(int groupPosition) {
        if(layoutHandler != null) {
            if(mProvider.get(groupPosition) instanceof Pair)
                return layoutHandler.getItemLayout(new ItemPosition(((Pair) mProvider.get(groupPosition)).first, groupPosition, -1));
            else
                return layoutHandler.getItemLayout(new ItemPosition(mProvider.get(groupPosition), groupPosition, -1));
        } else {
            if(mProvider.get(groupPosition) instanceof Pair)
                return ((int) map.get(((Pair) mProvider.get(groupPosition)).first.getClass()));
            else
                return ((int) map.get(mProvider.get(groupPosition).getClass()));
        }
    }

    @Override
    public int getChildItemViewType(int groupPosition, int childPosition) {
        if(layoutHandler != null) {
            if(mProvider.get(groupPosition) instanceof Pair) {
                if(((Pair) mProvider.get(groupPosition)).second instanceof List)
                    return layoutHandler.getItemLayout(new ItemPosition(((List) ((Pair) mProvider.get(groupPosition)).second).get(childPosition), groupPosition, childPosition));
                else if(((Pair) mProvider.get(groupPosition)).second instanceof ArrayList)
                    return layoutHandler.getItemLayout(new ItemPosition(((ArrayList) ((Pair) mProvider.get(groupPosition)).second).get(childPosition), groupPosition, childPosition));
                else
                    return 0;
            } else
                return layoutHandler.getItemLayout(new ItemPosition(mProvider.get(groupPosition), groupPosition, childPosition));
        } else {
            if(mProvider.get(groupPosition) instanceof Pair) {
                if(((Pair) mProvider.get(groupPosition)).second instanceof List)
                    return ((int) map.get(((List) ((Pair) mProvider.get(groupPosition)).second).get(childPosition).getClass()));
                else if(((Pair) mProvider.get(groupPosition)).second instanceof ArrayList)
                    return ((int) map.get(((ArrayList) ((Pair) mProvider.get(groupPosition)).second).get(childPosition).getClass()));
                else
                    return 0;
            } else
                return ((int) map.get(mProvider.get(groupPosition).getClass()));
        }
    }

    @Override
    public RxGenericsAdapter.MyGroupViewHolder onCreateGroupViewHolder(ViewGroup view, int type) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, type, view, false);
        return new MyGroupViewHolder(binding);
    }

    @Override
    public RxGenericsAdapter.MyChildViewHolder onCreateChildViewHolder(ViewGroup view, int type) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, type, view, false);
        return new MyChildViewHolder(binding);
    }

    @Override
    public void onBindGroupViewHolder(RxGenericsAdapter.MyGroupViewHolder holder, int groupPosition, int viewType) {
        if(mProvider.get(groupPosition) instanceof Pair)
            holder.bindTo(groupPosition, ((Pair) mProvider.get(groupPosition)).first);
        else
            holder.bindTo(groupPosition, mProvider.get(groupPosition));
        mPublishSubject.onNext(holder);
    }

    @Override
    public void onBindChildViewHolder(RxGenericsAdapter.MyChildViewHolder holder, int groupPosition, int childPosition, int viewType) {
        if(mProvider.get(groupPosition) instanceof Pair) {
            if(((Pair) mProvider.get(groupPosition)).second instanceof List)
                holder.bindTo(groupPosition, childPosition, ((List) ((Pair) mProvider.get(groupPosition)).second).get(childPosition));
            else if(((Pair) mProvider.get(groupPosition)).second instanceof ArrayList)
                holder.bindTo(groupPosition, childPosition, ((ArrayList) ((Pair) mProvider.get(groupPosition)).second).get(childPosition));
        } else
            holder.bindTo(groupPosition, childPosition,mProvider.get(groupPosition));
        mPublishSubject.onNext(holder);
    }

    @Override
    public void onBindGroupViewHolder(RxGenericsAdapter.MyGroupViewHolder holder, int groupPosition, int viewType, List<Object> payloads) {
        if (isForDataBinding(payloads))
            holder.getBinding().executePendingBindings();
        else
            onBindGroupViewHolder(holder, groupPosition, viewType);
    }

    @Override
    public void onBindChildViewHolder(RxGenericsAdapter.MyChildViewHolder holder, int groupPosition, int childPosition, int viewType, List<Object> payloads) {
        if (isForDataBinding(payloads))
            holder.getBinding().executePendingBindings();
        else
            onBindChildViewHolder(holder, groupPosition, childPosition, viewType);
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

    public void onAttachedToRecyclerView(RecyclerView rv) {
        //recyclerView = rv;
        inflater = LayoutInflater.from(rv.getContext());
    }

    public void onDetachedFromRecyclerView(RecyclerView rv) {
        //recyclerView = null;
    }

    @Override
    public boolean onCheckCanExpandOrCollapseGroup(RxGenericsAdapter.MyGroupViewHolder holder, int groupPosition, int x, int y, boolean expand) {
        // check the item is *not* pinned
        if(mProvider.get(groupPosition) instanceof Pair) {
            if(((Pair) mProvider.get(groupPosition)).first instanceof GroupData) {
                if (((GroupData) ((Pair) mProvider.get(groupPosition)).first).isPinned() != DataUtils.SWAP_NONE) {
                    // return false to raise View.OnClickListener#onClick() event
                    return false;
                }
            }
        } else {
            if (((GroupData) mProvider.get(groupPosition)).isPinned() != DataUtils.SWAP_NONE) {
                // return false to raise View.OnClickListener#onClick() event
                return false;
            }
        }

        // check is enabled
        if (!(holder.itemView.isEnabled() && holder.itemView.isClickable())) {
            return false;
        }

        final View containerView = holder.getContainerView();
        final View dragHandleView = holder.getDragHandleView();

        int offsetX = 0;
        int offsetY = 0;

        if(containerView != null) {
            offsetX = containerView.getLeft() + (int) (ViewCompat.getTranslationX(containerView) + 0.5f);
            offsetY = containerView.getTop() + (int) (ViewCompat.getTranslationY(containerView) + 0.5f);
        }

        if(dragHandleView != null)
            return !ViewUtils.hitTest(dragHandleView, x - offsetX, y - offsetY);
        else
            return false;
    }

    @Override
    public boolean onCheckGroupCanStartDrag(RxGenericsAdapter.MyGroupViewHolder holder, int groupPosition, int x, int y) {
        // x, y --- relative from the itemView's top-left
        final View containerView = holder.getContainerView();
        final View dragHandleView = holder.getDragHandleView();

        int offsetX = 0;
        int offsetY = 0;

        if(containerView != null) {
            offsetX = containerView.getLeft() + (int) (ViewCompat.getTranslationX(containerView) + 0.5f);
            offsetY = containerView.getTop() + (int) (ViewCompat.getTranslationY(containerView) + 0.5f);
        }

        if(dragHandleView != null)
            return ViewUtils.hitTest(dragHandleView, x - offsetX, y - offsetY);
        else
            return false;
    }

    @Override
    public boolean onCheckChildCanStartDrag(RxGenericsAdapter.MyChildViewHolder holder, int groupPosition, int childPosition, int x, int y) {
        // x, y --- relative from the itemView's top-left
        final View containerView = holder.getContainerView();
        final View dragHandleView = holder.getDragHandleView();

        int offsetX = 0;
        int offsetY = 0;

        if(containerView != null) {
            offsetX = containerView.getLeft() + (int) (ViewCompat.getTranslationX(containerView) + 0.5f);
            offsetY = containerView.getTop() + (int) (ViewCompat.getTranslationY(containerView) + 0.5f);
        }

        if(dragHandleView != null)
            return ViewUtils.hitTest(dragHandleView, x - offsetX, y - offsetY);
        else
            return false;
    }

    @Override
    public ItemDraggableRange onGetGroupItemDraggableRange(RxGenericsAdapter.MyGroupViewHolder holder, int groupPosition) {
        // no drag-sortable range specified
        return null;
    }

    @Override
    public ItemDraggableRange onGetChildItemDraggableRange(RxGenericsAdapter.MyChildViewHolder holder, int groupPosition, int childPosition) {
        // no drag-sortable range specified
        return null;
    }

    @Override
    public boolean onCheckGroupCanDrop(int draggingGroupPosition, int dropGroupPosition) {
        return true;
    }

    @Override
    public boolean onCheckChildCanDrop(int draggingGroupPosition, int draggingChildPosition, int dropGroupPosition, int dropChildPosition) {
        return true;
    }

    @Override
    public void onMoveGroupItem(int fromGroupPosition, int toGroupPosition) {
        if (fromGroupPosition == toGroupPosition) {
            return;
        }

        final DataType item = mProvider.remove(fromGroupPosition);
        mProvider.add(toGroupPosition, item);
    }

    @Override
    public void onMoveChildItem(int fromGroupPosition, int fromChildPosition, int toGroupPosition, int toChildPosition) {
        if ((fromGroupPosition == toGroupPosition) && (fromChildPosition == toChildPosition)) {
            return;
        }

        final DataType fromGroup = mProvider.get(fromGroupPosition);
        final DataType toGroup = mProvider.get(toGroupPosition);

        if(fromGroup instanceof Pair && toGroup instanceof Pair) {
            if(((Pair) fromGroup).second instanceof  List && ((Pair) toGroup).second instanceof  List) {
                final Object item = ((List) ((Pair) fromGroup).second).remove(fromChildPosition);
                //noinspection unchecked
                ((List) ((Pair) toGroup).second).add(toChildPosition, item);
            } else if(((Pair) fromGroup).second instanceof ArrayList && ((Pair) toGroup).second instanceof  ArrayList) {
                final Object item = ((ArrayList) ((Pair) fromGroup).second).remove(fromChildPosition);
                //noinspection unchecked
                ((ArrayList) ((Pair) toGroup).second).add(toChildPosition, item);
            }
        } else {
            final DataType item = mProvider.remove(fromChildPosition);
            mProvider.add(toChildPosition, item);
        }
    }

    @Override
    public int onGetGroupItemSwipeReactionType(RxGenericsAdapter.MyGroupViewHolder holder, int groupPosition, int x, int y) {
        if (onCheckGroupCanStartDrag(holder, groupPosition, x, y)) {
            return Swipeable.REACTION_CAN_NOT_SWIPE_BOTH_H;
        }

        return Swipeable.REACTION_CAN_SWIPE_BOTH_H;
    }

    @Override
    public int onGetChildItemSwipeReactionType(RxGenericsAdapter.MyChildViewHolder holder, int groupPosition, int childPosition, int x, int y) {
        if (onCheckChildCanStartDrag(holder, groupPosition, childPosition, x, y)) {
            return Swipeable.REACTION_CAN_NOT_SWIPE_BOTH_H;
        }

        return Swipeable.REACTION_CAN_SWIPE_BOTH_H;
    }

    @Override
    public void onSetGroupItemSwipeBackground(RxGenericsAdapter.MyGroupViewHolder holder, int groupPosition, int type) {

    }

    @Override
    public void onSetChildItemSwipeBackground(RxGenericsAdapter.MyChildViewHolder holder, int groupPosition, int childPosition, int type) {

    }

    @Override
    public SwipeResultAction onSwipeGroupItem(RxGenericsAdapter.MyGroupViewHolder holder, int groupPosition, int result) {
        switch (result) {
            // swipe right
            case Swipeable.RESULT_SWIPED_RIGHT:
                if(mProvider.get(groupPosition) instanceof Pair) {
                    if(((Pair) mProvider.get(groupPosition)).first instanceof GroupData) {
                        if(((GroupData) ((Pair) mProvider.get(groupPosition)).first).isPinned() == DataUtils.SWAP_LEFT) {
                            // pinned --- back to default position
                            return new GroupUnpinResultAction(this, groupPosition);
                        } else {
                            // not pinned --- remove
                            return new GroupSwipeRightResultAction(this, groupPosition);
                        }
                    }
                } else {
                    if(mProvider.get(groupPosition) instanceof GroupData) {
                        if(((GroupData) mProvider.get(groupPosition)).isPinned() == DataUtils.SWAP_LEFT) {
                            // pinned --- back to default position
                            return new GroupUnpinResultAction(this, groupPosition);
                        } else {
                            // not pinned --- remove
                            return new GroupSwipeRightResultAction(this, groupPosition);
                        }
                    }
                }
                // swipe left -- pin
            case Swipeable.RESULT_SWIPED_LEFT:
                if(mProvider.get(groupPosition) instanceof Pair) {
                    if(((Pair) mProvider.get(groupPosition)).first instanceof GroupData) {
                        if(((GroupData) ((Pair) mProvider.get(groupPosition)).first).isPinned() == DataUtils.SWAP_RIGHT) {
                            // pinned --- back to default position
                            return new GroupUnpinResultAction(this, groupPosition);
                        } else {
                            // not pinned --- remove
                            return new GroupSwipeLeftResultAction(this, groupPosition);
                        }
                    }
                } else {
                    if(mProvider.get(groupPosition) instanceof GroupData) {
                        if(((GroupData) mProvider.get(groupPosition)).isPinned() == DataUtils.SWAP_RIGHT) {
                            // pinned --- back to default position
                            return new GroupUnpinResultAction(this, groupPosition);
                        } else {
                            // not pinned --- remove
                            return new GroupSwipeLeftResultAction(this, groupPosition);
                        }
                    }
                }
                // other --- do nothing
            case Swipeable.RESULT_CANCELED:
            default:
                if (groupPosition != RecyclerView.NO_POSITION) {
                    return new GroupUnpinResultAction(this, groupPosition);
                } else {
                    return null;
                }
        }
    }

    @Override
    public SwipeResultAction onSwipeChildItem(RxGenericsAdapter.MyChildViewHolder holder, int groupPosition, int childPosition, int result) {
        switch (result) {
            // swipe right
            case Swipeable.RESULT_SWIPED_RIGHT:
                if(mProvider.get(groupPosition) instanceof Pair) {
                    if(((Pair) mProvider.get(groupPosition)).second instanceof List) {
                        if(((List) ((Pair) mProvider.get(groupPosition)).second).get(childPosition) instanceof ChildData) {
                            if (((ChildData) ((List) ((Pair) mProvider.get(groupPosition)).second).get(childPosition)).isPinned() == DataUtils.SWAP_LEFT) {
                                // pinned --- back to default position
                                return new ChildUnpinResultAction(this, groupPosition, childPosition);
                            } else {
                                // not pinned --- remove
                                return new ChildSwipeRightResultAction(this, groupPosition, childPosition);
                            }
                        }
                    } else if(((Pair) mProvider.get(groupPosition)).second instanceof ArrayList) {
                        if(((ArrayList) ((Pair) mProvider.get(groupPosition)).second).get(childPosition) instanceof ChildData) {
                            if (((ChildData) ((ArrayList) ((Pair) mProvider.get(groupPosition)).second).get(childPosition)).isPinned() == DataUtils.SWAP_LEFT) {
                                // pinned --- back to default position
                                return new ChildUnpinResultAction(this, groupPosition, childPosition);
                            } else {
                                // not pinned --- remove
                                return new ChildSwipeRightResultAction(this, groupPosition, childPosition);
                            }
                        }
                    }
                } else {
                    if(mProvider.get(groupPosition) instanceof ChildData) {
                        if (((ChildData) mProvider.get(groupPosition)).isPinned() == DataUtils.SWAP_LEFT) {
                            // pinned --- back to default position
                            return new ChildUnpinResultAction(this, groupPosition, childPosition);
                        } else {
                            // not pinned --- remove
                            return new ChildSwipeRightResultAction(this, groupPosition, childPosition);
                        }
                    }
                }
                // swipe left -- pin
            case Swipeable.RESULT_SWIPED_LEFT:
                if(mProvider.get(groupPosition) instanceof Pair) {
                    if(((Pair) mProvider.get(groupPosition)).second instanceof List) {
                        if(((List) ((Pair) mProvider.get(groupPosition)).second).get(childPosition) instanceof ChildData) {
                            if (((ChildData) ((List) ((Pair) mProvider.get(groupPosition)).second).get(childPosition)).isPinned() == DataUtils.SWAP_RIGHT) {
                                // pinned --- back to default position
                                return new ChildUnpinResultAction(this, groupPosition, childPosition);
                            } else {
                                // not pinned --- remove
                                return new ChildSwipeLeftResultAction(this, groupPosition, childPosition);
                            }
                        }
                    } else if(((Pair) mProvider.get(groupPosition)).second instanceof ArrayList) {
                        if(((ArrayList) ((Pair) mProvider.get(groupPosition)).second).get(childPosition) instanceof ChildData) {
                            if (((ChildData) ((ArrayList) ((Pair) mProvider.get(groupPosition)).second).get(childPosition)).isPinned() == DataUtils.SWAP_RIGHT) {
                                // pinned --- back to default position
                                return new ChildUnpinResultAction(this, groupPosition, childPosition);
                            } else {
                                // not pinned --- remove
                                return new ChildSwipeLeftResultAction(this, groupPosition, childPosition);
                            }
                        }
                    }
                } else {
                    if(mProvider.get(groupPosition) instanceof ChildData) {
                        if (((ChildData) mProvider.get(groupPosition)).isPinned() == DataUtils.SWAP_RIGHT) {
                            // pinned --- back to default position
                            return new ChildUnpinResultAction(this, groupPosition, childPosition);
                        } else {
                            // not pinned --- remove
                            return new ChildSwipeLeftResultAction(this, groupPosition, childPosition);
                        }
                    }
                }
                // other --- do nothing
            case Swipeable.RESULT_CANCELED:
            default:
                if (groupPosition != RecyclerView.NO_POSITION) {
                    return new ChildUnpinResultAction(this, groupPosition, childPosition);
                } else {
                    return null;
                }
        }
    }

    public abstract class MyBaseViewHolder extends AbstractDraggableSwipeableItemViewHolder implements ExpandableItemViewHolder {
        private ViewDataBinding binding;
        private int mExpandStateFlags;

        public MyBaseViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

        @Override
        public int getExpandStateFlags() {
            return mExpandStateFlags;
        }

        @Override
        public void setExpandStateFlags(int flag) {
            mExpandStateFlags = flag;
        }

        @Override
        public View getSwipeableContainerView() {
            if(contentResId != 0 && binding.getRoot().findViewById(contentResId) != null)
                return binding.getRoot().findViewById(contentResId);
            else
                return null;
        }

        public View getContainerView() {
            if(contentResId != null && contentResId != 0 && binding.getRoot().findViewById(contentResId) != null)
                return binding.getRoot().findViewById(contentResId);
            else
                return null;
        }

        public View getDragHandleView() {
            if(dragHandleResId != null && dragHandleResId != 0 && binding.getRoot().findViewById(dragHandleResId) != null)
                return binding.getRoot().findViewById(dragHandleResId);
            else
                return null;
        }
    }

    public class MyGroupViewHolder extends MyBaseViewHolder {
        private Object item;
        private int groupPosition;

        public MyGroupViewHolder(ViewDataBinding binding) {
            super(binding);
        }

        public Object getItem() {
            return item;
        }

        public void bindTo(int groupPosition, Object item) {
            this.item = item;
            this.groupPosition = groupPosition;
            getBinding().setVariable(variable, item);
            getBinding().executePendingBindings();

            if (onClickListener != null) {
                if(onClickResId != null && onClickResId.length > 0) {
                    for (int ids : onClickResId) {
                        if(itemView.findViewById(ids) != null) {
                            itemView.findViewById(ids).setOnClickListener(v -> onClickListener.onClick(new ItemViewTypePosition(item, v, getItemViewType(), groupPosition, -1)));
                        }
                    }

                    if(expandIndicatorResId != null && expandIndicatorResId != 0) {
                        itemView.findViewById(expandIndicatorResId).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(mExpandableItemManager.isGroupExpanded(groupPosition))
                                    mExpandableItemManager.collapseGroup(groupPosition);
                                else
                                    mExpandableItemManager.expandGroup(groupPosition);
                            }
                        });
                    }
                } else {
                    itemView.setOnClickListener(v -> onClickListener.onClick(new ItemViewTypePosition(item, itemView, getItemViewType(), groupPosition, -1)));
                }
            }

            if (onLongClickListener != null) {
                if(onLongClickResId != null && onLongClickResId.length > 0) {
                    for(int ids : onLongClickResId) {
                        if(itemView.findViewById(ids) != null) {
                            itemView.findViewById(ids).setOnLongClickListener(v -> {
                                onLongClickListener.onLongClick(new ItemViewTypePosition(item, itemView, getItemViewType(), groupPosition, -1));
                                return true;
                            });
                        }
                    }
                } else {
                    itemView.setOnLongClickListener(v -> {
                        onLongClickListener.onLongClick(new ItemViewTypePosition(item, itemView, getItemViewType(), groupPosition, -1));
                        return true;
                    });
                }
            }

            if(onClickListener == null && expandIndicatorResId != null && expandIndicatorResId != 0) {
                itemView.findViewById(expandIndicatorResId).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mExpandableItemManager.isGroupExpanded(groupPosition))
                            mExpandableItemManager.collapseGroup(groupPosition);
                        else
                            mExpandableItemManager.expandGroup(groupPosition);
                    }
                });
            }

            // set background resource (target view ID: container)
            final int dragState = getDragStateFlags();
            final int expandState = getExpandStateFlags();
            final int swipeState = getSwipeStateFlags();

            if (((dragState & Draggable.STATE_FLAG_IS_UPDATED) != 0) ||
                    ((expandState & Expandable.STATE_FLAG_IS_UPDATED) != 0) ||
                    ((swipeState & Swipeable.STATE_FLAG_IS_UPDATED) != 0)) {
                boolean isExpanded;
                boolean animateIndicator = ((expandState & Expandable.STATE_FLAG_HAS_EXPANDED_STATE_CHANGED) != 0);

                if ((dragState & Draggable.STATE_FLAG_IS_ACTIVE) != 0) {
                    // need to clear drawable state here to get correct appearance of the dragging item.
                    if(contentResId != null && contentResId != 0 && itemView.findViewById(contentResId) != null)
                        DrawableUtils.clearState(((FrameLayout) itemView.findViewById(contentResId)).getForeground());
                }

                if ((expandState & Expandable.STATE_FLAG_IS_EXPANDED) != 0) {
                    isExpanded = true;
                } else {
                    isExpanded = false;
                }

                if(expandIndicatorResId != null && expandIndicatorResId != 0 && itemView.findViewById(expandIndicatorResId) != null)
                    ((ExpandableItemIndicator) itemView.findViewById(expandIndicatorResId)).setExpandedState(isExpanded, animateIndicator);
            }

            // set swiping properties
            setMaxLeftSwipeAmount(intMaxLeftSwipeAmount);
            setMaxRightSwipeAmount(intMaxRightSwipeAmount);
            if(item instanceof GroupData) {
                setSwipeItemHorizontalSlideAmount(
                        ((GroupData)item).isPinned() == DataUtils.SWAP_LEFT ? intMaxLeftSwipeAmount :
                        ((GroupData)item).isPinned() == DataUtils.SWAP_RIGHT ? intMaxRightSwipeAmount : 0);
            }
        }
    }

    public class MyChildViewHolder extends MyBaseViewHolder {
        private Object item;
        private int groupPosition;
        private int childPosition;

        public MyChildViewHolder(ViewDataBinding binding) {
            super(binding);
        }

        public void bindTo(int groupPosition, int childPosition, Object item) {
            this.item = item;
            this.groupPosition = groupPosition;
            this.childPosition = childPosition;
            getBinding().setVariable(variable, item);
            getBinding().executePendingBindings();

            if (onClickListener != null) {
                if (onClickResId != null && onClickResId.length > 0) {
                    for (int ids : onClickResId) {
                        if (itemView.findViewById(ids) != null) {
                            itemView.findViewById(ids).setOnClickListener(v -> onClickListener.onClick(new ItemViewTypePosition(item, v, getItemViewType(), groupPosition, childPosition)));
                        }
                    }
                } else {
                    itemView.setOnClickListener(v -> onClickListener.onClick(new ItemViewTypePosition(item, itemView, getItemViewType(), groupPosition, childPosition)));
                }
            }

            if (onLongClickListener != null) {
                if (onLongClickResId != null && onLongClickResId.length > 0) {
                    for (int ids : onLongClickResId) {
                        if (itemView.findViewById(ids) != null) {
                            itemView.findViewById(ids).setOnLongClickListener(v -> {
                                onLongClickListener.onLongClick(new ItemViewTypePosition(item, itemView, getItemViewType(), groupPosition, childPosition));
                                return true;
                            });
                        }
                    }
                } else {
                    itemView.setOnLongClickListener(v -> {
                        onLongClickListener.onLongClick(new ItemViewTypePosition(item, itemView, getItemViewType(), groupPosition, childPosition));
                        return true;
                    });
                }
            }

            final int dragState = getDragStateFlags();
            final int swipeState = getSwipeStateFlags();

            if (((dragState & Draggable.STATE_FLAG_IS_UPDATED) != 0) ||
                    ((swipeState & Swipeable.STATE_FLAG_IS_UPDATED) != 0)) {

                if ((dragState & Draggable.STATE_FLAG_IS_ACTIVE) != 0) {
                    // need to clear drawable state here to get correct appearance of the dragging item.
                    if(contentResId != null && contentResId != 0 && itemView.findViewById(contentResId) != null)
                        DrawableUtils.clearState(((FrameLayout) itemView.findViewById(contentResId)).getForeground());
                }
            }

            // set swiping properties
            setMaxLeftSwipeAmount(intMaxLeftSwipeAmount);
            setMaxRightSwipeAmount(intMaxRightSwipeAmount);
            if(item instanceof ChildData) {
                setSwipeItemHorizontalSlideAmount(
                        ((ChildData)item).isPinned() == DataUtils.SWAP_LEFT ? intMaxLeftSwipeAmount :
                                ((ChildData)item).isPinned() == DataUtils.SWAP_RIGHT ? intMaxRightSwipeAmount : 0);
            }
        }
    }

    private static class GroupSwipeLeftResultAction extends SwipeResultActionMoveToSwipedDirection {
        private RxGenericsAdapter mAdapter;
        private final int mGroupPosition;
        private boolean mSetPinned;

        GroupSwipeLeftResultAction(RxGenericsAdapter adapter, int groupPosition) {
            mAdapter = adapter;
            mGroupPosition = groupPosition;
        }

        @Override
        protected void onPerformAction() {
            super.onPerformAction();

            if(mAdapter.mProvider.get(mGroupPosition) instanceof Pair) {
                if(((Pair) mAdapter.mProvider.get(mGroupPosition)).first instanceof GroupData) {
                    GroupData item = ((GroupData) ((Pair) mAdapter.mProvider.get(mGroupPosition)).first);
                    if (item.isPinned() == DataUtils.SWAP_NONE) {
                        item.setPinned(DataUtils.SWAP_LEFT);
                        mAdapter.mExpandableItemManager.notifyGroupItemChanged(mGroupPosition);
                        mSetPinned = true;
                    }
                }
            } else {
                if(mAdapter.mProvider.get(mGroupPosition) instanceof GroupData) {
                    GroupData item = (GroupData) mAdapter.mProvider.get(mGroupPosition);
                    if (item.isPinned() == DataUtils.SWAP_NONE) {
                        item.setPinned(DataUtils.SWAP_LEFT);
                        mAdapter.mExpandableItemManager.notifyGroupItemChanged(mGroupPosition);
                        mSetPinned = true;
                    }
                }
            }
        }

        @Override
        protected void onSlideAnimationEnd() {
            super.onSlideAnimationEnd();
        }

        @Override
        protected void onCleanUp() {
            super.onCleanUp();
            // clear the references
            mAdapter = null;
        }
    }

    private static class GroupSwipeRightResultAction extends SwipeResultActionRemoveItem {
        private RxGenericsAdapter mAdapter;
        private final int mGroupPosition;
        private boolean mSetPinned;

        GroupSwipeRightResultAction(RxGenericsAdapter adapter, int groupPosition) {
            mAdapter = adapter;
            mGroupPosition = groupPosition;
        }

        @Override
        protected void onPerformAction() {
            super.onPerformAction();

            if(mAdapter.mProvider.get(mGroupPosition) instanceof Pair) {
                if(((Pair) mAdapter.mProvider.get(mGroupPosition)).first instanceof GroupData) {
                    GroupData item = ((GroupData) ((Pair) mAdapter.mProvider.get(mGroupPosition)).first);
                    if (item.isPinned() == DataUtils.SWAP_NONE) {
                        item.setPinned(DataUtils.SWAP_RIGHT);
                        mAdapter.mExpandableItemManager.notifyGroupItemChanged(mGroupPosition);
                        mSetPinned = true;
                    }
                }
            } else {
                if(mAdapter.mProvider.get(mGroupPosition) instanceof GroupData) {
                    GroupData item = (GroupData) mAdapter.mProvider.get(mGroupPosition);
                    if (item.isPinned() == DataUtils.SWAP_NONE) {
                        item.setPinned(DataUtils.SWAP_RIGHT);
                        mAdapter.mExpandableItemManager.notifyGroupItemChanged(mGroupPosition);
                        mSetPinned = true;
                    }
                }
            }
        }

        @Override
        protected void onSlideAnimationEnd() {
            super.onSlideAnimationEnd();
        }

        @Override
        protected void onCleanUp() {
            super.onCleanUp();
            // clear the references
            mAdapter = null;
        }
    }

    private static class GroupUnpinResultAction extends SwipeResultActionDefault {
        private RxGenericsAdapter mAdapter;
        private final int mGroupPosition;

        GroupUnpinResultAction(RxGenericsAdapter adapter, int groupPosition) {
            mAdapter = adapter;
            mGroupPosition = groupPosition;
        }

        @Override
        protected void onPerformAction() {
            super.onPerformAction();

            if(mAdapter.mProvider.get(mGroupPosition) instanceof Pair) {
                if(((Pair) mAdapter.mProvider.get(mGroupPosition)).first instanceof GroupData) {
                    GroupData item = ((GroupData) ((Pair) mAdapter.mProvider.get(mGroupPosition)).first);
                    if (item.isPinned() != DataUtils.SWAP_NONE) {
                        item.setPinned(DataUtils.SWAP_NONE);
                        mAdapter.mExpandableItemManager.notifyGroupItemChanged(mGroupPosition);
                    }
                }
            } else {
                if(mAdapter.mProvider.get(mGroupPosition) instanceof GroupData) {
                    GroupData item = (GroupData) mAdapter.mProvider.get(mGroupPosition);
                    if (item.isPinned() != DataUtils.SWAP_NONE) {
                        item.setPinned(DataUtils.SWAP_NONE);
                        mAdapter.mExpandableItemManager.notifyGroupItemChanged(mGroupPosition);
                    }
                }
            }
        }

        @Override
        protected void onCleanUp() {
            super.onCleanUp();
            // clear the references
            mAdapter = null;
        }
    }

    private static class ChildSwipeLeftResultAction extends SwipeResultActionMoveToSwipedDirection {
        private RxGenericsAdapter mAdapter;
        private final int mGroupPosition;
        private final int mChildPosition;
        private boolean mSetPinned;

        ChildSwipeLeftResultAction(RxGenericsAdapter adapter, int groupPosition, int childPosition) {
            mAdapter = adapter;
            mGroupPosition = groupPosition;
            mChildPosition = childPosition;
        }

        @Override
        protected void onPerformAction() {
            super.onPerformAction();
            if(mAdapter.mProvider.get(mGroupPosition) instanceof Pair) {
                if(((Pair) mAdapter.mProvider.get(mGroupPosition)).second instanceof List) {
                    if(((List) ((Pair) mAdapter.mProvider.get(mGroupPosition)).second).get(mChildPosition) instanceof ChildData) {
                        ChildData item = ((ChildData) ((List) ((Pair) mAdapter.mProvider.get(mGroupPosition)).second).get(mChildPosition));

                        if (item.isPinned() == DataUtils.SWAP_NONE) {
                            item.setPinned(DataUtils.SWAP_LEFT);
                            mAdapter.mExpandableItemManager.notifyChildItemChanged(mGroupPosition, mChildPosition);
                            mSetPinned = true;
                        }
                    }
                } else if(((Pair) mAdapter.mProvider.get(mGroupPosition)).second instanceof ArrayList) {
                    if(((ArrayList) ((Pair) mAdapter.mProvider.get(mGroupPosition)).second).get(mChildPosition) instanceof ChildData) {
                        ChildData item = ((ChildData) ((ArrayList) ((Pair) mAdapter.mProvider.get(mGroupPosition)).second).get(mChildPosition));

                        if (item.isPinned() == DataUtils.SWAP_NONE) {
                            item.setPinned(DataUtils.SWAP_LEFT);
                            mAdapter.mExpandableItemManager.notifyChildItemChanged(mGroupPosition, mChildPosition);
                            mSetPinned = true;
                        }
                    }
                }
            } else {
                if(mAdapter.mProvider.get(mGroupPosition) instanceof ChildData) {
                    ChildData item = (ChildData) mAdapter.mProvider.get(mGroupPosition);

                    if (item.isPinned() == DataUtils.SWAP_NONE) {
                        item.setPinned(DataUtils.SWAP_LEFT);
                        mAdapter.mExpandableItemManager.notifyChildItemChanged(mGroupPosition, mChildPosition);
                        mSetPinned = true;
                    }
                }
            }
        }

        @Override
        protected void onSlideAnimationEnd() {
            super.onSlideAnimationEnd();
        }

        @Override
        protected void onCleanUp() {
            super.onCleanUp();
            // clear the references
            mAdapter = null;
        }
    }

    private static class ChildSwipeRightResultAction extends SwipeResultActionRemoveItem {
        private RxGenericsAdapter mAdapter;
        private final int mGroupPosition;
        private final int mChildPosition;
        private boolean mSetPinned;

        ChildSwipeRightResultAction(RxGenericsAdapter adapter, int groupPosition, int childPosition) {
            mAdapter = adapter;
            mGroupPosition = groupPosition;
            mChildPosition = childPosition;
        }

        @Override
        protected void onPerformAction() {
            super.onPerformAction();

            if(mAdapter.mProvider.get(mGroupPosition) instanceof Pair) {
                if(((Pair) mAdapter.mProvider.get(mGroupPosition)).second instanceof List) {
                    if(((List) ((Pair) mAdapter.mProvider.get(mGroupPosition)).second).get(mChildPosition) instanceof ChildData) {
                        ChildData item = ((ChildData) ((List) ((Pair) mAdapter.mProvider.get(mGroupPosition)).second).get(mChildPosition));

                        if (item.isPinned() == DataUtils.SWAP_NONE) {
                            item.setPinned(DataUtils.SWAP_RIGHT);
                            mAdapter.mExpandableItemManager.notifyChildItemChanged(mGroupPosition, mChildPosition);
                            mSetPinned = true;
                        }
                    }
                } else if(((Pair) mAdapter.mProvider.get(mGroupPosition)).second instanceof ArrayList) {
                    if(((ArrayList) ((Pair) mAdapter.mProvider.get(mGroupPosition)).second).get(mChildPosition) instanceof ChildData) {
                        ChildData item = ((ChildData) ((ArrayList) ((Pair) mAdapter.mProvider.get(mGroupPosition)).second).get(mChildPosition));

                        if (item.isPinned() == DataUtils.SWAP_NONE) {
                            item.setPinned(DataUtils.SWAP_RIGHT);
                            mAdapter.mExpandableItemManager.notifyChildItemChanged(mGroupPosition, mChildPosition);
                            mSetPinned = true;
                        }
                    }
                }
            } else {
                if(mAdapter.mProvider.get(mGroupPosition) instanceof ChildData) {
                    ChildData item = (ChildData) mAdapter.mProvider.get(mGroupPosition);

                    if (item.isPinned() == DataUtils.SWAP_NONE) {
                        item.setPinned(DataUtils.SWAP_RIGHT);
                        mAdapter.mExpandableItemManager.notifyChildItemChanged(mGroupPosition, mChildPosition);
                        mSetPinned = true;
                    }
                }
            }
        }

        @Override
        protected void onSlideAnimationEnd() {
            super.onSlideAnimationEnd();
        }

        @Override
        protected void onCleanUp() {
            super.onCleanUp();
            // clear the references
            mAdapter = null;
        }
    }

    private static class ChildUnpinResultAction extends SwipeResultActionDefault {
        private RxGenericsAdapter mAdapter;
        private final int mGroupPosition;
        private final int mChildPosition;

        ChildUnpinResultAction(RxGenericsAdapter adapter, int groupPosition, int childPosition) {
            mAdapter = adapter;
            mGroupPosition = groupPosition;
            mChildPosition = childPosition;
        }

        @Override
        protected void onPerformAction() {
            super.onPerformAction();

            if(mAdapter.mProvider.get(mGroupPosition) instanceof Pair) {
                if(((Pair) mAdapter.mProvider.get(mGroupPosition)).second instanceof List) {
                    if(((List) ((Pair) mAdapter.mProvider.get(mGroupPosition)).second).get(mChildPosition) instanceof ChildData) {
                        ChildData item = ((ChildData) ((List) ((Pair) mAdapter.mProvider.get(mGroupPosition)).second).get(mChildPosition));

                        if (item.isPinned() != DataUtils.SWAP_NONE) {
                            item.setPinned(DataUtils.SWAP_NONE);
                            mAdapter.mExpandableItemManager.notifyChildItemChanged(mGroupPosition, mChildPosition);
                        }
                    }
                } else if(((Pair) mAdapter.mProvider.get(mGroupPosition)).second instanceof ArrayList) {
                    if(((ArrayList) ((Pair) mAdapter.mProvider.get(mGroupPosition)).second).get(mChildPosition) instanceof ChildData) {
                        ChildData item = ((ChildData) ((ArrayList) ((Pair) mAdapter.mProvider.get(mGroupPosition)).second).get(mChildPosition));

                        if (item.isPinned() != DataUtils.SWAP_NONE) {
                            item.setPinned(DataUtils.SWAP_NONE);
                            mAdapter.mExpandableItemManager.notifyChildItemChanged(mGroupPosition, mChildPosition);
                        }
                    }
                }
            } else {
                if(mAdapter.mProvider.get(mGroupPosition) instanceof ChildData) {
                    ChildData item = (ChildData) mAdapter.mProvider.get(mGroupPosition);

                    if (item.isPinned() != DataUtils.SWAP_NONE) {
                        item.setPinned(DataUtils.SWAP_NONE);
                        mAdapter.mExpandableItemManager.notifyChildItemChanged(mGroupPosition, mChildPosition);
                    }
                }
            }
        }

        @Override
        protected void onCleanUp() {
            super.onCleanUp();
            // clear the references
            mAdapter = null;
        }
    }

    public rx.Observable<RxGenericsAdapter.MyBaseViewHolder> asObservable() {
        return mPublishSubject.asObservable();
    }

    public List<DataType> getDataSet() {
        return mProvider;
    }

    public void updateDataSet(List<DataType> dataSet) {
        mProvider = dataSet;
        notifyDataSetChanged();
    }

    public static RxGenericsAdapter.Builder with(List list, int variable) {
        return (new RxGenericsAdapter.Builder<>(list, variable));
    }

    public static class Builder<DataType> implements RecyclerViewExpandableItemManager.OnGroupExpandListener, RecyclerViewExpandableItemManager.OnGroupCollapseListener {
        private Integer variable;
        private List<DataType> list;
        private Integer[] onClickResIds;
        private Integer[] onLongClickResIds;

        private Map<Class<?>, Object> map;
        private RxGenericsAdapter.LayoutHandler handler = null;
        private RxGenericsAdapter.OnClickListener onClick = null;
        private RxGenericsAdapter.OnLoadMoreListener onLoadMore = null;
        private RxGenericsAdapter.OnLongClickListener onLongClick = null;

        private Integer contentResId, expandIndicatorResId, dragHandleResId, childItemHeight;
        private Float intMaxLeftSwipeAmount = 0f, intMaxRightSwipeAmount = 0f;

        private RecyclerView.Adapter mWrappedAdapter = null;
        private RecyclerViewExpandableItemManager mExpandableItemManager = null;
        private RecyclerViewDragDropManager mDragDropManager = null;
        private RecyclerViewSwipeManager mSwipeManager = null;
        private RecyclerViewTouchActionGuardManager mTouchActionGuardManager = null;

        public RxGenericsAdapter.Builder map(Class<?> clazz, int layout) {
            map.put(clazz, layout);
            return this;
        }

        public <T> RxGenericsAdapter.Builder map(int layout) {
            return map(Object.class, layout);
        }

        public RxGenericsAdapter.Builder layoutHandler(RxGenericsAdapter.LayoutHandler layoutHandler) {
            handler = layoutHandler;
            return this;
        }

        public RxGenericsAdapter.Builder layout(Func1<? super RxGenericsAdapter.ItemPosition, Integer> func1) {
            return layoutHandler((detail) -> func1.call(detail).intValue());
        }

        public RxGenericsAdapter.Builder onClickListener(RxGenericsAdapter.OnClickListener listener, Integer... resIds) {
            onClick = listener;
            onClickResIds = resIds;
            return this;
        }

        public RxGenericsAdapter.Builder onClick(Func1<? super RxGenericsAdapter.ItemViewTypePosition, Void> func1, Integer... resIds) {
            return onClickListener((detail) -> func1.call(detail), resIds);
        }

        public RxGenericsAdapter.Builder onLongClickListener(RxGenericsAdapter.OnLongClickListener listener, Integer... resIds) {
            onLongClick = listener;
            onLongClickResIds = resIds;
            return this;
        }

        public RxGenericsAdapter.Builder onLongClick(Func1<? super RxGenericsAdapter.ItemViewTypePosition, Void> func1, Integer... resIds) {
            return onLongClickListener((detail) -> func1.call(detail), resIds);
        }

        public RxGenericsAdapter.Builder onLoadMoreListener(RxGenericsAdapter.OnLoadMoreListener listener) {
            onLoadMore = listener;
            return this;
        }

        public RxGenericsAdapter.Builder onLoadMore(Func1<? super Integer, Boolean> func1) {
            return onLoadMoreListener(size -> func1.call(size));
        }

        public RxGenericsAdapter.Builder onSwapMenuListener(Integer contentViewResId, Float intMaxLeftSwipeAmount, Float intMaxRightSwipeAmount) {
            contentResId = contentViewResId;
            this.intMaxLeftSwipeAmount = intMaxLeftSwipeAmount;
            this.intMaxRightSwipeAmount = intMaxRightSwipeAmount;

            // swipe manager
            mSwipeManager = new RecyclerViewSwipeManager();
            return this;
        }

        public Builder onDragListener(Integer dragHandleResId) {
            this.dragHandleResId = dragHandleResId;

            // drag & drop manager
            mDragDropManager = new RecyclerViewDragDropManager();
            return this;
        }

        public Builder onExpandListener(Integer expandIndicatorResId, Bundle savedInstanceState) {
            this.expandIndicatorResId = expandIndicatorResId;

            final Parcelable eimSavedState = (savedInstanceState != null) ? savedInstanceState.getParcelable(SAVED_STATE_EXPANDABLE_ITEM_MANAGER) : null;
            mExpandableItemManager = new RecyclerViewExpandableItemManager(eimSavedState);
            mExpandableItemManager.setOnGroupExpandListener(this);
            mExpandableItemManager.setOnGroupCollapseListener(this);

            return this;
        }

        public RxGenericsAdapter into(Integer childItemHeight, RecyclerView recyclerView, RecyclerView.LayoutManager... layoutManager) {
            this.childItemHeight = childItemHeight;
            // touch guard manager  (this class is required to suppress scrolling while swipe-dismiss animation is running)
            mTouchActionGuardManager = new RecyclerViewTouchActionGuardManager();
            mTouchActionGuardManager.setInterceptVerticalScrollingWhileAnimationRunning(true);
            mTouchActionGuardManager.setEnabled(true);

            RxGenericsAdapter adapter = build();
            if(layoutManager != null && layoutManager.length > 0) {
                recyclerView.setHasFixedSize(false);
                layoutManager[0].setAutoMeasureEnabled(true);
                recyclerView.setLayoutManager(layoutManager[0]);
            }

            if(onLoadMore != null)
                setScrolling(recyclerView);

            if(mExpandableItemManager != null)
                mWrappedAdapter = mExpandableItemManager.createWrappedAdapter(adapter);
            else {
                final Parcelable eimSavedState = null;
                mExpandableItemManager = new RecyclerViewExpandableItemManager(eimSavedState);
                mExpandableItemManager.setOnGroupExpandListener(this);
                mExpandableItemManager.setOnGroupCollapseListener(this);
                mWrappedAdapter = mExpandableItemManager.createWrappedAdapter(adapter);
            }
            if(mDragDropManager != null) {
                if(mWrappedAdapter != null)
                    mWrappedAdapter = mDragDropManager.createWrappedAdapter(mWrappedAdapter);
                else
                    mWrappedAdapter = mDragDropManager.createWrappedAdapter(adapter);
            }
            if(mSwipeManager != null) {
                if(mWrappedAdapter != null)
                    mWrappedAdapter = mSwipeManager.createWrappedAdapter(mWrappedAdapter);
                else
                    mWrappedAdapter = mSwipeManager.createWrappedAdapter(adapter);
                final GeneralItemAnimator animator = new SwipeDismissItemAnimator();
                animator.setSupportsChangeAnimations(false);
                recyclerView.setItemAnimator(animator);
            }

            recyclerView.setAdapter(mWrappedAdapter);
            mTouchActionGuardManager.attachRecyclerView(recyclerView);

            if(mExpandableItemManager != null)
                mExpandableItemManager.attachRecyclerView(recyclerView);
            if(mDragDropManager != null)
                mDragDropManager.attachRecyclerView(recyclerView);
            if(mSwipeManager != null)
                mSwipeManager.attachRecyclerView(recyclerView);

            return adapter;
        }

        public void setScrolling(RecyclerView recyclerView) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                private boolean loading = false;
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

        public RxGenericsAdapter build() {
            return new RxGenericsAdapter<DataType>(
                    list, variable, map, handler, intMaxLeftSwipeAmount, intMaxRightSwipeAmount,
                    mExpandableItemManager,
                    onClick, onClickResIds, onLongClick, onLongClickResIds,
                    contentResId, expandIndicatorResId, dragHandleResId);
        }

        public Builder(List<DataType> list, Integer variable) {
            super();
            this.list = list;
            this.variable = variable;
            map = new HashMap<>();
        }

        @Override
        public void onGroupExpand(int groupPosition, boolean fromUser) {
            if (fromUser && mExpandableItemManager != null)
                mExpandableItemManager.scrollToGroup(groupPosition, childItemHeight, 0, 0);
        }

        @Override
        public void onGroupCollapse(int groupPosition, boolean fromUser) {

        }
    }

    public static interface Draggable extends DraggableItemConstants {}

    public static interface Expandable extends ExpandableItemConstants {}

    public static interface Swipeable extends SwipeableItemConstants {}

    public interface LayoutHandler {
        int getItemLayout(ItemPosition detail);
    }

    public interface OnClickListener {
        void onClick(ItemViewTypePosition detail);
    }

    public interface OnLongClickListener {
        void onLongClick(ItemViewTypePosition detail);
    }

    public interface OnLoadMoreListener {
        boolean onLoadMore(int size);
    }

    public static class ItemPosition {
        private Object item;
        private int groupPosition;
        private int childPosition;

        public ItemPosition(Object item, int groupPosition, int childPosition) {
            this.item = item;
            this.groupPosition = groupPosition;
            this.childPosition = childPosition;
        }

        public Object getItem() {
            return item;
        }

        public int getGroupPosition() {
            return groupPosition;
        }

        public int getChildPosition() {
            return childPosition;
        }
    }

    public static class ItemViewTypePosition {
        private Object item;
        private View view;
        private int type;
        private int groupPosition;
        private int childPosition;

        public ItemViewTypePosition(Object item, View view, int type, int groupPosition, int childPosition) {
            this.item = item;
            this.view = view;
            this.type = type;
            this.groupPosition = groupPosition;
            this.childPosition = childPosition;
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

        public int getGroupPosition() {
            return groupPosition;
        }

        public int getChildPosition() {
            return childPosition;
        }
    }
}
