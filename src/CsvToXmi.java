import java.io.*;
import java.util.*;

public class CsvToXmi{
/* This class will rewrite the CSV as you can get from Atlas.Ti (the csv being seperated with a ; sign) into a portion of XMI which you can then paste into the
 * xmi file that you can export form WebAnno
 */
	
int index;
Queue<Integer> listOfIndexes = new LinkedList<Integer>();
int tokenCounts[]=new int[99];
int offsetTracker[][]=new int[10][10000];
String pageIndex="1";

private int addOffset(int location){
	int offset=0;
	for(int i=0;i<=location;i++){
		offset+=offsetTracker[Integer.valueOf(pageIndex)][i];
	}
	return offset;
}
private int properReference(String string, BufferedReader reader) throws IOException{
	pageIndex=copyString("", string , ':',reader);
	int i;
	int offset=0;
	for(i=1;i<Integer.valueOf(pageIndex);i++){
		offset+=tokenCounts[i];
	}
	return offset;
}

private String copyString(String copy,String original, char separationSymbol, BufferedReader reader) throws IOException{
	System.out.println(original);
	
	/*if(original.charAt(index)=='"'){
		original+=reader.readLine();
		original.replace('"',' ');
	}*/
	while(original.charAt(index)!=separationSymbol){
		if(index>=original.length()){
			System.out.println(original);
			return "not defined";
		}
		if(original.charAt(index)=='\n'){
			return copy.trim();
		}
		copy+=original.charAt(index);
		index++;
	}
	return copy.trim();
	}
	
	public void setOffsetTracker(int[][] offsetTracker) {
	this.offsetTracker = offsetTracker;
}

	public void setTokenCounts(int[] tokenCounts) {
	this.tokenCounts = tokenCounts;
}
	CsvToXmi(int tokenCounts[],int offsetTracker[][]){
		setTokenCounts(tokenCounts);
		setOffsetTracker(offsetTracker);
		int offset=0;
		try(BufferedReader reader= new BufferedReader(new FileReader("C:\\Users\\chris\\Desktop\\Paper\\StackOverflow Posts\\D1\\D1V2.csv"))){
			Integer id=16052+10;
			try(BufferedWriter writer= new BufferedWriter(new FileWriter("C:\\Users\\chris\\Desktop\\Paper\\StackOverflow Posts\\D1\\D1XMI.txt"))){
				String string = reader.readLine();
				string = reader.readLine();
				while(string!=null){
					String edit ="";
					String begin="";
					String end="";
					index=0; 
					edit=copyString(edit, string, ';',reader);
					index++;
					offset=properReference(string,reader);
					index++;
					begin=copyString(begin, string , ' ',reader);
					offset+=Integer.valueOf(begin);
					offset-=addOffset(Integer.valueOf(begin));
					index+=3;
					begin=Integer.toString(offset);
					offset=properReference(string,reader);
					index++;
					while(index!=string.length()){
						end+=string.charAt(index);
						index++;
					}
					offset+=Integer.valueOf(end);
					offset-=addOffset(Integer.valueOf(end));
					end=Integer.toString(offset);
					if(Integer.valueOf(end)<Integer.valueOf(begin)){
						System.out.println("something went wrong, end smaller \t"+offset);
					}
					else{
						//<chunk:Chunk xmi:id="14986" sofa="12" begin="0" end="15" chunkValue="test"/>
						writer.write("<type:CoreferenceLink xmi:id="+"\""+id.toString()+"\" sofa=\"12\" begin=\""+begin+"\" end=\""+end+"\"  annotation=\""+edit+"\"/>"+"\n");
						listOfIndexes.offer(id);
						id+=10;
					}
					string = reader.readLine();
				}
				index=0;
				while(index<4){
					listOfIndexes.offer(id);
					id+=13;
					index++;
				}
				while(!listOfIndexes.isEmpty()){
					writer.write(listOfIndexes.poll()+" ");
				}
			}catch (IOException e) {
			   System.out.println("writer"+e);
			}
		}catch (IOException e) {
		   System.out.println(e);
		}
		
	}
}
