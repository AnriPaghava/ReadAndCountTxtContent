package TaskTrend;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileWriter;    
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
public class ReadAndCount {
	@SuppressWarnings("unchecked")
	static void ReadFilesAndCountWords(String folderPath, String FilePath) throws IOException {
		  File directoryPath = new File(folderPath);
	      File filesList[] = directoryPath.listFiles();
	      int countAll = 0;
	      JSONArray txtList = new JSONArray();
	      int CountOnlyTxt = 0;
	      for(File file : filesList) {
	    	  int count =0;
		      JSONObject txtDetails = new JSONObject();
	    	  if (file.isFile() && file.getName().endsWith(".txt")) {
			         FileInputStream fis = new FileInputStream(file);
			         byte[] bytesArray = new byte[(int)file.length()];
			         fis.read(bytesArray);
			         String[] s = (new String(bytesArray)).split(" ");
			         CountOnlyTxt = CountOnlyTxt + 1;
			         for (int i=0; i<s.length; i++) {
			        	if (!s[i].isBlank()) {
				            count++;
			        	}
			         }
			         countAll = countAll + count;
			         txtDetails.put("Filename", file.getName());
			         txtDetails.put("Word_Count", count);
			         fis.close();
	    		  } 
		        txtList.add(txtDetails);
	      }
	      JSONObject mainObj = new JSONObject();
	      mainObj.put("Details", txtList);
	      mainObj.put("total_words:", countAll);
	      mainObj.put("total_Files:", CountOnlyTxt);
	  	  try (FileWriter filejson = new FileWriter(FilePath)) {
	       	 filejson.write(mainObj.toJSONString()); 
	       	 filejson.flush();
	 
	       } catch (IOException e) {
	            e.printStackTrace();
	       }
	   }
	private static String readPath(BufferedReader bufferedReader) throws IOException {
	    boolean ok = false;
	    File f;
	    do {
	        System.out.println("Please enter a Path:");
	        f = new File(bufferedReader.readLine());
	        if(f.isDirectory())
	            ok = true;
	        else
	            System.err.println("Doesn't exist or is not a folder.");
	    } while(!ok);
	    return f.getAbsolutePath();
	}
	private static String CheckFile(BufferedReader bufferedReader) throws IOException {
	    boolean ok = false;
	    File f;
	    do {
	        System.out.println("Please enter a File Path:");
	        f = new File(bufferedReader.readLine());
	        if (!f.isDirectory() && f.isFile() && f.getName().endsWith(".json"))
	        	ok=true;
	        else
	            System.err.println("Doesn't exist or is not a JSO File.");
	    } while(!ok);
	    return f.getAbsolutePath();
	}
	public static void main(String args[])throws IOException{
		ReadFilesAndCountWords(readPath(new BufferedReader(new InputStreamReader(System.in))), CheckFile(new BufferedReader(new InputStreamReader(System.in))));
	}
}	
