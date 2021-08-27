package platform;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JsonGenerator {


    public Map<String, Object> getOneSnippetJson(Snippet snippet) {
        // to keep the order of JSON entries
        LinkedHashMap<String, Object> jsonContent = new LinkedHashMap<>();
        jsonContent.put("code", snippet.getCode());
        jsonContent.put("date", snippet.getStringDate());
        jsonContent.put("time", snippet.timeLeft());
        jsonContent.put("views", snippet.getNumberOfViews());

        return jsonContent;
    }

    public Map<String, Object> getFullInfoJson(Snippet snippet) {
        LinkedHashMap<String, Object> jsonContent = new LinkedHashMap<>();
        Arrays.stream(snippet.getClass().getDeclaredFields())
                .forEachOrdered(field -> {
                    field.setAccessible(true);
                    try {
                        jsonContent.put(field.getName(), field.get(snippet));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });

        return jsonContent;
    }

    public List<Map<String, Object>> getMultipleSnippetJson(List<Snippet> snippetList) {
        return snippetList.stream()
                .map(this::getOneSnippetJson)
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getMultipleFullInfoSnippetJson(List<Snippet> snippetList) {
        return snippetList.stream()
                .map(this::getFullInfoJson)
                .collect(Collectors.toList());
    }

    public Map<String, String> getIdJson(String id) {
        return Map.of("id", id);
    }

}
