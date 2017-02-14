package com.meizu.i18n.phonenumbers;

import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

public final class Phonemetadata {

    public static class NumberFormat implements Externalizable {
        private static final long serialVersionUID = 1;
        private String domesticCarrierCodeFormattingRule_ = "";
        private String format_ = "";
        private boolean hasDomesticCarrierCodeFormattingRule;
        private boolean hasFormat;
        private boolean hasNationalPrefixFormattingRule;
        private boolean hasNationalPrefixOptionalWhenFormatting;
        private boolean hasPattern;
        private List<String> leadingDigitsPattern_ = new ArrayList();
        private String nationalPrefixFormattingRule_ = "";
        private boolean nationalPrefixOptionalWhenFormatting_ = false;
        private String pattern_ = "";

        public static final class Builder extends NumberFormat {
            public NumberFormat build() {
                return this;
            }
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public boolean hasPattern() {
            return this.hasPattern;
        }

        public String getPattern() {
            return this.pattern_;
        }

        public NumberFormat setPattern(String str) {
            this.hasPattern = true;
            this.pattern_ = str;
            return this;
        }

        public boolean hasFormat() {
            return this.hasFormat;
        }

        public String getFormat() {
            return this.format_;
        }

        public NumberFormat setFormat(String str) {
            this.hasFormat = true;
            this.format_ = str;
            return this;
        }

        public List<String> leadingDigitPatterns() {
            return this.leadingDigitsPattern_;
        }

        public int leadingDigitsPatternSize() {
            return this.leadingDigitsPattern_.size();
        }

        public String getLeadingDigitsPattern(int i) {
            return (String) this.leadingDigitsPattern_.get(i);
        }

        public NumberFormat addLeadingDigitsPattern(String str) {
            if (str == null) {
                throw new NullPointerException();
            }
            this.leadingDigitsPattern_.add(str);
            return this;
        }

        public boolean hasNationalPrefixFormattingRule() {
            return this.hasNationalPrefixFormattingRule;
        }

        public String getNationalPrefixFormattingRule() {
            return this.nationalPrefixFormattingRule_;
        }

        public NumberFormat setNationalPrefixFormattingRule(String str) {
            this.hasNationalPrefixFormattingRule = true;
            this.nationalPrefixFormattingRule_ = str;
            return this;
        }

        public NumberFormat clearNationalPrefixFormattingRule() {
            this.hasNationalPrefixFormattingRule = false;
            this.nationalPrefixFormattingRule_ = "";
            return this;
        }

        public boolean hasNationalPrefixOptionalWhenFormatting() {
            return this.hasNationalPrefixOptionalWhenFormatting;
        }

        public boolean isNationalPrefixOptionalWhenFormatting() {
            return this.nationalPrefixOptionalWhenFormatting_;
        }

        public NumberFormat setNationalPrefixOptionalWhenFormatting(boolean z) {
            this.hasNationalPrefixOptionalWhenFormatting = true;
            this.nationalPrefixOptionalWhenFormatting_ = z;
            return this;
        }

        public boolean hasDomesticCarrierCodeFormattingRule() {
            return this.hasDomesticCarrierCodeFormattingRule;
        }

        public String getDomesticCarrierCodeFormattingRule() {
            return this.domesticCarrierCodeFormattingRule_;
        }

        public NumberFormat setDomesticCarrierCodeFormattingRule(String str) {
            this.hasDomesticCarrierCodeFormattingRule = true;
            this.domesticCarrierCodeFormattingRule_ = str;
            return this;
        }

        public NumberFormat mergeFrom(NumberFormat numberFormat) {
            if (numberFormat.hasPattern()) {
                setPattern(numberFormat.getPattern());
            }
            if (numberFormat.hasFormat()) {
                setFormat(numberFormat.getFormat());
            }
            int leadingDigitsPatternSize = numberFormat.leadingDigitsPatternSize();
            for (int i = 0; i < leadingDigitsPatternSize; i++) {
                addLeadingDigitsPattern(numberFormat.getLeadingDigitsPattern(i));
            }
            if (numberFormat.hasNationalPrefixFormattingRule()) {
                setNationalPrefixFormattingRule(numberFormat.getNationalPrefixFormattingRule());
            }
            if (numberFormat.hasDomesticCarrierCodeFormattingRule()) {
                setDomesticCarrierCodeFormattingRule(numberFormat.getDomesticCarrierCodeFormattingRule());
            }
            setNationalPrefixOptionalWhenFormatting(numberFormat.isNationalPrefixOptionalWhenFormatting());
            return this;
        }

