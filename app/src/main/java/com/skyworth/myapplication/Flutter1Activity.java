package com.skyworth.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.flutter.embedding.android.FlutterView;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.embedding.engine.renderer.FlutterUiDisplayListener;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class Flutter1Activity extends AppCompatActivity {
    FlutterEngine mFlutter1Engine;
    FlutterView   mFlutter1View;
    MethodChannel mFlutter1MethodChannel1;
    String        flutter1Name = "default is null";
    Button        mButton1,mButton2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 通过FlutterView引入Flutter编写的页面
        setContentView(R.layout.activity_flutter1);
        initFlutterEngine();
        mFlutter1View = createFlutterView();
        mButton1 = findViewById(R.id.button1);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFlutter1MethodChannel1!=null){
                    Log.d("TAG","mButton1 java 调用 flutter");
                    mFlutter1MethodChannel1.invokeMethod("getFlutterName", flutter1Name, new MethodChannel.Result() {
                        @Override
                        public void success(Object result) {
                            flutter1Name = (String) result;
                            mButton1.setText(flutter1Name);
                        }

                        @Override
                        public void error(String errorCode, String errorMessage, Object errorDetails) {
                            Log.d("TAG","errorCode = "+ errorCode + ",errorMessage = "+errorMessage);
                        }

                        @Override
                        public void notImplemented() {
                            Log.d("TAG","flutter 端没有实现  getFlutterName");
                        }
                    });
                }
            }
        });
        mButton2 = findViewById(R.id.checkFlutterMap);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFlutter1MethodChannel1!=null){
                    Log.d("TAG","mButton2 java 调用 flutter");
                    mFlutter1MethodChannel1.invokeMethod("checkFlutterMap", null, new MethodChannel.Result() {
                        @Override
                        public void success(Object result) {
                            String flutterMapStr  = (String) result;
                            Log.d("TAG","flutterMapStr = "+ flutterMapStr);
                        }

                        @Override
                        public void error(String errorCode, String errorMessage, Object errorDetails) {
                            Log.d("TAG","errorCode = "+ errorCode + ",errorMessage = "+errorMessage);
                        }

                        @Override
                        public void notImplemented() {
                            Log.d("TAG","flutter 端没有实现  checkFlutterMap");
                        }
                    });
                }
            }
        });
        // 关键代码，将Flutter页面显示到FlutterView中
        mFlutter1View.attachToFlutterEngine(mFlutter1Engine);
    }

    private FlutterView createFlutterView() {
        FlutterView flutterView = new FlutterView(this);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        FrameLayout flContainer = findViewById(R.id.fl_flutter);
        flContainer.addView(flutterView, lp);
        flContainer.setVisibility(View.INVISIBLE);
        FlutterUiDisplayListener listener = new FlutterUiDisplayListener() {
            @Override
            public void onFlutterUiDisplayed() {
                flContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFlutterUiNoLongerDisplayed() {

            }
        };
        flutterView.addOnFirstFrameRenderedListener(listener);
        return flutterView;
    }

    private void initFlutterEngine() {
        mFlutter1Engine = FlutterEngineCache.getInstance().get("flutter1");
        if (mFlutter1Engine == null){
            mFlutter1Engine = new FlutterEngine(this);
            mFlutter1Engine.getNavigationChannel().setInitialRoute("route1");
            initChannel(mFlutter1Engine);
            mFlutter1Engine.getDartExecutor().executeDartEntrypoint(
                    DartExecutor.DartEntrypoint.createDefault()
            );
            FlutterEngineCache.getInstance().put("flutter1", mFlutter1Engine);
        }
    }

    private void initChannel(FlutterEngine flutterEngine) {
        mFlutter1MethodChannel1 = new MethodChannel(flutterEngine.getDartExecutor(), "flutter1/flutter2Java");
        mFlutter1MethodChannel1.setMethodCallHandler(new MethodChannel.MethodCallHandler() {
            @Override
            public void onMethodCall(MethodCall call, MethodChannel.Result result) {
                if (call == null || result == null){
                    if (result!=null){
                        result.error("-1","MethodCall is null",new Exception("MethodCall is null"));
                    }
                    return;
                }
                if ("getInt".equals(call.method)){
                    result.success(Integer.MAX_VALUE);
                }if ("getLong".equals(call.method)){
                    result.success(Long.MAX_VALUE);
                }else {
                    result.notImplemented();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFlutter1Engine.getLifecycleChannel().appIsResumed();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFlutter1Engine.getLifecycleChannel().appIsInactive();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFlutter1Engine.getLifecycleChannel().appIsPaused();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FlutterEngineCache.getInstance().remove("flutter1");
        mFlutter1Engine.destroy();
        mFlutter1Engine = null;
        mFlutter1View = null;
        mFlutter1MethodChannel1 = null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}