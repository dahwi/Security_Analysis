import java.util.ArrayList;

//class for a ticker symbol (publicly traded security)
public class TickerSymbol
{
    //member variables
    private final String SYMBOL;                    //always uppercase, alpha, 3-5?
    private ArrayList<Pair<String,String>> data;    //fundamentals (mostly?)
    
    //constructor
    public TickerSymbol(String setSymbol)
    {
        SYMBOL = setSymbol;
        data = new ArrayList<Pair<String,String>>();
    }
    
    //keep track of var w/ max # of attributes
    private static int maxNumAttributes = 0;
    
    //getters
    public String getSymbol()
    {
        return SYMBOL;
    }
    
    //null return value indicates result not found
    public String getValue(String key)
    {
        for (Pair<String,String> curr:data)
            if (key.equals(curr.getFirst()))
                return curr.getSecond();
        return null;
    }
    
    //add an attribute to the ticker symbol (multiple methods)
    public void addAttribute(String key, String value)
    {
        Pair<String,String>temp = new Pair<String,String>(key,value);
        this.addAttribute(temp);
    }
    public void addAttribute(Pair<String,String> toAdd)
    {
        data.add(toAdd);
        maxNumAttributes = Math.max(maxNumAttributes,data.size());
    }
    
    //wrapper method to add multiple attributes
    public void addAttributes(ArrayList<Pair<String,String>> toAdd)
    {
        for (Pair<String,String>curr:toAdd)
            this.addAttribute(curr);
    }
    
    //utility methods
    public int numAttributes()
    {
        return data.size();
    }
    public static int totalAttributes()
    {
        return maxNumAttributes;
    }
    
    //print out the header line for the csv file
    public String csvHeader()
    {
        StringBuilder sb = new StringBuilder();
        if (data.size()>0)
            sb.append("Ticker Symbol");
        for (int i=0; i<data.size(); i++)
        {
            sb.append(',');
            sb.append(data.get(i).getFirst());
        }
        return sb.toString();
    }
    
    //return csv data line in order of header line
    public String csvData()   //no newline; no ordering
    {
        StringBuilder sb = new StringBuilder();
        if (data.size()>0)
            sb.append(this.getSymbol());
        for (int i=0; i<data.size(); i++)
        {
            sb.append(',');
            sb.append(""+data.get(i).getSecond()+"");   //potentially add quotes to deal with annoying data
        }
        return sb.toString();
    }
}