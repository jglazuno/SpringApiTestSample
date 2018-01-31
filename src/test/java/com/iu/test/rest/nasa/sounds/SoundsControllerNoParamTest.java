package com.iu.test.rest.nasa.sounds;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.iu.spring.rest.nasa.sounds.model.Sound;
import com.iu.spring.rest.nasa.sounds.test.service.ResponsePage;
import com.iu.spring.rest.nasa.sounds.test.service2.TestSoundApiService;

@Test(groups = {"BB"})
public class SoundsControllerNoParamTest {

  private TestSoundApiService tsas = new TestSoundApiService();
  protected Gson gson = new GsonBuilder().create();

  @Test(description = "No params provided")
  public void noParametersTest() throws Exception {

    HttpResponse httpResponse = tsas.get();

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_OK);
    softly.assertThat(ContentType.getOrDefault(httpResponse.getEntity()).getMimeType())
        .isEqualTo("application/json");
    softly.assertAll();

    ResponsePage<Sound> rp = gson.fromJson(httpResponse.getEntity().toString(),
        new TypeToken<ResponsePage<Sound>>() {}.getType());

    SoftAssertions softly2 = new SoftAssertions();
    softly2.assertThat(rp.count).isEqualTo(10);
    softly2.assertThat(rp.results).hasSize(10);
    softly2.assertAll();

  }
}
