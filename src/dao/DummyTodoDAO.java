package dao;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import model.TodoState;
import model.Todo;

public class DummyTodoDAO implements TodoDAO {
	
	ArrayList<Todo>	todoList = new ArrayList<>();

	public DummyTodoDAO() {
		todoList.add(new Todo(1, "Einkaufen", "Mehl", TodoState.TODO, LocalDate.of(2021,Month.FEBRUARY,22)));
		todoList.add(new Todo(1, "Sport", "laufen", TodoState.TODO, LocalDate.now()));
	}
	
	
	@Override
	public List<Todo> findAll() {
		// TODO Auto-generated method stub
		return todoList;
	}

	@Override
	public boolean save(Todo todo) {
		// TODO Auto-generated method stub
		return todoList.add(todo);
	}


	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean update(Todo todo) {
		// TODO Auto-generated method stub
		return false;
	}

}
