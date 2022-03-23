package com.app2018212763.smartcabinet.Bean;

public class Box {
    private String box_number;

    public String getBox_number() {
        return box_number;
    }

    public void setBox_number(String number) {
        this.box_number = number;
    }
    @Override
    public String toString() {
        return this.box_number + "空闲中";
    }
}
