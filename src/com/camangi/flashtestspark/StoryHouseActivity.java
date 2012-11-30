package com.camangi.flashtestspark;

import android.app.Activity;
import android.app.Instrumentation;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.camangi.flashtestspark.FixAdobeWebView;

public class StoryHouseActivity extends Activity {
    /** Called when the activity is first created. */
	FixAdobeWebView mWebView1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mWebView1 = (FixAdobeWebView) findViewById(R.id.webview1);
        mWebView1.getSettings().setJavaScriptEnabled(true);
        mWebView1.getSettings().setPluginsEnabled(true);
        mWebView1.loadUrl("file:///android_asset/html_no_copy/index.html");
        
        mWebView1.setWebViewClient(new WebViewClient() {

        	   public void onPageFinished(WebView view, String url) {
        		   new Thread(new Runnable() {         
        	            //@Override
        	            public void run() {                 
        	                new Instrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_DPAD_LEFT);
        	            }   
        	        }).start();
        	    }
        	   public boolean shouldOverrideKeyEvent (WebView view, KeyEvent event) {
        		   int keyCode = event.getKeyCode();
        		   if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER)
        			   return true;
        		   return false;
        	   }
        	});
        
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) { 
    	if ((keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) || (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) 
    			|| (keyCode == KeyEvent.KEYCODE_ENTER)) { 
    		return true;
    	} 
    	
    	return super.onKeyDown(keyCode, event); 
    }
}