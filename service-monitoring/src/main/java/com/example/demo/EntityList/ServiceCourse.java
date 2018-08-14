package com.example.demo.EntityList;

public class ServiceCourse {
    private String name;//进程名
    private String cpu;//进程占用cpu
    private String mMemory;//进程占用内存
    private String user;//进程关联账户
    private int count;//进程个数

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getmMemory() {
        return mMemory;
    }

    public void setmMemory(String mMemory) {
        this.mMemory = mMemory;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
