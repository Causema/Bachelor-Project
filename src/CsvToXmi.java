import java.io.*;
import java.util.*;

public class CsvToXmi{
/* This class will rewrite the CSV as you can get from Atlas.Ti (the csv being seperated with a ; sign) into a portion of XMI which you can then paste into the
 * xmi file that you can export form WebAnno
 */
	
int index;
Queue<Integer> listOfIndexes = new LinkedList<Integer>();
int tokenCounts[]=new int[99];

private int properReference(String string){
	String pageIndex=copyString("", string , ':');
	int i;
	int offset=0;
	for(i=1;i<Integer.valueOf(pageIndex);i++){
		offset+=tokenCounts[Integer.valueOf(pageIndex)];
	}
	return offset;
}

private String copyString(String copy,String original, char separationSymbol){
	while(original.charAt(index)!=separationSymbol){
		copy+=original.charAt(index);
		index++;
		}
	return copy;
	}
	
	public void setTokenCounts(int[] tokenCounts) {
	this.tokenCounts = tokenCounts;
	int i=1;
	int test=0;
	while(i<4){
		System.out.println(tokenCounts[i]);
		i++;
	}
	
}
	CsvToXmi(int tokenCounts[]){
		setTokenCounts(tokenCounts);
		int offset=0;
		try(BufferedReader reader= new BufferedReader(new FileReader("C:\\Users\\chris\\Desktop\\Paper\\test.txt"))){
			Integer id=16057;
			try(BufferedWriter writer= new BufferedWriter(new FileWriter("C:\\Users\\chris\\Desktop\\Paper\\test2.txt"))){
				String string = reader.readLine();
				string = reader.readLine();
				while(string!=null){
					String edit ="";
					String begin="";
					String end="";
					index=0; 
					edit=copyString(edit, string, ';');
					index++;
					offset=properReference(string);
					index++;
					begin=copyString(begin, string , ' ');
					offset+=Integer.valueOf(begin);
					index+=3;
					begin=Integer.toString(offset);
					offset=properReference(string);
					index++;
					while(index!=string.length()){
						end+=string.charAt(index);
						index++;
					}
					offset+=Integer.valueOf(end);
					end=Integer.toString(offset);
					
					//<chunk:Chunk xmi:id="14986" sofa="12" begin="0" end="15" chunkValue="test"/>
					writer.write("<custom:Test xmi:id="+"\""+id.toString()+"\" sofa=\"12\" begin=\""+begin+"\" end=\""+end+"\"  annotation=\""+edit+"\"/>"+"\n");
					listOfIndexes.offer(id);
					id+=5;
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
			
			System.out.println(reader.readLine());
		}catch (IOException e) {
		   System.out.println(e);
		}
		
	}
}
