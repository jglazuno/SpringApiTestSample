package com.iu.spring.rest.nasa.sounds.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Unable to process request")
public class UnableProcessRequestException extends Exception {

}
