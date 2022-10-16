package util;

import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

public class JsonResponse {
    /**
     * Generates JSON Response
     * @param data Object
     * @return JSON Object Response
     * @author Group
     */
    public static ObjectNode createJsonResponse(Object data, String keyword, boolean success, String error, boolean isNew, boolean allProjects) {

        ObjectNode result = Json.newObject();
        result.put("success", success);
        if(success) {
            result.putPOJO("data", data);
            result.put("keyword", keyword);
        }else {
            result.put("error", error);
        }
        result.put("isNew", isNew);
        result.put("allProjects", allProjects);
        return result;
    }


}
