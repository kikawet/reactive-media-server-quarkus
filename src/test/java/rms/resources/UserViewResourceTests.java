package rms.resources;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.RunOnVertxContext;
import io.quarkus.test.vertx.UniAsserter;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import rms.model.User;
import rms.model.UserView;
import rms.model.Video;

@QuarkusTest
class UserViewResourceTests {

    @Test
    @Disabled("Totally imposible to test with mocks because .persist")
    @RunOnVertxContext
    void Given_Create_When_UserView_Valid_Then_Get_Created(UniAsserter asserter) {
        asserter.execute(() -> PanacheMock.mock(User.class, Video.class, UserView.class));

        User u = new User();
        u.setLogin("111");
        Video v = new Video();
        v.setTitle("valid");

        asserter.execute(() -> Mockito.when(User.findById(u.getLogin())).thenReturn(Uni.createFrom().item(u)));
        asserter.execute(() -> Mockito.when(Video.findById(v.getTitle())).thenReturn(Uni.createFrom().item(v)));
        asserter.execute(() -> Mockito.when(UserView.builder()).thenCallRealMethod());

        Map<String, String> userViewRequest = new HashMap<>();

        userViewRequest.put("userLogin", u.getLogin());
        userViewRequest.put("videoTitle", v.getTitle());
        userViewRequest.put("completionPercentage", "11.13");

        asserter.execute(() -> given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body(userViewRequest)
                .when()
                .post("/view")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("source", is("System"))
                .body("userLogin", is(u.getLogin()))
                .body("videoTitle", is(v.getTitle()))
                .body("timestamp", is(Matchers.notNullValue())));

        asserter.execute(() -> PanacheMock.verify(UserView.class).persistAndFlush());

        asserter.surroundWith(x -> Panache.withSession(() -> x));
    }

    @Test
    @RunOnVertxContext
    void Given_Create_When_UserView_Invalid_Then_Get_400(UniAsserter asserter) {
        asserter.execute(() -> given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body("{}")
                .when()
                .post("/view")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("violations", is(not(empty()))));

        asserter.surroundWith(x -> Panache.withSession(() -> x));
    }
}
