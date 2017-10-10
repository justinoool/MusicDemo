package com.example.jack.myapplication;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jack.myapplication.api.ApiStores;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.webkit.WebSettings.PluginState.ON;

public class MvTestActivity extends AppCompatActivity  {

    private Button json;
    private Retrofit mRetrofit;
    private ApiStores mApiStores;
    private TextView text;
    private final String TAG="MvTestActivity";
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = new Bundle();
            String value = bundle.getString("value");
            setText(value);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mv_test);

        json = (Button) findViewById(R.id.bt_json);
        text = (TextView)findViewById(R.id.json_tv);
        mRetrofit = new Retrofit.Builder()
                .baseUrl(mApiStores.BASE_PARAMETERS_MVINFO)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mApiStores = mRetrofit.create(ApiStores.class);
      json.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Observable<String> mvListInfo = mApiStores.getMVListInfo("20");
              mvListInfo.subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .subscribe(new Observer<String>() {
                          @Override
                          public void onSubscribe(@NonNull Disposable d) {

                          }

                          @Override
                          public void onNext(@NonNull String s) {
                              Gson gson = new GsonBuilder().create();
                              MVListInfo info = gson.fromJson(s, MVListInfo.class);
                              Log.d(TAG,info.getResult().toString());

                          }

                          @Override
                          public void onError(@NonNull Throwable e) {

                          }

                          @Override
                          public void onComplete() {

                          }
                      });
          }
      });

    }

    public void setText(String s ){
        if(!TextUtils.isEmpty(s)){
            text.setText(s);
        }else{
            text.setText("error");
        }
    }
   


}
