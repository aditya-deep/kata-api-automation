package base;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public abstract class BaseApiClient {

    protected RequestSpecification requestSpec;

    public BaseApiClient() {
        RestAssured.baseURI = "https://automationintesting.online/api";
        requestSpec = RestAssured.given()
                .header("Content-Type", "application/json");
    }

    protected Response get(String endpoint) {
        return requestSpec.get(endpoint);
    }

    protected Response post(String endpoint, Object body) {
        return requestSpec.body(body).post(endpoint);
    }

    protected Response put(String endpoint, Object body) {
        return requestSpec.body(body).put(endpoint);
    }

    protected Response delete(String endpoint) {
        return requestSpec.delete(endpoint);
    }

}
