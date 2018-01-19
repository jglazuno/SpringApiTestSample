package com.iu.spring.rest.nasa.sounds.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason="Wrong api_key was supplied. Get correct one at https://api.nasa.gov")
public class ForbiddenApiKey extends Exception {

}
