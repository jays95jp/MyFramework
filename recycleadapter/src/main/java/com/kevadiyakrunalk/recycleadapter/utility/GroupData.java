package com.kevadiyakrunalk.recycleadapter.utility;

/**
 * Created by KevadiyaKrunalK on 08-01-2017.
 */

public abstract class GroupData {
    public abstract boolean isSectionHeader();
    public abstract long getGroupId();

    public abstract void setPinned(int pinned);
    public abstract int isPinned();
}
