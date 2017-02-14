package com.meizu.common.renderer.effect;

import android.util.Log;
import com.meizu.common.widget.MzContactsContract.MzGroups;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LoadUtil {
    public static float[][] loadFromFileVertexOnly(String str) {
        int i = 0;
        float[][] fArr = new float[2][];
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        try {
            int i2;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(GLRenderManager.getInstance().getAppContext().getResources().getAssets().open(str)));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                String[] split = readLine.split("[ ]+");
                if (split[0].trim().equals("v")) {
                    arrayList.add(Float.valueOf(Float.parseFloat(split[1])));
                    arrayList.add(Float.valueOf(Float.parseFloat(split[2])));
                    arrayList.add(Float.valueOf(Float.parseFloat(split[3])));
                } else if (split[0].trim().equals("vt")) {
                    arrayList4.add(Float.valueOf(Float.parseFloat(split[1])));
                    arrayList4.add(Float.valueOf(Float.parseFloat(split[2])));
                } else if (split[0].trim().equals("f")) {
                    int[] iArr = new int[3];
                    iArr[0] = Integer.parseInt(split[1].split(MzGroups.GROUP_SPLIT_MARK_SLASH)[0]) - 1;
                    float floatValue = ((Float) arrayList.get(iArr[0] * 3)).floatValue();
                    float floatValue2 = ((Float) arrayList.get((iArr[0] * 3) + 1)).floatValue();
                    float floatValue3 = ((Float) arrayList.get((iArr[0] * 3) + 2)).floatValue();
                    arrayList3.add(Float.valueOf(floatValue));
                    arrayList3.add(Float.valueOf(floatValue2));
                    arrayList3.add(Float.valueOf(floatValue3));
                    iArr[1] = Integer.parseInt(split[2].split(MzGroups.GROUP_SPLIT_MARK_SLASH)[0]) - 1;
                    floatValue = ((Float) arrayList.get(iArr[1] * 3)).floatValue();
                    floatValue2 = ((Float) arrayList.get((iArr[1] * 3) + 1)).floatValue();
                    floatValue3 = ((Float) arrayList.get((iArr[1] * 3) + 2)).floatValue();
                    arrayList3.add(Float.valueOf(floatValue));
                    arrayList3.add(Float.valueOf(floatValue2));
                    arrayList3.add(Float.valueOf(floatValue3));
                    iArr[2] = Integer.parseInt(split[3].split(MzGroups.GROUP_SPLIT_MARK_SLASH)[0]) - 1;
                    floatValue = ((Float) arrayList.get(iArr[2] * 3)).floatValue();
                    floatValue2 = ((Float) arrayList.get((iArr[2] * 3) + 1)).floatValue();
                    floatValue3 = ((Float) arrayList.get((iArr[2] * 3) + 2)).floatValue();
                    arrayList3.add(Float.valueOf(floatValue));
                    arrayList3.add(Float.valueOf(floatValue2));
                    arrayList3.add(Float.valueOf(floatValue3));
                    arrayList2.add(Integer.valueOf(iArr[0]));
                    arrayList2.add(Integer.valueOf(iArr[1]));
                    arrayList2.add(Integer.valueOf(iArr[2]));
                    int parseInt = Integer.parseInt(split[1].split(MzGroups.GROUP_SPLIT_MARK_SLASH)[1]) - 1;
                    arrayList5.add(arrayList4.get(parseInt * 2));
                    arrayList5.add(arrayList4.get((parseInt * 2) + 1));
                    parseInt = Integer.parseInt(split[2].split(MzGroups.GROUP_SPLIT_MARK_SLASH)[1]) - 1;
                    arrayList5.add(arrayList4.get(parseInt * 2));
                    arrayList5.add(arrayList4.get((parseInt * 2) + 1));
                    parseInt = Integer.parseInt(split[3].split(MzGroups.GROUP_SPLIT_MARK_SLASH)[1]) - 1;
                    arrayList5.add(arrayList4.get(parseInt * 2));
                    arrayList5.add(arrayList4.get((parseInt * 2) + 1));
                }
            }
            int size = arrayList3.size();
            float[] fArr2 = new float[size];
            for (i2 = 0; i2 < size; i2++) {
                fArr2[i2] = ((Float) arrayList3.get(i2)).floatValue();
            }
            i2 = arrayList5.size();
            float[] fArr3 = new float[i2];
            while (i < i2) {
                if (i % 2 == 1) {
                    fArr3[i] = 1.0f - ((Float) arrayList5.get(i)).floatValue();
                } else {
                    fArr3[i] = ((Float) arrayList5.get(i)).floatValue();
                }
                i++;
            }
            fArr[0] = fArr2;
            fArr[1] = fArr3;
        } catch (Exception e) {
            Log.e("load error", "load error");
            e.printStackTrace();
        }
        return fArr;
    }
}
