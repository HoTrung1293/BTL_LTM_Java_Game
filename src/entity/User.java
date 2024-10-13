package entity;

public class User {
    private String id;
    private String gender, username, status;
    private String password;
    private int rating, avg_competitor, avg_time, total_match, win, lose ,draw;
    public void setId(String id){
        this.id=id;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public void setStatus(String status){
        this.status=status;
    }
    public void setGender(String gender){
        this.gender=gender;
    }
    public void setAvgCompetitor(int avg_competitor){
        this.avg_competitor= avg_competitor;
    }
    public void setAvgTime(int avg_time){
        this.avg_time=avg_time;
    }
    public void setTotalMatch(int total_match){
        this.total_match=total_match;
    }
    public void setWin(int win){
        this.win=win;
    }
    public void setLose(int lose){
        this.lose=lose;
    }
    public void setDraw(int draw){
        this.draw=draw;
    }
    public void setRating(int rating){
        this.rating= rating;
    }
    
    public String getId(){
        return id;
    }
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getStatus(){
        return status;
    }
    public String getGender(){
        return gender;
    }
    public int getAvgCompetitor(){
        return avg_competitor;
    }
    public int getAvgTime(){
        return avg_time;
    }
    public int getTotalMatch(){
        return total_match;
    }
    public int getWin(){
        return win;
    }
    public int getLose(){
        return lose;
    }
    public int getDraw(){
        return draw;
    }
    public int getRating(){
        return rating;
    }
}
