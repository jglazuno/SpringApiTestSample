package com.iu.spring.rest.nasa.sounds.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iu.spring.rest.nasa.sounds.model.Sound;
import com.iu.spring.rest.nasa.sounds.service.SoundService;

@RestController
@RequestMapping("/sounds")
public class SoundController {

  @Autowired
  private SoundService soundService;

  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<List<Sound>> get(
      @RequestParam(value = "api_key", defaultValue = "DEMO_KEY") String key,
      @RequestParam(value = "q", required = false) String q,
      @RequestParam(value = "limit", defaultValue = "10") int limit) throws ForbiddenApiKey {

    if (!soundService.isKeyValid(key)) {
      throw new ForbiddenApiKey();
    }
    
    List<Sound> sounds = soundService.getSounds(key, q, limit);

    if (sounds == null || sounds.isEmpty()) {

      return new ResponseEntity<List<Sound>>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<List<Sound>>(sounds, HttpStatus.OK);
  }

}
