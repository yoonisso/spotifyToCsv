package POJO;
import java.util.Arrays;
import java.util.List;

public class Song{
    private String name;
    private List<Artist> artistList;

    public Song(String name, List<Artist> artistList){
        this.name = name;
        this.artistList = artistList;
    }

    public Song(String name, String artist){
        this.name = name;
        this.artistList = Arrays.asList(new Artist(artist));
    }

    public String getName(){
        return name;
    }

    public List<Artist> getArtistList(){
        return artistList;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setArtist(List<Artist> artistList){
        this.artistList = artistList;
    }
}
