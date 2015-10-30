/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.itcompany.imenu.category;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.itcompany.imenu.CategoryDish;
import com.itcompany.imenu.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;

/**
 * Adapter for the planet data used in our drawer menu,
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private ArrayList<CategoryDish> arrayListCategoryDish;
    private OnItemClickListener mListener;
    private DisplayImageOptions options;
    private Context context;

    /**
     * Interface for receiving click events from cells.
     */
    public interface OnItemClickListener {
        public void onClick(View view, int position);
    }

    /**
     * Custom viewholder for our planet views.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView categoryName;
        public final TextView categoryVal;
        public final ImageView categoryImage;

        // проставляем данные для элементов

        public ViewHolder(View itemView) {
            super(itemView);
            // проставляем данные для элементов
            categoryName = (TextView)itemView.findViewById(R.id.tvCategoryName );
            categoryVal = (TextView)itemView.findViewById(R.id.tvCategoryVal);
            categoryImage = (ImageView)itemView.findViewById(R.id.ivCategoryImage);
            Log.d("mylog", "ada 7");
        }
    }

    public CategoryAdapter(Context context, ArrayList<CategoryDish> arrayListCategoryDish, OnItemClickListener listener) {
        Log.d("mylog", "ada 6");
        this.arrayListCategoryDish = arrayListCategoryDish;
        this.mListener = listener;
        this.context = context;
        Log.d("mylog", "ada 66");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("mylog", "ada 5");
        LayoutInflater vi = LayoutInflater.from(parent.getContext());
        View v = vi.inflate(R.layout.drawer_list_item, parent, false);
        Log.d("mylog", "ada 55");



        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_action_barcode_1)
                .showImageForEmptyUri(R.drawable.ic_add_circle_black_24dp)
                .showImageOnFail(R.drawable.ic_add_circle_outline_black_24dp)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(20)).build();

        Log.d("mylog", "ada 1");
        holder.categoryName.setText(arrayListCategoryDish.get(position).getCategoryName());
        Log.d("mylog", "ada 11" + arrayListCategoryDish.get(position).getCategoryName());
        if (arrayListCategoryDish.get(position).getCategoryVal()==0){
            holder.categoryVal.setText("");
        }else {
            holder.categoryVal.setText(arrayListCategoryDish.get(position).getCategoryVal().toString());
        }

        ImageLoader imageLoader = ImageLoader.getInstance(); // Получили экземпляр
        imageLoader.init(ImageLoaderConfiguration.createDefault(context)); // Проинициализировали конфигом по умолчанию
        imageLoader.displayImage(("http://sushi.s-pom.ru"+arrayListCategoryDish.get(position).getCategoryImage()), holder.categoryImage, options);
        // Запустили асинхронный показ картинки

        Log.d("mylog", "ada 2");
        holder.categoryName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(view, position);
            }
        });
        Log.d("mylog", "ada 3");
    }

    @Override
    public int getItemCount() {
        Log.d("mylog", "ada 4");
        return arrayListCategoryDish.size();
    }
}
