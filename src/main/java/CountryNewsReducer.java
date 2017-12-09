import javafx.util.Pair;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.util.*;

/**
 * Created by Antoine Sauray on 16/11/2017.
 */
public class CountryNewsReducer extends org.apache.hadoop.mapreduce.Reducer<Text, IntWritable, Text, IntWritable> {

    private Map<String, Pair<String, Integer>> map;


    protected void setup(Context context)
            throws IOException,
            InterruptedException {
        map = new HashMap<>();
    }

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int i=0;
        int count = 0;
        while (values.iterator().hasNext()) {
            i+=values.iterator().next().get();
            count++;
        }
        map.put(key.toString(), new Pair<>(key.toString(), i));
    }

    protected void cleanup(Context context)
            throws IOException,
            InterruptedException {

        List<Pair<String, Integer>> list = new ArrayList<>(map.size());
        for(String key : map.keySet()) {
            list.add(map.get(key));
        }

        list.sort(new Comparator<Pair<String, Integer>>() {
            @Override
            public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
        int beg = list.size()-10;
        if(beg < 0) beg=0;
        for(Pair<String, Integer> p: list.subList(beg, list.size())) {
            context.write(new Text(p.getKey()), new IntWritable(p.getValue()));
        }

    }
}
