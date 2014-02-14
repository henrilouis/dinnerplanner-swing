package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.DinnerModel;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Henri on 12-2-14.
 */
public class ingredientsController
{
    // use @FXML declaration here to bind objects to the fxml, check the other documents.
    @FXML VBox exampleBox;
    DinnerModel dM;
    public ingredientsController(DinnerModel dinnerModel)
    {
        dM = dinnerModel;
        //System.out.println(dinnerModel.getFullMenu());
    }

    @FXML void initialize()
    {
        // Here you should loop through the ingredients in the menu and echo them.
        // It can be done like the updateMenu function in the main controller using:
        //
        // for(Ingredient i : dinnerModel.getAllIngredients()) { code }
        //
        // the i then is one dish object which has all the properties
        exampleBox.getChildren().add(new Label(dM.getAllIngredients().toString()));
    }

}