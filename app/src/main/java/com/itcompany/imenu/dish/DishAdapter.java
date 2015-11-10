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

package com.itcompany.imenu.dish;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itcompany.imenu.CategoryDish;
import com.itcompany.imenu.Dish;
import com.itcompany.imenu.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for the planet data used in our drawer menu,
 */
public class DishAdapter extends RecyclerView.Adapter<DishAdapter.ViewHolder> {
    private List<Dish> listDish;
    private OnItemClickListener mListener;
    private DisplayImageOptions options;
    private Context context;
    private ImageLoader imageLoader;

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
        public final TextView dishName;
        public final TextView priceRub;
        public final TextView priceCent;
        public final TextView oldPriceRub;
        public final TextView oldPriceCent;
        public final ImageView dishImage;
        public final RelativeLayout dishLoyout;


        // проставляем данные для элементов

        public ViewHolder(View itemView) {
            super(itemView);
            // проставляем данные для элементов
            dishName = (TextView)itemView.findViewById(R.id.tvDishName);
            priceRub = (TextView)itemView.findViewById(R.id.tvPrice);
            priceCent = (TextView)itemView.findViewById(R.id.tvPriceCent);
            oldPriceRub = (TextView)itemView.findViewById(R.id.tvOldPrice);
            oldPriceCent = (TextView)itemView.findViewById(R.id.tvOldPriceCent);
            dishLoyout=(RelativeLayout)itemView.findViewById(R.id.rlDishLoyout);
            dishImage = (ImageView)itemView.findViewById(R.id.ivDishImage);

        }
    }

    public DishAdapter(Context context, OnItemClickListener listener) {
        Log.d("mylog", "DishAdapter()");
        this.mListener = listener;
        this.listDish = Dish.listAll(Dish.class);
        this.context = context;

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(20)).build();

        imageLoader = ImageLoader.getInstance(); // Получили экземпляр
        imageLoader.init(ImageLoaderConfiguration.createDefault(context)); // Проинициализировали конфигом по умолчанию
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater vi = LayoutInflater.from(parent.getContext());
        View v = vi.inflate(R.layout.dish_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.dishName.setText(listDish.get(position).getDishName());
        Log.d("mylog", "Выводим " + listDish.get(position).getDishName());

        imageLoader.displayImage(("http://sushi.s-pom.ru" + listDish.get(position).getDishImage()), holder.dishImage, options);
        // Запустили асинхронный показ картинки

        Log.d("mylog", "Картинка "+listDish.get(position).getDishImage());
        holder.dishLoyout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(view, position);
            }
        });
        //Log.d("mylog", "ada 3");
    }

    @Override
    public int getItemCount() {
        Log.d("mylog", "Количество записей "+listDish.size());
        return listDish.size();
    }

}
