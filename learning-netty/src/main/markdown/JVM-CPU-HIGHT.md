

## 引子

*. ***某个线程CPU占用过高。***

### 1. 查看系统状况

top 
ps

目的：找出 pid 42057

### 2. 定位问题线程

ps -mp 42057 -o "user, %cpu, stime, pid,"

定位到某个线程

### 3. jstack查看线程堆栈信息

printf "%x\n" 42057

jstack 42057 | grep a449

### 4. jstat查看进程内存状况

jstat -gcutil 42057

### 5. jstack 和 jmap 分析进程堆栈和内存状况

使用jmap命令导出heapdump文件，然后拿到本地使用jvisualvm.exe分析。

命令: jmap [option] vmid 
jmap -dump:format=b,file=dump.bin 6764

命令: jstack [option] vmid 
jstack -l 6764 >> jstack.out