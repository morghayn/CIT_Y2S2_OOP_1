package View;

import Controller.IActivity;
import Model.Activity;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.function.UnaryOperator;

import static java.lang.Integer.parseInt;

/**
 * <p>This class is utilized to generate the Activity Management tab for the application.</p>
 */
public class ActivityManagement
{

    private static TableColumn<Activity, String> columnOne, columnTwo, columnThree, columnFour;
    private static int HBOXSPACING = 25, VBOXSPACING = 10;
    private static TextField fieldWeek, fieldPoints;
    private static ComboBox<String> comboBoxActivity;
    private static TableView<Activity> tableView;
    private static DatePicker datePicker;
    private IActivity controller;

    /**
     * <p>Linking controller to view and merely logging success of instantiation.</p>
     *
     * @param controller the controller instance shared among views to manage the Activity model
     */
    public ActivityManagement(IActivity controller)
    {
        this.controller = controller;
        System.out.println("Success Log: Activity Management instance created");
    }

    /**
     * <p>A VBox containing the Activity Management GUI components is generated.</p>
     *
     * <p>Communication between buttons and {@link IActivity} is established.</p>
     *
     * @return a VBox containing the Activity Management GUI components
     */
    public VBox setup()
    {
        // :: instantiating management fields (++ filtering just number input for weeks and points)
        fieldWeek = new TextField();
        comboBoxActivity = new ComboBox<>();
        fieldPoints = new TextField();
        datePicker = new DatePicker();
        fieldWeek.setTextFormatter(new TextFormatter<>(allowNumbers(false)));
        fieldPoints.setTextFormatter(new TextFormatter<>(allowNumbers(true)));
        comboBoxActivity.setEditable(true);
        setComboBoxStyle(comboBoxActivity);
        setupComboBox(comboBoxActivity);
        comboBoxActivity.setOnAction(e -> updatePointsField());

        // :: instantiating and styling new labels
        Label labelWeek = new Label("Week");
        Label labelActivity = new Label("Activity");
        Label labelPoints = new Label("Points");
        Label labelDate = new Label("Pick Date");
        setLabelStyle(labelWeek);
        setLabelStyle(labelActivity);
        setLabelStyle(labelPoints);
        setLabelStyle(labelDate);

        // :: instantiating and styling new buttons
        Button buttonAdd = new Button("Add");
        Button buttonRemove = new Button("Remove");
        Button buttonList = new Button("List");
        Button buttonSummary = new Button("Summary");
        Button buttonSave = new Button("Save");
        Button buttonLoad = new Button("Load");
        setButtonStyle(buttonAdd);
        setButtonStyle(buttonRemove);
        setButtonStyle(buttonList);
        setButtonStyle(buttonSummary);
        setButtonStyle(buttonSave);
        setButtonStyle(buttonLoad);

        // :: setting button events
        buttonAdd.setOnAction(e -> submitForm());
        buttonRemove.setOnAction(e -> removeActivity());
        buttonList.setOnAction(e -> reloadTable());
        buttonSummary.setOnAction(e -> tallyPoints());
        buttonSave.setOnAction(e -> controller.saveActivities());
        buttonLoad.setOnAction(e -> controller.loadActivities());

        // :: setting up tableView
        tableView = new TableView<>();
        columnOne = new TableColumn<>("Week");
        columnTwo = new TableColumn<>("Activity");
        columnThree = new TableColumn<>("Points");
        columnFour = new TableColumn<>("Date");
        columnOne.setCellValueFactory(new PropertyValueFactory<>("week"));
        columnTwo.setCellValueFactory(new PropertyValueFactory<>("activity"));
        columnThree.setCellValueFactory(new PropertyValueFactory<>("points"));
        columnFour.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableView.getColumns().add(columnOne);
        tableView.getColumns().add(columnTwo);
        tableView.getColumns().add(columnThree);
        tableView.getColumns().add(columnFour);
        setTableStyle();

        // :: setting up HBoxes
        HBox rowOne = new HBox(HBOXSPACING, labelWeek, fieldWeek, labelActivity, comboBoxActivity);
        HBox rowTwo = new HBox(HBOXSPACING, labelPoints, fieldPoints, labelDate, datePicker);
        HBox rowSaveLoad = new HBox(HBOXSPACING, buttonSave, buttonLoad);
        HBox rowAddRemoveListSummary = new HBox(100, buttonAdd, buttonRemove, buttonList, buttonSummary);

        // :: VBox and starting application
        VBox mainColumn = new VBox(VBOXSPACING, rowOne, rowTwo, rowAddRemoveListSummary, tableView, rowSaveLoad);
        mainColumn.setPadding(new Insets(20, 20, 20, 20));

        // :: setting content and returning reference
        return mainColumn;
    }

    /**
     * <p>Updates points field on form according to which pre-defined activity is selected from the activities
     * comboBox</p>
     */
    private void updatePointsField()
    {
        int index = comboBoxActivity.getSelectionModel().getSelectedIndex();
        if (!(index < 0))
        {
            fieldPoints.setText(controller.getPredefinedPoints(index) + "");
        }
    }

    /**
     * <p>Retrieves ArrayList from the controller and populates the ComboBox.</p>
     */
    private void setupComboBox(ComboBox<String> comboBoxActivity)
    {
        for (Activity activity : controller.loadPredefinedActivities())
        {
            comboBoxActivity.getItems().add(activity.getActivity());
        }
    }

