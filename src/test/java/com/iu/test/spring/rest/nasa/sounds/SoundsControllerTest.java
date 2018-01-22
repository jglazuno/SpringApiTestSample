package com.iu.test.spring.rest.nasa.sounds;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.iu.spring.rest.nasa.config.WebConfig;

@Test
@ContextConfiguration(classes = WebConfig.class)
@WebAppConfiguration
public class SoundsControllerTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  private String endpoint = "/sounds?api_key=%s&q=%s&limit=%s";

  @BeforeClass
  public void buildMVC() {
    mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  public void soundNoParams() throws Exception {
    mockMvc.perform(get("/sounds", "/").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$.count", is(10))).andExpect(jsonPath("$.results", hasSize(10)));

  }

  @DataProvider(name = "Positive")
  public Object[][] entriesPositive() {
    return new Object[][] {{"", "", "", 10}, {"DEMO_KEY", "", "", 10}, {"", "", "0", 0},
        {"", "", "15", 15}, {"", "Voyager", "", 5}, {"", "!@#", "10", 0}};
  }

  @Test(dataProvider = "Positive")
  public void soundFullParamsPositive(String key, String q, String limit, int expectedSize)
      throws Exception {

    ResultActions ra = mockMvc
        .perform(
            get(String.format(endpoint, key, q, limit), "/").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(jsonPath("$.count", is(expectedSize)))
        .andExpect(jsonPath("$.results", hasSize(expectedSize)));


    // Lets assume sorting by search string q applies to only Title field of Sound instance
    if (!q.isEmpty() && expectedSize != 0) {
      for (int i = 0; i < expectedSize; i++) {
        ra.andExpect(jsonPath("$.results[" + i + "].title", contains(q)));
      }
    }
  }

  @DataProvider(name = "Negative")
  public Object[][] entriesNegative() {
    return new Object[][] {
        {"123", "", "", status().isForbidden(),
            "Wrong api_key was supplied. Get correct one at https://api.nasa.gov"},
        {"", "", "abc", status().isBadRequest(), "Unable to process request"}};
  }

  @Test(dataProvider = "Negative")
  public void soundFullParamsNegative(String key, String q, String limit, ResultMatcher rm,
      String reason) throws Exception {

    mockMvc
        .perform(
            get(String.format(endpoint, key, q, limit), "/").accept(MediaType.APPLICATION_JSON))
        .andExpect(rm).andExpect(status().reason(reason));
  }

}
