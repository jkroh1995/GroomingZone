package tdd.groomingzone.post.freeboard.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tdd.groomingzone.post.freeboard.dto.FreeBoardApiDto;
import tdd.groomingzone.post.freeboard.dto.MultiFreeBoardCommandResponse;
import tdd.groomingzone.post.freeboard.dto.GetFreeBoardPageCommand;
import tdd.groomingzone.post.freeboard.service.GetFreeBoardPageUseCase;
import tdd.groomingzone.global.pagedresponse.PagedResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/free-board")
public class GetFreeBoardPageController {

    private final GetFreeBoardPageUseCase getFreeBoardPageUseCase;

    public GetFreeBoardPageController(GetFreeBoardPageUseCase getFreeBoardPageUseCase) {
        this.getFreeBoardPageUseCase = getFreeBoardPageUseCase;
    }

    @GetMapping
    public ResponseEntity<PagedResponseDto<FreeBoardApiDto.SimpleResponse>> getFreeBoardPage(@RequestParam(name = "title", required = false) String title,
                                                                                       @RequestParam(name = "content", required = false) String content,
                                                                                       @RequestParam(name = "writer", required = false)String writer,
                                                                                       @RequestParam(name = "page", defaultValue = "1") int pageNumber) {
        GetFreeBoardPageCommand getFreeBoardPageCommand = GetFreeBoardPageCommand.of(title, content, writer, pageNumber);
        MultiFreeBoardCommandResponse commandResponse = getFreeBoardPageUseCase.getFreeBoardPage(getFreeBoardPageCommand);
        List<FreeBoardApiDto.SimpleResponse> responseDtoList= commandResponse.pageResponse().stream()
                .map(FreeBoardApiDto.SimpleResponse::of)
                .collect(Collectors.toList());
        PagedResponseDto<FreeBoardApiDto.SimpleResponse> response = new PagedResponseDto<>(responseDtoList, commandResponse.pageInfo());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
