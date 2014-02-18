package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.DinnerModel;
import model.Dish;

import java.util.Observable;
import java.util.Observer;


/**
 * Created by Henri on 12-2-14.
 */
public class preparationController implements Observer {

    @FXML VBox preparationMenuBox;
    DinnerModel dM;

    public preparationController(DinnerModel dinnerModel)
    {
        dM = dinnerModel;
        dM.addObserver(this);
    }

    @FXML void initialize() {
        preparationMenuBox.getChildren().clear();
        if (dM.getFullMenu().isEmpty())
        {
            Label label = new Label();
            label.setText("Currently no dishes in the menu. Add dishes to the menu to see them here.");
            preparationMenuBox.getChildren().add(label);
        }
        else
        {
            for(Dish d : dM.getFullMenu())
            {

                Label label = new Label();
                Text text = new Text();

                text.maxWidth(400);
                text.setWrappingWidth(550);

                if(d.getType() == 1)
                {
                    label.setText("Starter - "+d.getName());
                    label.setFont(Font.font("Verdana", 20));
                   //System.out.println("Starter");
                }
                else if(d.getType()==2)
                {
                    label.setText("Main - "+d.getName());
                    label.setFont(Font.font("Verdana", 20));
                    //System.out.println("Main");
                }
                else if(d.getType()==3)
                {
                    label.setText("Desert - "+d.getName());
                    label.setFont(Font.font("Verdana", 20));
                    //System.out.println("Desert");
                }
                text.setText(d.getDescription()+"\n");

                preparationMenuBox.getChildren().add(label);
                preparationMenuBox.getChildren().add(text);
            }
        }
    }


    @Override
    public void update(Observable o, Object arg)
    {
        initialize();
    }
}