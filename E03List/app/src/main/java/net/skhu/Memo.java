package net.skhu;

import android.provider.ContactsContract;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Memo {
    final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String title;
    Date date;
    boolean checked;

    public Memo(String title, Date date){
        this.title = title;
        this.date = date;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public Date getDate(){
        return date;
    }
    public void setDate(Date date){
        this.date = date;
    }
    public String getDateFormatted(){
        return format.format(date);
    }
    public boolean isChecked(){
        return checked;
    }
    public void setChecked(boolean checked){
        this.checked = checked;
    }
}
