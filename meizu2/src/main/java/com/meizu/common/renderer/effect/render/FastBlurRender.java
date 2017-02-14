package com.meizu.common.renderer.effect.render;

import android.opengl.GLES20;
import com.meizu.common.renderer.Utils;
import com.meizu.common.renderer.effect.GLCanvas;
import com.meizu.common.renderer.effect.GLES31Utils;
import com.meizu.common.renderer.effect.RenderInfo;
import com.meizu.common.renderer.effect.ShaderUtil;
import com.meizu.common.renderer.effect.cache.BasicTextureCache;
import com.meizu.common.renderer.effect.element.TextureElement;
import com.meizu.common.renderer.effect.parameters.BlurParameters;
import com.meizu.common.renderer.effect.parameters.FastBlurParameters;
import com.meizu.common.renderer.effect.texture.BasicTexture;
import com.meizu.common.renderer.effect.texture.Texture;

public class FastBlurRender extends Render {
    private static final int BLUR_WORK_SIZE = 16;
    public static final String FAST_BLUR = "fast_blur";
    private static int TEXTURE_FORMAT = GLES31Utils.GL_RGBA16F;
    private static String scaleShader = ("#version 310 es \nprecision highp float; \nuniform sampler2D uInputImage; \nuniform int uRadius; \nlayout(" + getPixelFormat() + ", binding = 0) writeonly uniform highp image2D uOutputImage; \n" + "layout (local_size_x = 1, local_size_y = " + 16 + ", local_size_z = 1) in;\n" + "ivec2 offset(int value, int base) {\n" + "    return ivec2(value, base);\n" + "}\n" + "vec3 imageFetch(ivec2 pos, ivec2 imageSize) {\n" + "     return texture(uInputImage, vec2(float(pos.x)/float(imageSize.x), float(pos.y)/float(imageSize.y))).rgb;" + "}\n" + "void boxBlurH() {\n" + "    ivec2 imageSize = ivec2(imageSize(uOutputImage));\n" + "    int size =  int(imageSize.x);\n" + "    int base =  int(gl_GlobalInvocationID.y);\n" + "    int radius = min(uRadius, size);\n" + "    float weight = 1.0/float(radius*2+1);\n" + "    vec3 left  = imageFetch(offset(0, base), imageSize);\n" + "    vec3 right = imageFetch(offset(size-1, base), imageSize);\n" + "    vec3 color = left*float(radius+1);\n" + "    for(int i=0; i<radius; i++) {\n" + "        color += imageFetch(offset(i, base), imageSize);\n" + "    }\n" + "    for(int i=0; i<=radius; i++) { \n" + "        color += imageFetch(offset(i+radius, base), imageSize) - left;\n" + "        imageStore(uOutputImage, offset(i, base), vec4(color*weight, 1.0));\n" + "    }\n" + "    for(int i=radius+1; i<size-radius; i++) { \n" + "        color += imageFetch(offset(i+radius, base), imageSize) - \n" + "                 imageFetch(offset(i-radius-1, base),imageSize);\n" + "        imageStore(uOutputImage, offset(i, base), vec4(color*weight, 1.0));\n" + "    }\n" + "    for(int i=size-radius; i<size; i++) { \n" + "        color += right - imageFetch(offset(i-radius-1, base), imageSize);\n" + "        imageStore(uOutputImage, offset(i, base), vec4(color*weight, 1.0));\n" + "    }\n" + "}\n" + "void main() { \n" + "    boxBlurH();\n" + "}");
    private final BlurParameters mDrawingParameters;
    private boolean mNotSupportMemoryBarrier;
    protected int mProgramH;
    protected int mProgramS;
    protected int mProgramV;
    private int[] mSize;
    protected int mUniformRadiusH;
    protected int mUniformRadiusS;
    protected int mUniformRadiusV;
    private int mUniformTextureS;

    public FastBlurRender(GLCanvas gLCanvas) {
        super(gLCanvas);
        this.mSize = new int[3];
        this.mProgramH = 0;
        this.mProgramV = 0;
        this.mProgramS = 0;
        this.mProgramH = ShaderUtil.createComputeProgram(getBlurShader(false));
        this.mUniformRadiusH = GLES20.glGetUniformLocation(this.mProgramH, "uRadius");
        this.mProgramV = ShaderUtil.createComputeProgram(getBlurShader(true));
        this.mUniformRadiusV = GLES20.glGetUniformLocation(this.mProgramV, "uRadius");
        this.mProgramS = ShaderUtil.createComputeProgram(scaleShader);
        this.mUniformTextureS = GLES20.glGetUniformLocation(this.mProgramS, "uInputImage");
        this.mUniformRadiusS = GLES20.glGetUniformLocation(this.mProgramS, "uRadius");
        this.mDrawingParameters = new FastBlurParameters();
        this.mKey = FAST_BLUR;
        this.mNotSupportMemoryBarrier = "Adreno (TM) 430".equals(GLES20.glGetString(7937));
    }

