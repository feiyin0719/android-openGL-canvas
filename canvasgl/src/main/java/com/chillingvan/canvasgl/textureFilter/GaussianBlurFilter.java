package com.chillingvan.canvasgl.textureFilter;

import android.opengl.GLES20;

import com.chillingvan.canvasgl.ICanvasGL;
import com.chillingvan.canvasgl.OpenGLUtil;
import com.chillingvan.canvasgl.glcanvas.BasicTexture;

import java.util.ArrayList;

public class GaussianBlurFilter extends FilterGroup {
    public static class GaussianFilter extends BasicTextureFilter {
        private float blurSize;
        private int dir;
        public static final String texelWidthOffset = "texelWidthOffset";
        public static final String texelHeightOffset = "texelHeightOffset";


        public GaussianFilter(int dir, float blurSize) {
            this.dir = dir;
            this.blurSize = blurSize;


        }

        public GaussianFilter(int dir) {
            this(dir, 1f);
        }

        public static final String VERTEX_SHADER =
                "precision mediump float;\n" +
                        "uniform mat4 " + MATRIX_UNIFORM + ";\n" +
                        "uniform mat4 " + TEXTURE_MATRIX_UNIFORM + ";\n" +
                        "attribute vec2 " + POSITION_ATTRIBUTE + ";\n" +
                        // "attribute vec2 " + GLES20Canvas.TEXTURE_COORD_ATTRIBUTE + ";\n" +
                        "const int GAUSSIAN_SAMPLES = 9;\n" +
                        "\n" +
                        "uniform float " + texelWidthOffset + ";\n" +
                        "uniform float " + texelHeightOffset + ";\n" +
                        "\n" +
                        "varying vec2 vTextureCoord;\n" +
                        "varying vec2 blurCoordinates[GAUSSIAN_SAMPLES];\n" +
                        "\n" +
                        "void main()\n" +
                        "{\n" +
                        "  vec4 pos = vec4(" + POSITION_ATTRIBUTE + ", 0.0, 1.0);\n" +
                        "	gl_Position = " + MATRIX_UNIFORM + "* pos;\n" +
                        "	vTextureCoord = (" + TEXTURE_MATRIX_UNIFORM + " * pos).xy;\n" +
                        "	\n" +
                        "	int multiplier = 0;\n" +
                        "	vec2 blurStep;\n" +
                        "   vec2 singleStepOffset = vec2(texelHeightOffset, texelWidthOffset);\n" +
                        "    \n" +
                        "	for (int i = 0; i < GAUSSIAN_SAMPLES; i++)\n" +
                        "   {\n" +
                        "		multiplier = (i - ((GAUSSIAN_SAMPLES - 1) / 2));\n" +
                        "       // Blur in x (horizontal)\n" +
                        "       blurStep = float(multiplier) * singleStepOffset;\n" +
                        "		blurCoordinates[i] = vTextureCoord.xy + blurStep;\n" +
                        "	}\n" +
                        "}\n";

        public static final String FRAGMENT_SHADER =
                "uniform sampler2D " + TEXTURE_SAMPLER_UNIFORM + ";\n" +
                        "\n" +
                        "const lowp int GAUSSIAN_SAMPLES = 9;\n" +
                        "\n" +
                        "varying highp vec2 vTextureCoord;\n" +
                        "varying highp vec2 blurCoordinates[GAUSSIAN_SAMPLES];\n" +
                        "\n" +
                        "void main()\n" +
                        "{\n" +
                        "	lowp vec3 sum = vec3(0.0);\n" +
                        "   lowp vec4 fragColor=texture2D(uTextureSampler,vTextureCoord);\n" +
                        "	\n" +
                        "    sum += texture2D(uTextureSampler, blurCoordinates[0]).rgb * 0.05;\n" +
                        "    sum += texture2D(uTextureSampler, blurCoordinates[1]).rgb * 0.09;\n" +
                        "    sum += texture2D(uTextureSampler, blurCoordinates[2]).rgb * 0.12;\n" +
                        "    sum += texture2D(uTextureSampler, blurCoordinates[3]).rgb * 0.15;\n" +
                        "    sum += texture2D(uTextureSampler, blurCoordinates[4]).rgb * 0.18;\n" +
                        "    sum += texture2D(uTextureSampler, blurCoordinates[5]).rgb * 0.15;\n" +
                        "    sum += texture2D(uTextureSampler, blurCoordinates[6]).rgb * 0.12;\n" +
                        "    sum += texture2D(uTextureSampler, blurCoordinates[7]).rgb * 0.09;\n" +
                        "    sum += texture2D(uTextureSampler, blurCoordinates[8]).rgb * 0.05;\n" +
                        "\n" +
                        "	gl_FragColor = vec4(sum,fragColor.a);\n" +
                        "}";


        @Override
        public String getVertexShader() {
            return VERTEX_SHADER;
        }

        @Override
        public String getFragmentShader() {
            return FRAGMENT_SHADER;
        }


        @Override
        public void onPreDraw(int program, BasicTexture texture, ICanvasGL canvas) {
            super.onPreDraw(program, texture, canvas);
            int widthOffsetLocation = GLES20.glGetUniformLocation(program, texelWidthOffset);
            int heightOffsetLocation = GLES20.glGetUniformLocation(program, texelHeightOffset);
            if (dir == 0) {
                OpenGLUtil.setFloat(widthOffsetLocation, blurSize / texture.getWidth());
                OpenGLUtil.setFloat(heightOffsetLocation, 0);

            } else {
                OpenGLUtil.setFloat(widthOffsetLocation, 0);
                OpenGLUtil.setFloat(heightOffsetLocation, blurSize / texture.getHeight());
            }


        }

        public void setBlurSize(float blurSize) {
            this.blurSize = blurSize;
        }


    }


    public GaussianBlurFilter(float blurSize) {
        super(new ArrayList<TextureFilter>());
        mFilters.add(new GaussianFilter(0, blurSize));
        mFilters.add(new GaussianFilter(1, blurSize));
        updateMergedFilters();
    }

    public void setBlurSize(float blurSize) {
        for (TextureFilter textureFilter : mFilters) {
            if (textureFilter instanceof GaussianFilter) {
                GaussianFilter gaussianFilter = (GaussianFilter) textureFilter;
                gaussianFilter.setBlurSize(blurSize);
            }

        }
    }
}
