package com.meizu.i18n.phonenumbers;

import com.meizu.common.widget.MzContactsContract.MzGroups;
import com.meizu.i18n.phonenumbers.Phonenumber.PhoneNumber;
import java.util.Arrays;

public final class PhoneNumberMatch {
    private final PhoneNumber number;
    private final String rawString;
    private final int start;

    PhoneNumberMatch(int i, String str, PhoneNumber phoneNumber) {
        if (i < 0) {
            throw new IllegalArgumentException("Start index must be >= 0.");
        } else if (str == null || phoneNumber == null) {
            throw new NullPointerException();
        } else {
            this.start = i;
            this.rawString = str;
            this.number = phoneNumber;
        }
    }

    public PhoneNumber number() {
        return this.number;
    }

    public int start() {
        return this.start;
    }

    public int end() {
        return this.start + this.rawString.length();
    }

    public String rawString() {
        return this.rawString;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.start), this.rawString, this.number});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PhoneNumberMatch)) {
            return false;
        }
        PhoneNumberMatch phoneNumberMatch = (PhoneNumberMatch) obj;
        if (this.rawString.equals(phoneNumberMatch.rawString) && this.start == phoneNumberMatch.start && this.number.equals(phoneNumberMatch.number)) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "PhoneNumberMatch [" + start() + MzGroups.GROUP_SPLIT_MARK_EXTRA + end() + ") " + this.rawString;
    }
}
