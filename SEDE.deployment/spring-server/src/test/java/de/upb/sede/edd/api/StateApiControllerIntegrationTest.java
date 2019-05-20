package de.upb.sede.edd.api;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StateApiControllerIntegrationTest {

    @Autowired
    private StateApi api;

    @Test
    public void stateGetTest() throws Exception {
        ResponseEntity<Void> responseEntity = api.stateGet();
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void statePutTest() throws Exception {
        ResponseEntity<Void> responseEntity = api.statePut();
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

}
