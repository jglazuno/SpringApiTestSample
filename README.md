# SpringApiTestSample

This sample shows how to implement REST endpoint with Java Spring MVC and test that could be used to verify this enpoint

Several points about endpoint:
- test service added to retrive real data from nasa service https://api.nasa.gov/planetary/sounds
- some additional restrictions added on the Spring service level

Tests:
There is a single test class src/test/java/com/iu/test/spring/rest/nasa/sounds/SoundsControllerTest.java with several test methods which show:
- positive and negative cases
- used parametrization for same type tests execution 

