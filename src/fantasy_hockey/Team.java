/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fantasy_hockey;

import java.util.ArrayList;

/**
 *
 * @author Billy
 */
public class Team {
    private String township;
    private String nickname;
    private ArrayList<Game> schedule;
    private int numberOfGames;
    //private Player[] roster
    private int[] record;
    
    public Team(String township, String nickname){
        this.township = township;
        this.nickname = nickname;
        numberOfGames =0;
        schedule = new ArrayList<Game>();
    }
    
    public void addGame(Game add){
        schedule.add(add);
        numberOfGames++;
    }
    
    public Game[] getSchedule(){
        Game[] out = new Game[schedule.size()];
        out = schedule.toArray(out);
        return out;
    }
    
    public int getNumberOfGames(){
        return numberOfGames;
    }
    
    public String getName(){
        return township+" "+nickname;
    }
    
    
}
