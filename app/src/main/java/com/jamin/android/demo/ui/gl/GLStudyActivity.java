package com.jamin.android.demo.ui.gl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jamin.android.demo.R;
import com.jamin.android.demo.ui.base.BaseActivity;
import com.jamin.framework.util.LogUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by jamin on 2017/3/13.
 * https://blog.piasy.com/2016/06/07/Open-gl-es-android-2-part-1/
 * 学习
 */

public class GLStudyActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gl_view);
        GLSurfaceView glSurfaceView = (GLSurfaceView) findViewById(R.id.gl_view);
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setRenderer(new MyRenderer());
        //RENDERMODE_WHEN_DIRTY 懒惰渲染，需要手动调用glSurfaceView.requestRender()
        //RENDERMODE_CONTINUOUSLY 不停的渲染
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    static class MyRenderer implements GLSurfaceView.Renderer {
        private static final String VERTEX_SHADER = "attribute vec4 vPosition;\n"
                + "void main() {\n"
                + "  gl_Position = vPosition;\n"
                + "}";
        private static final String FRAGMENT_SHADER = "precision mediump float;\n"
                + "void main() {\n"
                + "  gl_FragColor = vec4(0.5,0,0,1);\n"
                + "}";//着色
        private static final float[] VERTEX = {   // in counterclockwise order:
                0, 1, 0.0f, // top
                -0.5f, -1, 0.0f, // bottom left
                1f, -1, 0.0f,  // bottom right
        };

        private final FloatBuffer mVertexBuffer;

        private int mProgram;
        private int mPositionHandle;

        MyRenderer() {
            mVertexBuffer = ByteBuffer.allocateDirect(VERTEX.length * 4)
                    .order(ByteOrder.nativeOrder())
                    .asFloatBuffer()
                    .put(VERTEX);
            mVertexBuffer.position(0);
        }

        @Override
        public void onSurfaceCreated(GL10 unused, EGLConfig config) {
            //callback when surface create
            LogUtil.d("onSurfaceCreated");
        }

        @Override
        public void onSurfaceChanged(GL10 unused, int width, int height) {
            LogUtil.d("onSurfaceChanged");
            //callback when surface size change
            //创建 GLSL 程序
            mProgram = GLES20.glCreateProgram();
            //加载shader代码
            int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, VERTEX_SHADER);
            int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SHADER);
            //attach shader 代码
            GLES20.glAttachShader(mProgram, vertexShader);
            GLES20.glAttachShader(mProgram, fragmentShader);
            //链接 shader 代码
            GLES20.glLinkProgram(mProgram);
            //获取shader代码中的变量索引 ?
            mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        }

        @Override
        public void onDrawFrame(GL10 unused) {
            //LogUtil.d("onDrawFrame");
            ////callback per frame
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

            GLES20.glUseProgram(mProgram);
            //对于 attribute 类型的变量，我们需要先 enable，再赋值，绘制完毕之后再 disable
            GLES20.glEnableVertexAttribArray(mPositionHandle);
            GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false,
                    12, mVertexBuffer);
            //GLES20.glDrawArrays 或者 GLES20.glDrawElements 开始绘制。
            // 注意，执行完毕之后，GPU 就在显存中处理好帧数据了，
            // 但此时并没有更新到 surface 上，
            // 是 GLSurfaceView 会在调用 renderer.onDrawFrame 之后，
            // 调用 mEglHelper.swap()，来把显存的帧数据更新到 surface 上的。
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
            GLES20.glDisableVertexAttribArray(mPositionHandle);
        }

        static int loadShader(int type, String shaderCode) {
            int shader = GLES20.glCreateShader(type);
            GLES20.glShaderSource(shader, shaderCode);
            GLES20.glCompileShader(shader);
            return shader;
        }
    }
}
