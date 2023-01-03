package com.example.C13_S3_MC_1.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "Track With this id does not Exists")
public class TrackNotFoundException extends Exception{
    public TrackNotFoundException(){
        super();
    }
}
