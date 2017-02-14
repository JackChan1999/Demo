package com.meizu.common.preference;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import com.meizu.common.a.d;
import com.meizu.common.a.f;
import com.meizu.common.a.g;
import com.meizu.common.a.j;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ListPreference extends android.preference.ListPreference implements OnGlobalLayoutListener, OnDismissListener {
    private static Field j;
    private static Method k;
    private a a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private ViewTreeObserver g;
    private View h;
    private ListAdapter i = null;

    private class a extends ListPopupWindow {
        final /* synthetic */ ListPreference a;
        private ListAdapter b;

        public a(final ListPreference listPreference, Context context) {
            this.a = listPreference;
            super(context);
            setModal(true);
            setPromptPosition(0);
            setOnItemClickListener(new OnItemClickListener(this) {
                final /* synthetic */ a b;

                public void onItemClick(AdapterView parent, View v, int position, long id) {
                    this.b.a.f = position;
                    this.b.setSelection(this.b.a.f);
                    this.b.dismiss();
                }
            });
        }

        public void setAdapter(ListAdapter adapter) {
            super.setAdapter(adapter);
            this.b = adapter;
        }

        public void show() {
            int anchorViewPaddingLeft = this.a.h.getPaddingLeft();
            int anchorViewPaddingRight = this.a.h.getPaddingRight();
            int anchorViewWidth = this.a.h.getWidth();
            if (this.a.b <= 0 || this.a.b > (anchorViewWidth - anchorViewPaddingLeft) - anchorViewPaddingRight) {
                this.a.b = (anchorViewWidth - anchorViewPaddingLeft) - anchorViewPaddingRight;
            }
            setContentWidth(this.a.b);
            try {
                if (ListPreference.k == null) {
                    ListPreference.k = getClass().getMethod("setLayoutMode", new Class[]{Integer.TYPE});
                }
                ListPreference.k.invoke(this, new Object[]{Integer.valueOf(4)});
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (this.a.c > 0) {
                int listHeight = 0;
                if (this.b != null && this.b.getCount() > 0) {
                    int position = 0;
                    do {
                        View itemView = this.b.getView(position, null, getListView());
                        if (itemView != null) {
                            itemView.measure(0, 0);
                            listHeight += itemView.getMeasuredHeight();
                        }
                        position++;
                    } while (position < this.b.getCount());
                    if (listHeight > this.a.c) {
                        setHeight(this.a.c);
                    }
                } else if (listHeight > this.a.c) {
                    setHeight(this.a.c);
                }
            }
            this.a.f = this.a.findIndexOfValue(this.a.getValue());
            setInputMethodMode(2);
            super.show();
            getListView().setChoiceMode(1);
            setSelection(this.a.f);
            setOnDismissListener(this.a);
        }
    }

    public ListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, j.ListPreference, 0, 0);
        this.b = a.getLayoutDimension(j.ListPreference_mcDropDownWidth, context.getResources().getDimensionPixelSize(d.mz_popup_menu_item_min_width));
        this.c = a.getLayoutDimension(j.ListPreference_mcMaxDropDownHeight, this.c);
        this.d = a.getResourceId(j.ListPreference_mcSingleChoiceItemLayout, g.mz_select_popup_singlechoice);
        a.recycle();
    }

    protected void onBindView(View view) {
        super.onBindView(view);
        this.h = view;
    }

    protected void onClick() {
        try {
            if (j == null) {
                j = Preference.class.getDeclaredField("mPreferenceView");
            }
            Object ob = j.get(this);
            if (ob instanceof View) {
                this.h = (View) ob;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.e = getContext().getResources().getConfiguration().orientation;
        if (this.a == null) {
            this.a = new a(this, getContext());
        }
        this.h.setActivated(true);
        this.g = this.h.getViewTreeObserver();
        if (this.g != null) {
            this.g.addOnGlobalLayoutListener(this);
        }
        this.a.setAnchorView(this.h);
        if (this.i != null) {
            this.a.setAdapter(this.i);
        } else {
            this.a.setAdapter(new ArrayAdapter(getContext(), this.d, f.text1, getEntries()));
        }
        this.a.show();
    }

    protected void a() {
        CharSequence[] ev = getEntryValues();
        if (this.f >= 0 && ev != null) {
            String value = ev[this.f].toString();
            if (callChangeListener(value)) {
                setValue(value);
            }
        }
    }

    @TargetApi(16)
    public void onDismiss() {
        a();
        this.h.setActivated(false);
        ViewTreeObserver vto = this.h.getViewTreeObserver();
        if (vto != null) {
            vto.removeOnGlobalLayoutListener(this);
        }
    }

    public void onGlobalLayout() {
        if (!this.a.isShowing()) {
            return;
        }
        if (this.h == null || !this.h.isShown() || this.e != getContext().getResources().getConfiguration().orientation) {
            this.a.dismiss();
        } else if (this.a.isShowing() && this.h != this.a.getAnchorView()) {
            this.a.setAnchorView(this.h);
            this.a.show();
        }
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (!enabled && this.a != null && this.a.isShowing()) {
            this.a.dismiss();
        }
    }
}
