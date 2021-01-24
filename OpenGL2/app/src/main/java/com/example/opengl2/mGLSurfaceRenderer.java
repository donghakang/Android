package com.example.opengl2;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;


import com.example.opengl2.entity.cube;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static java.nio.ByteBuffer.*;

public class mGLSurfaceRenderer implements GLSurfaceView.Renderer {

    Context ctx;


    private float[] mModelMatrix = new float[16];
    private float[] mViewMatrix = new float[16];
    private float[] mProjectionMatrix = new float[16];
    private float[] mMVPMatrix = new float[16];
    private float[] mLightModeMatrix = new float[16];

    ArrayList<Float> VerticesData;
    private final FloatBuffer VerticesBuffer;
    ArrayList<Float> ColorData;
    private final FloatBuffer ColorBuffer;
//    private final FloatBuffer NormalBuffer;

    private int mMVPMatrixHandle;
    private int mMVMatrixHandle;
    private int mPositionHandle;
    private int mColorHandle;
    private int mNormalHandle;

    private final int BYTES_PER_FLOAT = 4;


    // offset
    private final int mPositionOffset = 0;
    private final int mColorOffset = 3;

    // data size
    private final int mPositionDataSize = 3;
    private final int mColorDataSize = 4;
    private final int mNormalDataSize = 3;

    private final float[] mLightPosInModelSpace = new float[] {0.0f, 0.0f, 0.0f, 1.0f};
    private final float[] mLightPosInWorldSpace = new float[4];
    private final float[] mLightPosInEyeSpace = new float[4];
    private int mPerVertexProgramHandle;
    private int mPointProgramHandle;



    private final int mStrideBytes = (mPositionDataSize + mColorDataSize) * BYTES_PER_FLOAT;



    public mGLSurfaceRenderer(Context ctx)
    {
        this.ctx = ctx;
        // Define points for equilateral triangles.


        VerticesData = new ArrayList<>();
        ColorData = new ArrayList<>();
        readfile("merona.obj");

        // initialize
        VerticesBuffer = allocateDirect(VerticesData.size() * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();

        ColorBuffer = allocateDirect(ColorData.size() * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();

        for (float vertex : VerticesData) VerticesBuffer.put(vertex);
        VerticesBuffer.position(0);
        for (float color : ColorData) ColorBuffer.put(color);
        ColorBuffer.position(0);
    }

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config)
    {
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);

        // set up camera
        initCamera();

        String vertexShaderCode = "";
        String fragmentShaderCode = "";
        try {
            InputStream vertexShaderStream = ctx.getResources().openRawResource(R.raw.vertex_shader);
            vertexShaderCode = IOUtils.toString(vertexShaderStream, Charset.defaultCharset());
            vertexShaderStream.close();

            // Convert fragment_shader.txt to a string
            InputStream fragmentShaderStream = ctx.getResources().openRawResource(R.raw.fragment_shader);
            fragmentShaderCode = IOUtils.toString(fragmentShaderStream, Charset.defaultCharset());

        } catch (IOException e) {

        }


        int vertexShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        int fragmentShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES20.glShaderSource(vertexShader, vertexShaderCode);
        GLES20.glShaderSource(fragmentShader, fragmentShaderCode);

        GLES20.glCompileShader(vertexShader);
        GLES20.glCompileShader(fragmentShader);

        // Create a program object and store the handle to it.
        int programHandle = GLES20.glCreateProgram();

        if (programHandle != 0)
        {
            GLES20.glAttachShader(programHandle, vertexShader);
            GLES20.glAttachShader(programHandle, fragmentShader);

            GLES20.glBindAttribLocation(programHandle, 0, "a_Position");
            GLES20.glBindAttribLocation(programHandle, 1, "a_Color");

            GLES20.glLinkProgram(programHandle);

            final int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);

            if (linkStatus[0] == 0)
            {
                GLES20.glDeleteProgram(programHandle);
                programHandle = 0;
            }
        }

        if (programHandle == 0)
        {
            throw new RuntimeException("Error creating program.");
        }

        mMVPMatrixHandle = GLES20.glGetUniformLocation(programHandle, "u_MVPMatrix");
        mPositionHandle = GLES20.glGetAttribLocation(programHandle, "a_Position");
        mColorHandle = GLES20.glGetAttribLocation(programHandle, "a_Color");

