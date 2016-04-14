/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fantasy_hockey;

import java.util.Date;

/**
 *
 * @author Billy
 */
public class Game {
    private Date date;
    private Team home;
    private Team away;
    private int[] score;
    
    public Game(Date d, Team h, Team a){
        date = d;
        home = h;
        away = a;
        
        home.addGame(this);
        away.addGame(this);
    }
    
    public void setScore(int[] s){
        
    }
    
    public Date getDate(){
        return date;
    }
}
