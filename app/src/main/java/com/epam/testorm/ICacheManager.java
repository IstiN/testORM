package com.epam.testorm;

import android.widget.Adapter;
import android.widget.ListAdapter;

/**
 * Created by Mikhail_Ivanou on 7/27/2015.
 */
public interface ICacheManager {

    void processFeed(String string);

    ListAdapter getFullAdapter();

    ListAdapter getFilteredAdapter();

    ListAdapter getImagesOnlyAdapter();


}
