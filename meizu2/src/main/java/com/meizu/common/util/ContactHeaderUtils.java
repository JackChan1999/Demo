package com.meizu.common.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import com.meizu.common.R;
import com.meizu.common.widget.CircleProgressBar;

public class ContactHeaderUtils {
    private static final Config BITMAP_CONFIG = Config.ARGB_8888;
    private static final int COLORDRAWABLE_DIMENSION = 2;
    private static final int DEFAULT_BG_COLOR = -11227562;
    private static final int DEFAULT_BORDER_COLOR = -1;
    private static final int[] colorArray = new int[]{-1937599, -2271161, -9540664, -11437103, -14107502, DEFAULT_BG_COLOR, -1464773};

    public static Bitmap createContactHeaderDrawable(Resources resources, int i, int i2, Object obj, Object obj2, int i3) {
        Object[] objArr = new Object[1];
        Object[] objArr2 = new Object[]{obj};
        objArr2[0] = obj2;
        return createContactHeaderDrawable(resources, i, i2, objArr, objArr2, i3);
    }

    public static Bitmap createContactHeaderDrawable(Resources resources, int i, int i2, Object[] objArr, Object[] objArr2, int i3) {
        int i4;
        int i5;
        float f;
        int i6 = 1;
        if (objArr != null) {
            i6 = objArr.length;
        }
        if (i6 > 3) {
            i4 = 3;
        } else {
            i4 = i6;
        }
        float f2 = resources.getDisplayMetrics().density;
        int i7 = (int) (((float) i) * f2);
        int i8 = (int) (((float) i2) * f2);
        i6 = ((int) f2) + 1;
        if (i4 == 1) {
            i5 = 0;
        } else {
            i5 = i6;
        }
        int i9 = DEFAULT_BG_COLOR;
        RectF rectF = new RectF();
        RectF rectF2 = new RectF();
        Paint paint = new Paint();
        Paint paint2 = new Paint();
        Paint paint3 = new Paint();
        Paint paint4 = new Paint();
        Paint paint5 = new Paint();
        if (i3 == -1) {
            i3 = -1;
        }
        f2 = 0.0f;
        String str = "";
        Bitmap createBitmap = Bitmap.createBitmap(i7, i8, Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        paint4.setStyle(Style.FILL_AND_STROKE);
        paint4.setAntiAlias(true);
        paint4.setColor(DEFAULT_BG_COLOR);
        paint5.setAntiAlias(true);
        paint5.setTextAlign(Align.CENTER);
        paint5.setColor(-1);
        paint2.setStyle(Style.STROKE);
        paint2.setAntiAlias(true);
        paint2.setColor(i3);
        paint2.setStrokeWidth((float) i5);
        paint3.setStyle(Style.STROKE);
        paint3.setAntiAlias(true);
        paint3.setColor(419430400);
        paint3.setStrokeWidth(1.0f);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        if (i4 == 3) {
            rectF2.set(0.0f, 0.0f, (float) ((i7 * 33) / 46), (float) ((i8 * 33) / 46));
        }
        if (i4 == 2) {
            rectF2.set(0.0f, 0.0f, (float) ((i7 * 38) / 46), (float) ((i8 * 38) / 46));
        } else {
            rectF2.set(0.0f, 0.0f, (float) (((i4 + 1) * i7) / (i4 * 2)), (float) (((i4 + 1) * i8) / (i4 * 2)));
        }
        float min = Math.min(((rectF2.height() - ((float) i5)) - 1.0f) / CircleProgressBar.BAR_WIDTH_DEF_DIP, ((rectF2.width() - ((float) i5)) - 1.0f) / CircleProgressBar.BAR_WIDTH_DEF_DIP);
        rectF.set(rectF2);
        rectF.inset((float) i5, (float) i5);
        float min2 = Math.min(rectF.height() / CircleProgressBar.BAR_WIDTH_DEF_DIP, rectF.width() / CircleProgressBar.BAR_WIDTH_DEF_DIP);
        if (i4 == 3) {
            f2 = (float) (((i7 * 13) / 92) + i5);
        }
        if (i4 == 2) {
            f2 = (float) (((i7 * 8) / 92) + (i5 / 2));
        }
        if (i4 == 1) {
            f = 0.0f;
        } else {
            f = f2;
        }
        Matrix matrix = null;
        Shader shader = null;
        Bitmap bitmap = null;
        int i10 = i4 - 1;
        while (i10 >= 0) {
            int i11;
            String str2;
            Object obj = null;
            if (objArr == null || objArr[i10] == null || (objArr[i10] instanceof String)) {
                CharSequence charSequence;
                if (objArr == null || objArr[i10] == null) {
                    Object obj2 = str;
                    i11 = i9;
                } else {
                    str = checkText((String) objArr[i10]);
                    charSequence = str;
                    i11 = getColorByText((String) objArr[i10]);
                }
                if (objArr == null || objArr[i10] == null || TextUtils.isEmpty(charSequence)) {
                    obj = 1;
                    bitmap = getBitmapFromDrawable(resources.getDrawable(R.drawable.mc_contact_list_picture));
                    str2 = charSequence;
                    i6 = i11;
                } else {
                    CharSequence charSequence2 = charSequence;
                    i6 = i11;
                }
            } else if (objArr[i10] instanceof Bitmap) {
                bitmap = (Bitmap) objArr[i10];
                i6 = i9;
                str2 = str;
            } else if (objArr[i10] instanceof Drawable) {
                bitmap = getBitmapFromDrawable((Drawable) objArr[i10]);
                i6 = i9;
                str2 = str;
            } else {
                i6 = i9;
                str2 = str;
            }
            if (objArr2 == null || objArr2[i10] == null) {
                i11 = i6;
            } else {
                i11 = getColorByText((String) objArr2[i10]);
            }
            if (bitmap != null) {
                shader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
                if (((float) bitmap.getWidth()) * rectF.height() > rectF.width() * ((float) bitmap.getHeight())) {
                    f2 = rectF.height() / ((float) bitmap.getHeight());
                } else {
                    f2 = rectF.width() / ((float) bitmap.getWidth());
                }
                matrix = new Matrix();
                matrix.setScale(f2, f2);
                shader.setLocalMatrix(matrix);
            }
            if (i10 == 0) {
                paint4.setColor(i11);
                if (!TextUtils.isEmpty(str2)) {
                    canvas.drawCircle(((float) (i7 / 2)) - f, (float) (i8 / 2), min2, paint4);
                    paint5.setTextSize((float) (((double) min2) * 0.9d));
                    FontMetrics fontMetrics = paint5.getFontMetrics();
                    canvas = canvas;
                    canvas.drawText(str2, ((float) (i7 / 2)) - f, ((((fontMetrics.bottom - fontMetrics.top) / CircleProgressBar.BAR_WIDTH_DEF_DIP) - fontMetrics.bottom) + ((float) (i8 / 2))) - CircleProgressBar.BAR_WIDTH_DEF_DIP, paint5);
                } else if (bitmap != null) {
                    matrix.postTranslate(((((float) i7) - rectF.width()) / CircleProgressBar.BAR_WIDTH_DEF_DIP) - f, (((float) i8) - rectF.height()) / CircleProgressBar.BAR_WIDTH_DEF_DIP);
                    shader.setLocalMatrix(matrix);
                    paint.setShader(shader);
                    if (obj != null) {
                        canvas.drawCircle(((float) (i7 / 2)) - f, (float) (i8 / 2), min2, paint4);
                    }
                    canvas.drawCircle(((float) (i7 / 2)) - f, (float) (i8 / 2), min2, paint);
                }
                if (i5 != 0) {
                    canvas.drawCircle(((float) (i7 / 2)) - f, (float) (i8 / 2), min, paint2);
                }
                canvas.drawCircle(((float) (i7 / 2)) - f, (float) (i8 / 2), min2, paint3);
            } else if (i10 == i4 - 1) {
                if (bitmap == null) {
                    paint4.setColor(i11);
                    canvas.drawCircle(((float) (i7 / 2)) + f, (float) (i8 / 2), min2, paint4);
                } else if (obj != null) {
                    paint4.setColor(i11);
                    canvas.drawCircle(((float) (i7 / 2)) + f, (float) (i8 / 2), min2, paint4);
                } else {
                    matrix.postTranslate(((((float) i7) - rectF.width()) / CircleProgressBar.BAR_WIDTH_DEF_DIP) + f, (((float) i8) - rectF.height()) / CircleProgressBar.BAR_WIDTH_DEF_DIP);
                    shader.setLocalMatrix(matrix);
                    paint.setShader(shader);
                    canvas.drawCircle(((float) (i7 / 2)) + f, (float) (i8 / 2), min2, paint);
                }
                if (i5 != 0) {
                    canvas.drawCircle(((float) (i7 / 2)) + f, (float) (i8 / 2), min, paint2);
                }
                canvas.drawCircle(((float) (i7 / 2)) + f, (float) (i8 / 2), min2, paint3);
            } else {
                if (bitmap == null) {
                    paint4.setColor(i11);
                    canvas.drawCircle((float) (i7 / 2), (float) (i8 / 2), min2, paint4);
                } else if (obj != null) {
                    paint4.setColor(i11);
                    canvas.drawCircle((float) (i7 / 2), (float) (i8 / 2), min2, paint4);
                } else {
                    matrix.postTranslate((((float) i7) - rectF.width()) / CircleProgressBar.BAR_WIDTH_DEF_DIP, (((float) i8) - rectF.height()) / CircleProgressBar.BAR_WIDTH_DEF_DIP);
                    shader.setLocalMatrix(matrix);
                    paint.setShader(shader);
                    canvas.drawCircle((float) (i7 / 2), (float) (i8 / 2), min2, paint);
                }
                if (i5 != 0) {
                    canvas.drawCircle((float) (i7 / 2), (float) (i8 / 2), min, paint2);
                }
                canvas.drawCircle((float) (i7 / 2), (float) (i8 / 2), min2, paint3);
            }
            i10--;
            String str3 = str2;
            i9 = i11;
            str = str3;
        }
        return createBitmap;
    }

    public static int getColorByText(String str) {
        int i = 5;
        if (!TextUtils.isEmpty(str)) {
            i = Math.abs(str.hashCode()) % colorArray.length;
        }
        return i < colorArray.length ? colorArray[i] : DEFAULT_BG_COLOR;
    }

    private static String checkText(String str) {
        String str2 = "";
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        Object trim = str.trim();
        if (TextUtils.isEmpty(trim)) {
            return "";
        }
        str2 = trim.substring(0, 1);
        char charAt = str2.charAt(0);
        if ('a' > charAt || charAt > 'z') {
            return str2;
        }
        return str2.toUpperCase();
    }

    private static Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        try {
            if (drawable instanceof ColorDrawable) {
                return Bitmap.createBitmap(2, 2, BITMAP_CONFIG);
            }
            return Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
        } catch (OutOfMemoryError e) {
            Log.e("ContactHeaderUtils ", "getBitmapFromDrawable  OutOfMemoryError !");
            return null;
        }
    }
}
