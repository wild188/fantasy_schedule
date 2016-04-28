/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fantasy_hockey;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Billy
 */
public class TeamScheduleOptimization1 {
        
    private League league;
    private Game[][] scheduleSquared;
    private Team[] optimalCombination;
    private int[][] topCombinations;
    private Date start;
    private Date end;
    private double avgNumberOfGames;
    private double solutionAvgNumOfGames; 
    
    private int bestScore;
    private int[] bestTeams;
    private int currentScore;
    private int[] currentTeams;
    private int maxTeams;
    private int totalNumTeams;
    
    public int maxCounter;
    public int counter;
    
    
    
    
    public TeamScheduleOptimization1(League l){
        league = l;
//        int[] indexes = new int[league.getNumberOfTeams()];
//        for(int i = 0; i< league.getNumberOfTeams(); i++){
//            indexes[i] = i;
//        }
//        int[][] combos = combinationGenerator(indexes, 5);
//        System.out.println(combos.length + " possible cominations!!!!");
//        
//        for( int i =0; i < combos.length; i++){
//            for( int j =0; j < combos[i].length; j++){
//                System.out.print(combos[i][j]+",");
//            }
//            System.out.print("\n");
//        }
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
        
        scheduleSquared = popSS(start, end);
        
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
        double avgScore = 0;
        int tempAvgScore = 0;
        
        bestScore = 0;
        this.maxTeams = maxTeams;
        currentTeams = new int[maxTeams];
        bestTeams = new int[maxTeams];
        totalNumTeams = numOfTeams;
        counter = 0;
        maxCounter = possibleComb(totalNumTeams, maxTeams);
        
        int[] currentCombo = new int[maxTeams];
        for(int i = 0; i<maxTeams; i++){
            currentCombo[i] = i;
        }
        
        System.out.println("balls");
        //not sure about the while logic
        recursiveTeamOptimizer(-1, 0);
        System.out.println("Number of combos: "+ counter+" of "+ maxCounter + " possible.");
        
        System.out.println("Optimal team Combination:");
        for(int h = 0; h < maxTeams; h++){
            System.out.println(league.getTeamByIndex(bestTeams[h]).getName());
        }
        System.out.println("Independence score of "+ bestScore);
        double rawScoreIndex = (double)(currentSolutionScore) / (double)(maxTeams * avgGamesPlayed);
        System.out.println("Independence index of "+ rawScoreIndex);
        System.out.println("Avg independence index was "+ (avgScore / (double)(maxTeams * avgGamesPlayed)));
        
        
//        int[] indexes = new int[league.getNumberOfTeams()];
//        for(int i = 0; i< league.getNumberOfTeams(); i++){
//            indexes[i] = i;
//        }
//        int[][] combos = combinationGenerator(indexes, maxTeams);
//        
//        for(int z =0; z < combos.length; z++){
//            
//            int possibleScore = teamComboEvaluator(combos[z], scheduleSquared);
//            tempAvgScore += possibleScore;
//            if(possibleScore >= currentSolutionScore){
//                currentSolution = possibleSolution;
//                for(int h = 0; h < maxTeams; h++){
//                    currentSolution[h] = combos[z][h];
//                }
//                currentSolutionScore = possibleScore; 
//            }
//            
//            if((z % 1000)==0){
//                avgScore += (double)(tempAvgScore)/combos.length;
//                tempAvgScore = 0;
//            }
//        }
//        avgScore += (double)(tempAvgScore)/combos.length;
//        
//        
//        for(int i=0; i < numOfTeams; i++){
//            possibleSolution[0]= i;
//            
//            System.out.println("Analysing combinations with "+ league.getTeamByIndex(i).getName());
//            
//            for(int j = i + 1; (j+maxTeams-1) < numOfTeams; j++){
//                //System.out.println(j);
//                
//                for(int k = 0; k< maxTeams - 1; k++){
//                    possibleSolution[k] = j + k;
//                }
//                int possibleSolutionScore = teamComboEvaluator(possibleSolution, scheduleSquared);
//                
//                System.out.println("Possible score " + possibleSolutionScore);
//                
//                if(possibleSolutionScore > currentSolutionScore){
//                    //currentSolution = possibleSolution;
//                    for(int h = 0; h < maxTeams; h++){
//                        currentSolution[h] = possibleSolution[h];
//                    }
//                    currentSolutionScore = possibleSolutionScore; 
//                }
//            }
//        }
//        
//        System.out.println("Optimal team Combination:");
//        for(int h = 0; h < maxTeams; h++){
//            System.out.println(league.getTeamByIndex(currentSolution[h]).getName());
//        }
//        System.out.println("Independence score of "+ currentSolutionScore);
//        double rawScoreIndex = (double)(currentSolutionScore) / (double)(maxTeams * avgGamesPlayed);
//        System.out.println("Independence index of "+ rawScoreIndex);
//        System.out.println("Avg independence index was "+ (avgScore / (double)(maxTeams * avgGamesPlayed)));
//        
    }
    
