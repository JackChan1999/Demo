package sdk.meizu.traffic.util;

import android.content.Context;
import sdk.meizu.traffic.R;

public class ThemeUtil {
    public static int getCurrentThemeColor(Context context) {
        return context.obtainStyledAttributes(R.styleable.MZTheme).getInt(R.styleable.MZTheme_mzThemeColor, context.getResources().getColor(R.color.flyme_blue));
    }
}
