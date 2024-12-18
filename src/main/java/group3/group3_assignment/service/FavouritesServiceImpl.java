package group3.group3_assignment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import group3.group3_assignment.entity.User;
import group3.group3_assignment.entity.Favourites;
import group3.group3_assignment.entity.Recipe;
import group3.group3_assignment.exception.DuplicateFavouritesException;
import group3.group3_assignment.exception.FavUserNotFoundException;
import group3.group3_assignment.exception.RecipeNotFoundException;
import group3.group3_assignment.exception.UserNotFoundException;
import group3.group3_assignment.repository.UserRepo;
import group3.group3_assignment.repository.FavouritesRepository;
import group3.group3_assignment.repository.RecipeRepo;

@Service
public class FavouritesServiceImpl implements FavouritesService {

  private FavouritesRepository favouritesRepository;
  private RecipeRepo recipeRepo;
  private UserRepo userRepo;

  public FavouritesServiceImpl(FavouritesRepository favouritesRepository, RecipeRepo recipeRepo,
      UserRepo userRepo) {
    this.favouritesRepository = favouritesRepository;
    this.recipeRepo = recipeRepo;
    this.userRepo = userRepo;
  }

  @Override
  public void deleteFavourites(Long id, Integer recipeId) {
    if (favouritesRepository.findByUserIdAndRecipeId(id, recipeId) == null) {
      throw new FavUserNotFoundException();
    }
    favouritesRepository.deleteByUserIdAndRecipeId(id, recipeId);
  }

  @Override
  public ArrayList<Favourites> getFavouritesByUserId(Long id) {
    Optional<List<Favourites>> optionalFavourites = Optional.of(favouritesRepository.findAllByUserId(id));
    if (optionalFavourites.isPresent()) {
      // If the Optional contains a value, unwrap it and return the Favourites object
      ArrayList<Favourites> foundFavourites = (ArrayList<Favourites>) optionalFavourites.get();
      return foundFavourites;
    }
    throw new FavUserNotFoundException();
  }

  @Override
  public Favourites addFavourites(Long id, Integer recipeId, Favourites favourites) {
    Recipe selectedRecipe = recipeRepo.findById(recipeId).orElseThrow(() -> new RecipeNotFoundException(recipeId));

    if (userRepo.findById(id) == null) {
      throw new FavUserNotFoundException();
    }
    User selectedUser = userRepo.findById(id)
        .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
    if (favouritesRepository.findByUserIdAndRecipeId(id, recipeId) != null) {
      throw new DuplicateFavouritesException();
    }
    favourites.setRecipe(selectedRecipe);
    favourites.setUser(selectedUser);
    return favouritesRepository.save(favourites);
  }
}
