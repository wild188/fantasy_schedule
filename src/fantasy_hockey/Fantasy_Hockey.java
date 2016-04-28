/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fantasy_hockey;

import java.io.File;

/**
 *
 * @author Billy
 */
public class Fantasy_Hockey {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String teamPath = "C:\\Users\\Billy\\Desktop\\Programing\\Fantasy_Project\\List_of_teams.txt";
        String schedulePath = "C:\\Users\\Billy\\Desktop\\Programing\\Fantasy_Project\\2015_2016_NHL_Schedule.csv";
        
        Controler c1 = new Controler(teamPath, schedulePath);
    }
    
}
