package group3.group3_assignment.service;

import java.util.ArrayList;
import group3.group3_assignment.entity.Favourites;

public interface FavouritesService {

  void deleteFavourites(Long id, Integer recipeId);

  ArrayList<Favourites> getFavouritesByUserId(Long id);

  Favourites addFavourites(Long id, Integer recipeId, Favourites favourites);

}
