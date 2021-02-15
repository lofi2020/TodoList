package model;

import java.time.LocalDate;

public class Todo {

    private int id;
    private String title;
    private String task;
    private TodoState state = TodoState.TODO;
    private LocalDate deadline;

    public Todo() {
    }

    public Todo(int id, String title, String task, TodoState state, LocalDate localDate) {
        super();
        this.id = id;
        this.state = state;
        this.title = title;
        this.task = task;
        this.deadline = localDate;
    }

    public Todo(String title, String task, TodoState state, LocalDate
            deadline) {
        super();
        this.state = state;
        this.title = title;
        this.task = task;
        this.deadline = deadline;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }


    @Override
    public String toString() {
        return "Todo [id=" + id + ", title=" + title + ", task=" + task + ", state=" + state + ", deadline=" + deadline
                + "]";
    }


    public TodoState getState() {
        return state;
    }

    public void setState(TodoState state) {
        this.state = state;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((deadline == null) ? 0 : deadline.hashCode());
        result = prime * result + id;
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        result = prime * result + ((task == null) ? 0 : task.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Todo other = (Todo) obj;
        if (deadline == null) {
            if (other.deadline != null)
                return false;
        } else if (!deadline.equals(other.deadline))
            return false;
        if (id != other.id)
            return false;
        if (state != other.state)
            return false;
        if (task == null) {
            if (other.task != null)
                return false;
        } else if (!task.equals(other.task))
            return false;
        if (title == null) {
			return other.title == null;
        } else return title.equals(other.title);
	}


}