    public void setParameters(BlurParameters blurParameters) {
        this.mDrawingParameters.copyFrom(blurParameters);
    }

    public BlurParameters getParameters() {
        return this.mDrawingParameters;
    }

    public boolean draw(RenderInfo renderInfo) {
        TextureElement textureElement = (TextureElement) renderInfo.element;
        Object blur = blur(renderInfo);
        if (blur != null) {
            textureElement.mTexture = blur;
        }
        drawBlur(renderInfo);
        BasicTextureCache.getInstance().put(blur);
        return true;
    }

    public BasicTexture blur(RenderInfo renderInfo) {
        if (skipBlur()) {
            return null;
        }
        TextureElement textureElement = (TextureElement) renderInfo.element;
        float scale = this.mDrawingParameters.getScale();
        int radius = (int) ((((float) this.mDrawingParameters.getRadius()) * this.mDrawingParameters.getLevel()) + FastBlurParameters.DEFAULT_LEVEL);
        int min = Math.min(this.mDrawingParameters.getPassCount(), 3);
        int max = Math.max((int) (((float) textureElement.mWidth) * scale), 1);
        int max2 = Math.max((int) (scale * ((float) textureElement.mHeight)), 1);
        BasicTexture basicTexture = BasicTextureCache.getInstance().get(max, max2, TEXTURE_FORMAT);
        BasicTexture basicTexture2 = BasicTextureCache.getInstance().get(max, max2, TEXTURE_FORMAT);
        basicTexture.onBind(this.mGLCanvas);
        basicTexture2.onBind(this.mGLCanvas);
        boxesForGauss((float) Math.max(radius, 0), min);
        for (int i = 0; i < min; i++) {
            if (i == 0) {
                blurH(textureElement.mTexture, basicTexture, (this.mSize[i] - 1) / 2);
            } else {
                GLES20.glUseProgram(this.mProgramH);
                GLES31Utils.glBindImageTexture(0, basicTexture2.getId(), 0, false, 0, GLES31Utils.GL_READ_ONLY, TEXTURE_FORMAT);
                GLES31Utils.glBindImageTexture(1, basicTexture.getId(), 0, false, 0, GLES31Utils.GL_WRITE_ONLY, TEXTURE_FORMAT);
                GLES20.glUniform1i(this.mUniformRadiusH, (this.mSize[i] - 1) / 2);
                GLES31Utils.glDispatchCompute(1, Utils.nextMultipleN(max2, 16) / 16, 1);
                GLES31Utils.glMemoryBarrier(32);
            }
            if (this.mNotSupportMemoryBarrier) {
                GLES20.glFinish();
            }
            GLES20.glUseProgram(this.mProgramV);
            GLES31Utils.glBindImageTexture(0, basicTexture.getId(), 0, false, 0, GLES31Utils.GL_READ_ONLY, TEXTURE_FORMAT);
            GLES31Utils.glBindImageTexture(1, basicTexture2.getId(), 0, false, 0, GLES31Utils.GL_WRITE_ONLY, TEXTURE_FORMAT);
            GLES20.glUniform1i(this.mUniformRadiusV, (this.mSize[i] - 1) / 2);
            GLES31Utils.glDispatchCompute(Utils.nextMultipleN(max, 16) / 16, 1, 1);
            GLES31Utils.glMemoryBarrier(32);
        }
        BasicTextureCache.getInstance().put(basicTexture);
        return basicTexture2;
    }

    public void drawBlur(RenderInfo renderInfo) {
        BlurFilterRender instace = BlurFilterRender.getInstace(this.mGLCanvas);
        instace.setFilterColor(this.mDrawingParameters.getFilterColor());
        instace.setIntensity(this.mDrawingParameters.getIntensity());
        instace.draw(renderInfo);
    }

    public void blurH(Texture texture, Texture texture2, int i) {
        GLES20.glUseProgram(this.mProgramS);
        GLES31Utils.glMemoryBarrier(256);
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(3553, texture.getId());
        GLES20.glUniform1i(this.mUniformTextureS, 0);
        GLES20.glUniform1i(this.mUniformRadiusS, i);
        GLES31Utils.glBindImageTexture(0, texture2.getId(), 0, false, 0, GLES31Utils.GL_WRITE_ONLY, TEXTURE_FORMAT);
        GLES31Utils.glDispatchCompute(1, Utils.nextMultipleN(texture2.getHeight(), 16) / 16, 1);
        GLES31Utils.glMemoryBarrier(32);
    }

