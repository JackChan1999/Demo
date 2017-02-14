package com.meizu.i18n.phonenumbers.geocoding;

import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Iterator;
import java.util.SortedMap;

class DefaultMapStorage extends AreaCodeMapStorageStrategy {
    private String[] descriptions;
    private int[] phoneNumberPrefixes;

    public int getPrefix(int i) {
        return this.phoneNumberPrefixes[i];
    }

    public String getDescription(int i) {
        return this.descriptions[i];
    }

    public void readFromSortedMap(SortedMap<Integer, String> sortedMap) {
        this.numOfEntries = sortedMap.size();
        this.phoneNumberPrefixes = new int[this.numOfEntries];
        this.descriptions = new String[this.numOfEntries];
        int i = 0;
        for (Integer intValue : sortedMap.keySet()) {
            int intValue2 = intValue.intValue();
            int i2 = i + 1;
            this.phoneNumberPrefixes[i] = intValue2;
            this.possibleLengths.add(Integer.valueOf(((int) Math.log10((double) intValue2)) + 1));
            i = i2;
        }
        sortedMap.values().toArray(this.descriptions);
    }

    public void readExternal(ObjectInput objectInput) {
        int i;
        int i2 = 0;
        this.numOfEntries = objectInput.readInt();
        if (this.phoneNumberPrefixes == null || this.phoneNumberPrefixes.length < this.numOfEntries) {
            this.phoneNumberPrefixes = new int[this.numOfEntries];
        }
        if (this.descriptions == null || this.descriptions.length < this.numOfEntries) {
            this.descriptions = new String[this.numOfEntries];
        }
        for (i = 0; i < this.numOfEntries; i++) {
            this.phoneNumberPrefixes[i] = objectInput.readInt();
            this.descriptions[i] = objectInput.readUTF();
        }
        i = objectInput.readInt();
        this.possibleLengths.clear();
        while (i2 < i) {
            this.possibleLengths.add(Integer.valueOf(objectInput.readInt()));
            i2++;
        }
    }

    public void writeExternal(ObjectOutput objectOutput) {
        objectOutput.writeInt(this.numOfEntries);
        for (int i = 0; i < this.numOfEntries; i++) {
            objectOutput.writeInt(this.phoneNumberPrefixes[i]);
            objectOutput.writeUTF(this.descriptions[i]);
        }
        objectOutput.writeInt(this.possibleLengths.size());
        Iterator it = this.possibleLengths.iterator();
        while (it.hasNext()) {
            objectOutput.writeInt(((Integer) it.next()).intValue());
        }
    }
}
