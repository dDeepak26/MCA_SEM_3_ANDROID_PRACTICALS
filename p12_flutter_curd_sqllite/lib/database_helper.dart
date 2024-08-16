import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart';

class DatabaseHelper {
  static Future<Database> database() async {
    final dbPath = await getDatabasesPath();
    return openDatabase(
      join(dbPath, 'users_database.db'),
      onCreate: (db, version) {
        return db.execute(
          'CREATE TABLE users(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, age INTEGER)',
        );
      },
      version: 1,
    );
  }

  static Future<List<Map<String, dynamic>>> getUsers() async {
    final db = await DatabaseHelper.database();
    return db.query('users', orderBy: "id");
  }

  static Future<int> createUser(String name, int age) async {
    final db = await DatabaseHelper.database();
    final data = {'name': name, 'age': age};
    return await db.insert('users', data);
  }

  static Future<int> updateUser(int id, String name, int age) async {
    final db = await DatabaseHelper.database();
    final data = {'name': name, 'age': age};
    return await db.update('users', data, where: "id = ?", whereArgs: [id]);
  }

  static Future<int> deleteUser(int id) async {
    final db = await DatabaseHelper.database();
    return await db.delete('users', where: "id = ?", whereArgs: [id]);
  }
}
