package group3.group3_assignment.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import group3.group3_assignment.entity.Favourites;

public interface FavouritesRepository extends JpaRepository<Favourites, Long> {

  Favourites save(Optional<Favourites> favouritesToUpdate);

  List<Favourites> findAllById(Long id);

  List<Favourites> findAllByUserId(Long id);

  Favourites findByUserIdAndRecipeId(Long id, Integer recipeId);

  void deleteByUserIdAndRecipeId(Long id, Integer recipeId);

}
