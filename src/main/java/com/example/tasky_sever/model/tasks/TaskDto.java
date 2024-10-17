package com.example.tasky_sever.model.tasks;

public class TaskDto {
    public String title;
    public String start_at;
    public String due_at;
    public String tag;
    public Priority priority;
    public Status status;

    public TaskDto(String title, String start_at, String due_at, String tag, Priority priority, Status status) {
        this.title = title;
        this.start_at = start_at;
        this.due_at = due_at;
        this.tag = tag;
        this.priority = priority;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart_at() {
        return start_at;
    }

    public void setStart_at(String start_at) {
        this.start_at = start_at;
    }

    public String getDue_at() {
        return due_at;
    }

    public void setDue_at(String due_at) {
        this.due_at = due_at;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
