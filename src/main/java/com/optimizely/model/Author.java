package com.optimizely.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@ToString
@Getter @Setter
@Entity
@Table(name = "author")
public class Author {

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  @Column(name = "author_id")
  private Integer authorId;

  @Column(name = "firstName")
  private String firstName;

  @Column(name = "lastName")
  private String lastName;

  @Column(name = "email")
  private String email;

  @OneToMany(mappedBy = "author")
  List<AuthorHasBook> authorHasBook;

  @OneToMany(mappedBy = "author")
  List<AuthorHasMegazine> authorHasMegazine;

  public Author(String firstName, String lastName, String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }
}