        public void writeExternal(ObjectOutput objectOutput) {
            objectOutput.writeUTF(this.pattern_);
            objectOutput.writeUTF(this.format_);
            int leadingDigitsPatternSize = leadingDigitsPatternSize();
            objectOutput.writeInt(leadingDigitsPatternSize);
            for (int i = 0; i < leadingDigitsPatternSize; i++) {
                objectOutput.writeUTF((String) this.leadingDigitsPattern_.get(i));
            }
            objectOutput.writeBoolean(this.hasNationalPrefixFormattingRule);
            if (this.hasNationalPrefixFormattingRule) {
                objectOutput.writeUTF(this.nationalPrefixFormattingRule_);
            }
            objectOutput.writeBoolean(this.hasDomesticCarrierCodeFormattingRule);
            if (this.hasDomesticCarrierCodeFormattingRule) {
                objectOutput.writeUTF(this.domesticCarrierCodeFormattingRule_);
            }
            objectOutput.writeBoolean(this.nationalPrefixOptionalWhenFormatting_);
        }

        public void readExternal(ObjectInput objectInput) {
            setPattern(objectInput.readUTF());
            setFormat(objectInput.readUTF());
            int readInt = objectInput.readInt();
            for (int i = 0; i < readInt; i++) {
                this.leadingDigitsPattern_.add(objectInput.readUTF());
            }
            if (objectInput.readBoolean()) {
                setNationalPrefixFormattingRule(objectInput.readUTF());
            }
            if (objectInput.readBoolean()) {
                setDomesticCarrierCodeFormattingRule(objectInput.readUTF());
            }
            setNationalPrefixOptionalWhenFormatting(objectInput.readBoolean());
        }
    }

    public static class PhoneMetadata implements Externalizable {
        private static final long serialVersionUID = 1;
        private int countryCode_ = 0;
        private PhoneNumberDesc emergency_ = null;
        private PhoneNumberDesc fixedLine_ = null;
        private PhoneNumberDesc generalDesc_ = null;
        private boolean hasCountryCode;
        private boolean hasEmergency;
        private boolean hasFixedLine;
        private boolean hasGeneralDesc;
        private boolean hasId;
        private boolean hasInternationalPrefix;
        private boolean hasLeadingDigits;
        private boolean hasLeadingZeroPossible;
        private boolean hasMainCountryForCode;
        private boolean hasMobile;
        private boolean hasNationalPrefix;
        private boolean hasNationalPrefixForParsing;
        private boolean hasNationalPrefixTransformRule;
        private boolean hasNoInternationalDialling;
        private boolean hasPager;
        private boolean hasPersonalNumber;
        private boolean hasPreferredExtnPrefix;
        private boolean hasPreferredInternationalPrefix;
        private boolean hasPremiumRate;
        private boolean hasSameMobileAndFixedLinePattern;
        private boolean hasSharedCost;
        private boolean hasTollFree;
        private boolean hasUan;
        private boolean hasVoicemail;
        private boolean hasVoip;
        private String id_ = "";
        private String internationalPrefix_ = "";
        private List<NumberFormat> intlNumberFormat_ = new ArrayList();
        private String leadingDigits_ = "";
        private boolean leadingZeroPossible_ = false;
        private boolean mainCountryForCode_ = false;
        private PhoneNumberDesc mobile_ = null;
        private String nationalPrefixForParsing_ = "";
        private String nationalPrefixTransformRule_ = "";
        private String nationalPrefix_ = "";
        private PhoneNumberDesc noInternationalDialling_ = null;
        private List<NumberFormat> numberFormat_ = new ArrayList();
        private PhoneNumberDesc pager_ = null;
        private PhoneNumberDesc personalNumber_ = null;
        private String preferredExtnPrefix_ = "";
        private String preferredInternationalPrefix_ = "";
        private PhoneNumberDesc premiumRate_ = null;
        private boolean sameMobileAndFixedLinePattern_ = false;
        private PhoneNumberDesc sharedCost_ = null;
        private PhoneNumberDesc tollFree_ = null;
        private PhoneNumberDesc uan_ = null;
        private PhoneNumberDesc voicemail_ = null;
        private PhoneNumberDesc voip_ = null;

