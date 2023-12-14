package url_shortner.url.service;

public interface UrlShortenerService {

    String shorten(String url);

    String resolve(String hash);
}