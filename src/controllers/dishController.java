package controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.DinnerModel;
import model.Dish;
import model.Ingredient;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Henri on 12-2-14 updated just now.
 */

public class dishController implements Observer
{
    @FXML public Label dishName;
    @FXML public Label dishPrice;
    @FXML public Text dishDescription;
    @FXML public VBox dishImageBox = new VBox();

    // The table and columns
    @FXML TableView<Items> itemTbl;
    @FXML TableColumn dishIngredient;
    @FXML TableColumn ingredientQuantity;
    @FXML TableColumn ingredientCost;

    private DinnerModel dM;
    private Dish d;

    // The table's data
    ObservableList<Items> data;

    static long nextId = 1;
    
    public dishController(Dish dish, DinnerModel dinnerModel)
    {
        d = dish;
        dM = dinnerModel;
        dM.addObserver(this);
    }


    @FXML void initialize()
    {
        //System.out.println(d.getName());
        dishImageBox.getChildren().clear();

            Image dishImage;
            ImageView dishImageView;

            final Text dishLabel;
            VBox dishBox = new VBox();

            dishImage = new Image(new File("images/"+d.getImage()).toURI().toString());
            //dishLabel = new Text(d.getName());
           // dishLabel.setTextAlignment(TextAlignment.RIGHT);
            dishImageView = new ImageView(dishImage);
            dishBox.getChildren().add(dishImageView);
            //dishBox.getChildren().add(dishLabel);
            dishBox.getStyleClass().add("recipe");

        dishImageBox.getChildren().add(dishBox);

        dishName.setText(d.getName());
        dishPrice.setText("SEK "+String.valueOf(getDishPrice())+" for "+(String.valueOf(dM.getNumberOfGuests()))+" Person(s)");
        dishDescription.setText(d.getDescription());
        dishDescription.setWrappingWidth(316);

        // Set up the table data
        dishIngredient.setCellValueFactory(
                new PropertyValueFactory<Items,String>("Ingredient")
        );
        ingredientQuantity.setCellValueFactory(
                new PropertyValueFactory<Items,Integer>("Quantity")
        );
        ingredientCost.setCellValueFactory(
                new PropertyValueFactory<Items,String>("Cost")
        );

        data = FXCollections.observableArrayList();
        itemTbl.setItems(data);

        fillTable();
    }

    public float getDishPrice()
    {
        double price = 0;
        for(Ingredient i : d.getIngredients())
        {
            price += i.getPrice();
        }
        price = price * dM.getNumberOfGuests();
        return (float) price;
    }

    @Override
    public void update(Observable o, Object arg) {
        initialize();
    }

    public class Items
    {
        public SimpleStringProperty ingredient = new SimpleStringProperty();
        public SimpleStringProperty quantity = new SimpleStringProperty();
        public SimpleStringProperty cost = new SimpleStringProperty();

        public String getIngredient() {
            return ingredient.get();
        }

        public String getCost() {
            return cost.get();
        }

        public String getQuantity() {
            return quantity.get();
        }
    }

    @FXML private void fillTable()
    {
        for(Ingredient i: d.getIngredients())
        {
            Items item = new Items();
            item.ingredient.setValue(i.getName());
            Float quantity = new Float(i.getQuantity());
            quantity = quantity * dM.getNumberOfGuests();
            item.quantity.setValue(String.valueOf(quantity)+" "+i.getUnit());
            Float cost = new Float(i.getPrice()*dM.getNumberOfGuests());
            item.cost.setValue("SEK "+cost.toString());
            data.add(item);
        }

    }

}
