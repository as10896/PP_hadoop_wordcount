package wordcount;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class WordCountKeyComparator extends WritableComparator {
	
	public WordCountKeyComparator() {
		super(Text.class, true);
	}	
	
	public int compare(WritableComparable o1, WritableComparable o2) {
		Text key1 = (Text) o1;
		Text key2 = (Text) o2;

		// TODO Order by A -> a -> B -> b ....
		
		String letter1 = key1.toString();
		String letter2 = key2.toString();


		int compare = compare(letter1.toUpperCase(), letter2.toUpperCase());

		if(compare < 0)
			return -1;
		else if(compare > 0)
			return 1;
		else{
			if(compare(letter1, letter2) < 0)
				return -1;
			else if (compare(letter1, letter2) > 0)
				return 1;
			else
				return 0;	// return 0代表兩個東西是一樣的，如果都沒有return 0，他就會認為兩個是不一樣的東西，之後會無法grouping		
		}	

		// return -1在前面，return 1在後面
		// 前面的值減後面的值

	}

	private int compare(String s1, String s2){
		return s1.compareTo(s2);
	}
}
