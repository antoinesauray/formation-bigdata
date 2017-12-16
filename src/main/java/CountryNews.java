import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class CountryNews {

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();

        /*
        conf.set("filter", "month");
        conf.setInt("filterValue", 5);
        */

        Job job = Job.getInstance(conf, "gdelt"); job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class); job.setMapperClass(CountryNewsMapper.class);
        job.setReducerClass(CountryNewsReducer.class); job.setJarByClass(CountryNews.class);
        job.setInputFormatClass(TextInputFormat.class); job.setOutputFormatClass(TextOutputFormat.class);
        FileInputFormat.addInputPath(job, new Path(args[0])); FileOutputFormat.setOutputPath(job, new Path(args[1]));
        job.waitForCompletion(true);
    }
}