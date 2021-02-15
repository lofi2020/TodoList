package dao;

import db.DBConnector;
import model.Todo;
import model.TodoState;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLTodoDAO implements TodoDAO {

    @Override
    public List<Todo> findAll() {
        List<Todo> todos = new ArrayList<Todo>();

        try {

            Statement selectStatement = DBConnector.instance().getConnection().createStatement();

            ResultSet rs = selectStatement.executeQuery("SELECT * FROM todos");

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String task = rs.getString("task");
                String state = rs.getString("state");
                Date date = rs.getDate("deadline");
                System.out.printf("%d %s\t  %s\n %s\n %s\n", id, title, task, state, date.toLocalDate());
                todos.add(new Todo(id, title, task, TodoState.valueOf(state), date.toLocalDate()));
            }

            DBConnector.instance().closeConnection();

        } catch (SQLException | DBException e) {

            e.printStackTrace();

        }
        return todos;
    }

    @Override
    public boolean save(Todo todo) {
        boolean result = false;
        try {
            PreparedStatement ps = DBConnector.instance().getConnection()
                    .prepareStatement("INSERT INTO todos (title, task, state, deadline) values(?, ?, ?, ?)");
            ps.setString(1, todo.getTitle());
            ps.setString(2, todo.getTask());
            ps.setString(3, todo.getState().toString());
            ps.setDate(4, Date.valueOf(todo.getDeadline()));
            result = ps.execute();
            // call executeUpdate to execute our sql update statement
            ps.close();
            DBConnector.instance().closeConnection();
            return result;
        } catch (SQLException | DBException e) {
            // log the exception
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
        try {
            PreparedStatement ps = DBConnector.instance().getConnection().prepareStatement("DELETE FROM todos WHERE id = ?");

            // set the preparedstatement parameters
            ps.setInt(1, id);

            // call executeUpdate to execute our sql update statement
            ps.executeUpdate();
            ps.close();
            DBConnector.instance().closeConnection();
            result = true;
        } catch (SQLException | DBException e) {
            // log the exception
            e.printStackTrace();

		}
        return result;
    }

    @Override
    public boolean update(Todo todo) {
        boolean result = false;
        try {
            PreparedStatement ps = DBConnector.instance().getConnection()
                    .prepareStatement("UPDATE todos SET title = ?, task = ?, state = ?, deadline = ? WHERE id = ?");

            // set the preparedstatement parameters
            ps.setString(1, todo.getTitle());
            ps.setString(2, todo.getTask());
            ps.setString(3, todo.getState().toString());
            ps.setDate(4, Date.valueOf(todo.getDeadline()));
            ps.setInt(5, todo.getId());

            // call executeUpdate to execute our sql update statement
            ps.executeUpdate();
            ps.close();
            DBConnector.instance().closeConnection();
            result = true;
        } catch (SQLException | DBException e) {
            // log the exception
            e.printStackTrace();
        }
        return result;
    }




}
