package com.example.jack.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jack.myapplication.api.RequestPost;
import com.example.jack.myapplication.model.HeFengWeather;
import com.example.jack.myapplication.model.WeatherModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends AppCompatActivity implements View.OnClickListener{

    Button async,sync;
    TextView mRetrofitTv;
    private static final String TAG = "RetrofitActivity";
    private Retrofit mRetrofit;
    private RequestPost mRequestPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
       mRetrofitTv = (TextView) findViewById(R.id.retrofit_tv);
        async = (Button) findViewById(R.id.retrofit_btn_async);
        sync = (Button) findViewById(R.id.retrofit_btn_sync);

        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.heweather.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mRequestPost = mRetrofit.create(RequestPost.class);

        async.setOnClickListener(this);

        sync.setOnClickListener(this);

    }

    // 设置TextView
    private void setText(String s) {
        try {
            mRetrofitTv.setText(s);
        } catch (Exception e) {
            Log.e(TAG, "setText: ", e);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.retrofit_btn_async:
                Observable<HeFengWeather> observable = mRequestPost.PostData("深圳", "74fca083630f4d5599f11d72b9099808");
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<HeFengWeather>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onNext(@NonNull HeFengWeather heFengWeather) {
                                List<HeFengWeather.HeWeather5Bean> beanList = heFengWeather.getHeWeather5();
                                for(HeFengWeather.HeWeather5Bean weather5Bean : beanList){
                                    String string = weather5Bean.getBasic().toString();
                                    setText(string);
                                }
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                break;
            case R.id.retrofit_btn_sync:
                Toast.makeText(this, "22", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
