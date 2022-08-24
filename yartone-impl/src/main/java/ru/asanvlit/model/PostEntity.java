package ru.asanvlit.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import ru.asanvlit.dto.enums.ArtworkType;
import ru.asanvlit.dto.enums.PostState;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

import static javax.persistence.CascadeType.*;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "post")
public class PostEntity extends AbstractEntity {

    @Enumerated(value = EnumType.STRING)
    @Column(name = "artwork_type", nullable = false, columnDefinition = "VARCHAR(50)")
    private ArtworkType artworkType;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private PostState state;

    @Range(min = 0, max = 5000)
    @Column(name = "hours_spent", columnDefinition = "VARCHAR(50)")
    private Integer hoursSpent;

    @Length(max = 200)
    @Column(columnDefinition = "text")
    private String materials;

    @Length(max = 500)
    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "publishing_date", columnDefinition = "timestamp default now()")
    private Instant publishedAt;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private AccountEntity author;

    @OneToMany(cascade = {PERSIST, REFRESH, REMOVE})
    @JoinTable(name = "post_attachment",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "file_id", referencedColumnName = "id"))
    private Set<FileInfoEntity> attachments;
}
