package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.DinnerModel;
import model.Dish;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Henri on 7-2-14.
 */
public class mainController implements Initializable
{
    private DinnerModel dinnerModel = new DinnerModel();
    // The components of our views
    // Components for the menu

    @FXML public HBox recipeBox = new HBox();
    @FXML public VBox dinnerMenuBox = new VBox();
    @FXML public ChoiceBox noPeople = new ChoiceBox();
    @FXML public Label totalPrice = new Label();

    @FXML public Button starterButton = new Button();
    @FXML public Button mainButton = new Button();
    @FXML public Button dessertButton = new Button();

    @FXML public Button ingredientsButton = new Button();
    @FXML public Button preparationButton = new Button();

    @FXML public TextField searchField = new TextField();

    private String currentType = "starter";

    // Handlers

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        dinnerModel.addDish("Meat balls");

        updateMenu();
        updateStringSearch(currentType, searchField.getText());

        // handlers

        noPeople.getItems().addAll("1", "2", "3", "4", "5", "6");
        noPeople.getSelectionModel().selectedItemProperty().addListener(new javafx.beans.value.ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observableValue, String o, String o2)
            {
                dinnerModel.setNumberOfGuests(Integer.parseInt(o2));
                updatePrice();
            }
        });

        starterButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent actionEvent) {
                currentType = "starter";
                updateStringSearch(currentType, searchField.getText());
            }
        });

        mainButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent actionEvent) {
                currentType = "main";
                updateStringSearch(currentType, searchField.getText());
            }
        });

        dessertButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent actionEvent) {
                currentType = "dessert";
                updateStringSearch(currentType, searchField.getText());
            }
        });

        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                updateStringSearch(currentType, searchField.getText());
            }
        });

        ingredientsButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent actionEvent) {
                Parent root;
                try {
                    ingredientsController IngredientsController = new ingredientsController(dinnerModel);
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/IngredientsView.fxml"));

                    root = FXMLLoader.load(getClass().getClassLoader().getResource("views/IngredientsView.fxml"));
                    fxmlLoader.setRoot(root);
                    fxmlLoader.setController(IngredientsController);

                    Stage stage = new Stage();
                    stage.setTitle("Ingredients");
                    stage.setScene(new Scene(root, 600, 400));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        preparationButton.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent actionEvent) {

                Parent root;
                try {
                    preparationController PreparationController = new preparationController(dinnerModel);
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/PreparationView.fxml"));

                    root = FXMLLoader.load(getClass().getClassLoader().getResource("views/PreparationView.fxml"));
                    fxmlLoader.setRoot(root);
                    fxmlLoader.setController(PreparationController);

                    Stage stage = new Stage();
                    stage.setTitle("Preparation");
                    stage.setScene(new Scene(root, 600, 400));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    public void updateMenu()
    {
        dinnerMenuBox.getChildren().clear();
        for(Dish d : dinnerModel.getFullMenu())
        {
            Image dishImage;
            final Text dishLabel;
            ImageView dishImageView;
            VBox dishBox = new VBox();

            Button dishRemove = new Button();
            dishRemove.setText("x");

            dishImage = new Image(new File("images/"+d.getImage()).toURI().toString());
            dishLabel = new Text(d.getName());
            dishLabel.setWrappingWidth(140);
            dishLabel.setTextAlignment(TextAlignment.CENTER);
            dishImageView = new ImageView(dishImage);
            dishBox.getChildren().add(dishImageView);
            dishBox.getChildren().add(dishLabel);
            dishBox.getChildren().add(dishRemove);
            dishBox.getStyleClass().add("recipe");

            dishRemove.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    //System.out.println(mouseEvent.getSource().toString());
                    dinnerModel.removeDish(dishLabel.getText());
                    updateMenu();
                    updatePrice();
                }
            });

            dinnerMenuBox.getChildren().add(dishBox);
        }
    }

    public void updateStringSearch(String string, String s2)
    {
        recipeBox.getChildren().clear();

        int type = 1;

        if(string == "starter")
        {
            type = 1;
        }
        else if(string == "main")
        {
            type = 2;
        }
        else if(string == "dessert")
        {
            type = 3;
        }

        for(final Dish d : dinnerModel.filterDishesOfType(type, s2))
        {
            Image dishImage;
            final Text dishLabel;
            ImageView dishImageView;

            VBox dishBox = new VBox();
            dishImage = new Image(new File("images/"+d.getImage()).toURI().toString());
            dishLabel = new Text(d.getName());
            dishLabel.setWrappingWidth(140);
            dishLabel.setTextAlignment(TextAlignment.CENTER);
            dishImageView = new ImageView(dishImage);
            dishBox.getChildren().add(dishImageView);
            dishBox.getChildren().add(dishLabel);
            dishBox.getStyleClass().add("recipe");

            // mouseclick

            dishBox.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {

                    //System.out.println(mouseEvent.getSource().toString());
                    /*dinnerModel.addDish(dishLabel.getText());
                    updateMenu();
                    updatePrice();*/

                    Parent root;
                    try {
                        dishController DishController = new dishController(d);
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/DishView.fxml"));

                        root = FXMLLoader.load(getClass().getClassLoader().getResource("views/DishView.fxml"));
                        fxmlLoader.setRoot(root);
                        fxmlLoader.setController(DishController);

                        Stage stage = new Stage();
                        stage.setTitle(dishLabel.getText());
                        stage.setScene(new Scene(root, 600, 400));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            recipeBox.getChildren().add(dishBox);
        }

    }

    public void updatePrice()
    {
        String s = String.valueOf(dinnerModel.getTotalMenuPrice());
        s = s.substring(0,s.length()-2);
        totalPrice.setText("SEK "+s);
    }

}
