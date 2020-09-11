package com.skyworth.myapplication;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import io.flutter.embedding.android.FlutterView;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.embedding.engine.renderer.FlutterUiDisplayListener;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.view.FlutterMain;

public class Flutter2Activity extends AppCompatActivity {
    FlutterEngine mFlutter2Engine;
    FlutterView   mFlutter2View;
    MethodChannel mFlutter2MethodChannel;
    Button        mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FlutterMain.startInitialization(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flutter2);
        mButton = findViewById(R.id.button1);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFlutter2MethodChannel != null) {
                    mFlutter2MethodChannel.invokeMethod("getFlutterMethod", null, new MethodChannel.Result() {
                        @Override
                        public void success(Object result) {
                            if (result != null) {
                                String str = result.toString();
                                mButton.setText(str);
                            }
                        }

                        @Override
                        public void error(String errorCode, String errorMessage, Object errorDetails) {

                        }

                        @Override
                        public void notImplemented() {

                        }
                    });
                }
            }
        });
        initFlutterEngine();
        mFlutter2View = createFlutterView();
        mFlutter2View.attachToFlutterEngine(mFlutter2Engine);
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
        mFlutter2Engine = FlutterEngineCache.getInstance().get("flutter2");
        if (mFlutter2Engine == null) {
            mFlutter2Engine = new FlutterEngine(this);
            mFlutter2Engine.getNavigationChannel().setInitialRoute("route2");
            initChannel(mFlutter2Engine);
            mFlutter2Engine.getDartExecutor().executeDartEntrypoint(
                    DartExecutor.DartEntrypoint.createDefault()
            );
            FlutterEngineCache.getInstance().put("my_engine_id", mFlutter2Engine);
        }
    }

    private void initChannel(FlutterEngine flutter2Engine) {
        mFlutter2MethodChannel = new MethodChannel(flutter2Engine.getDartExecutor(), "native/flutter2Java");
        mFlutter2MethodChannel.setMethodCallHandler(new MethodChannel.MethodCallHandler() {
            @Override
            public void onMethodCall(MethodCall call, MethodChannel.Result result) {
                if (call == null || result == null) {
                    if (result != null) {
                        result.error("-1", "MethodCall is null", new Exception("MethodCall is null"));
                    }
                    return;
                }
                if ("getJavaMethod".equals(call.method)) {
                    result.success("success ");
                } else {
                    result.success(" unKnow method");
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        mFlutter2Engine.getLifecycleChannel().appIsResumed();
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
        mFlutter2Engine.getLifecycleChannel().appIsInactive();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFlutter2Engine.getLifecycleChannel().appIsPaused();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FlutterEngineCache.getInstance().remove("my_engine_id");
        mFlutter2Engine.destroy();
        mFlutter2Engine = null;
        mFlutter2View = null;
        mFlutter2MethodChannel = null;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}