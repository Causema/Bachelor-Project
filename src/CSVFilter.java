import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CSVFilter {
	

	public CSVFilter() throws IOException {
		BufferedReader reader= new BufferedReader(new FileReader("C:\\Users\\chris\\Desktop\\Paper\\StackOverflow Posts\\D1\\D1.csv"));
		BufferedWriter writer= new BufferedWriter(new FileWriter("C:\\Users\\chris\\Desktop\\Paper\\StackOverflow Posts\\D1\\D1V2.csv"));
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
				}
				line+=line2;
				line.replace(line.charAt(0), ' ');
				line.trim();
			}
			writer.write(line+"\n");
			line=reader.readLine();
			
		}	
		reader.close();
		writer.close();
	}
}
