package singleproject.demo.posting;

import lombok.Getter;
import lombok.Setter;
import singleproject.demo.posting.entity.Posting;

public class PostingDto {

    @Getter
    @Setter
    public static class Post{
            private String title;
            private String content;
            private String nickname;

        public Posting postingDtoToPosting() {
            Posting posting = new Posting();
            posting.setTitle(title);
            posting.setContent(content);

            return posting;
        }
    }
}
