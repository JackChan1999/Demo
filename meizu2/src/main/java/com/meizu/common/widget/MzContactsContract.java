package com.meizu.common.widget;

import android.accounts.Account;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.Groups;
import android.provider.ContactsContract.PhoneLookup;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import sdk.meizu.traffic.auth.MzAccountManager;

public final class MzContactsContract {
    public static final String ALLOW_CALLLOGS_PARAM_KEY = "allow_calllogs";
    public static final Uri AUTHORITY_URI_NOTIFY = Uri.parse("content://com.android.contacts.notify");
    public static final String HAS_MORE_KEY = "has_more";
    private static Pattern SPLIT_PATTERN = Pattern.compile("([\\w-\\.]+)@((?:[\\w]+\\.)+)([a-zA-Z]{2,4})|[\\w]+");
    public static final String START_PARAM_KEY = "start";
    public static final String USE_WEIGHT_ORDER = "use_weight_order";

    public static final class MzAccounts {
        public static final Account DEVICES_ONLY_ACCOUNT = new Account("DeviceOnly", "DeviceOnly");
        private static final String DEVICES_ONLY_ACCOUNT_NAME = "DeviceOnly";
        private static final String DEVICES_ONLY_ACCOUNT_TYPE = "DeviceOnly";
        public static final String FLYME_ACCOUNT_TYPE = "com.meizu.account";
        public static final String SINA_ACCOUNT_TYPE = "com.meizu.sns.sina";
        public static final Account VENDER_ACCOUNT = new Account("account.vender", "account.vender");
        private static final String VENDER_ACCOUNT_NAME = "account.vender";
        public static final String VENDER_ACCOUNT_TYPE = "account.vender";

        private MzAccounts() {
        }

        public static Account[] addDeviceOnlyAccount(Account[] accountArr) {
            int i = 0;
            Account[] accountArr2 = new Account[(accountArr.length + 1)];
            accountArr2[0] = DEVICES_ONLY_ACCOUNT;
            int i2 = 1;
            int length = accountArr.length;
            while (i < length) {
                int i3 = i2 + 1;
                accountArr2[i2] = accountArr[i];
                i++;
                i2 = i3;
            }
            return accountArr2;
        }

        public static boolean isFlymeAccount(Account account) {
            if (account == null || !TextUtils.equals(account.type, "com.meizu.account")) {
                return false;
            }
            return true;
        }
    }

    public static final class MzCommonDataKinds {

        public static final class MzEmail {
            public static final Uri CONTENT_GROUP_URI = Uri.withAppendedPath(Email.CONTENT_URI, "group");
            public static final String USE_CUSTOM_ORDER = "use_custom_order";
        }

        public static final class MzEvent {
            public static final int TYPE_LUNAR_BIRTHDAY = 4;

            public static int getTypeResource(Integer num) {
                return Event.getTypeResource(num);
            }
        }

        public static final class MzGroupMembership {
            public static final String GROUP_TITLE = "group_title";
        }

        public static final class MzOrganization {
            public static final String IS_ORGANIZATION_NOTE = "data12";
        }

        public static final class MzPhone {
            public static final Uri CONTENT_GROUP_URI = Uri.withAppendedPath(Phone.CONTENT_URI, "group");
            public static final String CONVERT_LETTERS = "convert_letters";

            private MzPhone() {
            }
        }

        public static final class MzPhoneAndEmail {
            public static final Uri CONTENT_FILTER_URI = Uri.withAppendedPath(CONTENT_URI, "filter");
            public static final Uri CONTENT_GROUP_URI = Uri.withAppendedPath(CONTENT_URI, "group");
            public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/phone_email";
            public static final String CONTENT_TYPE = "vnd.android.cursor.dir/phone_email";
            public static final Uri CONTENT_URI = Uri.withAppendedPath(Data.CONTENT_URI, "phones_emails");

            private MzPhoneAndEmail() {
            }
        }

        private MzCommonDataKinds() {
        }
    }

    public interface MzContactColumns {
        public static final String ADDRESS = "address";
        public static final String DISTANCE = "distance";
        public static final String PHONE_NUMBER = "phone_number";
        public static final String RECORD_TYPE = "record_type";
        public static final String SNS_TYPE = "sns_type";
    }

    public interface MzContactOptionsColumns {
        public static final String ORGANIZATION_NOTE = "organization_note";
    }

