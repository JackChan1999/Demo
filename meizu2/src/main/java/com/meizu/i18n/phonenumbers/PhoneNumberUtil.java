package com.meizu.i18n.phonenumbers;

import com.meizu.common.util.LunarCalendar;
import com.meizu.common.widget.MzContactsContract.MzGroups;
import com.meizu.i18n.phonenumbers.NumberParseException.ErrorType;
import com.meizu.i18n.phonenumbers.Phonemetadata.NumberFormat;
import com.meizu.i18n.phonenumbers.Phonemetadata.PhoneMetadata;
import com.meizu.i18n.phonenumbers.Phonemetadata.PhoneMetadataCollection;
import com.meizu.i18n.phonenumbers.Phonemetadata.PhoneNumberDesc;
import com.meizu.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.meizu.i18n.phonenumbers.Phonenumber.PhoneNumber.CountryCodeSource;
import com.ted.sdk.ivr.DialpadAction;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberUtil {
    private static final Map<Character, Character> ALL_PLUS_NUMBER_GROUPING_SYMBOLS;
    private static final Map<Character, Character> ALPHA_MAPPINGS;
    private static final Map<Character, Character> ALPHA_PHONE_MAPPINGS;
    private static final Pattern CAPTURING_DIGIT_PATTERN = Pattern.compile("(\\p{Nd})");
    private static final String CAPTURING_EXTN_DIGITS = "(\\p{Nd}{1,7})";
    private static final Pattern CC_PATTERN = Pattern.compile("\\$CC");
    private static final String COLOMBIA_MOBILE_TO_FIXED_LINE_PREFIX = "3";
    private static final String DEFAULT_EXTN_PREFIX = " ext. ";
    private static final Map<Character, Character> DIALLABLE_CHAR_MAPPINGS;
    private static final String DIGITS = "\\p{Nd}";
    private static final Pattern EXTN_PATTERN = Pattern.compile("(?:" + EXTN_PATTERNS_FOR_PARSING + ")$", 66);
    static final String EXTN_PATTERNS_FOR_MATCHING;
    private static final String EXTN_PATTERNS_FOR_PARSING;
    private static final Pattern FG_PATTERN = Pattern.compile("\\$FG");
    private static final Pattern FIRST_GROUP_ONLY_PREFIX_PATTERN = Pattern.compile("\\(?\\$1\\)?");
    private static final Pattern FIRST_GROUP_PATTERN = Pattern.compile("(\\$\\d)");
    private static final Logger LOGGER = Logger.getLogger(PhoneNumberUtil.class.getName());
    private static final int MAX_INPUT_STRING_LENGTH = 250;
    static final int MAX_LENGTH_COUNTRY_CODE = 3;
    static final int MAX_LENGTH_FOR_NSN = 16;
    static final String META_DATA_FILE_PREFIX = "/com/meizu/i18n/phonenumbers/data/PhoneNumberMetadataProto";
    private static final int MIN_LENGTH_FOR_NSN = 2;
    private static final int NANPA_COUNTRY_CODE = 1;
    static final Pattern NON_DIGITS_PATTERN = Pattern.compile("(\\D+)");
    private static final Pattern NP_PATTERN = Pattern.compile("\\$NP");
    static final String PLUS_CHARS = "+＋";
    static final Pattern PLUS_CHARS_PATTERN = Pattern.compile("[+＋]+");
    static final char PLUS_SIGN = '+';
    static final int REGEX_FLAGS = 66;
    public static final String REGION_CODE_FOR_NON_GEO_ENTITY = "001";
    private static final String RFC3966_EXTN_PREFIX = ";ext=";
    private static final String RFC3966_ISDN_SUBADDRESS = ";isub=";
    private static final String RFC3966_PHONE_CONTEXT = ";phone-context=";
    private static final String RFC3966_PREFIX = "tel:";
    private static final String SECOND_NUMBER_START = "[\\\\/] *x";
    static final Pattern SECOND_NUMBER_START_PATTERN = Pattern.compile(SECOND_NUMBER_START);
    private static final Pattern SEPARATOR_PATTERN = Pattern.compile("[-x‐-―−ー－-／  ­​⁠　()（）［］.\\[\\]/~⁓∼～]+");
    private static final char STAR_SIGN = '*';
    private static final Pattern UNIQUE_INTERNATIONAL_PREFIX = Pattern.compile("[\\d]+(?:[~⁓∼～][\\d]+)?");
    private static final String UNKNOWN_REGION = "ZZ";
    private static final String UNWANTED_END_CHARS = "[[\\P{N}&&\\P{L}]&&[^#]]+$";
    static final Pattern UNWANTED_END_CHAR_PATTERN = Pattern.compile(UNWANTED_END_CHARS);
    private static final String VALID_ALPHA = (Arrays.toString(ALPHA_MAPPINGS.keySet().toArray()).replaceAll("[, \\[\\]]", "") + Arrays.toString(ALPHA_MAPPINGS.keySet().toArray()).toLowerCase().replaceAll("[, \\[\\]]", ""));
    private static final Pattern VALID_ALPHA_PHONE_PATTERN = Pattern.compile("(?:.*?[A-Za-z]){3}.*");
    private static final String VALID_PHONE_NUMBER = ("\\p{Nd}{2}|[+＋]*+(?:[-x‐-―−ー－-／  ­​⁠　()（）［］.\\[\\]/~⁓∼～*]*\\p{Nd}){3,}[-x‐-―−ー－-／  ­​⁠　()（）［］.\\[\\]/~⁓∼～*" + VALID_ALPHA + DIGITS + "]*");
    private static final Pattern VALID_PHONE_NUMBER_PATTERN = Pattern.compile(VALID_PHONE_NUMBER + "(?:" + EXTN_PATTERNS_FOR_PARSING + ")?", 66);
    static final String VALID_PUNCTUATION = "-x‐-―−ー－-／  ­​⁠　()（）［］.\\[\\]/~⁓∼～";
    private static final String VALID_START_CHAR = "[+＋\\p{Nd}]";
    private static final Pattern VALID_START_CHAR_PATTERN = Pattern.compile(VALID_START_CHAR);
    private static PhoneNumberUtil instance = null;
    private Map<Integer, List<String>> countryCallingCodeToRegionCodeMap = null;
    private final Map<Integer, PhoneMetadata> countryCodeToNonGeographicalMetadataMap = Collections.synchronizedMap(new HashMap());
    private final Set<Integer> countryCodesForNonGeographicalRegion = new HashSet();
    private String currentFilePrefix = META_DATA_FILE_PREFIX;
    private final Set<String> nanpaRegions = new HashSet(35);
    private RegexCache regexCache = new RegexCache(100);
    private final Map<String, PhoneMetadata> regionToMetadataMap = Collections.synchronizedMap(new HashMap());
    private final Set<String> supportedRegions = new HashSet(320);

    public enum Leniency {
        POSSIBLE {
            boolean verify(PhoneNumber phoneNumber, String str, PhoneNumberUtil phoneNumberUtil) {
                return phoneNumberUtil.isPossibleNumber(phoneNumber);
            }
        },
        VALID {
            boolean verify(PhoneNumber phoneNumber, String str, PhoneNumberUtil phoneNumberUtil) {
                if (phoneNumberUtil.isValidNumber(phoneNumber) && PhoneNumberMatcher.containsOnlyValidXChars(phoneNumber, str, phoneNumberUtil)) {
                    return PhoneNumberMatcher.isNationalPrefixPresentIfRequired(phoneNumber, phoneNumberUtil);
                }
                return false;
            }
        },
        STRICT_GROUPING {
            boolean verify(PhoneNumber phoneNumber, String str, PhoneNumberUtil phoneNumberUtil) {
                if (phoneNumberUtil.isValidNumber(phoneNumber) && PhoneNumberMatcher.containsOnlyValidXChars(phoneNumber, str, phoneNumberUtil) && !PhoneNumberMatcher.containsMoreThanOneSlash(str) && PhoneNumberMatcher.isNationalPrefixPresentIfRequired(phoneNumber, phoneNumberUtil)) {
                    return PhoneNumberMatcher.checkNumberGroupingIsValid(phoneNumber, str, phoneNumberUtil, new NumberGroupingChecker() {
                        public boolean checkGroups(PhoneNumberUtil phoneNumberUtil, PhoneNumber phoneNumber, StringBuilder stringBuilder, String[] strArr) {
                            return PhoneNumberMatcher.allNumberGroupsRemainGrouped(phoneNumberUtil, phoneNumber, stringBuilder, strArr);
                        }
                    });
                }
                return false;
            }
        },
        EXACT_GROUPING {
            boolean verify(PhoneNumber phoneNumber, String str, PhoneNumberUtil phoneNumberUtil) {
                if (phoneNumberUtil.isValidNumber(phoneNumber) && PhoneNumberMatcher.containsOnlyValidXChars(phoneNumber, str, phoneNumberUtil) && !PhoneNumberMatcher.containsMoreThanOneSlash(str) && PhoneNumberMatcher.isNationalPrefixPresentIfRequired(phoneNumber, phoneNumberUtil)) {
                    return PhoneNumberMatcher.checkNumberGroupingIsValid(phoneNumber, str, phoneNumberUtil, new NumberGroupingChecker() {
                        public boolean checkGroups(PhoneNumberUtil phoneNumberUtil, PhoneNumber phoneNumber, StringBuilder stringBuilder, String[] strArr) {
                            return PhoneNumberMatcher.allNumberGroupsAreExactlyPresent(phoneNumberUtil, phoneNumber, stringBuilder, strArr);
                        }
                    });
                }
                return false;
            }
        };

        abstract boolean verify(PhoneNumber phoneNumber, String str, PhoneNumberUtil phoneNumberUtil);
    }

    public enum MatchType {
        NOT_A_NUMBER,
        NO_MATCH,
        SHORT_NSN_MATCH,
        NSN_MATCH,
        EXACT_MATCH
    }

    public enum PhoneNumberFormat {
        E164,
        INTERNATIONAL,
        NATIONAL,
        RFC3966
    }

    public enum PhoneNumberType {
        FIXED_LINE,
        MOBILE,
        FIXED_LINE_OR_MOBILE,
        TOLL_FREE,
        PREMIUM_RATE,
        SHARED_COST,
        VOIP,
        PERSONAL_NUMBER,
        PAGER,
        UAN,
        VOICEMAIL,
        UNKNOWN
    }

    public enum ValidationResult {
        IS_POSSIBLE,
        INVALID_COUNTRY_CODE,
        TOO_SHORT,
        TOO_LONG
    }

    static {
        Map hashMap = new HashMap();
        hashMap.put(Character.valueOf(DialpadAction.ZERO), Character.valueOf(DialpadAction.ZERO));
        hashMap.put(Character.valueOf(DialpadAction.ONE), Character.valueOf(DialpadAction.ONE));
        hashMap.put(Character.valueOf(DialpadAction.TWO), Character.valueOf(DialpadAction.TWO));
        hashMap.put(Character.valueOf(DialpadAction.THREE), Character.valueOf(DialpadAction.THREE));
        hashMap.put(Character.valueOf(DialpadAction.FOUR), Character.valueOf(DialpadAction.FOUR));
        hashMap.put(Character.valueOf(DialpadAction.FIVE), Character.valueOf(DialpadAction.FIVE));
        hashMap.put(Character.valueOf(DialpadAction.SIX), Character.valueOf(DialpadAction.SIX));
        hashMap.put(Character.valueOf(DialpadAction.SEVEN), Character.valueOf(DialpadAction.SEVEN));
        hashMap.put(Character.valueOf(DialpadAction.EIGHT), Character.valueOf(DialpadAction.EIGHT));
        hashMap.put(Character.valueOf(DialpadAction.NINE), Character.valueOf(DialpadAction.NINE));
        Map hashMap2 = new HashMap(40);
        hashMap2.put(Character.valueOf('A'), Character.valueOf(DialpadAction.TWO));
        hashMap2.put(Character.valueOf('B'), Character.valueOf(DialpadAction.TWO));
        hashMap2.put(Character.valueOf('C'), Character.valueOf(DialpadAction.TWO));
        hashMap2.put(Character.valueOf('D'), Character.valueOf(DialpadAction.THREE));
        hashMap2.put(Character.valueOf('E'), Character.valueOf(DialpadAction.THREE));
        hashMap2.put(Character.valueOf('F'), Character.valueOf(DialpadAction.THREE));
        hashMap2.put(Character.valueOf('G'), Character.valueOf(DialpadAction.FOUR));
        hashMap2.put(Character.valueOf('H'), Character.valueOf(DialpadAction.FOUR));
        hashMap2.put(Character.valueOf('I'), Character.valueOf(DialpadAction.FOUR));
        hashMap2.put(Character.valueOf('J'), Character.valueOf(DialpadAction.FIVE));
        hashMap2.put(Character.valueOf('K'), Character.valueOf(DialpadAction.FIVE));
        hashMap2.put(Character.valueOf('L'), Character.valueOf(DialpadAction.FIVE));
        hashMap2.put(Character.valueOf('M'), Character.valueOf(DialpadAction.SIX));
        hashMap2.put(Character.valueOf('N'), Character.valueOf(DialpadAction.SIX));
        hashMap2.put(Character.valueOf('O'), Character.valueOf(DialpadAction.SIX));
        hashMap2.put(Character.valueOf('P'), Character.valueOf(DialpadAction.SEVEN));
        hashMap2.put(Character.valueOf('Q'), Character.valueOf(DialpadAction.SEVEN));
        hashMap2.put(Character.valueOf('R'), Character.valueOf(DialpadAction.SEVEN));
        hashMap2.put(Character.valueOf('S'), Character.valueOf(DialpadAction.SEVEN));
        hashMap2.put(Character.valueOf('T'), Character.valueOf(DialpadAction.EIGHT));
        hashMap2.put(Character.valueOf('U'), Character.valueOf(DialpadAction.EIGHT));
        hashMap2.put(Character.valueOf('V'), Character.valueOf(DialpadAction.EIGHT));
        hashMap2.put(Character.valueOf('W'), Character.valueOf(DialpadAction.NINE));
        hashMap2.put(Character.valueOf('X'), Character.valueOf(DialpadAction.NINE));
        hashMap2.put(Character.valueOf('Y'), Character.valueOf(DialpadAction.NINE));
        hashMap2.put(Character.valueOf('Z'), Character.valueOf(DialpadAction.NINE));
        ALPHA_MAPPINGS = Collections.unmodifiableMap(hashMap2);
        hashMap2 = new HashMap(100);
        hashMap2.putAll(ALPHA_MAPPINGS);
        hashMap2.putAll(hashMap);
        ALPHA_PHONE_MAPPINGS = Collections.unmodifiableMap(hashMap2);
        hashMap2 = new HashMap();
        hashMap2.putAll(hashMap);
        hashMap2.put(Character.valueOf(PLUS_SIGN), Character.valueOf(PLUS_SIGN));
        hashMap2.put(Character.valueOf('*'), Character.valueOf('*'));
        DIALLABLE_CHAR_MAPPINGS = Collections.unmodifiableMap(hashMap2);
        Map hashMap3 = new HashMap();
        for (Character charValue : ALPHA_MAPPINGS.keySet()) {
            char charValue2 = charValue.charValue();
            hashMap3.put(Character.valueOf(Character.toLowerCase(charValue2)), Character.valueOf(charValue2));
            hashMap3.put(Character.valueOf(charValue2), Character.valueOf(charValue2));
        }
        hashMap3.putAll(hashMap);
        hashMap3.put(Character.valueOf('-'), Character.valueOf('-'));
        hashMap3.put(Character.valueOf('－'), Character.valueOf('-'));
        hashMap3.put(Character.valueOf('‐'), Character.valueOf('-'));
        hashMap3.put(Character.valueOf('‑'), Character.valueOf('-'));
        hashMap3.put(Character.valueOf('‒'), Character.valueOf('-'));
        hashMap3.put(Character.valueOf('–'), Character.valueOf('-'));
        hashMap3.put(Character.valueOf('—'), Character.valueOf('-'));
        hashMap3.put(Character.valueOf('―'), Character.valueOf('-'));
        hashMap3.put(Character.valueOf('−'), Character.valueOf('-'));
        hashMap3.put(Character.valueOf('/'), Character.valueOf('/'));
        hashMap3.put(Character.valueOf('／'), Character.valueOf('/'));
        hashMap3.put(Character.valueOf(' '), Character.valueOf(' '));
        hashMap3.put(Character.valueOf('　'), Character.valueOf(' '));
        hashMap3.put(Character.valueOf('⁠'), Character.valueOf(' '));
        hashMap3.put(Character.valueOf('.'), Character.valueOf('.'));
        hashMap3.put(Character.valueOf('．'), Character.valueOf('.'));
        ALL_PLUS_NUMBER_GROUPING_SYMBOLS = Collections.unmodifiableMap(hashMap3);
        String str = "xｘ#＃~～";
        EXTN_PATTERNS_FOR_PARSING = createExtnPattern(MzGroups.GROUP_SPLIT_MARK_EXTRA + str);
        EXTN_PATTERNS_FOR_MATCHING = createExtnPattern(str);
    }

    private static String createExtnPattern(String str) {
        return ";ext=(\\p{Nd}{1,7})|[  \\t,]*(?:e?xt(?:ensi(?:ó?|ó))?n?|ｅ?ｘｔｎ?|[" + str + "]|int|anexo|ｉｎｔ)" + "[:\\.．]?[  \\t,-]*" + CAPTURING_EXTN_DIGITS + "#?|" + "[- ]+(" + DIGITS + "{1,5})#";
    }

    private PhoneNumberUtil() {
    }

    private void init(String str) {
        this.currentFilePrefix = str;
        for (Entry entry : this.countryCallingCodeToRegionCodeMap.entrySet()) {
            List list = (List) entry.getValue();
            if (list.size() == 1 && REGION_CODE_FOR_NON_GEO_ENTITY.equals(list.get(0))) {
                this.countryCodesForNonGeographicalRegion.add(entry.getKey());
            } else {
                this.supportedRegions.addAll(list);
            }
        }
        if (this.supportedRegions.remove(REGION_CODE_FOR_NON_GEO_ENTITY)) {
            LOGGER.log(Level.WARNING, "invalid metadata (country calling code was mapped to the non-geo entity as well as specific region(s))");
        }
        this.nanpaRegions.addAll((Collection) this.countryCallingCodeToRegionCodeMap.get(Integer.valueOf(1)));
    }

    void loadMetadataFromFile(String str, String str2, int i) {
        InputStream objectInputStream;
        Throwable e;
        boolean equals = REGION_CODE_FOR_NON_GEO_ENTITY.equals(str2);
        String str3 = str + "_" + (equals ? String.valueOf(i) : str2);
        InputStream resourceAsStream = PhoneNumberUtil.class.getResourceAsStream(str3);
        if (resourceAsStream == null) {
            LOGGER.log(Level.SEVERE, "missing metadata: " + str3);
            throw new RuntimeException("missing metadata: " + str3);
        }
        try {
            objectInputStream = new ObjectInputStream(resourceAsStream);
            try {
                PhoneMetadataCollection phoneMetadataCollection = new PhoneMetadataCollection();
                phoneMetadataCollection.readExternal(objectInputStream);
                List metadataList = phoneMetadataCollection.getMetadataList();
                if (metadataList.isEmpty()) {
                    LOGGER.log(Level.SEVERE, "empty metadata: " + str3);
                    throw new RuntimeException("empty metadata: " + str3);
                }
                if (metadataList.size() > 1) {
                    LOGGER.log(Level.WARNING, "invalid metadata (too many entries): " + str3);
                }
                PhoneMetadata phoneMetadata = (PhoneMetadata) metadataList.get(0);
                if (equals) {
                    this.countryCodeToNonGeographicalMetadataMap.put(Integer.valueOf(i), phoneMetadata);
                } else {
                    this.regionToMetadataMap.put(str2, phoneMetadata);
                }
                close(objectInputStream);
            } catch (IOException e2) {
                e = e2;
                try {
                    LOGGER.log(Level.SEVERE, "cannot load/parse metadata: " + str3, e);
                    throw new RuntimeException("cannot load/parse metadata: " + str3, e);
                } catch (Throwable th) {
                    e = th;
                    close(objectInputStream);
                    throw e;
                }
            }
        } catch (IOException e3) {
            e = e3;
            objectInputStream = null;
            LOGGER.log(Level.SEVERE, "cannot load/parse metadata: " + str3, e);
            throw new RuntimeException("cannot load/parse metadata: " + str3, e);
        } catch (Throwable th2) {
            e = th2;
            objectInputStream = null;
            close(objectInputStream);
            throw e;
        }
    }

    private static void close(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (Throwable e) {
                LOGGER.log(Level.WARNING, "error closing input stream (ignored)", e);
            }
        }
    }

    static String extractPossibleNumber(String str) {
        Matcher matcher = VALID_START_CHAR_PATTERN.matcher(str);
        if (!matcher.find()) {
            return "";
        }
        String substring = str.substring(matcher.start());
        Matcher matcher2 = UNWANTED_END_CHAR_PATTERN.matcher(substring);
        if (matcher2.find()) {
            substring = substring.substring(0, matcher2.start());
            LOGGER.log(Level.FINER, "Stripped trailing characters: " + substring);
        }
        matcher2 = SECOND_NUMBER_START_PATTERN.matcher(substring);
        if (matcher2.find()) {
            return substring.substring(0, matcher2.start());
        }
        return substring;
    }

    static boolean isViablePhoneNumber(String str) {
        if (str.length() < 2) {
            return false;
        }
        return VALID_PHONE_NUMBER_PATTERN.matcher(str).matches();
    }

    static String normalize(String str) {
        if (VALID_ALPHA_PHONE_PATTERN.matcher(str).matches()) {
            return normalizeHelper(str, ALPHA_PHONE_MAPPINGS, true);
        }
        return normalizeDigitsOnly(str);
    }

    static void normalize(StringBuilder stringBuilder) {
        stringBuilder.replace(0, stringBuilder.length(), normalize(stringBuilder.toString()));
    }

    public static String normalizeDigitsOnly(String str) {
        return normalizeDigits(str, false).toString();
    }

    static StringBuilder normalizeDigits(String str, boolean z) {
        StringBuilder stringBuilder = new StringBuilder(str.length());
        for (char c : str.toCharArray()) {
            int digit = Character.digit(c, 10);
            if (digit != -1) {
                stringBuilder.append(digit);
            } else if (z) {
                stringBuilder.append(c);
            }
        }
        return stringBuilder;
    }

    public static String convertAlphaCharactersInNumber(String str) {
        return normalizeHelper(str, ALPHA_PHONE_MAPPINGS, false);
    }

    public int getLengthOfGeographicalAreaCode(PhoneNumber phoneNumber) {
        PhoneMetadata metadataForRegion = getMetadataForRegion(getRegionCodeForNumber(phoneNumber));
        if (metadataForRegion == null) {
            return 0;
        }
        if ((metadataForRegion.hasNationalPrefix() || phoneNumber.isItalianLeadingZero()) && isNumberGeographical(phoneNumber)) {
            return getLengthOfNationalDestinationCode(phoneNumber);
        }
        return 0;
    }

    public int getLengthOfNationalDestinationCode(PhoneNumber phoneNumber) {
        PhoneNumber phoneNumber2;
        if (phoneNumber.hasExtension()) {
            phoneNumber2 = new PhoneNumber();
            phoneNumber2.mergeFrom(phoneNumber);
            phoneNumber2.clearExtension();
        } else {
            phoneNumber2 = phoneNumber;
        }
        String[] split = NON_DIGITS_PATTERN.split(format(phoneNumber2, PhoneNumberFormat.INTERNATIONAL));
        if (split.length <= 3) {
            return 0;
        }
        if (getRegionCodeForCountryCode(phoneNumber.getCountryCode()).equals("AR") && getNumberType(phoneNumber) == PhoneNumberType.MOBILE) {
            return split[3].length() + 1;
        }
        return split[2].length();
    }

    private static String normalizeHelper(String str, Map<Character, Character> map, boolean z) {
        StringBuilder stringBuilder = new StringBuilder(str.length());
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            Character ch = (Character) map.get(Character.valueOf(Character.toUpperCase(charAt)));
            if (ch != null) {
                stringBuilder.append(ch);
            } else if (!z) {
                stringBuilder.append(charAt);
            }
        }
        return stringBuilder.toString();
    }

    static synchronized PhoneNumberUtil getInstance(String str, Map<Integer, List<String>> map) {
        PhoneNumberUtil phoneNumberUtil;
        synchronized (PhoneNumberUtil.class) {
            if (instance == null) {
                instance = new PhoneNumberUtil();
                instance.countryCallingCodeToRegionCodeMap = map;
                instance.init(str);
            }
            phoneNumberUtil = instance;
        }
        return phoneNumberUtil;
    }

    static synchronized void resetInstance() {
        synchronized (PhoneNumberUtil.class) {
            instance = null;
        }
    }

    public Set<String> getSupportedRegions() {
        return Collections.unmodifiableSet(this.supportedRegions);
    }

    public Set<Integer> getSupportedGlobalNetworkCallingCodes() {
        return Collections.unmodifiableSet(this.countryCodesForNonGeographicalRegion);
    }

    public static synchronized PhoneNumberUtil getInstance() {
        PhoneNumberUtil instance;
        synchronized (PhoneNumberUtil.class) {
            if (instance == null) {
                instance = getInstance(META_DATA_FILE_PREFIX, CountryCodeToRegionCodeMap.getCountryCodeToRegionCodeMap());
            } else {
                instance = instance;
            }
        }
        return instance;
    }

    static boolean formattingRuleHasFirstGroupOnly(String str) {
        return FIRST_GROUP_ONLY_PREFIX_PATTERN.matcher(str).matches();
    }

    boolean isNumberGeographical(PhoneNumber phoneNumber) {
        PhoneNumberType numberType = getNumberType(phoneNumber);
        return numberType == PhoneNumberType.FIXED_LINE || numberType == PhoneNumberType.FIXED_LINE_OR_MOBILE;
    }

    private boolean isValidRegionCode(String str) {
        return str != null && this.supportedRegions.contains(str);
    }

    private boolean hasValidCountryCallingCode(int i) {
        return this.countryCallingCodeToRegionCodeMap.containsKey(Integer.valueOf(i));
    }

    public String format(PhoneNumber phoneNumber, PhoneNumberFormat phoneNumberFormat) {
        if (phoneNumber.getNationalNumber() == 0 && phoneNumber.hasRawInput()) {
            String rawInput = phoneNumber.getRawInput();
            if (rawInput.length() > 0) {
                return rawInput;
            }
        }
        StringBuilder stringBuilder = new StringBuilder(20);
        format(phoneNumber, phoneNumberFormat, stringBuilder);
        return stringBuilder.toString();
    }

    public void format(PhoneNumber phoneNumber, PhoneNumberFormat phoneNumberFormat, StringBuilder stringBuilder) {
        stringBuilder.setLength(0);
        int countryCode = phoneNumber.getCountryCode();
        String nationalSignificantNumber = getNationalSignificantNumber(phoneNumber);
        if (phoneNumberFormat == PhoneNumberFormat.E164) {
            stringBuilder.append(nationalSignificantNumber);
            prefixNumberWithCountryCallingCode(countryCode, PhoneNumberFormat.E164, stringBuilder);
        } else if (hasValidCountryCallingCode(countryCode)) {
            PhoneMetadata metadataForRegionOrCallingCode = getMetadataForRegionOrCallingCode(countryCode, getRegionCodeForCountryCode(countryCode));
            stringBuilder.append(formatNsn(nationalSignificantNumber, metadataForRegionOrCallingCode, phoneNumberFormat));
            maybeAppendFormattedExtension(phoneNumber, metadataForRegionOrCallingCode, phoneNumberFormat, stringBuilder);
            prefixNumberWithCountryCallingCode(countryCode, phoneNumberFormat, stringBuilder);
        } else {
            stringBuilder.append(nationalSignificantNumber);
        }
    }

    public String formatByPattern(PhoneNumber phoneNumber, PhoneNumberFormat phoneNumberFormat, List<NumberFormat> list) {
        int countryCode = phoneNumber.getCountryCode();
        String nationalSignificantNumber = getNationalSignificantNumber(phoneNumber);
        if (!hasValidCountryCallingCode(countryCode)) {
            return nationalSignificantNumber;
        }
        PhoneMetadata metadataForRegionOrCallingCode = getMetadataForRegionOrCallingCode(countryCode, getRegionCodeForCountryCode(countryCode));
        StringBuilder stringBuilder = new StringBuilder(20);
        NumberFormat chooseFormattingPatternForNumber = chooseFormattingPatternForNumber(list, nationalSignificantNumber);
        if (chooseFormattingPatternForNumber == null) {
            stringBuilder.append(nationalSignificantNumber);
        } else {
            NumberFormat numberFormat = new NumberFormat();
            numberFormat.mergeFrom(chooseFormattingPatternForNumber);
            CharSequence nationalPrefixFormattingRule = chooseFormattingPatternForNumber.getNationalPrefixFormattingRule();
            if (nationalPrefixFormattingRule.length() > 0) {
                String nationalPrefix = metadataForRegionOrCallingCode.getNationalPrefix();
                if (nationalPrefix.length() > 0) {
                    numberFormat.setNationalPrefixFormattingRule(FG_PATTERN.matcher(NP_PATTERN.matcher(nationalPrefixFormattingRule).replaceFirst(nationalPrefix)).replaceFirst("\\$1"));
                } else {
                    numberFormat.clearNationalPrefixFormattingRule();
                }
            }
            stringBuilder.append(formatNsnUsingPattern(nationalSignificantNumber, numberFormat, phoneNumberFormat));
        }
        maybeAppendFormattedExtension(phoneNumber, metadataForRegionOrCallingCode, phoneNumberFormat, stringBuilder);
        prefixNumberWithCountryCallingCode(countryCode, phoneNumberFormat, stringBuilder);
        return stringBuilder.toString();
    }

    public String formatNationalNumberWithCarrierCode(PhoneNumber phoneNumber, String str) {
        int countryCode = phoneNumber.getCountryCode();
        String nationalSignificantNumber = getNationalSignificantNumber(phoneNumber);
        if (!hasValidCountryCallingCode(countryCode)) {
            return nationalSignificantNumber;
        }
        PhoneMetadata metadataForRegionOrCallingCode = getMetadataForRegionOrCallingCode(countryCode, getRegionCodeForCountryCode(countryCode));
        StringBuilder stringBuilder = new StringBuilder(20);
        stringBuilder.append(formatNsn(nationalSignificantNumber, metadataForRegionOrCallingCode, PhoneNumberFormat.NATIONAL, str));
        maybeAppendFormattedExtension(phoneNumber, metadataForRegionOrCallingCode, PhoneNumberFormat.NATIONAL, stringBuilder);
        prefixNumberWithCountryCallingCode(countryCode, PhoneNumberFormat.NATIONAL, stringBuilder);
        return stringBuilder.toString();
    }

    private PhoneMetadata getMetadataForRegionOrCallingCode(int i, String str) {
        return REGION_CODE_FOR_NON_GEO_ENTITY.equals(str) ? getMetadataForNonGeographicalRegion(i) : getMetadataForRegion(str);
    }

    public String formatNationalNumberWithPreferredCarrierCode(PhoneNumber phoneNumber, String str) {
        if (phoneNumber.hasPreferredDomesticCarrierCode()) {
            str = phoneNumber.getPreferredDomesticCarrierCode();
        }
        return formatNationalNumberWithCarrierCode(phoneNumber, str);
    }

    public String formatNumberForMobileDialing(PhoneNumber phoneNumber, String str, boolean z) {
        int countryCode = phoneNumber.getCountryCode();
        if (hasValidCountryCallingCode(countryCode)) {
            String str2 = "";
            PhoneNumber clearExtension = new PhoneNumber().mergeFrom(phoneNumber).clearExtension();
            String regionCodeForCountryCode = getRegionCodeForCountryCode(countryCode);
            if (str.equals(regionCodeForCountryCode)) {
                PhoneNumberType numberType = getNumberType(clearExtension);
                boolean z2 = numberType == PhoneNumberType.FIXED_LINE || numberType == PhoneNumberType.MOBILE || numberType == PhoneNumberType.FIXED_LINE_OR_MOBILE;
                str2 = (regionCodeForCountryCode.equals("CO") && numberType == PhoneNumberType.FIXED_LINE) ? formatNationalNumberWithCarrierCode(clearExtension, COLOMBIA_MOBILE_TO_FIXED_LINE_PREFIX) : (regionCodeForCountryCode.equals("BR") && z2) ? clearExtension.hasPreferredDomesticCarrierCode() ? formatNationalNumberWithPreferredCarrierCode(clearExtension, "") : "" : ((countryCode == 1 || regionCodeForCountryCode.equals(REGION_CODE_FOR_NON_GEO_ENTITY) || (regionCodeForCountryCode.equals("MX") && z2)) && canBeInternationallyDialled(clearExtension)) ? format(clearExtension, PhoneNumberFormat.INTERNATIONAL) : format(clearExtension, PhoneNumberFormat.NATIONAL);
            } else if (canBeInternationallyDialled(clearExtension)) {
                return z ? format(clearExtension, PhoneNumberFormat.INTERNATIONAL) : format(clearExtension, PhoneNumberFormat.E164);
            }
            if (z) {
                return str2;
            }
            return normalizeHelper(str2, DIALLABLE_CHAR_MAPPINGS, true);
        } else if (phoneNumber.hasRawInput()) {
            return phoneNumber.getRawInput();
        } else {
            return "";
        }
    }

    public String formatOutOfCountryCallingNumber(PhoneNumber phoneNumber, String str) {
        if (isValidRegionCode(str)) {
            int countryCode = phoneNumber.getCountryCode();
            String nationalSignificantNumber = getNationalSignificantNumber(phoneNumber);
            if (!hasValidCountryCallingCode(countryCode)) {
                return nationalSignificantNumber;
            }
            if (countryCode == 1) {
                if (isNANPACountry(str)) {
                    return countryCode + " " + format(phoneNumber, PhoneNumberFormat.NATIONAL);
                }
            } else if (countryCode == getCountryCodeForValidRegion(str)) {
                return format(phoneNumber, PhoneNumberFormat.NATIONAL);
            }
            PhoneMetadata metadataForRegion = getMetadataForRegion(str);
            String internationalPrefix = metadataForRegion.getInternationalPrefix();
            String str2 = "";
            if (!UNIQUE_INTERNATIONAL_PREFIX.matcher(internationalPrefix).matches()) {
                if (metadataForRegion.hasPreferredInternationalPrefix()) {
                    internationalPrefix = metadataForRegion.getPreferredInternationalPrefix();
                } else {
                    internationalPrefix = str2;
                }
            }
            PhoneMetadata metadataForRegionOrCallingCode = getMetadataForRegionOrCallingCode(countryCode, getRegionCodeForCountryCode(countryCode));
            StringBuilder stringBuilder = new StringBuilder(formatNsn(nationalSignificantNumber, metadataForRegionOrCallingCode, PhoneNumberFormat.INTERNATIONAL));
            maybeAppendFormattedExtension(phoneNumber, metadataForRegionOrCallingCode, PhoneNumberFormat.INTERNATIONAL, stringBuilder);
            if (internationalPrefix.length() > 0) {
                stringBuilder.insert(0, " ").insert(0, countryCode).insert(0, " ").insert(0, internationalPrefix);
            } else {
                prefixNumberWithCountryCallingCode(countryCode, PhoneNumberFormat.INTERNATIONAL, stringBuilder);
            }
            return stringBuilder.toString();
        }
        LOGGER.log(Level.WARNING, "Trying to format number from invalid region " + str + ". International formatting applied.");
        return format(phoneNumber, PhoneNumberFormat.INTERNATIONAL);
    }

    public String formatInOriginalFormat(PhoneNumber phoneNumber, String str) {
        if (phoneNumber.hasRawInput() && (hasUnexpectedItalianLeadingZero(phoneNumber) || !hasFormattingPatternForNumber(phoneNumber))) {
            return phoneNumber.getRawInput();
        }
        if (!phoneNumber.hasCountryCodeSource()) {
            return format(phoneNumber, PhoneNumberFormat.NATIONAL);
        }
        String format;
        String regionCodeForCountryCode;
        switch (phoneNumber.getCountryCodeSource()) {
            case FROM_NUMBER_WITH_PLUS_SIGN:
                format = format(phoneNumber, PhoneNumberFormat.INTERNATIONAL);
                break;
            case FROM_NUMBER_WITH_IDD:
                format = formatOutOfCountryCallingNumber(phoneNumber, str);
                break;
            case FROM_NUMBER_WITHOUT_PLUS_SIGN:
                format = format(phoneNumber, PhoneNumberFormat.INTERNATIONAL).substring(1);
                break;
            default:
                regionCodeForCountryCode = getRegionCodeForCountryCode(phoneNumber.getCountryCode());
                String nddPrefixForRegion = getNddPrefixForRegion(regionCodeForCountryCode, true);
                format = format(phoneNumber, PhoneNumberFormat.NATIONAL);
                if (!(nddPrefixForRegion == null || nddPrefixForRegion.length() == 0 || rawInputContainsNationalPrefix(phoneNumber.getRawInput(), nddPrefixForRegion, regionCodeForCountryCode))) {
                    PhoneMetadata metadataForRegion = getMetadataForRegion(regionCodeForCountryCode);
                    NumberFormat chooseFormattingPatternForNumber = chooseFormattingPatternForNumber(metadataForRegion.numberFormats(), getNationalSignificantNumber(phoneNumber));
                    if (chooseFormattingPatternForNumber != null) {
                        nddPrefixForRegion = chooseFormattingPatternForNumber.getNationalPrefixFormattingRule();
                        int indexOf = nddPrefixForRegion.indexOf("$1");
                        if (indexOf > 0 && normalizeDigitsOnly(nddPrefixForRegion.substring(0, indexOf)).length() != 0) {
                            NumberFormat numberFormat = new NumberFormat();
                            numberFormat.mergeFrom(chooseFormattingPatternForNumber);
                            numberFormat.clearNationalPrefixFormattingRule();
                            List arrayList = new ArrayList(1);
                            arrayList.add(numberFormat);
                            format = formatByPattern(phoneNumber, PhoneNumberFormat.NATIONAL, arrayList);
                            break;
                        }
                    }
                }
                break;
        }
        regionCodeForCountryCode = phoneNumber.getRawInput();
        if (format == null || regionCodeForCountryCode.length() <= 0 || normalizeHelper(format, DIALLABLE_CHAR_MAPPINGS, true).equals(normalizeHelper(regionCodeForCountryCode, DIALLABLE_CHAR_MAPPINGS, true))) {
            return format;
        }
        return regionCodeForCountryCode;
    }

    private boolean rawInputContainsNationalPrefix(String str, String str2, String str3) {
        boolean z = false;
        String normalizeDigitsOnly = normalizeDigitsOnly(str);
        if (normalizeDigitsOnly.startsWith(str2)) {
            try {
                z = isValidNumber(parse(normalizeDigitsOnly.substring(str2.length()), str3));
            } catch (NumberParseException e) {
            }
        }
        return z;
    }

    private boolean hasUnexpectedItalianLeadingZero(PhoneNumber phoneNumber) {
        return phoneNumber.isItalianLeadingZero() && !isLeadingZeroPossible(phoneNumber.getCountryCode());
    }

    private boolean hasFormattingPatternForNumber(PhoneNumber phoneNumber) {
        int countryCode = phoneNumber.getCountryCode();
        PhoneMetadata metadataForRegionOrCallingCode = getMetadataForRegionOrCallingCode(countryCode, getRegionCodeForCountryCode(countryCode));
        if (metadataForRegionOrCallingCode == null) {
            return false;
        }
        if (chooseFormattingPatternForNumber(metadataForRegionOrCallingCode.numberFormats(), getNationalSignificantNumber(phoneNumber)) != null) {
            return true;
        }
        return false;
    }

    public String formatOutOfCountryKeepingAlphaChars(PhoneNumber phoneNumber, String str) {
        String rawInput = phoneNumber.getRawInput();
        if (rawInput.length() == 0) {
            return formatOutOfCountryCallingNumber(phoneNumber, str);
        }
        int countryCode = phoneNumber.getCountryCode();
        if (!hasValidCountryCallingCode(countryCode)) {
            return rawInput;
        }
        rawInput = normalizeHelper(rawInput, ALL_PLUS_NUMBER_GROUPING_SYMBOLS, true);
        String nationalSignificantNumber = getNationalSignificantNumber(phoneNumber);
        if (nationalSignificantNumber.length() > 3) {
            int indexOf = rawInput.indexOf(nationalSignificantNumber.substring(0, 3));
            if (indexOf != -1) {
                rawInput = rawInput.substring(indexOf);
            }
        }
        PhoneMetadata metadataForRegion = getMetadataForRegion(str);
        if (countryCode == 1) {
            if (isNANPACountry(str)) {
                return countryCode + " " + rawInput;
            }
        } else if (metadataForRegion != null && countryCode == getCountryCodeForValidRegion(str)) {
            NumberFormat chooseFormattingPatternForNumber = chooseFormattingPatternForNumber(metadataForRegion.numberFormats(), nationalSignificantNumber);
            if (chooseFormattingPatternForNumber == null) {
                return rawInput;
            }
            NumberFormat numberFormat = new NumberFormat();
            numberFormat.mergeFrom(chooseFormattingPatternForNumber);
            numberFormat.setPattern("(\\d+)(.*)");
            numberFormat.setFormat("$1$2");
            return formatNsnUsingPattern(rawInput, numberFormat, PhoneNumberFormat.NATIONAL);
        }
        nationalSignificantNumber = "";
        if (metadataForRegion != null) {
            nationalSignificantNumber = metadataForRegion.getInternationalPrefix();
            if (!UNIQUE_INTERNATIONAL_PREFIX.matcher(nationalSignificantNumber).matches()) {
                nationalSignificantNumber = metadataForRegion.getPreferredInternationalPrefix();
            }
        }
        StringBuilder stringBuilder = new StringBuilder(rawInput);
        maybeAppendFormattedExtension(phoneNumber, getMetadataForRegionOrCallingCode(countryCode, getRegionCodeForCountryCode(countryCode)), PhoneNumberFormat.INTERNATIONAL, stringBuilder);
        if (nationalSignificantNumber.length() > 0) {
            stringBuilder.insert(0, " ").insert(0, countryCode).insert(0, " ").insert(0, nationalSignificantNumber);
        } else {
            LOGGER.log(Level.WARNING, "Trying to format number from invalid region " + str + ". International formatting applied.");
            prefixNumberWithCountryCallingCode(countryCode, PhoneNumberFormat.INTERNATIONAL, stringBuilder);
        }
        return stringBuilder.toString();
    }

    public String getNationalSignificantNumber(PhoneNumber phoneNumber) {
        StringBuilder stringBuilder = new StringBuilder(phoneNumber.isItalianLeadingZero() ? "0" : "");
        stringBuilder.append(phoneNumber.getNationalNumber());
        return stringBuilder.toString();
    }

    private void prefixNumberWithCountryCallingCode(int i, PhoneNumberFormat phoneNumberFormat, StringBuilder stringBuilder) {
        switch (phoneNumberFormat) {
            case E164:
                stringBuilder.insert(0, i).insert(0, PLUS_SIGN);
                return;
            case INTERNATIONAL:
                stringBuilder.insert(0, " ").insert(0, i).insert(0, PLUS_SIGN);
                return;
            case RFC3966:
                stringBuilder.insert(0, LunarCalendar.DATE_SEPARATOR).insert(0, i).insert(0, PLUS_SIGN).insert(0, RFC3966_PREFIX);
                return;
            default:
                return;
        }
    }

    private String formatNsn(String str, PhoneMetadata phoneMetadata, PhoneNumberFormat phoneNumberFormat) {
        return formatNsn(str, phoneMetadata, phoneNumberFormat, null);
    }

    private String formatNsn(String str, PhoneMetadata phoneMetadata, PhoneNumberFormat phoneNumberFormat, String str2) {
        List numberFormats = (phoneMetadata.intlNumberFormats().size() == 0 || phoneNumberFormat == PhoneNumberFormat.NATIONAL) ? phoneMetadata.numberFormats() : phoneMetadata.intlNumberFormats();
        NumberFormat chooseFormattingPatternForNumber = chooseFormattingPatternForNumber(numberFormats, str);
        if (chooseFormattingPatternForNumber == null) {
            return str;
        }
        return formatNsnUsingPattern(str, chooseFormattingPatternForNumber, phoneNumberFormat, str2);
    }

    NumberFormat chooseFormattingPatternForNumber(List<NumberFormat> list, String str) {
        for (NumberFormat numberFormat : list) {
            int leadingDigitsPatternSize = numberFormat.leadingDigitsPatternSize();
            if ((leadingDigitsPatternSize == 0 || this.regexCache.getPatternForRegex(numberFormat.getLeadingDigitsPattern(leadingDigitsPatternSize - 1)).matcher(str).lookingAt()) && this.regexCache.getPatternForRegex(numberFormat.getPattern()).matcher(str).matches()) {
                return numberFormat;
            }
        }
        return null;
    }

    String formatNsnUsingPattern(String str, NumberFormat numberFormat, PhoneNumberFormat phoneNumberFormat) {
        return formatNsnUsingPattern(str, numberFormat, phoneNumberFormat, null);
    }

    private String formatNsnUsingPattern(String str, NumberFormat numberFormat, PhoneNumberFormat phoneNumberFormat, String str2) {
        CharSequence replaceAll;
        Object format = numberFormat.getFormat();
        Matcher matcher = this.regexCache.getPatternForRegex(numberFormat.getPattern()).matcher(str);
        String str3 = "";
        if (phoneNumberFormat != PhoneNumberFormat.NATIONAL || str2 == null || str2.length() <= 0 || numberFormat.getDomesticCarrierCodeFormattingRule().length() <= 0) {
            str3 = numberFormat.getNationalPrefixFormattingRule();
            if (phoneNumberFormat != PhoneNumberFormat.NATIONAL || str3 == null || str3.length() <= 0) {
                replaceAll = matcher.replaceAll(format);
            } else {
                replaceAll = matcher.replaceAll(FIRST_GROUP_PATTERN.matcher(format).replaceFirst(str3));
            }
        } else {
            replaceAll = matcher.replaceAll(FIRST_GROUP_PATTERN.matcher(format).replaceFirst(CC_PATTERN.matcher(numberFormat.getDomesticCarrierCodeFormattingRule()).replaceFirst(str2)));
        }
        if (phoneNumberFormat != PhoneNumberFormat.RFC3966) {
            return replaceAll;
        }
        matcher = SEPARATOR_PATTERN.matcher(replaceAll);
        if (matcher.lookingAt()) {
            replaceAll = matcher.replaceFirst("");
        }
        return matcher.reset(replaceAll).replaceAll(LunarCalendar.DATE_SEPARATOR);
    }

    public PhoneNumber getExampleNumber(String str) {
        return getExampleNumberForType(str, PhoneNumberType.FIXED_LINE);
    }

    public PhoneNumber getExampleNumberForType(String str, PhoneNumberType phoneNumberType) {
        PhoneNumber phoneNumber = null;
        if (isValidRegionCode(str)) {
            PhoneNumberDesc numberDescByType = getNumberDescByType(getMetadataForRegion(str), phoneNumberType);
            try {
                if (numberDescByType.hasExampleNumber()) {
                    phoneNumber = parse(numberDescByType.getExampleNumber(), str);
                }
            } catch (NumberParseException e) {
                LOGGER.log(Level.SEVERE, e.toString());
            }
        } else {
            LOGGER.log(Level.WARNING, "Invalid or unknown region code provided: " + str);
        }
        return phoneNumber;
    }

    public PhoneNumber getExampleNumberForNonGeoEntity(int i) {
        PhoneMetadata metadataForNonGeographicalRegion = getMetadataForNonGeographicalRegion(i);
        if (metadataForNonGeographicalRegion != null) {
            PhoneNumberDesc generalDesc = metadataForNonGeographicalRegion.getGeneralDesc();
            try {
                if (generalDesc.hasExampleNumber()) {
                    return parse("+" + i + generalDesc.getExampleNumber(), UNKNOWN_REGION);
                }
            } catch (NumberParseException e) {
                LOGGER.log(Level.SEVERE, e.toString());
            }
        } else {
            LOGGER.log(Level.WARNING, "Invalid or unknown country calling code provided: " + i);
        }
        return null;
    }

    private void maybeAppendFormattedExtension(PhoneNumber phoneNumber, PhoneMetadata phoneMetadata, PhoneNumberFormat phoneNumberFormat, StringBuilder stringBuilder) {
        if (phoneNumber.hasExtension() && phoneNumber.getExtension().length() > 0) {
            if (phoneNumberFormat == PhoneNumberFormat.RFC3966) {
                stringBuilder.append(RFC3966_EXTN_PREFIX).append(phoneNumber.getExtension());
            } else if (phoneMetadata.hasPreferredExtnPrefix()) {
                stringBuilder.append(phoneMetadata.getPreferredExtnPrefix()).append(phoneNumber.getExtension());
            } else {
                stringBuilder.append(DEFAULT_EXTN_PREFIX).append(phoneNumber.getExtension());
            }
        }
    }

    PhoneNumberDesc getNumberDescByType(PhoneMetadata phoneMetadata, PhoneNumberType phoneNumberType) {
        switch (phoneNumberType) {
            case PREMIUM_RATE:
                return phoneMetadata.getPremiumRate();
            case TOLL_FREE:
                return phoneMetadata.getTollFree();
            case MOBILE:
                return phoneMetadata.getMobile();
            case FIXED_LINE:
            case FIXED_LINE_OR_MOBILE:
                return phoneMetadata.getFixedLine();
            case SHARED_COST:
                return phoneMetadata.getSharedCost();
            case VOIP:
                return phoneMetadata.getVoip();
            case PERSONAL_NUMBER:
                return phoneMetadata.getPersonalNumber();
            case PAGER:
                return phoneMetadata.getPager();
            case UAN:
                return phoneMetadata.getUan();
            case VOICEMAIL:
                return phoneMetadata.getVoicemail();
            default:
                return phoneMetadata.getGeneralDesc();
        }
    }

    public PhoneNumberType getNumberType(PhoneNumber phoneNumber) {
        PhoneMetadata metadataForRegionOrCallingCode = getMetadataForRegionOrCallingCode(phoneNumber.getCountryCode(), getRegionCodeForNumber(phoneNumber));
        if (metadataForRegionOrCallingCode == null) {
            return PhoneNumberType.UNKNOWN;
        }
        return getNumberTypeHelper(getNationalSignificantNumber(phoneNumber), metadataForRegionOrCallingCode);
    }

    private PhoneNumberType getNumberTypeHelper(String str, PhoneMetadata phoneMetadata) {
        PhoneNumberDesc generalDesc = phoneMetadata.getGeneralDesc();
        if (!generalDesc.hasNationalNumberPattern() || !isNumberMatchingDesc(str, generalDesc)) {
            return PhoneNumberType.UNKNOWN;
        }
        if (isNumberMatchingDesc(str, phoneMetadata.getPremiumRate())) {
            return PhoneNumberType.PREMIUM_RATE;
        }
        if (isNumberMatchingDesc(str, phoneMetadata.getTollFree())) {
            return PhoneNumberType.TOLL_FREE;
        }
        if (isNumberMatchingDesc(str, phoneMetadata.getSharedCost())) {
            return PhoneNumberType.SHARED_COST;
        }
        if (isNumberMatchingDesc(str, phoneMetadata.getVoip())) {
            return PhoneNumberType.VOIP;
        }
        if (isNumberMatchingDesc(str, phoneMetadata.getPersonalNumber())) {
            return PhoneNumberType.PERSONAL_NUMBER;
        }
        if (isNumberMatchingDesc(str, phoneMetadata.getPager())) {
            return PhoneNumberType.PAGER;
        }
        if (isNumberMatchingDesc(str, phoneMetadata.getUan())) {
            return PhoneNumberType.UAN;
        }
        if (isNumberMatchingDesc(str, phoneMetadata.getVoicemail())) {
            return PhoneNumberType.VOICEMAIL;
        }
        if (isNumberMatchingDesc(str, phoneMetadata.getFixedLine())) {
            if (phoneMetadata.isSameMobileAndFixedLinePattern()) {
                return PhoneNumberType.FIXED_LINE_OR_MOBILE;
            }
            if (isNumberMatchingDesc(str, phoneMetadata.getMobile())) {
                return PhoneNumberType.FIXED_LINE_OR_MOBILE;
            }
            return PhoneNumberType.FIXED_LINE;
        } else if (phoneMetadata.isSameMobileAndFixedLinePattern() || !isNumberMatchingDesc(str, phoneMetadata.getMobile())) {
            return PhoneNumberType.UNKNOWN;
        } else {
            return PhoneNumberType.MOBILE;
        }
    }

    PhoneMetadata getMetadataForRegion(String str) {
        if (!isValidRegionCode(str)) {
            return null;
        }
        synchronized (this.regionToMetadataMap) {
            if (!this.regionToMetadataMap.containsKey(str)) {
                loadMetadataFromFile(this.currentFilePrefix, str, 0);
            }
        }
        return (PhoneMetadata) this.regionToMetadataMap.get(str);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    PhoneMetadata getMetadataForNonGeographicalRegion(int r4) {
        /*
        r3 = this;
        r1 = r3.countryCodeToNonGeographicalMetadataMap;
        monitor-enter(r1);
        r0 = r3.countryCallingCodeToRegionCodeMap;	 Catch:{ all -> 0x0033 }
        r2 = java.lang.Integer.valueOf(r4);	 Catch:{ all -> 0x0033 }
        r0 = r0.containsKey(r2);	 Catch:{ all -> 0x0033 }
        if (r0 != 0) goto L_0x0012;
    L_0x000f:
        r0 = 0;
        monitor-exit(r1);	 Catch:{ all -> 0x0033 }
    L_0x0011:
        return r0;
    L_0x0012:
        r0 = r3.countryCodeToNonGeographicalMetadataMap;	 Catch:{ all -> 0x0033 }
        r2 = java.lang.Integer.valueOf(r4);	 Catch:{ all -> 0x0033 }
        r0 = r0.containsKey(r2);	 Catch:{ all -> 0x0033 }
        if (r0 != 0) goto L_0x0025;
    L_0x001e:
        r0 = r3.currentFilePrefix;	 Catch:{ all -> 0x0033 }
        r2 = "001";
        r3.loadMetadataFromFile(r0, r2, r4);	 Catch:{ all -> 0x0033 }
    L_0x0025:
        monitor-exit(r1);	 Catch:{ all -> 0x0033 }
        r0 = r3.countryCodeToNonGeographicalMetadataMap;
        r1 = java.lang.Integer.valueOf(r4);
        r0 = r0.get(r1);
        r0 = (com.meizu.i18n.phonenumbers.Phonemetadata.PhoneMetadata) r0;
        goto L_0x0011;
    L_0x0033:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0033 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.i18n.phonenumbers.PhoneNumberUtil.getMetadataForNonGeographicalRegion(int):com.meizu.i18n.phonenumbers.Phonemetadata$PhoneMetadata");
    }

    private boolean isNumberMatchingDesc(String str, PhoneNumberDesc phoneNumberDesc) {
        return this.regexCache.getPatternForRegex(phoneNumberDesc.getPossibleNumberPattern()).matcher(str).matches() && this.regexCache.getPatternForRegex(phoneNumberDesc.getNationalNumberPattern()).matcher(str).matches();
    }

    public boolean isValidNumber(PhoneNumber phoneNumber) {
        return isValidNumberForRegion(phoneNumber, getRegionCodeForNumber(phoneNumber));
    }

    public boolean isValidNumberForRegion(PhoneNumber phoneNumber, String str) {
        int countryCode = phoneNumber.getCountryCode();
        PhoneMetadata metadataForRegionOrCallingCode = getMetadataForRegionOrCallingCode(countryCode, str);
        if (metadataForRegionOrCallingCode == null || (!REGION_CODE_FOR_NON_GEO_ENTITY.equals(str) && countryCode != getCountryCodeForValidRegion(str))) {
            return false;
        }
        PhoneNumberDesc generalDesc = metadataForRegionOrCallingCode.getGeneralDesc();
        String nationalSignificantNumber = getNationalSignificantNumber(phoneNumber);
        if (!generalDesc.hasNationalNumberPattern()) {
            countryCode = nationalSignificantNumber.length();
            if (countryCode <= 2 || countryCode > 16) {
                return false;
            }
            return true;
        } else if (getNumberTypeHelper(nationalSignificantNumber, metadataForRegionOrCallingCode) == PhoneNumberType.UNKNOWN) {
            return false;
        } else {
            return true;
        }
    }

    public String getRegionCodeForNumber(PhoneNumber phoneNumber) {
        int countryCode = phoneNumber.getCountryCode();
        List list = (List) this.countryCallingCodeToRegionCodeMap.get(Integer.valueOf(countryCode));
        if (list == null) {
            LOGGER.log(Level.WARNING, "Missing/invalid country_code (" + countryCode + ") for number " + getNationalSignificantNumber(phoneNumber));
            return null;
        } else if (list.size() == 1) {
            return (String) list.get(0);
        } else {
            return getRegionCodeForNumberFromRegionList(phoneNumber, list);
        }
    }

    private String getRegionCodeForNumberFromRegionList(PhoneNumber phoneNumber, List<String> list) {
        Object nationalSignificantNumber = getNationalSignificantNumber(phoneNumber);
        for (String str : list) {
            PhoneMetadata metadataForRegion = getMetadataForRegion(str);
            if (metadataForRegion.hasLeadingDigits()) {
                if (this.regexCache.getPatternForRegex(metadataForRegion.getLeadingDigits()).matcher(nationalSignificantNumber).lookingAt()) {
                    return str;
                }
            } else if (getNumberTypeHelper(nationalSignificantNumber, metadataForRegion) != PhoneNumberType.UNKNOWN) {
                return str;
            }
        }
        return null;
    }

    public String getRegionCodeForCountryCode(int i) {
        List list = (List) this.countryCallingCodeToRegionCodeMap.get(Integer.valueOf(i));
        return list == null ? UNKNOWN_REGION : (String) list.get(0);
    }

    public List<String> getRegionCodesForCountryCode(int i) {
        List list = (List) this.countryCallingCodeToRegionCodeMap.get(Integer.valueOf(i));
        if (list == null) {
            list = new ArrayList(0);
        }
        return Collections.unmodifiableList(list);
    }

    public int getCountryCodeForRegion(String str) {
        if (isValidRegionCode(str)) {
            return getCountryCodeForValidRegion(str);
        }
        Logger logger = LOGGER;
        Level level = Level.WARNING;
        StringBuilder append = new StringBuilder().append("Invalid or missing region code (");
        if (str == null) {
            str = "null";
        }
        logger.log(level, append.append(str).append(") provided.").toString());
        return 0;
    }

    private int getCountryCodeForValidRegion(String str) {
        PhoneMetadata metadataForRegion = getMetadataForRegion(str);
        if (metadataForRegion != null) {
            return metadataForRegion.getCountryCode();
        }
        throw new IllegalArgumentException("Invalid region code: " + str);
    }

    public String getNddPrefixForRegion(String str, boolean z) {
        PhoneMetadata metadataForRegion = getMetadataForRegion(str);
        if (metadataForRegion == null) {
            Logger logger = LOGGER;
            Level level = Level.WARNING;
            StringBuilder append = new StringBuilder().append("Invalid or missing region code (");
            if (str == null) {
                str = "null";
            }
            logger.log(level, append.append(str).append(") provided.").toString());
            return null;
        }
        String nationalPrefix = metadataForRegion.getNationalPrefix();
        if (nationalPrefix.length() == 0) {
            return null;
        }
        if (z) {
            return nationalPrefix.replace("~", "");
        }
        return nationalPrefix;
    }

    public boolean isNANPACountry(String str) {
        return this.nanpaRegions.contains(str);
    }

    boolean isLeadingZeroPossible(int i) {
        PhoneMetadata metadataForRegionOrCallingCode = getMetadataForRegionOrCallingCode(i, getRegionCodeForCountryCode(i));
        if (metadataForRegionOrCallingCode == null) {
            return false;
        }
        return metadataForRegionOrCallingCode.isLeadingZeroPossible();
    }

    public boolean isAlphaNumber(String str) {
        if (!isViablePhoneNumber(str)) {
            return false;
        }
        CharSequence stringBuilder = new StringBuilder(str);
        maybeStripExtension(stringBuilder);
        return VALID_ALPHA_PHONE_PATTERN.matcher(stringBuilder).matches();
    }

    public boolean isPossibleNumber(PhoneNumber phoneNumber) {
        return isPossibleNumberWithReason(phoneNumber) == ValidationResult.IS_POSSIBLE;
    }

    private ValidationResult testNumberLengthAgainstPattern(Pattern pattern, String str) {
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return ValidationResult.IS_POSSIBLE;
        }
        if (matcher.lookingAt()) {
            return ValidationResult.TOO_LONG;
        }
        return ValidationResult.TOO_SHORT;
    }

    public ValidationResult isPossibleNumberWithReason(PhoneNumber phoneNumber) {
        String nationalSignificantNumber = getNationalSignificantNumber(phoneNumber);
        int countryCode = phoneNumber.getCountryCode();
        if (!hasValidCountryCallingCode(countryCode)) {
            return ValidationResult.INVALID_COUNTRY_CODE;
        }
        PhoneNumberDesc generalDesc = getMetadataForRegionOrCallingCode(countryCode, getRegionCodeForCountryCode(countryCode)).getGeneralDesc();
        if (generalDesc.hasNationalNumberPattern()) {
            return testNumberLengthAgainstPattern(this.regexCache.getPatternForRegex(generalDesc.getPossibleNumberPattern()), nationalSignificantNumber);
        }
        LOGGER.log(Level.FINER, "Checking if number is possible with incomplete metadata.");
        int length = nationalSignificantNumber.length();
        if (length < 2) {
            return ValidationResult.TOO_SHORT;
        }
        if (length > 16) {
            return ValidationResult.TOO_LONG;
        }
        return ValidationResult.IS_POSSIBLE;
    }

    public boolean isPossibleNumber(String str, String str2) {
        try {
            return isPossibleNumber(parse(str, str2));
        } catch (NumberParseException e) {
            return false;
        }
    }

    public boolean truncateTooLongNumber(PhoneNumber phoneNumber) {
        if (isValidNumber(phoneNumber)) {
            return true;
        }
        PhoneNumber phoneNumber2 = new PhoneNumber();
        phoneNumber2.mergeFrom(phoneNumber);
        long nationalNumber = phoneNumber.getNationalNumber();
        do {
            nationalNumber /= 10;
            phoneNumber2.setNationalNumber(nationalNumber);
            if (isPossibleNumberWithReason(phoneNumber2) == ValidationResult.TOO_SHORT || nationalNumber == 0) {
                return false;
            }
        } while (!isValidNumber(phoneNumber2));
        phoneNumber.setNationalNumber(nationalNumber);
        return true;
    }

    public AsYouTypeFormatter getAsYouTypeFormatter(String str) {
        return new AsYouTypeFormatter(str);
    }

    int extractCountryCode(StringBuilder stringBuilder, StringBuilder stringBuilder2) {
        if (stringBuilder.length() == 0 || stringBuilder.charAt(0) == DialpadAction.ZERO) {
            return 0;
        }
        int length = stringBuilder.length();
        int i = 1;
        while (i <= 3 && i <= length) {
            int parseInt = Integer.parseInt(stringBuilder.substring(0, i));
            if (this.countryCallingCodeToRegionCodeMap.containsKey(Integer.valueOf(parseInt))) {
                stringBuilder2.append(stringBuilder.substring(i));
                return parseInt;
            }
            i++;
        }
        return 0;
    }

    int maybeExtractCountryCode(String str, PhoneMetadata phoneMetadata, StringBuilder stringBuilder, boolean z, PhoneNumber phoneNumber) {
        if (str.length() == 0) {
            return 0;
        }
        Object stringBuilder2 = new StringBuilder(str);
        String str2 = "NonMatch";
        if (phoneMetadata != null) {
            str2 = phoneMetadata.getInternationalPrefix();
        }
        CountryCodeSource maybeStripInternationalPrefixAndNormalize = maybeStripInternationalPrefixAndNormalize(stringBuilder2, str2);
        if (z) {
            phoneNumber.setCountryCodeSource(maybeStripInternationalPrefixAndNormalize);
        }
        int countryCode;
        if (maybeStripInternationalPrefixAndNormalize == CountryCodeSource.FROM_DEFAULT_COUNTRY) {
            if (phoneMetadata != null) {
                countryCode = phoneMetadata.getCountryCode();
                String valueOf = String.valueOf(countryCode);
                String stringBuilder3 = stringBuilder2.toString();
                if (stringBuilder3.startsWith(valueOf)) {
                    CharSequence stringBuilder4 = new StringBuilder(stringBuilder3.substring(valueOf.length()));
                    PhoneNumberDesc generalDesc = phoneMetadata.getGeneralDesc();
                    Pattern patternForRegex = this.regexCache.getPatternForRegex(generalDesc.getNationalNumberPattern());
                    maybeStripNationalPrefixAndCarrierCode(stringBuilder4, phoneMetadata, null);
                    Pattern patternForRegex2 = this.regexCache.getPatternForRegex(generalDesc.getPossibleNumberPattern());
                    if ((!patternForRegex.matcher(stringBuilder2).matches() && patternForRegex.matcher(stringBuilder4).matches()) || testNumberLengthAgainstPattern(patternForRegex2, stringBuilder2.toString()) == ValidationResult.TOO_LONG) {
                        stringBuilder.append(stringBuilder4);
                        if (z) {
                            phoneNumber.setCountryCodeSource(CountryCodeSource.FROM_NUMBER_WITHOUT_PLUS_SIGN);
                        }
                        phoneNumber.setCountryCode(countryCode);
                        return countryCode;
                    }
                }
            }
            phoneNumber.setCountryCode(0);
            return 0;
        } else if (stringBuilder2.length() <= 2) {
            throw new NumberParseException(ErrorType.TOO_SHORT_AFTER_IDD, "Phone number had an IDD, but after this was not long enough to be a viable phone number.");
        } else {
            countryCode = extractCountryCode(stringBuilder2, stringBuilder);
            if (countryCode != 0) {
                phoneNumber.setCountryCode(countryCode);
                return countryCode;
            }
            throw new NumberParseException(ErrorType.INVALID_COUNTRY_CODE, "Country calling code supplied was not recognised.");
        }
    }

    private boolean parsePrefixAsIdd(Pattern pattern, StringBuilder stringBuilder) {
        Matcher matcher = pattern.matcher(stringBuilder);
        if (!matcher.lookingAt()) {
            return false;
        }
        int end = matcher.end();
        Matcher matcher2 = CAPTURING_DIGIT_PATTERN.matcher(stringBuilder.substring(end));
        if (matcher2.find() && normalizeDigitsOnly(matcher2.group(1)).equals("0")) {
            return false;
        }
        stringBuilder.delete(0, end);
        return true;
    }

    CountryCodeSource maybeStripInternationalPrefixAndNormalize(StringBuilder stringBuilder, String str) {
        if (stringBuilder.length() == 0) {
            return CountryCodeSource.FROM_DEFAULT_COUNTRY;
        }
        Matcher matcher = PLUS_CHARS_PATTERN.matcher(stringBuilder);
        if (matcher.lookingAt()) {
            stringBuilder.delete(0, matcher.end());
            normalize(stringBuilder);
            return CountryCodeSource.FROM_NUMBER_WITH_PLUS_SIGN;
        }
        Pattern patternForRegex = this.regexCache.getPatternForRegex(str);
        normalize(stringBuilder);
        return parsePrefixAsIdd(patternForRegex, stringBuilder) ? CountryCodeSource.FROM_NUMBER_WITH_IDD : CountryCodeSource.FROM_DEFAULT_COUNTRY;
    }

    boolean maybeStripNationalPrefixAndCarrierCode(StringBuilder stringBuilder, PhoneMetadata phoneMetadata, StringBuilder stringBuilder2) {
        int length = stringBuilder.length();
        String nationalPrefixForParsing = phoneMetadata.getNationalPrefixForParsing();
        if (length == 0 || nationalPrefixForParsing.length() == 0) {
            return false;
        }
        Matcher matcher = this.regexCache.getPatternForRegex(nationalPrefixForParsing).matcher(stringBuilder);
        if (!matcher.lookingAt()) {
            return false;
        }
        Pattern patternForRegex = this.regexCache.getPatternForRegex(phoneMetadata.getGeneralDesc().getNationalNumberPattern());
        boolean matches = patternForRegex.matcher(stringBuilder).matches();
        int groupCount = matcher.groupCount();
        String nationalPrefixTransformRule = phoneMetadata.getNationalPrefixTransformRule();
        if (nationalPrefixTransformRule != null && nationalPrefixTransformRule.length() != 0 && matcher.group(groupCount) != null) {
            StringBuilder stringBuilder3 = new StringBuilder(stringBuilder);
            stringBuilder3.replace(0, length, matcher.replaceFirst(nationalPrefixTransformRule));
            if (matches && !patternForRegex.matcher(stringBuilder3.toString()).matches()) {
                return false;
            }
            if (stringBuilder2 != null && groupCount > 1) {
                stringBuilder2.append(matcher.group(1));
            }
            stringBuilder.replace(0, stringBuilder.length(), stringBuilder3.toString());
            return true;
        } else if (matches && !patternForRegex.matcher(stringBuilder.substring(matcher.end())).matches()) {
            return false;
        } else {
            if (!(stringBuilder2 == null || groupCount <= 0 || matcher.group(groupCount) == null)) {
                stringBuilder2.append(matcher.group(1));
            }
            stringBuilder.delete(0, matcher.end());
            return true;
        }
    }

    String maybeStripExtension(StringBuilder stringBuilder) {
        Matcher matcher = EXTN_PATTERN.matcher(stringBuilder);
        if (matcher.find() && isViablePhoneNumber(stringBuilder.substring(0, matcher.start()))) {
            int groupCount = matcher.groupCount();
            for (int i = 1; i <= groupCount; i++) {
                if (matcher.group(i) != null) {
                    String group = matcher.group(i);
                    stringBuilder.delete(matcher.start(), stringBuilder.length());
                    return group;
                }
            }
        }
        return "";
    }

    private boolean checkRegionForParsing(String str, String str2) {
        if (isValidRegionCode(str2) || (str != null && str.length() != 0 && PLUS_CHARS_PATTERN.matcher(str).lookingAt())) {
            return true;
        }
        return false;
    }

    public PhoneNumber parse(String str, String str2) {
        PhoneNumber phoneNumber = new PhoneNumber();
        parse(str, str2, phoneNumber);
        return phoneNumber;
    }

    public void parse(String str, String str2, PhoneNumber phoneNumber) {
        parseHelper(str, str2, false, true, phoneNumber);
    }

    public PhoneNumber parseAndKeepRawInput(String str, String str2) {
        PhoneNumber phoneNumber = new PhoneNumber();
        parseAndKeepRawInput(str, str2, phoneNumber);
        return phoneNumber;
    }

    public void parseAndKeepRawInput(String str, String str2, PhoneNumber phoneNumber) {
        parseHelper(str, str2, true, true, phoneNumber);
    }

    public Iterable<PhoneNumberMatch> findNumbers(CharSequence charSequence, String str) {
        return findNumbers(charSequence, str, Leniency.VALID, Long.MAX_VALUE);
    }

    public Iterable<PhoneNumberMatch> findNumbers(CharSequence charSequence, String str, Leniency leniency, long j) {
        final CharSequence charSequence2 = charSequence;
        final String str2 = str;
        final Leniency leniency2 = leniency;
        final long j2 = j;
        return new Iterable<PhoneNumberMatch>() {
            public Iterator<PhoneNumberMatch> iterator() {
                return new PhoneNumberMatcher(PhoneNumberUtil.this, charSequence2, str2, leniency2, j2);
            }
        };
    }

    private void parseHelper(String str, String str2, boolean z, boolean z2, PhoneNumber phoneNumber) {
        if (str == null) {
            throw new NumberParseException(ErrorType.NOT_A_NUMBER, "The phone number supplied was null.");
        } else if (str.length() > MAX_INPUT_STRING_LENGTH) {
            throw new NumberParseException(ErrorType.TOO_LONG, "The string supplied was too long to parse.");
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            buildNationalNumberForParsing(str, stringBuilder);
            if (!isViablePhoneNumber(stringBuilder.toString())) {
                throw new NumberParseException(ErrorType.NOT_A_NUMBER, "The string supplied did not seem to be a phone number.");
            } else if (!z2 || checkRegionForParsing(stringBuilder.toString(), str2)) {
                int maybeExtractCountryCode;
                if (z) {
                    phoneNumber.setRawInput(str);
                }
                String maybeStripExtension = maybeStripExtension(stringBuilder);
                if (maybeStripExtension.length() > 0) {
                    phoneNumber.setExtension(maybeStripExtension);
                }
                PhoneMetadata metadataForRegion = getMetadataForRegion(str2);
                StringBuilder stringBuilder2 = new StringBuilder();
                try {
                    maybeExtractCountryCode = maybeExtractCountryCode(stringBuilder.toString(), metadataForRegion, stringBuilder2, z, phoneNumber);
                } catch (NumberParseException e) {
                    Matcher matcher = PLUS_CHARS_PATTERN.matcher(stringBuilder.toString());
                    if (e.getErrorType() == ErrorType.INVALID_COUNTRY_CODE && matcher.lookingAt()) {
                        maybeExtractCountryCode = maybeExtractCountryCode(stringBuilder.substring(matcher.end()), metadataForRegion, stringBuilder2, z, phoneNumber);
                        if (maybeExtractCountryCode == 0) {
                            throw new NumberParseException(ErrorType.INVALID_COUNTRY_CODE, "Could not interpret numbers after plus-sign.");
                        }
                    }
                    throw new NumberParseException(e.getErrorType(), e.getMessage());
                }
                if (maybeExtractCountryCode != 0) {
                    String regionCodeForCountryCode = getRegionCodeForCountryCode(maybeExtractCountryCode);
                    if (!regionCodeForCountryCode.equals(str2)) {
                        metadataForRegion = getMetadataForRegionOrCallingCode(maybeExtractCountryCode, regionCodeForCountryCode);
                    }
                } else {
                    normalize(stringBuilder);
                    stringBuilder2.append(stringBuilder);
                    if (str2 != null) {
                        phoneNumber.setCountryCode(metadataForRegion.getCountryCode());
                    } else if (z) {
                        phoneNumber.clearCountryCodeSource();
                    }
                }
                if (stringBuilder2.length() < 2) {
                    throw new NumberParseException(ErrorType.TOO_SHORT_NSN, "The string supplied is too short to be a phone number.");
                }
                if (metadataForRegion != null) {
                    StringBuilder stringBuilder3 = new StringBuilder();
                    maybeStripNationalPrefixAndCarrierCode(stringBuilder2, metadataForRegion, stringBuilder3);
                    if (z) {
                        phoneNumber.setPreferredDomesticCarrierCode(stringBuilder3.toString());
                    }
                }
                maybeExtractCountryCode = stringBuilder2.length();
                if (maybeExtractCountryCode < 2) {
                    throw new NumberParseException(ErrorType.TOO_SHORT_NSN, "The string supplied is too short to be a phone number.");
                } else if (maybeExtractCountryCode > 16) {
                    throw new NumberParseException(ErrorType.TOO_LONG, "The string supplied is too long to be a phone number.");
                } else {
                    if (stringBuilder2.charAt(0) == DialpadAction.ZERO) {
                        phoneNumber.setItalianLeadingZero(true);
                    }
                    phoneNumber.setNationalNumber(Long.parseLong(stringBuilder2.toString()));
                }
            } else {
                throw new NumberParseException(ErrorType.INVALID_COUNTRY_CODE, "Missing or invalid default region.");
            }
        }
    }

    private void buildNationalNumberForParsing(String str, StringBuilder stringBuilder) {
        int indexOf = str.indexOf(RFC3966_PHONE_CONTEXT);
        if (indexOf > 0) {
            int length = RFC3966_PHONE_CONTEXT.length() + indexOf;
            if (str.charAt(length) == PLUS_SIGN) {
                int indexOf2 = str.indexOf(59, length);
                if (indexOf2 > 0) {
                    stringBuilder.append(str.substring(length, indexOf2));
                } else {
                    stringBuilder.append(str.substring(length));
                }
            }
            stringBuilder.append(str.substring(str.indexOf(RFC3966_PREFIX) + RFC3966_PREFIX.length(), indexOf));
        } else {
            stringBuilder.append(extractPossibleNumber(str));
        }
        indexOf = stringBuilder.indexOf(RFC3966_ISDN_SUBADDRESS);
        if (indexOf > 0) {
            stringBuilder.delete(indexOf, stringBuilder.length());
        }
    }

    public MatchType isNumberMatch(PhoneNumber phoneNumber, PhoneNumber phoneNumber2) {
        PhoneNumber phoneNumber3 = new PhoneNumber();
        phoneNumber3.mergeFrom(phoneNumber);
        PhoneNumber phoneNumber4 = new PhoneNumber();
        phoneNumber4.mergeFrom(phoneNumber2);
        phoneNumber3.clearRawInput();
        phoneNumber3.clearCountryCodeSource();
        phoneNumber3.clearPreferredDomesticCarrierCode();
        phoneNumber4.clearRawInput();
        phoneNumber4.clearCountryCodeSource();
        phoneNumber4.clearPreferredDomesticCarrierCode();
        if (phoneNumber3.hasExtension() && phoneNumber3.getExtension().length() == 0) {
            phoneNumber3.clearExtension();
        }
        if (phoneNumber4.hasExtension() && phoneNumber4.getExtension().length() == 0) {
            phoneNumber4.clearExtension();
        }
        if (phoneNumber3.hasExtension() && phoneNumber4.hasExtension() && !phoneNumber3.getExtension().equals(phoneNumber4.getExtension())) {
            return MatchType.NO_MATCH;
        }
        int countryCode = phoneNumber3.getCountryCode();
        int countryCode2 = phoneNumber4.getCountryCode();
        if (countryCode == 0 || countryCode2 == 0) {
            phoneNumber3.setCountryCode(countryCode2);
            if (phoneNumber3.exactlySameAs(phoneNumber4)) {
                return MatchType.NSN_MATCH;
            }
            if (isNationalNumberSuffixOfTheOther(phoneNumber3, phoneNumber4)) {
                return MatchType.SHORT_NSN_MATCH;
            }
            return MatchType.NO_MATCH;
        } else if (phoneNumber3.exactlySameAs(phoneNumber4)) {
            return MatchType.EXACT_MATCH;
        } else {
            if (countryCode == countryCode2 && isNationalNumberSuffixOfTheOther(phoneNumber3, phoneNumber4)) {
                return MatchType.SHORT_NSN_MATCH;
            }
            return MatchType.NO_MATCH;
        }
    }

    private boolean isNationalNumberSuffixOfTheOther(PhoneNumber phoneNumber, PhoneNumber phoneNumber2) {
        String valueOf = String.valueOf(phoneNumber.getNationalNumber());
        String valueOf2 = String.valueOf(phoneNumber2.getNationalNumber());
        return valueOf.endsWith(valueOf2) || valueOf2.endsWith(valueOf);
    }

    public MatchType isNumberMatch(String str, String str2) {
        try {
            return isNumberMatch(parse(str, UNKNOWN_REGION), str2);
        } catch (NumberParseException e) {
            if (e.getErrorType() == ErrorType.INVALID_COUNTRY_CODE) {
                try {
                    return isNumberMatch(parse(str2, UNKNOWN_REGION), str);
                } catch (NumberParseException e2) {
                    if (e2.getErrorType() == ErrorType.INVALID_COUNTRY_CODE) {
                        try {
                            PhoneNumber phoneNumber = new PhoneNumber();
                            PhoneNumber phoneNumber2 = new PhoneNumber();
                            parseHelper(str, null, false, false, phoneNumber);
                            parseHelper(str2, null, false, false, phoneNumber2);
                            return isNumberMatch(phoneNumber, phoneNumber2);
                        } catch (NumberParseException e3) {
                            return MatchType.NOT_A_NUMBER;
                        }
                    }
                    return MatchType.NOT_A_NUMBER;
                }
            }
            return MatchType.NOT_A_NUMBER;
        }
    }

    public MatchType isNumberMatch(PhoneNumber phoneNumber, String str) {
        try {
            return isNumberMatch(phoneNumber, parse(str, UNKNOWN_REGION));
        } catch (NumberParseException e) {
            if (e.getErrorType() == ErrorType.INVALID_COUNTRY_CODE) {
                String regionCodeForCountryCode = getRegionCodeForCountryCode(phoneNumber.getCountryCode());
                try {
                    if (regionCodeForCountryCode.equals(UNKNOWN_REGION)) {
                        PhoneNumber phoneNumber2 = new PhoneNumber();
                        parseHelper(str, null, false, false, phoneNumber2);
                        return isNumberMatch(phoneNumber, phoneNumber2);
                    }
                    MatchType isNumberMatch = isNumberMatch(phoneNumber, parse(str, regionCodeForCountryCode));
                    if (isNumberMatch == MatchType.EXACT_MATCH) {
                        return MatchType.NSN_MATCH;
                    }
                    return isNumberMatch;
                } catch (NumberParseException e2) {
                    return MatchType.NOT_A_NUMBER;
                }
            }
            return MatchType.NOT_A_NUMBER;
        }
    }

    boolean canBeInternationallyDialled(PhoneNumber phoneNumber) {
        PhoneMetadata metadataForRegion = getMetadataForRegion(getRegionCodeForNumber(phoneNumber));
        if (metadataForRegion != null && isNumberMatchingDesc(getNationalSignificantNumber(phoneNumber), metadataForRegion.getNoInternationalDialling())) {
            return false;
        }
        return true;
    }

    public String getNationalNumberWithPrefix(PhoneNumber phoneNumber) {
        if (phoneNumber == null) {
            return null;
        }
        String regionCodeForNumber = getRegionCodeForNumber(phoneNumber);
        String valueOf = String.valueOf(phoneNumber.getNationalNumber());
        if (!isValidRegionCode(regionCodeForNumber) && !REGION_CODE_FOR_NON_GEO_ENTITY.equals(regionCodeForNumber)) {
            return valueOf;
        }
        String nationalSignificantNumber = getNationalSignificantNumber(phoneNumber);
        PhoneMetadata metadataForRegionOrCallingCode = getMetadataForRegionOrCallingCode(phoneNumber.getCountryCode(), regionCodeForNumber);
        PhoneNumberType numberTypeHelper = getNumberTypeHelper(nationalSignificantNumber, metadataForRegionOrCallingCode);
        if (regionCodeForNumber.equals("CN") && numberTypeHelper == PhoneNumberType.FIXED_LINE) {
            return metadataForRegionOrCallingCode.getNationalPrefix() + String.valueOf(phoneNumber.getNationalNumber());
        }
        return valueOf;
    }
}
