package ankiety.modules.ankieta.link;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {

    public Link getLinkByKey(String key);
}
