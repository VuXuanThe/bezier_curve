package com.org.ktdh_vuxuanthe.ware.draw_bezier;

import android.opengl.GLES20;

public abstract class BaseGLShape {
    protected int mProgramHandler;

    /**
     * 必须在OpenGL的子线程中调用,会出现无法加载shader问题。
     */
    public void createShape() {
        createProgram();
        loadShaderInProgram();
        useProgram();
        createBuffer();
        findHandlersInGLSL();
        fillHandlersValue();
    }

    private void createProgram() {
        mProgramHandler = GLES20.glCreateProgram();
    }

    protected void useProgram() {
        GLES20.glUseProgram(mProgramHandler);
    }

    protected void loadShaderInProgram() {
        String vertexShaderCode = getVertexShaderCode();
        String fragmentShaderCode = getFragmentShaderCode();
        if (vertexShaderCode == null || fragmentShaderCode == null) {
            throw new RuntimeException("getVertexShaderCode and getFragmentShaderCode can't return null");
        }
        GLHelper.loadShaders(mProgramHandler, vertexShaderCode, fragmentShaderCode);
    }

    protected abstract String getVertexShaderCode();

    protected abstract String getFragmentShaderCode();

    protected abstract void findHandlersInGLSL();

    protected abstract void createBuffer();

    protected abstract void fillHandlersValue();

    /**
     * onDrawFrame 时调用
     */
    public void draw(float[] mvpMatrix) {
        useProgram();
        enableAttrArrays();
        drawGL(mvpMatrix);
        disableAttrArrays();
    }

    protected abstract void enableAttrArrays();

    protected abstract void disableAttrArrays();

    protected abstract void drawGL(float[] MVPMatrix);

}
