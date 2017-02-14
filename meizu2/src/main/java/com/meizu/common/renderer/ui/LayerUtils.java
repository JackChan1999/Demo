package com.meizu.common.renderer.ui;

import com.meizu.common.widget.MzContactsContract.MzSearchSnippetColumns;

public class LayerUtils {
    public static int windowTypeToLayerLw(int i) {
        if (i >= 1 && i <= 99) {
            return 2;
        }
        switch (i) {
            case 2000:
                return 15;
            case 2001:
                return 4;
            case 2002:
                return 3;
            case 2003:
                return 10;
            case 2005:
                return 7;
            case 2007:
                return 8;
            case 2008:
                return 6;
            case 2011:
                return 11;
            case 2012:
                return 12;
            case 2013:
            case 2030:
                return 2;
            case 2017:
                return 14;
            case 2023:
                return 9;
            case 2025:
                return 1;
            case 2029:
                return 13;
            case 2031:
                return 5;
            default:
                return -1;
        }
    }

    public static int getLayer(int i) {
        return (windowTypeToLayerLw(i) * MzSearchSnippetColumns.SEARCH_WEIGHT_NAME) + 1000;
    }
}
