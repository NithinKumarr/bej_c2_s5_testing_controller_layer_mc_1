package com.example.C13_S3_MC_1.controller;

import com.example.C13_S3_MC_1.domain.Track;
import com.example.C13_S3_MC_1.exception.TrackAlreadyExistsException;
import com.example.C13_S3_MC_1.exception.TrackNotFoundException;
import com.example.C13_S3_MC_1.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class TrackController {
    TrackService trackService;

    @Autowired
    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @PostMapping("/track")
    public ResponseEntity<?> addTrack(@RequestBody Track track) throws TrackAlreadyExistsException {
        return new ResponseEntity<>(trackService.addTrack(track), HttpStatus.CREATED);
    }

    @GetMapping("/tracks")
    public ResponseEntity<?> getAllTheTracks(){
        return new ResponseEntity<>(trackService.getAllTrack(), HttpStatus.OK);
    }

    @DeleteMapping("/track/{id}")
    public ResponseEntity<?> deleteTrack(@PathVariable int id) throws TrackNotFoundException {
        return new ResponseEntity<>(trackService.deleteTrack(id),HttpStatus.OK);
    }

    @GetMapping("tracks/{rating}")
    public ResponseEntity<?> getTrackByRating(@PathVariable int rating)  {
        return new ResponseEntity<>(trackService.getTrackListByRating(rating),HttpStatus.OK);
    }
    @GetMapping("track/{name}")
    public ResponseEntity<?> getTrackByArtist(@PathVariable  String name){
        return new ResponseEntity<>(trackService.getTrackListByArtistName(name),HttpStatus.OK);
    }
}