import java.io.*;
import java.util.*;

//class takes care of downloading all the html source files
//from the Yahoo! Finance database
public class DownloadAllData
{
    //driver method using list of tickers as input
    public static void main(String[] args) throws IOException
    {
        //declare input stuff
        File nyseCSV = new File("Files/nyse-list.csv");
        File nasdaqCSV=new File("Files/nasdaq-list.csv");
        
        ArrayList<String> nyseList = DataTools.readInTickers(nyseCSV);
        ArrayList<String> nasdaqList = DataTools.readInTickers(nasdaqCSV);
        ArrayList<String> allTickers = DataTools.combineNoRepeats(nyseList,nasdaqList);
        
        //declare output stuff
        File temp;
        
        //get & output desired info page-by-page (don't lose all data in case of crash; buffer every few pages to avoid overflow & preserve data)
        //output NYSE
        for (String currTicker:allTickers)
        {
            System.out.println(currTicker);
            temp = new File("Files/"+currTicker+".txt");
            WebTools.writeSourceToFile(YahooFinance.KEY_STATS_PAGE.replace("TICKER",currTicker),temp);
        }
        
        //close output and input streams
    }
   
    
}