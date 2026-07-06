import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class OrderClient {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    // Метод создания заказа
    public Response create(Order order) {
        return given()
                .header("Content-type", "application/json")
                .baseUri(BASE_URL)
                .body(order)
                .post("/api/v1/orders");
    }

    // Метод получения списка заказов
    public Response getList() {
        return given()
                .baseUri(BASE_URL) // Используем baseUri прямо здесь
                .get("/api/v1/orders");
    }
}