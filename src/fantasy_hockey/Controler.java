/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fantasy_hockey;

import java.io.File;
import java.util.Date;
import javax.swing.JProgressBar;


/**
 *
 * @author Billy
 */
public class Controler {
    
    private League l1;
    private Analysis a1;
    private UserInterface gui;
    
    
    public Controler(String teamPath, String shedulePath){
        File teams = new File(teamPath);
        File schedule = new File(shedulePath);
        l1 = new League(teams, schedule);
        a1 = new Analysis(l1);
        
        gui = new UserInterface(this);
        
        Date s = new Date("10/7/2015");
        Date e = new Date("11/3/2015");
        
        //a1.teamOptimazation(s, e, 10);
        
//        TeamScheduleOptimization tso = new TeamScheduleOptimization(l1);
//        tso.teamOptimazation(s, e, 10);
        
        //new algorithm
//        TeamScheduleOptimization1 tso1 = new TeamScheduleOptimization1(l1);
//        tso1.teamOptimazation(s, e, 8);
    }
    
    public void getTeamOptimization(Date start, Date end, int maxTeams){
        TeamScheduleOptimization1 tso1 = new TeamScheduleOptimization1(l1);
        
        JProgressBar progressBar = gui.getProgressBar();
        progressBar = new JProgressBar(0, tso1.maxCounter);
        progressBar.setValue(0);
        progressBar.setValue(tso1.counter);
        
        tso1.teamOptimazation(start, end, maxTeams);
        //progressBar = new JProgressBar(0, tso1.maxCounter);
        //progressBar.setValue(0);
        
    }
}