    /**
     * <p>A form submission is attempted. {@link #isFormValid()} is used to check if the submission is valid.</p>
     *
     * <p>If valid the form is submitted.</p>
     *
     * <p>Else if the form is invalid, an instance of {@link PopupWindow} is created and delivers a detailed error
     * message generated with {@link #invalidFields()}.</p>
     */
    private void submitForm()
    {
        if (isFormValid())
        {
            tableView.getItems().add(
                    controller.addActivity(
                            parseInt(fieldWeek.getText()),
                            comboBoxActivity.getValue(),
                            parseInt(fieldPoints.getText()),
                            datePicker.getValue()
                    )
            );
            clearForm();
        }
        else
        {
            new PopupWindow("Field Input Error", invalidFields());
        }
    }

    /**
     * <p>An instance of {@link PopupWindow is created, delivering the total points ammased by the user.</p>
     */
    private void tallyPoints()
    {
        new PopupWindow("Total Points", controller.summarizeActivityPoints());
    }

    /**
     * <p>Verifies that each field in the form is filled out properly.</p>
     *
     * @return the boolean to represent a valid or invalid form
     */
    private boolean isFormValid()
    {
        return (
                fieldWeek.getText().length() != 0 &&
                comboBoxActivity.getValue() != null &&
                fieldPoints.getText().length() != 0 &&
                datePicker.getValue() != null
        );
    }

    /**
     * <p>Generates a detailed error message of the fields that are invalid.</p>
     *
     * @return the detailed error messages indicating what fields are currently invalid
     */
    private String invalidFields()
    {
        return (
                "Attempts to add field has failed, the following input issues occurred:\n" +
                (fieldWeek.getText().length() == 0 ? "\t- No week has been entered\n" : "") +
                (comboBoxActivity.getValue() == null ? "\n\t- No activity has been entered\n" : "") +
                (fieldPoints.getText().length() == 0 ? "\t- No points have been entered\n" : "") +
                (datePicker.getValue() == null ? "\t- No date has been picked\n" : "")
        );
    }

    /**
     * <p>Attempts to remove an activity.</p>
     *
     * <p>Else if attempt fails when no element is selected an instance of {@link PopupWindow} is created and delivers
     * a detailed error message.</p>
     */
    private void removeActivity()
    {
        if (tableView.getSelectionModel().getSelectedIndex() != -1)
        {
            controller.removeActivity(tableView.getSelectionModel().getSelectedIndex());
            tableView.getItems().remove(tableView.getSelectionModel().getSelectedIndex());
        }
        else
        {
            new PopupWindow("Removal Error", "No activity selected in the list box.");
        }
    }

    /**
     * <p>Retrieves ArrayList from the controller and repopulates the tableView.</p>
     */
    private void reloadTable()
    {
        tableView.getItems().clear();
        for (Activity activity : controller.getActivityArrayList())
        {
            tableView.getItems().add(activity);
        }
    }

    /**
     * <p>Clears data inputted into form.</p>
     */
    private void clearForm()
    {
        comboBoxActivity.setValue(null);
        fieldPoints.clear();
        fieldWeek.clear();
        datePicker.setValue(null);
    }

    /**
     * <p>Returns a unary operator to format text boxes to only allow number input.</p>
     *
     * @param negative_allowed indicates to the code whether a field may have negative integers
     * @return the unary operator that is generated, will either allow all whole integers, or just positive integers
     */
    private static UnaryOperator<TextFormatter.Change> allowNumbers(boolean negative_allowed)
    {
        return change ->
        {
            String text = change.getControlNewText();
            return (
                    text.matches(negative_allowed ? "-?([0-9]*)?" : "([0-9]*)?")
                    ? change
                    : null
            );
        };
    }

    /**
     * <p>Applies pre-configured styling to the TableView. Columns are resized and disables the horizontal scroll of
     * the TableView.</p>
     */
    private void setTableStyle()
    {
        columnOne.prefWidthProperty().bind(tableView.widthProperty().divide(4));
        columnTwo.prefWidthProperty().bind(tableView.widthProperty().divide(4));
        columnThree.prefWidthProperty().bind(tableView.widthProperty().divide(4));
        columnFour.prefWidthProperty().bind(tableView.widthProperty().divide(4));
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * <p>Applies fxml styling to the ComboBox passed through the parameters.</p>
     *
     * @param comboBox the comboBox in which pre-configured fxml styling will be applied
     */
    private void setComboBoxStyle(ComboBox comboBox)
    {
        comboBox.setStyle(
                "-fx-min-width: 150;" + /*"-fx-background-color: #F8B195;" +*/
                "-fx-padding: 0 0 0 0;" +
                "-fx-font-size: 12px;"
        );
    }

    /**
     * <p>Applies fxml styling to the label passed through the parameters.</p>
     *
     * @param label the label in which pre-configured fxml styling will be applied
     */
    private void setLabelStyle(Label label)
    {
        label.setStyle(
                "-fx-min-width: 100;" + /*"-fx-background-color: #F8B195;" +*/
                "-fx-padding: 5 5 5 5;" +
                "-fx-font-size: 12px;"
        );
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