package com.qa.demo.utils.api;

import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.testng.Reporter;

import com.qa.demo.utils.reporting.Log;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

/**
 * This class provides methods to interact with Rest Services.
 * 
 * @author deenesh
 */
public class APIHelper {

    /**
     * This method calls REST API with given endpoint without any headers.
     * 
     * @param apiEndPoint
     *            API URL
     * @return JSON response object
     * @throws custom
     *             exception with custom message
     */
    public static ClientResponse getResponseNoHeader(String apiEndPoint) {
        try {
            Log.info("Creating client response for endPoint: " + apiEndPoint);
            Client client = Client.create();
            WebResource webResource = client.resource(apiEndPoint);
            return webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        } catch (Exception e) {
            Log.error("Unable to get API Response for " + apiEndPoint, e);
            throw new APIException("Unable to get API Response for " + apiEndPoint, e);
        }
    }

    /**
     * enum declaring different api methods.
     * 
     */
    public enum HTTP_VERB {
        GET,
        POST,
        PUT,
        DELETE,
        INV_VAL
    }

    private void setHeaders(Builder builder, Map<String, String> headers) {
        if (headers != null && headers.size() != 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                if (("Accept").equalsIgnoreCase(entry.getKey())) {
                    builder.header(entry.getKey(), entry.getValue());
                }
                if (("Authorization").equalsIgnoreCase(entry.getKey())) {
                    builder.header(entry.getKey(), entry.getValue());
                }
                if (("Content-Type").equalsIgnoreCase(entry.getKey())) {
                    builder.header(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    private Client createClient(String username, String password) {
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter(username, password));
        return client;
    }

    private Client createClient() {
        return Client.create();
    }

    private ClientResponse callAPIMethod(HTTP_VERB verb, Client client, String apiEndPoint, String requestContent,
            Map<String, String> headers) {
        ClientResponse response = null;
        try {
            WebResource webResource = client.resource(apiEndPoint);
            Builder builder = webResource.getRequestBuilder();
            setHeaders(builder, headers);
            response = callWebservice(verb, builder, requestContent);
            Log.info("API Response Status: " + response.getStatus());
            return response;
        } catch (Exception e) {
            Log.error("Client Handler exception, unable to get web Service response " + e.getCause());
            Reporter.log("Client Handler exception, unable to get web service Response : " + e.getCause());
            throw new APIException("Unable to get Client Response" + e);
        }
    }

    /**
     * Create Client Response object for the API request
     * 
     * @param verb
     * @param apiEndPoint
     * @param requestContent
     * @param headers
     * @param username
     * @param password
     * @return ClientResponse
     */
    public ClientResponse getResponse(HTTP_VERB verb, String apiEndPoint, String requestContent,
            Map<String, String> headers, String username, String password) {
        Client client = createClient(username, password);
        return callAPIMethod(verb, client, apiEndPoint, requestContent, headers);
    }

    /**
     * Create Client REsponse object for the API request
     * 
     * @param verb
     * @param apiEndPoint
     * @param requestContent
     * @param headers
     * @return ClientResponse
     */
    public ClientResponse getResponse(HTTP_VERB verb, String apiEndPoint, String requestContent,
            Map<String, String> headers) {
        Client client = createClient();
        return callAPIMethod(verb, client, apiEndPoint, requestContent, headers);
    }

    private ClientResponse callWebservice(HTTP_VERB verb, Builder builder, String requestContent) {
        ClientResponse response = null;
        switch (verb) {
        case GET:
            response = builder.get(ClientResponse.class);
            break;
        case POST:
            response = builder.post(ClientResponse.class, requestContent);
            break;
        case PUT:
            response = builder.put(ClientResponse.class, requestContent);
            break;
        default:
            break;
        }
        return response;
    }

}
