package controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.DinnerModel;
import model.Ingredient;

import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

/**
 * Created by Henri on 12-2-14.
 */
public class ingredientsController implements Observer
{
    // The table and columns
    @FXML
    TableView<Item> itemTbl;

    @FXML
    TableColumn itemIngredient;
    @FXML
    TableColumn itemQuantity;
    @FXML
    TableColumn itemCost;

    // The table's data
    ObservableList<Item> data;

    static long nextId = 1;

    // use @FXML declaration here to bind objects to the fxml, check the other documents.
    DinnerModel dM;
    public ingredientsController(DinnerModel dinnerModel)
    {
        dM = dinnerModel;
        //System.out.println(dinnerModel.getFullMenu());
        dM.addObserver(this);
    }

    @FXML void initialize()
    {
        // Set up the table data
        itemIngredient.setCellValueFactory(
                new PropertyValueFactory<Item,String>("Ingredient")
        );
        itemQuantity.setCellValueFactory(
                new PropertyValueFactory<Item,Integer>("Quantity")
        );
        itemCost.setCellValueFactory(
                new PropertyValueFactory<Item,String>("Cost")
        );

        data = FXCollections.observableArrayList();
        itemTbl.setItems(data);

        fillTable();
    }

    @Override
    public void update(Observable o, Object arg)
    {
        fillTable();
    }

    public class Item
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

    @FXML
    private void fillTable()
    {
        data.clear();
        for(Ingredient i: dM.getAllIngredients())
        {
            Item item = new Item();
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