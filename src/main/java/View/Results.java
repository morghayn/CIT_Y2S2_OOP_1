package View;

import Controller.IActivity;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Results
{

    private static int HBOXSPACING = 100, VBOXSPACING = 10;
    private static ListView<String> listView;
    private IActivity controller;

    /**
     *
     * <p>Linking controller to view and merely logging success of instantiation.</p>
     *
     * @param controller the controller instance shared among views to manage the Activity model
     */
    public Results(IActivity controller)
    {
        this.controller = controller;
        System.out.println("Success Log: Results instance created");
    }

    /**
     * <p>A VBox containing the Results GUI components is generated.</p>
     *
     * <p>Communication between {@link IActivity} is established.</p>
     *
     * @return content containing the Results GUI components
     */
    public VBox setup()
    {
        // :: setting up buttons
        Button buttonOrderByWeek = new Button("List By Week");
        Button buttonOrderByDate = new Button("List By Date");
        Button buttonOrderByPoints = new Button("List By Points");
        Button buttonOrderByActivity = new Button("List By Activity");
        setButtonStyle(buttonOrderByWeek);
        setButtonStyle(buttonOrderByDate);
        setButtonStyle(buttonOrderByPoints);
        setButtonStyle(buttonOrderByActivity);
        buttonOrderByWeek.setOnAction(e -> populateListView(1));
        buttonOrderByDate.setOnAction(e -> populateListView(2));
        buttonOrderByPoints.setOnAction(e -> populateListView(3));
        buttonOrderByActivity.setOnAction(e -> populateListView(4));

        // :: setting up listView and HBox
        listView = new ListView<>();
        HBox orderButtonRow = new HBox(HBOXSPACING);
        orderButtonRow.getChildren().addAll(buttonOrderByWeek, buttonOrderByDate, buttonOrderByPoints, buttonOrderByActivity);

        // :: setting up VBox
        VBox mainColumn = new VBox(VBOXSPACING);
        mainColumn.setPadding(new Insets(20, 20, 20, 20));
        mainColumn.getChildren().add(orderButtonRow);
        mainColumn.getChildren().add(listView);

        // :: setting up content
        return mainColumn;
    }

    /**
     * <p>Populated the ListView with the sorted data.</p>
     *
     * @param sender the sender implies to the controller on what parameter the data should be sorted
     */
    private void populateListView(int sender)
    {
        listView.getItems().clear();

        for (String activity : controller.orderList(sender))
        {
            listView.getItems().add(activity);
        }
    }

    /**
     * <p>Applies fxml styling to the button passed through the parameters.</p>
     *
     * @param button the button in which pre-configured fxml styling will be applied
     */
    private void setButtonStyle(Button button)
    {
        button.setStyle(
                "-fx-min-width: 75;" + /*"-fx-background-color: #dc143c;" +*/
                "-fx-padding: 5 5 5 5;" +
                "-fx-font-size: 12px;" /*"-fx-text-fill: #ffffff;"*/
        );
    }

}
