package com.meizu.common.widget;

import android.database.Cursor;
import android.widget.AlphabetIndexer;
import java.text.Collator;

public class GroupAlphabetIndexer extends AlphabetIndexer {
    private static final String DEFAULT_GROUP_LETTERS = "#1234567890";
    private Collator mCollator = Collator.getInstance();
    private String mFirstGroupLetters = DEFAULT_GROUP_LETTERS;

    public GroupAlphabetIndexer(Cursor cursor, int i, CharSequence charSequence) {
        super(cursor, i, charSequence);
        this.mCollator.setStrength(0);
    }

    public void setFirstGroupLetters(String str) {
        this.mFirstGroupLetters = str;
    }

    protected int compare(String str, String str2) {
        return compare(str, str2, this.mFirstGroupLetters);
    }

    protected int compare(String str, String str2, String str3) {
        Object obj = " ";
        if (str.length() > 0) {
            obj = str.substring(0, 1);
        }
        if (str3.contains(obj)) {
            if (str3.contains(str2)) {
                return 0;
            }
            return 1;
        } else if (str3.contains(str2)) {
            return -1;
        } else {
            return this.mCollator.compare(obj, str2);
        }
    }
}
