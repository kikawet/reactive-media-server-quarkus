package rms.resources;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.vertx.RunOnVertxContext;
import io.quarkus.test.vertx.UniAsserter;
import io.smallrye.mutiny.Uni;
import rms.model.Video;

@QuarkusTest
public class VideoResourceTests {

    @Test
    @RunOnVertxContext
    void Given_GetVideoByTitle_When_Video_Exists_Then_Get_VideoDTO(UniAsserter asserter) {
        asserter.execute(() -> PanacheMock.mock(Video.class));
        Video v = new Video();
        v.setTitle("valid");
        asserter.execute(() -> Mockito.when(Video.findById(v.getTitle())).thenReturn(Uni.createFrom().item(v)));

        asserter.execute(() -> given()
                .pathParam("title", v.getTitle())
                .when()
                .get("/video/{title}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("title", is(v.getTitle()))
                .body("isPrivate", is(nullValue()))); // Assert that this property is hidden

        asserter.surroundWith(x -> Panache.withSession(() -> x));
    }

    @Test
    @RunOnVertxContext
    void Given_GetVideoByTitle_When_Video_IsPrivate_Then_Get_404(UniAsserter asserter) {
        asserter.execute(() -> PanacheMock.mock(Video.class));
        Video v = new Video();
        v.setTitle("valid");
        v.setPrivate(true);
        asserter.execute(() -> Mockito.when(Video.findById(v.getTitle())).thenReturn(Uni.createFrom().item(v)));

        asserter.execute(() -> given()
                .pathParam("title", v.getTitle())
                .when()
                .get("/video/{title}")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND));

        asserter.surroundWith(x -> Panache.withSession(() -> x));
    }

    @Test
    @RunOnVertxContext
    void Given_GetVideoByTitle_When_No_Video_Exists_Then_Fails(UniAsserter asserter) {
        asserter.execute(() -> given()
                .pathParam("title", "---")
                .when()
                .get("/video/{title}")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND));

        asserter.surroundWith(x -> Panache.withSession(() -> x));
    }
}
