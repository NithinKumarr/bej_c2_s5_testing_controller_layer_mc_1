package com.example.C13_S3_MC_1.serviceTest;

import com.example.C13_S3_MC_1.domain.Artist;
import com.example.C13_S3_MC_1.domain.Track;
import com.example.C13_S3_MC_1.exception.TrackAlreadyExistsException;
import com.example.C13_S3_MC_1.exception.TrackNotFoundException;
import com.example.C13_S3_MC_1.repository.TrackRepository;
import com.example.C13_S3_MC_1.service.TrackService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TrackServiceTest {
    @Mock
    TrackRepository trackRepository;
    @InjectMocks
    TrackService trackService;
    private List<Track> trackList;
    private Track track1;
    private Track track2;
    private Track track3;
    private Artist artist1;
    private Artist artist2;
    private Artist artist3;

    @BeforeEach
    public void setUp() {
        this.artist1 = new Artist(1001, "artist1");
        this.track1 = new Track(101, "track1", 3, artist1);

        this.artist2 = new Artist(1002, "artist2");
        this.track2 = new Track(102, "track2", 5, artist2);

        this.artist3 = new Artist(1003, "artist1");
        this.track3 = new Track(103, "track2", 6, artist3);

        trackList=new ArrayList<>();
        trackList.add(track1);
        trackList.add(track2);
        trackList.add(track3);
    }

    @Test
    public void givenTrackToSaveReturnTrackSuccess() throws TrackAlreadyExistsException {
        when(trackRepository.findById(track1.getTrackId())).thenReturn(Optional.ofNullable(null));
        when(trackRepository.save(track1)).thenReturn(track1);
       assertEquals(track1,trackService.addTrack(track1));
    }
    @Test
    public void givenTrackToSaveReturnTrackFailure() throws TrackAlreadyExistsException {
        when(trackRepository.findById(track1.getTrackId())).thenReturn(Optional.ofNullable(track1));
       assertThrows(TrackAlreadyExistsException.class,()->trackService.addTrack(track1));
    }

    @Test
    public void givenTrackToDeleteReturnTrueSuccess() throws TrackNotFoundException {
        when(trackRepository.findById(track1.getTrackId())).thenReturn(Optional.ofNullable(track1));
        boolean b=trackService.deleteTrack(track1.getTrackId());
        assertEquals(true,b);
    }
    @Test
    public void givenTrackToDeleteReturnTrueFailure() throws TrackNotFoundException {
        when(trackRepository.findById(track1.getTrackId())).thenReturn(Optional.ofNullable(null));
       assertThrows(TrackNotFoundException.class,()->trackService.deleteTrack(track1.getTrackId()));
    }
    @Test
    public void givenTrackReturnAllTracksSuccess(){
        when(trackRepository.findAll()).thenReturn(trackList);
        assertEquals(3,trackService.getAllTrack().size());
    }
    @Test
    public void givenTrackReturnAllTracksFailure(){
        when(trackRepository.findAll()).thenReturn(trackList);
        assertNotEquals(5,trackService.getAllTrack().size());
    }

    @Test
    public void givenArtistNameShouldReturnTrackListSuccess(){
        List<Track> newTrack=new ArrayList<>();

        Iterator<Track> iterator=trackList.iterator();
        while (iterator.hasNext()){
            Track track=iterator.next();
            if(track.getTrackArtist().artistName.equals("artist1")){
                newTrack.add(track);
            }
        }

        when(trackRepository.getAllTracksByArtistName("artist1")).thenReturn(newTrack);
        List<Track> list1=new ArrayList<>();
        list1=trackService.getTrackListByArtistName("artist1");

        assertEquals(newTrack,list1);
    }
    @Test
    public void givenArtistNameShouldReturnTrackListFailure(){
        List<Track> newTrack=new ArrayList<>();

        Iterator<Track> iterator=trackList.iterator();
        while (iterator.hasNext()){
            Track track=iterator.next();
            if(track.getTrackArtist().artistName.equals("artist1")){
                newTrack.add(track);
            }
        }

        when(trackRepository.getAllTracksByArtistName("artist1")).thenReturn(newTrack);
        List<Track> list1=new ArrayList<>();
        list1=trackService.getTrackListByArtistName("artist1");

        assertNotEquals(trackList,list1);
    }

    @Test
    public void givenRatingShouldReturnTrackListSuccess(){
        List<Track> newTrack=new ArrayList<>();

        Iterator<Track> iterator=trackList.iterator();
        while (iterator.hasNext()){
            Track track=iterator.next();
            if(track.getTrackRating()>4){
                newTrack.add(track);
            }
        }

        when(trackRepository.getAllTracksByRating(4)).thenReturn(newTrack);
        List<Track> list1=new ArrayList<>();
        list1=trackService.getTrackListByRating(4);

        assertEquals(newTrack,list1);
    }

    @Test
    public void givenRatingShouldReturnTrackListFailure(){
        List<Track> newTrack=new ArrayList<>();

        Iterator<Track> iterator=trackList.iterator();
        while (iterator.hasNext()){
            Track track=iterator.next();
            if(track.getTrackRating()>4){
                newTrack.add(track);
            }
        }

        when(trackRepository.getAllTracksByRating(4)).thenReturn(newTrack);
        List<Track> list1=new ArrayList<>();
        list1=trackService.getTrackListByRating(4);

       assertNotEquals(trackList,list1);
    }
    @AfterEach
    public void tearDown(){
        this.artist1=null;
        this.track1=null;

        this.artist2=null;
        this.track2=null;

        this.artist3=null;
        this.track3=null;
    }
}
