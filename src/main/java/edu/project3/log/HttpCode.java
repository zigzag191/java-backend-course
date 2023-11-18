package edu.project3.log;

public enum HttpCode {

    CODE_200("OK", 200),
    CODE_404("Not Found", 404),
    CODE_500("Internal Server Error", 500),
    CODE_304("Not Modified", 304),
    CODE_UNKNOWN("Unknown", -1);

    private final String codeName;
    private final int code;

    HttpCode(String name, int code) {
        this.codeName = name;
        this.code = code;
    }

    public static HttpCode of(int n) {
        for (var code : HttpCode.values()) {
            if (code.getCode() == n) {
                return code;
            }
        }
        return CODE_UNKNOWN;
    }

    public String getCodeName() {
        return codeName;
    }

    public int getCode() {
        return code;
    }

}
