package com.iu.spring.rest.nasa.sounds.service;

import com.iu.spring.rest.nasa.sounds.controller.UnableProcessRequestException;
import com.iu.spring.rest.nasa.sounds.model.Sound;
import com.iu.spring.rest.nasa.sounds.test.service.ResponsePage;

public interface SoundService {

  ResponsePage<Sound> getSounds(String apiKey, String searchString, int limit)
      throws UnableProcessRequestException;

  boolean isKeyValid(String apiKey);

}
