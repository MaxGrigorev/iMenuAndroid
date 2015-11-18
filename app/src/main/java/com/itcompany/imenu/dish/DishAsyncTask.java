package com.itcompany.imenu.dish;


import android.os.AsyncTask;
import android.util.Log;

import com.itcompany.imenu.AsyncTaskResult;
import com.itcompany.imenu.CategoryDish;
import com.itcompany.imenu.Dish;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max on 24.03.2015.
 */
public class DishAsyncTask extends AsyncTask<Integer, Void, AsyncTaskResult> {
    public AsyncResponse response=null;
    String result;

    Dish dish;


    public DishAsyncTask(AsyncResponse response) {
       this.response=response;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected AsyncTaskResult doInBackground(Integer... params) {
        //Параметры
        AsyncTaskResult asyncTaskResult=new AsyncTaskResult();
        asyncTaskResult.setError(0);
        String url = "";
        Integer position=0;
        Document doc = null;
        Connection conJs;
        Elements elementsDish;
        Elements elementsDishName;
        Elements elementsDishImg;
        Elements elementsDishPrice;
        Elements elementsDishInfo;
        Elements elementsDishPreview;

        Elements elementsImg;

        if( params.length > 0 ){
            position = params[0];
        }

        List<CategoryDish> listCategoryDish = CategoryDish.listAll(CategoryDish.class);
        url=listCategoryDish.get(position).getCategoryHref();
        try {
                //Проходимся по ссылкам категории
                conJs=Jsoup.connect("http://sushi.s-pom.ru" + url);
                doc = conJs.get();
                elementsImg = doc.select("div#item img");//может быть одна картинка

                elementsDish=doc.select("div#item li");
                for(int i=0; i<elementsDish.size(); i++) {
                    elementsDishImg = elementsDish.eq(i).select("img");
                    elementsDishName = elementsDish.eq(i).select("h3,h2");
                    elementsDishPrice = elementsDish.eq(i).select("span.price");
                    elementsDishInfo = elementsDish.eq(i).select("p.product-info");
                    elementsDishPreview = elementsDish.eq(i).select("p.product-preview");

                    dish=new Dish();
                    dish.setDishName(elementsDishName.text());
                    Log.d("mylog1", "dish name " + elementsDishName.text());
                    if (elementsImg.size()==1){
                        dish.setDishImage(elementsImg.attr("src"));
                    }else {
                        dish.setDishImage(elementsDishImg.attr("src"));
                    }
                    dish.setDishPrice(elementsDishPrice.text());
                    dish.setDishInfo(elementsDishInfo.text());
                    dish.setDishPreview(elementsDishPreview.text());
                    dish.save();
                }

        }
        catch (HttpStatusException e) {
            asyncTaskResult.setError(1);
            Log.d("mylog", "SaleGoodsAsyncTask HttpStatusException: " + e);
            e.printStackTrace();
            return asyncTaskResult;
        }
        catch (IOException e) {
            asyncTaskResult.setError(2);
            Log.d("mylog", "SaleGoodsAsyncTask IOException: " + e);
            e.printStackTrace();
            return asyncTaskResult;
        }
        catch (Exception e) {
            asyncTaskResult.setError(3);
            Log.d("mylog", "SaleGoodsAsyncTask Exception: " + e);
            e.printStackTrace();
            return asyncTaskResult;
        }
        finally {
            return asyncTaskResult;
        }

    }

    @Override
    protected void onPostExecute(AsyncTaskResult result) {
        super.onPostExecute(result);
        //отправляем данные в баркод
        switch(result.getError()) {
            case 0:
                response.processFinish();
                break;
            case 1:
                break;
            case 2:
                response.hostOffline();
                break;
            case 3:
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }


    public interface AsyncResponse {
        void processFinish();
        void hostOffline();
    }

}
