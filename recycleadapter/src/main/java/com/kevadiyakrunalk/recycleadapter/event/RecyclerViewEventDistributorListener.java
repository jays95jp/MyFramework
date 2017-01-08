package com.kevadiyakrunalk.recycleadapter.event;

public interface RecyclerViewEventDistributorListener {
    void onAddedToEventDistributor(BaseRecyclerViewEventDistributor distributor);
    void onRemovedFromEventDistributor(BaseRecyclerViewEventDistributor distributor);
}
