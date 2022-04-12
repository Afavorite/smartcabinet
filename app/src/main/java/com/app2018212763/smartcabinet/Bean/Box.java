package com.app2018212763.smartcabinet.Bean;

public class Box {
    public String box_number;
    public String box_temp;
    public String box_lock;
    public String box_owner;
    public String box_status;
    public String box_sterilization;

    public String getBox_number() {
        return box_number;
    }

    public void setBox_number(String number) {
        this.box_number = number;
    }

    public String getBox_temp() {
        return box_temp;
    }

    public void setBox_temp(String box_temp) {
        this.box_temp = box_temp;
    }

    public String getBox_lock() {
        return box_lock;
    }

    public void setBox_lock(String box_lock) {
        this.box_lock = box_lock;
    }

    public String getBox_owner() {
        return box_owner;
    }

    public void setBox_owner(String box_owner) {
        this.box_owner = box_owner;
    }

    public String getBox_status() {
        return box_status;
    }

    public void setBox_status(String box_status) {
        this.box_status = box_status;
    }

    public String getBox_sterilization() {
        return box_sterilization;
    }

    public void setBox_sterilization(String box_sterilization) {
        this.box_sterilization = box_sterilization;
    }

    @Override
    public String toString() {
        return this.box_number + "空闲中";
    }
}
