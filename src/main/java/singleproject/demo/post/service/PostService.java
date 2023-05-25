package singleproject.demo.post.service;

import org.springframework.stereotype.Service;
import singleproject.demo.member.entity.Member;
import singleproject.demo.post.entity.Post;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    public List<Post> getAllPosts() {
        return new ArrayList<>();
    }
}
