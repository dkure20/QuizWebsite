package Friends.Models;

import java.sql.Timestamp;

public class FriendRequest {
    private int fromId;
    private int toId;
    private Timestamp timestamp;
    public FriendRequest(int from, int to, Timestamp timeNow){
        this.fromId = from;
        this.toId = to;
        this.timestamp = timeNow;
    }
    public int getFromId(){
        return this.fromId;
    }
    public int getToId(){
        return this.toId;
    }
    public Timestamp getTime(){
        return this.timestamp;
    }
}
