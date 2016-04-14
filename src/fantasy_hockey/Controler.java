/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fantasy_hockey;

import java.io.File;
import java.util.Date;


/**
 *
 * @author Billy
 */
public class Controler {
    
    private League l1;
    private Analysis a1;
    
    
    public Controler(String teamPath, String shedulePath){
        File teams = new File(teamPath);
        File schedule = new File(shedulePath);
        l1 = new League(teams, schedule);
        a1 = new Analysis(l1);
        
        Date s = new Date("10/7/2015");
        Date e = new Date("11/3/2015");
        
        //a1.teamOptimazation(s, e, 10);
        
        TeamScheduleOptimization tso = new TeamScheduleOptimization(l1);
        tso.teamOptimazation(s, e, 8);
    }
}
