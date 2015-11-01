package com.itcompany.imenu;

import android.util.Log;

import com.orm.SugarRecord;

import java.util.ArrayList;

/**
 * Created by Max on 13.08.2015.
 */
public class CategoryDish extends SugarRecord<CategoryDish> {
    String categoryName;
    String categoryHref;
    Integer categoryVal;
    String categoryImage;
    //private ArrayList<Dish> dishArrayList;

    public CategoryDish() {
    }

    /*public CategoryDish() {
        this.categoryName = "";
        this.categoryHref = "";
        this.categoryVal = 0;
        this.categoryImage = "";
        //this.dishArrayList=new ArrayList<Dish>();

    }*/

    public String getCategoryName() {
        return categoryName;
    }
    public String getCategoryHref() {
        return categoryHref;
    }
    public String getCategoryImage() {
        return categoryImage;
    }
    public Integer getCategoryVal() {
        return categoryVal;
    }
    //public ArrayList<Dish> getDishArrayList() {
    //    return dishArrayList;
    //}


    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public void setCategoryHref(String categoryHref) {
        this.categoryHref = categoryHref;
    }
    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }
    public void setCategoryVal(Integer categoryVal) {
        this.categoryVal = categoryVal;
    }
    //public void setDishArrayList(ArrayList<Dish> dishArrayList) {
    //    this.dishArrayList = dishArrayList;
    //}

}
