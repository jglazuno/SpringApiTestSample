package com.iu.spring.rest.nasa.sounds.test.service2;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClients;

/**
 * A service to operate with API
 * <p>
 * Contains typified CRUD methods. Uses enumeration {@link Endpoint} with known endpoints to define
 * how to operate with specific objects.
 * 
 * @param <P>
 */
public class TestSoundApiService {
  protected HttpClient httpClient = HttpClients.createDefault();
  protected ResponseHandler<String> responseHandler = new BasicResponseHandler();

  public HttpResponse get(NameValuePair... parameters) throws ClientProtocolException, IOException {

    HttpUriRequest rq = RequestBuilder.get("https://api.nasa.gov/planetary/sounds")
        .addParameters(parameters).build();

    return httpClient.execute(rq);
  }


}
