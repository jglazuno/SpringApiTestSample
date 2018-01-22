package com.iu.spring.rest.nasa.sounds.service;

import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import com.iu.spring.rest.nasa.sounds.controller.UnableProcessRequestException;
import com.iu.spring.rest.nasa.sounds.model.Sound;
import com.iu.spring.rest.nasa.sounds.test.service.ResponsePage;
import com.iu.spring.rest.nasa.sounds.test.service.TestSoundApiService;

@Service
public class SoundServiceImpl implements SoundService {

  private TestSoundApiService tsas = new TestSoundApiService();

  public ResponsePage<Sound> getSounds(String apiKey, String searchString, int limit)
      throws UnableProcessRequestException {

    ResponsePage<Sound> results;

    try {
      if (searchString != null) {
        results = tsas.getEntities(new BasicNameValuePair("api_key", apiKey),
            new BasicNameValuePair("q", searchString),
            new BasicNameValuePair("limit", String.valueOf(limit)));
      } else {
        results = tsas.getEntities(new BasicNameValuePair("api_key", apiKey),
            new BasicNameValuePair("limit", String.valueOf(limit)));
      }
    } catch (Exception e) {
      throw new UnableProcessRequestException();
    }

    return results;
  }

  public boolean isKeyValid(String apiKey) {
    if (apiKey.equals("DEMO_KEY"))
      return true;

    return false;
  }
}
