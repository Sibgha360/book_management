package com.optimizely.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "megazine")
public class Megazine {

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  @Column(name = "megazine_id")
  private Integer megazineId;

  @Column(name = "title")
  private String title;

  @Column(name = "isbn")
  private String isbn;

  @Column(name = "publication_date")
  private LocalDate publicationDate;

  @OneToMany(mappedBy = "megazine", cascade = CascadeType.ALL)
  List<AuthorHasMegazine> authorHasMegazines;

  public Megazine(String title, String isbn, LocalDate publicationDate) {
    this.title = title;
    this.isbn = isbn;
    this.publicationDate = publicationDate;
  }

  public void addAuthorHasMegazine(AuthorHasMegazine authorHasMegazine){
    if (authorHasMegazines == null)
      authorHasMegazines = new ArrayList<>();
    this.authorHasMegazines.add(authorHasMegazine);
  }
}