        public static final class Builder extends PhoneMetadata {
            public PhoneMetadata build() {
                return this;
            }
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public boolean hasGeneralDesc() {
            return this.hasGeneralDesc;
        }

        public PhoneNumberDesc getGeneralDesc() {
            return this.generalDesc_;
        }

        public PhoneMetadata setGeneralDesc(PhoneNumberDesc phoneNumberDesc) {
            if (phoneNumberDesc == null) {
                throw new NullPointerException();
            }
            this.hasGeneralDesc = true;
            this.generalDesc_ = phoneNumberDesc;
            return this;
        }

        public boolean hasFixedLine() {
            return this.hasFixedLine;
        }

        public PhoneNumberDesc getFixedLine() {
            return this.fixedLine_;
        }

        public PhoneMetadata setFixedLine(PhoneNumberDesc phoneNumberDesc) {
            if (phoneNumberDesc == null) {
                throw new NullPointerException();
            }
            this.hasFixedLine = true;
            this.fixedLine_ = phoneNumberDesc;
            return this;
        }

        public boolean hasMobile() {
            return this.hasMobile;
        }

        public PhoneNumberDesc getMobile() {
            return this.mobile_;
        }

        public PhoneMetadata setMobile(PhoneNumberDesc phoneNumberDesc) {
            if (phoneNumberDesc == null) {
                throw new NullPointerException();
            }
            this.hasMobile = true;
            this.mobile_ = phoneNumberDesc;
            return this;
        }

        public boolean hasTollFree() {
            return this.hasTollFree;
        }

        public PhoneNumberDesc getTollFree() {
            return this.tollFree_;
        }

        public PhoneMetadata setTollFree(PhoneNumberDesc phoneNumberDesc) {
            if (phoneNumberDesc == null) {
                throw new NullPointerException();
            }
            this.hasTollFree = true;
            this.tollFree_ = phoneNumberDesc;
            return this;
        }

        public boolean hasPremiumRate() {
            return this.hasPremiumRate;
        }

        public PhoneNumberDesc getPremiumRate() {
            return this.premiumRate_;
        }

        public PhoneMetadata setPremiumRate(PhoneNumberDesc phoneNumberDesc) {
            if (phoneNumberDesc == null) {
                throw new NullPointerException();
            }
            this.hasPremiumRate = true;
            this.premiumRate_ = phoneNumberDesc;
            return this;
        }

        public boolean hasSharedCost() {
            return this.hasSharedCost;
        }

        public PhoneNumberDesc getSharedCost() {
            return this.sharedCost_;
        }

        public PhoneMetadata setSharedCost(PhoneNumberDesc phoneNumberDesc) {
            if (phoneNumberDesc == null) {
                throw new NullPointerException();
            }
            this.hasSharedCost = true;
            this.sharedCost_ = phoneNumberDesc;
            return this;
        }

        public boolean hasPersonalNumber() {
            return this.hasPersonalNumber;
        }

        public PhoneNumberDesc getPersonalNumber() {
            return this.personalNumber_;
        }

        public PhoneMetadata setPersonalNumber(PhoneNumberDesc phoneNumberDesc) {
            if (phoneNumberDesc == null) {
                throw new NullPointerException();
            }
            this.hasPersonalNumber = true;
            this.personalNumber_ = phoneNumberDesc;
            return this;
        }

        public boolean hasVoip() {
            return this.hasVoip;
        }

        public PhoneNumberDesc getVoip() {
            return this.voip_;
        }

        public PhoneMetadata setVoip(PhoneNumberDesc phoneNumberDesc) {
            if (phoneNumberDesc == null) {
                throw new NullPointerException();
            }
            this.hasVoip = true;
            this.voip_ = phoneNumberDesc;
            return this;
        }

