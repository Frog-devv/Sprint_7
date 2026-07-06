import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class CourierClient {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    // Метод создания
    public Response create(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .body(courier)
                .post("/api/v1/courier");
    }

    // Метод логина
    public Response login(CourierCredentials creds) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .body(creds)
                .post("/api/v1/courier/login");
    }


    public Response delete(int courierId) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .delete("/api/v1/courier/" + courierId);
    }
}