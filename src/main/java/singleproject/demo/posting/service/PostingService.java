package singleproject.demo.posting.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import singleproject.demo.member.service.MemberService;
import singleproject.demo.posting.PostingDto;
import singleproject.demo.posting.PostingRepository;
import singleproject.demo.posting.entity.Posting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostingService {

    private final MemberService memberService;
    private final PostingRepository postingRepository;

    public PostingService(MemberService memberService, PostingRepository postingRepository) {
        this.memberService = memberService;
        this.postingRepository = postingRepository;
    }

    public List<Posting> getAllPosts() {
        return postingRepository.findAll();
    }

    public void createPost(PostingDto.Post post) {
        Posting posting = post.postingDtoToPosting();
        posting.setMember(memberService.findMemberByNickname(post.getNickname()));
        postingRepository.save(posting);
    }

    @Transactional
    public Posting getPosting(long postingId) {
        Optional<Posting> optionalPosting = postingRepository.findById(postingId);
        Posting posting = optionalPosting.orElseThrow(() ->
                new IllegalArgumentException("123"));
        updateViewCount(posting);

        return posting;
    }

    private void updateViewCount(Posting posting) {
        long viewCount = posting.getViewCount();
        posting.setViewCount(viewCount+1);
    }
}
