package ru.simbirsoft.intensiv.workWithDB;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = "findStatisticByNameAndDate", query = "SELECT c FROM DataStatisticDB c WHERE c.name = :name AND c.date = :date"),
        @NamedQuery(name = "findMaxIdStatistic", query = "SELECT MAX(id) FROM DataStatisticDB")
})
@Table(name = "statistic")
public class DataStatisticDB {

    public DataStatisticDB(){

    }

    public DataStatisticDB(String activity, long time, String comment, String name, Date date) {
        this.activity = activity;
        this.time = time;
        this.comment = comment;
        this.name = name;
        this.date = date;
    }

    @Id
    private int id;

    private String activity;
    private long time;
    private String comment;
    private String name;
    private Date date;

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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataStatisticDB that = (DataStatisticDB) o;

        if (id != that.id) return false;
        if (time != that.time) return false;
        if (activity != null ? !activity.equals(that.activity) : that.activity != null) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return date != null ? date.equals(that.date) : that.date == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (activity != null ? activity.hashCode() : 0);
        result = 31 * result + (int) (time ^ (time >>> 32));
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
