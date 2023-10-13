package rms.resources;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.quarkus.test.vertx.RunOnVertxContext;
import io.quarkus.test.vertx.UniAsserter;
import io.smallrye.mutiny.Uni;
import rms.model.User;

@QuarkusTest
public class UserResourceTests {

    @Test
    @Disabled("Times out")
    @RunOnVertxContext
    @TestSecurity(user = "valid")
    void Given_GetHistoryByUser_When_User_Exists_Then_Get_History(UniAsserter asserter) {
        asserter.execute(() -> PanacheMock.mock(User.class));
        User u = new User();
        u.setLogin("valid");
        asserter.execute(() -> Mockito.when(User.findById(u.getLogin())).thenReturn(Uni.createFrom().item(u)));

        asserter.execute(() -> given()
                .pathParam("login", u.getLogin())
                .when()
                .get("/user/{login}/history")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("size()", is(0)));

        asserter.surroundWith(x -> Panache.withSession(() -> x));
    }

    @Test
    @RunOnVertxContext
    void Given_GetHistoryByUser_When_No_User_Provided_Then_404(UniAsserter asserter) {
        asserter.execute(() -> given()
                .pathParam("login", "")
                .when()
                .get("/user/{login}/history")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND));

        asserter.surroundWith(x -> Panache.withSession(() -> x));
    }

    @Test
    @RunOnVertxContext
    @TestSecurity(user = "999")
    void Given_GetHistoryByUser_When_No_User_Exists_Then_Fails(UniAsserter asserter) {
        asserter.execute(() -> given()
                .pathParam("userId", "999")
                .when()
                .get("/user/{userId}/history")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND));

        asserter.surroundWith(x -> Panache.withSession(() -> x));
    }

    @Test
    @Disabled("Suggestion is a view so it won't return empty List")
    @RunOnVertxContext
    void Given_GetSuggestionByUser_When_User_Exists_Then_Get_Suggestions(UniAsserter asserter) {
        asserter.execute(() -> PanacheMock.mock(User.class));
        User u = new User();
        u.setLogin("valid");
        asserter.execute(() -> Mockito.when(User.findById(u.getLogin())).thenReturn(Uni.createFrom().item(u)));

        asserter.execute(() -> given()
                .pathParam("login", u.getLogin())
                .when()
                .get("/user/{login}/suggestion")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("size()", is(0)));

        asserter.surroundWith(x -> Panache.withSession(() -> x));
    }

    @Test
    @RunOnVertxContext
    void Given_GetSuggestionByUser_When_No_User_Provided_Then_404(UniAsserter asserter) {
        asserter.execute(() -> given()
                .pathParam("login", "")
                .when()
                .get("/user/{login}/suggestion")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND));

        asserter.surroundWith(x -> Panache.withSession(() -> x));
    }

    @Test
    @Disabled("Times out")
    @RunOnVertxContext
    @TestSecurity(user = "999")
    void Given_GetSuggestionByUser_When_No_User_Exists_Then_Fails(UniAsserter asserter) {
        asserter.execute(() -> given()
                .pathParam("userId", "999")
                .when()
                .get("/user/{userId}/suggestion")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND));

        asserter.surroundWith(x -> Panache.withSession(() -> x));
    }
}