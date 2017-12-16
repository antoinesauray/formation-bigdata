import javafx.util.Pair;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.util.*;

/**
 * Created by Antoine Sauray on 16/11/2017.
 */
public class CountryNewsReducer extends org.apache.hadoop.mapreduce.Reducer<Text, IntWritable, Text, IntWritable> {

    private class MyPair implements Comparable<MyPair> {
        private String key;
        private Integer value;
        MyPair(String key, Integer value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public int compareTo(MyPair o) {
            return value.compareTo(o.value);
        }
    }

    private Map<String, MyPair> map;


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
        map.put(key.toString(), new MyPair(key.toString(), i));
    }


    /*
    protected void cleanup(Context context)
            throws IOException,
            InterruptedException {

        List<MyPair> list = new ArrayList<>(map.size());
        for(String key : map.keySet()) {
            list.add(map.get(key));
        }
        Collections.sort(list);

        int beg = list.size()-10;
        if(beg < 0) beg=0;
        for(MyPair p: list.subList(beg, list.size())) {
            context.write(new Text(p.key), new IntWritable(p.value));
        }
    }
    */
}
