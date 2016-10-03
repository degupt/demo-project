package com.qa.demo.utils.api;

import com.qa.demo.utils.reporting.Log;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

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
            return webResource.get(ClientResponse.class);
        } catch (Exception e) {
            Log.error("Unable to get API Response for " + apiEndPoint, e);
            throw new APIException("Unable to get API Response for " + apiEndPoint, e);
        }
    }

}
