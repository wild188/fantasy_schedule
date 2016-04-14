/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fantasy_hockey;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Billy
 */
public class Analysis {
        
    private League league;
    
    public Analysis(League l){
        league = l;
    }
    
    public void teamOptimazation(Date start, Date end, int maxTeams){
        /*
        * This could be done through graph optimization methods. 
        * Each team would be a node and their overlapping games 
        * with other teams would be the edge weights. We could then
        * construct a graph with maxTeams # of nodes with a minimum edge weight.
        *
        * Measuring independednt weight and maximizing edge weight 
        * would be a more accurate way of measuring the situation.
        * This way you get the max # of games played with your team.
        */
        
        Game[][] scheduleSquared = popSS(start, end);
        
        /*
        * Making a two dimentional array to hold each teams relevant schedule.
        * I will then recursively parse throught the array and calculate the 
        * independent games. Returning the group of the hiest independence.
        *
        * Not sure hot to quanitfy independence.
        */
        
        
        int avgGamesPlayed = calculateAvgNumGames(scheduleSquared);
        
        
        int[] currentSolution = new int[maxTeams];
        int currentSolutionScore = 0;
        int[] possibleSolution = new int[maxTeams];
        int numOfTeams = league.getNumberOfTeams();
        
        for(int i=0; i < numOfTeams; i++){
            possibleSolution[0]= i;
            
            System.out.println("Analysing combinations with "+ league.getTeamByIndex(i).getName());
            
            for(int j = i + 1; (j+maxTeams-1) < numOfTeams; j++){
                //System.out.println(j);
                
                for(int k = 0; k< maxTeams - 1; k++){
                    possibleSolution[k] = j + k;
                }
                int possibleSolutionScore = teamComboEvaluator(possibleSolution, scheduleSquared);
                
                System.out.println("Possible score " + possibleSolutionScore);
                
                if(possibleSolutionScore > currentSolutionScore){
                    //currentSolution = possibleSolution;
                    for(int h = 0; h < maxTeams; h++){
                        currentSolution[h] = possibleSolution[h];
                    }
                    currentSolutionScore = possibleSolutionScore; 
                }
            }
        }
        
        System.out.println("Optimal team Combination:");
        for(int h = 0; h < maxTeams; h++){
            System.out.println(league.getTeamByIndex(currentSolution[h]).getName());
        }
        System.out.println("Independence score of "+ currentSolutionScore);
        double rawScoreIndex = (double)(currentSolutionScore) / (double)(maxTeams * avgGamesPlayed);
        System.out.println("Independence index of "+ rawScoreIndex);
        
//        int stop = maxTeams * numOfTeams;
//        boolean cont = true;
//        int counter = 0;
//        while(cont){
//            
//            cont = counter < stop;
//            counter++;
//        }
    }
    
    private Game[][] popSS(Date start, Date end){
        int nOT = league.getNumberOfTeams();
        Game[][] out = new Game[nOT][];
        for(int i=0; i<nOT; i++){
            ArrayList<Game> relevantSchedule = new ArrayList<Game>();
            Team currentTeam = league.getTeamByIndex(i);
            Game[] schedule = currentTeam.getSchedule();
            for(int j=0; j<currentTeam.getNumberOfGames(); j++){
                Date currentDate = schedule[j].getDate();
                if(currentDate.after(end)){
                    break;
                } else if(currentDate.before(start)){
                    continue;
                } 
                relevantSchedule.add(schedule[j]);
            }
            out[i] = new Game[relevantSchedule.size()];
            out[i] = relevantSchedule.toArray(out[i]);
            
//            out[i] = new Game[league.getTeamByIndex(i).getNumberOfGames()];
//            out[i] = league.getTeamByIndex(i).getSchedule();
        }
        return out;
    }
    
    private int[] teamOpHelper(int max, int progress, int[] currentSol, Game[][] ss){
        int[] out = {-1};
        return out;
    } 
    
    private int teamComboEvaluator(int[] combination, Game[][] ss){
        int out = 0; // looking for largest value
        
        int numberOfTeams= combination.length;
        ArrayList<Date> searched = new ArrayList<Date>();
        for(int i = 0; i < numberOfTeams; i++){
            int teamIndex = combination[i];
            for(int j=0; j < ss[teamIndex].length; j++){
                Date current = ss[teamIndex][j].getDate();
                if(bSearchDates(searched.toArray(new Date[searched.size()]), current)){
                    continue;
                }else{
                    searched.add(current);
                }
                
                int independence = numberOfTeams; // number of teams - overlap
                for(int k =0; k < numberOfTeams; k++){
                    if (k == j ){
                        continue;
                    }
                    if(bSearchSchedule(ss[k], current) >= 0){
                        //System.out.println("Found");
                        independence--;
                    } else{
                        //System.out.println("Not found");
                    }
                    
                }
                out += independence;
            }
        }
        return out;
    }
    
    private boolean bSearchDates(Date[] list, Date target){
        
        //System.out.print("Already checked? ");
        
        int min = 0;
        int max = list.length;
        int stopCount = 5;
        if(stopCount > max){
            stopCount = max;
        }
        
        while(min < max){
            int avg = (min + max)/2;
            
            
            //System.out.println("min: "+min+" max: "+max+" avg: "+avg);
            
            int comp = target.compareTo(list[avg]);
            if(comp == 0){
                //System.out.print("yes \n");
                return true;
            } else if( (min+stopCount)>=max ){
                for(int i=0; i<stopCount; i++){
                    if(list[i].equals(target)){
                        //System.out.print("yes \n");
                        return true;
                    }
                }
                //System.out.print("no \n");
                return false;
            }else if( comp < 0){
                max = avg;
            } else if( comp > 0){
                min = avg;
            }
        }
        
        //System.out.print("no \n");
        return false;
    }
    
    private int bSearchSchedule(Game[] schedule, Date day){
        
        //System.out.print("bSearch called... ");
        
        int out = -1;
        int min = 0;
        int max = schedule.length;
        boolean stop = false;
        int stopCount = 5;
        if(stopCount > max){
            stopCount = max;
        }
        
        while(!stop){
            int avg = (min + max)/2;
            int rel = day.compareTo(schedule[avg].getDate());
            if(rel == 0){
                //System.out.print("found mmatch \n");
                out = avg;
                return out;
            } else if(rel < 0){
                max = avg;
            } else if(rel > 0){
                min = avg;
            }
            if((min + stopCount) > max){
                for(int i =min; i < max; i++){
                    rel = day.compareTo(schedule[avg].getDate());
                    if(rel == 0){
                        out = avg;
                        return out;
                    }
                }
                return out;
            }
            
            if( min == max || min > max ){
                //System.out.print("no match found \n");
                stop = true;
            }
        }
        
        return out;
    }
    
    private int calculateAvgNumGames(Game[][] schedule){
        int out = 0;
        for (int i=0; i<schedule.length; i++){
            out+= schedule[i].length;
        }
        out = out/schedule.length;
        return out;
    }
}