package wearable.android.zaim.net.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.scribe.model.Token;

import progress.menu.item.ProgressMenuItemHelper;
import wearable.android.zaim.net.R;
import wearable.android.zaim.net.common.utils.PreferenceUtils;
import wearable.android.zaim.net.common.utils.ToastUtils;
import wearable.android.zaim.net.services.SendLoginStatusService;
import wearable.android.zaim.net.tasks.GetAccessTokenTask;
import wearable.android.zaim.net.tasks.GetAccessTokenTaskCallback;
import wearable.android.zaim.net.tasks.GetRequestTokenTask;
import wearable.android.zaim.net.tasks.GetRequestTokenTaskCallback;
import wearable.android.zaim.net.utils.NetWorkUtils;
import wearable.android.zaim.net.utils.ZaimUtils;

public class LoginActivity extends Activity implements
        GetRequestTokenTaskCallback, GetAccessTokenTaskCallback {

    private WebView mWebView;

    private ProgressMenuItemHelper mProgressHelper;

    private Token mRequestToken;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWebView = new WebView(getApplicationContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                GetAccessTokenTask accessTokenTask = new GetAccessTokenTask(getApplicationContext(), mRequestToken, LoginActivity.this);
                accessTokenTask.execute(message);
                return true;
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                mProgressHelper.stopProgress();
                // dirty huck...
                view.loadUrl(getString(R.string.get_verifier));
            }
        });
        setContentView(mWebView);

        if (NetWorkUtils.isNotConnect(this)) {
            ToastUtils.show(this, R.string.message_disconnect);
            return;
        }
        GetRequestTokenTask mRequestTokenTask = new GetRequestTokenTask(getApplicationContext(), this);
        mRequestTokenTask.execute();
    }

    @Override
    protected void onSaveInstanceState(@SuppressWarnings("NullableProblems") Bundle outState) {
        new WebView(getApplicationContext()).saveState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        mProgressHelper = new ProgressMenuItemHelper(menu, R.id.action_refresh);
        mProgressHelper.startProgress();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mProgressHelper.startProgress();
        mWebView.reload();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccessGetRequestToken(Token requestToken) {
        mRequestToken = requestToken;
        String url = ZaimUtils.getOauthService(this).getAuthorizationUrl(mRequestToken);
        mWebView.loadUrl(url);
    }

    @Override
    public void onSuccessGetAccessToken(Token accessToken) {
        PreferenceUtils.saveAccessToken(this, accessToken.getToken(), accessToken.getSecret());
        startService(SendLoginStatusService.createIntent(this, true));
        startActivity(MainActivity.createIntent(this));
        finish();
    }

}