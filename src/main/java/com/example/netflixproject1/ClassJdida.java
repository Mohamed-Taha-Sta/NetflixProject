package com.example.netflixproject1;

public class ClassJdida {

    private int id;
    private String name;

    public ClassJdida(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ClassJdida{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }




}
