package com.camangi.flashtestspark;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsoluteLayout;

@SuppressWarnings("deprecation")
public class FixAdobeWebView extends WebView {

    View whiteView;
    private boolean eatenFirstFlashDraw;

    public FixAdobeWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        whiteView = new WhiteSurfaceView(context);
        whiteView.setLayoutParams(new AbsoluteLayout.LayoutParams(1280, 720, 0, 0));
        
        addView(whiteView);
    }


    private class WhiteSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
        public WhiteSurfaceView(Context context) {
            super(context);
            getHolder().addCallback(this);
        }
        //@Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Canvas canvas = holder.lockCanvas();
            if (canvas != null) {
                canvas.drawColor(Color.WHITE);
                holder.unlockCanvasAndPost(canvas);
            }
        }
        //@Override
        public void surfaceCreated(SurfaceHolder holder) { }
        //@Override
        public void surfaceDestroyed(SurfaceHolder holder) { }
    }


    //
    // Override drawChild to eat the first draw of the FlashPaintSurface
    //
    @Override 
    protected boolean drawChild (Canvas canvas, View child, long drawingTime) {
             if (!eatenFirstFlashDraw && child.getClass().getName().equals("com.adobe.flashplayer.FlashPaintSurface")) {
                 eatenFirstFlashDraw = true;
                 return true;
             }
        return super.drawChild(canvas, child, drawingTime);
    }
    private boolean is_gone=false;
    public void onWindowVisibilityChanged(int visibility)
           {super.onWindowVisibilityChanged(visibility);
            if (visibility==View.GONE)
               {try
                    {WebView.class.getMethod("onPause").invoke(this);//stop flash
                    }
                catch (Exception e) {}
                this.pauseTimers();
                this.is_gone=true;
               }
            else if (visibility==View.VISIBLE)
                 {try
                      {WebView.class.getMethod("onResume").invoke(this);//resume flash
                      }
                  catch (Exception e) {}
                  this.resumeTimers();
                  this.is_gone=false;
                 }
           }
    public void onDetachedFromWindow()
           {//this will be trigger when back key pressed, not when home key pressed
            if (this.is_gone)
               {try
                   {this.destroy();
                   }
                catch (Exception e) {}
               }
           }
	public void setWebViewClient(WebViewClient webViewClient) {
		// TODO Auto-generated method stub
		super.setWebViewClient(webViewClient);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
		if ((keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) || (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) 
    			|| (keyCode == KeyEvent.KEYCODE_ENTER)) { 
    		return true;
    	}
    	
    	return super.onKeyDown(keyCode, event); 
    }
    
}
