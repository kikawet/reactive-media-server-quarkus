package rms.resources.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class UserControllerTest {

    @Test
    void Given_TestGetHistoryByUser_When_User_Exists_Then_Get_History() {
        PanacheMock.mock(User.class);
        User u = new User();
        u.setLogin("valid");
        Mockito.when(User.findByIdOptional(u.getLogin())).thenReturn(Optional.of(u));

        given()
                .pathParam("login", u.getLogin())
                .when()
                .get("/user/{login}/history")
                .then()
                .assertThat()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    void Given_TestGetHistoryByUser_When_No_User_Provided_Then_404() {
        given()
                .pathParam("login", "")
                .when()
                .get("/user/{login}/history")
                .then()
                .statusCode(404);
    }

    @Test
    void Given_TestGetHistoryByUser_When_No_User_Exists_Then_Fails() {
        PanacheMock.mock(User.class);
        Mockito.when(User.findByIdOptional("999")).thenReturn(Optional.empty());
        given()
                .pathParam("userId", "999")
                .when()
                .get("/user/{userId}/history")
                .then()
                .statusCode(404);
    }

    @Test
    void Given_TestGetSuggestionByUser_When_User_Exists_Then_Get_History() {
        PanacheMock.mock(User.class);
        User u = new User();
        u.setLogin("valid");
        Mockito.when(User.findByIdOptional(u.getLogin())).thenReturn(Optional.of(u));

        given()
                .pathParam("login", u.getLogin())
                .when()
                .get("/user/{login}/suggestion")
                .then()
                .assertThat()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    void Given_TestGetSuggestionByUser_When_No_User_Provided_Then_404() {
        given()
                .pathParam("login", "")
                .when()
                .get("/user/{login}/suggestion")
                .then()
                .statusCode(404);
    }

    @Test
    void Given_TestGetSuggestionByUser_When_No_User_Exists_Then_Fails() {
        PanacheMock.mock(User.class);
        Mockito.when(User.findByIdOptional("999")).thenReturn(Optional.empty());
        given()
                .pathParam("userId", "999")
                .when()
                .get("/user/{userId}/suggestion")
                .then()
                .statusCode(404);
    }

}