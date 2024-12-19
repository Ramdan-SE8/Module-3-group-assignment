package group3.group3_assignment.service;

import java.util.ArrayList;
import group3.group3_assignment.entity.Favourites;

public interface FavouritesService {

  void deleteFavourites(Long userId, Integer recipeId);

  ArrayList<Favourites> getFavouritesByUserId(Long userId);

  Favourites addFavourites(Long userId, Integer recipeId, Favourites favourites);

}
