package com.meizu.i18n.phonenumbers.geocoding;

import com.meizu.common.widget.MzContactsContract.MzGroups;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.SortedMap;
import java.util.TreeSet;

abstract class AreaCodeMapStorageStrategy {
    protected int numOfEntries = 0;
    protected final TreeSet<Integer> possibleLengths = new TreeSet();

    public abstract String getDescription(int i);

    public abstract int getPrefix(int i);

    public abstract void readExternal(ObjectInput objectInput);

    public abstract void readFromSortedMap(SortedMap<Integer, String> sortedMap);

    public abstract void writeExternal(ObjectOutput objectOutput);

    AreaCodeMapStorageStrategy() {
    }

    public int getNumOfEntries() {
        return this.numOfEntries;
    }

    public TreeSet<Integer> getPossibleLengths() {
        return this.possibleLengths;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int numOfEntries = getNumOfEntries();
        for (int i = 0; i < numOfEntries; i++) {
            stringBuilder.append(getPrefix(i)).append(MzGroups.GROUP_SPLIT_MARK_XML).append(getDescription(i)).append("\n");
        }
        return stringBuilder.toString();
    }
}
