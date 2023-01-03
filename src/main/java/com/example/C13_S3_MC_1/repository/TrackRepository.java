package com.example.C13_S3_MC_1.repository;

import com.example.C13_S3_MC_1.domain.Track;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends MongoRepository<Track,Integer> {

    @Query("{trackRating:{$gt:?0}}")
    public List<Track> getAllTracksByRating(int trackRating);

    @Query("{'trackArtist.artistName':{$in:[?0]}}")
    public List<Track> getAllTracksByArtistName(String trackArtist);
}
