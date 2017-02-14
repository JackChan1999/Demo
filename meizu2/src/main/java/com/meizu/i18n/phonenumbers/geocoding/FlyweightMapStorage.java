package com.meizu.i18n.phonenumbers.geocoding;

import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;

final class FlyweightMapStorage extends AreaCodeMapStorageStrategy {
    private static final int INT_NUM_BYTES = 4;
    private static final int SHORT_NUM_BYTES = 2;
    private int descIndexSizeInBytes;
    private ByteBuffer descriptionIndexes;
    private String[] descriptionPool;
    private ByteBuffer phoneNumberPrefixes;
    private int prefixSizeInBytes;

    FlyweightMapStorage() {
    }

    public int getPrefix(int i) {
        return readWordFromBuffer(this.phoneNumberPrefixes, this.prefixSizeInBytes, i);
    }

    public String getDescription(int i) {
        return this.descriptionPool[readWordFromBuffer(this.descriptionIndexes, this.descIndexSizeInBytes, i)];
    }

    public void readFromSortedMap(SortedMap<Integer, String> sortedMap) {
        SortedSet treeSet = new TreeSet();
        this.numOfEntries = sortedMap.size();
        this.prefixSizeInBytes = getOptimalNumberOfBytesForValue(((Integer) sortedMap.lastKey()).intValue());
        this.phoneNumberPrefixes = ByteBuffer.allocate(this.numOfEntries * this.prefixSizeInBytes);
        int i = 0;
        for (Entry entry : sortedMap.entrySet()) {
            int intValue = ((Integer) entry.getKey()).intValue();
            storeWordInBuffer(this.phoneNumberPrefixes, this.prefixSizeInBytes, i, intValue);
            this.possibleLengths.add(Integer.valueOf(((int) Math.log10((double) intValue)) + 1));
            treeSet.add(entry.getValue());
            i++;
        }
        createDescriptionPool(treeSet, sortedMap);
    }

    private void createDescriptionPool(SortedSet<String> sortedSet, SortedMap<Integer, String> sortedMap) {
        this.descIndexSizeInBytes = getOptimalNumberOfBytesForValue(sortedSet.size() - 1);
        this.descriptionIndexes = ByteBuffer.allocate(this.numOfEntries * this.descIndexSizeInBytes);
        this.descriptionPool = new String[sortedSet.size()];
        sortedSet.toArray(this.descriptionPool);
        int i = 0;
        for (int i2 = 0; i2 < this.numOfEntries; i2++) {
            storeWordInBuffer(this.descriptionIndexes, this.descIndexSizeInBytes, i, Arrays.binarySearch(this.descriptionPool, (String) sortedMap.get(Integer.valueOf(readWordFromBuffer(this.phoneNumberPrefixes, this.prefixSizeInBytes, i2)))));
            i++;
        }
    }

    public void readExternal(ObjectInput objectInput) {
        int i;
        int i2 = 0;
        this.prefixSizeInBytes = objectInput.readInt();
        this.descIndexSizeInBytes = objectInput.readInt();
        int readInt = objectInput.readInt();
        this.possibleLengths.clear();
        for (i = 0; i < readInt; i++) {
            this.possibleLengths.add(Integer.valueOf(objectInput.readInt()));
        }
        i = objectInput.readInt();
        if (this.descriptionPool == null || this.descriptionPool.length < i) {
            this.descriptionPool = new String[i];
        }
        while (i2 < i) {
            this.descriptionPool[i2] = objectInput.readUTF();
            i2++;
        }
        readEntries(objectInput);
    }

    private void readEntries(ObjectInput objectInput) {
        this.numOfEntries = objectInput.readInt();
        if (this.phoneNumberPrefixes == null || this.phoneNumberPrefixes.capacity() < this.numOfEntries) {
            this.phoneNumberPrefixes = ByteBuffer.allocate(this.numOfEntries * this.prefixSizeInBytes);
        }
        if (this.descriptionIndexes == null || this.descriptionIndexes.capacity() < this.numOfEntries) {
            this.descriptionIndexes = ByteBuffer.allocate(this.numOfEntries * this.descIndexSizeInBytes);
        }
        for (int i = 0; i < this.numOfEntries; i++) {
            readExternalWord(objectInput, this.prefixSizeInBytes, this.phoneNumberPrefixes, i);
            readExternalWord(objectInput, this.descIndexSizeInBytes, this.descriptionIndexes, i);
        }
    }

    public void writeExternal(ObjectOutput objectOutput) {
        int i;
        objectOutput.writeInt(this.prefixSizeInBytes);
        objectOutput.writeInt(this.descIndexSizeInBytes);
        objectOutput.writeInt(this.possibleLengths.size());
        Iterator it = this.possibleLengths.iterator();
        while (it.hasNext()) {
            objectOutput.writeInt(((Integer) it.next()).intValue());
        }
        objectOutput.writeInt(this.descriptionPool.length);
        for (String writeUTF : this.descriptionPool) {
            objectOutput.writeUTF(writeUTF);
        }
        objectOutput.writeInt(this.numOfEntries);
        for (i = 0; i < this.numOfEntries; i++) {
            writeExternalWord(objectOutput, this.prefixSizeInBytes, this.phoneNumberPrefixes, i);
            writeExternalWord(objectOutput, this.descIndexSizeInBytes, this.descriptionIndexes, i);
        }
    }

    private static int getOptimalNumberOfBytesForValue(int i) {
        return i <= 32767 ? 2 : 4;
    }

    private static void readExternalWord(ObjectInput objectInput, int i, ByteBuffer byteBuffer, int i2) {
        int i3 = i2 * i;
        if (i == 2) {
            byteBuffer.putShort(i3, objectInput.readShort());
        } else {
            byteBuffer.putInt(i3, objectInput.readInt());
        }
    }

    private static void writeExternalWord(ObjectOutput objectOutput, int i, ByteBuffer byteBuffer, int i2) {
        int i3 = i2 * i;
        if (i == 2) {
            objectOutput.writeShort(byteBuffer.getShort(i3));
        } else {
            objectOutput.writeInt(byteBuffer.getInt(i3));
        }
    }

    private static int readWordFromBuffer(ByteBuffer byteBuffer, int i, int i2) {
        int i3 = i2 * i;
        return i == 2 ? byteBuffer.getShort(i3) : byteBuffer.getInt(i3);
    }

    private static void storeWordInBuffer(ByteBuffer byteBuffer, int i, int i2, int i3) {
        int i4 = i2 * i;
        if (i == 2) {
            byteBuffer.putShort(i4, (short) i3);
        } else {
            byteBuffer.putInt(i4, i3);
        }
    }
}