        public boolean hasPager() {
            return this.hasPager;
        }

        public PhoneNumberDesc getPager() {
            return this.pager_;
        }

        public PhoneMetadata setPager(PhoneNumberDesc phoneNumberDesc) {
            if (phoneNumberDesc == null) {
                throw new NullPointerException();
            }
            this.hasPager = true;
            this.pager_ = phoneNumberDesc;
            return this;
        }

        public boolean hasUan() {
            return this.hasUan;
        }

        public PhoneNumberDesc getUan() {
            return this.uan_;
        }

        public PhoneMetadata setUan(PhoneNumberDesc phoneNumberDesc) {
            if (phoneNumberDesc == null) {
                throw new NullPointerException();
            }
            this.hasUan = true;
            this.uan_ = phoneNumberDesc;
            return this;
        }

        public boolean hasVoicemail() {
            return this.hasVoicemail;
        }

        public PhoneNumberDesc getVoicemail() {
            return this.voicemail_;
        }

        public PhoneMetadata setVoicemail(PhoneNumberDesc phoneNumberDesc) {
            if (phoneNumberDesc == null) {
                throw new NullPointerException();
            }
            this.hasVoicemail = true;
            this.voicemail_ = phoneNumberDesc;
            return this;
        }

        public boolean hasEmergency() {
            return this.hasEmergency;
        }

        public PhoneNumberDesc getEmergency() {
            return this.emergency_;
        }

        public PhoneMetadata setEmergency(PhoneNumberDesc phoneNumberDesc) {
            if (phoneNumberDesc == null) {
                throw new NullPointerException();
            }
            this.hasEmergency = true;
            this.emergency_ = phoneNumberDesc;
            return this;
        }

        public boolean hasNoInternationalDialling() {
            return this.hasNoInternationalDialling;
        }

        public PhoneNumberDesc getNoInternationalDialling() {
            return this.noInternationalDialling_;
        }

        public PhoneMetadata setNoInternationalDialling(PhoneNumberDesc phoneNumberDesc) {
            if (phoneNumberDesc == null) {
                throw new NullPointerException();
            }
            this.hasNoInternationalDialling = true;
            this.noInternationalDialling_ = phoneNumberDesc;
            return this;
        }

        public boolean hasId() {
            return this.hasId;
        }

        public String getId() {
            return this.id_;
        }

        public PhoneMetadata setId(String str) {
            this.hasId = true;
            this.id_ = str;
            return this;
        }

        public boolean hasCountryCode() {
            return this.hasCountryCode;
        }

        public int getCountryCode() {
            return this.countryCode_;
        }

        public PhoneMetadata setCountryCode(int i) {
            this.hasCountryCode = true;
            this.countryCode_ = i;
            return this;
        }

        public boolean hasInternationalPrefix() {
            return this.hasInternationalPrefix;
        }

        public String getInternationalPrefix() {
            return this.internationalPrefix_;
        }

        public PhoneMetadata setInternationalPrefix(String str) {
            this.hasInternationalPrefix = true;
            this.internationalPrefix_ = str;
            return this;
        }

        public boolean hasPreferredInternationalPrefix() {
            return this.hasPreferredInternationalPrefix;
        }

        public String getPreferredInternationalPrefix() {
            return this.preferredInternationalPrefix_;
        }

        public PhoneMetadata setPreferredInternationalPrefix(String str) {
            this.hasPreferredInternationalPrefix = true;
            this.preferredInternationalPrefix_ = str;
            return this;
        }

        public boolean hasNationalPrefix() {
            return this.hasNationalPrefix;
        }

        public String getNationalPrefix() {
            return this.nationalPrefix_;
        }

        public PhoneMetadata setNationalPrefix(String str) {
            this.hasNationalPrefix = true;
            this.nationalPrefix_ = str;
            return this;
        }

        public boolean hasPreferredExtnPrefix() {
            return this.hasPreferredExtnPrefix;
        }

        public String getPreferredExtnPrefix() {
            return this.preferredExtnPrefix_;
        }

