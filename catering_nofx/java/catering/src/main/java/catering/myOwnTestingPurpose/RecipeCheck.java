package catering.myOwnTestingPurpose;

import catering.businesslogic.recipe.Recipe;

import java.util.ArrayList;

public class RecipeCheck {
    public static void main(String[] args) {
        ArrayList<Recipe> recipes = new ArrayList<>();
        recipes = Recipe.loadAllRecipes();
    }
}