    public static class MzContacts implements MzContactColumns {
        public static final Uri CONTENT_EX_GROUP_URI = Uri.withAppendedPath(CONTENT_EX_URI, "group");
        public static final Uri CONTENT_EX_URI = Uri.withAppendedPath(Contacts.CONTENT_URI, "ex");

        public static final class MzPhoto {
            public static final String FORCE_SET_PRIMARY = "data12";
        }
    }

    public static final class MzData {
        public static final Uri CONTENT_FILTER_URI = Uri.withAppendedPath(Data.CONTENT_URI, "filter");

        public static Uri buildUriWithRequestMimetypes(Uri uri, String[] strArr) {
            if (strArr == null || strArr.length < 1) {
                return uri;
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (CharSequence charSequence : strArr) {
                if (stringBuilder.length() > 0) {
                    stringBuilder.append(';');
                }
                if (TextUtils.equals("vnd.android.cursor.item/phone_v2", charSequence)) {
                    stringBuilder.append("phone");
                } else if (TextUtils.equals("vnd.android.cursor.item/email_v2", charSequence)) {
                    stringBuilder.append("email");
                }
            }
            if (stringBuilder.length() > 0) {
                return uri.buildUpon().appendQueryParameter("request_mimes", stringBuilder.toString()).build();
            }
            return uri;
        }
    }

    public static final class MzDirectory {
        public static final Uri CONTENT_NOTIFY_URI = Uri.withAppendedPath(MzContactsContract.AUTHORITY_URI_NOTIFY, "directories");
        public static final String IS_VISIBLE = "is_visible";
        public static final long NET_CONTACT = 2;
    }

    public static final class MzDisplayPhoto {
        public static final String STORE_ORIGINAL = "store_original";
    }

    public interface MzGroupsColumns {
        public static final String ACCOUNT_ORDER = "account_order";
        public static final String REJECTED = "rejected";
        public static final String SUMMARY_DATA_COUNT = "summary_data_count";
        public static final String VIEW_ORDER = "view_order";
    }

    public static final class MzGroups implements BaseColumns, MzGroupsColumns {
        public static final Uri CONTENT_ACCOUNT_URI = Uri.withAppendedPath(Groups.CONTENT_URI, MzAccountManager.PATH_ACCOUNT);
        public static final Uri CONTENT_SUMMARY_FILTER_URI = Uri.withAppendedPath(Groups.CONTENT_SUMMARY_URI, "filter");
        public static final String GROUP_SPLIT_MARK_EXTRA = ",";
        public static final String GROUP_SPLIT_MARK_SLASH = "/";
        public static final String GROUP_SPLIT_MARK_VCARD = ";";
        public static final String GROUP_SPLIT_MARK_XML = "|";

