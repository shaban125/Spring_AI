package com.spring.ollama.spring_ai_ollama.helper;

import java.util.*;
public class Main {
    // ye method true return karega agar item list mein hua
    public static boolean checkItem(List<String> scene, String item) {
        return scene.contains(item);
    }
    public static void main(String[] args) {
        // 1. List banana, naam scene
        List<String> scene = new ArrayList<>();
        scene.add("menu");
        scene.add("game");
        scene.add("pause");
        // 2. Check karna
        System.out.println(checkItem(scene, "game")); // true ayega
        System.out.println(checkItem(scene, "shop")); // false ayega
    }
}