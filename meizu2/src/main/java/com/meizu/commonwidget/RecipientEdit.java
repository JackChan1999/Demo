package com.meizu.commonwidget;

import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings.Secure;
import android.support.v4.view.InputDeviceCompat;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.util.Rfc822Token;
import android.text.util.Rfc822Tokenizer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import com.meizu.common.widget.CircleProgressBar;
import com.meizu.common.widget.MzContactsContract.MzGroups;
import defpackage.aiu;
import defpackage.aiv$a;
import defpackage.aiv$b;
import defpackage.aiv$c;
import defpackage.aiv$d;
import defpackage.aiv$e;
import defpackage.aiv$f;
import defpackage.aiv$g;
import defpackage.aiw;
import defpackage.aix;
import defpackage.aiy;
import defpackage.aiz;
import defpackage.aja;
import defpackage.ajb;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class RecipientEdit extends FrameLayout implements OnClickListener, OnFocusChangeListener, OnLongClickListener {
    private static c ac;
    private HashMap<String, String> A;
    private HashMap<String, aiu> B;
    private boolean C;
    private boolean D;
    private int E;
    private String F;
    private int G;
    private boolean H;
    private m I;
    private OnDragListener J;
    private h K;
    private boolean L;
    private boolean M;
    private boolean N;
    private int O;
    private int P;
    private boolean Q;
    private boolean R;
    private boolean S;
    private boolean T;
    private boolean U;
    private boolean V;
    private boolean W;
    public Context a;
    private ArrayList<String> aa;
    private b ab;
    private Handler ad;
    private final OnScrollChangedListener ae;
    private boolean af;
    private TextWatcher ag;
    private Runnable ah;
    private String ai;
    private int aj;
    private boolean ak;
    public i b;
    public f c;
    public e d;
    public g e;
    public int f;
    HandlerThread g;
    Handler h;
    boolean i;
    Handler j;
    private a k;
    private j l;
    private LinearLayout m;
    private ScrollView n;
    private AbsoluteLayout o;
    private TextView p;
    private RecipientAutoCompleteTextView q;
    private View r;
    private TextView s;
    private TextView t;
    private View u;
    private ContentResolver v;
    private int w;
    private int x;
    private String y;
    private ArrayList<String> z;

    public static class ItemView extends LinearLayout {
        int a;
        int b;
        private TextView c;

        public ItemView(Context context) {
            super(context);
        }

        public ItemView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public ItemView(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
        }

        protected void onFinishInflate() {
            super.onFinishInflate();
            this.c = (TextView) findViewById(aiv$d.text);
        }

        public void setSelected(boolean z) {
            this.c.setSelected(z);
        }

        public TextView a() {
            return this.c;
        }

        public void a(CharSequence charSequence) {
            this.c.setText(charSequence);
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0) {
                this.a = (int) motionEvent.getX();
                this.b = (int) motionEvent.getY();
            }
            return super.onTouchEvent(motionEvent);
        }
    }

    public static class RecipientAutoCompleteTextView extends AutoCompleteTextView {
        private a a;
        private d b;
        private View c;
        private b d;
        private ListAdapter e;
        private OnScrollListener f;
        private Drawable g;
        private Drawable h;
        private Drawable i;

        public interface a {
            boolean a(View view, int i, KeyEvent keyEvent);
        }

        class b extends DataSetObserver {
            final /* synthetic */ RecipientAutoCompleteTextView a;

            private b(RecipientAutoCompleteTextView recipientAutoCompleteTextView) {
                this.a = recipientAutoCompleteTextView;
            }

            public void onChanged() {
                this.a.a();
            }
        }

        static class c {
            public static Object a(Object obj, String str, String str2) {
                try {
                    Field declaredField = Class.forName(str).getDeclaredField(str2);
                    declaredField.setAccessible(true);
                    return declaredField.get(obj);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            public static ListPopupWindow a(AutoCompleteTextView autoCompleteTextView) {
                return (ListPopupWindow) a(autoCompleteTextView, "android.widget.AutoCompleteTextView", "mPopup");
            }

            public static PopupWindow a(ListPopupWindow listPopupWindow) {
                return (PopupWindow) a(listPopupWindow, "android.widget.ListPopupWindow", "mPopup");
            }

            public static View a(PopupWindow popupWindow) {
                return (View) a(popupWindow, "android.widget.PopupWindow", "mPopupView");
            }
        }

        public boolean onKeyPreIme(int i, KeyEvent keyEvent) {
            if (this.a == null || !this.a.a(this, i, keyEvent)) {
                return super.onKeyPreIme(i, keyEvent);
            }
            return true;
        }

        public void setOnKeyPreImeListener(a aVar) {
            this.a = aVar;
        }

        public RecipientAutoCompleteTextView(Context context) {
            super(context);
            b();
        }

        public RecipientAutoCompleteTextView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            b();
        }

        public RecipientAutoCompleteTextView(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
            b();
        }

        private void b() {
            this.h = getResources().getDrawable(aiv$c.mw_list_history_background_noshadow);
            this.i = getResources().getDrawable(aiv$c.mw_list_history_background);
        }

        protected void performFiltering(CharSequence charSequence, int i) {
            if (((ParcelableImageSpan[]) ((Editable) charSequence).getSpans(0, charSequence.length(), ParcelableImageSpan.class)).length == 0) {
                if (charSequence instanceof Spannable) {
                    Spannable spannable = (Spannable) charSequence;
                    int composingSpanStart = BaseInputConnection.getComposingSpanStart(spannable);
                    int composingSpanEnd = BaseInputConnection.getComposingSpanEnd(spannable);
                    if (composingSpanStart >= 0 && composingSpanEnd >= 0) {
                        CharSequence subSequence = spannable.subSequence(composingSpanStart, composingSpanEnd);
                        charSequence = charSequence.toString().replace(subSequence, subSequence.toString().replace("'", ""));
                    }
                }
                super.performFiltering(charSequence, i);
            }
        }

        public void a(boolean z) {
            boolean isPopupShowing = isPopupShowing();
            super.dismissDropDown();
            if (z) {
                aiw aiw = (aiw) getAdapter();
                if (aiw != null) {
                    aiw.d();
                }
            }
            if (this.b != null && isPopupShowing) {
                this.b.a(this.c, false);
            }
        }

        public void dismissDropDown() {
            a(true);
        }

        public ListPopupWindow getPopup() {
            return c.a((AutoCompleteTextView) this);
        }

        public void showDropDown() {
            boolean isPopupShowing = isPopupShowing();
            super.showDropDown();
            if (!(this.b == null || isPopupShowing)) {
                this.b.a(this.c, true);
            }
            if (this.f != null && !isPopupShowing) {
                getPopup().getListView().setOnScrollListener(this.f);
            }
        }

        public void a() {
            int i;
            ListPopupWindow popup = getPopup();
            View anchorView = popup.getAnchorView();
            if (anchorView == null) {
                if (getDropDownAnchor() != -1) {
                    anchorView = getRootView().findViewById(getDropDownAnchor());
                } else {
                    anchorView = this;
                }
            }
            PopupWindow a = c.a(popup);
            if (a.getMaxAvailableHeight(anchorView, getDropDownVerticalOffset()) < (getResources().getDimensionPixelSize(aiv$b.mw_recipient_list_item_height) * getAdapter().getCount()) + 0) {
                if (this.g != this.h) {
                    this.g = this.h;
                    i = 1;
                }
                i = 0;
            } else {
                if (this.g != this.i) {
                    this.g = this.i;
                    i = 1;
                }
                i = 0;
            }
            if (i != 0) {
                anchorView = c.a(a);
                if (anchorView == null) {
                    setDropDownBackgroundDrawable(this.g);
                } else {
                    anchorView.setBackground(this.g);
                }
            }
        }

        public void a(View view, d dVar) {
            this.c = view;
            this.b = dVar;
        }

        public void setOnScrollListener(OnScrollListener onScrollListener) {
            this.f = onScrollListener;
        }

        public <T extends ListAdapter & Filterable> void setAdapter(T t) {
            super.setAdapter(t);
            if (this.d == null) {
                this.d = new b();
            } else if (this.e != null) {
                this.e.unregisterDataSetObserver(this.d);
            }
            this.e = t;
            if (this.e != null) {
                t.registerDataSetObserver(this.d);
            }
        }
    }

    public static class a {
        public String a;
        public String b;

        public a(String str, String str2) {
            this.a = str;
            this.b = str2;
        }
    }

    static class b {
        public int a;
        public int b;
        public ItemView c;

        b() {
        }
    }

    static class c implements Runnable {
        private final Object a = new Object();
        private Looper b;
        private int c;

        static /* synthetic */ int a(c cVar) {
            int i = cVar.c + 1;
            cVar.c = i;
            return i;
        }

        static /* synthetic */ int b(c cVar) {
            int i = cVar.c - 1;
            cVar.c = i;
            return i;
        }

        public c(String str) {
            new Thread(this, str).start();
            synchronized (this.a) {
                while (this.b == null) {
                    try {
                        this.a.wait();
                    } catch (InterruptedException e) {
                    }
                }
            }
        }

        public void run() {
            synchronized (this.a) {
                Looper.prepare();
                this.b = Looper.myLooper();
                this.a.notifyAll();
            }
            Looper.loop();
        }

        public Looper a() {
            return this.b;
        }

        public void b() {
            this.b.quit();
        }
    }

    public interface d {
        void a(View view, boolean z);
    }

    public interface e {
        void a();
    }

    public interface f {
        void a();
    }

    public interface g {
        void a(RecipientEdit recipientEdit);
    }

    public interface h {
        void a(String str, int i);

        void a(String str, int i, int i2, int i3);

        void b(String str, int i, int i2, int i3);
    }

    public interface i {
        void a(int i);
    }

    public interface j {
        void a(CharSequence charSequence, int i, int i2, int i3);
    }

    class k extends Handler {
        final /* synthetic */ RecipientEdit a;

        public k(RecipientEdit recipientEdit, Looper looper) {
            this.a = recipientEdit;
            super(looper);
        }

        public void handleMessage(Message message) {
            Object obj;
            a aVar;
            Message obtain;
            String str = ((a) message.obj).b;
            String str2 = ((a) message.obj).a;
            String str3 = "";
            if (!TextUtils.isEmpty(str)) {
                String[] split = str.split(MzGroups.GROUP_SPLIT_MARK_VCARD);
                if (split.length > 1) {
                    str = split[0];
                    str3 = str;
                    obj = split[1];
                    if (TextUtils.isEmpty(str3)) {
                        str3 = ((aiw) this.a.q.getAdapter()).a(str2);
                    }
                    if (!TextUtils.isEmpty(obj)) {
                        str3 = str3 + MzGroups.GROUP_SPLIT_MARK_VCARD + obj;
                    }
                    aVar = new a(str2, str3);
                    obtain = Message.obtain(this.a.j, 1);
                    obtain.obj = aVar;
                    this.a.j.sendMessage(obtain);
                }
            }
            String str4 = str3;
            str3 = str;
            str = str4;
            if (TextUtils.isEmpty(str3)) {
                str3 = ((aiw) this.a.q.getAdapter()).a(str2);
            }
            if (TextUtils.isEmpty(obj)) {
                str3 = str3 + MzGroups.GROUP_SPLIT_MARK_VCARD + obj;
            }
            aVar = new a(str2, str3);
            obtain = Message.obtain(this.a.j, 1);
            obtain.obj = aVar;
            this.a.j.sendMessage(obtain);
        }
    }

    class l extends DragShadowBuilder {
        final /* synthetic */ RecipientEdit a;

        public l(RecipientEdit recipientEdit, View view) {
            this.a = recipientEdit;
            super(view);
        }

        public void onProvideShadowMetrics(Point point, Point point2) {
            ItemView itemView = (ItemView) getView();
            if (itemView != null) {
                point.set(itemView.getWidth(), itemView.getHeight());
                point2.set(itemView.a, point.y - 10);
                this.a.ab.a = itemView.a;
                this.a.ab.b = (point.y - 10) + 20;
                return;
            }
            Log.e("RecipientEdit", "Asked for drag thumb metrics but no view");
        }
    }

    public interface m {
        void a(View view);

        void b(View view);
    }

    public final class n implements Runnable {
        final /* synthetic */ RecipientEdit a;
        private String b;
        private String c;

        public n(RecipientEdit recipientEdit, String str, String str2) {
            this.a = recipientEdit;
            this.b = str;
            this.c = str2;
        }

        public void run() {
            int indexOf = this.a.z.indexOf(this.b);
            if (indexOf > -1) {
                CharSequence charSequence;
                this.a.A.put(this.b, this.c);
                String str = this.c;
                if (TextUtils.isEmpty(this.c)) {
                    charSequence = this.b;
                } else {
                    Object obj = str;
                }
                ((ItemView) this.a.o.getChildAt(indexOf + 1)).a(charSequence);
                this.a.b(this.a.hasFocus());
            }
        }
    }

    public RecipientEdit(Context context) {
        this(context, null);
    }

    public RecipientEdit(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RecipientEdit(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.G = 0;
        this.N = false;
        this.Q = false;
        this.R = true;
        this.S = false;
        this.T = false;
        this.U = false;
        this.V = false;
        this.W = false;
        this.aa = new ArrayList();
        this.ae = new aix(this);
        this.af = true;
        this.ag = new aiz(this);
        this.j = new aja(this);
        this.ah = new ajb(this);
        this.ak = false;
        this.a = context;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, aiv$g.RecipientEdit, i, 0);
        this.x = obtainStyledAttributes.getInt(aiv$g.RecipientEdit_mzInputType, 3);
        this.y = obtainStyledAttributes.getString(aiv$g.RecipientEdit_mzHint);
        this.w = obtainStyledAttributes.getDimensionPixelSize(aiv$g.RecipientEdit_mzMaxHeight, 0);
        this.i = obtainStyledAttributes.getBoolean(aiv$g.RecipientEdit_isEasyMode, false);
        obtainStyledAttributes.recycle();
        this.g = new HandlerThread("QueryThread");
        this.g.start();
        this.h = new k(this, this.g.getLooper());
        a();
    }

    private void a() {
        this.v = this.a.getContentResolver();
        this.C = true;
        this.D = true;
        this.z = new ArrayList();
        this.A = new HashMap();
        this.B = new HashMap();
        setOnClickListener(this);
        if (this.i) {
            inflate(this.a, aiv$e.mw_recipient_edit_layout_easymode, this);
        } else {
            inflate(this.a, aiv$e.mw_recipient_edit_layout, this);
        }
        this.m = (LinearLayout) findViewById(aiv$d.mz_recipient_root);
        this.n = (ScrollView) findViewById(aiv$d.mz_recipient_scrollview);
        this.n.setOverScrollMode(1);
        this.o = (AbsoluteLayout) findViewById(aiv$d.mz_recipient_layout);
        this.p = (TextView) findViewById(aiv$d.mz_recipient_hint);
        this.q = (RecipientAutoCompleteTextView) findViewById(aiv$d.mz_recipient_edit);
        this.r = findViewById(aiv$d.mz_recipient_add_btn);
        this.s = (TextView) findViewById(aiv$d.mz_recipient_hint2);
        this.t = (TextView) findViewById(aiv$d.mz_recipient_name);
        this.o.setClickable(true);
        this.o.setOnClickListener(this);
        if (TextUtils.isEmpty(this.y)) {
            this.y = getResources().getString(aiv$f.mw_recipient_hint_str);
        }
        this.p.setText(this.y);
        this.s.setText(this.y);
        this.q.setDropDownAnchor(getId());
        this.q.setDropDownBackgroundResource(aiv$c.mw_list_history_background);
        this.q.addTextChangedListener(this.ag);
        this.q.setOnClickListener(this);
        this.q.setOnFocusChangeListener(this);
        this.q.setOnKeyPreImeListener(new aiy(this));
        if (this.x == 2) {
            setBackgroundResource(aiv$c.mw_recipient_divider_email_2px);
        } else {
            setBackgroundResource(aiv$c.mw_recipient_divider_sms_2px);
        }
        this.q.setInputType(33);
        this.F = Secure.getString(this.v, "default_input_method");
        super.setFocusable(false);
        super.setFocusableInTouchMode(false);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        int imeOptions = this.q.getImeOptions() & InputDeviceCompat.SOURCE_ANY;
        if (!b()) {
            this.q.setImeOptions(imeOptions | 1);
            this.q.setImeActionLabel(getResources().getString(aiv$f.mw_recipient_edit_imeActionLabel), 1);
        } else if (focusSearch(130) != null) {
            this.q.setImeOptions(imeOptions | 5);
        } else {
            this.q.setImeOptions(imeOptions | 6);
        }
        return super.onCreateInputConnection(editorInfo);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (ac == null) {
            ac = new c("mz_LocalUpdate_Thread");
        }
        if (this.ad == null) {
            c.a(ac);
            this.ad = new Handler(ac.a());
        }
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver != null) {
            viewTreeObserver.addOnScrollChangedListener(this.ae);
        }
    }

    public void onDetachedFromWindow() {
        if (ac != null && c.b(ac) == 0) {
            ac.b();
            ac = null;
        }
        if (this.h != null) {
            this.g.quit();
        }
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver != null) {
            viewTreeObserver.removeOnScrollChangedListener(this.ae);
        }
        super.onDetachedFromWindow();
    }

    private boolean b() {
        return TextUtils.equals(Secure.getString(this.v, "default_input_method"), "com.baidu.input_mz/com.meizu.input.MzInputService");
    }

    private void c() {
        if (this.q.getText() instanceof Spannable) {
            Spannable text = this.q.getText();
            int composingSpanStart = BaseInputConnection.getComposingSpanStart(text);
            int composingSpanEnd = BaseInputConnection.getComposingSpanEnd(text);
            if (composingSpanStart >= 0 && composingSpanEnd >= 0) {
                this.q.setText(text.subSequence(0, composingSpanStart).toString() + text.subSequence(composingSpanEnd, text.length()).toString());
            }
        }
        Editable text2 = this.q.getText();
        ParcelableImageSpan[] parcelableImageSpanArr = (ParcelableImageSpan[]) text2.getSpans(0, text2.length(), ParcelableImageSpan.class);
        if (parcelableImageSpanArr.length > 0) {
            text2.delete(text2.getSpanStart(parcelableImageSpanArr[0]), text2.getSpanEnd(parcelableImageSpanArr[0]));
        }
    }

    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.D = true;
        View findViewById = findViewById(aiv$d.mz_recipient_root);
        findViewById.setPadding(getResources().getDimensionPixelSize(aiv$b.mw_recipient_padding_left), findViewById.getPaddingTop(), getResources().getDimensionPixelSize(aiv$b.mw_recipient_padding_right), findViewById.getPaddingBottom());
    }

    public void onFocusChange(View view, boolean z) {
        c();
        CharSequence text = this.q.getText();
        if (!(view.getId() != aiv$d.mz_recipient_edit || z || TextUtils.isEmpty(text))) {
            a(text);
            this.q.setText("");
        }
        if (getOnFocusChangeListener() != null) {
            getOnFocusChangeListener().onFocusChange(this, z);
        }
        if (!(z || this.u == null)) {
            this.u.setSelected(false);
            b(this.u);
            this.u = null;
            this.q.setCursorVisible(true);
        }
        b(z);
        if (true == this.af) {
            this.o.invalidate();
            e();
            this.af = false;
        }
    }

    public void b(boolean z) {
        if (z || this.M) {
            this.r.setVisibility(0);
            this.n.setVisibility(0);
            this.s.setVisibility(8);
            this.t.setVisibility(8);
            e();
            return;
        }
        int measuredWidth;
        CharSequence substring;
        int length;
        LayoutParams layoutParams = getLayoutParams();
        if (layoutParams.height != -2) {
            layoutParams.height = -2;
            setLayoutParams(layoutParams);
        }
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        if (this.Q) {
            measuredWidth = ((this.m.getMeasuredWidth() - this.r.getMeasuredWidth()) - this.m.getPaddingStart()) - this.m.getPaddingEnd();
        } else {
            this.s.measure(0, 0);
            measuredWidth = (((this.m.getMeasuredWidth() - this.s.getMeasuredWidth()) - this.r.getMeasuredWidth()) - this.m.getPaddingStart()) - this.m.getPaddingEnd();
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        Iterator it = this.z.iterator();
        CharSequence charSequence = null;
        String str = null;
        boolean z2 = true;
        int i = 0;
        Object obj = 1;
        String str2 = null;
        String str3 = null;
        int i2 = 0;
        while (it.hasNext()) {
            String str4;
            String str5 = (String) it.next();
            int i3 = i + 1;
            String str6 = (String) this.A.get(str5);
            if (TextUtils.isEmpty(str6)) {
                str4 = str5;
            } else {
                String[] split = str6.split(MzGroups.GROUP_SPLIT_MARK_VCARD);
                if (split.length > 1) {
                    str4 = split[0] + split[1];
                } else {
                    str4 = str6;
                }
            }
            stringBuffer.append(str4).append("，");
            stringBuffer2.append(str4).append("，");
            substring = stringBuffer2.substring(0, stringBuffer2.length() - 1);
            str6 = getResources().getString(aiv$f.mw_recipient_others_displayname, new Object[]{Integer.valueOf(this.z.size() - i3)});
            if (i3 == 1) {
                str3 = String.valueOf(stringBuffer);
                z2 = b(str5);
            }
            int length2;
            int length3;
            if (i3 == 1 && this.t.getPaint().measureText(substring) >= ((float) measuredWidth)) {
                str = String.valueOf(stringBuffer2);
                if (!(str == null || b(str5) || !this.R)) {
                    length2 = (str.length() - str4.length()) - 1;
                    length3 = str.length();
                    arrayList3.add(Integer.valueOf(length2));
                    arrayList4.add(Integer.valueOf(length3));
                }
            } else if (this.t.getPaint().measureText(substring) < ((float) measuredWidth)) {
                str = String.valueOf(stringBuffer2);
                if (!(str == null || b(str5) || !this.R)) {
                    length2 = (str.length() - str4.length()) - 1;
                    length3 = str.length();
                    arrayList3.add(Integer.valueOf(length2));
                    arrayList4.add(Integer.valueOf(length3));
                }
            } else {
                obj = null;
            }
            if (i3 != 1 || this.t.getPaint().measureText(String.valueOf(stringBuffer) + str6) <= ((float) measuredWidth)) {
                if (this.t.getPaint().measureText(String.valueOf(stringBuffer) + str6) > ((float) measuredWidth)) {
                    if (obj == null) {
                        break;
                    }
                    i = i3;
                    charSequence = substring;
                } else {
                    str2 = String.valueOf(stringBuffer);
                    i = this.z.size() - i3;
                    if (!(str2 == null || b(str5) || !this.R)) {
                        length = (str2.length() - str4.length()) - 1;
                        i2 = str2.length();
                        arrayList.add(Integer.valueOf(length));
                        arrayList2.add(Integer.valueOf(i2));
                    }
                    length = i;
                    str6 = str2;
                }
            } else {
                length = this.z.size() - i3;
                str6 = str2;
            }
            i2 = length;
            str2 = str6;
            charSequence = substring;
            i = i3;
        }
        substring = charSequence;
        if (str == null && str3 == null) {
            if (this.ak) {
                this.t.setText(this.ai);
                this.t.setTextColor(this.aj);
            } else {
                this.t.setText("");
            }
        } else if (obj != null) {
            if (this.ak) {
                this.t.setTextColor(getResources().getColor(aiv$a.mw_recipient_text_red));
            }
            measuredWidth = substring.length();
            CharSequence spannableString = new SpannableString(substring);
            if (arrayList3.size() > 0 && arrayList3.size() == arrayList4.size() && this.R) {
                for (i = 0; i < arrayList3.size(); i++) {
                    if (this.G == 2) {
                        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(aiv$a.mw_recipient_text_invalidate_calendar)), ((Integer) arrayList3.get(i)).intValue(), ((Integer) arrayList4.get(i)).intValue() > measuredWidth ? measuredWidth : ((Integer) arrayList4.get(i)).intValue(), 33);
                    } else {
                        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(aiv$a.mw_recipient_text_invalidate)), ((Integer) arrayList3.get(i)).intValue(), ((Integer) arrayList4.get(i)).intValue() > measuredWidth ? measuredWidth : ((Integer) arrayList4.get(i)).intValue(), 33);
                    }
                }
            }
            this.t.setText(spannableString);
        } else if (i2 > 0) {
            if (this.ak) {
                this.t.setTextColor(getResources().getColor(aiv$a.mw_recipient_text_red));
            }
            str6 = getResources().getString(aiv$f.mw_recipient_others_displayname, new Object[]{Integer.valueOf(i2)});
            CharSequence spannableString2;
            if (this.t.getPaint().measureText(str3 + str6) > ((float) measuredWidth)) {
                length = str3.length() - 1;
                while (this.t.getPaint().measureText(str3.substring(0, length) + "..，" + str6) > ((float) measuredWidth)) {
                    if (length <= 0) {
                        Log.d("refreshlayout:", "mFirstDisplayName:" + str3 + "   mDisplayNamesElse:" + str6 + "   maxContextWidth:" + measuredWidth);
                        break;
                    }
                    length--;
                }
                spannableString2 = new SpannableString(str3.substring(0, length) + "..，" + str6);
                if (!z2 && this.R) {
                    if (this.G == 2) {
                        spannableString2.setSpan(new ForegroundColorSpan(getResources().getColor(aiv$a.mw_recipient_text_invalidate_calendar)), 0, length + 3, 33);
                    } else {
                        spannableString2.setSpan(new ForegroundColorSpan(getResources().getColor(aiv$a.mw_recipient_text_invalidate)), 0, length + 3, 33);
                    }
                }
                this.t.setText(spannableString2);
            } else {
                spannableString2 = new SpannableString(str2 + str6);
                if (arrayList.size() > 0 && arrayList.size() == arrayList2.size()) {
                    for (i = 0; i < arrayList.size(); i++) {
                        if (this.G == 2) {
                            spannableString2.setSpan(new ForegroundColorSpan(getResources().getColor(aiv$a.mw_recipient_text_invalidate_calendar)), ((Integer) arrayList.get(i)).intValue(), ((Integer) arrayList2.get(i)).intValue(), 33);
                        } else {
                            spannableString2.setSpan(new ForegroundColorSpan(getResources().getColor(aiv$a.mw_recipient_text_invalidate)), ((Integer) arrayList.get(i)).intValue(), ((Integer) arrayList2.get(i)).intValue(), 33);
                        }
                    }
                }
                this.t.setText(spannableString2);
            }
        }
        this.t.setVisibility(0);
        if (this.Q) {
            this.s.setVisibility(8);
        } else {
            this.s.setVisibility(0);
        }
        this.r.setVisibility(0);
        this.n.setVisibility(8);
    }

    public boolean dispatchDragEvent(DragEvent dragEvent) {
        if (this.J != null && isEnabled() && this.J.onDrag(this, dragEvent)) {
            return true;
        }
        return onDragEvent(dragEvent);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onDragEvent(DragEvent r8) {
        /*
        r7 = this;
        r1 = 1;
        r2 = 0;
        r0 = r8.getLocalState();
        r3 = r0 instanceof com.meizu.commonwidget.RecipientEdit.b;
        if (r3 != 0) goto L_0x0011;
    L_0x000a:
        r0 = r7.q;
        r0 = r0.onDragEvent(r8);
    L_0x0010:
        return r0;
    L_0x0011:
        r3 = r8.getX();
        r3 = (int) r3;
        r4 = r8.getY();
        r4 = (int) r4;
        r0 = (com.meizu.commonwidget.RecipientEdit.b) r0;
        r5 = r0.c;
        r6 = r8.getAction();
        switch(r6) {
            case 1: goto L_0x0034;
            case 2: goto L_0x005e;
            case 3: goto L_0x0073;
            case 4: goto L_0x00ed;
            case 5: goto L_0x0059;
            case 6: goto L_0x004b;
            default: goto L_0x0026;
        };
    L_0x0026:
        r0 = r2;
    L_0x0027:
        r1 = r8.getResult();
        if (r1 == 0) goto L_0x0010;
    L_0x002d:
        r7.T = r2;
        r7.U = r2;
        r7.V = r2;
        goto L_0x0010;
    L_0x0034:
        r7.U = r1;
        r7.W = r2;
        r0 = r7.getRecipients();
        r0 = (java.util.ArrayList) r0;
        r7.aa = r0;
        r7.M = r1;
        r0 = r7.hasFocus();
        r7.b(r0);
        r0 = r1;
        goto L_0x0027;
    L_0x004b:
        r0 = r7.o;
        r0.removeView(r5);
        r0 = r7.hasFocus();
        r7.b(r0);
        r0 = r1;
        goto L_0x0027;
    L_0x0059:
        r7.requestFocus();
        r0 = r1;
        goto L_0x0027;
    L_0x005e:
        r6 = r7.o;
        r5 = r6.indexOfChild(r5);
        r0 = r7.a(r0, r3, r4);
        if (r0 == r5) goto L_0x0071;
    L_0x006a:
        r0 = r7.hasFocus();
        r7.b(r0);
    L_0x0071:
        r0 = r1;
        goto L_0x0027;
    L_0x0073:
        r7.T = r1;
        r3 = r7.a(r0, r3, r4);
        r7.O = r3;
        r7.N = r1;
        if (r3 <= 0) goto L_0x00a6;
    L_0x007f:
        r0 = r7.P;
        if (r0 <= 0) goto L_0x00a8;
    L_0x0083:
        r0 = r7.P;
        if (r3 == r0) goto L_0x009a;
    L_0x0087:
        r0 = r7.z;
        r4 = r7.P;
        r4 = r4 + -1;
        r0 = r0.remove(r4);
        r0 = (java.lang.String) r0;
        r4 = r7.z;
        r3 = r3 + -1;
        r4.add(r3, r0);
    L_0x009a:
        r0 = r7.q;
        r0.setCursorVisible(r2);
        r7.u = r5;
        r5.setVisibility(r2);
        r7.P = r2;
    L_0x00a6:
        r0 = r1;
        goto L_0x0027;
    L_0x00a8:
        r0 = r8.getClipData();
        r4 = r0.getItemCount();
        if (r4 <= 0) goto L_0x00a6;
    L_0x00b2:
        r0 = r0.getItemAt(r2);
        r4 = r7.a;
        r0 = r0.coerceToText(r4);
        r0 = android.text.util.Rfc822Tokenizer.tokenize(r0);
        r4 = r0.length;
        if (r4 <= 0) goto L_0x00a6;
    L_0x00c3:
        r4 = r0[r2];
        r4 = r4.getAddress();
        r0 = r0[r2];
        r0 = r0.getName();
        r0 = r7.a(r4, r0, r3);
        if (r0 == 0) goto L_0x00e0;
    L_0x00d5:
        r0 = r7.q;
        r0.setCursorVisible(r2);
        r7.u = r5;
        r5.setVisibility(r2);
        goto L_0x00a6;
    L_0x00e0:
        r0 = r7.o;
        r0.removeViewAt(r3);
        r0 = 8;
        r5.setVisibility(r0);
        r7.N = r2;
        goto L_0x00a6;
    L_0x00ed:
        r7.V = r1;
        r0 = r7.W;
        if (r0 != 0) goto L_0x0026;
    L_0x00f3:
        r0 = r7.P;
        if (r0 <= 0) goto L_0x011c;
    L_0x00f7:
        r0 = r5.getVisibility();
        r3 = 4;
        if (r0 != r3) goto L_0x0139;
    L_0x00fe:
        r0 = r5.getParent();
        r0 = (android.view.ViewGroup) r0;
        if (r0 == 0) goto L_0x0109;
    L_0x0106:
        r0.removeView(r5);
    L_0x0109:
        r0 = r7.o;
        r3 = r7.P;
        r0.addView(r5, r3);
        r0 = r7.q;
        r0.setCursorVisible(r2);
        r7.u = r5;
        r5.setVisibility(r2);
    L_0x011a:
        r7.P = r2;
    L_0x011c:
        r7.M = r2;
        r0 = r7.hasFocus();
        r7.b(r0);
        r7.N = r2;
        r0 = r7.H;
        if (r0 == 0) goto L_0x0136;
    L_0x012b:
        r0 = r7.I;
        if (r0 == 0) goto L_0x0136;
    L_0x012f:
        r0 = r7.I;
        r0.b(r7);
        r7.H = r2;
    L_0x0136:
        r0 = r1;
        goto L_0x0027;
    L_0x0139:
        r0 = r7.P;
        r0 = r0 + -1;
        r7.a(r0, r2);
        goto L_0x011a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.commonwidget.RecipientEdit.onDragEvent(android.view.DragEvent):boolean");
    }

    private int a(b bVar, int i, int i2) {
        int left = (i - this.n.getLeft()) - bVar.a;
        int top = (i2 - this.n.getTop()) - bVar.b;
        View view = bVar.c;
        Rect rect = new Rect(left, top, view.getWidth() + left, view.getHeight() + top);
        top = rect.centerX();
        int centerY = rect.centerY();
        int indexOfChild = this.o.indexOfChild(view);
        Rect rect2 = new Rect();
        int childCount = this.o.getChildCount();
        int i3 = 0;
        while (i3 < childCount) {
            this.o.getChildAt(i3).getHitRect(rect2);
            int centerX = rect2.centerX();
            int centerY2 = rect2.centerY();
            if (rect2.contains(top, centerY) || rect.contains(centerX, centerY2)) {
                if (centerX < top) {
                    i3++;
                }
            } else if (rect.contains(rect2.left, centerY2)) {
                break;
            } else if (rect.contains(rect2.right, centerY2)) {
                i3++;
                break;
            } else {
                i3++;
            }
        }
        i3 = -1;
        if (i3 == 0) {
            i3 = 1;
        } else if (i3 == childCount) {
            i3 = childCount - 1;
        }
        if (indexOfChild > 0 && indexOfChild < i3) {
            i3--;
        }
        if (indexOfChild < 0 && r3 < 0) {
            i3 = childCount - 1;
        }
        if (i3 <= 0 || i3 == indexOfChild) {
            return indexOfChild;
        }
        LinearLayout.LayoutParams layoutParams;
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(view);
        }
        if (i3 <= 0 || i3 != 1) {
            top = (((this.m.getMeasuredWidth() - this.r.getMeasuredWidth()) - this.m.getPaddingStart()) - this.m.getPaddingEnd()) - (c(view) / 2);
        } else {
            this.s.measure(0, 0);
            top = ((((this.m.getMeasuredWidth() - this.s.getMeasuredWidth()) - this.r.getMeasuredWidth()) - this.m.getPaddingStart()) - this.m.getPaddingEnd()) - (c(view) / 2);
        }
        TextView a = ((ItemView) view).a();
        centerY = (c(view) / 2) + ((int) a.getPaint().measureText(String.valueOf(a.getText())));
        if (centerY > top) {
            layoutParams = (LinearLayout.LayoutParams) a.getLayoutParams();
            layoutParams.width = top;
            a.setLayoutParams(layoutParams);
        } else if (centerY > view.getWidth()) {
            layoutParams = (LinearLayout.LayoutParams) a.getLayoutParams();
            layoutParams.width = centerY;
            a.setLayoutParams(layoutParams);
        }
        this.o.addView(view, i3);
        if (childCount > 3) {
            TextView a2;
            int measureText;
            if (i3 == 1) {
                view = this.o.getChildAt(2);
                top = ((this.m.getMeasuredWidth() - this.r.getMeasuredWidth()) - this.m.getPaddingStart()) - this.m.getPaddingEnd();
                a2 = ((ItemView) view).a();
                measureText = (int) a.getPaint().measureText(String.valueOf(a2.getText()));
                if (measureText > top) {
                    layoutParams = (LinearLayout.LayoutParams) a2.getLayoutParams();
                    layoutParams.width = top;
                    a2.setLayoutParams(layoutParams);
                } else if (measureText > view.getWidth()) {
                    layoutParams = (LinearLayout.LayoutParams) a2.getLayoutParams();
                    layoutParams.width = measureText;
                    a2.setLayoutParams(layoutParams);
                }
            } else if (indexOfChild == 1) {
                view = this.o.getChildAt(1);
                this.s.measure(0, 0);
                top = (((this.m.getMeasuredWidth() - this.s.getMeasuredWidth()) - this.r.getMeasuredWidth()) - this.m.getPaddingStart()) - this.m.getPaddingEnd();
                a2 = ((ItemView) view).a();
                measureText = (int) a.getPaint().measureText(String.valueOf(a2.getText()));
                if (measureText > top) {
                    layoutParams = (LinearLayout.LayoutParams) a2.getLayoutParams();
                    layoutParams.width = top;
                    a2.setLayoutParams(layoutParams);
                } else if (measureText > view.getWidth()) {
                    layoutParams = (LinearLayout.LayoutParams) a2.getLayoutParams();
                    layoutParams.width = measureText;
                    a2.setLayoutParams(layoutParams);
                }
            }
        }
        return i3;
    }

    public boolean onLongClick(View view) {
        if (!this.L || !(view instanceof ItemView)) {
            return false;
        }
        c();
        CharSequence text = this.q.getText();
        if (!TextUtils.isEmpty(text)) {
            a(text);
            this.q.setText("");
        }
        if (view != this.u) {
            d(view);
        }
        if (this.u != null) {
            this.u.setSelected(false);
            if (view != this.u) {
                b(this.u);
            }
            this.u = null;
            this.q.setCursorVisible(true);
        }
        view.setSelected(true);
        this.P = this.o.indexOfChild(view);
        String str = (String) this.z.get(this.P - 1);
        String str2 = (String) this.A.get(str);
        if (TextUtils.isEmpty(str2)) {
            str2 = str;
        }
        ClipData newPlainText = ClipData.newPlainText(null, new Rfc822Token(str2, str, null).toString());
        if (this.ab == null) {
            this.ab = new b();
        }
        this.ab.c = (ItemView) view;
        if (view.startDrag(newPlainText, new l(this, view), this.ab, 0)) {
            view.setVisibility(4);
            if (this.I != null) {
                this.I.a(this);
                this.H = true;
            }
        }
        return true;
    }

    public void onClick(View view) {
        if (view instanceof AutoCompleteTextView) {
            if (this.u != null) {
                this.u.setSelected(false);
                b(this.u);
                this.u = null;
                this.q.setCursorVisible(true);
            }
        } else if (view instanceof ItemView) {
            c();
            CharSequence text = this.q.getText();
            if (!TextUtils.isEmpty(text)) {
                a(text);
                this.q.setText("");
            }
            if (this.u == null) {
                this.q.setCursorVisible(false);
            } else if (view == this.u) {
                String str;
                String str2 = (String) this.z.get(this.o.indexOfChild(this.u) - 1);
                String str3 = (String) this.A.get(str2);
                String[] split = str3.split(MzGroups.GROUP_SPLIT_MARK_VCARD);
                if (split.length > 1) {
                    str = split[0];
                } else {
                    str = str3;
                }
                ((InputMethodManager) this.a.getSystemService("input_method")).hideSoftInputFromWindow(this.q.getWindowToken(), 0);
                ((aiw) this.q.getAdapter()).a(str2, str);
                return;
            } else {
                this.u.setSelected(false);
                b(this.u);
            }
            this.u = view;
            view.setSelected(true);
            a(view);
            ((InputMethodManager) this.a.getSystemService("input_method")).showSoftInput(this.q, 0);
        } else {
            if (this.u != null) {
                this.u.setSelected(false);
                b(this.u);
                this.u = null;
                this.q.setCursorVisible(true);
            }
            this.q.requestFocus();
            ((InputMethodManager) this.a.getSystemService("input_method")).showSoftInput(this.q, 1);
            if (!this.q.isPopupShowing()) {
            }
        }
    }

    private void a(View view) {
        TextView a = ((ItemView) view).a();
        String valueOf = String.valueOf(a.getText());
        CharSequence substring = valueOf.substring(0, valueOf.length() - 1);
        float measureText = a.getPaint().measureText(valueOf);
        float measureText2 = a.getPaint().measureText(substring);
        int i = (int) ((measureText - measureText2) / CircleProgressBar.BAR_WIDTH_DEF_DIP);
        int i2 = (int) ((measureText - measureText2) - ((float) i));
        int i3 = i2 / 2;
        int i4 = i2 - i3;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) a.getLayoutParams();
        layoutParams.width = a.getMeasuredWidth() - i;
        a.setLayoutParams(layoutParams);
        a.setPadding(i3, 0, i4, 0);
        a.setText(substring);
    }

    private void b(View view) {
        TextView a = ((ItemView) view).a();
        CharSequence charSequence = String.valueOf(a.getText()) + "，";
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) a.getLayoutParams();
        layoutParams.width = (c(view) / 2) + a.getWidth();
        a.setLayoutParams(layoutParams);
        a.setPadding(0, 0, 0, 0);
        a.setText(charSequence);
    }

    private int c(View view) {
        return (int) ((ItemView) view).a().getPaint().measureText("，");
    }

    private void d(View view) {
        TextView a = ((ItemView) view).a();
        String valueOf = String.valueOf(a.getText());
        CharSequence substring = valueOf.substring(0, valueOf.length() - 1);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) a.getLayoutParams();
        layoutParams.width = a.getWidth() - (c(view) / 2);
        a.setLayoutParams(layoutParams);
        a.setText(substring);
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 66 && this.q.getListSelection() == -1) {
            c();
            CharSequence text = this.q.getText();
            if (!TextUtils.isEmpty(text)) {
                if (keyEvent.getAction() == 1) {
                    this.q.setText("");
                    a(text);
                }
                return true;
            }
        }
        if (keyEvent.getKeyCode() == 67 && keyEvent.getAction() == 0) {
            if (this.u != null) {
                int indexOfChild = this.o.indexOfChild(this.u);
                a(indexOfChild - 1, true);
                if (!this.Q && indexOfChild == 1 && this.o.getChildCount() > 2) {
                    View childAt = this.o.getChildAt(1);
                    if (childAt instanceof ItemView) {
                        this.s.measure(0, 0);
                        int measuredWidth = (((this.m.getMeasuredWidth() - this.s.getMeasuredWidth()) - this.r.getMeasuredWidth()) - this.m.getPaddingStart()) - this.m.getPaddingEnd();
                        TextView a = ((ItemView) childAt).a();
                        if (((int) a.getPaint().measureText(String.valueOf(a.getText()))) > measuredWidth) {
                            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) a.getLayoutParams();
                            layoutParams.width = measuredWidth;
                            a.setLayoutParams(layoutParams);
                        }
                    }
                }
                this.u = null;
                this.q.setCursorVisible(true);
                this.n.scrollTo(0, this.o.getMeasuredHeight());
                return true;
            } else if (this.o.getChildCount() > 2 && TextUtils.isEmpty(this.q.getText())) {
                this.u = this.o.getChildAt(this.o.getChildCount() - 2);
                this.u.setSelected(true);
                a(this.u);
                this.q.setCursorVisible(false);
                return true;
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    private CharSequence c(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            return charSequence;
        }
        int length = charSequence.length();
        int i = 0;
        while (i < length && charSequence.charAt(i) <= ' ') {
            i++;
        }
        if (i >= length) {
            return charSequence;
        }
        String charSequence2 = charSequence.toString();
        int indexOf = charSequence2.indexOf(" ", i);
        Object obj = null;
        int i2 = i;
        while (indexOf > 0 && indexOf <= length) {
            Object obj2;
            char charAt = charSequence2.charAt(indexOf - 1);
            if (charAt == ',' || charAt == ';') {
                a(charSequence2.substring(i2, indexOf - 1));
                obj2 = 1;
            } else {
                CharSequence substring = charSequence2.substring(i2, indexOf);
                if (((aiw) this.q.getAdapter()).d(substring)) {
                    a(substring);
                    int i3 = 1;
                } else {
                    obj2 = obj;
                }
            }
            int i4 = indexOf + 1;
            indexOf = charSequence2.indexOf(" ", i4);
            while (i4 == indexOf) {
                i4 = indexOf + 1;
                indexOf = charSequence2.indexOf(" ", i4);
            }
            if (obj2 != null) {
                obj2 = null;
            } else {
                i4 = i2;
            }
            i2 = i4;
            obj = obj2;
        }
        if (i2 >= length) {
            return "";
        }
        if (i2 != i) {
            return charSequence2.substring(i2);
        }
        return charSequence;
    }

    public boolean requestFocus(int i, Rect rect) {
        return this.q.requestFocus(i, rect);
    }

    public void setFocusable(boolean z) {
        this.q.setFocusable(z);
    }

    public void setFocusableInTouchMode(boolean z) {
        this.q.setFocusableInTouchMode(z);
    }

    protected void onMeasure(int i, int i2) {
        this.q.measure(0, 0);
        if ((this.q.getLayout().getLineWidth(0) + ((float) this.q.getTotalPaddingLeft())) + ((float) this.q.getTotalPaddingRight()) >= ((float) this.E) && this.E < this.o.getWidth() && this.o.indexOfChild(this.q) > 1) {
            AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams) this.q.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.x = 0;
                layoutParams.y += this.q.getHeight();
                this.E = this.o.getWidth();
                this.q.setWidth(this.E);
            }
        }
        super.onMeasure(i, i2);
        if (this.D) {
            this.D = false;
            b(hasFocus());
            super.onMeasure(i, i2);
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        Object string = Secure.getString(this.v, "default_input_method");
        if (!TextUtils.equals(string, this.F)) {
            this.F = string;
            int imeOptions = this.q.getImeOptions() & InputDeviceCompat.SOURCE_ANY;
            if (!b() || !TextUtils.isEmpty(this.q.getText())) {
                this.q.setImeOptions(imeOptions | 1);
                this.q.setImeActionLabel(getResources().getString(aiv$f.mw_recipient_edit_imeActionLabel), 1);
            } else if (focusSearch(130) != null) {
                this.q.setImeOptions(imeOptions | 5);
                this.q.setImeActionLabel(null, 5);
            } else {
                this.q.setImeOptions(imeOptions | 6);
                this.q.setImeActionLabel(null, 6);
            }
            setInputType(this.x);
        }
        if (this.q.isPopupShowing()) {
            this.q.a();
        }
        super.onLayout(z, i, i2, i3, i4);
    }

    private void e() {
        if (getMeasuredWidth() != 0) {
            if (this.o.getMeasuredWidth() == 0) {
                measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
            }
            if (this.o.getMeasuredWidth() < this.t.getMeasuredWidth()) {
                this.o.measure(MeasureSpec.makeMeasureSpec(this.t.getMeasuredWidth(), 1073741824), MeasureSpec.makeMeasureSpec(0, 0));
            }
            if (getLayoutDirection() == 1) {
                g();
            } else {
                f();
            }
            LayoutParams layoutParams = getLayoutParams();
            if (this.M) {
                layoutParams.height = -2;
            } else if (this.w > 0) {
                measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), MeasureSpec.makeMeasureSpec(0, 0));
                if (getMeasuredHeight() >= this.w) {
                    layoutParams.height = this.w;
                } else {
                    layoutParams.height = -2;
                }
            }
        }
    }

    private void f() {
        int i;
        int childCount = this.o.getChildCount();
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i2 < childCount) {
            View childAt = this.o.getChildAt(i2);
            if (childAt.getVisibility() != 8) {
                if (i2 == 1 && childCount > 2 && (childAt instanceof ItemView)) {
                    ((ItemView) childAt).a().setMaxWidth(this.o.getMeasuredWidth() - i4);
                }
                childAt.measure(0, 0);
                if (i2 == childCount - 1) {
                    i = i4 + 60;
                } else {
                    i = childAt.getMeasuredWidth() + i4;
                }
                if (i > this.o.getMeasuredWidth() && i2 > 1) {
                    i3 = childAt.getMeasuredHeight() + i3;
                    i4 = 0;
                }
                AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams) childAt.getLayoutParams();
                layoutParams.x = i4;
                layoutParams.y = i3;
                if (this.N && this.O == i2) {
                    i4 += childAt.getMeasuredWidth() + (c(childAt) / 2);
                } else {
                    i4 += childAt.getMeasuredWidth();
                }
            }
            i2++;
        }
        this.E = this.o.getMeasuredWidth() - ((AbsoluteLayout.LayoutParams) this.q.getLayoutParams()).x;
        if (this.E > 0) {
            i = this.E;
        } else {
            i = 60;
        }
        this.E = i;
        this.q.setWidth(this.E);
    }

    private void g() {
        AbsoluteLayout.LayoutParams layoutParams;
        int measuredWidth = this.o.getMeasuredWidth();
        int childCount = this.o.getChildCount();
        int i = 0;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = this.o.getChildAt(i2);
            if (childAt.getVisibility() != 8) {
                int i3;
                if (i2 == 1 && childCount > 2 && (childAt instanceof ItemView)) {
                    ((ItemView) childAt).a().setMaxWidth(measuredWidth);
                }
                childAt.measure(0, 0);
                if (i2 == childCount - 1) {
                    i3 = 60;
                } else {
                    i3 = childAt.getMeasuredWidth();
                }
                if (measuredWidth - i3 < 0) {
                    if (this.o.getMeasuredWidth() < i3) {
                        i3 = 0;
                    } else {
                        i3 = this.o.getMeasuredWidth() - i3;
                    }
                    i += childAt.getMeasuredHeight();
                    measuredWidth = i3;
                } else {
                    measuredWidth -= i3;
                }
                layoutParams = (AbsoluteLayout.LayoutParams) childAt.getLayoutParams();
                layoutParams.x = measuredWidth;
                layoutParams.y = i;
            }
        }
        layoutParams = (AbsoluteLayout.LayoutParams) this.q.getLayoutParams();
        this.E = layoutParams.x + 60;
        layoutParams.x = 0;
        this.q.setWidth(this.E);
    }

    public boolean a(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(charSequence.toString());
        if (a(arrayList) > 0) {
            return true;
        }
        return false;
    }

    public boolean a(CharSequence charSequence, CharSequence charSequence2) {
        if (TextUtils.isEmpty(charSequence)) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        if (TextUtils.isEmpty(charSequence2)) {
            arrayList.add(charSequence.toString());
        } else {
            arrayList.add(charSequence.toString() + MzGroups.GROUP_SPLIT_MARK_VCARD + charSequence2.toString());
        }
        if (a(arrayList) > 0) {
            return true;
        }
        return false;
    }

    public int a(ArrayList<String> arrayList) {
        if (arrayList == null) {
            return 0;
        }
        int size = arrayList.size();
        int i = 0;
        boolean z = false;
        int i2 = 0;
        while (i < size) {
            boolean z2;
            int i3;
            String str = (String) arrayList.get(i);
            if (str == null || TextUtils.isEmpty(str)) {
                z2 = z;
                i3 = i2;
            } else {
                String[] split = str.split(MzGroups.GROUP_SPLIT_MARK_VCARD);
                if (2 == split.length) {
                    z2 = a(split[0], split[1]);
                } else if (split.length == 1) {
                    z2 = a(split[0], null);
                } else if (3 == split.length) {
                    z2 = a(split[0], split[1] + MzGroups.GROUP_SPLIT_MARK_VCARD + split[2]);
                } else {
                    z2 = z;
                }
                i3 = z2 ? i2 + 1 : i2;
            }
            i++;
            i2 = i3;
            z = z2;
        }
        if (i2 <= 0) {
            return i2;
        }
        if (this.d != null) {
            this.d.a();
        }
        if (this.e != null) {
            this.e.a(this);
        }
        if (this.z.size() > 1 && (this.f & 6) > 0 && this.b != null) {
            this.b.a(this.f & this.x);
        }
        if (this.D || this.S) {
            return i2;
        }
        b(hasFocus());
        return i2;
    }

    public boolean a(String str, String str2) {
        return a(str, str2, -1);
    }

    private void b(String str, String str2) {
        this.S = true;
        Message obtain = Message.obtain(this.h, 1);
        obtain.obj = new a(str, str2);
        this.h.sendMessage(obtain);
    }

    public boolean a(String str, String str2, int i) {
        String a = a(str);
        if (TextUtils.isEmpty(a) || this.z.contains(a)) {
            return false;
        }
        String str3;
        CharSequence charSequence;
        View childAt;
        if ((this.f & 2) == 2) {
            int i2 = 1;
        } else {
            boolean z = false;
        }
        if ((this.f & 4) == 4) {
            int i3 = 1;
        } else {
            boolean z2 = false;
        }
        if (i2 == 0 && ((aiw) this.q.getAdapter()).d(a)) {
            this.f |= 2;
        } else if (i3 == 0 && ((aiw) this.q.getAdapter()).b(a)) {
            this.f |= 4;
        }
        Object obj = "";
        if (!TextUtils.isEmpty(str2)) {
            String[] split = str2.split(MzGroups.GROUP_SPLIT_MARK_VCARD);
            if (split.length > 1) {
                str3 = split[0];
                obj = split[1];
                if (TextUtils.isEmpty(str3)) {
                    b(a, str2);
                    if (TextUtils.isEmpty(str3)) {
                        charSequence = str3;
                    } else {
                        Object obj2 = a;
                    }
                }
                childAt = this.o.getChildAt(i);
                if (childAt != null) {
                    childAt.setOnClickListener(this);
                    childAt.setOnLongClickListener(this);
                } else if (TextUtils.isEmpty(obj)) {
                    i = a(str3 + obj, a);
                } else {
                    i = a(charSequence, a);
                }
                this.z.add(i - 1, a);
                if (TextUtils.isEmpty(obj)) {
                    this.A.put(a, str3 + MzGroups.GROUP_SPLIT_MARK_VCARD + obj);
                } else {
                    this.A.put(a, str3);
                }
                if (this.K != null) {
                    this.K.a(a, 1);
                }
                return true;
            }
        }
        str3 = str2;
        if (TextUtils.isEmpty(str3)) {
            b(a, str2);
            if (TextUtils.isEmpty(str3)) {
                Object obj22 = a;
            } else {
                charSequence = str3;
            }
        }
        childAt = this.o.getChildAt(i);
        if (childAt != null) {
            childAt.setOnClickListener(this);
            childAt.setOnLongClickListener(this);
        } else if (TextUtils.isEmpty(obj)) {
            i = a(charSequence, a);
        } else {
            i = a(str3 + obj, a);
        }
        this.z.add(i - 1, a);
        if (TextUtils.isEmpty(obj)) {
            this.A.put(a, str3);
        } else {
            this.A.put(a, str3 + MzGroups.GROUP_SPLIT_MARK_VCARD + obj);
        }
        if (this.K != null) {
            this.K.a(a, 1);
        }
        return true;
    }

    private int a(CharSequence charSequence, String str) {
        View view;
        if (this.i) {
            view = (ItemView) inflate(this.a, aiv$e.mw_recipient_itemview_easymode, null);
        } else if (this.G == 1) {
            r0 = (ItemView) inflate(this.a, aiv$e.mw_recipient_itemview_mns, null);
        } else if (this.G == 2) {
            r0 = (ItemView) inflate(this.a, aiv$e.mw_recipient_itemview_calendar, null);
        } else {
            r0 = (ItemView) inflate(this.a, aiv$e.mw_recipient_itemview, null);
        }
        TextView a = view.a();
        if (!b(str) && this.R) {
            if (this.G == 2) {
                a.setTextColor(getResources().getColor(aiv$a.mw_recipient_text_invalidate_calendar));
            } else {
                a.setTextColor(getResources().getColor(aiv$a.mw_recipient_text_invalidate));
            }
        }
        a.setText(charSequence + "，");
        view.setClickable(true);
        view.setOnClickListener(this);
        view.setLongClickable(true);
        view.setOnLongClickListener(this);
        view.setFocusable(false);
        view.setFocusableInTouchMode(false);
        int childCount = this.o.getChildCount() - 1;
        this.o.addView(view, childCount);
        return childCount;
    }

    private String a(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        String trim = str.trim();
        if ((this.x & 4) == 4 && trim.startsWith("@")) {
            return trim;
        }
        String stringBuilder;
        if ((this.x & 1) == 1 && ((aiw) this.q.getAdapter()).c(trim)) {
            int length = trim.length();
            StringBuilder stringBuilder2 = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                char charAt = trim.charAt(i);
                if (!(charAt == ' ' || charAt == '.' || charAt == '-' || charAt == '(' || charAt == ')')) {
                    stringBuilder2.append(charAt);
                }
            }
            stringBuilder = stringBuilder2.toString();
        } else {
            if (((aiw) this.q.getAdapter()).d(trim)) {
                Rfc822Token[] tokenize = Rfc822Tokenizer.tokenize(trim);
                if (tokenize.length > 0) {
                    stringBuilder = tokenize[0].getAddress();
                }
            }
            stringBuilder = trim;
        }
        return stringBuilder;
    }

    public boolean b(CharSequence charSequence) {
        CharSequence a = a(charSequence.toString());
        if (TextUtils.isEmpty(a)) {
            return false;
        }
        this.B.remove(charSequence);
        return a(this.z.indexOf(a), true);
    }

    private boolean a(int i, boolean z) {
        int size = this.z.size();
        if (i <= -1 || i >= size) {
            return false;
        }
        String str = (String) this.z.remove(i);
        this.A.remove(str);
        if (((aiw) this.q.getAdapter()).d(str)) {
            this.f &= -3;
        } else if (((aiw) this.q.getAdapter()).b(str)) {
            this.f &= -5;
        }
        if (z) {
            this.o.removeViewAt(i + 1);
            b(hasFocus());
        }
        if (this.d != null) {
            this.d.a();
        }
        if (this.e != null) {
            this.e.a(this);
        }
        if (this.K != null) {
            this.K.a(str, 2);
        }
        return true;
    }

    public int getRecipientCount() {
        int size = this.z.size();
        CharSequence a = a(this.q.getText().toString());
        if (TextUtils.isEmpty(a) || this.z.contains(a)) {
            return size;
        }
        return size + 1;
    }

    public void setEnabledDrag(boolean z) {
        this.L = z;
    }

    public void setDragWatcher(m mVar) {
        this.I = mVar;
    }

    public void setOnDragListener(OnDragListener onDragListener) {
        this.J = onDragListener;
    }

    public void setHint(CharSequence charSequence) {
        this.p.setText(charSequence);
        this.s.setText(charSequence);
    }

    public void setMaxHeight(int i) {
        this.w = i;
    }

    public int getMaxHeight() {
        return this.w;
    }

    public void setHeaderText(String str) {
    }

    public void setButtonClickListener(OnClickListener onClickListener) {
        this.r.setOnClickListener(onClickListener);
    }

    public void setButtonVisibility(boolean z) {
        this.C = z;
        this.r.setVisibility(z ? 0 : 8);
    }

    public boolean getButtonVisibility() {
        return this.C;
    }

    public void setInputType(int i) {
        if (this.x != i) {
            if (i == 2) {
                setBackgroundResource(aiv$c.mw_recipient_divider_email_2px);
            } else {
                setBackgroundResource(aiv$c.mw_recipient_divider_sms_2px);
            }
        }
        this.x = i;
    }

    public int getInputType() {
        return this.x;
    }

    public void setImeOptions(int i) {
        this.q.setImeOptions((this.q.getImeOptions() & 255) | i);
    }

    public int getImeOptions() {
        return this.q.getImeOptions() & InputDeviceCompat.SOURCE_ANY;
    }

    public void setOnKeyPreImeListener(a aVar) {
        this.k = aVar;
    }

    public void setOnTextChangedListener(j jVar) {
        this.l = jVar;
    }

    public void setOnRecipientChangedListener(e eVar) {
        this.d = eVar;
    }

    public void setOnRecipientSimpleChangedListener(g gVar) {
        this.e = gVar;
    }

    public void setOnRecipientFirstAddListener(f fVar) {
        this.c = fVar;
    }

    public void setOnSingleRecipientAddWhenGroupListener(i iVar) {
        this.b = iVar;
    }

    public void setOnDropDownListener(d dVar) {
        this.q.a(this, dVar);
    }

    public void setAdapter(aiw aiw) {
        this.q.setAdapter(aiw);
        setInputType(aiw.i());
    }

    public void setRecipientSipStateCheckListener(h hVar) {
        this.K = hVar;
    }

    public void setScrollListener(OnScrollListener onScrollListener) {
        this.q.setOnScrollListener(onScrollListener);
    }

    public void setNoBackground(Boolean bool) {
        if (bool.booleanValue()) {
            setBackground(null);
        }
    }

    public void setDropDownAnchor(int i) {
        this.q.setDropDownAnchor(i);
    }

    public void d() {
        this.q.setText("");
        this.f = 0;
        int childCount = this.o.getChildCount() - 2;
        if (childCount > 0) {
            this.z.clear();
            this.B.clear();
            this.A.clear();
            this.o.removeViews(1, childCount);
            b(hasFocus());
            if (this.d != null) {
                this.d.a();
            }
            if (this.e != null) {
                this.e.a(this);
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.q.setOnItemClickListener(onItemClickListener);
    }

    public HashMap<String, aiu> getRecipientsInfo() {
        return this.B;
    }

    public ArrayList<String> b(int i) {
        int i2;
        ArrayList<String> arrayList = new ArrayList();
        aiw aiw = (aiw) this.q.getAdapter();
        aiw = (aiw) this.q.getAdapter();
        int i3 = ((this.f & 2) & i) == 2 ? 1 : 0;
        aiw = (aiw) this.q.getAdapter();
        aiw = (aiw) this.q.getAdapter();
        if (((this.f & 4) & i) == 4) {
            i2 = 1;
        } else {
            i2 = 0;
        }
        int size = this.z.size();
        String a = a(this.q.getText().toString());
        if (size > 0 && ((i3 != 0 && ((aiw) this.q.getAdapter()).d(a)) || (i2 != 0 && ((aiw) this.q.getAdapter()).b(a)))) {
            this.q.setText("");
        }
        if (!(i3 == 0 && i2 == 0) && size > 1) {
            ArrayList arrayList2 = (ArrayList) this.z.clone();
            for (int i4 = size - 1; i4 > 0; i4--) {
                String str = (String) arrayList2.get(i4);
                if ((i3 != 0 && ((aiw) this.q.getAdapter()).d(str)) || (i2 != 0 && ((aiw) this.q.getAdapter()).b(str))) {
                    this.z.remove(i4);
                    this.A.remove(str);
                    this.o.removeViewAt(i4 + 1);
                    arrayList.add(str);
                }
            }
            if (this.z.size() > 1) {
                String str2 = (String) this.z.get(0);
                if ((i3 != 0 && ((aiw) this.q.getAdapter()).d(str2)) || (i2 != 0 && ((aiw) this.q.getAdapter()).b(str2))) {
                    this.z.remove(0);
                    this.A.remove(str2);
                    this.o.removeViewAt(1);
                    arrayList.add(str2);
                }
            }
            if (i3 != 0 && !((aiw) this.q.getAdapter()).d((String) this.z.get(0))) {
                aiw = (aiw) this.q.getAdapter();
                this.f &= -3;
            } else if (!(i2 == 0 || ((aiw) this.q.getAdapter()).b((String) this.z.get(0)))) {
                aiw = (aiw) this.q.getAdapter();
                this.f &= -5;
            }
            b(hasFocus());
            if (this.d != null) {
                this.d.a();
            }
        }
        return arrayList;
    }

    public ArrayList<String> getRecipientDataList() {
        return this.z;
    }

    public HashMap<String, String> getRecipientMap() {
        return this.A;
    }

    public aiw getAdapter() {
        return (aiw) this.q.getAdapter();
    }

    public boolean getmIsFirstLayout() {
        return this.D;
    }

    public List<String> getValidNumbers() {
        String str;
        ArrayList arrayList = new ArrayList();
        Iterator it = this.z.iterator();
        while (it.hasNext()) {
            str = (String) it.next();
            if (b(str)) {
                arrayList.add(str);
            }
        }
        str = a(this.q.getText().toString());
        if (!(TextUtils.isEmpty(str) || this.z.contains(str) || !b(str))) {
            arrayList.add(str);
        }
        if (arrayList.size() > 0) {
            return arrayList;
        }
        return null;
    }

    private boolean b(String str) {
        boolean z = false;
        if ((this.x & 1) == 1) {
            z = ((aiw) this.q.getAdapter()).c(str);
        }
        if (!z && (this.x & 2) == 2) {
            z = ((aiw) this.q.getAdapter()).d(str);
        }
        if (z || (this.x & 4) != 4) {
            return z;
        }
        return ((aiw) this.q.getAdapter()).b(str);
    }

    public List<String> getRecipients() {
        String str;
        ArrayList arrayList = new ArrayList();
        Iterator it = this.z.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            str = (String) this.A.get(str2);
            if (TextUtils.isEmpty(str)) {
                str = str2;
            } else {
                String[] split = str.split(MzGroups.GROUP_SPLIT_MARK_VCARD);
                if (split.length > 1) {
                    str = split[0];
                }
            }
            arrayList.add(str2 + MzGroups.GROUP_SPLIT_MARK_VCARD + str);
        }
        str = a(this.q.getText().toString());
        if (!(TextUtils.isEmpty(str) || this.z.contains(str))) {
            str2 = ((aiw) this.q.getAdapter()).a(str);
            if (TextUtils.isEmpty(str2)) {
                str2 = str;
            }
            arrayList.add(str + MzGroups.GROUP_SPLIT_MARK_VCARD + str2);
        }
        if (arrayList.size() > 0) {
            return arrayList;
        }
        return null;
    }

    public List<String> getAllNumbers() {
        List arrayList = new ArrayList();
        if (this.z != null && this.z.size() > 0) {
            arrayList = (ArrayList) this.z.clone();
        }
        CharSequence a = a(this.q.getText().toString());
        if (!(TextUtils.isEmpty(a) || this.z.contains(a))) {
            arrayList.add(a);
        }
        return arrayList.size() > 0 ? arrayList : null;
    }

    public boolean h() {
        Iterator it = this.z.iterator();
        while (it.hasNext()) {
            if (!b((String) it.next())) {
                return true;
            }
        }
        String a = a(this.q.getText().toString());
        if (TextUtils.isEmpty(a) || b(a)) {
            return false;
        }
        return true;
    }

    public boolean i() {
        Iterator it = this.z.iterator();
        while (it.hasNext()) {
            if (b((String) it.next())) {
                return true;
            }
        }
        String a = a(this.q.getText().toString());
        if (TextUtils.isEmpty(a) || !b(a)) {
            return false;
        }
        return true;
    }

    public RecipientAutoCompleteTextView getRecipientAutoCompleteTextView() {
        return this.q;
    }

    public String getInputText() {
        return a(this.q.getText().toString());
    }

    public void setIsValidateRecognition(boolean z) {
        this.R = z;
    }

    public void setRecipientColorType(int i) {
        this.G = i;
        if (this.G == 1) {
            this.q.setTextColor(getResources().getColor(aiv$a.mw_recipient_text_green));
            this.t.setTextColor(getResources().getColor(aiv$a.mw_recipient_text_green));
        } else if (this.G == 2) {
            this.q.setTextColor(getResources().getColor(aiv$a.mw_recipient_text_red));
            this.t.setTextColor(getResources().getColor(aiv$a.mw_recipient_text_red));
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(RecipientEdit.class.getName());
    }
}
