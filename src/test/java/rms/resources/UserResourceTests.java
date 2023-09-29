package rms.resources;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import rms.model.User;

@QuarkusTest
public class UserResourceTests {

    @Test
    void Given_GetHistoryByUser_When_User_Exists_Then_Get_History() {
        PanacheMock.mock(User.class);
        User u = new User();
        u.setLogin("valid");
        Mockito.when(User.findById(u.getLogin())).thenReturn(Uni.createFrom().item(u));

        given()
                .pathParam("login", u.getLogin())
                .when()
                .get("/user/{login}/history")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("size()", is(0));
    }

    @Test
    void Given_GetHistoryByUser_When_No_User_Provided_Then_404() {
        given()
                .pathParam("login", "")
                .when()
                .get("/user/{login}/history")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    void Given_GetHistoryByUser_When_No_User_Exists_Then_Fails() {
        given()
                .pathParam("userId", "999")
                .when()
                .get("/user/{userId}/history")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    void Given_GetSuggestionByUser_When_User_Exists_Then_Get_History() {
        PanacheMock.mock(User.class);
        User u = new User();
        u.setLogin("valid");
        Mockito.when(User.findById(u.getLogin())).thenReturn(Uni.createFrom().item(u));

        given()
                .pathParam("login", u.getLogin())
                .when()
                .get("/user/{login}/suggestion")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("size()", is(0));
    }

    @Test
    void Given_GetSuggestionByUser_When_No_User_Provided_Then_404() {
        given()
                .pathParam("login", "")
                .when()
                .get("/user/{login}/suggestion")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    void Given_GetSuggestionByUser_When_No_User_Exists_Then_Fails() {
        given()
                .pathParam("userId", "999")
                .when()
                .get("/user/{userId}/suggestion")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

}