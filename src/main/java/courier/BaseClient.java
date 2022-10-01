package courier;


import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;



public class BaseClient {
    static Reader reader;

    static {
        try {
            reader = Files.newBufferedReader(Paths.get("src/main/resources/Config.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static JsonObject parser;

    static {
        try {
            parser = (JsonObject) Jsoner.deserialize(reader);
        } catch (JsonException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String BASE_URI = (String) parser.get("BASE_URI");

    public BaseClient() throws IOException, JsonException {
    }

    protected RequestSpecification getSpec() {
        return given().log().all()
                .header("Content-Type", "application/json")
                .baseUri(BASE_URI);
    }


    


}
