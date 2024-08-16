import 'package:flutter/material.dart';
import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart';
import './database_helper.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'SQLite CRUD Demo',
      theme: ThemeData(primarySwatch: Colors.blue),
      home: HomePage(),
    );
  }
}

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  final TextEditingController _nameController = TextEditingController();
  final TextEditingController _ageController = TextEditingController();
  List<Map<String, dynamic>> _users = [];

  @override
  void initState() {
    super.initState();
    _refreshUsers();
  }

  Future<void> _refreshUsers() async {
    final data = await DatabaseHelper.getUsers();
    setState(() {
      _users = data;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('SQLite CRUD Demo')),
      body: ListView.builder(
        itemCount: _users.length,
        itemBuilder: (context, index) => Card(
          margin: EdgeInsets.all(15),
          child: ListTile(
            title: Text(_users[index]['name']),
            subtitle: Text('Age: ${_users[index]['age']}'),
            trailing: Row(
              mainAxisSize: MainAxisSize.min,
              children: [
                IconButton(
                  icon: Icon(Icons.edit),
                  onPressed: () => _showForm(context, _users[index]['id']),
                ),
                IconButton(
                  icon: Icon(Icons.delete),
                  onPressed: () => _deleteUser(context, _users[index]['id']),
                ),
              ],
            ),
          ),
        ),
      ),
      floatingActionButton: FloatingActionButton(
        child: Icon(Icons.add),
        onPressed: () => _showForm(context, null),
      ),
    );
  }

  Future<void> _showForm(BuildContext context, int? id) async {
    if (id != null) {
      final existingUser = _users.firstWhere((element) => element['id'] == id);
      _nameController.text = existingUser['name'];
      _ageController.text = existingUser['age'].toString();
    } else {
      _nameController.clear();
      _ageController.clear();
    }

    await showDialog(
      context: context,
      builder: (_) => AlertDialog(
        title: Text(id == null ? 'Create User' : 'Update User'),
        content: SingleChildScrollView(
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              TextField(
                controller: _nameController,
                decoration: InputDecoration(hintText: 'Name'),
              ),
              SizedBox(height: 10),
              TextField(
                controller: _ageController,
                keyboardType: TextInputType.number,
                decoration: InputDecoration(hintText: 'Age'),
              ),
            ],
          ),
        ),
        actions: [
          TextButton(
            child: Text('Cancel'),
            onPressed: () => Navigator.of(context).pop(),
          ),
          ElevatedButton(
            child: Text(id == null ? 'Create' : 'Update'),
            onPressed: () async {
              if (id == null) {
                await _addUser();
              } else {
                await _updateUser(id);
              }
              _nameController.clear();
              _ageController.clear();
              Navigator.of(context).pop();
            },
          ),
        ],
      ),
    );
  }

  Future<void> _addUser() async {
    await DatabaseHelper.createUser(
      _nameController.text,
      int.parse(_ageController.text),
    );
    _refreshUsers();
  }

  Future<void> _updateUser(int id) async {
    await DatabaseHelper.updateUser(
      id,
      _nameController.text,
      int.parse(_ageController.text),
    );
    _refreshUsers();
  }

  Future<void> _deleteUser(BuildContext context, id) async {
    await DatabaseHelper.deleteUser(id);
    ScaffoldMessenger.of(context).showSnackBar(SnackBar(
      content: Text('Successfully deleted a user!'),
    ));
    _refreshUsers();
  }
}
