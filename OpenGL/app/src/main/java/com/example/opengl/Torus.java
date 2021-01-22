package com.example.opengl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

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

public class Torus {
    private List<String> verticesList;
    private List<String> facesList;


    private FloatBuffer verticesBuffer;
    private ShortBuffer facesBuffer;

    private int program;

    public Torus(Context context) throws IOException {
        verticesList = new ArrayList<>();
        facesList = new ArrayList<>();

        // Open the OBJ file with a Scanner
        Scanner scanner = null;
        scanner = new Scanner(context.getAssets().open("torus.obj"));


        // Loop through all its lines
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if(line.startsWith("v ")) {
                // Add vertex line to list of vertices
                verticesList.add(line);
            } else if(line.startsWith("f ")) {
                // Add face line to faces list
                Log.d("dddd", "Line: " + line);
                facesList.add(line);
            }
        }

        Log.d("ddddd", verticesList.size() + "   " + facesList.size());

        // Close the scanner
        scanner.close();






        // Create buffer for vertices
        ByteBuffer buffer1 = ByteBuffer.allocateDirect(verticesList.size() * 3 * 4);
        buffer1.order(ByteOrder.nativeOrder());
        verticesBuffer = buffer1.asFloatBuffer();

        // Create buffer for faces
        ByteBuffer buffer2 = ByteBuffer.allocateDirect(facesList.size() * 3 * 2);
        buffer2.order(ByteOrder.nativeOrder());
        facesBuffer = buffer2.asShortBuffer();

        int c_v = 0;
        int c_f = 0;

        for(String vertex: verticesList) {
            String coords[] = vertex.split("\\s+"); // Split by space

            float x = Float.parseFloat(coords[1]);
            float y = Float.parseFloat(coords[2]);
            float z = Float.parseFloat(coords[3]);
            verticesBuffer.put(x);
            verticesBuffer.put(y);
            verticesBuffer.put(z);

            c_v += 1;
        }
        verticesBuffer.position(0);

        for(String face: facesList) {
            String vertexIndices[] = face.split(" |/");
            Log.d("ddddd", "FACE: " + face);
            Log.d("ddddd", "VERTEX: " + vertexIndices[1] + ", " + vertexIndices[2]);
            short vertex1 = Short.parseShort(vertexIndices[1]);
            short vertex2 = Short.parseShort(vertexIndices[2]);
            short vertex3 = Short.parseShort(vertexIndices[3]);
            Log.d("ddddd", "VERTEXX:" + vertex1 + ", " + vertex2 + ", " + vertex3);
            facesBuffer.put((short)(vertex1 - 1));
            facesBuffer.put((short)(vertex2 - 1));
            facesBuffer.put((short)(vertex3 - 1));

            c_f += 1;
        }
        facesBuffer.position(0);



        Log.d ("dddd", "DEFAULT : " + c_v + "     " + c_f);




        // Convert vertex_shader.txt to a string
        String vertexShaderCode;
        String fragmentShaderCode;
        InputStream vertexShaderStream = context.getResources().openRawResource(R.raw.vertex_shader);
        vertexShaderCode = IOUtils.toString(vertexShaderStream, Charset.defaultCharset());
        vertexShaderStream.close();

        // Convert fragment_shader.txt to a string
        InputStream fragmentShaderStream = context.getResources().openRawResource(R.raw.fragment_shader);
        fragmentShaderCode = IOUtils.toString(fragmentShaderStream, Charset.defaultCharset());


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
                facesList.size() * 3, GLES20.GL_UNSIGNED_SHORT, facesBuffer);

        GLES20.glDisableVertexAttribArray(position);
    }
}
