package com.epam.testorm.common;

import android.content.Context;
import android.text.Spannable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mike on 15.08.13.
 */
public abstract class AbstractListAdapter<T> extends ArrayAdapter<T> {

    private Context mContext;
    private List<T> mItems;

    private int mLayout;

    public AbstractListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public AbstractListAdapter(Context context, int layout, List<T> objects) {
        super(context, layout, 0, objects);
        this.mLayout = layout;
        this.mItems = objects;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = getConvertViewForType(position, parent);
        }
        convertView = getCustomItem(position, convertView, parent);
        final T item = getItem(position);

        initView(convertView, item, position);
        return convertView;
    }

    protected View getCustomItem(int position, View convertView, ViewGroup parent) {
        return convertView;
    }

    protected View getConvertViewForType(int position, ViewGroup parent) {
        return View.inflate(mContext, mLayout, null);
    }

    protected abstract void initView(View convertView, T item, int position);

    protected void setTextViewWithHtml(View view, int resId, String text) {
        ViewUtils.setTextViewWithHtml(view, resId, text);
    }

    protected void setTextView(View view, int resId, String text) {
        ViewUtils.setTextView(view, resId, text);
    }
    protected void setTextView(View view, int resId, Spannable text) {
        ViewUtils.setTextView(view, resId, text);
    }

    protected ImageView setImage(View view, int resId, String url) {
        return ViewUtils.setImage(view, resId, url);
    }

    public void update(ArrayList<T> authors) {
        this.mItems = authors;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mItems == null) {
            return 0;
        }
        return mItems.size();
    }

    @Override
    public T getItem(int position) {
        if (mItems == null || mItems.size() == 0) return null;
        return mItems.get(position);
    }

    @Override
    public int getPosition(T item) {
        if (mItems == null || mItems.size() == 0) return 0;
        return mItems.indexOf(item);
    }

    public void addItem(T item) {
        mItems.add(item);
        notifyDataSetChanged();
    }

    public List<T> getItems() {
        return mItems;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    public Context getContext() {
        return mContext;
    }

    public int getBaseLayout() {
        return mLayout;
    }

}
