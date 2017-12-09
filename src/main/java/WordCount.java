import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class WordCount {

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();

        // conf.set("filter", "M");
        // conf.setInt("filterValue", 5);

        // conf.set("filter", "W");
        // conf.setInt("filterValue", 19);

        conf.set("filter", "D");
        conf.setInt("filterValue", 15);

        Job job = Job.getInstance(conf, "gdelt"); job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class); job.setMapperClass(MyMapper.class);
        job.setReducerClass(MyReducer.class); job.setJarByClass(WordCount.class);
        job.setInputFormatClass(TextInputFormat.class); job.setOutputFormatClass(TextOutputFormat.class);
        FileInputFormat.addInputPath(job, new Path(args[0])); FileOutputFormat.setOutputPath(job, new Path(args[1]));
        job.waitForCompletion(true);
    }
}