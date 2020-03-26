package edu.upc.dsa.ejemplo_retrofit_app;

public class Repo {
    int id;
    String name;
    String full_name;

    @Override
    public String toString() {
        return "Repo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", full_name='" + full_name + '\'' +
                '}';
    }


}
