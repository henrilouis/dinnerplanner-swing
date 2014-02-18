package model;

import javafx.beans.InvalidationListener;

import java.util.Observable;

import java.util.HashSet;
import java.util.Set;

public class DinnerModel extends Observable implements IDinnerModel
{
    int nrGuests;
    Set<Dish> dishes = new HashSet<Dish>();
    Set<Dish> selecteddishes = new HashSet<Dish>();

    /**
     * TODO: For Lab2 you need to implement the IDinnerModel interface.
     * When you do this you will have all the needed fields and methods
     * for the dinner planner (number of guests, selected dishes, etc.).
     */

    /**
     * The constructor of the overall model. Set the default values here
     */
    public DinnerModel(){
        //Adding some example data, you can add more
        Dish dish1 = new Dish("French toast",Dish.STARTER,"toast.jpg","In a large mixing bowl, beat the eggs. Add the milk, brown sugar and nutmeg; stir well to combine. Soak bread slices in the egg mixture until saturated. Heat a lightly oiled griddle or frying pan over medium high heat. Brown slices on both sides, sprinkle with cinnamon and serve hot.");
        Ingredient dish1ing1 = new Ingredient("eggs",0.5,"",1);
        Ingredient dish1ing2 = new Ingredient("milk",30,"ml",6);
        Ingredient dish1ing3 = new Ingredient("brown sugar",7,"g",1);
        Ingredient dish1ing4 = new Ingredient("ground nutmeg",0.5,"g",12);
        Ingredient dish1ing5 = new Ingredient("white bread",2,"slices",2);
        dish1.addIngredient(dish1ing1);
        dish1.addIngredient(dish1ing2);
        dish1.addIngredient(dish1ing3);
        dish1.addIngredient(dish1ing4);
        dish1.addIngredient(dish1ing5);
        dishes.add(dish1);

        Dish dish2 = new Dish("Meat balls",Dish.MAIN,"meatballs.jpg","Preheat an oven to 400 degrees F (200 degrees C). Place the beef into a mixing bowl, and season with salt, onion, garlic salt, Italian seasoning, oregano, red pepper flakes, hot pepper sauce, and Worcestershire sauce; mix well. Add the milk, Parmesan cheese, and bread crumbs. Mix until evenly blended, then form into 1 1/2-inch meatballs, and place onto a baking sheet. Bake in the preheated oven until no longer pink in the center, 20 to 25 minutes.");
        Ingredient dish2ing1 = new Ingredient("extra lean ground beef",115,"g",20);
        Ingredient dish2ing2 = new Ingredient("sea salt",0.7,"g",3);
        Ingredient dish2ing3 = new Ingredient("small onion, diced",0.25,"",2);
        Ingredient dish2ing4 = new Ingredient("garlic salt",0.6,"g",3);
        Ingredient dish2ing5 = new Ingredient("Italian seasoning",0.3,"g",3);
        Ingredient dish2ing6 = new Ingredient("dried oregano",0.3,"g",3);
        Ingredient dish2ing7 = new Ingredient("crushed red pepper flakes",0.6,"g",3);
        Ingredient dish2ing8 = new Ingredient("Worcestershire sauce",16,"ml",7);
        Ingredient dish2ing9 = new Ingredient("milk",20,"ml",4);
        Ingredient dish2ing10 = new Ingredient("grated Parmesan cheese",5,"g",8);
        Ingredient dish2ing11 = new Ingredient("seasoned bread crumbs",115,"g",4);
        dish2.addIngredient(dish2ing1);
        dish2.addIngredient(dish2ing2);
        dish2.addIngredient(dish2ing3);
        dish2.addIngredient(dish2ing4);
        dish2.addIngredient(dish2ing5);
        dish2.addIngredient(dish2ing6);
        dish2.addIngredient(dish2ing7);
        dish2.addIngredient(dish2ing8);
        dish2.addIngredient(dish2ing9);
        dish2.addIngredient(dish2ing10);
        dish2.addIngredient(dish2ing11);
        dishes.add(dish2);

        Dish dish3 = new Dish("Sourdough",Dish.STARTER,"sourdough.jpg","Mix milk with other grains and yeast.");
        Ingredient dish3ing1 = new Ingredient("yeast",0.5,"",1);
        Ingredient dish3ing2 = new Ingredient("milk",30,"ml",6);
        Ingredient dish3ing3 = new Ingredient("grain",7,"g",1);

        dish3.addIngredient(dish3ing1);
        dish3.addIngredient(dish3ing2);
        dish3.addIngredient(dish3ing3);

        dishes.add(dish3);

        Dish dish4 = new Dish("Ice cream",Dish.DESERT,"icecream.jpg","Place the banana and mango in a sealed container and freeze for 2–3 hours or until frozen. Place the frozen fruit and yogurt in a blender or food processor and blend until smooth. Serve immediately or place in a sealed container and freeze. After freezing allow to soften for 5 minutes before serving.");
        Ingredient dish4ing1 = new Ingredient("Peeled & chopped bananas",2,"",2);
        Ingredient dish4ing2 = new Ingredient("Peeled & chopped mangos",3,"",8);
        Ingredient dish4ing3 = new Ingredient("Thick or vanilla yogurt",280,"g",12);

        dish4.addIngredient(dish4ing1);
        dish4.addIngredient(dish4ing2);
        dish4.addIngredient(dish4ing3);

        dishes.add(dish4);

        nrGuests = 1;
    }

