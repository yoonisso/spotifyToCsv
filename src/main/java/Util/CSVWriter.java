package Util;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import POJO.Artist;
import POJO.Playlist;
import POJO.Song;

public class CSVWriter{
    public static void writePlaylistToCSV(List<Playlist> playlists, String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath);
        CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT)) {
            for (Playlist playlist : playlists) {
                csvPrinter.print(playlist.getName());
                csvPrinter.println();
                for (Song song : playlist.getSongList()){
                    csvPrinter.print(song.getName());
                    for (Artist artist : song.getArtistList()){
                        csvPrinter.print(artist.getName());
                    }
                    csvPrinter.println();;
                }
                csvPrinter.println();
            }
            csvPrinter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
