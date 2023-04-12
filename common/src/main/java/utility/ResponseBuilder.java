package utility;

public class ResponseBuilder {
    private static StringBuilder response = new StringBuilder();

    public static void appendln(String s) {
        response.append(s).append("\n");
    }

    public static String getAndClear() {
        String responseToGet = response.toString();
        response.setLength(0);
        return responseToGet;
    }

    public static String get() {
        return response.toString();
    }
}
