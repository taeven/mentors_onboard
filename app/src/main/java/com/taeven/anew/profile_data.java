package com.taeven.anew;

/**
 * Created by vaibhav on 15/12/16.
 */
public class profile_data {
    private String name,email,password,sex,address,day,month,year;
    private long mobile;

    profile_data()
    {
        this.name = "";
        this.email = "";
        this.password = "";
        this.sex="";

        this.day="";
        this.month="";
        this.year="";
        this.address="";
        this.mobile=0;
    }

    public long getMobile() {
        return mobile;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate()
    {
        String date;
        date=day+"-"+month+"-"+year;
        return date;
    }


}
