package com.meizu.i18n.phonenumbers.geocoding;

import com.meizu.i18n.phonenumbers.PhoneNumberUtil;
import com.meizu.i18n.phonenumbers.Phonenumber.PhoneNumber;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.logging.Logger;

public class AreaCodeMap implements Externalizable {
    private static final Logger LOGGER = Logger.getLogger(AreaCodeMap.class.getName());
    private AreaCodeMapStorageStrategy areaCodeMapStorage;
    private final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

    AreaCodeMapStorageStrategy getAreaCodeMapStorage() {
        return this.areaCodeMapStorage;
    }

    private static int getSizeOfAreaCodeMapStorage(AreaCodeMapStorageStrategy areaCodeMapStorageStrategy, SortedMap<Integer, String> sortedMap) {
        areaCodeMapStorageStrategy.readFromSortedMap(sortedMap);
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        areaCodeMapStorageStrategy.writeExternal(objectOutputStream);
        objectOutputStream.flush();
        int size = byteArrayOutputStream.size();
        objectOutputStream.close();
        return size;
    }

    private AreaCodeMapStorageStrategy createDefaultMapStorage() {
        return new DefaultMapStorage();
    }

    private AreaCodeMapStorageStrategy createFlyweightMapStorage() {
        return new FlyweightMapStorage();
    }

    AreaCodeMapStorageStrategy getSmallerMapStorage(SortedMap<Integer, String> sortedMap) {
        try {
            AreaCodeMapStorageStrategy createFlyweightMapStorage = createFlyweightMapStorage();
            int sizeOfAreaCodeMapStorage = getSizeOfAreaCodeMapStorage(createFlyweightMapStorage, sortedMap);
            AreaCodeMapStorageStrategy createDefaultMapStorage = createDefaultMapStorage();
            return sizeOfAreaCodeMapStorage < getSizeOfAreaCodeMapStorage(createDefaultMapStorage, sortedMap) ? createFlyweightMapStorage : createDefaultMapStorage;
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
            return createFlyweightMapStorage();
        }
    }

    public void readAreaCodeMap(SortedMap<Integer, String> sortedMap) {
        this.areaCodeMapStorage = getSmallerMapStorage(sortedMap);
    }

    public void readExternal(ObjectInput objectInput) {
        if (objectInput.readBoolean()) {
            this.areaCodeMapStorage = new FlyweightMapStorage();
        } else {
            this.areaCodeMapStorage = new DefaultMapStorage();
        }
        this.areaCodeMapStorage.readExternal(objectInput);
    }

    public void writeExternal(ObjectOutput objectOutput) {
        objectOutput.writeBoolean(this.areaCodeMapStorage instanceof FlyweightMapStorage);
        this.areaCodeMapStorage.writeExternal(objectOutput);
    }

    String lookup(PhoneNumber phoneNumber) {
        int numOfEntries = this.areaCodeMapStorage.getNumOfEntries();
        if (numOfEntries == 0) {
            return null;
        }
        long parseLong = Long.parseLong(phoneNumber.getCountryCode() + this.phoneUtil.getNationalSignificantNumber(phoneNumber));
        long j = parseLong;
        int i = numOfEntries - 1;
        SortedSet possibleLengths = this.areaCodeMapStorage.getPossibleLengths();
        while (possibleLengths.size() > 0) {
            Integer num = (Integer) possibleLengths.last();
            String valueOf = String.valueOf(j);
            if (valueOf.length() > num.intValue()) {
                j = Long.parseLong(valueOf.substring(0, num.intValue()));
            }
            i = binarySearch(0, i, j);
            if (i < 0) {
                return null;
            }
            if (j == ((long) this.areaCodeMapStorage.getPrefix(i))) {
                return this.areaCodeMapStorage.getDescription(i);
            }
            possibleLengths = possibleLengths.headSet(num);
        }
        return null;
    }

    private int binarySearch(int i, int i2, long j) {
        int i3 = 0;
        int i4 = i2;
        int i5 = i;
        while (i5 <= i4) {
            i3 = (i5 + i4) >>> 1;
            int prefix = this.areaCodeMapStorage.getPrefix(i3);
            if (((long) prefix) == j) {
                break;
            } else if (((long) prefix) > j) {
                i3--;
                i4 = i3;
            } else {
                i5 = i3 + 1;
            }
        }
        return i3;
    }

    public String toString() {
        return this.areaCodeMapStorage.toString();
    }
}
