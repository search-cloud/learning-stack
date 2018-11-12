基于IDEA环境下的Spark2.X程序开发
1.Windows开发环境配置与安装

下载IDEA并安装，可以百度一下免费文档。

2.IDEA Maven工程创建与配置

1）配置maven



2）新建Project项目



3）选择maven骨架



4）创建项目名称



5）选择maven地址



6）生成maven项目



7）选择scala版本



8）新建Java 和 scala目录



9）编辑pom.xml文件

a）地址一

b）地址二

3.开发Spark Application程序并进行本地测试

1）idea编写WordCount程序

package com.spark.test
import org.apache.spark.{SparkConf, SparkContext}
object MyScalaWordCout {
  def main(args: Array[String]): Unit = {
    //参数检查
    if (args.length < 2) {
      System.err.println("Usage: MyWordCout
  ")
      System.exit(1)
    }
    //获取参数
    val input=args(0)
    val output=args(1)
    //创建scala版本的SparkContext
    val conf=new SparkConf().setAppName("myWordCount")
    val sc=new SparkContext(conf)
    //读取数据
    val lines=sc.textFile(input)
    //进行相关计算
    val resultRdd=lines.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_)
    //保存结果
    resultRdd.saveAsTextFile(output)
    sc.stop()
  }
}
4.Spark Application程序打包

1）项目打jar包，参考之前讲过的项目打包方式

2）spark-submit方式提交作业

bin/spark-submit --master local[2] /opt/jars/sparkStu.jar hdfs://bigdata-pro01.kfk.com:9000/user/data/stu.txt hdfs://bigdata-pro01.kfk.com:9000/user/data/output