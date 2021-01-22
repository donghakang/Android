package com.example.opengl;

import androidx.appcompat.app.AppCompatActivity;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity implements GLSurfaceView.Renderer {

    GLSurfaceView mySurfaceView;
    Torus torus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mySurfaceView = (GLSurfaceView)findViewById(R.id.my_surface_view);
        mySurfaceView.setEGLContextClientVersion(2);
        mySurfaceView.setRenderer(this);

//        ObjLoader o = new ObjLoader(this, "torus.obj");
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mySurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        try {
            torus = new Torus(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        torus.draw();
    }
}