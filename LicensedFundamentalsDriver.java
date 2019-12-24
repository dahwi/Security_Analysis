import java.io.*;
import java.util.*;

//driver for all the work
public class LicensedFundamentalsDriver
{
    //driver initial method
    public static void main(String[] args) throws IOException
    {
        PrintWriter fout = new PrintWriter(new FileWriter("Scraped Dataset.csv"));
        File nyseList = new File("nyse-list.csv"),
             nasdaqList=new File("nasdaq-list.csv");
        ArrayList<String>allSymbols = DataTools.combineNoRepeats(DataTools.readInTickers(nyseList),DataTools.readInTickers(nasdaqList));
        
        //TickerSymbol[] allTickers = new TickerSymbol[allSymbols.size()];
        TickerSymbol current;
        for (int i=0; i<allSymbols.size(); i++)
        {
            System.out.print(DataTools.dotUntilNum(allSymbols.get(i),15));
            current = new TickerSymbol(allSymbols.get(i));
            current.addAttributes(YahooFinance.getAllKeyStats(current.getSymbol()));    //add key stats
            current.addAttributes(YahooFinance.getAnalystOpinion(current.getSymbol()));  //add analyst opinion
            System.out.print("PARSED..........");
            if (i==0)
                fout.println(current.csvHeader());
            fout.println(current.csvData());
            System.out.println("PRINTED TO FILE");
            if (i%50==0 && i>0)
            {
                fout.flush();
                System.out.println("OUTPUT BUFFER FLUSHED AT N="+i);
            }
        }
        System.out.println("\n\nTASK COMPLETED");
        fout.close();
    }
    
}