    private void recursiveTeamOptimizer(int previous, int currentPlace){
        
        //counter++;
        
        int remainingTeams = maxTeams - currentPlace;
        if(remainingTeams <= 0){
            
            currentScore = teamComboEvaluator(currentTeams, scheduleSquared);
            
            if(currentScore > bestScore){
                //bestTeams = currentTeams;        
                for(int j = 0; j < maxTeams; j++){
                    bestTeams[j] = currentTeams[j];
                }
                
                bestScore = currentScore;
            }
            
//            for(int i = 0; i<maxTeams; i++){
//                System.out.print(currentTeams[i]+", ");
//            }
//            System.out.println();
            counter++;
        }else{
            for(int i = previous+1; i < (totalNumTeams - remainingTeams +1); i++){
                currentTeams[currentPlace] = i;
                //evaluate current teams
                recursiveTeamOptimizer(i, currentPlace + 1);
            }
        }
        
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
    
    //public class Combination {
 
    public int[][] combinationGenerator(int[]  elements, int K){
 
        // get the length of the array
        // e.g. for {'A','B','C','D'} => N = 4 
        int N = elements.length;
         
        if(K > N){
            System.out.println("Invalid input, K > N");
            return null;
        }
        // calculate the possible combinations
        // e.g. c(4,2)
        int possibilities = possibleComb(N,K);
        
        
        System.out.println(possibilities+" possible combinations");
        
        int[][] out = new int[possibilities][K];
        int counter =0;
         
        // get the combination by index 
        // e.g. 01 --> AB , 23 --> CD
        int combination[] = new int[K];
         
        // position of current index
        //  if (r = 1)              r*
        //  index ==>        0   |   1   |   2
        //  element ==>      A   |   B   |   C
        int r = 0;      
        int index = 0;
         
        while(r >= 0 && counter < possibilities){
            // possible indexes for 1st position "r=0" are "0,1,2" --> "A,B,C"
            // possible indexes for 2nd position "r=1" are "1,2,3" --> "B,C,D"
             
            // for r = 0 ==> index < (4+ (0 - 2)) = 2
            if(index <= (N + (r - K))){
                    combination[r] = index;
                     
                // if we are at the last position print and increase the index
                if(r == K-1){
 
                    //do something with the combination e.g. add to list or print
                    //print(combination, elements);
                    //out[counter]= combination;
                    
                    
                    for( int z = 0; z < K; z++){
                        out[counter][z] = combination[z];
                    }
                    counter++;
                    index++;                
                }
                else{
                    // select index for next position
                    index = combination[r]+1;
                    r++;                                        
                }
            }
            else{
                r--;
                if(r > 0)
                    index = combination[r]+1;
                else
                    index = combination[0]+1;   
            }           
        }
        return out;
    }
    
    private int possibleComb(int n, int k){
        long num = 1;
        for( int i = n; i > (n-k); i--){
            num *= i;
        }
        long den = 1;
        for( int j = k; j > 0; j--){
            den *= j;
        }
        long tempOut = num/den;
        int out = (int)(tempOut);
        return out;
    }
    
    private long possibleCombLong(int n, int k){
        long num = 1;
        for( int i = n; i > (n-k); i--){
            num *= i;
        }
        long den = 1;
        for( int j = k; j > 0; j--){
            den *= j;
        }
        long out = num/den;
        return out;
    }
    
    private int factorial(int n){
        int out = n;
        for( int i = n; i > 0; i--){
            out *=i;
        }
        return out;
    }
}