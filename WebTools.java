import java.net.*;
import java.io.*;

//class takes care of all the source download aspects
public class WebTools
{
    //downloads the html source of the passed url
    public static String getSource(String url) throws IOException
    {
        URL pageLocation = new URL(url);
        BufferedReader in = new BufferedReader(new InputStreamReader(pageLocation.openStream()));
        
        String inputLine;
        StringBuilder sb = new StringBuilder("");
        while ((inputLine = in.readLine()) != null)
        {
            sb.append(inputLine);
            sb.append("\n");
        }
        return sb.toString();
    }
    
    //outputs the html source at the given url to the given file
    public static void writeSourceToFile(String url, File fout) throws IOException
    {
        PrintWriter out = new PrintWriter(new FileWriter(fout));
        String source = WebTools.getSource(url);
        
        out.println(source);
        
        out.close();
    }
}