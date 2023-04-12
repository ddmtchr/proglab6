package utility;

import java.io.Serializable;

public class Response implements Serializable {
    private String responseBody;
    private int execCode;
    public Response(String b, int ec) {
        responseBody = b;
        execCode = ec;
    }

    public String getBody() {
        return responseBody;
    }

    public int getExecCode() {
        return execCode;
    }
}
