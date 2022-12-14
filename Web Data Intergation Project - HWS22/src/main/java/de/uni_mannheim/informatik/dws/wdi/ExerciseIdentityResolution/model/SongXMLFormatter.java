package de.uni_mannheim.informatik.dws.wdi.ExerciseIdentityResolution.model;
import edu.stanford.nlp.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import de.uni_mannheim.informatik.dws.winter.model.io.XMLFormatter;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SongXMLFormatter extends XMLFormatter<Song> {
    public Element createRootElement(Document doc) {
        return doc.createElement("Songs");
    }


    @Override
    public Element createElementFromRecord(Song record, Document doc) {
        Element song = doc.createElement("song");

        String str_album_genres = convertArrayToString(record.getAlbum_genres());

        String str_artists = String.join(",", Arrays.stream(record.getArtists()).map(x -> StringUtils.capitalize(x)).collect(Collectors.toList()));

        song.appendChild(createTextElement("id", record.getIdentifier(), doc));

        song.appendChild(createTextElementWithProvenance("track_name",
                record.getTrack_name(),
                record.getMergedAttributeProvenance(Song.Track_Name), doc));
        song.appendChild(createTextElementWithProvenance("album_name",
                record.getAlbum_name(),
                record.getMergedAttributeProvenance(Song.Album_Name), doc));
        song.appendChild(createTextElementWithProvenance("release_date",
                record.getRelease_date().toLocalDate().toString(),
                record.getMergedAttributeProvenance(Song.Release_Date), doc));

        song.appendChild(createTextElementWithProvenance("artists",str_artists , record
                .getMergedAttributeProvenance(Song.Artists), doc));

        song.appendChild(createTextElementWithProvenance("album_genres", str_album_genres, record
                .getMergedAttributeProvenance(Song.Album_Genres), doc));

        song.appendChild(createTextElementWithProvenance("album_type",
                record.getAlbum_type(),
                record.getMergedAttributeProvenance(Song.Album_Type), doc));

        song.appendChild(createTextElementWithProvenance("tempo", Float.toString(Math.round(record.getTempo()))
                ,record
                .getMergedAttributeProvenance(Song.Tempo), doc));

        song.appendChild(createTextElementWithProvenance("duration", Integer.toString(record.getDuration())
                ,record
                        .getMergedAttributeProvenance(Song.Duration), doc));

        return song;
    }

    protected Element createTextElementWithProvenance(String name,
                                                      String value, String provenance, Document doc) {
        Element elem = createTextElement(name, value, doc);
        elem.setAttribute("provenance", provenance);
        return elem;
    }

    protected  String convertArrayToString(String[] arr){
        String res = "";

        for(int i = 0; i< arr.length; i++){
            if(arr[i]!="" && (arr[i].length()>1)){
                res += arr[i]+", ";}
        }

        if(res.isEmpty()){
            return res;
        }
        else{
            return res.substring(0, res.length()-2);
        }
    }

}
