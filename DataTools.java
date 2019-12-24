import java.util.*;
import java.io.*;

//class contains common methods to help dealing with data
public class DataTools
{
    //private constructor to prevent instantiation
    private DataTools(){}
    
    //given a start string and end string, returns the part in the middle
    public static String getPartBetween(String all, String start, String end) throws IllegalArgumentException
    {
        int beginStrIndex = all.indexOf(start)+start.length();
        int endStrIndex = all.indexOf(end);
            
        if (beginStrIndex==-1)
            throw new IllegalArgumentException("Can't find 'start' String");
        else if (endStrIndex==-1)
            throw new IllegalArgumentException("Can't find 'end' String");
        else if (endStrIndex<=beginStrIndex)
        {
            //System.out.println(beginStrIndex+" "+endStrIndex);
            throw new IllegalArgumentException("'start' String appears after 'end String");
        }
            
        return all.substring(beginStrIndex,endStrIndex);
    }
    
    //return list of all indices of occurrence of string in larger string
    public static ArrayList<Integer> allIndicesOf(String all, String search)
    {
        ArrayList<Integer> answer = new ArrayList<Integer>();
        
        /* int currIndex = all.indexOf(search),
            totalSoFar = 0;
        while (currIndex>=0)
        {
            answer.add(totalSoFar+currIndex);
            all = all.substring(currIndex+2);
            totalSoFar += currIndex+2;
            currIndex = all.indexOf(search);
        } */
		//CHANGE
		int idx = 0
		int currIndex = all.indexOf(search, idx);
		while(currIndex >= 0){
			answer.add(currIndex);
			idx = currIndex+search.length();
			currIndex = all.indexOf(search, idx);
		}
        
        return answer;
    }
    
    //wrapper method to output an html source to a file
    public static void writeHTMLToFile(File fout, String htmlSource) throws IOException
    {
        PrintWriter out = new PrintWriter(new FileWriter(fout));
        
        out.println(htmlSource);
        
        out.close();
    }
    
    //remove commas
    public static String noCommas(String before)
    {
        StringBuilder sb = new StringBuilder("");
        
        for (int i=0; i<before.length(); i++)
            if (before.charAt(i)!=',')
                sb.append(before.charAt(i));
                
        return sb.toString();
    }
    
    //remove html tags in data
    public static String noHTMLTags(String before)
    {
        String htmlTagRegex = "\\<[^\\<\\>]*\\>";
        
        return before.replaceAll(htmlTagRegex,"");
    }
    
    //remove whitespace function to use in DownloadData class
    public static String noWhitespace(String before)
    {
        StringBuilder after = new StringBuilder("");
        
        char temp;
        for (int i=0; i<before.length(); i++)
        {
            temp = before.charAt(i);
            if (temp!=' ' && temp!='\n' && temp!='\r' && temp!='\t')
                after.append(temp);
        }
        
        return after.toString();
    }
    
    //get the list of ticker symbols from the CSV file
    protected static ArrayList<String> readInTickers(File csvTickerList) throws IOException
    {
        Scanner fin = new Scanner(csvTickerList).useDelimiter(",\n");
        ArrayList<String>tickerList = new ArrayList<String>();
        
        String tempLine, tempTicker;
        int firstCommaIndex;
        while (fin.hasNext())
        {
            tempLine = fin.nextLine();
            firstCommaIndex = tempLine.indexOf(",");
            tempTicker = tempLine.substring(0,firstCommaIndex);
            if (!tempTicker.contains("^") && !tempTicker.contains(".") && !tempTicker.equalsIgnoreCase("symbol"))
                tickerList.add(DataTools.noWhitespace(tempTicker));
        }
        
        return tickerList;
    }
    
    //sort each individual arraylist and then use a merge idea to avoid repeats
    //remove tickers shared between two indices
    //combine each ticker to uppercase before so don't have to use "equalsIgnoreCase"
    protected static ArrayList<String> combineNoRepeats(ArrayList<String> a, ArrayList<String> b)
    {
        //can't just initialize with another arraylist's contents b/c have to convert to uppercase individually
        ArrayList<String>firstCombine = new ArrayList<String>();
        firstCombine.ensureCapacity(a.size()+b.size());
        for (String curr:a)
            firstCombine.add(curr.toUpperCase());
        for (String curr:b)
            firstCombine.add(curr.toUpperCase());
            
        Collections.sort(firstCombine);
        
        ArrayList<String>answer = new ArrayList<String>();
        answer.ensureCapacity(firstCombine.size());
        
        answer.add(firstCombine.get(0));
        for (int i=1; i<firstCombine.size(); i++)
        {
            if (!firstCombine.get(i).equals(firstCombine.get(i-1)))
                answer.add(firstCombine.get(i));
        }
        
        return answer;
    }
    
    //remove all characters that are not digits or periods
    public static String digitDotOnly(String a)
    {
        StringBuilder sb = new StringBuilder("");
        for (int i=0; i<a.length(); i++)
            if (a.charAt(i)>='0' && a.charAt(i)<='9' || a.charAt(i)=='.')
                sb.append(a.charAt(i));
        return sb.toString();
    }
    
    //quick method for correct significant digits
    public static String dotUntilNum(String a, int desiredLen)
    {
        int numAdd = desiredLen-a.length();
        StringBuilder sb = new StringBuilder(a);
        for (int i=0; i<numAdd; i++)
            sb.append('.');
        return sb.toString();
    }
}