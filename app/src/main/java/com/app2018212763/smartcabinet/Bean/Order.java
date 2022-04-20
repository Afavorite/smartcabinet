package com.app2018212763.smartcabinet.Bean;

public class Order {
    private String order_number;
    private String order_creator;
    private String order_box_number;
    private String order_status;
    private String order_create_time;
    private String order_end_time;
    private String order_use_start_time;
    private String order_use_end_time;
    private String order_temp;
    private String order_sterilization;
    private String order_temp_switch;

    public String getOrder_number() {
        return order_number;
    }
    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getOrder_creator() { return order_creator; }
    public void setOrder_creator(String order_creator) { this.order_creator = order_creator; }

    public String getOrder_box_number() { return order_box_number; }
    public void setOrder_box_number(String order_box_number) { this.order_box_number = order_box_number; }

    public String getOrder_status() { return order_status; }
    public void setOrder_status(String order_status) { this.order_status = order_status; }

    public String getOrder_create_time() { return order_create_time; }
    public void setOrder_create_time(String order_create_time) { this.order_create_time = order_create_time; }

    public String getOrder_end_time() { return order_end_time; }
    public void setOrder_end_time(String order_end_time) { this.order_end_time = order_end_time; }

    public String getOrder_use_start_time() { return order_use_start_time; }
    public void setOrder_use_start_time(String order_use_start_time) { this.order_use_start_time = order_use_start_time; }

    public String getOrder_use_end_time() { return order_use_end_time; }
    public void setOrder_use_end_time(String order_use_end_time) { this.order_use_end_time = order_use_end_time; }

    public String getOrder_temp() { return order_temp; }
    public void setOrder_temp(String order_temp) { this.order_temp = order_temp; }

    public String getOrder_sterilization() { return order_sterilization; }
    public void setOrder_sterilization(String order_sterilization) { this.order_sterilization = order_sterilization; }

    public String getOrder_temp_switch() {
        return order_temp_switch;
    }
    public void setOrder_temp_switch(String order_temp_switch) {
        this.order_temp_switch = order_temp_switch;
    }
}