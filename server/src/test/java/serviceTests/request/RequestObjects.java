package serviceTests.request;

import spark.Request;

public class RequestObjects extends Request {
    private String body;

   public RequestObjects(String body) {

        this.body = body;
    }

    public String body() {

        return (String) body;
    }
}
