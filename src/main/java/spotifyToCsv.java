import java.util.Scanner;

import Client.SpotifyClient;
import POJO.Playlist;
import Util.CSVWriter;

import java.util.List;

public class spotifyToCsv{
    public static final String FILE_OUTPUT_PATH = "data/output.csv";
    public static void main(String[] args){
        String spotifyUserName = readName();

        SpotifyClient spotifyClient = new SpotifyClient();
        spotifyClient.setSpotifyUserName(spotifyUserName);
        if(spotifyClient.getSpotifyUserName() == null){
            System.out.println("User with name: " + spotifyUserName + " does not exist!");
            System.exit(1);
        }

        List<Playlist> playlists = spotifyClient.getAllPlaylists();

        for (int i=0; i<playlists.size(); i++){
            playlists.get(i).setSongList(spotifyClient.getAllSongsOfPlaylist(playlists.get(i)));
        }
        
        if(!playlists.isEmpty()){
            CSVWriter.writePlaylistToCSV(playlists, FILE_OUTPUT_PATH);
        }
    }

    private static String readName(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- Enter Spotify-User ---");
        String name = scanner.nextLine();
        scanner.close();
        return name;
    }
}