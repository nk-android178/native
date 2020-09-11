import 'package:flutter/material.dart';

class nextpage extends StatefulWidget {
  @override
  _nextpageState createState() => _nextpageState();
}

class _nextpageState extends State<nextpage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("nextpage"),
      ),
      body: Column(
        children: [
          Text("显示新界面"),
          RaisedButton(
            child: Text("显示数据"),
          )
        ],
      ),
    );
  }
}
