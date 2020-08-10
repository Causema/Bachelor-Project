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
	for(int i=0;i<location;i++){
		offset+=offsetTracker[Integer.valueOf(pageIndex)][i];
	}
	//offset-=9;
	return offset;
}
private int pageOffset(String string, BufferedReader reader) throws IOException{
	pageIndex=copyString("", string , ':',reader);
	int i;
	int offset=0;
	for(i=1;i<Integer.valueOf(pageIndex);i++){
		offset+=tokenCounts[i];
	}
	return offset;
}

private String copyString(String copy,String original, char separationSymbol, BufferedReader reader) throws IOException{
	while(original.charAt(index)!=separationSymbol){
		if(index>=original.length()){
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
	CsvToXmi(int tokenCounts[],int offsetTracker[][],Integer id,String filePath){
		setTokenCounts(tokenCounts);
		setOffsetTracker(offsetTracker);
		int offset=0;
		try(BufferedReader reader= new BufferedReader(new FileReader(filePath+"V2.csv"))){
			try(BufferedWriter writer= new BufferedWriter(new FileWriter(filePath+"XMI.txt"))){
				String string = reader.readLine();
				string = reader.readLine();
				while(string!=null){
					String edit ="";
					String begin="";
					String end="";
					index=0; 
					int answerOffset=-1;
					offset=-1;
					edit=copyString(edit, string, ';',reader);
					index++;
					offset+=pageOffset(string,reader);
					index++;
					begin=copyString(begin, string , ' ',reader);
					offset+=Integer.valueOf(begin);
					answerOffset+=addOffset(Integer.valueOf(begin));
					offset-=answerOffset;
					index+=3;
					begin=Integer.toString(offset);
					offset=-1;
					offset+=pageOffset(string,reader);
					index++;
					while(index!=string.length()){
						end+=string.charAt(index);
						index++;
					}
					
					offset+=Integer.valueOf(end);
					offset-=answerOffset;
					end=Integer.toString(offset);
					
					if(Integer.valueOf(end)<Integer.valueOf(begin)){
						System.out.print("something went wrong, end smaller \t"+edit);
					}
					else{
						//<chunk:Chunk xmi:id="14986" sofa="12" begin="0" end="15" chunkValue="test"/>
						writer.write("<custom:Test xmi:id="+"\""+id.toString()+"\" sofa=\"12\" begin=\""+begin+"\" end=\""+end+"\"  annotation=\""+edit+"\" chaining=\"\" reference=\"\"/>"+"\n");
						listOfIndexes.offer(id);
						id+=11;
					}
					string = reader.readLine();
				}
				index=0;
				
				while(index<4){
					listOfIndexes.offer(id);
					id+=11;
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
