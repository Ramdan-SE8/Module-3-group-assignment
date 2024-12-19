package group3.group3_assignment.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipeId")
    private Integer id;

    @NotBlank(message = "Image URL is mandatory")
    @Column(name = "imgSrc")
    private String imgSrc;

    @Size(min = 5, max = 100, message = "Title must be between 5 characters long")
    @Column(name = "title")
    private String title;

    @Size(min = 5, max = 300, message = "Title must be minimum 5 characters long")
    @Column(name = "description")
    private String description;

    @NotNull(message = "Ingredients cannot be empty")
    @Size(min = 1, message = "Must have minimum 1 ingredient")
    @ElementCollection
    @Column(name = "ingredients")
    private List<String> ingredients;

    @NotNull(message = "Steps cannot be empty")
    @Size(min = 1, message = "Must have minimum 1 step")
    @ElementCollection
    @Column(name = "steps")
    private List<String> steps;

    @JsonIgnore
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favourites> favourites;

    @JsonIgnoreProperties({ "recipes", "password", "favourites" })
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;
}
