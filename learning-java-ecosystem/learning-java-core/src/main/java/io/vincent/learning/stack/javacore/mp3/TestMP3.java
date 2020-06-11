package io.vincent.learning.stack.javacore.mp3;

import java.io.File;
import java.util.HashMap;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.id3.AbstractID3v2Frame;

public class TestMP3 {

    /**
     * 获取歌曲信息
     *
     * @param file 歌曲文件
     * @return 歌曲信息
     */
    public Music getSongInfo(File file) {

        Music music = null;

        try {
            MP3File mp3File = (MP3File) AudioFileIO.read(file);
            MP3AudioHeader audioHeader = (MP3AudioHeader) mp3File.getAudioHeader();

            HashMap<String, AbstractID3v2Frame> frameMap = mp3File.getID3v2Tag().frameMap;

            // 歌名 TIT2
            String songName = getInfo(frameMap, "TIT2");
            // 歌手 TPE1
            String artist = getInfo(frameMap, "TPE1");
            // 专辑 TALB
            String album = getInfo(frameMap, "TALB");
            // 年份 TYER
            String year = getInfo(frameMap, "TYER");
            // pic APIC
            String apic = getInfo(frameMap, "APIC");
            // 其他 TXXX
            String txxx = getInfo(frameMap, "TXXX");
            // 时长
            int duration = audioHeader.getTrackLength();//

            music = new Music(songName, artist, album, year, apic, txxx, duration, file.getPath());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return music;
    }

    public String getInfo(HashMap<String, AbstractID3v2Frame> frameMap, String key) {
        String value = "";
        try {
            if (frameMap.get(key) == null) return value;
            if (frameMap.get(key).getBody() == null) return value;

            value = frameMap.get(key).getContent();
        } catch (Exception e) {
            e.printStackTrace();
            return value;
        }
        return value;
    }

    // 去除不必要的字符串
    private String reg(String input) {
        if (input == null || input.isEmpty()) return "";
        return input.substring(input.indexOf('"') + 1, input.lastIndexOf('"'));
    }
}

