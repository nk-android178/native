package com.skyworth.myapplication;

import android.app.Application;
import android.util.Log;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;

public class MyApplication extends Application {
//    FlutterEngine flutterEngine;
    @Override
    public void onCreate() {
        super.onCreate();
        // Instantiate a FlutterEngine.
//        flutterEngine = new FlutterEngine(this);
//        // Configure an initial route.
//        flutterEngine.getNavigationChannel().setInitialRoute("/route2");
//        // Start executing Dart code to pre-warm the FlutterEngine.
//        flutterEngine.getDartExecutor().executeDartEntrypoint(
//                DartExecutor.DartEntrypoint.createDefault()
//        );
//        // Cache the FlutterEngine to be used by FlutterActivity or FlutterFragment.
//        FlutterEngineCache
//                .getInstance()
//                .put("my_engine_id", flutterEngine);
        Log.d("application","初始化");
    }
}
