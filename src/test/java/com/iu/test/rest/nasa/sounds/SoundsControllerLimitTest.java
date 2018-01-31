package com.iu.test.rest.nasa.sounds;

import static org.assertj.core.api.Assertions.assertThat;

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
import com.iu.spring.rest.nasa.sounds.test.service2.TestSoundApiService;

@Test(groups = {"BB"})
public class SoundsControllerLimitTest {

  private TestSoundApiService tsas = new TestSoundApiService();
  protected Gson gson = new GsonBuilder().create();

  @DataProvider(name = "entries")
  public Object[][] entries() {
    return new Object[][] {{"", true}, {"0", false}, {"-1", true}, {"1", false},
        {String.valueOf(Integer.MAX_VALUE), false}, {String.valueOf(Integer.MAX_VALUE + 1), true},
        {"abc", true}, {"@%$#", true}, {"рпаы", true}};

  }

  @Test(dataProvider = "entries")
  public void limitTest(String limit, boolean def) throws Exception {

    HttpResponse httpResponse = tsas.get(new BasicNameValuePair("api_key", "DEMO_KEY"),
        new BasicNameValuePair("limit", limit));

    assertThat(httpResponse.getStatusLine().getStatusCode())
        .isEqualTo(HttpStatus.SC_OK);

    assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_OK);

    ResponsePage<Sound> rp = gson.fromJson(EntityUtils.toString(httpResponse.getEntity()),
        new TypeToken<ResponsePage<Sound>>() {}.getType());

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(ContentType.getOrDefault(httpResponse.getEntity()).getMimeType())
        .isEqualTo("application/json");
    if (def) {
      softly.assertThat(rp.count).isEqualTo(10);
    } else {
      softly.assertThat(rp.count).isLessThanOrEqualTo(Integer.parseInt(limit));
    }
    softly.assertThat(rp.results).hasSize(rp.count);
    softly.assertAll();
  }



}
