package io.flutter.plugins.webviewflutter;

import android.content.Context;
import android.webkit.WebView;

class ScrollListenerWebView extends WebView {
    public ScrollListenerWebView(Context context) {
        super(context);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollListener != null) {
            onScrollListener.onScrollOffsetChange(t);
        }
    }

    private ScrollListener onScrollListener;

    public void setOnScrollListener(ScrollListener listener) {
        this.onScrollListener = listener;
    }

    public interface ScrollListener {
        void onScrollOffsetChange(double offset);
    }
}
