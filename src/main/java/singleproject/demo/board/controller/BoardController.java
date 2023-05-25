package singleproject.demo.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import singleproject.demo.post.entity.Post;
import singleproject.demo.post.service.PostService;

import java.util.List;

@Controller
public class BoardController {

    private PostService postService;

    @GetMapping("/board")
    public String getBoard(Model model) {
        List<Post> posts = postService.getAllPosts(); // 게시물 데이터 조회
        model.addAttribute("posts", posts); // 모델에 게시물 데이터 추가
        return "board"; // 게시판 템플릿 이름 반환
    }
}