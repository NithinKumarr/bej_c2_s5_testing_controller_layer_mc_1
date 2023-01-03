package com.example.C13_S3_MC_1.service;

import com.example.C13_S3_MC_1.domain.Track;
import com.example.C13_S3_MC_1.exception.TrackAlreadyExistsException;
import com.example.C13_S3_MC_1.exception.TrackNotFoundException;

import java.util.List;

public interface ITrackService {
    public Track addTrack(Track track) throws TrackAlreadyExistsException;
    public List<Track> getAllTrack();
    public boolean deleteTrack(int trackId) throws TrackNotFoundException;
    public List<Track> getTrackListByRating(int trackRating);
    public List<Track> getTrackListByArtistName(String artistName);
}
