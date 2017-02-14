package com.meizu.common.renderer.effect;

import android.opengl.GLES20;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ShaderUtil {
    public static int loadShader(int i, String str) {
        int glCreateShader = GLES20.glCreateShader(i);
        if (glCreateShader != 0) {
            GLES20.glShaderSource(glCreateShader, str);
            GLES20.glCompileShader(glCreateShader);
            int[] iArr = new int[1];
            GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
            if (iArr[0] == 0) {
                Log.e(GLRenderManager.TAG, "Could not compile shader " + i + ":" + str);
                Log.e(GLRenderManager.TAG, "Info :" + GLES20.glGetShaderInfoLog(glCreateShader));
                Log.e(GLRenderManager.TAG, str);
                GLES20.glDeleteShader(glCreateShader);
                return 0;
            }
        }
        return glCreateShader;
    }

    public static int createProgram(String str, String str2) {
        int loadShader = loadShader(35633, str);
        if (loadShader == 0) {
            return 0;
        }
        int loadShader2 = loadShader(35632, str2);
        if (loadShader2 == 0) {
            return 0;
        }
        int glCreateProgram = GLES20.glCreateProgram();
        if (glCreateProgram != 0) {
            GLES20.glAttachShader(glCreateProgram, loadShader);
            checkGlError("glAttachShader");
            GLES20.glAttachShader(glCreateProgram, loadShader2);
            checkGlError("glAttachShader");
            GLES20.glLinkProgram(glCreateProgram);
            GLES20.glDeleteShader(loadShader);
            GLES20.glDeleteShader(loadShader2);
            checkGlError("glDeleteShader");
            int[] iArr = new int[1];
            GLES20.glGetProgramiv(glCreateProgram, 35714, iArr, 0);
            if (iArr[0] != 1) {
                Log.e(GLRenderManager.TAG, "Could not link program: ");
                Log.e(GLRenderManager.TAG, GLES20.glGetProgramInfoLog(glCreateProgram));
                GLES20.glDeleteProgram(glCreateProgram);
                return 0;
            }
        }
        return glCreateProgram;
    }

    public static int createComputeProgram(String str) {
        int loadShader = loadShader(GLES31Utils.GL_COMPUTE_SHADER, str);
        if (loadShader == 0) {
            return 0;
        }
        int glCreateProgram = GLES20.glCreateProgram();
        if (glCreateProgram != 0) {
            GLES20.glAttachShader(glCreateProgram, loadShader);
            checkGlError("glAttachShader");
            GLES20.glLinkProgram(glCreateProgram);
            GLES20.glDeleteShader(loadShader);
            checkGlError("glDeleteShader");
            int[] iArr = new int[1];
            GLES20.glGetProgramiv(glCreateProgram, 35714, iArr, 0);
            if (iArr[0] != 1) {
                Log.e(GLRenderManager.TAG, "Could not link program: ");
                Log.e(GLRenderManager.TAG, GLES20.glGetProgramInfoLog(glCreateProgram));
                GLES20.glDeleteProgram(glCreateProgram);
                return 0;
            }
        }
        return glCreateProgram;
    }

    public static void checkGlError(String str) {
        int glGetError = GLES20.glGetError();
        if (glGetError != 0) {
            Log.e(GLRenderManager.TAG, str + ": glError " + glGetError);
            throw new RuntimeException(str + ": glError " + glGetError);
        }
    }

    public static String loadFromAssetsFile(String str) {
        String replaceAll;
        Exception exception;
        try {
            InputStream open = GLRenderManager.getInstance().getAppContext().getAssets().open(str);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                int read = open.read();
                if (read == -1) {
                    break;
                }
                byteArrayOutputStream.write(read);
            }
            byte[] toByteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            open.close();
            String str2 = new String(toByteArray, "UTF-8");
            try {
                replaceAll = str2.replaceAll("\\r\\n", "\n");
            } catch (Exception e) {
                exception = e;
                replaceAll = str2;
                exception.printStackTrace();
                return replaceAll;
            }
        } catch (Exception e2) {
            Exception exception2 = e2;
            replaceAll = null;
            exception = exception2;
            exception.printStackTrace();
            return replaceAll;
        }
        return replaceAll;
    }
}
