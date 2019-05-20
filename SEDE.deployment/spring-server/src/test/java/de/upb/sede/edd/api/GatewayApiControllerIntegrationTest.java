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
public class GatewayApiControllerIntegrationTest {

    @Autowired
    private GatewayApi api;

    @Test
    public void gatewayGroupPathGetTest() throws Exception {
        String groupPath = "groupPath_example";
        ResponseEntity<String> responseEntity = api.gatewayGroupPathGet(groupPath);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void gatewayGroupPathPostTest() throws Exception {
        String body = "body_example";
        String groupPath = "groupPath_example";
        ResponseEntity<Void> responseEntity = api.gatewayGroupPathPost(body, groupPath);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void gatewayGroupPathPutTest() throws Exception {
        String body = "body_example";
        String groupPath = "groupPath_example";
        ResponseEntity<Void> responseEntity = api.gatewayGroupPathPut(body, groupPath);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

}
