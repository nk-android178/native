import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class nextpage extends StatefulWidget {
  @override
  _nextpageState createState() => _nextpageState();
}

class _nextpageState extends State<nextpage> {
  static const platform = const MethodChannel('com.skyworth.myapplication/battery');

  String _batteryLevel = 'unknown battery level';

  String _content = '';

  @override
  void initState(){
    platform.setMethodCallHandler((call) => Future<String>((){
      switch(call.method){
        case "send":
          return _send(call.arguments); //handler.arguments表示native传递的方法参数
          break;
      }
      return null;
    }));
    super.initState();
  }

  String  _send(arg){
    setState(() {
      _content = arg;
    });
    return "收到"+arg;
  }

  void _sendToNative(){

  }

  Future<Null> _getBatteryLevel() async{
    String batteryLevel;
    try {
      final int result = await platform.invokeMethod('getBatteryLevel');
      batteryLevel = 'Battery level at $result % .';
    } catch (e, s) {
      batteryLevel = 'Failed to get battery level $s ';
      print(s);
    }

    setState(() {

      _batteryLevel = batteryLevel;
    });
  }

  getFlutterName() async{
    return await 'nextPage';
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "next",
      home: Scaffold(
        appBar: AppBar(
          title: Text("nextpage"),
        ),
        body: Column(
          children: [
            Text("显示新界面"),
            RaisedButton(
              child: Text("Get Battery Level"),
              onPressed: _getBatteryLevel,
            ),
            Text(_batteryLevel),
            Text(_content)
          ],
        ),
      ),
    );
  }
}