        public static String getGroupTitle(ContentResolver contentResolver, long j) {
            Throwable th;
            Cursor cursor = null;
            if (j <= 0) {
                return null;
            }
            String[] strArr = new String[]{String.valueOf(j)};
            try {
                String string;
                ContentResolver contentResolver2 = contentResolver;
                Cursor query = contentResolver2.query(Groups.CONTENT_URI, new String[]{"title"}, "_id=?", strArr, null);
                if (query != null) {
                    try {
                        if (query.moveToFirst()) {
                            string = query.getString(0);
                            if (query != null) {
                                return string;
                            }
                            query.close();
                            return string;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        cursor = query;
                        if (cursor != null) {
                            cursor.close();
                        }
                        throw th;
                    }
                }
                string = null;
                if (query != null) {
                    return string;
                }
                query.close();
                return string;
            } catch (Throwable th3) {
                th = th3;
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        }
    }

    public static final class MzIntents {
        public static final String EXTRA_MULTIPLE_PICK_DATAS = "com.android.contacts.extra.MULTIPLE_PICK_DATAS";
        public static final String EXTRA_PICK_DATA = "com.android.contacts.extra.PICK_DATA";
        public static final String EXTRA_REQUEST_DATA_IDS = "com.android.contacts.extra.EXTRA_REQUEST_DATA_IDS";

        public static final class MzInsert {
            public static final String SOCIAL_PROFILE = "social_profile";
            public static final String SOCIAL_PROFILE_TYPE = "social_profile_type";
        }

        public static final class MzUI {
            public static final String BROWSE_ALL_CONTACTS_ACTION = "com.android.contacts.action.BROWSE_ALL_CONTACTS";
            public static final String REQUESTED_INFO_TYPE_KEY = "com.android.contacts.extra.REQUESTED_INFO_TYPE";
            public static final int REQUESTED_INFO_TYPE_NONE = -1;
            public static final int REQUESTED_INFO_TYPE_TEXT = 1;
            public static final int REQUESTED_INFO_TYPE_VCARD = 0;
            public static final String REQUESTED_ORIENTATION = "com.android.contacts.extra.REQUESTED_ORIENTATION";
            public static final String SHOULD_INCLUDE_CONTACT_KEY = "com.android.contacts.extra.SHOULD_INCLUDE_CONTACT";
            public static final String SHOULD_INCLUDE_PROFILE_KEY = "com.android.contacts.extra.SHOULD_INCLUDE_PROFILE";
            public static final String SUB_TITLE_EXTRA_KEY = "com.android.contacts.extra.SUB_TITLE_EXTRA";
        }
    }

    public static final class MzNetContacts {
        public static final String AUTHORITY = "com.meizu.netcontactservice.directory";
        public static final String ERROR_CODE_KEY = "error_code";
        public static final int ERROR_CODE_NETWORK_UNAVAILABLE = 1;
        public static final int ERROR_CODE_NO_ADDRESS = 2;
        public static final int ERROR_CODE_NUMBER_INVALIDATE = 3;
        public static final int ERROR_CODE_SUCCESS = 0;
        public static final int ERROR_CODE_UNKNOWN = 4;
        public static final String LINK_DISPLAY_NAME_AND_LABLE = "link_display_name_and_lable";
        public static final String NET_CONTACT_ACCOUNT_TYPE = "com.meizu.netcontactservice";
        public static final String NET_CONTACT_DIRECTORY_TYPE = "NetContact";
        public static final Uri PHONE_LOOKUP_URI = Uri.withAppendedPath(Uri.parse("content://com.meizu.netcontactservice.directory"), "phone_lookup");
        public static final String USE_YELLOW_PAGE_CONTACTS = "use_yellow_page_contacts";
        public static final long YELLOW_PAGE_MIN_ID = 9223372035781033983L;

        public static boolean isYPContact(Uri uri) {
            boolean z = false;
            try {
                z = isYPContact(ContentUris.parseId(uri));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return z;
        }

        public static boolean isYPContact(long j) {
            return j >= YELLOW_PAGE_MIN_ID;
        }
    }

    public static final class MzPhoneLookup implements MzContactColumns, MzContactOptionsColumns {
        public static final String CALL_ALLOWED_CONTACT_IDS = "call_allowed_contact_ids";
        public static final String CALL_REJECTED_CONTACT_IDS = "call_rejected_contact_ids";
        public static final String CALL_REJECTED_EXTRAS = "call_rejected_extras";
        public static final String CALL_REJECTED_TYPE = "call_rejected_type";

        public static Uri buildRejectedExtrasUri(Uri uri) {
            return uri.buildUpon().appendQueryParameter(CALL_REJECTED_EXTRAS, "true").build();
        }

        public static boolean isPhoneNumberInContact(Context context, String str) {
            Cursor cursor;
            Throwable th;
            Cursor cursor2 = null;
            if (context == null || TextUtils.isEmpty(str)) {
                return false;
            }
            try {
                Cursor query = context.getContentResolver().query(Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, str), new String[]{"_id"}, null, null, null);
                if (query != null) {
                    try {
                        boolean z = query.getCount() > 0;
                        if (query != null) {
                            query.close();
                        }
                        return z;
                    } catch (Exception e) {
                        cursor = query;
                        if (cursor != null) {
                            return false;
                        }
                        cursor.close();
                        return false;
                    } catch (Throwable th2) {
                        th = th2;
                        cursor2 = query;
                        if (cursor2 != null) {
                            cursor2.close();
                        }
                        throw th;
                    }
                } else if (query == null) {
                    return false;
                } else {
                    query.close();
                    return false;
                }
            } catch (Exception e2) {
                cursor = null;
                if (cursor != null) {
                    return false;
                }
                cursor.close();
                return false;
            } catch (Throwable th3) {
                th = th3;
                if (cursor2 != null) {
                    cursor2.close();
                }
                throw th;
            }
        }
    }

    public static final class MzQuickContact {
        public static final String ACTION_MZ_QUICK_CONTACT = "com.android.contacts.action.MZ_QUICK_CONTACT";
    }

    public interface MzRawContactColumns {
        public static final String IS_SUPER_PHONE = "is_super_phone";
        public static final String IS_SUPER_PHOTO = "is_super_photo";
        public static final String RAW_PHONE_NUMBER = "raw_phone_number";
        public static final String RAW_PHOTO_FILE_ID = "raw_photo_file_id";
        public static final String RAW_PHOTO_ID = "raw_photo_id";
        public static final String RAW_PHOTO_THUMBNAIL_URI = "raw_photo_thumb_uri";
        public static final String RAW_PHOTO_URI = "raw_photo_uri";
        public static final String SNS_TYPE = "sns_type";
    }

    public static class MzSearchSnippetColumns {
        public static final String SEARCH_WEIGHT = "search_weight";
        public static final int SEARCH_WEIGHT_CONTENT = 10002;
        public static final int SEARCH_WEIGHT_NAME = 10000;
        public static final int SEARCH_WEIGHT_TOKENS = 10001;
        public static final int SEARCH_WEIGHT_UNKNOW = 10003;
    }

    public interface MzSettingsColumns {
        public static final String DIRECTORY_VISIBLE = "directory_visible";
        public static final String IS_DEFAULT = "is_default";
        public static final String SYNC_WITH_DEFAULT_ACCOUNT = "sync_with_default_account";
    }

    public static final class MzSettings implements MzSettingsColumns {
        public static final Uri CONTENT_NOTIFY_URI = Uri.withAppendedPath(MzContactsContract.AUTHORITY_URI_NOTIFY, "settings");

        private MzSettings() {
        }
    }

    public static String snippetize(String str, String str2, String str3, char c, char c2, String str4, int i) {
        Object toLowerCase = str3 != null ? str3.toLowerCase() : null;
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str3) || TextUtils.isEmpty(str2) || !str.toLowerCase().contains(toLowerCase)) {
            return null;
        }
        String toLowerCase2 = str2 != null ? str2.toLowerCase() : "";
        List<String> arrayList = new ArrayList();
        MzSplit(toLowerCase2.trim(), arrayList, new ArrayList());
        for (String toLowerCase22 : arrayList) {
            if (toLowerCase22.startsWith(toLowerCase)) {
                return null;
            }
        }
        for (String str5 : str.split("\n")) {
            if (str5.toLowerCase().contains(toLowerCase)) {
                List arrayList2 = new ArrayList();
                List arrayList3 = new ArrayList();
                MzSplit(str5, arrayList2, arrayList3);
                List arrayList4 = new ArrayList();
                int i2 = -1;
                int i3 = -1;
                for (int i4 = 0; i4 < arrayList2.size(); i4++) {
                    toLowerCase22 = (String) arrayList2.get(i4);
                    if (toLowerCase22.toLowerCase().startsWith(toLowerCase)) {
                        arrayList4.add(c + toLowerCase22 + c2);
                        if (i2 == -1) {
                            i2 = Math.max(0, i4 - ((int) Math.floor(((double) Math.abs(i)) / 2.0d)));
                            i3 = Math.min(arrayList2.size(), Math.abs(i) + i2);
                        }
                    } else {
                        arrayList4.add(toLowerCase22);
                    }
                }
                if (i2 > -1) {
                    StringBuilder stringBuilder = new StringBuilder();
                    if (i2 > 0) {
                        stringBuilder.append(str4);
                    }
                    while (i2 < i3) {
                        String str6 = (String) arrayList2.get(i2);
                        stringBuilder.append((String) arrayList4.get(i2));
                        if (i2 < i3 - 1) {
                            stringBuilder.append(str5.substring(str6.length() + ((Integer) arrayList3.get(i2)).intValue(), ((Integer) arrayList3.get(i2 + 1)).intValue()));
                        }
                        i2++;
                    }
                    if (i3 < arrayList2.size()) {
                        stringBuilder.append(str4);
                    }
                    return stringBuilder.toString();
                }
            }
        }
        return null;
    }

    private static void MzSplit(String str, List<String> list, List<Integer> list2) {
        Matcher matcher = SPLIT_PATTERN.matcher(str);
        while (matcher.find()) {
            list.add(matcher.group());
            list2.add(Integer.valueOf(matcher.start()));
        }
    }
}
