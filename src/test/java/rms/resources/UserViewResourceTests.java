package rms.resources;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import rms.model.User;
import rms.model.Video;

@QuarkusTest
class UserViewResourceTests {
    @Test
    void Given_Create_When_UserView_Valid_Then_Get_Created() {
        PanacheMock.mock(User.class, Video.class);

        User u = new User();
        u.setLogin("111");
        Video v = new Video();
        v.setTitle("valid");

        Mockito.when(User.findById(u.getLogin())).thenReturn(Uni.createFrom().item(u));
        Mockito.when(Video.findById(v.getTitle())).thenReturn(Uni.createFrom().item(v));

        Map<String, String> userViewRequest = new HashMap<>();

        userViewRequest.put("userLogin", u.getLogin());
        userViewRequest.put("videoTitle", v.getTitle());
        userViewRequest.put("completionPercentage", "11.13");

        given()
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
                .body("timestamp", is(Matchers.notNullValue()));
    }

    @Test
    void Given_Create_When_UserView_Invalid_Then_Get_400() {
        given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body("{}")
                .when()
                .post("/view")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("violations", is(not(empty())));
    }
}
