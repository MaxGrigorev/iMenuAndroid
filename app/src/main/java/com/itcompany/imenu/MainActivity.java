package com.itcompany.imenu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.itcompany.imenu.category.CategoryAsyncTask;
import com.orm.SugarRecord;

public class MainActivity extends AppCompatActivity implements CategoryAsyncTask.AsyncResponse {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//Отчищаем таблицу Категории
        CategoryDish.deleteAll(CategoryDish.class);
//Запускаем асинхронную загрузку категори
//функции обратного вызова processFinish, hostOffline
        CategoryAsyncTask categoryAsyncTask = new CategoryAsyncTask(this);
        categoryAsyncTask.execute();

    }

    @Override
    public void onResume(){
        super.onResume();
        // Возобновите все приостановленные обновления UI,
        // потоки или процессы, которые были "заморожены",
        // когда данный объект был неактивным.
        Log.d("mylog", "onResume");
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        // super.onBackPressed();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinish() {
        Log.d("mylog", "proccesFinish");
        Log.d("mylog", "startActivity CategoryList");
        startActivity(new Intent("com.itcompany.imenu.CATEGORY"));

    }

    @Override
    public void hostOffline() {
        Log.d("mylog", "hostOffline");
    }


}
