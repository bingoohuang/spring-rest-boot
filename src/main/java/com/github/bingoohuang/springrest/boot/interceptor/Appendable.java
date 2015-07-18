package com.github.bingoohuang.springrest.boot.interceptor;

public interface Appendable {
    Appendable append(String str);

    Appendable appendAbbreviate(String str);

    Appendable append(char ch);
}
