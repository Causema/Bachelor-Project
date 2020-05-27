import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		TXTtoTSV test=new TXTtoTSV();
		int tokenCounts[]=test.getTokenCounts();
		System.out.println(tokenCounts[1]);
		CsvToXmi start = new CsvToXmi(tokenCounts);
	}

}
