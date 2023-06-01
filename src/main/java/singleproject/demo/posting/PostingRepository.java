package singleproject.demo.posting;

import org.springframework.data.repository.Repository;
import singleproject.demo.posting.entity.Posting;

import java.util.List;
import java.util.Optional;

public interface PostingRepository extends Repository<Posting, Long> {

    Posting save(Posting posting);

    List<Posting> findAll();

    Optional<Posting> findById(long postingId);
}
