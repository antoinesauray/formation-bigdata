import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * Created by Antoine Sauray on 16/11/2017.
 */
public class MyMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private static IntWritable one = new IntWritable(1);
    private Text word = new Text();

    @Override
    public void map(LongWritable key, Text value, Mapper.Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] split = line.split("\t");
        String numMentions = split[31];
        String countryCode = split[52];
        System.out.println(split[52]);
        word.set(countryCode);
        one.set(Integer.parseInt(numMentions));
        context.write(word, one);
    }
}
