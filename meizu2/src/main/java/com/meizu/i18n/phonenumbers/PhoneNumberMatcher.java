package com.meizu.i18n.phonenumbers;

import com.meizu.common.util.LunarCalendar;
import com.meizu.common.widget.MzContactsContract.MzGroups;
import com.meizu.i18n.phonenumbers.PhoneNumberUtil.Leniency;
import com.meizu.i18n.phonenumbers.PhoneNumberUtil.MatchType;
import com.meizu.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.meizu.i18n.phonenumbers.Phonemetadata.NumberFormat;
import com.meizu.i18n.phonenumbers.Phonemetadata.PhoneMetadata;
import com.meizu.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.meizu.i18n.phonenumbers.Phonenumber.PhoneNumber.CountryCodeSource;
import java.lang.Character.UnicodeBlock;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class PhoneNumberMatcher implements Iterator<PhoneNumberMatch> {
    private static final Pattern GROUP_SEPARATOR;
    private static final Pattern LEAD_CLASS;
    private static final Pattern MATCHING_BRACKETS;
    private static final Pattern PATTERN;
    private static final Pattern PUB_PAGES = Pattern.compile("\\d{1,5}-+\\d{1,5}\\s{0,4}\\(\\d{1,4}");
    private static final Pattern SLASH_SEPARATED_DATES = Pattern.compile("(?:(?:[0-3]?\\d/[01]?\\d)|(?:[01]?\\d/[0-3]?\\d))/(?:[12]\\d)?\\d{2}");
    private static final Pattern TIME_STAMPS = Pattern.compile("[12]\\d{3}[-/]?[01]\\d[-/]?[0-3]\\d [0-2]\\d$");
    private static final Pattern TIME_STAMPS_SUFFIX = Pattern.compile(":[0-5]\\d");
    private PhoneNumberMatch lastMatch = null;
    private final Leniency leniency;
    private long maxTries;
    private final PhoneNumberUtil phoneUtil;
    private final String preferredRegion;
    private int searchIndex = 0;
    private State state = State.NOT_READY;
    private final CharSequence text;

    interface NumberGroupingChecker {
        boolean checkGroups(PhoneNumberUtil phoneNumberUtil, PhoneNumber phoneNumber,
                            StringBuilder stringBuilder, String[] strArr);
    }

    enum State {
        NOT_READY,
        READY,
        DONE
    }

    static {
        String str = "(\\[（［";
        String str2 = ")\\]）］";
        String str3 = "[^" + str + str2 + "]";
        MATCHING_BRACKETS = Pattern.compile("(?:[" + str + "])?" + "(?:" + str3 + "+" + "[" + str2 + "])?" + str3 + "+" + "(?:[" + str + "]" + str3 + "+[" + str2 + "])" + limit(0, 3) + str3 + "*");
        str2 = limit(0, 2);
        str3 = limit(0, 4);
        String limit = limit(0, 19);
        str3 = "[-x‐-―−ー－-／  ­​⁠　()（）［］.\\[\\]/~⁓∼～]" + str3;
        String str4 = "\\p{Nd}" + limit(1, 19);
        str = str + "+＋";
        String str5 = "[" + str + "]";
        LEAD_CLASS = Pattern.compile(str5);
        GROUP_SEPARATOR = Pattern.compile("\\p{Z}[^" + str + "\\p{Nd}]*");
        PATTERN = Pattern.compile("(?:" + str5 + str3 + ")" + str2 + str4 + "(?:" + str3 + str4 + ")" + limit + "(?:" + PhoneNumberUtil.EXTN_PATTERNS_FOR_MATCHING + ")?", 66);
    }

    private static String limit(int i, int i2) {
        if (i >= 0 && i2 > 0 && i2 >= i) {
            return "{" + i + MzGroups.GROUP_SPLIT_MARK_EXTRA + i2 + "}";
        }
        throw new IllegalArgumentException();
    }

    PhoneNumberMatcher(PhoneNumberUtil phoneNumberUtil, CharSequence charSequence, String str, Leniency leniency, long j) {
        if (phoneNumberUtil == null || leniency == null) {
            throw new NullPointerException();
        } else if (j < 0) {
            throw new IllegalArgumentException();
        } else {
            this.phoneUtil = phoneNumberUtil;
            if (charSequence == null) {
                charSequence = "";
            }
            this.text = charSequence;
            this.preferredRegion = str;
            this.leniency = leniency;
            this.maxTries = j;
        }
    }

    private PhoneNumberMatch find(int i) {
        Matcher matcher = PATTERN.matcher(this.text);
        while (this.maxTries > 0 && matcher.find(r7)) {
            int start = matcher.start();
            CharSequence trimAfterFirstMatch = trimAfterFirstMatch(PhoneNumberUtil.SECOND_NUMBER_START_PATTERN, this.text.subSequence(start, matcher.end()));
            PhoneNumberMatch extractMatch = extractMatch(trimAfterFirstMatch, start);
            if (extractMatch != null) {
                return extractMatch;
            }
            i = start + trimAfterFirstMatch.length();
            this.maxTries--;
        }
        return null;
    }

    private static CharSequence trimAfterFirstMatch(Pattern pattern, CharSequence charSequence) {
        Matcher matcher = pattern.matcher(charSequence);
        if (matcher.find()) {
            return charSequence.subSequence(0, matcher.start());
        }
        return charSequence;
    }

    static boolean isLatinLetter(char c) {
        if (!Character.isLetter(c) && Character.getType(c) != 6) {
            return false;
        }
        UnicodeBlock of = UnicodeBlock.of(c);
        if (of.equals(UnicodeBlock.BASIC_LATIN) || of.equals(UnicodeBlock.LATIN_1_SUPPLEMENT) || of.equals(UnicodeBlock.LATIN_EXTENDED_A) || of.equals(UnicodeBlock.LATIN_EXTENDED_ADDITIONAL) || of.equals(UnicodeBlock.LATIN_EXTENDED_B) || of.equals(UnicodeBlock.COMBINING_DIACRITICAL_MARKS)) {
            return true;
        }
        return false;
    }

    private static boolean isInvalidPunctuationSymbol(char c) {
        return c == '%' || Character.getType(c) == 26;
    }

    private PhoneNumberMatch extractMatch(CharSequence charSequence, int i) {
        if (PUB_PAGES.matcher(charSequence).find() || SLASH_SEPARATED_DATES.matcher(charSequence).find()) {
            return null;
        }
        if (TIME_STAMPS.matcher(charSequence).find()) {
            if (TIME_STAMPS_SUFFIX.matcher(this.text.toString().substring(charSequence.length() + i)).lookingAt()) {
                return null;
            }
        }
        String charSequence2 = charSequence.toString();
        PhoneNumberMatch parseAndVerify = parseAndVerify(charSequence2, i);
        return parseAndVerify == null ? extractInnerMatch(charSequence2, i) : parseAndVerify;
    }

    private PhoneNumberMatch extractInnerMatch(String str, int i) {
        Matcher matcher = GROUP_SEPARATOR.matcher(str);
        if (matcher.find()) {
            CharSequence trimAfterFirstMatch = trimAfterFirstMatch(PhoneNumberUtil.UNWANTED_END_CHAR_PATTERN, str.substring(0, matcher.start()));
            PhoneNumberMatch parseAndVerify = parseAndVerify(trimAfterFirstMatch.toString(), i);
            if (parseAndVerify != null) {
                return parseAndVerify;
            }
            this.maxTries--;
            int end = matcher.end();
            PhoneNumberMatch parseAndVerify2 = parseAndVerify(trimAfterFirstMatch(PhoneNumberUtil.UNWANTED_END_CHAR_PATTERN, str.substring(end)).toString(), i + end);
            if (parseAndVerify2 != null) {
                return parseAndVerify2;
            }
            this.maxTries--;
            if (this.maxTries > 0) {
                while (matcher.find()) {
                    end = matcher.start();
                }
                CharSequence trimAfterFirstMatch2 = trimAfterFirstMatch(PhoneNumberUtil.UNWANTED_END_CHAR_PATTERN, str.substring(0, end));
                if (trimAfterFirstMatch2.equals(trimAfterFirstMatch)) {
                    return null;
                }
                parseAndVerify = parseAndVerify(trimAfterFirstMatch2.toString(), i);
                if (parseAndVerify != null) {
                    return parseAndVerify;
                }
                this.maxTries--;
            }
        }
        return null;
    }

    private PhoneNumberMatch parseAndVerify(String str, int i) {
        try {
            if (!MATCHING_BRACKETS.matcher(str).matches()) {
                return null;
            }
            if (this.leniency.compareTo(Leniency.VALID) >= 0) {
                char charAt;
                if (i > 0 && !LEAD_CLASS.matcher(str).lookingAt()) {
                    charAt = this.text.charAt(i - 1);
                    if (isInvalidPunctuationSymbol(charAt) || isLatinLetter(charAt)) {
                        return null;
                    }
                }
                int length = str.length() + i;
                if (length < this.text.length()) {
                    charAt = this.text.charAt(length);
                    if (isInvalidPunctuationSymbol(charAt) || isLatinLetter(charAt)) {
                        return null;
                    }
                }
            }
            PhoneNumber parseAndKeepRawInput = this.phoneUtil.parseAndKeepRawInput(str, this.preferredRegion);
            if (!this.leniency.verify(parseAndKeepRawInput, str, this.phoneUtil)) {
                return null;
            }
            parseAndKeepRawInput.clearCountryCodeSource();
            parseAndKeepRawInput.clearRawInput();
            parseAndKeepRawInput.clearPreferredDomesticCarrierCode();
            return new PhoneNumberMatch(i, str, parseAndKeepRawInput);
        } catch (NumberParseException e) {
            return null;
        }
    }

    static boolean allNumberGroupsRemainGrouped(PhoneNumberUtil phoneNumberUtil, PhoneNumber phoneNumber, StringBuilder stringBuilder, String[] strArr) {
        int i = 0;
        for (int i2 = 0; i2 < strArr.length; i2++) {
            i = stringBuilder.indexOf(strArr[i2], i);
            if (i < 0) {
                return false;
            }
            i += strArr[i2].length();
            if (i2 == 0 && i < stringBuilder.length() && Character.isDigit(stringBuilder.charAt(i))) {
                return stringBuilder.substring(i - strArr[i2].length()).startsWith(phoneNumberUtil.getNationalSignificantNumber(phoneNumber));
            }
        }
        return stringBuilder.substring(i).contains(phoneNumber.getExtension());
    }

    static boolean allNumberGroupsAreExactlyPresent(PhoneNumberUtil phoneNumberUtil, PhoneNumber phoneNumber, StringBuilder stringBuilder, String[] strArr) {
        int length;
        String[] split = PhoneNumberUtil.NON_DIGITS_PATTERN.split(stringBuilder.toString());
        if (phoneNumber.hasExtension()) {
            length = split.length - 2;
        } else {
            length = split.length - 1;
        }
        if (split.length == 1 || split[length].contains(phoneNumberUtil.getNationalSignificantNumber(phoneNumber))) {
            return true;
        }
        int i = length;
        length = strArr.length - 1;
        while (length > 0 && i >= 0) {
            if (!split[i].equals(strArr[length])) {
                return false;
            }
            length--;
            i--;
        }
        boolean z = i >= 0 && split[i].endsWith(strArr[0]);
        return z;
    }

    private static String[] getNationalNumberGroups(PhoneNumberUtil phoneNumberUtil, PhoneNumber phoneNumber, NumberFormat numberFormat) {
        if (numberFormat != null) {
            return phoneNumberUtil.formatNsnUsingPattern(phoneNumberUtil.getNationalSignificantNumber(phoneNumber), numberFormat, PhoneNumberFormat.RFC3966).split(LunarCalendar.DATE_SEPARATOR);
        }
        String format = phoneNumberUtil.format(phoneNumber, PhoneNumberFormat.RFC3966);
        int indexOf = format.indexOf(59);
        if (indexOf < 0) {
            indexOf = format.length();
        }
        return format.substring(format.indexOf(45) + 1, indexOf).split(LunarCalendar.DATE_SEPARATOR);
    }

    static boolean checkNumberGroupingIsValid(PhoneNumber phoneNumber, String str, PhoneNumberUtil phoneNumberUtil, NumberGroupingChecker numberGroupingChecker) {
        StringBuilder normalizeDigits = PhoneNumberUtil.normalizeDigits(str, true);
        if (numberGroupingChecker.checkGroups(phoneNumberUtil, phoneNumber, normalizeDigits, getNationalNumberGroups(phoneNumberUtil, phoneNumber, null))) {
            return true;
        }
        PhoneMetadata alternateFormatsForCountry = MetadataManager.getAlternateFormatsForCountry(phoneNumber.getCountryCode());
        if (alternateFormatsForCountry != null) {
            for (NumberFormat nationalNumberGroups : alternateFormatsForCountry.numberFormats()) {
                if (numberGroupingChecker.checkGroups(phoneNumberUtil, phoneNumber, normalizeDigits, getNationalNumberGroups(phoneNumberUtil, phoneNumber, nationalNumberGroups))) {
                    return true;
                }
            }
        }
        return false;
    }

    static boolean containsMoreThanOneSlash(String str) {
        int indexOf = str.indexOf(47);
        return indexOf > 0 && str.substring(indexOf + 1).contains(MzGroups.GROUP_SPLIT_MARK_SLASH);
    }

    static boolean containsOnlyValidXChars(PhoneNumber phoneNumber, String str, PhoneNumberUtil phoneNumberUtil) {
        int i = 0;
        while (i < str.length() - 1) {
            char charAt = str.charAt(i);
            if (charAt == 'x' || charAt == 'X') {
                charAt = str.charAt(i + 1);
                if (charAt == 'x' || charAt == 'X') {
                    i++;
                    if (phoneNumberUtil.isNumberMatch(phoneNumber, str.substring(i)) != MatchType.NSN_MATCH) {
                        return false;
                    }
                } else if (!PhoneNumberUtil.normalizeDigitsOnly(str.substring(i)).equals(phoneNumber.getExtension())) {
                    return false;
                }
            }
            i++;
        }
        return true;
    }

    static boolean isNationalPrefixPresentIfRequired(PhoneNumber phoneNumber, PhoneNumberUtil phoneNumberUtil) {
        if (phoneNumber.getCountryCodeSource() != CountryCodeSource.FROM_DEFAULT_COUNTRY) {
            return true;
        }
        PhoneMetadata metadataForRegion = phoneNumberUtil.getMetadataForRegion(phoneNumberUtil.getRegionCodeForCountryCode(phoneNumber.getCountryCode()));
        if (metadataForRegion == null) {
            return true;
        }
        NumberFormat chooseFormattingPatternForNumber = phoneNumberUtil.chooseFormattingPatternForNumber(metadataForRegion.numberFormats(), phoneNumberUtil.getNationalSignificantNumber(phoneNumber));
        if (chooseFormattingPatternForNumber == null || chooseFormattingPatternForNumber.getNationalPrefixFormattingRule().length() <= 0 || chooseFormattingPatternForNumber.isNationalPrefixOptionalWhenFormatting()) {
            return true;
        }
        String nationalPrefixFormattingRule = chooseFormattingPatternForNumber.getNationalPrefixFormattingRule();
        if (PhoneNumberUtil.normalizeDigitsOnly(nationalPrefixFormattingRule.substring(0, nationalPrefixFormattingRule.indexOf("$1"))).length() != 0) {
            return phoneNumberUtil.maybeStripNationalPrefixAndCarrierCode(new StringBuilder(PhoneNumberUtil.normalizeDigitsOnly(phoneNumber.getRawInput())), metadataForRegion, null);
        }
        return true;
    }

    public boolean hasNext() {
        if (this.state == State.NOT_READY) {
            this.lastMatch = find(this.searchIndex);
            if (this.lastMatch == null) {
                this.state = State.DONE;
            } else {
                this.searchIndex = this.lastMatch.end();
                this.state = State.READY;
            }
        }
        return this.state == State.READY;
    }

    public PhoneNumberMatch next() {
        if (hasNext()) {
            PhoneNumberMatch phoneNumberMatch = this.lastMatch;
            this.lastMatch = null;
            this.state = State.NOT_READY;
            return phoneNumberMatch;
        }
        throw new NoSuchElementException();
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