        public PhoneMetadata setPreferredExtnPrefix(String str) {
            this.hasPreferredExtnPrefix = true;
            this.preferredExtnPrefix_ = str;
            return this;
        }

        public boolean hasNationalPrefixForParsing() {
            return this.hasNationalPrefixForParsing;
        }

        public String getNationalPrefixForParsing() {
            return this.nationalPrefixForParsing_;
        }

        public PhoneMetadata setNationalPrefixForParsing(String str) {
            this.hasNationalPrefixForParsing = true;
            this.nationalPrefixForParsing_ = str;
            return this;
        }

        public boolean hasNationalPrefixTransformRule() {
            return this.hasNationalPrefixTransformRule;
        }

        public String getNationalPrefixTransformRule() {
            return this.nationalPrefixTransformRule_;
        }

        public PhoneMetadata setNationalPrefixTransformRule(String str) {
            this.hasNationalPrefixTransformRule = true;
            this.nationalPrefixTransformRule_ = str;
            return this;
        }

        public boolean hasSameMobileAndFixedLinePattern() {
            return this.hasSameMobileAndFixedLinePattern;
        }

        public boolean isSameMobileAndFixedLinePattern() {
            return this.sameMobileAndFixedLinePattern_;
        }

        public PhoneMetadata setSameMobileAndFixedLinePattern(boolean z) {
            this.hasSameMobileAndFixedLinePattern = true;
            this.sameMobileAndFixedLinePattern_ = z;
            return this;
        }

        public List<NumberFormat> numberFormats() {
            return this.numberFormat_;
        }

        public int numberFormatSize() {
            return this.numberFormat_.size();
        }

        public NumberFormat getNumberFormat(int i) {
            return (NumberFormat) this.numberFormat_.get(i);
        }

        public PhoneMetadata addNumberFormat(NumberFormat numberFormat) {
            if (numberFormat == null) {
                throw new NullPointerException();
            }
            this.numberFormat_.add(numberFormat);
            return this;
        }

        public List<NumberFormat> intlNumberFormats() {
            return this.intlNumberFormat_;
        }

        public int intlNumberFormatSize() {
            return this.intlNumberFormat_.size();
        }

        public NumberFormat getIntlNumberFormat(int i) {
            return (NumberFormat) this.intlNumberFormat_.get(i);
        }

        public PhoneMetadata addIntlNumberFormat(NumberFormat numberFormat) {
            if (numberFormat == null) {
                throw new NullPointerException();
            }
            this.intlNumberFormat_.add(numberFormat);
            return this;
        }

        public PhoneMetadata clearIntlNumberFormat() {
            this.intlNumberFormat_.clear();
            return this;
        }

        public boolean hasMainCountryForCode() {
            return this.hasMainCountryForCode;
        }

        public boolean isMainCountryForCode() {
            return this.mainCountryForCode_;
        }

        public boolean getMainCountryForCode() {
            return this.mainCountryForCode_;
        }

        public PhoneMetadata setMainCountryForCode(boolean z) {
            this.hasMainCountryForCode = true;
            this.mainCountryForCode_ = z;
            return this;
        }

        public boolean hasLeadingDigits() {
            return this.hasLeadingDigits;
        }

        public String getLeadingDigits() {
            return this.leadingDigits_;
        }

        public PhoneMetadata setLeadingDigits(String str) {
            this.hasLeadingDigits = true;
            this.leadingDigits_ = str;
            return this;
        }

        public boolean hasLeadingZeroPossible() {
            return this.hasLeadingZeroPossible;
        }

        public boolean isLeadingZeroPossible() {
            return this.leadingZeroPossible_;
        }

        public PhoneMetadata setLeadingZeroPossible(boolean z) {
            this.hasLeadingZeroPossible = true;
            this.leadingZeroPossible_ = z;
            return this;
        }

