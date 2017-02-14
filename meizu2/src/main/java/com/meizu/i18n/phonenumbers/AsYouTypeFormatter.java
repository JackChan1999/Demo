package com.meizu.i18n.phonenumbers;

import com.meizu.i18n.phonenumbers.Phonemetadata.NumberFormat;
import com.meizu.i18n.phonenumbers.Phonemetadata.PhoneMetadata;
import com.ted.sdk.ivr.DialpadAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AsYouTypeFormatter {
    private static final Pattern CHARACTER_CLASS_PATTERN = Pattern.compile("\\[([^\\[\\]])*\\]");
    private static final Pattern DIGIT_PATTERN = Pattern.compile(DIGIT_PLACEHOLDER);
    private static final String DIGIT_PLACEHOLDER = " ";
    private static final Pattern ELIGIBLE_FORMAT_PATTERN = Pattern.compile("[-x‐-―−ー－-／  ­​⁠　()（）［］.\\[\\]/~⁓∼～]*(\\$\\d[-x‐-―−ー－-／  ­​⁠　()（）［］.\\[\\]/~⁓∼～]*)+");
    private static final PhoneMetadata EMPTY_METADATA = new PhoneMetadata().setInternationalPrefix("NA");
    private static final int MIN_LEADING_DIGITS_LENGTH = 3;
    private static final Pattern NATIONAL_PREFIX_SEPARATORS_PATTERN = Pattern.compile("[- ]");
    private static final char SEPARATOR_BEFORE_NATIONAL_NUMBER = ' ';
    private static final Pattern STANDALONE_DIGIT_PATTERN = Pattern.compile("\\d(?=[^,}][^,}])");
    private boolean ableToFormat = true;
    private StringBuilder accruedInput = new StringBuilder();
    private StringBuilder accruedInputWithoutFormatting = new StringBuilder();
    private String currentFormattingPattern = "";
    private PhoneMetadata currentMetaData;
    private String currentOutput = "";
    private String defaultCountry;
    private PhoneMetadata defaultMetaData;
    private StringBuilder formattingTemplate = new StringBuilder();
    private boolean inputHasFormatting = false;
    private boolean isCompleteNumber = false;
    private boolean isExpectingCountryCallingCode = false;
    private int lastMatchPosition = 0;
    private StringBuilder nationalNumber = new StringBuilder();
    private String nationalPrefixExtracted = "";
    private int originalPosition = 0;
    private final PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
    private int positionToRemember = 0;
    private List<NumberFormat> possibleFormats = new ArrayList();
    private StringBuilder prefixBeforeNationalNumber = new StringBuilder();
    private RegexCache regexCache = new RegexCache(64);
    private boolean shouldAddSpaceAfterNationalPrefix = false;

    AsYouTypeFormatter(String str) {
        this.defaultCountry = str;
        this.currentMetaData = getMetadataForRegion(this.defaultCountry);
        this.defaultMetaData = this.currentMetaData;
    }

    private PhoneMetadata getMetadataForRegion(String str) {
        PhoneMetadata metadataForRegion = this.phoneUtil.getMetadataForRegion(this.phoneUtil.getRegionCodeForCountryCode(this.phoneUtil.getCountryCodeForRegion(str)));
        return metadataForRegion != null ? metadataForRegion : EMPTY_METADATA;
    }

    private boolean maybeCreateNewTemplate() {
        Iterator it = this.possibleFormats.iterator();
        while (it.hasNext()) {
            NumberFormat numberFormat = (NumberFormat) it.next();
            String pattern = numberFormat.getPattern();
            if (this.currentFormattingPattern.equals(pattern)) {
                return false;
            }
            if (createFormattingTemplate(numberFormat)) {
                this.currentFormattingPattern = pattern;
                this.shouldAddSpaceAfterNationalPrefix = NATIONAL_PREFIX_SEPARATORS_PATTERN.matcher(numberFormat.getNationalPrefixFormattingRule()).find();
                this.lastMatchPosition = 0;
                return true;
            }
            it.remove();
        }
        this.ableToFormat = false;
        return false;
    }

    private void getAvailableFormats(String str) {
        List numberFormats;
        if (!this.isCompleteNumber || this.currentMetaData.intlNumberFormatSize() <= 0) {
            numberFormats = this.currentMetaData.numberFormats();
        } else {
            numberFormats = this.currentMetaData.intlNumberFormats();
        }
        boolean hasNationalPrefix = this.currentMetaData.hasNationalPrefix();
        for (NumberFormat numberFormat : r0) {
            if (!(!hasNationalPrefix || this.isCompleteNumber || numberFormat.isNationalPrefixOptionalWhenFormatting())) {
                PhoneNumberUtil phoneNumberUtil = this.phoneUtil;
                if (!PhoneNumberUtil.formattingRuleHasFirstGroupOnly(numberFormat.getNationalPrefixFormattingRule())) {
                }
            }
            if (isFormatEligible(numberFormat.getFormat())) {
                this.possibleFormats.add(numberFormat);
            }
        }
        narrowDownPossibleFormats(str);
    }

    private boolean isFormatEligible(String str) {
        return ELIGIBLE_FORMAT_PATTERN.matcher(str).matches();
    }

    private void narrowDownPossibleFormats(String str) {
        int length = str.length() - 3;
        Iterator it = this.possibleFormats.iterator();
        while (it.hasNext()) {
            NumberFormat numberFormat = (NumberFormat) it.next();
            if (numberFormat.leadingDigitsPatternSize() > length && !this.regexCache.getPatternForRegex(numberFormat.getLeadingDigitsPattern(length)).matcher(str).lookingAt()) {
                it.remove();
            }
        }
    }

    private boolean createFormattingTemplate(NumberFormat numberFormat) {
        CharSequence pattern = numberFormat.getPattern();
        if (pattern.indexOf(124) != -1) {
            return false;
        }
        String replaceAll = STANDALONE_DIGIT_PATTERN.matcher(CHARACTER_CLASS_PATTERN.matcher(pattern).replaceAll("\\\\d")).replaceAll("\\\\d");
        this.formattingTemplate.setLength(0);
        replaceAll = getFormattingTemplate(replaceAll, numberFormat.getFormat());
        if (replaceAll.length() <= 0) {
            return false;
        }
        this.formattingTemplate.append(replaceAll);
        return true;
    }

    private String getFormattingTemplate(String str, String str2) {
        Matcher matcher = this.regexCache.getPatternForRegex(str).matcher("999999999999999");
        matcher.find();
        String group = matcher.group();
        if (group.length() < this.nationalNumber.length()) {
            return "";
        }
        return group.replaceAll(str, str2).replaceAll("9", DIGIT_PLACEHOLDER);
    }

    public void clear() {
        this.currentOutput = "";
        this.accruedInput.setLength(0);
        this.accruedInputWithoutFormatting.setLength(0);
        this.formattingTemplate.setLength(0);
        this.lastMatchPosition = 0;
        this.currentFormattingPattern = "";
        this.prefixBeforeNationalNumber.setLength(0);
        this.nationalPrefixExtracted = "";
        this.nationalNumber.setLength(0);
        this.ableToFormat = true;
        this.inputHasFormatting = false;
        this.positionToRemember = 0;
        this.originalPosition = 0;
        this.isCompleteNumber = false;
        this.isExpectingCountryCallingCode = false;
        this.possibleFormats.clear();
        this.shouldAddSpaceAfterNationalPrefix = false;
        if (!this.currentMetaData.equals(this.defaultMetaData)) {
            this.currentMetaData = getMetadataForRegion(this.defaultCountry);
        }
    }

    public String inputDigit(char c) {
        this.currentOutput = inputDigitWithOptionToRememberPosition(c, false);
        return this.currentOutput;
    }

    public String inputDigitAndRememberPosition(char c) {
        this.currentOutput = inputDigitWithOptionToRememberPosition(c, true);
        return this.currentOutput;
    }

    private String inputDigitWithOptionToRememberPosition(char c, boolean z) {
        this.accruedInput.append(c);
        if (z) {
            this.originalPosition = this.accruedInput.length();
        }
        if (isDigitOrLeadingPlusSign(c)) {
            c = normalizeAndAccrueDigitsAndPlusSign(c, z);
        } else {
            this.ableToFormat = false;
            this.inputHasFormatting = true;
        }
        if (this.ableToFormat) {
            switch (this.accruedInputWithoutFormatting.length()) {
                case 0:
                case 1:
                case 2:
                    return this.accruedInput.toString();
                case 3:
                    if (attemptToExtractIdd()) {
                        this.isExpectingCountryCallingCode = true;
                        break;
                    }
                    this.nationalPrefixExtracted = removeNationalPrefixFromNationalNumber();
                    return attemptToChooseFormattingPattern();
            }
            if (this.isExpectingCountryCallingCode) {
                if (attemptToExtractCountryCallingCode()) {
                    this.isExpectingCountryCallingCode = false;
                }
                return this.prefixBeforeNationalNumber + this.nationalNumber.toString();
            } else if (this.possibleFormats.size() <= 0) {
                return attemptToChooseFormattingPattern();
            } else {
                String inputDigitHelper = inputDigitHelper(c);
                String attemptToFormatAccruedDigits = attemptToFormatAccruedDigits();
                if (attemptToFormatAccruedDigits.length() > 0) {
                    return attemptToFormatAccruedDigits;
                }
                narrowDownPossibleFormats(this.nationalNumber.toString());
                if (maybeCreateNewTemplate()) {
                    return inputAccruedNationalNumber();
                }
                return this.ableToFormat ? appendNationalNumber(inputDigitHelper) : this.accruedInput.toString();
            }
        } else if (this.inputHasFormatting) {
            return this.accruedInput.toString();
        } else {
            if (attemptToExtractIdd()) {
                if (attemptToExtractCountryCallingCode()) {
                    return attemptToChoosePatternWithPrefixExtracted();
                }
            } else if (ableToExtractLongerNdd()) {
                this.prefixBeforeNationalNumber.append(SEPARATOR_BEFORE_NATIONAL_NUMBER);
                return attemptToChoosePatternWithPrefixExtracted();
            }
            return this.accruedInput.toString();
        }
    }

    private String attemptToChoosePatternWithPrefixExtracted() {
        this.ableToFormat = true;
        this.isExpectingCountryCallingCode = false;
        this.possibleFormats.clear();
        return attemptToChooseFormattingPattern();
    }

    private boolean ableToExtractLongerNdd() {
        if (this.nationalPrefixExtracted.length() > 0) {
            this.nationalNumber.insert(0, this.nationalPrefixExtracted);
            this.prefixBeforeNationalNumber.setLength(this.prefixBeforeNationalNumber.lastIndexOf(this.nationalPrefixExtracted));
        }
        if (this.nationalPrefixExtracted.equals(removeNationalPrefixFromNationalNumber())) {
            return false;
        }
        return true;
    }

    private boolean isDigitOrLeadingPlusSign(char c) {
        if (Character.isDigit(c)) {
            return true;
        }
        return this.accruedInput.length() == 1 && PhoneNumberUtil.PLUS_CHARS_PATTERN.matcher(Character.toString(c)).matches();
    }

    String attemptToFormatAccruedDigits() {
        for (NumberFormat numberFormat : this.possibleFormats) {
            Matcher matcher = this.regexCache.getPatternForRegex(numberFormat.getPattern()).matcher(this.nationalNumber);
            if (matcher.matches()) {
                this.shouldAddSpaceAfterNationalPrefix = NATIONAL_PREFIX_SEPARATORS_PATTERN.matcher(numberFormat.getNationalPrefixFormattingRule()).find();
                return appendNationalNumber(matcher.replaceAll(numberFormat.getFormat()));
            }
        }
        return "";
    }

    public int getRememberedPosition() {
        int i = 0;
        if (!this.ableToFormat) {
            return this.originalPosition;
        }
        int i2 = 0;
        while (i2 < this.positionToRemember && i < this.currentOutput.length()) {
            if (this.accruedInputWithoutFormatting.charAt(i2) == this.currentOutput.charAt(i)) {
                i2++;
            }
            i++;
        }
        return i;
    }

    private String appendNationalNumber(String str) {
        int length = this.prefixBeforeNationalNumber.length();
        if (!this.shouldAddSpaceAfterNationalPrefix || length <= 0 || this.prefixBeforeNationalNumber.charAt(length - 1) == SEPARATOR_BEFORE_NATIONAL_NUMBER) {
            return this.prefixBeforeNationalNumber + str;
        }
        return new String(this.prefixBeforeNationalNumber) + SEPARATOR_BEFORE_NATIONAL_NUMBER + str;
    }

    private String attemptToChooseFormattingPattern() {
        if (this.nationalNumber.length() < 3) {
            return appendNationalNumber(this.nationalNumber.toString());
        }
        getAvailableFormats(this.nationalNumber.substring(0, 3));
        return maybeCreateNewTemplate() ? inputAccruedNationalNumber() : this.accruedInput.toString();
    }

    private String inputAccruedNationalNumber() {
        int length = this.nationalNumber.length();
        if (length <= 0) {
            return this.prefixBeforeNationalNumber.toString();
        }
        String str = "";
        for (int i = 0; i < length; i++) {
            str = inputDigitHelper(this.nationalNumber.charAt(i));
        }
        return this.ableToFormat ? appendNationalNumber(str) : this.accruedInput.toString();
    }

    private boolean isNanpaNumberWithNationalPrefix() {
        return this.currentMetaData.getCountryCode() == 1 && this.nationalNumber.charAt(0) == DialpadAction.ONE && this.nationalNumber.charAt(1) != DialpadAction.ZERO && this.nationalNumber.charAt(1) != DialpadAction.ONE;
    }

    private String removeNationalPrefixFromNationalNumber() {
        int i = 1;
        if (isNanpaNumberWithNationalPrefix()) {
            this.prefixBeforeNationalNumber.append(DialpadAction.ONE).append(SEPARATOR_BEFORE_NATIONAL_NUMBER);
            this.isCompleteNumber = true;
        } else {
            if (this.currentMetaData.hasNationalPrefixForParsing()) {
                Matcher matcher = this.regexCache.getPatternForRegex(this.currentMetaData.getNationalPrefixForParsing()).matcher(this.nationalNumber);
                if (matcher.lookingAt()) {
                    this.isCompleteNumber = true;
                    i = matcher.end();
                    this.prefixBeforeNationalNumber.append(this.nationalNumber.substring(0, i));
                }
            }
            i = 0;
        }
        String substring = this.nationalNumber.substring(0, i);
        this.nationalNumber.delete(0, i);
        return substring;
    }

    private boolean attemptToExtractIdd() {
        Matcher matcher = this.regexCache.getPatternForRegex("\\+|" + this.currentMetaData.getInternationalPrefix()).matcher(this.accruedInputWithoutFormatting);
        if (!matcher.lookingAt()) {
            return false;
        }
        this.isCompleteNumber = true;
        int end = matcher.end();
        this.nationalNumber.setLength(0);
        this.nationalNumber.append(this.accruedInputWithoutFormatting.substring(end));
        this.prefixBeforeNationalNumber.setLength(0);
        this.prefixBeforeNationalNumber.append(this.accruedInputWithoutFormatting.substring(0, end));
        if (this.accruedInputWithoutFormatting.charAt(0) == '+') {
            return true;
        }
        this.prefixBeforeNationalNumber.append(SEPARATOR_BEFORE_NATIONAL_NUMBER);
        return true;
    }

    private boolean attemptToExtractCountryCallingCode() {
        if (this.nationalNumber.length() == 0) {
            return false;
        }
        CharSequence stringBuilder = new StringBuilder();
        int extractCountryCode = this.phoneUtil.extractCountryCode(this.nationalNumber, stringBuilder);
        if (extractCountryCode == 0) {
            return false;
        }
        this.nationalNumber.setLength(0);
        this.nationalNumber.append(stringBuilder);
        String regionCodeForCountryCode = this.phoneUtil.getRegionCodeForCountryCode(extractCountryCode);
        if (PhoneNumberUtil.REGION_CODE_FOR_NON_GEO_ENTITY.equals(regionCodeForCountryCode)) {
            this.currentMetaData = this.phoneUtil.getMetadataForNonGeographicalRegion(extractCountryCode);
        } else if (!regionCodeForCountryCode.equals(this.defaultCountry)) {
            this.currentMetaData = getMetadataForRegion(regionCodeForCountryCode);
        }
        this.prefixBeforeNationalNumber.append(Integer.toString(extractCountryCode)).append(SEPARATOR_BEFORE_NATIONAL_NUMBER);
        return true;
    }

    private char normalizeAndAccrueDigitsAndPlusSign(char c, boolean z) {
        if (c == '+') {
            this.accruedInputWithoutFormatting.append(c);
        } else {
            c = Character.forDigit(Character.digit(c, 10), 10);
            this.accruedInputWithoutFormatting.append(c);
            this.nationalNumber.append(c);
        }
        if (z) {
            this.positionToRemember = this.accruedInputWithoutFormatting.length();
        }
        return c;
    }

    private String inputDigitHelper(char c) {
        Matcher matcher = DIGIT_PATTERN.matcher(this.formattingTemplate);
        if (matcher.find(this.lastMatchPosition)) {
            String replaceFirst = matcher.replaceFirst(Character.toString(c));
            this.formattingTemplate.replace(0, replaceFirst.length(), replaceFirst);
            this.lastMatchPosition = matcher.start();
            return this.formattingTemplate.substring(0, this.lastMatchPosition + 1);
        }
        if (this.possibleFormats.size() == 1) {
            this.ableToFormat = false;
        }
        this.currentFormattingPattern = "";
        return this.accruedInput.toString();
    }
}
