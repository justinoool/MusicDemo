package com.example.jack.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jack.myapplication.api.RequestGetApi;
import com.example.jack.myapplication.api.RequestPost;
import com.example.jack.myapplication.model.HeFengWeather;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Retrofit2Activity extends AppCompatActivity implements View.OnClickListener {


    TextView mRetrofit2Tv;
    private Button bt_async, bt_sync;

    private static final String TAG = "Retrofit2Activity";
    private RequestGetApi mGetApi;
    private Retrofit mRetrofit;

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setText(msg.getData().getString("value"));
            Log.d(TAG,msg.getData().getString("value"));
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit2);

        mRetrofit2Tv = (TextView) findViewById(R.id.retrofit2_tv);
        bt_async = (Button) findViewById(R.id.retrofit2_btn_async);
        bt_sync = (Button) findViewById(R.id.retrofit2_btn_sync);

        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.heweather.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mGetApi = mRetrofit.create(RequestGetApi.class);

        bt_async.setOnClickListener(this);
        bt_sync.setOnClickListener(this);

    }
//"74fca083630f4d5599f11d72b9099808"

        // 设置TextView
    private void setText(String s) {
        try {
            mRetrofit2Tv.setText(s);
        } catch (Exception e) {
            Log.e(TAG, "setText: ", e);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.retrofit2_btn_async:

                Call<HeFengWeather> weatherCall = mGetApi.getData("深圳", "74fca083630f4d5599f11d72b9099808");
                weatherCall.enqueue(new Callback<HeFengWeather>() {
                    @Override
                    public void onResponse(Call<HeFengWeather> call, Response<HeFengWeather> response) {
                        if (response.isSuccessful()) {
                            List<HeFengWeather.HeWeather5Bean> heWeather5 = response.body().getHeWeather5();
                            for (HeFengWeather.HeWeather5Bean weather5Bean : heWeather5) {
                                setText(weather5Bean.getBasic().toString());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<HeFengWeather> call, Throwable t) {

                    }
                });

                break;
            case R.id.retrofit2_btn_sync:
                Toast.makeText(this, "11", Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Call<HeFengWeather> weatherCall1 = mGetApi.getData("广州", "74fca083630f4d5599f11d72b9099808");

                        try {

                            HeFengWeather body = weatherCall1.execute().body();
                            List<HeFengWeather.HeWeather5Bean> heWeather5 = body.getHeWeather5();
                            String text = "";
                            for (HeFengWeather.HeWeather5Bean weather5Bean : heWeather5) {
                                text=  weather5Bean.getBasic().toString();
                            }



                           /* Bundle bundle = new Bundle();
                            bundle.putString("value", text);
                            Message message = new Message();
                            message.setData(bundle);
                            mHandler.sendMessage(message);*/
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
        }

    }
}
