import javafx.util.Pair;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Antoine Sauray on 16/11/2017.
 */
public class ReligionAvgToneReducer extends org.apache.hadoop.mapreduce.Reducer<Text, FloatWritable, Text, FloatWritable> {

    @Override
    public void reduce(Text key, Iterable<FloatWritable> values, Context context) throws IOException, InterruptedException {
        float i=0;
        int count = 0;
        while (values.iterator().hasNext()) {
            i+=values.iterator().next().get();
            count++;
        }
        context.write(key, new FloatWritable(i/count));
    }
}
