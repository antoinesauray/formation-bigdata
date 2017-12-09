import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;

/**
 * Created by Antoine Sauray on 16/11/2017.
 */
public class MyReducer extends org.apache.hadoop.mapreduce.Reducer<Text, IntWritable, Text, IntWritable> {

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, MyReducer.Context context) throws IOException, InterruptedException {
        int i=0;
        while (values.iterator().hasNext()) {
            i+=values.iterator().next().get();
        }
        context.write(key, new IntWritable(i));
    }
}
