package com.example.C13_S3_MC_1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT,reason = "Track Already exists")
public class TrackAlreadyExistsException extends Exception{
    public TrackAlreadyExistsException(){
        super();
    }
}
