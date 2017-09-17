package com.example.jack.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jack.myapplication.R;
import com.example.jack.myapplication.api.ApiStores;
import com.example.jack.myapplication.model.MVInfo;

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
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MvTestActivity extends AppCompatActivity implements View.OnClickListener{

    private Button json;
    private Retrofit mRetrofit;
    private ApiStores mApiStores;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mv_test);

        json = (Button) findViewById(R.id.bt_json);
        text = (TextView) findViewById(R.id.json_tv);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(mApiStores.BASE_PARAMETERS_MVINFO)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mApiStores = mRetrofit.create(ApiStores.class);
      json.setOnClickListener(this);

    }

    public void setText(String s ){
        if(!TextUtils.isEmpty(s)){
            text.setText(s);
        }else{
            text.setText("error");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_json:
                Call<MVInfo> mvInfo = mApiStores.getMVInfo("baidu.ting.mv.playMV", "107056505");
                mvInfo.enqueue(new Callback<MVInfo>() {
                    @Override
                    public void onResponse(Call<MVInfo> call, Response<MVInfo> response) {
                        if (response.isSuccessful()){
                            String s = response.body().getResult().toString();
                            setText(s);
                        }
                    }

                    @Override
                    public void onFailure(Call<MVInfo> call, Throwable t) {

                    }
                });
                break;
        }
    }
}
