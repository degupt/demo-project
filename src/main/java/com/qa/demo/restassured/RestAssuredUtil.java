package com.qa.demo.restassured;

import static io.restassured.RestAssured.get;

import com.qa.demo.utils.api.APIException;
import com.qa.demo.utils.reporting.Log;

import io.restassured.response.Response;

/**
 * This class provides methods to interact with Rest Services using rest assured.
 * WORK in PROGRESS
 * @author deenesh
 */
public class RestAssuredUtil {

    public static enum HTTP_METHOD {
        GET,
        POST,
        PUT,
        DELETE,
        INV_VAL
    };

    /**
     * This method calls REST API with given endpoint without any headers.
     * 
     * @param apiEndPoint
     *            API URL
     * @return JSON response object
     * @throws custom
     *             exception with custom message
     */
    @SuppressWarnings("unchecked")
    public static <T> T getResponseNoHeader(String apiEndPoint, Class<T> objectType) {
        T webResource;
        try {
            Log.info("Creating client response for endPoint: " + apiEndPoint);
            webResource = (T) get(apiEndPoint).as(objectType.getClass());
        } catch (Exception e) {
            Log.error("Unable to get API Response for " + apiEndPoint, e);
            throw new APIException("Unable to get API Response for " + apiEndPoint, e);
        }
        return webResource;
    }

   public static void pure(){
       Response res = getResponseNoHeader("/json_path", Response.class);
   }


}
