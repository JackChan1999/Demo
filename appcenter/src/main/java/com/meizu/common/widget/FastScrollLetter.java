package com.meizu.common.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AbsListView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

@TargetApi(18)
@SuppressLint({"ViewConstructor"})
public class FastScrollLetter extends View {
    private static final String b = FastScrollLetter.class.getSimpleName();
    private static final String[] c = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private int A;
    private int B;
    private int C;
    private Bitmap D;
    private AbsListView E;
    private SectionIndexer F;
    private String G;
    private int H;
    private SparseArray<Integer> I;
    private int J;
    private int K;
    private int L;
    private int M;
    private a N;
    private int O;
    Paint a;
    private boolean d;
    private boolean e;
    private int f;
    private TextView g;
    private String h;
    private int i;
    private int j;
    private String[] k;
    private String[] l;
    private String[] m;
    private String n;
    private int o;
    private int p;
    private int q;
    private int r;
    private int s;
    private int t;
    private int u;
    private int v;
    private int w;
    private int x;
    private int y;
    private int z;

    public interface a {
        int a(int i);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(FastScrollLetter.class.getName());
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public void setHideLetterNum(String hideStr, int num) {
        int pos = 0;
        this.G = hideStr;
        this.H = num;
        String[] letters = new String[((((this.l.length - 1) / (this.H + 1)) * 2) + 2)];
        int i = 0;
        while (i < this.l.length) {
            int i2 = pos + 1;
            letters[pos] = this.l[i];
            pos = i2 + 1;
            letters[i2] = this.G;
            i += this.H + 1;
        }
        letters[letters.length - 1] = this.l[this.l.length - 1];
        this.l = letters;
    }

    public void setHideLetterStr(String hideStr, Bitmap bitmap) {
        if (bitmap != null) {
            this.D = bitmap;
        }
        this.G = hideStr;
    }

    public void setOverlayTextLetters(String[] overlayTextLetters) {
        this.k = overlayTextLetters;
        if (this.k == null || this.k.length == 0) {
            setOverLayText(this.n);
        }
    }

    public void setHideLetter(SparseArray<Integer> sparseArray, String[] hideLetters) {
        this.I = sparseArray;
        this.l = hideLetters;
    }

    public void setOverlayParam(int overlayTextWidth, int overlayTextMarginRight) {
        if (overlayTextWidth != -1) {
            this.s = overlayTextWidth;
            this.r = overlayTextWidth;
        }
        if (overlayTextMarginRight != -1) {
            this.t = overlayTextMarginRight;
        }
    }

    public void setFastScrollAlwaysVisible(boolean isAlwayShowLetter) {
        this.d = isAlwayShowLetter;
        if (this.d) {
            setVisibility(0);
        }
    }

    public void setLetterActiveColor(int defaultColor, int activeColor) {
        this.x = activeColor;
        this.w = defaultColor;
        this.a.setColor(this.w);
        invalidate();
    }

    public void setFastScrollEnabled(boolean isEnable) {
        this.e = isEnable;
        setVisibility(isEnable ? 0 : 8);
    }

    public void setLetters(String[] letters) {
        this.l = letters;
        this.m = letters;
        setOverlayTextLetters(letters);
    }

    public void setTopLetter(String topLetter) {
        this.n = topLetter;
        if (this.k == null || this.k.length == 0) {
            setOverLayText(this.n);
        }
    }

    public void setHeaderHeight(int height) {
        this.M = height;
    }

    public void setLetterParam(int letterTextSize, int letterTextColor, int letterMarginTop, int letterMarginBottom, int letterMarginRight, int letterWidth) {
        if (letterTextSize != -1) {
            this.v = letterTextSize;
            this.a.setTextSize((float) this.v);
        }
        if (letterTextColor != -1) {
            this.w = letterTextColor;
            this.x = this.w;
            this.a.setColor(this.w);
            invalidate();
        }
        if (letterMarginTop != -1) {
            this.y = letterMarginTop;
        }
        if (letterMarginBottom != -1) {
            this.z = letterMarginBottom;
        }
        if (letterMarginRight != -1) {
            this.A = letterMarginRight;
        }
        if (letterWidth != -1) {
            this.B = letterWidth;
        }
    }

