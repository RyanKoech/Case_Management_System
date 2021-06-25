public class Schedule {
    
    private String date;
    private int hour;
    private int min;
    private int sec;

    public Schedule(String date, int hour, int min, int sec) {
        this.date = date;
        this.hour = hour;
        this.min = min;
        this.sec = sec;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getSec() {
        return sec;
    }

    public void setSec(int sec) {
        this.sec = sec;
    }
    
    
    
}
