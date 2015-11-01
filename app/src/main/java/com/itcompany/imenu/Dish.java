package com.itcompany.imenu;

import android.util.Log;

import com.orm.SugarRecord;

/**
 * Created by Max on 19.05.2015.
 */
public class Dish extends SugarRecord<Dish> {
    private String dishName;
    private String dishPrice;
    private Double dishPriceDouble;
    private String dishOldPrice;
    private Double dishOldPriceDouble;
    private Integer dishVal;
    private String dishImage;
    private String dishPreview;
    private String dishInfo;

    public Dish() {
        this.dishName = "";
        this.dishPrice = "";
        this.dishOldPrice = "";
        this.dishImage = "";
        this.dishVal=1;
        this.dishPreview="";
        this.dishInfo="";





    }

    public String getDishName() {
        return dishName;
    }
    public String getDishPrice() {
        return dishPrice;
    }
    public Double getDishPriceDouble() {
        return dishPriceDouble;
    }
    public String getDishPriceRub() {
        if (dishPriceDouble==0.00){
            return "";
        }
        int dishPriceRub=dishPriceDouble.intValue();
        return Integer.toString(dishPriceRub);
    }
    public String getDishPriceCent() {
        if (dishPriceDouble==0.00){
            return "";
        }
        return String.format("%02.0f", (dishPriceDouble * 100 - dishPriceDouble.intValue() * 100));//Integer.toString(dishPriceCent);
    }
    public String getDishOldPrice() {
        return dishOldPrice;
    }
    public Double getDishOldPriceDouble() {
        return dishOldPriceDouble;
    }
    public String getDishOldPriceRub() {
        if (dishOldPriceDouble==0.00){
            return "";
        }
        int dishOldPriceRub=dishOldPriceDouble.intValue();
        return Integer.toString(dishOldPriceRub);
    }
    public String getDishOldPriceCent() {
        if (dishOldPriceDouble==0.00){
            return "";
        }
        return String.format("%02.0f", (dishOldPriceDouble * 100 - dishOldPriceDouble.intValue() * 100));
    }
    public String getDishImage() {
        return dishImage;
    }
    public String getDishPreview() {
        return dishPreview;
    }
    public String getDishInfo() {
        return dishInfo;
    }
    public Integer getDishVal() {
        return dishVal;
    }


    public void setDishName(String dishName) {
        this.dishName = dishName;
    }
    public void setDishPrice(String dishPrice) {
        if (dishPrice=="") {
            this.dishPriceDouble=0.00;
            Log.d("mylog", "dishPrice='' ");
        }else{
            try {
                this.dishPriceDouble = Double.valueOf(dishPrice);
                //System.out.println(f);
            } catch (NumberFormatException e) {
                this.dishPriceDouble=0.00;
                Log.d("mylog", "Неверный формат строки!");
            }
        }
        this.dishPrice = dishPrice;
        Log.d("mylog1", "set "+dishPrice);
    }
    public void setDishOldPrice(String dishOldPrice) {
        if (dishOldPrice=="") {
            this.dishOldPriceDouble=0.00;
            Log.d("mylog", "dishOldPrice='' ");
        }else{
            try {
                this.dishOldPriceDouble = Double.valueOf(dishOldPrice);
                //System.out.println(f);
            } catch (NumberFormatException e) {
                this.dishOldPriceDouble=0.00;
                Log.d("mylog", "Неверный формат строки!");
            }
        }
        this.dishOldPrice = dishOldPrice;
    }
    public void setDishImage(String dishImage) {
        this.dishImage = dishImage;
    }

    public void setDishVal(Integer dishVal) {
        this.dishVal = dishVal;
    }
    public void setDishPreview(String dishPreview) {
        this.dishPreview = dishPreview;
    }
    public void setDishInfo(String dishInfo) {
        this.dishInfo = dishInfo;
    }

}
