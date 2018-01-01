package wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCount {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		
		Job job = Job.getInstance(conf, "WordCount");
		job.setJarByClass(WordCount.class);
		
		// set the class of each stage in mapreduce
		job.setMapperClass(WordCountMapper.class);					// 把input，根據第一個字母做成key-value pair
		job.setPartitionerClass(WordCountPartitioner.class);		// 決定哪個key要去哪個reducer
		job.setSortComparatorClass(WordCountKeyComparator.class);	// 決定key的sorting方式

		job.setReducerClass(WordCountReducer.class);
		
		// set the output class of Mapper and Reducer
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		// set the number of reducer
		job.setNumReduceTasks(2);
		
		// add input/output path
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