        public void writeExternal(ObjectOutput objectOutput) {
            int i;
            int i2 = 0;
            objectOutput.writeBoolean(this.hasGeneralDesc);
            if (this.hasGeneralDesc) {
                this.generalDesc_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasFixedLine);
            if (this.hasFixedLine) {
                this.fixedLine_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasMobile);
            if (this.hasMobile) {
                this.mobile_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasTollFree);
            if (this.hasTollFree) {
                this.tollFree_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasPremiumRate);
            if (this.hasPremiumRate) {
                this.premiumRate_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasSharedCost);
            if (this.hasSharedCost) {
                this.sharedCost_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasPersonalNumber);
            if (this.hasPersonalNumber) {
                this.personalNumber_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasVoip);
            if (this.hasVoip) {
                this.voip_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasPager);
            if (this.hasPager) {
                this.pager_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasUan);
            if (this.hasUan) {
                this.uan_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasVoicemail);
            if (this.hasVoicemail) {
                this.voicemail_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasEmergency);
            if (this.hasEmergency) {
                this.emergency_.writeExternal(objectOutput);
            }
            objectOutput.writeBoolean(this.hasNoInternationalDialling);
            if (this.hasNoInternationalDialling) {
                this.noInternationalDialling_.writeExternal(objectOutput);
            }
            objectOutput.writeUTF(this.id_);
            objectOutput.writeInt(this.countryCode_);
            objectOutput.writeUTF(this.internationalPrefix_);
            objectOutput.writeBoolean(this.hasPreferredInternationalPrefix);
            if (this.hasPreferredInternationalPrefix) {
                objectOutput.writeUTF(this.preferredInternationalPrefix_);
            }
            objectOutput.writeBoolean(this.hasNationalPrefix);
            if (this.hasNationalPrefix) {
                objectOutput.writeUTF(this.nationalPrefix_);
            }
            objectOutput.writeBoolean(this.hasPreferredExtnPrefix);
            if (this.hasPreferredExtnPrefix) {
                objectOutput.writeUTF(this.preferredExtnPrefix_);
            }
            objectOutput.writeBoolean(this.hasNationalPrefixForParsing);
            if (this.hasNationalPrefixForParsing) {
                objectOutput.writeUTF(this.nationalPrefixForParsing_);
            }
            objectOutput.writeBoolean(this.hasNationalPrefixTransformRule);
            if (this.hasNationalPrefixTransformRule) {
                objectOutput.writeUTF(this.nationalPrefixTransformRule_);
            }
            objectOutput.writeBoolean(this.sameMobileAndFixedLinePattern_);
            int numberFormatSize = numberFormatSize();
            objectOutput.writeInt(numberFormatSize);
            for (i = 0; i < numberFormatSize; i++) {
                ((NumberFormat) this.numberFormat_.get(i)).writeExternal(objectOutput);
            }
            i = intlNumberFormatSize();
            objectOutput.writeInt(i);
            while (i2 < i) {
                ((NumberFormat) this.intlNumberFormat_.get(i2)).writeExternal(objectOutput);
                i2++;
            }
            objectOutput.writeBoolean(this.mainCountryForCode_);
            objectOutput.writeBoolean(this.hasLeadingDigits);
            if (this.hasLeadingDigits) {
                objectOutput.writeUTF(this.leadingDigits_);
            }
            objectOutput.writeBoolean(this.leadingZeroPossible_);
        }

