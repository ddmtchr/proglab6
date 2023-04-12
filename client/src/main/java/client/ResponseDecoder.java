package client;

import utility.Response;

public class ResponseDecoder {

    protected int decode(Response r) {
        System.out.println(r.getBody());
        int execCode = r.getExecCode();
        return execCode;
    }
}
