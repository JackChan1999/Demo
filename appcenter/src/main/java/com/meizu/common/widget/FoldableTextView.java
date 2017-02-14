package com.meizu.common.widget;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.DynamicLayout;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.Touch;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import com.meizu.common.a.h;
import com.meizu.common.a.i;
import com.meizu.common.a.j;

public class FoldableTextView extends TextView implements OnClickListener {
    private CharSequence a;
    private CharSequence b;
    private int c;
    private a d;
    private boolean e;
    private boolean f;
    private boolean g;
    private int h;
    private CharSequence i;
    private CharSequence j;
    private int k;
    private int l;
    private Layout m;
    private int n;
    private boolean o;
    private Float p;
    private boolean q;
    private boolean r;
    private boolean s;

    public interface a {
        boolean a(FoldableTextView foldableTextView, boolean z);
    }

    public static class b extends LinkMovementMethod {
        static b a;

        public static b a() {
            if (a == null) {
                a = new b();
            }
            return a;
        }

        public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
            int action = event.getAction();
            if (action != 1 && action != 0) {
                return Touch.onTouchEvent(widget, buffer, event);
            }
            int x = (((int) event.getX()) - widget.getTotalPaddingLeft()) + widget.getScrollX();
            int y = (((int) event.getY()) - widget.getTotalPaddingTop()) + widget.getScrollY();
            Layout layout = widget.getLayout();
            int off = layout.getOffsetForHorizontal(layout.getLineForVertical(y), (float) x);
            ClickableSpan[] link = (ClickableSpan[]) buffer.getSpans(off, off, ClickableSpan.class);
            if (link.length != 0) {
                if (action == 1) {
                    link[0].onClick(widget);
                } else if (action == 0) {
                    Selection.setSelection(buffer, buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]));
                }
                if (!(widget instanceof FoldableTextView)) {
                    return true;
                }
                ((FoldableTextView) widget).r = true;
                return true;
            }
            Selection.removeSelection(buffer);
            Touch.onTouchEvent(widget, buffer, event);
            return false;
        }
    }

    private class c extends ClickableSpan {
        final /* synthetic */ FoldableTextView a;
        private final CharSequence b;

        public c(FoldableTextView foldableTextView, CharSequence text) {
            this.a = foldableTextView;
            this.b = text;
        }

        public void updateDrawState(TextPaint ds) {
            if (this.a.h == 0) {
                ds.setColor(ds.linkColor);
            } else {
                ds.setColor(this.a.h);
            }
        }

        public void onClick(View widget) {
            if (!this.a.o) {
                if (this.a.d == null || this.a.d.a(this.a, false)) {
                    this.a.e = false;
                    this.a.setText(this.a.i, BufferType.NORMAL);
                    this.a.b();
                }
            }
        }
    }

    private class d {
        final /* synthetic */ FoldableTextView a;

        private d(FoldableTextView foldableTextView) {
            this.a = foldableTextView;
        }
    }

    public FoldableTextView(Context context) {
        this(context, null);
    }

    public FoldableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, i.Widget_MeizuCommon_FoldableTextView);
    }

    public FoldableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.e = true;
        this.f = false;
        this.g = true;
        this.h = 0;
        this.k = 0;
        this.l = 0;
        this.m = null;
        this.n = 250;
        this.o = false;
        this.p = Float.valueOf(1.0f);
        this.q = true;
        this.s = true;
        TypedArray a = context.obtainStyledAttributes(attrs, j.FoldableTextView, defStyle, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == j.FoldableTextView_mzTextEllipse) {
                this.a = a.getText(attr);
            } else if (attr == j.FoldableTextView_mzTextUnfold) {
                this.b = a.getText(attr);
            } else if (attr == j.FoldableTextView_mzMaxFoldLine) {
                this.c = a.getInt(attr, 0);
            } else if (attr == j.FoldableTextView_mzUnfoldAlignViewEdge) {
                this.f = a.getBoolean(attr, false);
            } else if (attr == j.FoldableTextView_mzClickToFold) {
                this.g = a.getBoolean(attr, false);
            } else if (attr == j.FoldableTextView_mzNonSpanClickable) {
                this.s = a.getBoolean(attr, true);
            } else if (attr == j.FoldableTextView_mzLinkColor) {
                this.h = a.getColor(attr, 0);
                if (this.h == 0) {
                    this.h = com.meizu.common.util.c.c(context) == null ? 0 : com.meizu.common.util.c.c(context).intValue();
                }
            } else if (attr == j.FoldableTextView_mzIsFold) {
                this.e = a.getBoolean(attr, true);
            }
        }
        a.recycle();
        if (TextUtils.isEmpty(this.b)) {
            this.b = context.getString(h.more_item_label);
        }
        if (TextUtils.isEmpty(this.a)) {
            this.a = "‥";
        }
        setMovementMethod(b.a());
        setOnClickListener(true);
    }

    public void setFoldText(String strEllipse, String strUnfold, boolean alignViewEdge) {
        this.f = alignViewEdge;
        if (strEllipse != null) {
            this.a = strEllipse;
        }
        if (strUnfold != null) {
            this.b = strUnfold;
        }
    }

    public CharSequence getStrEllipse() {
        return this.a;
    }

    public CharSequence getMoreText() {
        return this.b;
    }

    public void setFolding(int lineMax, a l) {
        this.c = lineMax;
        this.d = l;
        setText(this.i, BufferType.NORMAL);
    }

    public int getFoldLineMax() {
        return this.c;
    }

    public void setClickToFold(boolean enabled) {
        if (enabled) {
            this.g = true;
        } else {
            this.g = false;
        }
    }

    public void setLinkColor(int color) {
        this.h = color;
        invalidate();
    }

    public int getLinkColor() {
        return this.h;
    }

    public boolean getFoldStatus() {
        return this.e;
    }

    public void setFoldStatus(boolean fold) {
        if (this.e != fold) {
            this.e = fold;
            requestLayout();
            invalidate();
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(FoldableTextView.class.getName());
    }

    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        if (!(this.i == null || this.j == null || text == null || text.toString().equals(this.i.toString()) || text.toString().equals(this.j.toString()))) {
            this.i = text;
            b(text);
            if (this.e) {
                setHeight(this.l);
            } else {
                setHeight(this.k);
            }
        }
        requestLayout();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.i == null) {
            this.i = getText();
        }
        b(this.i);
        if (!this.o) {
            this.j = a(this.i);
            if (!this.e || this.c <= 0) {
                setText(this.i, BufferType.SPANNABLE);
                setMeasuredDimension(widthMeasureSpec, this.k);
                return;
            }
            setText(this.j, BufferType.SPANNABLE);
            setMeasuredDimension(widthMeasureSpec, this.l);
        } else if (this.e) {
            setMeasuredDimension(widthMeasureSpec, (int) (((float) this.k) - (((float) (this.k - this.l)) * this.p.floatValue())));
        } else {
            setMeasuredDimension(widthMeasureSpec, (int) (((float) this.l) + (((float) (this.k - this.l)) * this.p.floatValue())));
        }
    }

    private CharSequence a(CharSequence text) {
        this.m = getLayout();
        if (this.m == null) {
            return text;
        }
        SpannableStringBuilder sb = new SpannableStringBuilder(text);
        DynamicLayout tmpLayout = new DynamicLayout(sb, this.m.getPaint(), this.m.getWidth(), this.m.getAlignment(), 1.0f, 0.0f, false);
        if (tmpLayout.getLineCount() <= this.c || this.c == 0) {
            return text;
        }
        int lineMax = this.c;
        while (lineMax > 1) {
            int line = lineMax - 1;
            if (tmpLayout.getLineStart(line) < tmpLayout.getLineVisibleEnd(line)) {
                break;
            }
            lineMax = line;
        }
        int en = tmpLayout.getLineVisibleEnd(lineMax - 1);
        if (TextUtils.isEmpty(this.a)) {
            sb.delete(en, sb.length());
        } else {
            sb.replace(en, sb.length(), this.a);
        }
        sb.append(' ');
        int addIndex = sb.length();
        sb.append(this.b);
        sb.setSpan(new c(this, text), addIndex, sb.length(), 33);
        if (en > 0 && tmpLayout.getLineCount() > lineMax) {
            int delIndex = en;
            do {
                delIndex--;
                sb.delete(delIndex, delIndex + 1);
                if (delIndex <= 0) {
                    break;
                }
            } while (tmpLayout.getLineCount() > lineMax);
        } else if (this.f) {
            while (tmpLayout.getLineCount() == lineMax) {
                sb.replace(addIndex, addIndex, " ");
                if (tmpLayout.getLineCount() > lineMax) {
                    sb.delete(addIndex, addIndex + 1);
                    break;
                }
                addIndex++;
            }
        }
        return sb;
    }

    private void setOnClickListener(boolean set) {
        if (set) {
            setOnClickListener(this);
        } else {
            setOnClickListener(null);
        }
    }

    public void onClick(View v) {
        a();
    }

    public void a() {
        if (this.o || !this.g) {
            return;
        }
        if (this.e) {
            if (this.d == null || this.d.a(this, false)) {
                this.e = false;
                setText(this.i, BufferType.NORMAL);
                if (this.l != 0) {
                    b();
                }
            }
        } else if (this.d == null || this.d.a(this, true)) {
            this.e = true;
            if (this.k != 0 && getLayout() != null && getLayout().getLineCount() > this.c) {
                b();
            }
        }
    }

    private void b() {
        if (this.c != 0) {
            this.o = true;
            d mValueHolder = new d();
            String str = "height";
            int[] iArr = new int[2];
            iArr[0] = this.e ? this.k : this.l;
            iArr[1] = this.e ? this.l : this.k;
            ObjectAnimator objectAnimator = ObjectAnimator.ofInt(mValueHolder, str, iArr);
            objectAnimator.setDuration((long) this.n);
            objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            objectAnimator.addListener(new AnimatorListener(this) {
                final /* synthetic */ FoldableTextView a;

                {
                    this.a = r1;
                }

                public void onAnimationStart(Animator animator) {
                }

                public void onAnimationEnd(Animator animator) {
                    boolean z = false;
                    if (this.a.e) {
                        this.a.setText(this.a.j, BufferType.SPANNABLE);
                        this.a.setHeight(this.a.l);
                    } else {
                        this.a.setHeight(this.a.k);
                    }
                    this.a.o = false;
                    FoldableTextView foldableTextView = this.a;
                    if (!this.a.q) {
                        z = true;
                    }
                    foldableTextView.q = z;
                }

                public void onAnimationCancel(Animator animator) {
                    this.a.o = false;
                }

                public void onAnimationRepeat(Animator animator) {
                }
            });
            objectAnimator.addUpdateListener(new AnimatorUpdateListener(this) {
                final /* synthetic */ FoldableTextView a;

                {
                    this.a = r1;
                }

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.a.p = Float.valueOf(valueAnimator.getAnimatedFraction());
                    this.a.requestLayout();
                }
            });
            objectAnimator.start();
        }
    }

    private void b(CharSequence text) {
        this.m = getLayout();
        if (this.m != null) {
            this.k = (int) (((double) (((this.m.getLineBottom(this.m.getLineCount() - 1) - this.m.getLineTop(0)) + getPaddingBottom()) + getPaddingTop())) + 0.5d);
            if (this.m.getLineCount() <= this.c) {
                this.l = this.k;
            } else {
                this.l = (int) ((((double) (((this.m.getLineBottom(this.c - 1) - this.m.getLineTop(0)) + getPaddingBottom()) + getPaddingTop())) + 0.5d) + ((double) getLineSpacingExtra()));
            }
        }
    }

    public void setFoldDuration(int duration) {
        this.n = duration;
    }

    public boolean onTouchEvent(MotionEvent event) {
        this.r = false;
        boolean res = super.onTouchEvent(event);
        if (!this.q || this.s) {
            return res;
        }
        return this.r;
    }

    public void setNonSpanClickable(boolean clickable) {
        this.s = clickable;
    }

    public boolean hasFocusable() {
        return false;
    }
}
