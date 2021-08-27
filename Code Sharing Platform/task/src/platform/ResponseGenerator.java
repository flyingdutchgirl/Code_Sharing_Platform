package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Component
@RestController
public class ResponseGenerator {
    private final JsonGenerator jsonGenerator;
    private final static MultiValueMap<String, String> DEFAULT_HEADERS;
    private DatabaseHandler databaseHandler;

    static {
        DEFAULT_HEADERS = new LinkedMultiValueMap<>();
        DEFAULT_HEADERS.put("Content-Type", List.of("application/json"));
        DEFAULT_HEADERS.put(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, List.of("*"));
    }


    @Autowired
    public ResponseGenerator(JsonGenerator jsonGenerator,
                             DatabaseHandler databaseHandler) {
        this.jsonGenerator = jsonGenerator;
        this.databaseHandler = databaseHandler;
    }


    @GetMapping("/api/code/{UUID}")
    public ResponseEntity<Map<String, Object>> getJsonWithNthRecord(@PathVariable String UUID) {
        return databaseHandler.snippetByUuid(UUID)
                .map(snippet -> {
                    if (!snippet.isVisible())
                        databaseHandler.deleteSnippet(snippet);
                    return snippet;
                })
                .stream()
                .filter(Snippet::isVisible)
                .peek(snip -> databaseHandler.snippetViewed(snip.getUuid()))
                .map(jsonGenerator::getOneSnippetJson)
                .map(ResponseGenerator::httpResponse)
                .findFirst()
                .orElse(notFoundResponse404());
    }


    @GetMapping("/api/code/clear")
    public void clearDatabase() {
        databaseHandler.clearSnippetDatabase();
    }

    @GetMapping("/api/code/latest")
    public ResponseEntity<List<Map<String, Object>>> getJsonWithLatest() {
        var snippets = databaseHandler.latestSnippets();
        var json = jsonGenerator.getMultipleSnippetJson(snippets);
        return httpResponse(json);
    }

    @GetMapping("/api/code/all")
    public ResponseEntity<List<Map<String, Object>>> getJsonWithAll() {
        var snippets = databaseHandler.allSnippets();
        var json = jsonGenerator.getMultipleFullInfoSnippetJson(snippets);
        return httpResponse(json);
    }

    @PostMapping(value = "/api/code/new")
    public ResponseEntity<Map<String, String>> setCode(@RequestBody Map<String, Object> requestJsonMap) {
        String code = (String) requestJsonMap.get("code");
        Integer time = (Integer) requestJsonMap.get("time");
        Integer views = (Integer) requestJsonMap.get("views");

        Snippet snippet = new Snippet(code, time, views);
        databaseHandler.addSnippet(snippet);

        var responseJson = jsonGenerator.getIdJson(snippet.getUuid());
        return httpResponse(responseJson);
    }

    public static <T> ResponseEntity<T> httpResponse(T content) {
        return new ResponseEntity<T>(
                content,
                DEFAULT_HEADERS,
                HttpStatus.OK
        );
    }

    public static <T> ResponseEntity<T> notFoundResponse404() {
        return new ResponseEntity<T>(HttpStatus.NOT_FOUND);
    }

}
