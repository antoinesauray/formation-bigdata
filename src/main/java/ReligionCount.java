import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class ReligionCount {

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();

        conf.set("filter", "month");
        conf.setInt("filterValue", 5);

        Job job = Job.getInstance(conf, "gdelt"); job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class); job.setMapperClass(ReligionCountMapper.class);
        job.setReducerClass(ReligionCountReducer.class); job.setJarByClass(ReligionCount.class);
        job.setInputFormatClass(TextInputFormat.class); job.setOutputFormatClass(TextOutputFormat.class);
        FileInputFormat.addInputPath(job, new Path(args[0])); FileOutputFormat.setOutputPath(job, new Path(args[1]));
        job.waitForCompletion(true);
    }
}