package com.example.C13_S3_MC_1.service;

import com.example.C13_S3_MC_1.domain.Track;
import com.example.C13_S3_MC_1.exception.TrackAlreadyExistsException;
import com.example.C13_S3_MC_1.exception.TrackNotFoundException;
import com.example.C13_S3_MC_1.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TrackService implements ITrackService{

    TrackRepository trackRepository;
    @Autowired
    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Override
    public Track addTrack(Track track) throws TrackAlreadyExistsException {
        if(trackRepository.findById(track.getTrackId()).isEmpty()){
            return trackRepository.save(track);
        }
        throw new TrackAlreadyExistsException();
    }

    @Override
    public List<Track> getAllTrack() {
        return trackRepository.findAll();
    }

    @Override
    public boolean deleteTrack(int trackId) throws TrackNotFoundException {
        if (trackRepository.findById(trackId).isEmpty()){
            throw new TrackNotFoundException();
        }
        trackRepository.deleteById(trackId);
        return true;
    }

    @Override
    public List<Track> getTrackListByRating(int trackRating) {
        return trackRepository.getAllTracksByRating(trackRating);
    }

    @Override
    public List<Track> getTrackListByArtistName(String artistName) {
        return trackRepository.getAllTracksByArtistName(artistName);
    }
}
