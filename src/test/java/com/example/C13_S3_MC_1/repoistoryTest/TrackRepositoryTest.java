package com.example.C13_S3_MC_1.repoistoryTest;

import com.example.C13_S3_MC_1.domain.Artist;
import com.example.C13_S3_MC_1.domain.Track;
import com.example.C13_S3_MC_1.repository.TrackRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class TrackRepositoryTest {
    @Autowired
    TrackRepository trackRepository;
    private Track track1;
    private Track track2;
    private Track track3;
    private Artist artist1;
    private Artist artist2;
    private Artist artist3;

    @BeforeEach
    public void setUp(){
        this.artist1=new Artist(1001,"artist1");
        this.track1=new Track(101,"track1",3,artist1);

        this.artist2=new Artist(1002,"artist2");
        this.track2=new Track(102,"track2",5,artist2);

        this.artist3=new Artist(1003,"artist1");
        this.track3=new Track(103,"track2",6,artist3);

    }

    @Test
    public void givenTrackShouldSaveAndReturnTrack(){
        trackRepository.save(track1);
        Track track4=trackRepository.findById(track1.getTrackId()).get();
        assertEquals(track4,track1);
    }
    @Test
    public void givenTrackShouldDeleteReturnTrue(){
       trackRepository.save(track1);
       Track track4=trackRepository.findById(track1.getTrackId()).get();
       trackRepository.delete(track1);
       assertEquals(Optional.empty(),trackRepository.findById(track1.getTrackId()));
    }

    @Test
    public void givenTrackReturnAllTracks(){
        trackRepository.deleteAll();
        trackRepository.save(track1);
        trackRepository.save(track2);
        trackRepository.save(track3);
        assertEquals(3,trackRepository.findAll().size());
    }

    @Test
    public void givenArtistNameShouldReturnTracks(){
        trackRepository.deleteAll();
        trackRepository.save(track1);
        trackRepository.save(track2);
        trackRepository.save(track3);
        assertEquals(2,trackRepository.getAllTracksByArtistName("artist1").size());
    }
    @Test
    public void givenRatingShouldReturnTracks(){
        trackRepository.deleteAll();
        trackRepository.save(track1);
        trackRepository.save(track2);
        trackRepository.save(track3);
        assertEquals(2,trackRepository.getAllTracksByRating(4).size());
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
