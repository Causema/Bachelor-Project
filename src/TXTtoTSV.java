import java.io.*;
public class TXTtoTSV {
	String EOF="EindeBestand";
	String EOA="EindeParagraaf";
	String EOP="EindePagina";
	int test=0;
	int tokenCounts[]=new int[99];
	int offsetTracker[][]=new int[10][10000];
	Integer index=0;
	int pageCounter=1;
	int counter;
	int answerID=1;
	public int[][] getOffsetTracker() {
		return offsetTracker;
	}

	/*This class will change a txt file to a tsv file, The txt file needs to be prepped to contain the EOF,EOA,and EOP tags, respectively standing for: end of file,
	 * end of answer and end of page. 
	 * It will also return a array of tokencounts per page.
	 */
	public int[] getTokenCounts() {
		return tokenCounts;
	}

	private String completeAnswer(BufferedReader reader,String string) throws IOException{
		String answer="";
		String answerNumber="ANSWER "+answerID+" ";
		while(!string.equals(EOA)){
			if(!string.equals(EOP)){
				answer+=string+" ";
				tokenCounts[pageCounter]+=string.length()+1;
				test++;
			}else{
				pageCounter++;
				
			}
			string=reader.readLine();
		}
		offsetTracker[pageCounter][tokenCounts[pageCounter]]=Integer.parseInt(reader.readLine())+answerNumber.length();
		answerID++;
		return answer+answerNumber;
	}
	
	private String produceSingularWord(String answer){
		String word="";
		int temp=0;
		while(answer.charAt(index)!=' '&& answer.charAt(index)!='\t'){
			word+=answer.charAt(index);
			counter++;
				index++;
				if(index==answer.length()){
					break;
				}
		}
		return word;
	}
	
	private void dissectSentence(String answer,BufferedWriter writer) throws IOException{
		int wordId=1;
		if(answer.equals(EOP)){
			pageCounter++;
		}else{
			while(index<answer.length()-1){
				String word=produceSingularWord(answer);
				index++;
				writer.write(wordId+"\t"+word+"	_\t_\t_\t_\t_\t_\t_\t_\n");
				wordId++;
			}
		}
	}
	public TXTtoTSV() throws IOException {
		index=0;
		int docId=123456;
		int id=1;
	
		BufferedReader reader= new BufferedReader(new FileReader("C:\\Users\\chris\\Desktop\\Paper\\StackOverflow Posts\\D1\\D1.txt"));
		BufferedWriter writer= new BufferedWriter(new FileWriter("C:\\Users\\chris\\Desktop\\Paper\\StackOverflow Posts\\D1\\D1.tsv"));
		String string="";
		String answer="";
		writer.write("# newdoc id = "+docId+" \n# newpar id = "+docId+"p"+id+" \n# sent_id = "+docId+"s"+id+"\n# text = ");
		id++;
		string=reader.readLine();
		while(!string.equals(EOF)){
			answer+=completeAnswer(reader,string);
			string=reader.readLine();
			}
		answer.trim();
		writer.write(answer+"\n");
		answer+='$';
		dissectSentence(answer, writer);
		index=0;
		
		writer.close();
		}
			
	}
	
	
	
	

