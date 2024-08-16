import 'package:flutter/material.dart';
import '../widgets/product_box.dart';

class MyHomePage extends StatelessWidget {
  final String title;
  MyHomePage({Key? key, required this.title}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text(title)),
      body: ListView(
        padding: const EdgeInsets.fromLTRB(12.0, 12.0, 12.0, 12.0),
        children: <Widget>[
          ProductBox(
              name: "iPhone",
              description: "iPhone is the top branded phone ever",
              price: 55000,
              image: "iphone.jpg"),
          ProductBox(
              name: "Android",
              description: "Android is a very stylish phone",
              price: 10000,
              image: "android.jpg"),
          ProductBox(
              name: "Tablet",
              description: "Tablet is a popular device for official meetings",
              price: 25000,
              image: "tablet.jpg"),
          ProductBox(
              name: "Laptop",
              description: "Laptop is the most famous electronic device",
              price: 35000,
              image: "laptop.jpg"),
          ProductBox(
              name: "Desktop",
              description: "Desktop is most popular for regular use",
              price: 10000,
              image: "computer.jpg"),
          ProductBox(
              name: "Desktop",
              description: "Desktop is most popular for regular use",
              price: 10000,
              image: "computer.jpg"),
          // Add more product boxes here
        ],
      ),
    );
  }
}
