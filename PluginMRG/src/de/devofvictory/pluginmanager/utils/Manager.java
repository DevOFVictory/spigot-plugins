package de.devofvictory.pluginmanager.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class Manager
{
  public static String getPluginVersion(String id)
  {
    if (existPlugin(id)) {
      try
      {
        return getUrlSource("https://api.spigotmc.org/legacy/update.php?resource=" + id);
      }
      catch (IOException e)
      {
        e.printStackTrace();
        return null;
      }
    }
    return null;
  }
  
  public static String getPluginDownloadURL(String id)
  {
    if (existPlugin(id)) {
      return "https://api.spiget.org/v2/resources/" + id + "/download";
    }
    return null;
  }
  
  public static boolean existPlugin(String id)
  {
    try
    {
      if (!getUrlSource("https://api.spigotmc.org/legacy/update.php?resource=" + id).contains("Invalid resource")) {
        return true;
      }
      return false;
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return false;
  }
  
  public static void downloadFile(File destination, String url)
  {
    try
    {
      HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
      connection.connect();
      
      FileOutputStream outputStream = new FileOutputStream(destination);
      InputStream inputStream = connection.getInputStream();
      byte[] buffer = new byte['€'];
      int readBytes = 0;
      while ((readBytes = inputStream.read(buffer)) > 0) {
        outputStream.write(buffer, 0, readBytes);
      }
      outputStream.close();
      inputStream.close();
      connection.disconnect();
    }
    catch (Exception localException) {}
  }
  
  private static String getUrlSource(String link)
    throws UnsupportedEncodingException, IOException
  {
    URL url = new URL(link);
    URLConnection urlConn = url.openConnection();
    BufferedReader in = new BufferedReader(new InputStreamReader(
      urlConn.getInputStream(), "UTF-8"));
    
    StringBuilder a = new StringBuilder();
    String inputLine;
    while ((inputLine = in.readLine()) != null)
    {

      a.append(inputLine);
    }
    in.close();
    
    return a.toString();
  }
}
