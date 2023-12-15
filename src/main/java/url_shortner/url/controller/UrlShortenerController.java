package url_shortner.url.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import url_shortner.url.service.UrlShortenerService;
import url_shortner.url.service.UrlShortenerServiceImp;

import java.net.URI;

@RestController
@RequestMapping("/urls")
public class UrlShortenerController {

    private final UrlShortenerService service;

    public UrlShortenerController(UrlShortenerServiceImp service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> shorten(@RequestParam("url") UrlShortenerRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(new UrlShortenerResponse(service.shorten(request.url())));
        } catch (Exception exception) {
            return ResponseEntity
                .of(ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "internal.error"))
                .build();
        }
    }

    @GetMapping("/{hash}")
    public ResponseEntity<?> resolve(@PathVariable String hash) {
        try {
            return ResponseEntity
                .status(HttpStatus.MOVED_PERMANENTLY)
                .location(URI.create(service.resolve(hash)))
                .header(HttpHeaders.CONNECTION, "close")
                .build();
        } catch (Exception exception) {
            return ResponseEntity.of(ProblemDetail.forStatus(HttpStatus.BAD_REQUEST)).build();
        }
    }
}