package com.example.opengl2.entity;

import android.content.Context;
import android.opengl.GLES20;

import com.example.opengl2.R;

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

public class cube {

    Context ctx;
    /**
     *
     * Store the model matrix. This matrix is used to move models from object space (where each model can be thought
     * of being located at the center of the universe) to world space.
     */
    private float[] mModelMatrix = new float[16];

    /**
     * Store the view matrix. This can be thought of as our camera. This matrix transforms world space to eye space;
     * it positions things relative to our eye.
     */
    private float[] mViewMatrix = new float[16];

    /** Store the projection matrix. This is used to project the scene onto a 2D viewport. */
    private float[] mProjectionMatrix = new float[16];

    /** Allocate storage for the final combined matrix. This will be passed into the shader program. */
    private float[] mMVPMatrix = new float[16];

    /** Store our model data in a float buffer. */
    private final FloatBuffer mTriangle1Vertices;
//        private final FloatBuffer mTriangle2Vertices;
//        private final FloatBuffer mTriangle3Vertices;

    /** This will be used to pass in the transformation matrix. */
    private int mMVPMatrixHandle;

    /** This will be used to pass in model position information. */
    private int mPositionHandle;

    /** This will be used to pass in model color information. */
    private int mColorHandle;

    /** How many bytes per float. */
    private final int mBytesPerFloat = 4;

    /** How many elements per vertex. */
    private final int mStrideBytes = 7 * mBytesPerFloat;

    /** Offset of the position data. */
    private final int mPositionOffset = 0;

    /** Size of the position data in elements. */
    private final int mPositionDataSize = 3;

    /** Offset of the color data. */
    private final int mColorOffset = 3;

    /** Size of the color data in elements. */
    private final int mColorDataSize = 4;


    List<Float> triangle1VerticesData;
    /**
     * Initialize the model data.
     */
    public cube(Context ctx) {
        this.ctx = ctx;

//
        triangle1VerticesData = new ArrayList<>();


        readfile("cube.obj");


        // Initialize the buffers.
        mTriangle1Vertices = ByteBuffer.allocateDirect(triangle1VerticesData.size() * mBytesPerFloat)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();

        for (Float count : triangle1VerticesData) {
            mTriangle1Vertices.put(count);
        }

        mTriangle1Vertices.position(0);
//            mTriangle1Vertices.put(triangle1VerticesData).position(0);
//            mTriangle2Vertices.put(triangle2VerticesData).position(0);
//            mTriangle3Vertices.put(triangle3VerticesData).position(0);




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
        GLES20.glShaderSource(vertexShader, vertexShaderCode);

        int fragmentShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES20.glShaderSource(fragmentShader, fragmentShaderCode);


        GLES20.glCompileShader(vertexShader);
        GLES20.glCompileShader(fragmentShader);


        // Create a program object and store the handle to it.
        int programHandle = GLES20.glCreateProgram();

        if (programHandle != 0)
        {
            // Bind the vertex shader to the program.
            GLES20.glAttachShader(programHandle, vertexShader);

            // Bind the fragment shader to the program.
            GLES20.glAttachShader(programHandle, fragmentShader);

            // Bind attributes
            GLES20.glBindAttribLocation(programHandle, 0, "a_Position");
            GLES20.glBindAttribLocation(programHandle, 1, "a_Color");

            // Link the two shaders together into a program.
            GLES20.glLinkProgram(programHandle);

            // Get the link status.
            final int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);

            // If the link failed, delete the program.
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




        // Tell OpenGL to use this program when rendering.
        GLES20.glUseProgram(programHandle);
    }


    public void draw (float[] mvpMatrix) {


        // Pass in the position information
        mTriangle1Vertices.position(mPositionOffset);
        GLES20.glVertexAttribPointer(mPositionHandle, mPositionDataSize, GLES20.GL_FLOAT, false,
                mStrideBytes, mTriangle1Vertices);

        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Pass in the color information
        mTriangle1Vertices.position(mColorOffset);
        GLES20.glVertexAttribPointer(mColorHandle, mColorDataSize, GLES20.GL_FLOAT, false,
                mStrideBytes, mTriangle1Vertices);

        GLES20.glEnableVertexAttribArray(mColorHandle);

        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);


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

            triangle1VerticesData.add(objPositions.get((short) (position - 1))[0]);
            triangle1VerticesData.add(objPositions.get((short) (position - 1))[1]);
            triangle1VerticesData.add(objPositions.get((short) (position - 1))[2]);
//            objVertexData.add(objTextures.get((short)(texture-1))[0]);
//            objVertexData.add(objTextures.get((short)(texture-1))[1]);
//            objVertexData.add(objNormals.get((short)(normals-1))[0]);
//            objVertexData.add(objNormals.get((short)(normals-1))[1]);
//            objVertexData.add(objNormals.get((short)(normals-1))[2]);
            triangle1VerticesData.add(1.0f);
            triangle1VerticesData.add(1.0f);
            triangle1VerticesData.add(0.0f);
            triangle1VerticesData.add(1.0f);

        }
    }
}
