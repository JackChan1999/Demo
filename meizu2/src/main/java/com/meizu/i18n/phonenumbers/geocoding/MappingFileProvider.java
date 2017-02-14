package com.meizu.i18n.phonenumbers.geocoding;

import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeSet;

public class MappingFileProvider implements Externalizable {
    private static final Map<String, String> LOCALE_NORMALIZATION_MAP;
    private List<Set<String>> availableLanguages;
    private int[] countryCallingCodes;
    private int numOfEntries = 0;

    static {
        Map hashMap = new HashMap();
        hashMap.put("zh_TW", "zh_Hant");
        hashMap.put("zh_HK", "zh_Hant");
        hashMap.put("zh_MO", "zh_Hant");
        LOCALE_NORMALIZATION_MAP = Collections.unmodifiableMap(hashMap);
    }

    public void readFileConfigs(SortedMap<Integer, Set<String>> sortedMap) {
        this.numOfEntries = sortedMap.size();
        this.countryCallingCodes = new int[this.numOfEntries];
        this.availableLanguages = new ArrayList(this.numOfEntries);
        int i = 0;
        for (Integer intValue : sortedMap.keySet()) {
            int intValue2 = intValue.intValue();
            int i2 = i + 1;
            this.countryCallingCodes[i] = intValue2;
            this.availableLanguages.add(new HashSet((Collection) sortedMap.get(Integer.valueOf(intValue2))));
            i = i2;
        }
    }

    public void readExternal(ObjectInput objectInput) {
        this.numOfEntries = objectInput.readInt();
        if (this.countryCallingCodes == null || this.countryCallingCodes.length < this.numOfEntries) {
            this.countryCallingCodes = new int[this.numOfEntries];
        }
        if (this.availableLanguages == null) {
            this.availableLanguages = new ArrayList();
        }
        for (int i = 0; i < this.numOfEntries; i++) {
            this.countryCallingCodes[i] = objectInput.readInt();
            int readInt = objectInput.readInt();
            Set hashSet = new HashSet();
            for (int i2 = 0; i2 < readInt; i2++) {
                hashSet.add(objectInput.readUTF());
            }
            this.availableLanguages.add(hashSet);
        }
    }

    public void writeExternal(ObjectOutput objectOutput) {
        objectOutput.writeInt(this.numOfEntries);
        for (int i = 0; i < this.numOfEntries; i++) {
            objectOutput.writeInt(this.countryCallingCodes[i]);
            Set<String> set = (Set) this.availableLanguages.get(i);
            objectOutput.writeInt(set.size());
            for (String writeUTF : set) {
                objectOutput.writeUTF(writeUTF);
            }
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.numOfEntries; i++) {
            stringBuilder.append(this.countryCallingCodes[i]);
            stringBuilder.append('|');
            for (String append : new TreeSet((Collection) this.availableLanguages.get(i))) {
                stringBuilder.append(append);
                stringBuilder.append(',');
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    String getFileName(int i, String str, String str2, String str3) {
        if (str.length() == 0) {
            return "";
        }
        int binarySearch = Arrays.binarySearch(this.countryCallingCodes, i);
        if (binarySearch < 0) {
            return "";
        }
        Set set = (Set) this.availableLanguages.get(binarySearch);
        if (set.size() > 0) {
            String findBestMatchingLanguageCode = findBestMatchingLanguageCode(set, str, str2, str3);
            if (findBestMatchingLanguageCode.length() > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(i).append('_').append(findBestMatchingLanguageCode);
                return stringBuilder.toString();
            }
        }
        return "";
    }

    private String findBestMatchingLanguageCode(Set<String> set, String str, String str2, String str3) {
        String stringBuilder = constructFullLocale(str, str2, str3).toString();
        String str4 = (String) LOCALE_NORMALIZATION_MAP.get(stringBuilder);
        if (str4 != null && set.contains(str4)) {
            return str4;
        }
        if (set.contains(stringBuilder)) {
            return stringBuilder;
        }
        if (onlyOneOfScriptOrRegionIsEmpty(str2, str3)) {
            if (set.contains(str)) {
                return str;
            }
        } else if (str2.length() > 0 && str3.length() > 0) {
            str4 = '_' + str2;
            if (set.contains(str4)) {
                return str4;
            }
            str4 = '_' + str3;
            if (set.contains(str4)) {
                return str4;
            }
            if (set.contains(str)) {
                return str;
            }
        }
        return "";
    }

    private boolean onlyOneOfScriptOrRegionIsEmpty(String str, String str2) {
        return (str.length() == 0 && str2.length() > 0) || (str2.length() == 0 && str.length() > 0);
    }

    private StringBuilder constructFullLocale(String str, String str2, String str3) {
        StringBuilder stringBuilder = new StringBuilder(str);
        appendSubsequentLocalePart(str2, stringBuilder);
        appendSubsequentLocalePart(str3, stringBuilder);
        return stringBuilder;
    }

    private void appendSubsequentLocalePart(String str, StringBuilder stringBuilder) {
        if (str.length() > 0) {
            stringBuilder.append('_').append(str);
        }
    }
}
