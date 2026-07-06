import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.hamcrest.Matchers.*;

@ExtendWith(AllureJunit5.class)
public class CourierTests {
    private CourierClient courierClient;
    private OrderClient orderClient;
    private int courierId;

    @BeforeEach
    public void setUp() {
        courierClient = new CourierClient();
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Courier can be created")
    public void createCourierSuccess() {
        String login = "testLogin" + System.currentTimeMillis();
        Courier courier = new Courier(login, "1234", "Tester");

        var response = courierClient.create(courier);
        response.then().statusCode(201).body("ok", equalTo(true));

        courierId = courierClient.login(CourierCredentials.from(courier))
                .then().extract().path("id");
    }
    @Test
    @DisplayName("Courier login with wrong password returns error")
    public void loginCourierWrongPasswordReturnsError() {
        String login = "testLogin" + System.currentTimeMillis();

        courierClient.login(new CourierCredentials(login, "wrongPassword"))
                .then().statusCode(404);
    }
    @Test
    @DisplayName("Get order list returns orders")
    public void getOrderListContainsOrders() {
        orderClient.getList()
                .then().statusCode(200)
                .body("orders", notNullValue());
    }
    @Test
    @DisplayName("Cannot create two identical couriers")
    public void createTwoIdenticalCouriersReturnsError() {
        String login = "duplicateLogin" + System.currentTimeMillis();
        Courier courier = new Courier(login, "1234", "Tester");

        courierClient.create(courier); // Первый раз создаем
        courierClient.create(courier)  // Второй раз пытаемся
                .then().statusCode(409);
    }

    @Test
    @DisplayName("Create courier without login returns error")
    public void createCourierWithoutLoginReturnsError() {
        Courier courier = new Courier(null, "1234", "Tester");

        courierClient.create(courier)
                .then().statusCode(400);
    }

    @Test
    @DisplayName("Login courier without login returns error")
    public void loginCourierWithoutLoginReturnsError() {
        courierClient.login(new CourierCredentials(null, "1234"))
                .then().statusCode(400);
    }

    @Test
    @DisplayName("Login non-existent courier returns error")
    public void loginNonExistentCourierReturnsError() {
        courierClient.login(new CourierCredentials("nonExistentLogin999", "wrongPassword"))
                .then().statusCode(404);
    }

    @AfterEach
    public void tearDown() {
        if (courierId != 0) {
            courierClient.delete(courierId);
        }
    }
}