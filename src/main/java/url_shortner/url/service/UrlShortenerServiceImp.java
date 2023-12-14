package url_shortner.url.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import url_shortner.url.enums.HashAlgorithms;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UrlShortenerServiceImp implements UrlShortenerService {

    private final RedisTemplate<String, String> redis;
    private final MessageDigest digest = MessageDigest.getInstance(HashAlgorithms.SHA_256.getValue());

    public UrlShortenerServiceImp(RedisTemplate<String, String> redis) throws NoSuchAlgorithmException {
        this.redis = redis;
    }

    @Override
    public String shorten(String url) {
        byte[] bytes = digest.digest(url.getBytes());
        String hash = String.format("%32x", new BigInteger(1, bytes)).substring(0, 6);

        redis.opsForValue().set(hash, url);

        return hash;
    }

    @Override
    public String resolve(String hash) {
        return redis.opsForValue().get(hash);
    }
}