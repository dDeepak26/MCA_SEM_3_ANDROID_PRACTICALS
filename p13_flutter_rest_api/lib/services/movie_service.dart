import 'dart:convert';
import 'package:flutter/services.dart' show rootBundle;
import '../models/movie.dart';

class MovieService {
  Future<List<Movie>> loadMovies() async {
    final jsonString = await rootBundle.loadString('assets/movie.json');
    final jsonList = jsonDecode(jsonString) as List;
    return jsonList.map((json) => Movie.fromJson(json)).toList();
  }
}
