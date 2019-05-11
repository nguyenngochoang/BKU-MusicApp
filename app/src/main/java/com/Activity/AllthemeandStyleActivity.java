package com.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.Adapter.AllStyleAdapter;
import com.Adapter.AllthemeAdapter;
import com.Model.Style;
import com.Model.Styletheme;
import com.Model.Theme;
import com.Service.APIservice;
import com.Service.DataService;
import com.example.firebase.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllthemeandStyleActivity extends AppCompatActivity {

    RecyclerView recyclerViewtheme,recyclerViewstyle;
    Styletheme styletheme;
    Toolbar toolbar;
    AllthemeAdapter allthemeAdapter;
    AllStyleAdapter allStyleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allthemeand_style);
        anhxa();
        init();
        getData();
    }

    private void getData() {
        DataService dataService= APIservice.getservice();
        Call<Styletheme> callback= dataService.Getallstyletheme();
        callback.enqueue(new Callback<Styletheme>() {
            @Override
            public void onResponse(Call<Styletheme> call, Response<Styletheme> response) {
                styletheme=response.body();
                ArrayList<Theme> themes= (ArrayList<Theme>) styletheme.getTheme();
                ArrayList<Style> styles=(ArrayList<Style>) styletheme.getStyle();
                allStyleAdapter=new AllStyleAdapter(AllthemeandStyleActivity.this,styles);
                allthemeAdapter=new AllthemeAdapter(AllthemeandStyleActivity.this,themes);
                recyclerViewstyle.setLayoutManager(new GridLayoutManager(AllthemeandStyleActivity.this,2));
                recyclerViewtheme.setLayoutManager(new GridLayoutManager(AllthemeandStyleActivity.this,2));
                recyclerViewstyle.setAdapter(allStyleAdapter);
                recyclerViewtheme.setAdapter(allthemeAdapter);


            }

            @Override
            public void onFailure(Call<Styletheme> call, Throwable t) {

            }
        });
    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chủ đề và thể loại");
        toolbar.setTitleTextColor(getResources().getColor(R.color.violet));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhxa() {
        toolbar=findViewById(R.id.activity_allthemeand_style_toobar);
        recyclerViewstyle=findViewById(R.id.activity_allthemeand_style_stylerecyclerview);
        recyclerViewtheme=findViewById(R.id.activity_allthemeand_style_themerecyclerview);
    }
}