    /**
     * Returns the set of dishes of specific type. (1 = starter, 2 = main, 3 = desert).
     */
    public Set<Dish> getDishes(){
        return dishes;
    }

    public Set<Dish> getMenu(){
        return selecteddishes;
    }
    /**
     * Returns the set of dishes of specific type. (1 = starter, 2 = main, 3 = desert).
     */
    public Set<Dish> getDishesOfType(int type){
        Set<Dish> result = new HashSet<Dish>();
        for(Dish d : dishes){
            if(d.getType() == type){
                result.add(d);
            }
        }
        return result;
    }


    /**
     * Returns the set of dishes of specific type, that contain filter in their name
     * or name of any ingredient.
     */

    public Set<Dish> filterDishesOfType(int type, String filter){
        Set<Dish> result = new HashSet<Dish>();
        for(Dish d : dishes){
            if(d.getType() == type && d.contains(filter)){
                result.add(d);
            }
        }
        return result;
    }

    public void addDish(String name)
    {
        for(Dish d : dishes){
            if(d.getName() == name)
            {
                if(!selecteddishes.contains(d))
                {
                    selecteddishes.add(d);
                    this.setChanged();
                    this.notifyObservers("dishes");
                }
            }
        }
    }

    public void removeDish(String name)
    {
        for(Dish d : dishes){
            if(d.getName() == name)
            {
                if(selecteddishes.contains(d))
                {
                    selecteddishes.remove(d);
                    this.setChanged();
                    this.notifyObservers("dishes");
                }
            }
        }
    }

    @Override
    public int getNumberOfGuests() {
        return nrGuests;
    }

    @Override
    public void setNumberOfGuests(int numberOfGuests) {
        nrGuests = numberOfGuests;
        this.setChanged();
        this.notifyObservers("guests");
    }

    @Override
    public Dish getSelectedDish(int type)
    {
        return (Dish) getDishesOfType(type);
    }

    @Override
    public Set<Dish> getFullMenu() {
        return getMenu();
    }

    @Override
    public Set<Ingredient> getAllIngredients() {
        Set<Ingredient> result = new HashSet<Ingredient>();
        for(Dish d : selecteddishes){
            for(Ingredient i : d.getIngredients())
            {
                for(int b=0; b < nrGuests; b++)
                {
                    result.add(i);
                }
            }
        }
        return result;
    }

    @Override
    public float getTotalMenuPrice() {
        double price = 0;
        for(Ingredient i : getAllIngredients())
        {
            price += i.getPrice();
        }
        price = price * nrGuests;
        return (float) price;
    }

}