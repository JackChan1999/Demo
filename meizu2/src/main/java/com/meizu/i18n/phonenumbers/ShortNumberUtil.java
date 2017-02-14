package com.meizu.i18n.phonenumbers;

import com.meizu.i18n.phonenumbers.Phonemetadata.PhoneMetadata;
import java.util.regex.Pattern;

public class ShortNumberUtil {
    private final PhoneNumberUtil phoneUtil;

    public ShortNumberUtil() {
        this.phoneUtil = PhoneNumberUtil.getInstance();
    }

    ShortNumberUtil(PhoneNumberUtil phoneNumberUtil) {
        this.phoneUtil = phoneNumberUtil;
    }

    public boolean connectsToEmergencyNumber(String str, String str2) {
        return matchesEmergencyNumberHelper(str, str2, true);
    }

    public boolean isEmergencyNumber(String str, String str2) {
        return matchesEmergencyNumberHelper(str, str2, false);
    }

    private boolean matchesEmergencyNumberHelper(String str, String str2, boolean z) {
        Object extractPossibleNumber = PhoneNumberUtil.extractPossibleNumber(str);
        if (PhoneNumberUtil.PLUS_CHARS_PATTERN.matcher(extractPossibleNumber).lookingAt()) {
            return false;
        }
        PhoneMetadata metadataForRegion = this.phoneUtil.getMetadataForRegion(str2);
        if (metadataForRegion == null || !metadataForRegion.hasEmergency()) {
            return false;
        }
        Pattern compile = Pattern.compile(metadataForRegion.getEmergency().getNationalNumberPattern());
        CharSequence normalizeDigitsOnly = PhoneNumberUtil.normalizeDigitsOnly(extractPossibleNumber);
        return (!z || str2.equals("BR")) ? compile.matcher(normalizeDigitsOnly).matches() : compile.matcher(normalizeDigitsOnly).lookingAt();
    }
}
