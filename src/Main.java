import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
// 24
public class Main {
	public static void main(String[] args) throws IOException {
		String docNumber="105";
		String idPoint;
		String filePath="C:\\Users\\chris\\Desktop\\Paper\\StackOverflow Posts\\D"+docNumber+"\\D"+docNumber;
		try(BufferedReader reader= new BufferedReader(new FileReader(filePath+".xmi"))){
		String line=reader.readLine();
			while(!line.contains("type4:Sentence xmi:id=\"")){
				line=reader.readLine();
			}
			int i=28;
			idPoint="";
			while(line.charAt(i)!='\"'){
				idPoint+=String.valueOf(line.charAt(i));
				i++;
				
			}
			System.out.println("ID = "+idPoint);
		}catch (IOException e) {
			System.out.println("yo");
			 idPoint= "10640";
			}
		Integer id=Integer.valueOf(idPoint)+5;
		TXTtoTSV tsv=new TXTtoTSV(filePath);
		System.out.println(tsv.getCounter());
		int tokenCounts[]=tsv.getTokenCounts();
		int offsetTracker[][]=tsv.getOffsetTracker();
		new CSVFilter(filePath);
		new CsvToXmi(tokenCounts,offsetTracker,id,filePath);
		}
	}
