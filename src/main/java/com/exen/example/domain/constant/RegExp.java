package com.exen.example.domain.constant;

public class RegExp {
    public static final String nickname = "^[a-zA-Z0-9а-яА-Я. _-]{4,15}$";
    public static final String password = "^[a-zA-Z0-9а-яА-Я.,:; _?!+=/'\\\\\"*(){}\\[\\]\\-]{8,100}$";
    public static final String phrase = "^[a-zA-Z0-9а-яА-Я.,:; _?!+=/'\\\\\"*(){}\\[\\]\\-]{1,140}$";
    public static final String tag = "^[a-zA-Z0-9а-яА-Я.,:; _?!+=/'\\\"*(){}\\[\\]\\-]{3,25}$";
    public static final String partWord = "^[a-zA-Z0-9а-яА-Я.,:; _?!+=/'\\\"*(){}\\[\\]\\-]{3,25}$";
    public static final String partNickname = "^[a-zA-Z0-9а-яА-Я. _-]{3,15}$";
}
