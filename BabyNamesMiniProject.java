
/**
 * Write a description of BabyNamesMiniProject here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.io.*;
import org.apache.commons.csv.*;

public class BabyNamesMiniProject {
    
    /*
     * Modify the method totalBirths (shown in the video for this project) 
     * to also print the number of girls names , 
     * the number of boys names and the total names in the file.
     */
    
    public void totalBirths(FileResource fr){
        //initialise variables for total births
        int totalBirths = 0;
        int totalGirls = 0;
        int girlNames = 0;
        int totalBoys = 0;
        int boyNames = 0;
        
        //intialise for loop and traverse over CSVParser
        // we have used false in CSVParser method because we need a parser with no header row
        //there is no header row, we'll need to access the data in each record by indexing
        for(CSVRecord rec: fr.getCSVParser(false)){
            //use parseInt method to store the value into variable
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn; 
            if(rec.get(1).equals("F")){
                totalGirls += numBorn; 
                girlNames ++;
            }
            else{
                totalBoys += numBorn;
                boyNames++;
            }
        }
        //print all the biths
        System.out.println("total births = "+totalBirths);
        System.out.println("total number of girls = "+totalGirls);
        System.out.println("total number of boys = "+totalBoys);
        System.out.println("total names of girls = "+girlNames);
        System.out.println("total names of boys = "+boyNames);
    }
    public void testTotalBirths(){
            FileResource fr = new FileResource();
            totalBirths(fr);
    }
    
    
    /*
     * Write the method named getRank that has three parameters: an integer named year, a string named name, and 
     * a string named gender (F for female and M for male). This method returns the rank of the name in the file 
     * for the given gender,  where rank 1 is the name with the largest number of births. If the name is not in 
     * the file, then -1 is returned.  For example, in the file "yob2012short.csv", given the name Mason, 
     * the year 2012 and the gender ‘M’, the number returned is 2, as Mason is the boys name with the second 
     * highest number of births. Given the name Mason, the year 2012 and the gender ‘F’, 
     * the number returned is -1 as Mason does not appear with an F in that file.
     */
    
    public int getRank(int year, String name, String gender){
        int rank = 0;
        int temp = 0;
        //FileResource to select the file
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/yob"+year+".csv");
        
        for(CSVRecord rec: fr.getCSVParser(false)){
            if(rec.get(1).equals(gender)){
                //rank will be incremented each time when rec will traverse over parser 
                //until the condition for gender is true 
                rank++;
                if(rec.get(0).equals(name)){
                    // if the name matches temp will be set to 1 and comes out of loop
                    temp = 1;
                    break;
                }
            }
        }
        if(temp == 1) return rank;
        else return -1;
    }
    public void testGetRank(){
        int rank = getRank(1971, "Frank", "M");
        System.out.println("Rank of frank in 1971 is "+rank);
        
    }
    
    /*
     * Write the method named getName that has three parameters: an integer 
     * named year, an integer named rank, and a string named gender (F for 
     * female and M for male). This method returns the name of the person in 
     * the file at this rank, for the given gender, where rank 1 is the name with the 
     * largest number of births. If the rank does not exist in the file, then 
     * “NO NAME”  is returned.
     */
    
    public String getName(int year, int rank, String gender){
        String name=null;
        int num = 0;
        String f = "yob"+year+".csv";
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/"+f);
        for(CSVRecord rec: fr.getCSVParser(false)){
            if(rec.get(1).equals(gender)){
                //num will be incremented each time when rec will traverse over parser 
                //until the condition for gender is true 
                num++;
                
                //when num matches the given rank
                if(num == rank){
                    //then get the name
                    name =  rec.get(0);
                }
            }
        }
        if(name == null) return "NO NAME";
        else return name;
    }
    public void testGetName(){
        String name = getName(1982, 450, "M");
        System.out.println("Name of boy in 1982 at 450 rank is = "+name);
    }
    
    /*
     * What would your name be if you were born in a different year? Write the 
     * void method named whatIsNameInYear that has four parameters: a string name, 
     * an integer named year representing the year that name was born,  
     * an integer named newYear and a string named gender (F for female and M for male). 
     * This method determines what name would have been named if they were born in a 
     * different year, based on the same popularity. That is, you should determine 
     * the rank of name in the year they were born, and then print the name born in newYear 
     * that is at the same rank and same gender. For example, using the files "yob2012short.csv" 
     * and "yob2014short.csv", notice that in 2012 Isabella is the third most popular girl's name. 
     * If Isabella was born in 2014 instead, she would have been named Sophia, 
     * the third most popular girl's name that year.
     */
    
    public void whatIsNameInYear(String name, int year, int newYear, String gender){
        //get rank by calling getRank method and passing given parameters
        int rank = getRank(year, name, gender);
        if(rank == -1){
            System.out.println("No name like this in the file");
        }
        else{
            //get name by calling getName method by passing given parameters
            String newName = getName(newYear, rank, gender);
            System.out.println(name+" born in "+year+" would be "+newName+" if she was born in "+newYear);
        }
    }
    public void testWhatIsNameInYear(){
        whatIsNameInYear("Owen", 1974, 2014, "M");
    }
    
    /*
     * Write the method yearOfHighestRank that has two parameters: a string 
     * name, and a string named gender (F for female and M for male). This 
     * method selects a range of files to process and returns an integer, the year 
     * with the highest rank for the name and gender. If the name and gender are 
     * not in any of the selected files, it should return -1. For example, calling 
     * yearOfHighestRank with name Mason and gender ‘M’ and selecting the 
     * three test files above results in returning the year 2012. That is because 
     * Mason was ranked the  2nd most popular name in 2012, ranked 4th in 2013 and 
     * ranked 3rd in 2014. His highest ranking was in 2012.
     */
    
    public int yearOfHighestRank(String name, String gender){
        int highestRank = 0, highYear=0;
        //directory resource is used to traverse over many files
        DirectoryResource dr = new DirectoryResource();
        //traverse over files using for loop
        for(File f: dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            int rank = 0, num = 0;
            //get the name of the file as String 
            String fname = f.getName();
            
            //get year of file as integer
            int year = Integer.parseInt(fname.replaceAll("[\\D]", ""));
            //traverse over records in that file
            for(CSVRecord rec : fr.getCSVParser(false)){
                if(rec.get(1).equals(gender)){
                    //rank will be incremented each time when rec will traverse over parser 
                    //until the condition for gender is true
                    rank++;
                    if(rec.get(0).equals(name)){
                        num = 1;
                        break;
                    }
                }
            }
            
            //if name and gender matches and hihgerRank is null
            //set higherRank as rank, highYear as year of that file
            if(num == 1 && highestRank == 0){ highestRank = rank; highYear = year; }
            
            //if higherRank is less than rank, set higherRank as rank and highYear as year of that file
            // so that we get the year in which the name is at the toppest rank
            else if(num == 1 && rank != 0 && rank < highestRank){
                highestRank = rank;
                highYear = year;
            }
        }
        return highYear;
    }
    public void testYearOfHighestRank(){
        String name = "Mich";
        int year = yearOfHighestRank(name, "M");
        if(year == 0){ System.out.println("-1");}
        else {System.out.println(name+" has highest rank in "+year);}
    }
    
    /*
     * Write the method getAverageRank that has two parameters: a string name, 
     * and a string named gender (F for female and M for male). This method 
     * selects a range of files to process and returns a double representing 
     * the average rank of the name and gender over the selected files. 
     * It should return -1.0 if the name is not ranked in any of the selected files. 
     * For example calling getAverageRank with name Mason and gender ‘M’ and 
     * selecting the three test files above results in returning 3.0, 
     * as he is rank 2 in the year 2012, rank 4 in 2013 and rank 3 in 2014.  
     * As another example, calling   getAverageRank with name Jacob and 
     * gender ‘M’ and selecting the three test files above results in returning 2.66.
     */
    
    public double getAverageRank(String name, String gender){
        double highestRank = 0;
        double avg = 0;
        int n = 0;
        DirectoryResource dr = new DirectoryResource();
        for(File f: dr.selectedFiles()){
            //n will be incremented to calculate total number of files selected
            n++;
            FileResource fr = new FileResource(f);
            int rank = 0, num = 0;
            for(CSVRecord rec : fr.getCSVParser(false)){
                if(rec.get(1).equals(gender)){
                    //rank will be incremented each time when rec will traverse over parser 
                    //until the condition for gender is true
                    rank++;
                    if(rec.get(0).equals(name)){
                        //num will set to 1 if both gender and name matched and come out of loop
                        num = 1;
                        break;
                    }
                }
            }
            //highestRank is null, and name and gender matches the condition, set higherRank as rank
            if(num == 1 && highestRank == 0){ highestRank = rank;}
            else{
                //once the highestRank becomes not null, it will add up every time 
                // calculated total rank of that name in all files
                highestRank += rank;
            }
        }
        //calculate average by dividing highestRank(total of all ranks) with n(total number of files) 
        avg = highestRank/n;
        return avg;
    }
    public void testGetAverageRank(){
        String n = "Robert"; 
        double avg = getAverageRank(n, "M");
        if(avg <= 0){ System.out.println("-1"); }
        else{ System.out.println("Average rank of "+n+" is "+avg);}
    }
    
    /*
     * Write the method getTotalBirthsRankedHigher that has three parameters: 
     * an integer named year, a string named name, and a string named gender 
     * (F for female and M for male). This method returns an integer, 
     * the total number of births of those names with the same gender and 
     * same year who are ranked higher than name. For example, if 
     * getTotalBirthsRankedHigher accesses the "yob2012short.csv" file with 
     * name set to “Ethan”, gender set to “M”, and year set to 2012, then this 
     * method should return 15, since Jacob has 8 births and Mason has 7 births, 
     * and those are the only two ranked higher than Ethan. 
     */
    
    public int getTotalBirthsRankedHigher(int year, String name, String gender){
        int total = 0;
        
        String n = "yob"+year+".csv";
        FileResource fr = new FileResource("us_babynames/us_babynames_by_year/"+n);
        for(CSVRecord rec: fr.getCSVParser(false)){
            
            //store total number of borns as integer
            int numBorns = Integer.parseInt(rec.get(2));
            if(rec.get(1).equals(gender)){
                if(rec.get(0).equals(name)){
                    //return total if gender and name matched with the condition 
                    return total;
                }
                //if the name not matches, then total will be incremented by total number of borns
                total += numBorns;
            }
        }
        // returns total births that are ranked higher than that name
        return total;
    }
    public void testGetTotalBirthsRankedHigher(){
        String n = "Drew";
        int higher = getTotalBirthsRankedHigher(1990, n, "M");
        System.out.println("Total Births ranked higher than "+n+" are "+higher);
    }
}
