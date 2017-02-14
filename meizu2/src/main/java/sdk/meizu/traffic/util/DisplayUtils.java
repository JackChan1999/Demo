package sdk.meizu.traffic.util;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import com.meizu.common.renderer.effect.parameters.FastBlurParameters;

public class DisplayUtils {
    public static int getScreenHeight(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        return point.y;
    }

    public static int getScreenWidth(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        return point.x;
    }

    public static int getContentViewHeight(Context context) {
        return getScreenHeight(context);
    }

    public static int dipToPixel(Context context, float f) {
        return (int) (((double) (context.getResources().getDisplayMetrics().density * f)) + 0.5d);
    }

    public static int pixelToDip(Context context, float f) {
        return (int) ((f / context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
