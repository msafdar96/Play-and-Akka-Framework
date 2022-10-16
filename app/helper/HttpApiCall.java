package helper;

import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.mvc.Result;
import play.shaded.ahc.org.asynchttpclient.Response;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

/**
 * This Helper call handles all the api calls requests that come for
 * fetching the data from the freelancer api end point
 * @author Group
 */

public class HttpApiCall {
    private final String api = "https://www.freelancer.com/api/projects/0.1/projects/active/";
    private final String accessTokenName = "freelancer-oauth-v1";
    private final String accessToken = "opQVp2ZYc8Zk4s4ASPPahezCggVPXy";
    private final String limit = "250";
    private WSClient wsclient;

// User details and projects
    private final String api1 = "https://www.freelancer.com/api/projects/0.1/projects/";
    private final String user_api = "https://www.freelancer.com/api/users/0.1/users/";


    @Inject
    public HttpApiCall(WSClient wsclient){
        this.wsclient = wsclient;
    }

    /**
     * This helper method takes the keyword and search for all the active projects
     * for the keyword provided and return the WSResponse.
     * @author Group
     * @param keyword
     * @return <code>CompletionStage WSResponse </code>
     */
    public CompletionStage<WSResponse> searchProjects(String keyword){
        //keyword = keyword.replace(" ","%");
        CompletionStage<WSResponse> futurePromise = wsclient.url(api)
                .addHeader(accessTokenName, accessToken)
                .addQueryParameter("query",keyword)
                .addQueryParameter("compact","false")
                .addQueryParameter("job_details","true")
                .addQueryParameter("limit",limit)
                .setMethod("GET").stream();
        return futurePromise;
    }






    /**
     * This helper method takes the keyword user id and search for all the active projects
     * for the that user id and return the WSResponse.
     * @author Anusha Reddy
     * @param keyword
     * @return <code>CompletionStage WSResponse </code>
     */

    public CompletionStage<WSResponse> searchUserProjects(String keyword){
        CompletionStage<WSResponse> futurePromise = wsclient.url(api1)
                .addHeader(accessTokenName, accessToken)
                .addQueryParameter("owners[]",keyword)
                .addQueryParameter("limit",limit)
                .addQueryParameter("compact","false")
                .addQueryParameter("job_details","true")
                .setMethod("GET").stream();
        return futurePromise;
    }

    /**
     * This helper method takes the user id and search for details of that user.
     * @author Anusha Reddy
     * @param id
     * @return <code>CompletionStage WSResponse </code>
     */
    public CompletionStage<WSResponse> searchUserId(String id){
        String userapi = user_api+'/'+id;
        CompletionStage<WSResponse> futurePromise = wsclient.url(userapi)
                .addHeader(accessTokenName, accessToken)
                .setMethod("GET").stream();
        return futurePromise;
    }

}
