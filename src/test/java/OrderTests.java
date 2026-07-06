import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;


@ExtendWith(AllureJunit5.class)
public class OrderTests {
    private OrderClient orderClient;

    @BeforeEach
    public void setUp() {
        orderClient = new OrderClient();
    }

    @ParameterizedTest
    @MethodSource("provideColors")
    public void createOrderWithDifferentColors(String[] color) {
        Order order = new Order("Naruto", "Uchiha", "Konoha", "142", "8 800 355 35 35", 5, "2026-07-07", "comment", color);

        orderClient.create(order)
                .then().statusCode(201).body("track", notNullValue());
    }

    private static Stream<Arguments> provideColors() {
        return Stream.of(
                Arguments.of((Object) new String[]{"BLACK"}),
                Arguments.of((Object) new String[]{"GREY"}),
                Arguments.of((Object) new String[]{"BLACK", "GREY"}),
                Arguments.of((Object) new String[]{})
        );
    }
}