package com.meizu.i18n.phonenumbers;

import java.util.HashSet;
import java.util.Set;

public class AlternateFormatsCountryCodeSet {
    static Set<Integer> getCountryCodeSet() {
        Set<Integer> hashSet = new HashSet(8);
        hashSet.add(Integer.valueOf(43));
        hashSet.add(Integer.valueOf(44));
        hashSet.add(Integer.valueOf(49));
        hashSet.add(Integer.valueOf(55));
        hashSet.add(Integer.valueOf(61));
        hashSet.add(Integer.valueOf(81));
        return hashSet;
    }
}
