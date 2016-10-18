package com.qa.demo.restassured;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

import java.util.Map;

import com.qa.demo.utils.api.APIException;
import com.qa.demo.utils.common.CollectionUtils;
import com.qa.demo.utils.reporting.Log;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

/**
 * This class provides methods to interact with Rest Services using rest assured. WORK in PROGRESS
 * 
 * @author deenesh
 */
public class RestAssuredUtil {

    private static String errorMessage = "Unable to get API Response for ";
    private static String errorCreateMessage = "Unable to create API request ";

    public static enum HTTP_METHOD {
        GET,
        POST,
        PUT,
        DELETE,
        INV_VAL
    };

    /**
     * @param apiEndPoint
     * @param parametersMap
     * @param headersMap
     * @return
     */
    public static RequestSpecification requestSpecification(String apiEndPoint, Map<String, ?> parametersMap,
            Map<String, String> headersMap, Object body) {
        RequestSpecBuilder requestBuilder = new RequestSpecBuilder();
        RequestSpecification request = null;
        try {
            Log.info("Creating request for endPoint: " + apiEndPoint);
            if (CollectionUtils.isNotEmpty(parametersMap, "requestParameters")) {
                requestBuilder.addParams(parametersMap);
            }
            if (CollectionUtils.isNotEmpty(headersMap, "requestHeader")) {
                requestBuilder.addHeaders(headersMap);
            }

        } catch (Exception e) {
            Log.error(errorCreateMessage + apiEndPoint, e);
            throw new APIException(errorCreateMessage + apiEndPoint, e);
        }
        return request;
    }

    /**
     * This method calls REST API with given endpoint without any headers.
     * 
     * @param apiEndPoint
     * @param objectType
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getResponse(String apiEndPoint, Class<T> objectType, RequestSpecification request) {
        T response;
        try {
            Log.info("Creating client response for endPoint: " + apiEndPoint);
            response = (T) given().spec(request).when().get(apiEndPoint).as(objectType.getClass());
        } catch (Exception e) {
            Log.error(errorMessage + apiEndPoint, e);
            throw new APIException(errorMessage + apiEndPoint, e);
        }
        return response;
    }

}
