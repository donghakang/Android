package com.example.opengl2;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;



public class MainActivity extends Activity
{
    /** Hold a reference to our GLSurfaceView */
    private GLSurfaceView mGLSurfaceView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mGLSurfaceView = findViewById(R.id.mgl);

        mGLSurfaceView = new GLSurfaceView(this);



        // Request an OpenGL ES 2.0 compatible context.
        mGLSurfaceView.setEGLContextClientVersion(2);

        // Set the renderer to our demo renderer, defined below.
        mGLSurfaceView.setRenderer(new mGLSurfaceRenderer(this));


        setContentView(mGLSurfaceView);
    }


}