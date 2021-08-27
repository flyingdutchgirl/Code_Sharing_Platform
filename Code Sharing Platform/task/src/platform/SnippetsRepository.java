package platform;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface SnippetsRepository extends CrudRepository<Snippet, Integer> {
    @Override
    List<Snippet> findAll();

    @Override
    <S extends Snippet> S save(S entity);

    @Override
    void deleteAll();

    Optional<Snippet> findByUuid(String uuid);

    List<Snippet> findByRestrictedFalseOrderByCreationDateDesc();

}
