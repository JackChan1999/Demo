package com.meizu.i18n.phonenumbers.geocoding;

import com.meizu.i18n.phonenumbers.PhoneNumberUtil;
import com.meizu.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType;
import com.meizu.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.ted.sdk.ivr.DialpadAction;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhoneNumberOfflineGeocoder {
    private static final Logger LOGGER = Logger.getLogger(PhoneNumberOfflineGeocoder.class.getName());
    private static final String MAPPING_DATA_DIRECTORY = "/com/meizu/i18n/phonenumbers/geocoding/data/";
    private static PhoneNumberOfflineGeocoder instance = null;
    private Map<String, AreaCodeMap> availablePhonePrefixMaps = new HashMap();
    private MappingFileProvider mappingFileProvider = new MappingFileProvider();
    private final String phonePrefixDataDirectory;
    private final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

    PhoneNumberOfflineGeocoder(String str) {
        this.phonePrefixDataDirectory = str;
        loadMappingFileProvider();
    }

    private void loadMappingFileProvider() {
        InputStream objectInputStream;
        IOException e;
        Throwable th;
        try {
            objectInputStream = new ObjectInputStream(PhoneNumberOfflineGeocoder.class.getResourceAsStream(this.phonePrefixDataDirectory + "config"));
            try {
                this.mappingFileProvider.readExternal(objectInputStream);
                close(objectInputStream);
            } catch (IOException e2) {
                e = e2;
                try {
                    LOGGER.log(Level.WARNING, e.toString());
                    close(objectInputStream);
                } catch (Throwable th2) {
                    th = th2;
                    close(objectInputStream);
                    throw th;
                }
            }
        } catch (IOException e3) {
            e = e3;
            objectInputStream = null;
            LOGGER.log(Level.WARNING, e.toString());
            close(objectInputStream);
        } catch (Throwable th3) {
            th = th3;
            objectInputStream = null;
            close(objectInputStream);
            throw th;
        }
    }

    private AreaCodeMap getPhonePrefixDescriptions(int i, String str, String str2, String str3) {
        String fileName = this.mappingFileProvider.getFileName(i, str, str2, str3);
        if (fileName.length() == 0) {
            return null;
        }
        if (!this.availablePhonePrefixMaps.containsKey(fileName)) {
            loadAreaCodeMapFromFile(fileName);
        }
        return (AreaCodeMap) this.availablePhonePrefixMaps.get(fileName);
    }

    private void loadAreaCodeMapFromFile(String str) {
        InputStream objectInputStream;
        IOException e;
        Throwable th;
        try {
            objectInputStream = new ObjectInputStream(PhoneNumberOfflineGeocoder.class.getResourceAsStream(this.phonePrefixDataDirectory + str));
            try {
                AreaCodeMap areaCodeMap = new AreaCodeMap();
                areaCodeMap.readExternal(objectInputStream);
                this.availablePhonePrefixMaps.put(str, areaCodeMap);
                close(objectInputStream);
            } catch (IOException e2) {
                e = e2;
                try {
                    LOGGER.log(Level.WARNING, e.toString());
                    close(objectInputStream);
                } catch (Throwable th2) {
                    th = th2;
                    close(objectInputStream);
                    throw th;
                }
            }
        } catch (IOException e3) {
            e = e3;
            objectInputStream = null;
            LOGGER.log(Level.WARNING, e.toString());
            close(objectInputStream);
        } catch (Throwable th3) {
            th = th3;
            objectInputStream = null;
            close(objectInputStream);
            throw th;
        }
    }

    private static void close(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, e.toString());
            }
        }
    }

    public static synchronized PhoneNumberOfflineGeocoder getInstance() {
        PhoneNumberOfflineGeocoder phoneNumberOfflineGeocoder;
        synchronized (PhoneNumberOfflineGeocoder.class) {
            if (instance == null) {
                instance = new PhoneNumberOfflineGeocoder(MAPPING_DATA_DIRECTORY);
            }
            phoneNumberOfflineGeocoder = instance;
        }
        return phoneNumberOfflineGeocoder;
    }

    private String getCountryNameForNumber(PhoneNumber phoneNumber, Locale locale) {
        return getRegionDisplayName(this.phoneUtil.getRegionCodeForNumber(phoneNumber), locale);
    }

    private String getRegionDisplayName(String str, Locale locale) {
        return (str == null || str.equals("ZZ") || str.equals(PhoneNumberUtil.REGION_CODE_FOR_NON_GEO_ENTITY)) ? "" : new Locale("", str).getDisplayCountry(locale);
    }

    public String getDescriptionForValidNumber(PhoneNumber phoneNumber, Locale locale) {
        String areaDescriptionForNumber = getAreaDescriptionForNumber(phoneNumber, locale.getLanguage(), "", locale.getCountry());
        return areaDescriptionForNumber.length() > 0 ? areaDescriptionForNumber : getCountryNameForNumber(phoneNumber, locale);
    }

    public String getDescriptionForValidNumber(PhoneNumber phoneNumber, Locale locale, String str) {
        return getDescriptionForValidNumber(phoneNumber, locale, str, false);
    }

    public String getDescriptionForValidNumber(PhoneNumber phoneNumber, Locale locale, String str, boolean z) {
        String regionCodeForNumber = this.phoneUtil.getRegionCodeForNumber(phoneNumber);
        if (str.equals(regionCodeForNumber)) {
            return getDescriptionForValidNumber(phoneNumber, locale);
        }
        if (z) {
            String language = locale.getLanguage();
            String areaDescriptionForNumber = getAreaDescriptionForNumber(phoneNumber, language, "", locale.getCountry());
            if (!(areaDescriptionForNumber == null || areaDescriptionForNumber.equals(""))) {
                String regionDisplayName = getRegionDisplayName(regionCodeForNumber, locale);
                if (!(regionDisplayName == null || regionDisplayName.equals(""))) {
                    if (language.equals("zh") || language.equals("ja") || language.equals("ko")) {
                        return regionDisplayName + " " + areaDescriptionForNumber;
                    }
                    return areaDescriptionForNumber + " " + regionDisplayName;
                }
            }
        }
        return getRegionDisplayName(regionCodeForNumber, locale);
    }

    public String getDescriptionForNumber(PhoneNumber phoneNumber, Locale locale) {
        PhoneNumberType numberType = this.phoneUtil.getNumberType(phoneNumber);
        if (numberType == PhoneNumberType.UNKNOWN) {
            return "";
        }
        if (canBeGeocoded(numberType)) {
            return getDescriptionForValidNumber(phoneNumber, locale);
        }
        return getCountryNameForNumber(phoneNumber, locale);
    }

    public String getDescriptionForNumber(PhoneNumber phoneNumber, Locale locale, String str) {
        PhoneNumberType numberType = this.phoneUtil.getNumberType(phoneNumber);
        if (numberType == PhoneNumberType.UNKNOWN) {
            return "";
        }
        if (canBeGeocoded(numberType)) {
            return getDescriptionForValidNumber(phoneNumber, locale, str);
        }
        return getCountryNameForNumber(phoneNumber, locale);
    }

    private boolean canBeGeocoded(PhoneNumberType phoneNumberType) {
        return phoneNumberType == PhoneNumberType.FIXED_LINE || phoneNumberType == PhoneNumberType.MOBILE || phoneNumberType == PhoneNumberType.FIXED_LINE_OR_MOBILE;
    }

    private String getAreaDescriptionForNumber(PhoneNumber phoneNumber, String str, String str2, String str3) {
        String valueOf;
        int countryCode = phoneNumber.getCountryCode();
        if (countryCode == 86) {
            valueOf = String.valueOf(phoneNumber.getNationalNumber());
            if (valueOf.length() < 7 || valueOf.charAt(0) != DialpadAction.ONE || valueOf.charAt(1) == DialpadAction.ZERO) {
                countryCode = 860;
            } else {
                countryCode = Integer.valueOf(valueOf.substring(0, 3)).intValue() + 86000;
            }
        } else if (countryCode == 1) {
            countryCode = ((int) (phoneNumber.getNationalNumber() / 10000000)) + 1000;
        }
        AreaCodeMap phonePrefixDescriptions = getPhonePrefixDescriptions(countryCode, str, str2, str3);
        String lookup = phonePrefixDescriptions != null ? phonePrefixDescriptions.lookup(phoneNumber) : null;
        if ((lookup == null || lookup.length() == 0) && mayFallBackToEnglish(str)) {
            AreaCodeMap phonePrefixDescriptions2 = getPhonePrefixDescriptions(countryCode, "en", "", "");
            if (phonePrefixDescriptions2 == null) {
                return "";
            }
            valueOf = phonePrefixDescriptions2.lookup(phoneNumber);
        } else {
            valueOf = lookup;
        }
        return valueOf == null ? "" : valueOf;
    }

    private boolean mayFallBackToEnglish(String str) {
        return (str.equals("zh") || str.equals("ja") || str.equals("ko")) ? false : true;
    }
}
