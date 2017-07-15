package com.advance.technology.lib;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.advance.technology.lib.poetry.on.photo.R;


public class AppsFragment extends Fragment {
    WebView webview;
    ProgressBar progress;
    TextView retryTextView;
    View view;
    LinearLayout noInternetLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_promotion_lib, container, false);
        noInternetLayout = (LinearLayout) view.findViewById(R.id.noInternetLayout);
        retryTextView = (TextView) view.findViewById(R.id.retryTextView);
        webview = (WebView) view.findViewById(R.id.webview);
        webview.setWebViewClient(new MyWebViewClient());
        progress = (ProgressBar) view.findViewById(R.id.progressBar);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.loadUrl("http://jawadhussain.cf/AdvanceTechnology/index.html");
        retryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webview.loadUrl("http://jawadhussain.cf/AdvanceTechnology/index.html");
              //  view.findViewById(R.id.noInternetLayout).setVisibility(View.GONE);
            }
        });
        return view;
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.equals("http://jawadhussain.cf/AdvanceTechnology/index.html")) {
                view.loadUrl(url);
            } else {
                String packageNameArray[] = url.split("=");
                String packageName = packageNameArray[1];
                if (appInstalledOrNot(packageName)) {
                    Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage(packageName);
                    startActivity(intent);
                } else {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)));
                    }
                }
            }
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            progress.setVisibility(View.GONE);
            //  Utility.Toast(getActivity(),"On Page Fin");
            if (ConnectionDetector.isConnectingToInternet(getActivity())) {
                webview.setVisibility(View.VISIBLE);
                noInternetLayout.setVisibility(View.GONE);
            }
            super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progress.setVisibility(View.VISIBLE);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            noInternetLayout.setVisibility(View.VISIBLE);
        }
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getActivity().getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}
