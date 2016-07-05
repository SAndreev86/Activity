package ru.simbirsoft.intensiv.workWithDB;


import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "findActivityByName", query = "SELECT c FROM ActivityDB c WHERE c.name = :name"),
        @NamedQuery(name = "findMaxIdActivity", query = "SELECT MAX(id) FROM ActivityDB")
})
@Table(name = "activity")
public class ActivityDB {

    public ActivityDB(){

    }

    @Id
    private int id;

    private String activity;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActivityDB that = (ActivityDB) o;

        if (id != that.id) return false;
        if (activity != null ? !activity.equals(that.activity) : that.activity != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (activity != null ? activity.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
