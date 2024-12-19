package group3.group3_assignment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import group3.group3_assignment.entity.User;
import group3.group3_assignment.entity.Favourites;
import group3.group3_assignment.entity.Recipe;
import group3.group3_assignment.exception.FavUserNotFoundException;
import group3.group3_assignment.exception.DuplicateFavouritesException;
//import group3.group3_assignment.exception.FavUserNotFoundException;
import group3.group3_assignment.exception.FavouritesNotFoundException;
import group3.group3_assignment.exception.RecipeNotFoundException;
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
  public void deleteFavourites(Long userId, Integer recipeId) {

    if (favouritesRepository.findByUserIdAndRecipeId(userId, recipeId) == null) {
      throw new FavouritesNotFoundException();
    }
    favouritesRepository.deleteByUserIdAndRecipeId(userId, recipeId);
  }

  @Override
  public ArrayList<Favourites> getFavouritesByUserId(Long userId) {

    favouritesRepository.findById(userId).orElseThrow(() -> new FavUserNotFoundException(userId));

    Optional<List<Favourites>> optionalFavourites = Optional.of(favouritesRepository.findAllByUserId(userId));
    if (optionalFavourites.isPresent()) {
      // If the Optional contains a value, unwrap it and return the Favourites object
      ArrayList<Favourites> foundFavourites = (ArrayList<Favourites>) optionalFavourites.get();
      return foundFavourites;
    }
    throw new FavUserNotFoundException(userId);
  }

  @Override
  public Favourites addFavourites(Long userId, Integer recipeId, Favourites favourites) {
    Recipe selectedRecipe = recipeRepo.findById(recipeId).orElseThrow(() -> new RecipeNotFoundException(recipeId));
    User selectedUser = userRepo.findById(userId).orElseThrow(() -> new FavUserNotFoundException(userId));
    if (favouritesRepository.findByUserIdAndRecipeId(userId, recipeId) != null) {
      throw new DuplicateFavouritesException();
    }
    favourites.setRecipe(selectedRecipe);
    favourites.setUser(selectedUser);
    return favouritesRepository.save(favourites);
  }
}
