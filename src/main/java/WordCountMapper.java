import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Created by Antoine Sauray on 16/11/2017.
 */
public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private static IntWritable one = new IntWritable(1);
    private Text word = new Text();

    private boolean QUESTION_1 = true;

    @Override
    public void map(LongWritable key, Text value, WordCountMapper.Context context) throws IOException, InterruptedException {

        String line = value.toString();
        StringTokenizer tokenizer = new StringTokenizer(line);
        while(tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            // question 1
            if(QUESTION_1) {
                if(token.startsWith("m")) {
                    word.set(token);
                    context.write(word, one);
                }
            } else {
                word.set(token);
                context.write(word, one);
            }

        }
    }
}
