package com.itcompany.imenu.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.itcompany.imenu.R;

public class CategoryList extends AppCompatActivity implements CategoryAdapter.OnItemClickListener  {

    CategoryAdapter mAdapter;
    RecyclerView mRecyclerView;
    int scrollPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdapter = new CategoryAdapter(this,this);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        //mRecyclerView.scrollToPosition(11);


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

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button

            case R.id.action_settings:
                Log.d("mylog2", "R.id.action_settings ");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view, int position) {
        Log.d("mylog", "click " + position);

        Intent intent=new Intent("com.itcompany.imenu.DISH");

        // в ключ username пихаем текст из первого текстового поля
        intent.putExtra("position", position);

        startActivity(intent);
    }

}
