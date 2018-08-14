package com.example.demo.Entity;

/**
 * 存储服务器 硬盘，内存，cpu 使用情况
 */
public class UnilizationRatio {

    //硬盘使用率
    private Double disk[];
    //内存使用率
    private Double ram[];
    //cpu使用率
    private Double cpu[];
    //time时间
    private String dataArr[];

    public String[] getDataArr() {
        return dataArr;
    }

    public void setDataArr(String[] dataArr) {
        this.dataArr = dataArr;
    }

    public Double[] getDisk() {
        return disk;
    }

    public void setDisk(Double[] disk) {
        this.disk = disk;
    }

    public Double[] getRam() {
        return ram;
    }

    public void setRam(Double[] ram) {
        this.ram = ram;
    }

    public Double[] getCpu() {
        return cpu;
    }

    public void setCpu(Double[] cpu) {
        this.cpu = cpu;
    }
}
