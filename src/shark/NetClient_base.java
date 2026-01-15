/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shark;


import java.net.MalformedURLException;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.IOException;
import java.io.InputStream;


/**
 *
 * @author paulr
 */
public class NetClient_base implements Runnable{
    
//  String URL = "https://crm.wordshark.net/Activation/Activation1.svc/";
   String URL; 
  String method;
  String[] paramnames;
  String[] paramvalues;
  public String returnval = null;
  long inittime;
  long endtime;
  long nexttime = -1;
  int intervaltime;
  static String proxyServerName = null;
  static int proxyPort = -1;
  static String proxyUserName = null;
  static String proxyPassword = null;
  static final String GET = "GET";
  static final String POST = "POST";
  String typeGetPost;
    
    public NetClient_base(String url_, String methodName, String paramNames[], String paramVals[], int tryfortime, String httpType) {
     URL= url_;
     method = methodName;
     paramnames = paramNames;
     paramvalues = paramVals;
     inittime = System.currentTimeMillis();
     endtime = inittime + tryfortime;
     // roughly this number of  goes
     int goes = 5;
     intervaltime = tryfortime / goes;
     typeGetPost = httpType;
    }
    
    
    public void run(){
        if(typeGetPost.equals(POST))doPost();
        else if(typeGetPost.equals(GET))doGet();    
    }
    
    
    void doGet(){
    URL url = null;
    try
    {
      String sURL = URL+method;
      for(int i = 0; i < paramvalues.length; i++){
          if(paramvalues[i] == null)  paramvalues[i] = "null";
          sURL += "/"+paramvalues[i].replaceAll("/", "-");
      }
        String uri =sURL;
        url = new URL(uri);
        HttpURLConnection connection =
            (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/xml");
        InputStream xml = connection.getInputStream();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(xml);  
        returnval = doc.getFirstChild().getTextContent();

    }
    catch(Exception ee){
        int g;
         g = 0;
    }
      
      /*
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      
      // just want to do an HTTP GET here
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Accept", "application/xml");
      // uncomment this if you want to write output to this url
      //connection.setDoOutput(true);
      
      // give it 15 seconds to respond
      connection.setReadTimeout(15*1000);
      connection.connect();

      // read the output from the server
      reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      stringBuilder = new StringBuilder();

      String line = null;
      while ((line = reader.readLine()) != null)
      {
        stringBuilder.append(line);
      }
returnval = stringBuilder.toString();
//                     JSONParser parser = new JSONParser();
  //                      returnval = (String)parser.parse(stringBuilder.toString());
              int h;
              h = 8;
    }
    catch (Exception e)
    {
      e.printStackTrace();

    }
    finally
    {
      // close the reader; this can throw an exception too, so
      // wrap it in another try/catch block.
      if (reader != null)
      {
        try
        {
          reader.close();
        }
        catch (IOException ioe)
        {
          ioe.printStackTrace();
        }
      }
              */
   // }       
    }
    
    void doPost(){
        long l;
        while (true) {
            l = System.currentTimeMillis();
            if(l<endtime){
                if((nexttime<0 || l>nexttime)){
                  nexttime = l+intervaltime;
                  try {
                     String post_url = URL + method;
                        URL url = new URL(post_url);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setRequestProperty("Content-Type", "application/json");            
                        connection.setRequestProperty("Accept", "application/json");
                        connection.setDoInput(true);
                        connection.setDoOutput(true); 
                        JSONObject param=null;
                        if(paramnames!=null){
                            param=new JSONObject();
                            for(int i = 0; i < paramnames.length; i++){
                                param.put(paramnames[i], paramvalues[i]);
                            }
                        }
                        OutputStreamWriter wr= new OutputStreamWriter(connection.getOutputStream());
                        if(paramnames!=null){
                            wr.write(param.toString());
                        }
                        wr.close();
                        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuffer jsonString = new StringBuffer();
                        String line;
                        while ((line = br.readLine()) != null) {
                            jsonString.append(line);
                        }
                        br.close();
                        connection.disconnect();
                        JSONParser parser = new JSONParser();
                        returnval = (String)parser.parse(jsonString.toString());
                        break;
                    } catch (MalformedURLException e) {
                        e=e;
     //                   e.printStackTrace();
                    } catch (IOException e) {
                        e=e;
     //                   e.printStackTrace();
                    }
                    catch (Exception e) {
                        e=e;
      //                  e.printStackTrace();
                    }                
                }
            }
           else {
               break;
           }
        }        
    }

}
