package com.example.jack.myapplication;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jack.myapplication.api.ApiStores;
import com.example.jack.myapplication.model.WeatherModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {


    TextView mRetrofit1Tv;
    private ApiStores mApiStores;
    private Retrofit mRetrofit;
    private static final String TAG = "Retrofit1Activity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRetrofit1Tv = (TextView) findViewById(R.id.retrofit1_tv);
        mRetrofit = new Retrofit.Builder()
                .baseUrl(ApiStores.API_SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //获取api接口的实现类的对象
        mApiStores = mRetrofit.create(ApiStores.class);

        Button json = (Button) findViewById(R.id.retrofit1_btn_json);
        Button str = (Button) findViewById(R.id.retrofit1_btn_str);

        json.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 返回JSON
                Call<WeatherModel> modelCall = mApiStores.loadDataByJson("101190201");
                modelCall.enqueue(new Callback<WeatherModel>() {
                    @Override
                    public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                        if(response.body() == null){
                            mRetrofit1Tv.setText("null");
                        }else{
                            mRetrofit1Tv.setText(response.body().getWeatherinfo().toString());
                        }

                    }

                    @Override
                    public void onFailure(Call<WeatherModel> call, Throwable t) {

                    }
                });
            }
        });
        str.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<String> stringCall = mApiStores.loadDataByString("101190201");
                stringCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.body() == null){
                            Toast.makeText(MainActivity.this, "11", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "11", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        });
    }


}
