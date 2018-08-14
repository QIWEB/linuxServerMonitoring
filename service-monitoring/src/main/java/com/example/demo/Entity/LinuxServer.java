package com.example.demo.Entity;

/**
 * linux 服务器配置详情实体
 */
public class LinuxServer {
    //数据采集时间
    private String creationDate;
    //服务器唯一标识
    private String token;
    //代理版本
    private String version;
    //系统运行时间()
    private String uptime;
    //登录会话计数
    private String sessions;
    //进程程计数
    private String processes;
    //进程阵列
    private String processes_array;
    //descriptors队列
    private String file_handles;
    //descriptors队列限制
    private String file_handles_limit;
    //核心
    private String os_kernel;
    //系统操作系统
    private String os_name;
    //操作系统类型（如：64位）
    private String os_arch;
    //cpu型号
    private String cpu_name;
    //cpu核心
    private String cpu_cores;
    //cpu速度
    private String cpu_freq;
    //内存总数单位Bytes
    private String ram_total;
    //内存占用数单位Bytes
    private String ram_usage;
    //虚拟总内存单位Bytes
    private String swap_total;
    //虚拟内存占用数量单位Bytes
    private String swap_usage;
    //服务器系统名称
    private String disk_array;
    //硬盘总数单位Bytes
    private String disk_total;
    //硬盘使用数量单位Bytes
    private String disk_usage;
    //连接数
    private String connections;
    //网络接口
    private String nic;
    //ipv4-IP地址
    private String ipv4;
    //ipv46-IP地址
    private String ipv6;
    //接收流量单位Bytes
    private String rx;
    //发送流量 单位Bytes
    private String tx;
    //接收间隔
    private String rx_gap;
    //发送间隔
    private String tx_gap;
    //系统平均负载
    private String load;
    //cpu负载
    private String load_cpu;
    //io负载
    private String load_io;
    //欧洲网络延迟
    private String ping_eu;
    //美国网络延迟
    private String ping_us;
    //亚洲网络延迟
    private String ping_as;

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    public String getSessions() {
        return sessions;
    }

    public void setSessions(String sessions) {
        this.sessions = sessions;
    }

    public String getProcesses() {
        return processes;
    }

    public void setProcesses(String processes) {
        this.processes = processes;
    }

    public String getProcesses_array() {
        return processes_array;
    }

    public void setProcesses_array(String processes_array) {
        this.processes_array = processes_array;
    }

    public String getFile_handles() {
        return file_handles;
    }

    public void setFile_handles(String file_handles) {
        this.file_handles = file_handles;
    }

    public String getFile_handles_limit() {
        return file_handles_limit;
    }

    public void setFile_handles_limit(String file_handles_limit) {
        this.file_handles_limit = file_handles_limit;
    }

    public String getOs_kernel() {
        return os_kernel;
    }

    public void setOs_kernel(String os_kernel) {
        this.os_kernel = os_kernel;
    }

    public String getOs_name() {
        return os_name;
    }

    public void setOs_name(String os_name) {
        this.os_name = os_name;
    }

    public String getOs_arch() {
        return os_arch;
    }

    public void setOs_arch(String os_arch) {
        this.os_arch = os_arch;
    }

    public String getCpu_name() {
        return cpu_name;
    }

    public void setCpu_name(String cpu_name) {
        this.cpu_name = cpu_name;
    }

    public String getCpu_cores() {
        return cpu_cores;
    }

    public void setCpu_cores(String cpu_cores) {
        this.cpu_cores = cpu_cores;
    }

    public String getCpu_freq() {
        return cpu_freq;
    }

    public void setCpu_freq(String cpu_freq) {
        this.cpu_freq = cpu_freq;
    }

    public String getRam_total() {
        return ram_total;
    }

    public void setRam_total(String ram_total) {
        this.ram_total = ram_total;
    }

    public String getRam_usage() {
        return ram_usage;
    }

    public void setRam_usage(String ram_usage) {
        this.ram_usage = ram_usage;
    }

    public String getSwap_total() {
        return swap_total;
    }

    public void setSwap_total(String swap_total) {
        this.swap_total = swap_total;
    }

    public String getSwap_usage() {
        return swap_usage;
    }

    public void setSwap_usage(String swap_usage) {
        this.swap_usage = swap_usage;
    }

    public String getDisk_array() {
        return disk_array;
    }

    public void setDisk_array(String disk_array) {
        this.disk_array = disk_array;
    }

    public String getDisk_total() {
        return disk_total;
    }

    public void setDisk_total(String disk_total) {
        this.disk_total = disk_total;
    }

    public String getDisk_usage() {
        return disk_usage;
    }

    public void setDisk_usage(String disk_usage) {
        this.disk_usage = disk_usage;
    }

    public String getConnections() {
        return connections;
    }

    public void setConnections(String connections) {
        this.connections = connections;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getIpv4() {
        return ipv4;
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }

    public String getIpv6() {
        return ipv6;
    }

    public void setIpv6(String ipv6) {
        this.ipv6 = ipv6;
    }

    public String getRx() {
        return rx;
    }

    public void setRx(String rx) {
        this.rx = rx;
    }

    public String getTx() {
        return tx;
    }

    public void setTx(String tx) {
        this.tx = tx;
    }

    public String getRx_gap() {
        return rx_gap;
    }

    public void setRx_gap(String rx_gap) {
        this.rx_gap = rx_gap;
    }

    public String getTx_gap() {
        return tx_gap;
    }

    public void setTx_gap(String tx_gap) {
        this.tx_gap = tx_gap;
    }

    public String getLoad() {
        return load;
    }

    public void setLoad(String load) {
        this.load = load;
    }

    public String getLoad_cpu() {
        return load_cpu;
    }

    public void setLoad_cpu(String load_cpu) {
        this.load_cpu = load_cpu;
    }

    public String getLoad_io() {
        return load_io;
    }

    public void setLoad_io(String load_io) {
        this.load_io = load_io;
    }

    public String getPing_eu() {
        return ping_eu;
    }

    public void setPing_eu(String ping_eu) {
        this.ping_eu = ping_eu;
    }

    public String getPing_us() {
        return ping_us;
    }

    public void setPing_us(String ping_us) {
        this.ping_us = ping_us;
    }

    public String getPing_as() {
        return ping_as;
    }

    public void setPing_as(String ping_as) {
        this.ping_as = ping_as;
    }
}