        public void readExternal(ObjectInput objectInput) {
            int i;
            int i2 = 0;
            if (objectInput.readBoolean()) {
                PhoneNumberDesc phoneNumberDesc = new PhoneNumberDesc();
                phoneNumberDesc.readExternal(objectInput);
                setGeneralDesc(phoneNumberDesc);
            }
            if (objectInput.readBoolean()) {
                phoneNumberDesc = new PhoneNumberDesc();
                phoneNumberDesc.readExternal(objectInput);
                setFixedLine(phoneNumberDesc);
            }
            if (objectInput.readBoolean()) {
                phoneNumberDesc = new PhoneNumberDesc();
                phoneNumberDesc.readExternal(objectInput);
                setMobile(phoneNumberDesc);
            }
            if (objectInput.readBoolean()) {
                phoneNumberDesc = new PhoneNumberDesc();
                phoneNumberDesc.readExternal(objectInput);
                setTollFree(phoneNumberDesc);
            }
            if (objectInput.readBoolean()) {
                phoneNumberDesc = new PhoneNumberDesc();
                phoneNumberDesc.readExternal(objectInput);
                setPremiumRate(phoneNumberDesc);
            }
            if (objectInput.readBoolean()) {
                phoneNumberDesc = new PhoneNumberDesc();
                phoneNumberDesc.readExternal(objectInput);
                setSharedCost(phoneNumberDesc);
            }
            if (objectInput.readBoolean()) {
                phoneNumberDesc = new PhoneNumberDesc();
                phoneNumberDesc.readExternal(objectInput);
                setPersonalNumber(phoneNumberDesc);
            }
            if (objectInput.readBoolean()) {
                phoneNumberDesc = new PhoneNumberDesc();
                phoneNumberDesc.readExternal(objectInput);
                setVoip(phoneNumberDesc);
            }
            if (objectInput.readBoolean()) {
                phoneNumberDesc = new PhoneNumberDesc();
                phoneNumberDesc.readExternal(objectInput);
                setPager(phoneNumberDesc);
            }
            if (objectInput.readBoolean()) {
                phoneNumberDesc = new PhoneNumberDesc();
                phoneNumberDesc.readExternal(objectInput);
                setUan(phoneNumberDesc);
            }
            if (objectInput.readBoolean()) {
                phoneNumberDesc = new PhoneNumberDesc();
                phoneNumberDesc.readExternal(objectInput);
                setVoicemail(phoneNumberDesc);
            }
            if (objectInput.readBoolean()) {
                phoneNumberDesc = new PhoneNumberDesc();
                phoneNumberDesc.readExternal(objectInput);
                setEmergency(phoneNumberDesc);
            }
            if (objectInput.readBoolean()) {
                phoneNumberDesc = new PhoneNumberDesc();
                phoneNumberDesc.readExternal(objectInput);
                setNoInternationalDialling(phoneNumberDesc);
            }
            setId(objectInput.readUTF());
            setCountryCode(objectInput.readInt());
            setInternationalPrefix(objectInput.readUTF());
            if (objectInput.readBoolean()) {
                setPreferredInternationalPrefix(objectInput.readUTF());
            }
            if (objectInput.readBoolean()) {
                setNationalPrefix(objectInput.readUTF());
            }
            if (objectInput.readBoolean()) {
                setPreferredExtnPrefix(objectInput.readUTF());
            }
            if (objectInput.readBoolean()) {
                setNationalPrefixForParsing(objectInput.readUTF());
            }
            if (objectInput.readBoolean()) {
                setNationalPrefixTransformRule(objectInput.readUTF());
            }
            setSameMobileAndFixedLinePattern(objectInput.readBoolean());
            int readInt = objectInput.readInt();
            for (i = 0; i < readInt; i++) {
                NumberFormat numberFormat = new NumberFormat();
                numberFormat.readExternal(objectInput);
                this.numberFormat_.add(numberFormat);
            }
            i = objectInput.readInt();
            while (i2 < i) {
                NumberFormat numberFormat2 = new NumberFormat();
                numberFormat2.readExternal(objectInput);
                this.intlNumberFormat_.add(numberFormat2);
                i2++;
            }
            setMainCountryForCode(objectInput.readBoolean());
            if (objectInput.readBoolean()) {
                setLeadingDigits(objectInput.readUTF());
            }
            setLeadingZeroPossible(objectInput.readBoolean());
        }
    }

    public static class PhoneMetadataCollection implements Externalizable {
        private static final long serialVersionUID = 1;
        private List<PhoneMetadata> metadata_ = new ArrayList();

        public static final class Builder extends PhoneMetadataCollection {
            public PhoneMetadataCollection build() {
                return this;
            }
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public List<PhoneMetadata> getMetadataList() {
            return this.metadata_;
        }

        public int getMetadataCount() {
            return this.metadata_.size();
        }

        public PhoneMetadataCollection addMetadata(PhoneMetadata phoneMetadata) {
            if (phoneMetadata == null) {
                throw new NullPointerException();
            }
            this.metadata_.add(phoneMetadata);
            return this;
        }

        public void writeExternal(ObjectOutput objectOutput) {
            int metadataCount = getMetadataCount();
            objectOutput.writeInt(metadataCount);
            for (int i = 0; i < metadataCount; i++) {
                ((PhoneMetadata) this.metadata_.get(i)).writeExternal(objectOutput);
            }
        }

