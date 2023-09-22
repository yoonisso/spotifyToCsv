package Client;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import POJO.Artist;
import POJO.LimitOffset;
import POJO.Playlist;
import POJO.Song;
import Util.ConfigLoader;

import org.json.JSONException;

public class SpotifyClient {
    private final String ACCESS_TOKEN;
    private String spotifyUserName;

    private HttpClient httpClient;

    private final String ACCESS_TOKEN_REQUEST_URL = "https://accounts.spotify.com/api/token";
    private final String SPOTIFY_USER_URL_PREFIX = "https://api.spotify.com/v1/users/";
    private final String SPOTIFY_PLAYLIST_URL_PREFIX = "https://api.spotify.com/v1/playlists/";

    private final String ACCESS_TOKEN_SEARCH_KEY = "access_token";

    public SpotifyClient(){
        this.ACCESS_TOKEN = createAccessToken();
        this.httpClient = new HttpClient();
    }

    public void setSpotifyUserName(String name){
        if(checkIfSpotifyUserExists(name)){
            this.spotifyUserName = name;
        }
    }

    public String getSpotifyUserName(){
        return this.spotifyUserName;
    }

    private String createAccessToken(){
        try{
            JSONObject response = new JSONObject(httpClient.sendPostRequest(ACCESS_TOKEN_REQUEST_URL, "grant_type=client_credentials&client_id="+ConfigLoader.getClientId()+"&client_secret="+ConfigLoader.getClientSecret()));
            return response.getString(ACCESS_TOKEN_SEARCH_KEY);
        }catch(JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    public Boolean checkIfSpotifyUserExists(String name){
        if(httpClient.sendGetRequest(SPOTIFY_USER_URL_PREFIX + name, this.ACCESS_TOKEN) == null){
            return false;
        }
        return true;
    }

    private JSONObject sendSpotifyGetRequest(String url){
        return new JSONObject(httpClient.sendGetRequest(url, this.ACCESS_TOKEN));
    }

    public List<Playlist> getAllPlaylists(){
        JSONObject response = sendSpotifyGetRequest(SPOTIFY_USER_URL_PREFIX + this.spotifyUserName + "/playlists");
        int totalNumberOfPlaylists = response.getInt("total");
        List<LimitOffset> limitOffsets = getLimitAndOffsetFromTotal(totalNumberOfPlaylists);

        List<Playlist> playlists = new ArrayList<Playlist>();

        for(LimitOffset limitOffset : limitOffsets){
            response = sendSpotifyGetRequest(SPOTIFY_USER_URL_PREFIX + this.spotifyUserName + "/playlists?limit="+limitOffset.getLimit()+"&offset=" +limitOffset.getOffset());
            JSONArray itemsArray = response.getJSONArray("items");

            for(int i = 0; i < itemsArray.length(); i++){
                JSONObject playlistObject = itemsArray.getJSONObject(i);
                playlists.add(new Playlist(playlistObject.getString("name"), playlistObject.getString("id")));
            }
        }
        return playlists;
    }

    public List<Song> getAllSongsOfPlaylist(Playlist playlist){
        JSONObject response = sendSpotifyGetRequest(SPOTIFY_PLAYLIST_URL_PREFIX + playlist.getId() +"/tracks?limit=1");
        int totalNumberOfSongs = response.getInt("total");
        List<LimitOffset> limitOffsets = getLimitAndOffsetFromTotal(totalNumberOfSongs);

        List<Song> songList = new ArrayList<>();
        for(LimitOffset limitOffset : limitOffsets){
            response = sendSpotifyGetRequest(SPOTIFY_PLAYLIST_URL_PREFIX + playlist.getId() +"/tracks?limit=" +limitOffset.getLimit()+"&offset="+limitOffset.getOffset());
            JSONArray itemsArray = response.getJSONArray("items");

            for(int i = 0; i < itemsArray.length(); i++){
                try{
                    JSONObject itemObject = itemsArray.getJSONObject(i);
                    JSONObject trackObject = itemObject.getJSONObject("track");
                    String trackName = trackObject.getString("name");

                    JSONArray artistsArray = trackObject.getJSONArray("artists");
                    List<Artist> artistList = new ArrayList<Artist>();
                    for(int j = 0; j < artistsArray.length(); j++){
                        JSONObject artistObject = artistsArray.getJSONObject(j);
                        String artistName = artistObject.getString("name");
                        artistList.add(new Artist(artistName));
                    }
                    songList.add(new Song(trackName, artistList));
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }
        return songList;
    }

    public static List<LimitOffset> getLimitAndOffsetFromTotal(int total){
        List<LimitOffset> limitOffsetList= new ArrayList<>();
        int numberOfNeededApiCalls = (total/50) + 1;
       
        if(numberOfNeededApiCalls <= 1){
            limitOffsetList.add(new LimitOffset("50","0"));
            return limitOffsetList;
        }

        for(int i = 1; i<= numberOfNeededApiCalls; i++){
            limitOffsetList.add(new LimitOffset("50",Integer.toString((i-1)*50)));
        }
        return limitOffsetList;
    }
}