package com.esamarathon.bigredbutton;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;


public class BigRedButton
{
  public String IP;
  public String APIKEY;
  
  public BigRedButton() {}
  
  public static void main(String[] args)
    throws InterruptedException, IOException
  {
    BigRedButton bigRedButton = new BigRedButton();
    
    System.out.println("Starting Big Red Button!");
    System.out.println("http://10.20.30.41:3333/api/bigredbutton/:id");
    



    GpioController gpio = GpioFactory.getInstance();
    

    GpioPinDigitalInput bigRedButton_1 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, "Button 1", PinPullResistance.PULL_UP);
    GpioPinDigitalInput bigRedButton_2 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, "Button 2", PinPullResistance.PULL_UP);
    GpioPinDigitalInput bigRedButton_3 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04, "Button 3", PinPullResistance.PULL_UP);
    GpioPinDigitalInput bigRedButton_4 = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05, "Button 4", PinPullResistance.PULL_UP);
    

    bigRedButton_1.addListener(new GpioPinListener[] { new GpioPinListenerDigital()
    {
      public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event)
      {
        System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
        if (event.getState().isLow())
        {
          try
          {
            bigRedButton.sendSplit("1");
          } catch (IOException ex) {
            Logger.getLogger(BigRedButton.class.getName()).log(Level.SEVERE, null, ex);

          }
          
        }
        
      }
      

    } });
    bigRedButton_2.addListener(new GpioPinListener[] { new GpioPinListenerDigital()
    {
      public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event)
      {
        System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
        
        if (event.getState().isLow())
        {
          try
          {
            bigRedButton.sendSplit("2");
          } catch (IOException ex) {
            Logger.getLogger(BigRedButton.class.getName()).log(Level.SEVERE, null, ex);
          }
          
        }
        
      }
      
    } });
    bigRedButton_3.addListener(new GpioPinListener[] { new GpioPinListenerDigital()
    {
      public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event)
      {
        System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
        
        if (event.getState().isLow())
        {
          try
          {
            bigRedButton.sendSplit("3");
          } catch (IOException ex) {
            Logger.getLogger(BigRedButton.class.getName()).log(Level.SEVERE, null, ex);
          }
          
        }
        
      }
      
    } });
    bigRedButton_4.addListener(new GpioPinListener[] { new GpioPinListenerDigital()
    {
      public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event)
      {
        System.out.println(" --> GPIO PIN STATE CHANGE: " + event.getPin() + " = " + event.getState());
        
        if (event.getState().isLow()) {
          try
          {
            bigRedButton.sendSplit("4");
          } catch (IOException ex) {
            Logger.getLogger(BigRedButton.class.getName()).log(Level.SEVERE, null, ex);
          }
          
        }
        
      }
      
    } });
    System.out.println(" ... complete the GPIO #02 circuit and see the listener feedback here in the console.");
    
    for (;;)
    {
      Thread.sleep(100);
    }
  }
  






  public void sendSplit(String nr)
    throws UnsupportedEncodingException, IOException
  {
    HttpClient httpclient = HttpClients.createDefault();
    HttpPost httppost = new HttpPost("http://10.20.30.41:3333/api/bigredbutton/" + nr);
    

    List<NameValuePair> params = new ArrayList(2);
    

    httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
    

    HttpResponse response = httpclient.execute(httppost);
    HttpEntity entity = response.getEntity();
    
    if (entity != null) {
      InputStream instream = entity.getContent();
      


      instream.close();
    }
  }
  






  public void loadConfig()
    throws FileNotFoundException, IOException
  {
    APIKEY = "WodkaCruise";
    IP = "10.20.30.41:3333";
    

    InputStream fis = new FileInputStream("/etc/bigredbutton.txt");Throwable localThrowable9 = null;
    try {
      InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));Throwable localThrowable10 = null;
      try { BufferedReader br = new BufferedReader(isr);Throwable localThrowable11 = null;
        try { String line;
          while ((line = br.readLine()) != null) {
            if (!line.contains("#")) {
              if (line.contains("IP:")) {
                String temp = line;
                IP = StringUtils.remove(temp, "IP:");
                IP = StringUtils.remove(IP, " ");
              }
              if (line.contains("APIKEY:")) {
                String temp = line;
                APIKEY = StringUtils.remove(temp, "APIKEY:");
                APIKEY = StringUtils.remove(APIKEY, " ");
              }
            }
          }
        }
        catch (Throwable localThrowable1)
        {
          localThrowable11 = localThrowable1;throw localThrowable1; } finally {} } catch (Throwable localThrowable4) { String line; localThrowable10 = localThrowable4;throw localThrowable4; } finally {} } catch (Throwable localThrowable7) { localThrowable9 = localThrowable7;throw localThrowable7;








    }
    finally
    {







      if (fis != null) if (localThrowable9 != null) try { fis.close(); } catch (Throwable localThrowable8) { localThrowable9.addSuppressed(localThrowable8); } else fis.close();
    }
  }
}
