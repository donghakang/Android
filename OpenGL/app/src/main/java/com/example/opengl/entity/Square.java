package com.example.opengl.entity;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import com.example.opengl.R;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class Square {

    Context ctx;

    private final int mBytesPerFloat = 4;

    private FloatBuffer verticesBuffer;
    private FloatBuffer colorBuffer;
    private FloatBuffer texturesBuffer;
    private FloatBuffer normalsBuffer;
    private ShortBuffer facesBuffer;

    private FloatBuffer vertexBuffer;


    List<Float> vertices = new ArrayList<>();
    List<Float> colors = new ArrayList<>();
    List<Float> normals = new ArrayList<>();
    List<Float> textures = new ArrayList<>();
    List<String> faces = new ArrayList<>();


    ArrayList<Float> triangle1VerticesData = new ArrayList<>();
    /**
     * Store our model data in a float buffer.
     */
    private final FloatBuffer mTriangle1Vertices;

    /**
     * How many bytes per float.
     */


    private int vPMatrixHandle;

    private int program;


    private int mMVPMatrixHandle;
    private int mPositionHandle;
    private int mColorHandle;


    public Square(Context ctx, String file) {
        this.ctx = ctx;


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


        triangle1VerticesData = new ArrayList<>();
        triangle1VerticesData.add(-0.5f);
        triangle1VerticesData.add(-0.25f);
        triangle1VerticesData.add(0.0f);
        triangle1VerticesData.add(1.0f);
        triangle1VerticesData.add(0.0f);
        triangle1VerticesData.add(0.0f);
        triangle1VerticesData.add(1.0f);
        triangle1VerticesData.add( 0.5f);
        triangle1VerticesData.add(-0.25f);
        triangle1VerticesData.add(0.0f);
        triangle1VerticesData.add(0.0f);
        triangle1VerticesData.add(0.0f);
        triangle1VerticesData.add(1.0f);
        triangle1VerticesData.add(1.0f);
        triangle1VerticesData.add(0.0f);
        triangle1VerticesData.add(0.559016994f);
        triangle1VerticesData.add(0.0f);
        triangle1VerticesData.add(0.0f);
        triangle1VerticesData.add(1.0f);
        triangle1VerticesData.add(0.0f);
        triangle1VerticesData.add(1.0f);

        // Initialize the buffers.
        mTriangle1Vertices = ByteBuffer.allocateDirect(triangle1VerticesData.size() * mBytesPerFloat)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();

        mTriangle1Vertices.put(0);


        // Convert vertex_shader.txt to a string
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


        program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program, vertexShader);
        GLES20.glAttachShader(program, fragmentShader);

        GLES20.glBindAttribLocation(program, 0, "a_Position");
        GLES20.glBindAttribLocation(program, 1, "a_Color");

        GLES20.glLinkProgram(program);

        // Set program handles. These will later be used to pass in values to the program.
        mMVPMatrixHandle = GLES20.glGetUniformLocation(program, "u_MVPMatrix");
        mPositionHandle = GLES20.glGetAttribLocation(program, "a_Position");
        mColorHandle = GLES20.glGetAttribLocation(program, "a_Color");

        // Tell OpenGL to use this program when rendering.
        GLES20.glUseProgram(program);
    }


// New class members
    /**
     * Allocate storage for the final combined matrix. This will be passed into the shader program.
     */
    private float[] mMVPMatrix = new float[16];

    /**
     * How many elements per vertex.
     */
    private final int mStrideBytes = 7 * mBytesPerFloat;

    /**
     * Offset of the position data.
     */
    private final int mPositionOffset = 0;

    /**
     * Size of the position data in elements.
     */
    private final int mPositionDataSize = 3;

    /**
     * Offset of the color data.
     */
    private final int mColorOffset = 3;

    /**
     * Size of the color data in elements.
     */
    private final int mColorDataSize = 4;


    public void draw(float[] mvpMatrix) {
        mTriangle1Vertices.position(mPositionOffset);
        GLES20.glVertexAttribPointer(mPositionHandle, mPositionDataSize, GLES20.GL_FLOAT, false,
                mStrideBytes, mTriangle1Vertices);

        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Pass in the color information
        mTriangle1Vertices.position(mColorOffset);
        GLES20.glVertexAttribPointer(mColorHandle, mColorDataSize, GLES20.GL_FLOAT, false,
                mStrideBytes, mTriangle1Vertices);

        GLES20.glEnableVertexAttribArray(mColorHandle);


        // 카메라 프로젝션
        // get handle to shape's transformation matrix
        vPMatrixHandle = GLES20.glGetUniformLocation(program, "u_MVPMatrix");

        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, mvpMatrix, 0);


        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
    }
}