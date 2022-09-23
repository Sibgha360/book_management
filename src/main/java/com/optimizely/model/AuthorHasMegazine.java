package com.optimizely.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "author_has_megazine")
@NoArgsConstructor
@Getter @Setter
@ToString
public class AuthorHasMegazine {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "author_has_megazine_id")
    private Integer authorHasMegazineId;

    @ManyToOne
    @JoinColumn(name = "megazine_id")
    @JsonIgnore
    Megazine megazine;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonIgnore
    Author author;

    public AuthorHasMegazine(Megazine megazine, Author author) {
        this.megazine = megazine;
        this.author = author;
    }
}
