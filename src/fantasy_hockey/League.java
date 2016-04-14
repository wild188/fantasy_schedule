/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fantasy_hockey;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Billy
 */
public class League {
    
    private String[][] teamNameIndex;
    private Team[] season;
    private int numberOfTeams;
    
    
    public League(File teams, File schedules){
        populateTeams(teams);
        System.out.println(" \n Imported Team Names \n");
        printTeams();
        initializeSeason();
        System.out.println(" \n Initialized Teams \n");
        populateSchedule(schedules);
        System.out.println(" \n Populated Schedule \n");
    }
    
    private void populateTeams(File teamNames){
        
        int teams = countLines(teamNames);
        numberOfTeams = teams +1;
        teamNameIndex = new String[numberOfTeams][2];
        
        
        try (BufferedReader br = new BufferedReader(new FileReader(teamNames))){
            String line = "";
            int i =0;
            while((line = br.readLine()) != null){
                
                teamNameIndex[i]= line.split(",");
                i++;
            }
        } catch (IOException ex) {
            Logger.getLogger(League.class.getName()).log(Level.SEVERE, null, ex);
        }
          
    }
    
    private void populateSchedule(File scheduleData){
        
        try (BufferedReader br = new BufferedReader(new FileReader(scheduleData))){
            String line = "";
            game : while((line = br.readLine()) != null){
                String gameFields[] = line.split(",");
                Date d = new Date(gameFields[0]);
                int awayI = searchTeamNames(gameFields[2].split(" "));
                int homeI = searchTeamNames(gameFields[3].split(" "));
                if( awayI < 0){
                    System.out.println("Couldn'f find team "+gameFields[2]);
                    
                    //System.exit(0);
                    
                    continue game;
                } 
                if( homeI < 0){
                    System.out.println("Couldn'f find team "+gameFields[3]);
                    
                    //System.exit(0);
                    
                    continue game;
                } 
                Game g = new Game(d, season[awayI], season[homeI]); // this violates ULM low coupling
                
                
            }
        } catch (IOException ex) {
            Logger.getLogger(League.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void initializeSeason(){
        season = new Team[numberOfTeams];
        
        for(int i=0; i < numberOfTeams; i++){
            season[i] = new Team(teamNameIndex[i][0], teamNameIndex[i][1]);
        }
        
    }
    
    private int countLines(File input){
        
        //File f2 = new File(input.getAbsolutePath());
        
        int out = -1;
        try{
        LineNumberReader  lnr = new LineNumberReader(new FileReader(input));
        lnr.skip(Long.MAX_VALUE);
        out = lnr.getLineNumber();
        //System.out.println(lnr.getLineNumber() + 1); //Add 1 because line index starts at 0
        // Finally, the LineNumberReader object should be closed to prevent resource leak
        lnr.close();
        } catch (IOException ex) {
            Logger.getLogger(League.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }
    
    private void printTeams(){
        for(int i =0; i < numberOfTeams; i++){
            for(int j=0; j < teamNameIndex[i].length; j++){
                System.out.print(teamNameIndex[i][j]+",");
            }
            System.out.println();
        }
    }

    private int searchTeamNames(String[] names){
        int out = -1;
        int i =0;
        String search = names[0];
        while(out < 0 && i < names.length){
            if(i > 0){
                search += " "+names[i];
            }
            out = searchTeamNames(search);
            if (out > 0){
                //System.out.println("Found "+names[i]);
                return out;
            }else{
            //System.out.println("Searched for "+names[i]);
            }
            i++;
        }
        return out;
    }
    
    private int searchTeamNames(String name){
        int out = -1;
        for(int i = 0; i < numberOfTeams; i++){
            if(name.equals(teamNameIndex[i][0]) || name.equals(teamNameIndex[i][1])){
                return i;
            }
        }
        //System.out.println(name+" not found");
        //System.exit(0);
        return out;
    }

    public int getNumberOfTeams(){
        return numberOfTeams;
    }
    
    public Team getTeamByIndex(int index){
        return season[index];
    }
    
}
