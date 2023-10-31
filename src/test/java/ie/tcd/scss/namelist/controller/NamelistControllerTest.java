package ie.tcd.scss.namelist.controller;

import ie.tcd.scss.namelist.domain.NameDto;
import ie.tcd.scss.namelist.controller.NamelistController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class NamelistControllerTest {
    @LocalServerPort
    private int port;


    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void resetListShouldSucceed() {
        ResponseEntity<Void> response = this.restTemplate.postForEntity("http://localhost:" + port + "/namelist/reset", null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test
    public void addNameShouldSucceed() {
        ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/namelist/names", new NameDto("Robin"), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }


    @Test
    public void addDuplicateNameShouldSucceed() {
        ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/namelist/names", new NameDto("Robin"), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test
    public void addEmptyNameShouldFail() {
        ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:" + port + "/namelist/names", new NameDto(""), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }


    @Test
    public void getNamesShouldReturnEmptyList() {
        ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/namelist/names", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("(List of names is empty)");
    }


}

