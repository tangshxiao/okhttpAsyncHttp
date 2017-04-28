package com.zhy.sample_okhttp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhy.http.okhttp.encapsulationresponse.HttpRequest;
import com.zhy.http.okhttp.encapsulationresponse.HttpResponsecallback;
import com.zhy.http.okhttp.encapsulationresponse.RootBen;

import java.util.HashMap;


/**
 * Created by Android on 2017/4/14.
 */

public class NewMainActivity extends Activity {

    private Button button;
    private TextView textView;
    String URL = "https://api.test.anyicx.com/";
    // HTTps 请求接口初始化
    String INIT = URL + "api/driver/init";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newmainactivity);
        button = (Button) findViewById(R.id.BUTTON);
        textView = (TextView) findViewById(R.id.id_textview);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final HashMap requestParams = new HashMap();
                requestParams.put("id",1);
                HttpRequest.go(INIT, requestParams,NewMainActivity.this,new HttpResponsecallback<RootBen<User>>() {
                    @Override
                    public void onResponse(RootBen<User> response) {
                        super.onResponse(response);
                        if(response.getCode()==0){
                            textView.setText(response.getMsg());
                        }else {
                            textView.setText("errror");
                        }
                    }
                });
            }
        });



    }
}
