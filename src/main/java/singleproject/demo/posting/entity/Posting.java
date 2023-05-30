package singleproject.demo.posting.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import singleproject.demo.like.Recommend;
import singleproject.demo.member.entity.Member;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Posting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long postId;

    @Enumerated(EnumType.STRING)
    private Category category;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @OneToOne
    private Member member;

    @OneToMany(mappedBy = "posting")
    private Set<Recommend> likes = new HashSet<>();

    private long viewCount = 0;

    public int getLikesCount(){
        return likes.size();
    }

    @Getter
    public enum Category{
        QUESTION("질문"),
        RECRUITING("구인구직"),
        FREE("자유");

        private String category;

        Category(String category) {
            this.category = category;
        }
    }
}
