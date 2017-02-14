package com.meizu.cloud.app.core;

import android.content.res.ColorStateList;
import android.text.TextUtils;
import com.meizu.cloud.app.downlad.f.l;
import com.meizu.cloud.app.widget.CirProButton;
import com.meizu.cloud.app.widget.CircularProgressButton.c;

public class v {
    private CirProButton a;
    private boolean b;
    private boolean c;
    private String d;
    private l e;

    public CirProButton a() {
        return this.a;
    }

    public void a(CirProButton view) {
        this.a = view;
    }

    public void a(boolean show) {
        this.a.a(show, this.a.a());
    }

    public void a(c state, ColorStateList backgroundColorStateList, ColorStateList strokeColorStateList) {
        if (this.a != null && !this.a.b()) {
            this.a.getButton().setStateColorSelector(state, backgroundColorStateList, strokeColorStateList);
        }
    }

    public void a(c state, ColorStateList color) {
        if (this.a != null && !this.a.b()) {
            this.a.getButton().setStateTextColor(state, color);
        }
    }

    public void a(c state) {
        if (this.a != null && !this.a.b()) {
            this.a.getButton().setState(state, this.a.a(), false);
        }
    }

    public void b(boolean indeterminateProgressMode) {
        if (this.a != null && !this.a.b()) {
            b(5);
            if (this.a.getButton().getState() != c.PROGRESS) {
                a(c.PROGRESS);
            }
            this.a.getButton().setIndeterminateProgressMode(indeterminateProgressMode);
            if (indeterminateProgressMode) {
                this.a.getButton().invalidate();
            } else {
                this.a.getButton().setShowCenterIcon(true);
            }
        }
    }

    public void a(int progress) {
        if (this.a != null && !this.a.b()) {
            if (progress > 0) {
                this.a.getButton().setText("");
            }
            b(5);
            this.a.getButton().setProgressForState(progress);
        }
    }

    public void b(int width) {
        if (this.a != null && !this.a.b()) {
            this.a.getButton().setProgressStrokeWidth(width);
        }
    }

    public void c(int color) {
        if (this.a != null && !this.a.b()) {
            this.a.getButton().setProgressIndicatorColor(color);
        }
    }

    public void c(boolean clickable) {
        this.b = clickable;
        if (this.a != null) {
            this.a.setClickable(this.b);
        }
    }

    public void d(boolean enable) {
        this.c = enable;
        if (this.a != null) {
            this.a.getTextView().setEnabled(this.c);
        }
    }

    public String b() {
        return this.d;
    }

    public void a(String label) {
        this.d = label;
        if (this.a != null && !TextUtils.isEmpty(label)) {
            if (this.a.b()) {
                this.a.getTextView().setText(label);
            } else {
                this.a.getButton().setStateText(this.a.getButton().getState(), label);
            }
        }
    }

    public l c() {
        return this.e;
    }

    public void a(l anEnum) {
        this.e = anEnum;
    }
}
