package com.siftscience;

import com.siftscience.model.VerificationFieldSet;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static java.net.HttpURLConnection.HTTP_OK;

public class VerificationEventTest {

    @Test
    public void testVerification() throws Exception {
        String sessionId = "gigtleqddo84l8cm15qe4il";
        String verifiedValue = "14155551212";

        String expectedRequestBody = "{\n" +
                "  \"$type\"         : \"$verification\",\n" +
                "  \"$api_key\"      : \"your_api_key_here\",\n" +
                "  \"$user_id\"      : \"billy_jones_301\",\n" +
                "  \"$session_id\"   : \"" + sessionId + "\",\n" +
                "  \"$status\"       : \"$pending\",\n" +
                "  \"$verification_type\" : \"$sms\",\n" +
                "  \"$verified_value\" : \"" + verifiedValue + "\"\n" +
                "}";

        // Start a new mock server and enqueue a mock response.
        MockWebServer server = new MockWebServer();
        MockResponse response = new MockResponse();
        response.setResponseCode(HTTP_OK);
        response.setBody("{\n" +
                "    \"status\" : 0,\n" +
                "    \"error_message\" : \"OK\",\n" +
                "    \"time\" : 1327604222,\n" +
                "    \"request\" : \"" + TestUtils.unescapeJson(expectedRequestBody) + "\"\n" +
                "}");
        server.enqueue(response);
        server.start();
        HttpUrl baseUrl = server.url("");

        // Create a new client and link it to the mock server.
        SiftClient client = new SiftClient("your_api_key_here");
        client.setBaseUrl(baseUrl);

        // Build and execute the request against the mock server.
        SiftRequest request = client.buildRequest(new VerificationFieldSet()
                .setUserId("billy_jones_301")
                .setSessionId(sessionId)
                .setStatus("$pending")
                .setVerificationType("$sms")
                .setVerifiedValue(verifiedValue));

        SiftResponse siftResponse = request.send();

        // Verify the request.
        RecordedRequest request1 = server.takeRequest();
        Assert.assertEquals("POST", request1.getMethod());
        Assert.assertEquals("/v205/events", request1.getPath());
        JSONAssert.assertEquals(expectedRequestBody, request.getFieldSet().toJson(), true);

        // Verify the response.
        Assert.assertEquals(HTTP_OK, siftResponse.getHttpStatusCode());
        Assert.assertEquals(0, (int) siftResponse.getBody().getStatus());
        JSONAssert.assertEquals(response.getBody().readUtf8(),
                siftResponse.getBody().toJson(), true);

        server.shutdown();

    }
}
