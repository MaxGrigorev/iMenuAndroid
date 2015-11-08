package com.itcompany.imenu.category;


import android.os.AsyncTask;
import android.util.Log;



import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.itcompany.imenu.AsyncTaskResult;
import com.itcompany.imenu.CategoryDish;
import com.itcompany.imenu.Dish;
import com.itcompany.imenu.R;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Max on 24.03.2015.
 */
public class CategoryAsyncTask extends AsyncTask<Void, CategoryDish, AsyncTaskResult> {
    public AsyncResponse response=null;
    String result;

    CategoryDish categoryDish;

    public CategoryAsyncTask(AsyncResponse response) {
       this.response=response;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected AsyncTaskResult doInBackground(Void... params) {
        //Параметры
        AsyncTaskResult asyncTaskResult=new AsyncTaskResult();
        asyncTaskResult.setError(0);
        String url = "";
        Document doc = null;
        Connection conJs;
        String categoryDishHref="";
        Elements elementsDishImg;


        try {
            conJs=Jsoup.connect("http://sushi.s-pom.ru/menu/");
            doc = conJs.get();

            Elements elementsCategoryDish = doc.select("div#left-sidebar a");

            for(int a=0; a<elementsCategoryDish.size(); a++){
                categoryDish = new CategoryDish();
                categoryDish.setCategoryName(elementsCategoryDish.eq(a).text());
                categoryDishHref=elementsCategoryDish.eq(a).attr("href");
                categoryDish.setCategoryHref(categoryDishHref);
                //categoryDish.save();

                Log.d("mylog", "categoryDishHref " + elementsCategoryDish.eq(a).attr("href"));
                //Проходимся по ссылкам категории
                conJs=Jsoup.connect("http://sushi.s-pom.ru" + categoryDishHref);
                doc = conJs.get();

                elementsDishImg = doc.select("div#item img");//может быть одна картинка
                categoryDish.setCategoryImage(elementsDishImg.eq(0).attr("src"));
                Log.d("mylog", "elementsDishImg " + elementsDishImg.eq(0).attr("src"));
                categoryDish.save();
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
        return asyncTaskResult;
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