        GLES20.glUseProgram(programHandle);
    }



    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height)
    {
        // Set the OpenGL viewport to the same size as the surface.
        GLES20.glViewport(0, 0, width, height);

        // Create a new perspective projection matrix. The height will stay the same
        // while the width will vary as per aspect ratio.
        final float ratio = (float) width / height;
        final float left = -ratio;
        final float right = ratio;
        final float bottom = -1.0f;
        final float top = 1.0f;
        final float near = 1.0f;
        final float far = 10.0f;

        Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);
    }




    @Override
    public void onDrawFrame(GL10 glUnused)
    {
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

        // Do a complete rotation every 10 seconds.
        long time = SystemClock.uptimeMillis() % 10000L;
        float angleInDegrees = (360.0f / 10000.0f) * ((int) time);

        // Draw the triangle facing straight on.
        Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.rotateM(mModelMatrix, 0, angleInDegrees, 0.0f, 5.0f, 1.0f);
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mMVPMatrix, 0);

        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);


        draw();
    }




    private void draw()
    {
        // Pass in the position information
        VerticesBuffer.position(0);
        GLES20.glVertexAttribPointer(mPositionHandle,
                mPositionDataSize,
                GLES20.GL_FLOAT,
                false,
                0,
                VerticesBuffer);

        // Pass in the color information
        ColorBuffer.position(0);
        GLES20.glVertexAttribPointer(mColorHandle,
                mColorDataSize,
                GLES20.GL_FLOAT,
                false,
                0,
                ColorBuffer);

        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glEnableVertexAttribArray(mColorHandle);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, VerticesData.size() / 3);
    }

    private void initCamera() {
        // Position the eye behind the origin.
        final float eyeX = 0.0f;
        final float eyeY = 3.0f;
        final float eyeZ = -4.0f;

        // We are looking toward the distance
        final float lookX = 0.0f;
        final float lookY = 0.0f;
        final float lookZ = 0.0f;

        // Set our up vector. This is where our head would be pointing were we holding the camera.
        final float upX = 0.0f;
        final float upY = 1.0f;
        final float upZ = 0.0f;

        Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);
    }



    // read file
    private void readfile(String file) {

        List<Float[]> objPositions = new ArrayList<>();
        List<Float[]> objTextures = new ArrayList<>();
        List<Float[]> objNormals = new ArrayList<>();
        List<String> objIndices = new ArrayList<>();

        // 파일을 읽습니다.
        Scanner scanner = null;
        try {
            scanner = new Scanner(ctx.getAssets().open(file));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");

                switch (parts[0]) {
                    case "v":
                        // vertices
                        Float[] v = new Float[3];
                        v[0] = Float.valueOf(parts[1]);
                        v[1] = Float.valueOf(parts[2]);
                        v[2] = Float.valueOf(parts[3]);
                        objPositions.add(v);      // v 1.250000 0.000000 0.000000
                        break;
                    case "vt":
                        // textures
                        Float[] t = new Float[2];
                        t[0] = Float.valueOf(parts[1]);
                        t[1] = Float.valueOf(parts[2]);
                        objTextures.add(t);
                        break;
                    case "vn":
                        Float[] n = new Float[3];
                        n[0] = Float.valueOf(parts[1]);
                        n[1] = Float.valueOf(parts[2]);
                        n[2] = Float.valueOf(parts[3]);
                        objNormals.add(n);      // v 1.250000 0.000000 0.000000
                        break;
                    case "f":
                        // faces: vertex/texture/normal
                        if (parts.length < 5) {
                            objIndices.add(parts[1]);
                            objIndices.add(parts[2]);
                            objIndices.add(parts[3]);
                        } else {
                            objIndices.add(parts[1]);                  // f 80/87/80 92/100/80 93/101/80
                            objIndices.add(parts[2]);                  //   80/87/80 93/101/80 81/88/80
                            objIndices.add(parts[3]);

                            objIndices.add(parts[1]);
                            objIndices.add(parts[3]);
                            objIndices.add(parts[4]);
                        }
                        break;
                }
            }
            scanner.close();
        } catch (IOException e) {
            // IOException
        }


        for (String f : objIndices) {
            String[] parts = f.split("/");
            short position = Short.parseShort(parts[0]);
            short texture = Short.parseShort(parts[1]);
            short normals = Short.parseShort(parts[2]);

            VerticesData.add(objPositions.get((short) (position - 1))[0]);
            VerticesData.add(objPositions.get((short) (position - 1))[1]);
            VerticesData.add(objPositions.get((short) (position - 1))[2]);
//            objVertexData.add(objTextures.get((short)(texture-1))[0]);
//            objVertexData.add(objTextures.get((short)(texture-1))[1]);
//            objVertexData.add(objNormals.get((short)(normals-1))[0]);
//            objVertexData.add(objNormals.get((short)(normals-1))[1]);
//            objVertexData.add(objNormals.get((short)(normals-1))[2]);
            ColorData.add(0.5f);
            ColorData.add(1.0f);
            ColorData.add(0.7f);
            ColorData.add(1.0f);

        }
    }
}