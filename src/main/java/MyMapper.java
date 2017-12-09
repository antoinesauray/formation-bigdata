import org.apache.hadoop.conf.Configuration;
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
    public void map(LongWritable key, Text value, MyMapper.Context context) throws IOException, InterruptedException {

        Configuration conf = context.getConfiguration();
        String filter = conf.get("filter");
        System.out.println("filter: " + filter);
        int filterValue = conf.getInt("filterValue", -1);
        if(filterValue<0) return;

        String line = value.toString();
        String[] split = line.split("\t");
        String date = split[1]; // YYYYMMDD
        //String year = date.substring(0, 4);
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6, 8));
        int week = (month-1)*4 + day/7;

        if(filter.equals("M")) {
            if (month == filterValue) {
                String numMentions = split[31];
                String countryCode = split[52];
                word.set(countryCode);
                one.set(Integer.parseInt(numMentions));
                context.write(word, one);
            }
        } else if(filter.equals("W")) {
            if (week == filterValue) {
                String numMentions = split[31];
                String countryCode = split[52];
                word.set(countryCode);
                one.set(Integer.parseInt(numMentions));
                context.write(word, one);
            }
        } else if(filter.equals("D")) {
            if (day == filterValue) {
                String numMentions = split[31];
                String countryCode = split[52];
                word.set(countryCode);
                one.set(Integer.parseInt(numMentions));
                context.write(word, one);
            }
        } else {
            System.out.println("no filter match");
        }

    }
}
