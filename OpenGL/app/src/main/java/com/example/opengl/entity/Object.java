package com.example.opengl.entity;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

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
    private ShortBuffer facesBuffer;

    List<Float> vertices = new ArrayList<>();
    List<Float> normals = new ArrayList<>();
    List<Float> textures = new ArrayList<>();
    List<String> faces = new ArrayList<>();



    private int program;

    public Object (Context ctx, String file) {




        Scanner scanner = null;
        try {
            scanner = new Scanner(ctx.getAssets().open("torus.obj"));

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
                        if (parts.length < 4) {
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

        // Create buffer for faces
        ByteBuffer buffer2 = ByteBuffer.allocateDirect(faces.size() * 2);
        buffer2.order(ByteOrder.nativeOrder());
        facesBuffer = buffer2.asShortBuffer();


        // verticesBuffer.
        for (float v : vertices) {
            verticesBuffer.put(v);
        }

        // facesBuffer.
        for (String f : faces) {
            String[] parts = f.split("/");
            short position = Short.parseShort(parts[0]);
            // short texture = Short.parseShort(parts[1]);
            // short normal = Short.parseShort(parts[2]);

            facesBuffer.put((short)(position-1));
        }

        verticesBuffer.position(0);
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


    public void draw() {
        // Drawing code goes here
        int position = GLES20.glGetAttribLocation(program, "position");
        GLES20.glEnableVertexAttribArray(position);

        GLES20.glVertexAttribPointer(position,
                3,
                GLES20.GL_FLOAT,
                false,
                3 * 4,
                verticesBuffer);


        float[] projectionMatrix = new float[16];
        float[] viewMatrix = new float[16];
        float[] productMatrix = new float[16];

        Matrix.frustumM(projectionMatrix, 0,
                -1, 1,
                -1, 1,
                2, 9);
        Matrix.setLookAtM(viewMatrix, 0,
                0, 3, -4,
                0, 0, 0,
                0, 1, 0);
        Matrix.multiplyMM(productMatrix, 0,
                projectionMatrix, 0,
                viewMatrix, 0);

        int matrix = GLES20.glGetUniformLocation(program, "matrix");
        GLES20.glUniformMatrix4fv(matrix, 1, false, productMatrix, 0);

        GLES20.glDrawElements(GLES20.GL_TRIANGLES,
                faces.size(), GLES20.GL_UNSIGNED_SHORT, facesBuffer);

        GLES20.glDisableVertexAttribArray(position);
    }

}
