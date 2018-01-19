package com.iu.spring.rest.nasa.sounds.service;

import java.util.List;

import com.iu.spring.rest.nasa.sounds.model.Sound;

public interface SoundService {

  List<Sound> getSounds(String apiKey, String searchString, int limit);

  boolean isKeyValid(String apiKey);

}
