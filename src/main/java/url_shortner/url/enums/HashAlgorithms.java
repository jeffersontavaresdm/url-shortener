package url_shortner.url.enums;

public enum HashAlgorithms {
    SHA_1("SHA-1"),
    SHA_3("SHA-3"),
    SHA_256("SHA-256"),
    MD5("MD5");

    private final String value;

    HashAlgorithms(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
