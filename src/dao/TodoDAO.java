package dao;

import java.util.List;

import model.Todo;

public interface TodoDAO {
	
	public List<Todo> findAll();
	
	public boolean save(Todo todo);
	
	public boolean delete(int id);
	
	public boolean update(Todo todo);
	
}						
