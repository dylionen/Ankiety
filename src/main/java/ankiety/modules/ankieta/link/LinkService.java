package ankiety.modules.ankieta.link;

import ankiety.modules.ankieta.header.Header;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LinkService {
    public final LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public Optional<Header> getHeaderByKey(String key) {
        Optional<Header> headerOptional = Optional.ofNullable(linkRepository.getLinkByKey(key).getHeader());
        return headerOptional;
    }
}
