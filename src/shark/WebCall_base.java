package shark;
import java.net.*;
import org.apache.soap.util.xml.*;
import org.apache.soap.*;
import org.apache.soap.encoding.*;
import org.apache.soap.encoding.soapenc.*;
import org.apache.soap.rpc.*;
import org.apache.soap.transport.http.*;

import java.util.*;

public class WebCall_base implements Runnable{
  String url_;
  String namespace;
  String method;
  String[] paramnames;
  String[] paramvalues;
  public String returnval = null;
  long inittime;
  long endtime;
  long nexttime = -1;
  int intervaltime;
  int count1 = 0;
  static String proxyServerName = null;
  static int proxyPort = -1;
  static String proxyUserName = null;
  static String proxyPassword = null;


  public WebCall_base(String ur, String ur_subfolders, String ur_file, String names, String meth, String[] paramns, String[] paramvs, int tryfortime) {
//   url_ = ur+(ur_subfolders==null?"":ur_subfolders)+"/"+ur_file;
//   namespace = names+(names.endsWith("/")?"":"/");
   url_ = ur+(ur.endsWith("/")?"":"/")+(ur_subfolders==null?"":(ur_subfolders+(ur_subfolders.endsWith("/")?"":"/")))+ur_file;
   namespace = names+(names.endsWith("/")?"":"/");
   method = meth;
   paramnames = paramns;
   paramvalues = paramvs;
   inittime = System.currentTimeMillis();
   endtime = inittime + tryfortime;
   // roughly this number of  goes
   int goes = 5;
   intervaltime = tryfortime / goes;
  }
  public void run(){
    long l;
    while (true) {
        l = System.currentTimeMillis();
        if(l<endtime){
            if((nexttime<0 || l>nexttime)){
              nexttime = l+intervaltime;
              try {
                String sNamespace = namespace;
                String sMethod = method;
                URL url = new URL(url_);
                SOAPMappingRegistry smr = new SOAPMappingRegistry();
                BeanSerializer beanSer = new BeanSerializer();
                StringDeserializer sd = new StringDeserializer();
                for (int i = 0; paramnames!=null && i < paramnames.length; i++) {
                  smr.mapTypes(Constants.NS_URI_LITERAL_XML, new QName(sNamespace, paramnames[i]), String.class, beanSer, beanSer);
                }
                smr.mapTypes(Constants.NS_URI_SOAP_ENC, new QName(sNamespace, sMethod + "Result"), null, null, sd);
                // Build the call.
                Call call = new Call();
                call.setTargetObjectURI(sNamespace);
                call.setMethodName(sMethod);
                call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);
                call.setSOAPMappingRegistry(smr);
                //Build the params
                Vector params = new Vector();
                for (int i = 0; paramnames!=null && i < paramnames.length; i++) {
                  params.addElement(new Parameter(paramnames[i], String.class, paramvalues[i], null));
                }
                call.setParams(params);
                
                // http connection info
                SOAPHTTPConnection http = new SOAPHTTPConnection();
                boolean any = false;
                if(proxyServerName!=null){
                    http.setProxyHost(proxyServerName);
                    any = true;
                }
                if(proxyPort>=0){
                    http.setProxyPort(proxyPort);
                    any = true;
                }
                if(any && proxyPassword != null && proxyUserName!=null){                    
                    http.setProxyUserName(proxyUserName);
                    http.setProxyPassword(proxyPassword);  
                }
                if(any) call.setSOAPTransport(http) ;

                // Invoke the call.
                Response resp = null;
                String retstr = null;
                try {
                  resp = call.invoke(url, sNamespace + sMethod);
                } catch (SOAPException e) {
                }
                // Check the response.
                if (resp == null) {
                  continue;
                }
                if (!resp.generatedFault()) {
                  Parameter ret = resp.getReturnValue();
                  Object value = ret.getValue();
                  retstr = String.valueOf(value);
                  returnval = retstr;
                  break;
                }
                else {
                  Fault fault = resp.getFault();
                  System.err.println("Generated fault: ");
                  System.out.println("  Fault Code   = " + fault.getFaultCode());
                  System.out.println("  Fault String = " + fault.getFaultString());
                }
              } catch (Exception excep) {
              }
            }
        }
       else {
           break;
       }
    }
  }
}
