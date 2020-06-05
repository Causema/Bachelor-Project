import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		TXTtoTSV test=new TXTtoTSV();
		int tokenCounts[]=test.getTokenCounts();
		int offsetTracker[][]=test.getOffsetTracker();
		CsvToXmi start = new CsvToXmi(tokenCounts,offsetTracker);
	}

}
