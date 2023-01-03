package com.example.C13_S3_MC_1.controllerTest;

import com.example.C13_S3_MC_1.controller.TrackController;
import com.example.C13_S3_MC_1.domain.Artist;
import com.example.C13_S3_MC_1.domain.Track;
import com.example.C13_S3_MC_1.exception.TrackAlreadyExistsException;
import com.example.C13_S3_MC_1.exception.TrackNotFoundException;
import com.example.C13_S3_MC_1.service.TrackService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TrackControllerTest {

    @Mock
    TrackService trackService;

    @InjectMocks
    TrackController trackController;

    @Autowired
    MockMvc mockMvc;

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

        mockMvc= MockMvcBuilders.standaloneSetup(trackController).build();
    }

    @Test
    public void givenTrackShouldBeSavedSuccess() throws Exception {
        when(trackService.addTrack(any())).thenReturn(track1);
        mockMvc.perform(post("/api/v1/track").
                contentType(MediaType.APPLICATION_JSON).
                content(convertJsonToString(track1))).andExpect(status().isCreated()).
                andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void givenTrackShouldBeSavedFailure() throws Exception {
        when(trackService.addTrack(any())).thenThrow(TrackAlreadyExistsException.class);
        mockMvc.perform(post("/api/v1/track").
                        contentType(MediaType.APPLICATION_JSON).
                        content(convertJsonToString(track1))).andExpect(status().isConflict()).
                andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void givenTrackIdShouldDeleteSuccess() throws Exception {
        when(trackService.deleteTrack(anyInt())).thenReturn(true);
        mockMvc.perform(delete("/api/v1/track/1001").
                contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void givenTrackIdShouldDeleteFailure() throws Exception {
        when(trackService.deleteTrack(anyInt())).thenThrow(TrackNotFoundException.class);
        mockMvc.perform(delete("/api/v1/track/1007").
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isNotFound()).
                andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void givenTrackShouldReturnAllTheTracks() throws Exception {
        when(trackService.getAllTrack()).thenReturn(trackList);
        mockMvc.perform(get("/api/v1/tracks").
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void givenTrackRatingReturnTracks() throws Exception {
        when(trackService.getTrackListByRating(anyInt())).thenReturn(trackList);
        mockMvc.perform(get("/api/v1/tracks/4").
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void givenArtistNameReturnTracks() throws Exception {
        when(trackService.getTrackListByArtistName("artist1")).thenReturn(trackList);
        mockMvc.perform(get("/api/v1/track/artist1").
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andDo(MockMvcResultHandlers.print());
    }
    public static String convertJsonToString(Object object){
        String result;
        ObjectMapper objectMapper=new ObjectMapper();
        try {
           String jsonContent= objectMapper.writeValueAsString(object);
           result=jsonContent;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            result="json parse error";
        }
        return result;
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
