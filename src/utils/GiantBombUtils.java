package utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import utils.JsonUtils;

public class GiantBombUtils {
	
	public static List<String> getPlatformsList() {
		String response = null;
		List<String> platforms = new ArrayList<String>();
		
	    response = resultRequest("http://www.giantbomb.com/api/platforms/?format=json&api_key=784568466662eacd7cf5ba81d73976e4aa9291e3&field_list=name");  
	    
	    JsonObject JOPlatforms = JsonUtils.JsonObjectFromString(response);
	    JsonArray JAPlatforms = JOPlatforms.getJsonArray("results");
	    for(int i = 0; i < JAPlatforms.size(); i++){
	    	platforms.add(JAPlatforms.getJsonObject(i).getString("name"));
	    }
	    
	    return platforms;
	}
	
	
	private static String resultRequest(String requestURL) {	
		String responseBody = null; 
		
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
	        HttpGet httpget = new HttpGet(requestURL);
	        // NEED TO SET A CUSTOM USER AGENT, IT CAN BE A RANDOM VALUE
	        httpget.setHeader(HttpHeaders.USER_AGENT, "GiantBombUserAgent");
	        httpget.setHeader(HttpHeaders.ACCEPT, "application/json");
	
	        // Custom Response Handler
	        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
	            public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
	                int status = response.getStatusLine().getStatusCode();
	                if (status >= 200 && status < 300) {
	                    HttpEntity entity = response.getEntity();
	                    return entity != null ? EntityUtils.toString(entity) : null;
	                }
	                else {
	                    throw new ClientProtocolException("Unexpected response status: " + status);
	                }
	            }
	        };
	        
	        responseBody = httpclient.execute(httpget, responseHandler);
	        
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        return responseBody;
	}
	
}