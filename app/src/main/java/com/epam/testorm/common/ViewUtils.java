package com.epam.testorm.common;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by Mike on 03.09.13.
 */
public class ViewUtils {

    public static TextView setTextView(View view, int resId, CharSequence text) {
        return setTextView(view, resId, text, true);
    }

    public static TextView setTextView(View view, int resId, CharSequence text, boolean hideEmpty) {
        TextView textView = getTextView(view, resId, text, hideEmpty);
        if (textView == null) return null;
        textView.setText(text);
        return textView;
    }

    private static TextView getTextView(View view, int resId, CharSequence text, boolean hideEmpty) {
        TextView textView = (TextView) view.findViewById(resId);
        if (textView == null) return null;
        if (!TextUtils.isEmpty(text)) {
            textView.setVisibility(View.VISIBLE);
        } else {
            if (hideEmpty) {
                textView.setVisibility(View.GONE);
            }
        }
        return textView;
    }

    public static TextView setTextViewWithHtml(View view, int resId, String text) {
        return setTextViewWithHtml(view, resId, text, true);
    }

    public static TextView setTextViewWithHtml(View view, int resId, String text, boolean hideEmpty) {
        TextView textView = getTextView(view, resId, text, hideEmpty);
        if (textView == null) return null;
        textView.setAutoLinkMask(Linkify.WEB_URLS);
        textView.setText(text);
        return textView;
    }

    public static ImageView setImage(View view, int resId, int drawable) {
        View imageView = view.findViewById(resId);
        if (imageView != null && imageView instanceof ImageView) {
            ((ImageView) imageView).setImageResource(drawable);
            return (ImageView) imageView;
        }
        return null;
    }

    public static ImageView setImage(View view, int resId, String url) {
        View imageView = view.findViewById(resId);
        if (imageView != null && imageView instanceof ImageView) {
            ImageLoader.getInstance().displayImage(url, (ImageView) imageView);
            return (ImageView) imageView;
        }
        return null;
    }

    public static ImageView setImage(View view, int resId, String url, DisplayImageOptions options) {
        return setImage(view, resId, url, options, null);
    }

    public static ImageView setImage(View view, int resId, String url, ImageLoadingListener listener) {
        return setImage(view, resId, url, null, listener);
    }

    public static ImageView setImage(View view, int resId, String url, DisplayImageOptions options, ImageLoadingListener listener) {
        View imageView = view.findViewById(resId);
        if (imageView != null && imageView instanceof ImageView) {
            ImageLoader.getInstance().displayImage(url, (ImageView) imageView, options, listener);
            return (ImageView) imageView;
        }
        return null;
    }


    public static void initImage(final String baseUrl, final ImageView detailImage) {
        initImage(null, baseUrl, detailImage, null, null);
    }
    public static void initImage(final String baseUrl, final ImageView detailImage, final ImageLoadingListener listener) {
        initImage(null, baseUrl, detailImage, null, listener);
    }
    public static void initImage(final String baseUrl, final ImageView detailImage, final DisplayImageOptions options) {
        initImage(null, baseUrl, detailImage, options, null);
    }

    public static void initImage(final String originalUrl, final String baseUrl, final ImageView detailImage, final DisplayImageOptions options) {
        initImage(originalUrl, baseUrl, detailImage, options, null);
    }

    public static void initImage(final String originalUrl, final String baseUrl, final ImageView detailImage, final ImageLoadingListener listener) {
        initImage(originalUrl, baseUrl, detailImage, null, listener);
    }

    public static void initImage(final String originalUrl, final String baseUrl, final ImageView detailImage, final DisplayImageOptions options, final ImageLoadingListener listener) {
        if (detailImage == null) {
            return;
        }
        if (originalUrl == null) {
            ImageLoader.getInstance().displayImage(baseUrl, detailImage, options, listener);
            return;
        }
        ImageLoader.getInstance().displayImage(originalUrl, detailImage, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                if (listener != null) {
                    listener.onLoadingStarted(imageUri, view);
                }
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                if (listener != null) {
                    listener.onLoadingFailed(imageUri, view, failReason);
                }
                ImageLoader.getInstance().displayImage(baseUrl, (ImageView) view, options, listener);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (listener != null) {
                    listener.onLoadingComplete(imageUri, view, loadedImage);
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                if (listener != null) {
                    listener.onLoadingCancelled(imageUri, view);
                }
            }
        });
    }

    public static ImageView setImage(View convertView, int cardUserAvatar, String avatar, int ic_upload_image_stab) {
        if (StringUtils.isEmpty(avatar)) {
            return setImage(convertView, cardUserAvatar, ic_upload_image_stab);
        }
        return setImage(convertView, cardUserAvatar, avatar);
    }
}

