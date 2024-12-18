package group3.group3_assignment.entity;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

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
  // @JsonBackReference
  // This ignores the favourites field
  @JsonIgnoreProperties("favourites")
  @ManyToOne(optional = false)
  @JoinColumn(name = "recipe_id", referencedColumnName = "recipeId")
  private Recipe recipe;

  // @JsonIgnoreProperties("customer")
  @ManyToOne
  @JoinColumn(name = "customer_id", referencedColumnName = "id")
  private User user;

}
