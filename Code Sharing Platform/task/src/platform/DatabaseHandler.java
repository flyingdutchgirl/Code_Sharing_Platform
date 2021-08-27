package platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DatabaseHandler {

    private SnippetsRepository snippetsRepository;
    public final int NUMBER_OF_DISPLAYED_LATEST_SNIPPETS = 10;


    @Autowired
    public DatabaseHandler(SnippetsRepository snippetsRepository) {
        this.snippetsRepository = snippetsRepository;
    }

    public synchronized List<Snippet> allSnippets() {
        return snippetsRepository.findAll();
    }

    public synchronized Optional<Snippet> snippetByUuid(String uuid) {
        return snippetsRepository.findByUuid(uuid);
    }

    public synchronized List<Snippet> latestSnippets(int number) {
        return snippetsRepository
                .findByRestrictedFalseOrderByCreationDateDesc()
                .stream()
                .limit(number)
                .collect(Collectors.toList());
    }

    public synchronized List<Snippet> latestSnippets() {
        return latestSnippets(NUMBER_OF_DISPLAYED_LATEST_SNIPPETS);
    }

    public synchronized void deleteByUuid(String uuid) {
        snippetsRepository
                .findByUuid(uuid)
                .ifPresent(snip -> snippetsRepository.delete(snip));
    }

    public synchronized void deleteSnippet(Snippet snippet) {
        snippetsRepository.delete(snippet);
    }

    public synchronized void snippetViewed(String uuid) {
        var opt = snippetsRepository.findByUuid(uuid);
        if (opt.isPresent()) {
            var snippet = opt.orElseThrow();
            snippet.decreaseViewCounter();
            snippetsRepository.save(snippet);
        }
    }

    public synchronized void clearSnippetDatabase() {
        snippetsRepository.deleteAll();
    }

    public synchronized void addSnippet(Snippet snippet) {
        snippetsRepository.save(snippet);
    }
}
