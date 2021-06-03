package com.org.ktdh_vuxuanthe.ware.gl_es;

import android.opengl.GLES20;

import com.org.ktdh_vuxuanthe.ware.util.Const;
import com.org.ktdh_vuxuanthe.ware.interfaces.Renderable;

import java.nio.FloatBuffer;

public class CubicBezier implements Renderable {
    private final int mBufferId;
    private final BezierRenderer mRenderer;
    private final float[] color;
    private float[] starEndPoints;
    private float[] controlPoints;

    public CubicBezier(BezierRenderer renderer, FloatBuffer vboBuffer,
                       float p0x, float p0y,
                       float p3x, float p3y,
                       float p1x, float p1y,
                       float p2x, float p2y,
                       float[] color) {
        this.mRenderer = renderer;
        this.starEndPoints = new float[] { p0x, p0y, p3x, p3y };
        this.controlPoints = new float[] { p1x, p1y, p2x, p2y };
        this.color = color;

        final int buffers[] = new int[1];
        GLES20.glGenBuffers(1, buffers, 0);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[0]);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vboBuffer.capacity() * Const.BYTES_PER_FLOAT,
                vboBuffer, GLES20.GL_STATIC_DRAW);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

        mBufferId = buffers[0];
        vboBuffer = null;
    }

    @Override
    public void render(boolean flipY) {
        final float mulY = flipY ? -1f : 1f;

        GLES20.glUniform4f(mRenderer.colorHandle, color[0], color[1], color[2], color[3]);

        GLES20.glUniform4f(mRenderer.bzDataHandle, starEndPoints[0], starEndPoints[1] * mulY, starEndPoints[2], starEndPoints[3] * mulY);
        GLES20.glUniform4f(mRenderer.bzCtrlDataHandle, controlPoints[0], controlPoints[1] * mulY, controlPoints[2], controlPoints[3] * mulY);

        final int stride = Const.BYTES_PER_FLOAT * Const.T_DATA_SIZE;

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mBufferId);
        GLES20.glEnableVertexAttribArray(mRenderer.tDataHandle);
        GLES20.glVertexAttribPointer(mRenderer.tDataHandle,
                Const.T_DATA_SIZE,
                GLES20.GL_FLOAT,
                false,
                stride,
                0);

        // Clear the currently bound buffer (so future OpenGL calls do not use this buffer).
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

        // Draw it
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, mRenderer.numberOfPoints * Const.POINTS_PER_TRIANGLE);
    }

    @Override
    public void release() {
        // delete the buffer from OpenGL's memory
        final int[] buffersToDelete = new int[]{mBufferId};
        GLES20.glDeleteBuffers(buffersToDelete.length, buffersToDelete, 0);
    }
}
