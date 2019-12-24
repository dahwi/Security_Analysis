import java.util.ArrayList;
import java.io.IOException;

//class that takes care of everything with yahoo finance database
public class YahooFinance
{
    //private constructor to prevent instantiation
    private YahooFinance(){}
    
    private static final String HEAD_START_STRING = "<td class=\"yfnc_tablehead1\" width=\"74%\">",
                                DATA_START_STRING = "td class=\"yfnc_tabledata1\">",
                                HEAD_END_STRING_1 = "<font",
                                HEAD_END_STRING_2 = "</td>",
                                DATA_END_STRING = "</td>";
    
    //wrapper method to extract all values from Key Statistics page
    public static ArrayList<Pair<String,String>> getAllKeyStats(String ticker) throws IOException
    {
        return getAllKeyStatsFromSource(getKeyStatsHTML(ticker));
    }
    
    //actual method that extracts all values from Key Statistics page on Yahoo! Finance database
    private static ArrayList<Pair<String,String>> getAllKeyStatsFromSource(String source)
    {
        ArrayList<Pair<String,String>>keyStats = new ArrayList<Pair<String,String>>();
        //ArrayList<Integer>headLocations = DataTools.allIndicesOf(source,HEAD_START_STRING);
        
        Pair<String,String>tempPair;
        String tempFirst, tempSecond;
        String tempSource = source;
        int firstHeadIndex = tempSource.indexOf(HEAD_START_STRING);
        int endHeadIndex, firstDataIndex, endDataIndex;
        while (firstHeadIndex>=0)
        {
            //table head
            tempSource = tempSource.substring(firstHeadIndex);
            firstHeadIndex = HEAD_START_STRING.length();
            endHeadIndex = closerStringIndex(tempSource.indexOf(HEAD_END_STRING_1),tempSource.indexOf(HEAD_END_STRING_2));
            tempFirst = tempSource.substring(firstHeadIndex,endHeadIndex);
            
            //table data
            firstDataIndex = tempSource.indexOf(DATA_START_STRING);
            tempSource = tempSource.substring(firstDataIndex);
            firstDataIndex = DATA_START_STRING.length();
            endDataIndex = tempSource.indexOf(DATA_END_STRING);
            tempSecond = tempSource.substring(firstDataIndex, endDataIndex);
            
            //update
            tempPair = new Pair<String,String>(DataTools.noHTMLTags(DataTools.noCommas(tempFirst)),DataTools.noHTMLTags(DataTools.noCommas(tempSecond)));
            keyStats.add(tempPair);
            firstHeadIndex = tempSource.indexOf(HEAD_START_STRING);
        }
        
        return keyStats;
    }
    
    private final static String MEAN_RECOMMENDATION_START = "Mean Recommendation (this week):";
    private final static String MEAN_NUM_BROKER_START = "No. of Brokers";
    private final static int DATA_AFTER_HEADER = 45+MEAN_RECOMMENDATION_START.length();    //number of characters past which it's guaranteed data is within range of header
    
    //method (and wrapper) that extracts mean analyst recommendation from analyst opinion page
    public static ArrayList<Pair<String,String>> getAnalystOpinion(String ticker) throws IOException
    {
        return getAnalystOpinionFromSource(getAnalystOpinionHTML(ticker));
    }
    private static ArrayList<Pair<String,String>> getAnalystOpinionFromSource(String source)
    {
        //add analyst opinion then no. brokers
        ArrayList<Pair<String,String>>answer = new ArrayList<Pair<String,String>>();
        
        String tempFirst, tempSecond;
        int headIndex = source.indexOf(MEAN_RECOMMENDATION_START),
            endHeadIndex = headIndex+MEAN_RECOMMENDATION_START.length();
        int dataIndex = endHeadIndex+10, endDataIndex = DATA_AFTER_HEADER+headIndex;
        
        //some tickers don't have an analyst opinion
        if (headIndex<0)
        {
            Pair temp = new Pair<String,String>(MEAN_RECOMMENDATION_START, ""),
                 temp2= new Pair<String,String>(MEAN_NUM_BROKER_START, "");
            answer.add(temp);
            answer.add(temp2);
            return answer;
        }
        
        tempFirst = MEAN_RECOMMENDATION_START;
        tempSecond= DataTools.digitDotOnly(DataTools.noHTMLTags(source.substring(dataIndex, endDataIndex))).substring(1);    //remove first character b/c for some reason a '1' is always prepended (too lazy to actually check it out)
        answer.add(new Pair<String,String>(tempFirst,tempSecond));
        
        //add no. of brokers
        headIndex = source.indexOf(MEAN_NUM_BROKER_START);
        endHeadIndex = headIndex+MEAN_NUM_BROKER_START.length();
        dataIndex = endHeadIndex+10;
        endDataIndex = DATA_AFTER_HEADER+headIndex;
        
        tempFirst = MEAN_NUM_BROKER_START;
        tempSecond= DataTools.digitDotOnly(DataTools.noHTMLTags(source.substring(dataIndex, endDataIndex))).substring(1);   //remove first character b/c for some reason a '1' is always prepended (too lazy to actually check it out)
        answer.add(new Pair<String,String>(tempFirst,tempSecond));
        
        return answer;
    }
    
    //utility method to find attributes within source
    private static int closerStringIndex(int index1, int index2)
    {
        if (index1<0 && index2<0) return index2;
        if (index2<0) return index1;
        return Math.min(index1,index2);
    }
    
    protected static final String KEY_STATS_PAGE = "http://finance.yahoo.com/q/ks?s=TICKER+Key+Statistics";
    //download source for keystats page from yahoo! finance for given ticker symbol
    private static String getKeyStatsHTML(String ticker) throws IOException
    {
        return WebTools.getSource(KEY_STATS_PAGE.replace("TICKER",ticker));
    }
    
    protected static final String ANALYST_OPINION_PAGE = "http://finance.yahoo.com/q/ao?s=TICKER+Analyst+Opinion";
    //download source for analyst opinion page from Yahoo! finance for given ticker symbol
    private static String getAnalystOpinionHTML(String ticker) throws IOException
    {
        return WebTools.getSource(ANALYST_OPINION_PAGE.replace("TICKER",ticker));
    }
}