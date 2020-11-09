/*
 * NAME: Aaron
 */

import java.io.*;
import java.util.*;
import java.text.*;

public class TracerouteAnalysis {
	public static void main(String args[]) {
		String dumpFile = "/Users/aaron/Documents/dump.txt"; //dump file
		String analysis = "/Users/aaron/Documents/output.txt"; //output file
		
		String line = null;
	
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(dumpFile)); //Open up the dump file
			ArrayList<String> fileholder = new ArrayList<String>(); //create new ArrayList
			FileWriter fileWriter = new FileWriter(analysis); //create an output file
			PrintWriter printWriter = new PrintWriter(fileWriter); //open up a writer to write into the file
		
			/*
			 * Reading from dump file
			 */
			
			//Add lines from dump file to ArrayList
			while((line = bufferedReader.readLine()) != null) {
				fileholder.add(line);	
			} //end while((line = bufferedReader.readLine()) != null)
			
			int placeholder = 0; //for the current position
			int writerMan = 0; //for making sure every 3 lines has a ttl
			
			double timeDifference; //time difference
			
			//start going through the arraylist
			for(String L: fileholder) {
				
				String current = fileholder.get(placeholder); //this is the current line of code
				
				//initializing id, ttl, and times
				String id = null; 
				String ttl = null;
				
				String time1 = null;
				String time2 = null;
				
				//this line contains an id
				if (current.indexOf("id") >= 0) {
					id = current.substring(current.indexOf("id"), current.indexOf(", o")).trim();
					ttl = current.substring(current.indexOf("ttl"), current.indexOf(", i")).trim();
					time1 = current.substring(0, current.indexOf("IP")).trim();
				} //end if (current.indexOf("id") >= 0)
				
				//search through the rest of the file
				for(int i = placeholder + 1; i < fileholder.size(); i++) {
					String compareHere = fileholder.get(i);
					
					//this line has an id
					if(compareHere.indexOf("id") >= 0) {
						//checkID is equal to the 2nd id
						String checkID = compareHere.substring(compareHere.indexOf("id"), compareHere.indexOf(", o")).trim();
						
						//does checkId match id?
						if(checkID.equals(id) && !checkID.equals("id 0")) {
							
							//get line that contains the IP
							String IPLine = fileholder.get(i-1);
							String IP = IPLine.substring(0, IPLine.indexOf(" >")).trim(); //WHY DON'T YOU WORK???
							
							//get line that contains the time
							String timeLine = fileholder.get(i-2);
							
							//getting response time
							time2 = timeLine.substring(0, timeLine.indexOf("IP")).trim();
							
							//calculating time
							timeDifference = (Double.parseDouble(time2) - Double.parseDouble(time1)) * 1000;
							timeDifference = Math.round(timeDifference * 1000d)/1000d; // rounding to 3 decimal points
						
							/*
							 * Printing to output file
							*/
							
							//if at beginning of output file just put ttl, ip and difference
							if(writerMan == 0) {
								printWriter.println(ttl);
								printWriter.println(IP);
							} // end if(writerMan == 0)
							
							//every 3 intervals put some hyphens before the ttl to make it look nice
							else if (writerMan % 3  == 0 && writerMan != 0) {
								printWriter.println("--------------------------");
								printWriter.println(ttl);
								printWriter.println(IP);
							} // end else if
							
							printWriter.println(timeDifference);
							writerMan++;
							
							
						} //end if(checkID.equals(id) && !checkID.equals("id 0"))
						
					} //end if(compareHere.indexOf("id") >= 0)
					
				} //end for(int i = placeholder + 1; i < fileholder.size(); i++)
				
				placeholder++; //increment placeholder to next line in text file
				
			} //end for(String L: fileholder)
			
			//close everything
			fileWriter.close();
			printWriter.close();
			bufferedReader.close();
		} //end try
		
		//in case file not found
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + dumpFile + "'");
		} //end catch(FileNotFoundException ex)
		 
		catch (IOException ex) {
			System.out.println("Error reading file '" + analysis + "'");
		} //end catch (IOException ex)
		
	} //end public static void main(String args[]) 
	
} //end public class TracerouteAnalysis

