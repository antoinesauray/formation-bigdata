import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by Antoine Sauray on 16/11/2017.
 */
public class ReligionCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private static IntWritable one = new IntWritable(1);
    private static FloatWritable floatWritable = new FloatWritable(0.0f);
    private Text word = new Text();

    @Override
    public void map(LongWritable key, Text value, WordCountMapper.Context context) throws IOException, InterruptedException {

        Configuration conf = context.getConfiguration();
        String filter = conf.get("filter");
        String filterValue = conf.get("filterValue", "-1");

        String line = value.toString();
        String[] split = line.split("\t");
        String date = split[1]; // YYYYMMDD
        //String year = date.substring(0, 4);
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6, 8));
        int week = (month-1)*4 + day/7;

        String actor1Name = split[6];
        String actor2Name = split[16];
        String actor1Religion1Code = split[10];
        String actor1Religion2Code = split[11];
        System.out.println("actor1name: "+actor1Name);
        System.out.println("actor2name: "+actor2Name);
        String numMentions = split[31];
        String avgTone = split[34];
        String countryCode = split[52];

        word.set(actor1Religion1Code);
        one.set(Integer.parseInt(numMentions));


        if(filter != null) {
            if(filter.equals("month") && month == Integer.parseInt(filterValue)) {
                context.write(word, one);
            } else if(filter.equals("week") && week == Integer.parseInt(filterValue)) {
                context.write(word, one);
            } else if(filter.equals("day") && day == Integer.parseInt(filterValue)) {
                context.write(word, one);
            } else if(filter.equals("actor1name") && actor1Name.equals(filterValue)) {
                context.write(word, one);
            }
        } else { context.write(word, one); }

    }
}
