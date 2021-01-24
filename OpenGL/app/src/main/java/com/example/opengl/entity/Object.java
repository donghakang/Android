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
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class Object {

    private FloatBuffer verticesBuffer;
    private FloatBuffer texturesBuffer;
    private FloatBuffer normalsBuffer;
    private ShortBuffer facesBuffer;

    List<Float> vertices = new ArrayList<>();
    List<Float> normals = new ArrayList<>();
    List<Float> textures = new ArrayList<>();
    List<String> faces = new ArrayList<>();

    private int vPMatrixHandle;

    private int program;

    public Object (Context ctx, String file) {




        Scanner scanner = null;
        try {
            scanner = new Scanner(ctx.getAssets().open(file));

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");

                switch (parts[0]) {
                    case "v":
                        // vertices
                        vertices.add(Float.valueOf(parts[1]));      // v 1.250000 0.000000 0.000000
                        vertices.add(Float.valueOf(parts[2]));
                        vertices.add(Float.valueOf(parts[3]));
                        break;
                    case "vt":
                        // textures
                        textures.add(Float.valueOf(parts[1]));
                        textures.add(Float.valueOf(parts[2]));
                        break;
                    case "vn":
                        // normals
                        normals.add(Float.valueOf(parts[1]));
                        normals.add(Float.valueOf(parts[2]));
                        normals.add(Float.valueOf(parts[3]));
                        break;
                    case "f":
                        // faces: vertex/texture/normal
                        Log.d("dddd", "LEN: " + parts.length + "    " + parts[1] + " " + " " +  parts[2] + " " + parts[3]);
                        if (parts.length < 5) {
                            faces.add(parts[1]);
                            faces.add(parts[2]);
                            faces.add(parts[3]);
                        } else {
                            faces.add(parts[1]);                  // f 80/87/80 92/100/80 93/101/80
                            faces.add(parts[2]);                  //   80/87/80 93/101/80 81/88/80
                            faces.add(parts[3]);

                            faces.add(parts[1]);
                            faces.add(parts[3]);
                            faces.add(parts[4]);
                        }
                        break;
                }
            }
            scanner.close();
        } catch (IOException e) {
            // IOException
        }




        // Create buffer for vertices
        ByteBuffer buffer1 = ByteBuffer.allocateDirect(vertices.size() * 4);
        buffer1.order(ByteOrder.nativeOrder());
        verticesBuffer = buffer1.asFloatBuffer();

//        // Create buffer for vertices
//        ByteBuffer buffer2 = ByteBuffer.allocateDirect(textures.size() * 4);
//        buffer2.order(ByteOrder.nativeOrder());
//        texturesBuffer = buffer2.asFloatBuffer();
//
//        // Create buffer for vertices
//        ByteBuffer buffer3 = ByteBuffer.allocateDirect(normals.size() * 4);
//        buffer3.order(ByteOrder.nativeOrder());
//        normalsBuffer = buffer3.asFloatBuffer();


        // Create buffer for faces
        ByteBuffer buffer4 = ByteBuffer.allocateDirect(faces.size() * 2);
        buffer4.order(ByteOrder.nativeOrder());
        facesBuffer = buffer4.asShortBuffer();


        // verticesBuffer.
        for (float v : vertices) {
            verticesBuffer.put(v);
        }
//
//        for (float t : textures) {
//            texturesBuffer.put(t);
//        }
//
//        for (float n : normals) {
//            normalsBuffer.put(n);
//        }

        // facesBuffer.
        for (String f : faces) {
            String[] parts = f.split("/");
            short position = Short.parseShort(parts[0]);
            short texture = Short.parseShort(parts[1]);
            short normal = Short.parseShort(parts[2]);

            facesBuffer.put((short)(position-1));
        }

        verticesBuffer.position(0);
//        texturesBuffer.position(0);
//        normalsBuffer.position(0);
        facesBuffer.position(0);




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

        GLES20.glLinkProgram(program);
        GLES20.glUseProgram(program);
    }



    float colorValue[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };


    public void draw(float[] mvpMatrix) {
        // Drawing code goes here

        // 포지션 적용
        int position = GLES20.glGetAttribLocation(program, "a_Position");
        GLES20.glEnableVertexAttribArray(position);

        GLES20.glVertexAttribPointer(position,
                3,
                GLES20.GL_FLOAT,
                false,
                3 * 4,
                verticesBuffer);


        // 색깔 적용
        int color = GLES20.glGetUniformLocation(program, "v_Color");
        GLES20.glUniform4fv(color, 1, colorValue, 0);



        // 카메라 프로젝션
        // get handle to shape's transformation matrix
        vPMatrixHandle = GLES20.glGetUniformLocation(program, "u_MVPMatrix");

        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, mvpMatrix, 0);



        GLES20.glDrawElements(GLES20.GL_TRIANGLES,
                faces.size(), GLES20.GL_UNSIGNED_SHORT, facesBuffer);

        GLES20.glDisableVertexAttribArray(position);
    }

}