        public void readExternal(ObjectInput objectInput) {
            int readInt = objectInput.readInt();
            for (int i = 0; i < readInt; i++) {
                PhoneMetadata phoneMetadata = new PhoneMetadata();
                phoneMetadata.readExternal(objectInput);
                this.metadata_.add(phoneMetadata);
            }
        }

        public PhoneMetadataCollection clear() {
            this.metadata_.clear();
            return this;
        }
    }

    public static class PhoneNumberDesc implements Externalizable {
        private static final long serialVersionUID = 1;
        private String exampleNumber_ = "";
        private boolean hasExampleNumber;
        private boolean hasNationalNumberPattern;
        private boolean hasPossibleNumberPattern;
        private String nationalNumberPattern_ = "";
        private String possibleNumberPattern_ = "";

        public static final class Builder extends PhoneNumberDesc {
            public PhoneNumberDesc build() {
                return this;
            }
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public boolean hasNationalNumberPattern() {
            return this.hasNationalNumberPattern;
        }

        public String getNationalNumberPattern() {
            return this.nationalNumberPattern_;
        }

        public PhoneNumberDesc setNationalNumberPattern(String str) {
            this.hasNationalNumberPattern = true;
            this.nationalNumberPattern_ = str;
            return this;
        }

        public boolean hasPossibleNumberPattern() {
            return this.hasPossibleNumberPattern;
        }

        public String getPossibleNumberPattern() {
            return this.possibleNumberPattern_;
        }

        public PhoneNumberDesc setPossibleNumberPattern(String str) {
            this.hasPossibleNumberPattern = true;
            this.possibleNumberPattern_ = str;
            return this;
        }

        public boolean hasExampleNumber() {
            return this.hasExampleNumber;
        }

        public String getExampleNumber() {
            return this.exampleNumber_;
        }

        public PhoneNumberDesc setExampleNumber(String str) {
            this.hasExampleNumber = true;
            this.exampleNumber_ = str;
            return this;
        }

        public PhoneNumberDesc mergeFrom(PhoneNumberDesc phoneNumberDesc) {
            if (phoneNumberDesc.hasNationalNumberPattern()) {
                setNationalNumberPattern(phoneNumberDesc.getNationalNumberPattern());
            }
            if (phoneNumberDesc.hasPossibleNumberPattern()) {
                setPossibleNumberPattern(phoneNumberDesc.getPossibleNumberPattern());
            }
            if (phoneNumberDesc.hasExampleNumber()) {
                setExampleNumber(phoneNumberDesc.getExampleNumber());
            }
            return this;
        }

        public boolean exactlySameAs(PhoneNumberDesc phoneNumberDesc) {
            return this.nationalNumberPattern_.equals(phoneNumberDesc.nationalNumberPattern_) && this.possibleNumberPattern_.equals(phoneNumberDesc.possibleNumberPattern_) && this.exampleNumber_.equals(phoneNumberDesc.exampleNumber_);
        }

        public void writeExternal(ObjectOutput objectOutput) {
            objectOutput.writeBoolean(this.hasNationalNumberPattern);
            if (this.hasNationalNumberPattern) {
                objectOutput.writeUTF(this.nationalNumberPattern_);
            }
            objectOutput.writeBoolean(this.hasPossibleNumberPattern);
            if (this.hasPossibleNumberPattern) {
                objectOutput.writeUTF(this.possibleNumberPattern_);
            }
            objectOutput.writeBoolean(this.hasExampleNumber);
            if (this.hasExampleNumber) {
                objectOutput.writeUTF(this.exampleNumber_);
            }
        }

        public void readExternal(ObjectInput objectInput) {
            if (objectInput.readBoolean()) {
                setNationalNumberPattern(objectInput.readUTF());
            }
            if (objectInput.readBoolean()) {
                setPossibleNumberPattern(objectInput.readUTF());
            }
            if (objectInput.readBoolean()) {
                setExampleNumber(objectInput.readUTF());
            }
        }
    }

    private Phonemetadata() {
    }
}
