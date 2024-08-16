package com.example.p10_rest_api_java;

import java.util.List;

public class Pokemon {
    private String name;
    private String imageUrl;
    private List<String> types;
    private List<String> abilities;

    public Pokemon(String name, String imageUrl, List<String> types, List<String> abilities) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.types = types;
        this.abilities = abilities;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getTypes() {
        return types;
    }

    public List<String> getAbilities() {
        return abilities;
    }
}
