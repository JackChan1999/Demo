package sdk.meizu.traffic.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Pair;

import java.util.ArrayList;

public class ContactUtil {
    public static Pair<String, String> loadPhoneData(Context context, Uri uri) {
        Cursor cursor = context.getContentResolver().query(uri, new String[]{"display_name",
                "data1"}, null, null, null);
        Pair<String, String> pair = null;
        if (cursor.moveToFirst()) {
            String str1 = cursor.getString(0);
            String str2 = cursor.getString(1);
            pair = new Pair(str1, str2);
        }
        cursor.close();
        return pair;
    }

    public static ArrayList<Pair<String, String>> searchContactsByNum(Context context, String str) {
        ArrayList<Pair<String, String>> arrayList = new ArrayList();
        Cursor cursor = context.getContentResolver().query(Phone.CONTENT_URI, new
                String[]{"display_name", "data1"}, "data1 like ?", new String[]{"%" + str +
                "%"}, null);
        if (cursor.moveToFirst()) {
            do {
                String str1 = cursor.getString(0);
                String str2 = cursor.getString(1);
                arrayList.add(new Pair(str1, str2));
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return arrayList;
    }
}
