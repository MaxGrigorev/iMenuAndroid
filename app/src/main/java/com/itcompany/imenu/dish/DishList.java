package com.itcompany.imenu.dish;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.itcompany.imenu.CategoryDish;
import com.itcompany.imenu.Dish;
import com.itcompany.imenu.R;
import com.itcompany.imenu.category.CategoryAdapter;
import com.itcompany.imenu.category.CategoryAsyncTask;

import java.util.ArrayList;
import java.util.List;

public class DishList extends AppCompatActivity implements DishAsyncTask.AsyncResponse, DishAdapter.OnItemClickListener {

    DishAdapter mAdapter;
    RecyclerView mRecyclerView;
    Integer position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position=getIntent().getIntExtra("position", 0);

        setContentView(R.layout.dish);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        List<CategoryDish> listCategoryDish = CategoryDish.listAll(CategoryDish.class);
        //toolbar.setNavigationIcon(R.drawable.ic_launcher);
        Log.d("mylog2", "set title " + listCategoryDish.get(position).getCategoryName());
        getSupportActionBar().setTitle(listCategoryDish.get(position).getCategoryName());
        //toolbar.setSubtitle("Sub");
        //toolbar.setLogo(R.drawable.ic_launcher);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //Отчищаем таблицу Блюда
        Dish.deleteAll(Dish.class);
//Запускаем асинхронную загрузку блюда
//функции обратного вызова processFinish, hostOffline
        DishAsyncTask dishAsyncTask = new DishAsyncTask(this);
        dishAsyncTask.execute(position);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                Log.d("mylog2", "android.R.id.home ");
                onBackPressed();
                return true;
            case R.id.action_settings:
                Log.d("mylog2", "R.id.action_settings ");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view, int position) {

    }

    @Override
    public void processFinish() {
        mAdapter = new DishAdapter(this,this);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void hostOffline() {

    }
}