    public void copyTexture(Texture texture, Texture texture2) {
        GLES20.glUseProgram(this.mProgramS);
        GLES31Utils.glMemoryBarrier(256);
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(3553, texture.getId());
        GLES20.glUniform1i(this.mUniformTextureS, 0);
        GLES31Utils.glBindImageTexture(0, texture2.getId(), 0, false, 0, GLES31Utils.GL_WRITE_ONLY, texture2.getFormat());
        GLES31Utils.glDispatchCompute(Utils.nextMultipleN(texture2.getWidth(), 8) / 8, Utils.nextMultipleN(texture2.getHeight(), 16) / 16, 1);
        GLES31Utils.glMemoryBarrier(32);
    }

    public boolean skipBlur() {
        return this.mDrawingParameters.getLevel() < 0.01f;
    }

    private void boxesForGauss(float f, int i) {
        if (this.mSize.length != i) {
            this.mSize = new int[i];
        }
        int floor = (int) Math.floor((double) ((float) Math.sqrt((((12.0d * ((double) f)) * ((double) f)) / ((double) i)) + 1.0d)));
        if (floor % 2 == 0) {
            floor--;
        }
        int i2 = floor + 2;
        int round = Math.round((((((12.0f * f) * f) - ((float) ((i * floor) * floor))) - ((float) ((i * 4) * floor))) - ((float) (i * 3))) / ((float) ((floor * -4) - 4)));
        int i3 = 0;
        while (i3 < i) {
            this.mSize[i3] = i3 < round ? floor : i2;
            i3++;
        }
    }

    protected void finalize() {
        try {
            freeGLResource();
        } finally {
            super.finalize();
        }
    }

    public void freeGLResource() {
        if (this.mGLCanvas != null) {
            this.mGLCanvas.deleteProgram(this.mProgramH);
            this.mProgramH = 0;
            this.mGLCanvas.deleteProgram(this.mProgramV);
            this.mProgramV = 0;
            this.mGLCanvas.deleteProgram(this.mProgramS);
            this.mGLCanvas = null;
        }
    }

    public static void glTexStorage2D(int i, int i2, int i3, int i4, int i5) {
        GLES31Utils.glTexStorage2D(i, i2, i3, i4, i5);
    }

    private static String getPixelFormat() {
        return TEXTURE_FORMAT == GLES31Utils.GL_RGBA32F ? "rgba32f" : "rgba16f";
    }

    private String getBlurShader(boolean z) {
        int i;
        int i2 = 1;
        StringBuilder append = new StringBuilder().append("#version 310 es \nprecision highp float; \nuniform int uRadius; \nlayout(").append(getPixelFormat()).append(", binding = 0) readonly uniform highp image2D uInputImage; \n").append("layout(").append(getPixelFormat()).append(", binding = 1) writeonly uniform highp image2D uOutputImage; \n").append("layout (local_size_x = ");
        if (z) {
            i = 16;
        } else {
            i = 1;
        }
        StringBuilder append2 = append.append(i).append(", local_size_y = ");
        if (!z) {
            i2 = 16;
        }
        return append2.append(i2).append(", local_size_z = 1) in;\n").append("ivec2 offset(int value, int base) {\n").append("    return ").append(z).append(" ? ivec2(base, value) : ivec2(value, base);\n").append("}\n").append("void boxBlur() {\n").append("    int size = ").append(z).append(" ? int(imageSize(uOutputImage).y) : int(imageSize(uOutputImage).x);\n").append("    int base = ").append(z).append(" ? int(gl_GlobalInvocationID.x) : int(gl_GlobalInvocationID.y);\n").append("    int radius = min(uRadius, size);\n").append("    float weight = 1.0/float(radius*2+1);\n").append("    vec3 left  = imageLoad(uInputImage, offset(0, base)).rgb;\n").append("    vec3 right = imageLoad(uInputImage, offset(size-1, base)).rgb;\n").append("    vec3 color = left*float(radius+1);\n").append("    for(int i=0; i<radius; i++) {\n").append("        color += imageLoad(uInputImage, offset(i, base)).rgb;\n").append("    }\n").append("    for(int i=0; i<=radius; i++) { \n").append("        color += imageLoad(uInputImage, offset(i+radius, base)).rgb - left;\n").append("        imageStore(uOutputImage, offset(i, base), vec4(color*weight, 1.0));\n").append("    }\n").append("    for(int i=radius+1; i<size-radius; i++) { \n").append("        color += imageLoad(uInputImage, offset(i+radius, base)).rgb - \n").append("                 imageLoad(uInputImage, offset(i-radius-1, base)).rgb;\n").append("        imageStore(uOutputImage, offset(i, base), vec4(color*weight, 1.0));\n").append("    }\n").append("    for(int i=size-radius; i<size; i++) { \n").append("        color += right - imageLoad(uInputImage, offset(i-radius-1, base)).rgb;\n").append("        imageStore(uOutputImage, offset(i, base), vec4(color*weight, 1.0));\n").append("    }\n").append("}\n").append("void main() { \n").append("    boxBlur();\n").append("}").toString();
    }
}
