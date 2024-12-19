package group3.group3_assignment.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//import group3.group3_assignment.entity.Recipe.RecipeBuilder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "favourites")
public class Favourites {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "remarks")
  private String remarks;

  @Column(name = "favourites_date")
  private LocalDate favouritesDate;

  // This ignores the customer field

  // This ignores the favourites field
  // @JsonBackReference
  // @JsonIgnoreProperties("favourites")
  @JsonIgnoreProperties({ "favourites" })
  @ManyToOne(optional = false)
  @JoinColumn(name = "recipe_id", referencedColumnName = "recipeId")
  private Recipe recipe;

  @JsonIgnore
  // @JsonBackReference
  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

}
