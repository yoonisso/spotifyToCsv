package POJO;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

public class Playlist implements Serializable{
    private String name;
    private String id;
    private List<Song> songList;

    public Playlist(String name, String id, List<Song> songList){
        this.name = name;
        this.id = id;
        this.songList = songList;
    }

    public Playlist(String name, String id){
        this.name = name;
        this.id = id;
        songList = new ArrayList<>();
    }

    public Playlist(){
        
    }

    public String getName(){
        return name;
    }

    public String getId(){
        return id;
    }

    public List<Song> getSongList(){
        return songList;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setSongList(List<Song> songList){
        this.songList = songList;
    }
}
