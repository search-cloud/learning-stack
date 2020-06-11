package io.vincent.learning.stack.javacore.mp3;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Music {

    private int id;// 音乐编号
    private String name;// 音乐名
    private String singer;// 歌手名
    private String album;// 专辑名
    private String year;// 年份
    private String pic;// 专辑封面
    private String others;// 其他信息
    private int duration;// 时长,以秒为单位
    private String path;// 音乐文件存放路径

    public Music(){ }

    public Music(String name, String singer, String album, int duration, String path) {
        this.name = name;
        this.singer = singer;
        this.album = album;
        this.duration = duration;
        this.path = path;
    }

    public Music(int id, String name, String singer, String album, int duration, String path) {

        this.id = id;
        this.name = name;
        this.singer = singer;
        this.album = album;
        this.duration = duration;
        this.path = path;
    }

    public Music(String name, String singer, String album, String year, String pic, String others, int duration, String path) {
        this.name = name;
        this.singer = singer;
        this.album = album;
        this.year = year;
        this.pic = pic;
        this.others = others;
        this.duration = duration;
        this.path = path;
    }

    @Override
    public String toString() {
        return "Music{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", singer='" + singer + '\'' +
                ", album='" + album + '\'' +
                ", year='" + year + '\'' +
                ", pic='" + pic + '\'' +
                ", duration=" + duration +
                ", others=" + others +
                ", path='" + path + '\'' +
                '}';
    }
}

