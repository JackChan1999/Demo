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
import com.meizu.common.R;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ListPreference extends android.preference.ListPreference implements OnGlobalLayoutListener, OnDismissListener {
    private static Field sPreferenceView;
    private static Method sSetLayoutMode;
    private ListAdapter mAdapter;
    private int mClickedDialogEntryIndex;
    private int mCurrentOrientation;
    private int mDropDownWidth;
    private int mMaxDropDownHeight;
    private DropdownPopup mPopup;
    private View mPreferenceViewExt;
    private int mSingleChoiceItemLayout;
    private ViewTreeObserver mVto;

    class DropdownPopup extends ListPopupWindow {
        private ListAdapter mAdapter;

        public DropdownPopup(Context context) {
            super(context);
            setModal(true);
            setPromptPosition(0);
            setOnItemClickListener(new OnItemClickListener(ListPreference.this) {
                public void onItemClick(AdapterView adapterView, View view, int i, long j) {
                    ListPreference.this.mClickedDialogEntryIndex = i;
                    DropdownPopup.this.setSelection(ListPreference.this.mClickedDialogEntryIndex);
                    DropdownPopup.this.dismiss();
                }
            });
        }

        public void setAdapter(ListAdapter listAdapter) {
            super.setAdapter(listAdapter);
            this.mAdapter = listAdapter;
        }

        public void show() {
            int i = 0;
            int paddingLeft = ListPreference.this.mPreferenceViewExt.getPaddingLeft();
            int paddingRight = ListPreference.this.mPreferenceViewExt.getPaddingRight();
            int width = ListPreference.this.mPreferenceViewExt.getWidth();
            if (ListPreference.this.mDropDownWidth <= 0 || ListPreference.this.mDropDownWidth > (width - paddingLeft) - paddingRight) {
                ListPreference.this.mDropDownWidth = (width - paddingLeft) - paddingRight;
            }
            setContentWidth(ListPreference.this.mDropDownWidth);
            try {
                if (ListPreference.sSetLayoutMode == null) {
                    ListPreference.sSetLayoutMode = getClass().getMethod("setLayoutMode", new Class[]{Integer.TYPE});
                }
                ListPreference.sSetLayoutMode.invoke(this, new Object[]{Integer.valueOf(4)});
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (ListPreference.this.mMaxDropDownHeight > 0) {
                if (this.mAdapter != null && this.mAdapter.getCount() > 0) {
                    paddingLeft = 0;
                    paddingRight = 0;
                    do {
                        View view = this.mAdapter.getView(paddingLeft, null, getListView());
                        if (view != null) {
                            view.measure(0, 0);
                            paddingRight += view.getMeasuredHeight();
                        }
                        paddingLeft++;
                    } while (paddingLeft < this.mAdapter.getCount());
                    i = paddingRight;
                }
                if (i > ListPreference.this.mMaxDropDownHeight) {
                    setHeight(ListPreference.this.mMaxDropDownHeight);
                }
            }
            ListPreference.this.mClickedDialogEntryIndex = ListPreference.this.findIndexOfValue(ListPreference.this.getValue());
            setInputMethodMode(2);
            super.show();
            getListView().setChoiceMode(1);
            setSelection(ListPreference.this.mClickedDialogEntryIndex);
            setOnDismissListener(ListPreference.this);
        }
    }

    public ListPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAdapter = null;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ListPreference, 0, 0);
        this.mDropDownWidth = obtainStyledAttributes.getLayoutDimension(R.styleable.ListPreference_mcDropDownWidth, context.getResources().getDimensionPixelSize(R.dimen.mz_popup_menu_item_min_width));
        this.mMaxDropDownHeight = obtainStyledAttributes.getLayoutDimension(R.styleable.ListPreference_mcMaxDropDownHeight, this.mMaxDropDownHeight);
        this.mSingleChoiceItemLayout = obtainStyledAttributes.getResourceId(R.styleable.ListPreference_mcSingleChoiceItemLayout, R.layout.mz_select_popup_singlechoice);
        obtainStyledAttributes.recycle();
    }

    public ListPreference(Context context) {
        this(context, null);
    }

    protected void onBindView(View view) {
        super.onBindView(view);
        this.mPreferenceViewExt = view;
    }

    protected void onClick() {
        try {
            if (sPreferenceView == null) {
                sPreferenceView = Preference.class.getDeclaredField("mPreferenceView");
            }
            Object obj = sPreferenceView.get(this);
            if (obj instanceof View) {
                this.mPreferenceViewExt = (View) obj;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mCurrentOrientation = getContext().getResources().getConfiguration().orientation;
        if (this.mPopup == null) {
            this.mPopup = new DropdownPopup(getContext());
        }
        this.mPreferenceViewExt.setActivated(true);
        this.mVto = this.mPreferenceViewExt.getViewTreeObserver();
        if (this.mVto != null) {
            this.mVto.addOnGlobalLayoutListener(this);
        }
        this.mPopup.setAnchorView(this.mPreferenceViewExt);
        if (this.mAdapter != null) {
            this.mPopup.setAdapter(this.mAdapter);
        } else {
            this.mPopup.setAdapter(new ArrayAdapter(getContext(), this.mSingleChoiceItemLayout, R.id.text1, getEntries()));
        }
        this.mPopup.show();
    }

    protected void onDropdownPopupClosed() {
        CharSequence[] entryValues = getEntryValues();
        if (this.mClickedDialogEntryIndex >= 0 && entryValues != null) {
            String charSequence = entryValues[this.mClickedDialogEntryIndex].toString();
            if (callChangeListener(charSequence)) {
                setValue(charSequence);
            }
        }
    }

    public ListPopupWindow getDropdownPopup() {
        return this.mPopup;
    }

    public void setDropDownWidth(int i) {
        this.mDropDownWidth = i;
    }

    public void setMaxDropDownHeight(int i) {
        this.mMaxDropDownHeight = i;
    }

    @TargetApi(16)
    public void onDismiss() {
        onDropdownPopupClosed();
        this.mPreferenceViewExt.setActivated(false);
        ViewTreeObserver viewTreeObserver = this.mPreferenceViewExt.getViewTreeObserver();
        if (viewTreeObserver != null) {
            viewTreeObserver.removeOnGlobalLayoutListener(this);
        }
    }

    public void onGlobalLayout() {
        if (!this.mPopup.isShowing()) {
            return;
        }
        if (this.mPreferenceViewExt == null || !this.mPreferenceViewExt.isShown() || this.mCurrentOrientation != getContext().getResources().getConfiguration().orientation) {
            this.mPopup.dismiss();
        } else if (this.mPopup.isShowing() && this.mPreferenceViewExt != this.mPopup.getAnchorView()) {
            this.mPopup.setAnchorView(this.mPreferenceViewExt);
            this.mPopup.show();
        }
    }

    public void setEnabled(boolean z) {
        super.setEnabled(z);
        if (!z && this.mPopup != null && this.mPopup.isShowing()) {
            this.mPopup.dismiss();
        }
    }

    public void setAdapter(ListAdapter listAdapter) {
        if (listAdapter != null) {
            this.mAdapter = listAdapter;
        }
    }
}
