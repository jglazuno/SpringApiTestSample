package com.iu.test.rest.nasa.sounds;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.iu.spring.rest.nasa.sounds.model.Sound;
import com.iu.spring.rest.nasa.sounds.test.service.ResponsePage;
import com.iu.spring.rest.nasa.sounds.test.service2.ErrorResponsePage;
import com.iu.spring.rest.nasa.sounds.test.service2.TestSoundApiService;

@Test(groups = {"BB"})
public class SoundsControllerApiKeyTest {

  private TestSoundApiService tsas = new TestSoundApiService();
  protected Gson gson = new GsonBuilder().create();

  @DataProvider(name = "API key invalid")
  public Object[][] entriesKeyInvalid() {
    return new Object[][] {{"demo_key"}, {"NONEXISTING"}, {"123"}, {"@%$#"},
        {"рпаы"}, {"tña"}, {RandomStringUtils.randomAlphabetic(257)}};

  }

  @Test(dataProvider = "API key invalid")
  public void apiKeyNegativeTest(String apiKey) throws Exception {

    HttpResponse httpResponse = tsas.get(new BasicNameValuePair("api_key", apiKey));
    assertThat(httpResponse.getStatusLine().getStatusCode())
        .isEqualTo(HttpStatus.SC_FORBIDDEN);

    ErrorResponsePage rp =
        gson.fromJson(EntityUtils.toString(httpResponse.getEntity()), ErrorResponsePage.class);

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(rp.error.code).isEqualTo("API_KEY_INVALID");
    softly.assertThat(rp.error.message)
        .isEqualTo("An invalid api_key was supplied. Get one at https://api.nasa.gov");

    softly.assertAll();
  }


  @DataProvider(name = "API key valid")
  public Object[][] entriesKeyValid() {
    return new Object[][] {{null}, {""}, {"DEMO_KEY"}, {"DEMO_KEY2"}};
  }

  @Test(dataProvider = "API key valid")
  public void apiKeyPositiveTest(String apiKey) throws Exception {

    HttpResponse httpResponse;
    if (apiKey != null)
      httpResponse = tsas.get(new BasicNameValuePair("api_key", apiKey));
    else
      httpResponse = tsas.get();

    assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_OK);

    ResponsePage<Sound> rp = gson.fromJson(EntityUtils.toString(httpResponse.getEntity()),
        new TypeToken<ResponsePage<Sound>>() {}.getType());

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(ContentType.getOrDefault(httpResponse.getEntity()).getMimeType())
        .isEqualTo("application/json");
    softly.assertThat(rp.count).isEqualTo(10);
    softly.assertThat(rp.results).hasSize(10);
    softly.assertAll();
  }

}
