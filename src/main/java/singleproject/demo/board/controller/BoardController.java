package singleproject.demo.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import singleproject.demo.posting.PostingDto;
import singleproject.demo.posting.entity.Posting;
import singleproject.demo.posting.service.PostingService;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    private final PostingService postService;

    public BoardController(PostingService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String getBoard(Model model) {
        List<Posting> posts = postService.getAllPosts(); // 게시물 데이터 조회
        model.addAttribute("posts", posts); // 모델에 게시물 데이터 추가
        return "board"; // 게시판 템플릿 이름 반환
    }

    @GetMapping("/new")
    public String write(){
        return "write";
    }

    @PostMapping("/new")
    public String post(@ModelAttribute PostingDto.Post post){
        postService.createPost(post);
        return "redirect:/board";
    }

    @GetMapping("/{posting-id}")
    public String getPost(@PathVariable("posting-id") long postingId, Model model){
        Posting posting = postService.getPosting(postingId);
        model.addAttribute("post", posting);
        return "post";
    }
}