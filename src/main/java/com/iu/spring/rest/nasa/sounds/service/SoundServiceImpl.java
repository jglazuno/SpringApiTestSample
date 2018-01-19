package com.iu.spring.rest.nasa.sounds.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.iu.spring.rest.nasa.sounds.model.Sound;

@Service
public class SoundServiceImpl implements SoundService {

  private static final AtomicInteger counter = new AtomicInteger();
  static List<Sound> sounds = new ArrayList<Sound>(/* some sounds here */);


  public List<Sound> getSounds(String apiKey, String searchString, int limit) {
    // TODO: add realization :)
    return sounds;
  }

  public boolean isKeyValid(String apiKey) {
    if (apiKey.equals("DEMO_KEY"))
      return true;

    return false;
  }
}
