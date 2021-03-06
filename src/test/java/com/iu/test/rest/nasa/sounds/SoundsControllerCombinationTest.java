package com.iu.test.rest.nasa.sounds;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
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
public class SoundsControllerCombinationTest {

  private TestSoundApiService tsas = new TestSoundApiService();
  protected Gson gson = new GsonBuilder().create();


  @DataProvider(name = "entries")
  public Object[][] entries() {
    return new Object[][] {{"5", "i", 5, false},
        {String.valueOf(Integer.MAX_VALUE), "space", Integer.MAX_VALUE, false},
        {String.valueOf(Integer.MAX_VALUE), RandomStringUtils.randomAlphabetic(257), 0, true}};
  }

  @Test(dataProvider = "entries")
  public void combinationsTest(String limit, String search, int num, boolean strictNum)
      throws Exception {

    HttpResponse httpResponse = tsas.get(new BasicNameValuePair("api_key", "DEMO_KEY"),
        new BasicNameValuePair("q", search), new BasicNameValuePair("limit", limit));


    assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(HttpStatus.SC_OK);

    ResponsePage<Sound> rp = gson.fromJson(EntityUtils.toString(httpResponse.getEntity()),
        new TypeToken<ResponsePage<Sound>>() {}.getType());

    SoftAssertions softly = new SoftAssertions();

    if (strictNum) {
      softly.assertThat(rp.count).isEqualTo(num);
    } else {
      softly.assertThat(rp.count).isLessThanOrEqualTo(num);
    }
    softly.assertThat(rp.results).hasSize(rp.count);
    softly.assertThat(rp.results).allMatch(sound -> sound.getTitle().contains(search));
    softly.assertAll();
  }

}
