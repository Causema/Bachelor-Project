import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CSVFilter {
	String blackList[]={"Technology Solution","Knowledge Certainity Indicator",
			"Architecture Component Property",
			"Architecture Component",
			"Application Type",
			"Architecture Connector",
			"Architecture Connector Element",
			"Architecture Component Element",
			"Programming element",
			"Programming Activity",
			"Performance of Technology",
			"Domain",
			"License",
			"Data size and Structure",
			"Network",
			"Technology Type",
			"Development Tool",
			"Technology Solution",
			"Self Developed Component",
			"Technology Vendor",
			"Technology Website/Source code link"};
	
	
	public boolean filterCodes(String string){
		for(int i=0;i<blackList.length;i++){
			if(string.contains(blackList[i])){
				return false;
			}
		}
		return true;
	}

	public CSVFilter(String filePath) throws IOException {
		BufferedReader reader= new BufferedReader(new FileReader(filePath+".csv"));
		BufferedWriter writer= new BufferedWriter(new FileWriter(filePath+"V2.csv"));
		String line=reader.readLine();
		while(line!=null){
			if(line.charAt(0)==';'){
				line=reader.readLine();
				continue;
			}
			if(line.contains("\"")){
				String line2=" "+reader.readLine();
				if(!line2.contains("\"")){
					line2+=" "+reader.readLine();
					line2=line2.replaceAll("^\"+|\"+$", "");
				}
				line+=line2;
				line.trim();
			}
			if(filterCodes(line)){
				writer.write(line+"\n");
			}
			line=reader.readLine();
			
		}	
		reader.close();
		writer.close();
	}
}
