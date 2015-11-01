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

/**
 * Created by Max on 24.03.2015.
 */
public class DishAsyncTask extends AsyncTask<Void, Void, AsyncTaskResult<ArrayList<CategoryDish>>> {
    public AsyncResponse response=null;
    String result;

    ArrayList<CategoryDish> categoryDishArrayList = new ArrayList<CategoryDish>();

    CategoryDish categoryDish;


    public DishAsyncTask(AsyncResponse response) {
       this.response=response;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected AsyncTaskResult<ArrayList<CategoryDish>> doInBackground(Void... params) {
        //Параметры
        AsyncTaskResult<ArrayList<CategoryDish>> asyncTaskResult=new AsyncTaskResult<ArrayList<CategoryDish>>();
        asyncTaskResult.setError(0);
        String url = "";
        Document doc = null;
        Connection conJs;
        String categoryDishHref="";
        Elements elementsDishName;
        Elements elementsDishImg;
        Elements elementsDishPrice;
        Elements elementsDishInfo;
        Elements elementsDishPreview;
        Dish dish;
        ArrayList<Dish> dishArrayList;

        try {
            conJs=Jsoup.connect("http://sushi.s-pom.ru/menu/");
            doc = conJs.get();

            Elements elementsCategoryDish = doc.select("div#left-sidebar a");
            //Log.d("mylog", "fgat " +goodsNames.toString());
            //Elements goodsPrices = doc.select("div.goodsPrice");
            //Elements goodsOldPrices = doc.select("div.goodsOldPrice");
            //Elements goodsBarcode = doc.select("div.goodsBarcode");
            //Log.d("mylog", "fgat " +goodsOldPrices.toString());
            //Elements images = doc.select("img");


            for(int a=0; a<elementsCategoryDish.size(); a++){
                categoryDish = new CategoryDish();
                dishArrayList = new ArrayList<Dish>();
                categoryDish.setCategoryName(elementsCategoryDish.eq(a).text());
                categoryDishHref=elementsCategoryDish.eq(a).attr("href");
                categoryDish.setCategoryHref(categoryDishHref);
                Log.d("mylog", "href " + elementsCategoryDish.eq(a).attr("href"));
                //Проходимся по ссылкам категории
                conJs=Jsoup.connect("http://sushi.s-pom.ru" + categoryDishHref);
                doc = conJs.get();
                elementsDishName = doc.select("div#item li h3,div#item li h2");
                elementsDishImg = doc.select("div#item img");//может быть одна картинка
                elementsDishPrice = doc.select("div#item li span.price");
                elementsDishInfo = doc.select("div#item li p.product-info");
                elementsDishPreview = doc.select("div#item li p.product-preview");

                if (elementsDishImg.size()==1){
                    categoryDish.setCategoryImage(elementsDishImg.eq(0).attr("src"));
                    for(int i=0; i<elementsDishName.size(); i++){
                        dish=new Dish();
                        dish.setDishName(elementsDishName.eq(i).text());
                        dish.setDishImage(elementsDishImg.eq(0).attr("src"));
                        dish.setDishPrice(elementsDishPrice.eq(i).text());
                        Log.d("mylog1", "dish price " + elementsDishPrice.eq(i).text());
                        dish.setDishInfo(elementsDishInfo.eq(i).text());
                        dish.setDishPreview(elementsDishPreview.eq(i).text());
                        dishArrayList.add(dish);
                    }

                }else{
                    categoryDish.setCategoryImage(elementsDishImg.eq(0).attr("src"));
                    for(int i=0; i<elementsDishName.size(); i++){
                        dish=new Dish();
                        dish.setDishName(elementsDishName.eq(i).text());
                        dish.setDishImage(elementsDishImg.eq(i).attr("src"));
                        dish.setDishPrice(elementsDishPrice.eq(i).text());
                        dish.setDishInfo(elementsDishInfo.eq(i).text());
                        dish.setDishPreview(elementsDishPreview.eq(i).text());
                        dishArrayList.add(dish);
                    }
                }
                categoryDish.setDishArrayList(dishArrayList);


                categoryDishArrayList.add(categoryDish);
                //Log.d("mylog", "fgat " +goodsOldPrices.eq(a).text());
            }
            asyncTaskResult.setResult(categoryDishArrayList);

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
    protected void onPostExecute(AsyncTaskResult<ArrayList<CategoryDish>> result) {
        super.onPostExecute(result);
        //отправляем данные в баркод
        switch(result.getError()) {
            case 0:
                response.processFinish(result.getResult());
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
        void processFinish(ArrayList<CategoryDish> output);
        void hostOffline();
    }

}
