package com.vivek.brighestlight;

import java.util.List;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.widget.RemoteViews;
import android.widget.Toast;

public class FlashlightWidgetReceiver extends BroadcastReceiver{
	private static boolean isLightOn = false;
    private static Camera camera;
    private Parameters param;

    @Override
    public void onReceive(Context context, Intent intent) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            
            if(isLightOn) {
            	
                    views.setImageViewResource(R.id.widget_button, R.drawable.lightof);
            } else {
                    views.setImageViewResource(R.id.widget_button, R.drawable.lighton);
            }

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(new ComponentName(context,FlashlightWidgetProvider.class),views);

            if (isLightOn) {
                    if (camera != null) {
                            camera.stopPreview();
                            camera.release();
                            camera = null;
                    }
                    isLightOn = false;
            } else {
            	camera = Camera.open();

                if(camera == null) {
                        Toast.makeText(context, "Your device does not have flash light support", Toast.LENGTH_SHORT).show();
                } else 
                {
                        param = camera.getParameters();
                        List<String> pList = camera.getParameters().getSupportedFlashModes();
                        if(pList.contains(Parameters.FLASH_MODE_TORCH)){
                       	 param.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        }else if(pList.contains(Parameters.FLASH_MODE_ON)){
                       	 param.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                        }
                        
                        param.setFocusMode(Camera.Parameters.FOCUS_MODE_INFINITY);
                        try {
                                camera.setParameters(param);
                                camera.startPreview();
                                isLightOn = true;
                        } catch (Exception e) {
                                Toast.makeText(context,"Your device does not have flash light support", Toast.LENGTH_SHORT).show();
                               
                        }
                }
            }
    }
}