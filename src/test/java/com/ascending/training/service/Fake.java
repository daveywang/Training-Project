/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package com.ascending.training.service;

public class Fake {
    public int getInt() {
        return 100;
    }

    public double getDouble() {
        return 99.00;
    }

    public char getChar() {
        return 'a';
    }

    public boolean getBoolean() {
        return true;
    }

    public String getString() {
        return "String";
    }

    public Number getNumber() {
        return 888.00;
    }

    public Object getObject() {
        return new Fake();
    }
}