    public void setLayoutPaddingLeft(int paddingLeft) {
        this.C = paddingLeft;
    }

    @TargetApi(16)
    public void setLetterBackground(Drawable drawable) {
        setBackground(drawable);
    }

    @TargetApi(16)
    public void setOverlayBackground(Drawable drawable) {
        this.g.setBackground(drawable);
    }

    @TargetApi(17)
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int left = this.E.getWidth() - (this.A + this.B);
        int top = this.y;
        int right = this.E.getWidth() - this.A;
        int bottom = this.E.getHeight() - this.z;
        if (this.E.getLayoutDirection() == 0) {
            layout(left, top, right, bottom);
        } else {
            layout(this.A, top, this.A + this.B, bottom);
        }
        this.g.measure(this.r, this.r);
        setOverlayTextLayout(0.0f);
    }

    public void setSectionCompare(a compare) {
        this.N = compare;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!this.e || this.E.getScrollY() != 0) {
            return false;
        }
        int action = event.getAction();
        float y = event.getY();
        if (y < 0.0f) {
            y = 0.0f;
        }
        if (this.l == null || this.l.length == 0 || this.E == null || this.g == null || this.k == null || this.k.length == 0) {
            return false;
        }
        int site = a(y - ((float) this.y));
        switch (action) {
            case 0:
                if (site < 0) {
                    return false;
                }
                this.K = (int) event.getY();
                if (a(event.getX(), event.getY())) {
                    this.a.setColor(this.x);
                    invalidate();
                    this.E.requestDisallowInterceptTouchEvent(true);
                    b();
                    setOverlayTextLayout(event.getY());
                    a(true, (View) this);
                    a(event, site);
                    this.f = 1;
                    return true;
                } else if (!this.n.equals("") && b(event.getX(), event.getY())) {
                    this.i = -1;
                    this.a.setColor(this.x);
                    invalidate();
                    this.f = 1;
                    setOverlayTextLayout((float) getTop());
                    a(true, (View) this);
                    a();
                    return true;
                }
            case 1:
            case 3:
            case 4:
                this.E.requestDisallowInterceptTouchEvent(false);
                if (this.f == 1) {
                    this.i = -1;
                    this.a.setColor(this.w);
                    invalidate();
                    setOverlayTextLayout((float) this.K);
                    a(false, (View) this);
                    this.f = 0;
                    return true;
                }
                break;
            case 2:
                break;
        }
        if (this.f == 1) {
            if (site >= 0 && site < this.k.length) {
                this.K = (int) event.getY();
                if (this.i == -1) {
                    setOverlayTextLayout(event.getY());
                }
                a(event, site);
            } else if (!this.n.equals("") && b(event.getX(), event.getY())) {
                this.i = -1;
                a();
            }
        }
        if (this.f == 1) {
            return true;
        }
        return false;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingLeft = this.C;
        if (this.E.getLayoutDirection() == 1) {
            paddingLeft = this.C * -1;
        }
        String[] letters = this.l;
        if (letters != null && letters.length != 0) {
            int height = getHeight();
            int width = getWidth();
            this.j = height / letters.length;
            int i = 0;
            while (i < letters.length) {
                float xPos = (((float) (width / 2)) - (this.a.measureText(letters[i].toString()) / 2.0f)) + ((float) paddingLeft);
                float yPos = (float) ((this.j * i) + this.j);
                if (this.D == null || !letters[i].toString().equals(this.G)) {
                    canvas.drawText(letters[i].toString(), xPos, yPos, this.a);
                } else {
                    canvas.drawBitmap(this.D, (float) (((width / 2) - (this.D.getWidth() / 2)) + paddingLeft), (float) ((this.j * i) + (this.j / 2)), this.a);
                }
                i++;
            }
        }
    }

    private boolean a(float x, float y) {
        if (((float) getLeft()) >= x || x >= ((float) getRight()) || ((float) getTop()) >= y || ((float) getBottom()) <= y) {
            return false;
        }
        return true;
    }

    private boolean b(float x, float y) {
        if (((float) getLeft()) >= x || x >= ((float) getRight()) || ((float) getTop()) <= y || ((float) getBottom()) <= y) {
            return false;
        }
        return true;
    }

    private int a(float y) {
        this.j = getHeight() / this.l.length;
        int site = (int) (Math.ceil((double) (y / ((float) this.j))) - 1.0d);
        int pos = 0;
        if (site < 0 || site >= this.l.length) {
            return -1;
        }
        int i;
        float offY;
        if (this.I == null) {
            for (i = 0; i < site; i++) {
                if (this.l[i].equals(this.G)) {
                    pos += this.H;
                } else {
                    pos++;
                }
            }
            offY = y - ((float) (this.j * site));
            if (this.l[site].equals(this.G)) {
                pos += (int) (offY / ((float) (this.j / this.H)));
            }
        } else {
            for (i = 0; i < site; i++) {
                if (this.l[i].equals(this.G)) {
                    pos += ((Integer) this.I.get(i)).intValue();
                } else {
                    pos++;
                }
            }
            offY = y - ((float) (this.j * site));
            if (this.l[site].equals(this.G)) {
                pos += (int) Math.floor((double) ((offY / ((float) this.j)) * ((float) ((Integer) this.I.get(site)).intValue())));
            }
        }
        return pos;
    }

    private void a() {
        setOverLayText(this.n);
        if (this.E instanceof ListView) {
            ((ListView) this.E).setSelectionFromTop(0, -this.M);
        } else {
            this.E.setSelection(this.L);
        }
    }

    private int a(int site) {
        int section = -1;
        if (this.N != null) {
            return this.N.a(site);
        }
        String letter = this.k[site];
        Object[] obj = this.F.getSections();
        if (obj == null) {
            return -1;
        }
        for (int i = 0; i < obj.length; i++) {
            if (obj[i].toString().equals(letter)) {
                section = i;
                break;
            }
        }
        int footerViewCount = 0;
        if (this.O == 2 && section >= 0) {
            return section;
        }
        if (this.O == 1) {
            int position = this.F.getPositionForSection(section);
            if (this.E instanceof ListView) {
                footerViewCount = ((ListView) this.E).getFooterViewsCount();
            }
            if (position < this.E.getCount() - footerViewCount && this.F.getSectionForPosition(position) == section) {
                return section;
            }
        }
        return -1;
    }

    private void a(int site, float y) {
        ListAdapter adapter = (ListAdapter) this.E.getAdapter();
        if (adapter instanceof HeaderViewListAdapter) {
            this.L = ((HeaderViewListAdapter) adapter).getHeadersCount();
            adapter = ((HeaderViewListAdapter) adapter).getWrappedAdapter();
        }
        if (adapter instanceof SectionIndexer) {
            this.F = (SectionIndexer) adapter;
            int currentSite = this.i;
            int scrollSite = site;
            int selectionPos = b(scrollSite);
            if (this.i == -1) {
                selectionPos = c(scrollSite);
            }
            if (this.i < 0 || this.i >= this.k.length) {
                if (this.n != null && !this.n.equals("")) {
                    setOverLayText(this.n);
                    return;
                }
                return;
            } else if (currentSite != this.i) {
                setOverLayText(this.i);
                if (this.E instanceof ListView) {
                    ((ListView) this.E).setSelectionFromTop(this.L + selectionPos, -this.M);
                    return;
                } else {
                    this.E.setSelection(this.L + selectionPos);
                    return;
                }
            } else {
                return;
            }
        }
        Log.w(b, "mSectionIndexer is null, adapter is not implements");
    }

    private void setOverLayText(int site) {
        setOverLayText(this.k[site]);
    }

    private void setOverLayText(String letter) {
        int textSize = this.q;
        if (letter != this.h) {
            this.h = letter;
            switch (this.h.length()) {
                case 1:
                    textSize = this.o;
                    break;
                case 2:
                case 3:
                case 4:
                    textSize = this.p;
                    break;
            }
            this.g.setTextSize(0, (float) textSize);
            this.g.setText(this.h);
        }
    }

    private int b(int letterSite) {
        int position = -1;
        this.i = -1;
        int letterSite2 = letterSite;
        while (position == -1) {
            letterSite = letterSite2 - 1;
            int site = letterSite2;
            if (site >= this.k.length || site < 0) {
                break;
            }
            int section = a(site);
            if (section == -1) {
                letterSite2 = letterSite;
            } else {
                position = this.F.getPositionForSection(section);
                if (position != -1) {
                    this.i = Math.max(site, 0);
                }
                letterSite2 = letterSite;
            }
        }
        letterSite = letterSite2;
        return position;
    }

    private int c(int letterSite) {
        int position = -1;
        while (position == -1) {
            letterSite++;
            int site = letterSite;
            if (site >= this.k.length || site < 0) {
                break;
            }
            int section = a(site);
            if (section != -1) {
                position = this.F.getPositionForSection(section);
            }
        }
        if (this.i < 0 && letterSite < this.k.length) {
            this.i = letterSite;
        }
        if (position == -1) {
            return this.E.getCount();
        }
        return position;
    }

    private void a(boolean isShow, View view) {
        b(isShow, this.g);
        if (!this.d) {
            b(isShow, view);
        }
    }

    private void b(final boolean isShow, final View view) {
        float startAlpha;
        float endAlpha = 1.0f;
        if (view.getAnimation() == null) {
            if (!isShow || view.getVisibility() != 0) {
                if (!isShow && view.getVisibility() == 4) {
                    return;
                }
            }
            return;
        }
        if (isShow) {
            startAlpha = 0.0f;
        } else {
            startAlpha = 1.0f;
        }
        if (!isShow) {
            endAlpha = 0.0f;
        }
        view.clearAnimation();
        AlphaAnimation alphaAnim = new AlphaAnimation(startAlpha, endAlpha);
        alphaAnim.setAnimationListener(new AnimationListener(this) {
            final /* synthetic */ FastScrollLetter c;

            public void onAnimationEnd(Animation animation) {
                view.setVisibility(isShow ? 0 : 4);
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });
        alphaAnim.setDuration(180);
        alphaAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        view.setAnimation(alphaAnim);
        alphaAnim.startNow();
    }

    @TargetApi(17)
    private void setOverlayTextLayout(float offsetTop) {
        this.J = (int) offsetTop;
        int left = this.E.getWidth() - (this.t + this.r);
        int top = (int) (((float) this.u) + offsetTop);
        int right = this.E.getWidth() - this.t;
        int bottom = top + this.s;
        this.g.setTranslationY(0.0f);
        if (this.E.getLayoutDirection() == 0) {
            this.g.layout(left, top, right, bottom);
        } else {
            this.g.layout(this.t, top, this.t + this.r, bottom);
        }
    }

    private void b() {
        MotionEvent cancelFling = MotionEvent.obtain(0, 0, 3, 0.0f, 0.0f, 0);
        this.E.onTouchEvent(cancelFling);
        cancelFling.recycle();
    }

    private void a(MotionEvent event, int site) {
        this.g.setTranslationY((float) (((int) ((((float) this.u) + event.getY()) - ((float) this.J))) + (this.s / 2)));
        a(site, event.getY());
    }

    public int getOverlayTextWidth() {
        return this.r;
    }

    public int getOverlayTextMarginRight() {
        return this.t;
    }

    public int getLetterTextColor() {
        return this.w;
    }

    public int getLetterTextSize() {
        return this.v;
    }

    public int getLetterMarginTop() {
        return this.y;
    }

    public int getLetterMarginBottom() {
        return this.z;
    }

    public int getLetterMarginRight() {
        return this.A;
    }

    public int getLetterWidth() {
        return this.B;
    }

    public String[] getOverlayTextLetters() {
        return this.k;
    }

    public String[] getLetters() {
        return this.m;
    }

    public int getHeaderHeight() {
        return this.M;
    }

    public int getPaddingLeft() {
        return this.C;
    }

    public int getHideNum() {
        return this.H;
    }

    public String getHideStr() {
        return this.G;
    }
}
