package com.iu.spring.rest.nasa.sounds.test.service;

import java.io.IOException;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.iu.spring.rest.nasa.sounds.model.Sound;

/**
 * A service to operate with AXS API
 * <p>
 * Contains typified CRUD methods. Uses enumeration {@link Endpoint} with known endpoints to define
 * how to operate with specific objects.
 * 
 * @param <P>
 */
public class TestSoundApiService {
  protected HttpClient httpClient = HttpClients.createDefault();
  protected ResponseHandler<String> responseHandler = new BasicResponseHandler();
  protected Gson gson = new GsonBuilder().create();


  public ResponsePage<Sound> getEntities(NameValuePair... parameters) throws Exception {

    String rs;
    try {

      HttpUriRequest rq =
          RequestBuilder.get("https://api.nasa.gov/planetary/sounds").addParameters(parameters)
              .build();

      rs = httpClient.execute(rq, responseHandler);
    } catch (IOException e) {
      throw new Exception(e.getMessage(), e);
    }

    return gson.fromJson(rs, new TypeToken<ResponsePage<Sound>>() {}.getType());
  }


}
