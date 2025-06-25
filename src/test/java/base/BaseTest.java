package base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import utils.ConfigReader;

public class BaseTest {
    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = ConfigReader.getProperty("base.uri");
    }
}
