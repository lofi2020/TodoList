package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import dao.MySQLTodoDAO;
import dao.TodoDAO;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import model.Todo;
import model.TodoState;

public class TodoController {

	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	private TodoDAO todoDAO = new MySQLTodoDAO();

	@FXML
	private TableView<Todo> tableView;

	@FXML
	private TableColumn<Todo, Integer> idCol;

	@FXML
	private TableColumn<Todo, String> titleCol;

	@FXML
	private TableColumn<Todo, String> stateCol;

	@FXML
	private TableColumn<Todo, String> taskCol;

	@FXML
	private TableColumn<Todo, LocalDate> deadlineCol;

	@FXML
	private Button saveButton;

	@FXML
	private Button updateButton;

	@FXML
	private Button deleteButton;

	@FXML
	private Button newButton;

	@FXML
	private TextField idTextField;

	@FXML
	private TextField titleTextField;

	@FXML
	private TextField taskTextField;

	@FXML
	private DatePicker deadlinePicker;
	
    @FXML
    private Text formTitleText;
    
    @FXML
    private ChoiceBox<String> choiceBox;
    
	private SimpleBooleanProperty insertingProperty = new SimpleBooleanProperty(true);

	@FXML
	void initialize() {
		initTableView();
		initForm();
		loadAll();
	}

	private void initTableView() {
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		stateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
		taskCol.setCellValueFactory(new PropertyValueFactory<>("task"));
		deadlineCol.setCellValueFactory(new PropertyValueFactory<>("deadline"));
		tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				selectRow(newSelection);
			}
		});
		deadlineCol.setCellFactory(column -> {
			return new TableCell<Todo, LocalDate>() {
				@Override
				protected void updateItem(LocalDate item, boolean empty) {
					super.updateItem(item, empty);

					if (item == null || empty) {
						setText(null);
					} else {
						setText(dateTimeFormatter.format(item));

					}
				}
			};
		});
	}

	private void initForm() {
		updateButton.disableProperty().bind(insertingProperty);
		deleteButton.disableProperty().bind(insertingProperty);
		saveButton.disableProperty().bind(insertingProperty.not());
		newButton.visibleProperty().bind(insertingProperty.not());

		deadlinePicker.setConverter(new StringConverter<LocalDate>() {

			@Override
			public String toString(LocalDate localDate) {
				if (localDate == null)
					return "";
				return dateTimeFormatter.format(localDate);
			}

			@Override
			public LocalDate fromString(String dateString) {
				if (dateString == null || dateString.trim().isEmpty()) {
					return null;
				}
				return LocalDate.parse(dateString, dateTimeFormatter);
			}

		});

		ArrayList<String> stateNames = new ArrayList<String>();
		for (TodoState state : TodoState.values()) {
			stateNames.add(state.toString());
		}
		ObservableList<String> states =  FXCollections.observableArrayList(stateNames);
		this.choiceBox.setItems(states);
	}

	@FXML
	void onDelete(ActionEvent event) {
		todoDAO.delete(Integer.valueOf(this.idTextField.getText()));
		loadAll();
		reset();
	}

	@FXML
	void onInsert(ActionEvent event) {
		todoDAO.save(this.getTodo());
		loadAll();
	}

	/**
	 * New Clicked: change editing mode and reset the form
	 * @param event
	 */
	@FXML
	void onNew(ActionEvent event) {
		this.reset();
	}

	@FXML
	void onUpdate(ActionEvent event) {
		todoDAO.update(this.getTodo());
		loadAll();
	}
	

    @FXML
    void onDeleteMenuItem(ActionEvent event) {
    	ObservableList<Todo> list = tableView.getSelectionModel().getSelectedItems();
		for(Todo todo : list) {
			todoDAO.delete(todo.getId());
		}

		loadAll();
		reset();
    }

	/**
	 * Get todo from form
	 * @return
	 */
	private Todo getTodo() {
		Todo todo = new Todo();
		if (this.idTextField.getText() != null && !this.idTextField.getText().isEmpty()) {
			todo.setId(Integer.valueOf(this.idTextField.getText()));
		}
		todo.setTitle(this.titleTextField.getText());
		
		int selectedStateIndex = this.choiceBox.getSelectionModel().getSelectedIndex();
		todo.setState(TodoState.values()[selectedStateIndex]);
		
		todo.setTask(this.taskTextField.getText());
		todo.setDeadline(this.deadlinePicker.getValue());
		return todo;
	}

	/**
	 * Set select todo to the form and activate editing buttons
	 * @param todo
	 */
	private void selectRow(Todo todo) {
		System.out.println("Selected Todo: " + todo.toString());
		this.insertingProperty.set(false);
		this.formTitleText.setText("Edit Todo");
		this.idTextField.setText(Integer.valueOf(todo.getId()).toString());
		this.titleTextField.setText(todo.getTitle());
		this.taskTextField.setText(todo.getTask());
		this.deadlinePicker.setValue(todo.getDeadline());
		this.choiceBox.getSelectionModel().select(todo.getState().ordinal());
	}

	/**
	 * Load all todos from DB
	 */
	private void loadAll() {
		List<Todo> todos = todoDAO.findAll();
		ObservableList<Todo> list = FXCollections.observableArrayList(todos);
		tableView.setItems(list);
	}

	/**
	 * Reset the form
	 */
	private void reset() {
		this.formTitleText.setText("Insert new Todo");
		this.insertingProperty.set(true);
		this.tableView.getSelectionModel().clearSelection();
		this.idTextField.setText(null);
		this.titleTextField.setText(null);
		this.choiceBox.getSelectionModel().clearSelection();
		this.taskTextField.setText(null);
		this.deadlinePicker.setValue(null);
	}
}
