package hi.mr;

import java.io.IOException;
import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

// JDK API docs : http://docs.oracle.com/javase/7/docs/api/
// Hadoop API docs : http://hadoop.apache.org/docs/stable/api/

public class BillingTotal extends Configured implements Tool {
  public static void main(String[] args) throws Exception {
    int res = ToolRunner.run(new Configuration(), new BillingTotal(), args);
    System.exit(res);
  }

  @Override
  public int run(String[] args) throws Exception {

    if (args.length != 2) {
      System.out.println("usage : need <input path>  <output path>");
      return 1;
    }
    Path inputPath = new Path(args[0]);
    Path outputPath = new Path(args[1]);

    Configuration conf = getConf();

    Job job = new Job(conf, getClass().getName() + "--<your_name>"); // TODO
    job.setJarByClass(BillingTotal.class);
    job.setMapperClass(MyMapper.class);
    job.setReducerClass(MyReducer.class);
    job.setMapOutputValueClass(IntWritable.class);
    job.setMapOutputKeyClass(Text.class);
    job.setInputFormatClass(TextInputFormat.class);
    job.setOutputFormatClass(TextOutputFormat.class);
    TextInputFormat.setInputPaths(job, inputPath);
    TextOutputFormat.setOutputPath(job, outputPath);

    return job.waitForCompletion(true) ? 0 : 1;
  }

  static class MyMapper extends Mapper<Object, Text, Text, IntWritable> {
        // declared for re-use
        Text keyOutCustomer = new Text();
        IntWritable valueOutCost = new IntWritable();


    @Override
    public void map(Object key, Text record, Context context)
        throws IOException {
      // System.out.println (record);
      try {
        // sample data looks like this
        //  timestamp,  customer_id,  resource_id,   qty,   cost
        // 1325404800864,1,6,83,64
        // 1325404801728,2,3,17,166

        /// TODO : split the records by ,
        // String [] tokens = record.toString()......
        // System.out.println (Arrays.toString(tokens));

        /// TODO : extract the following from tokens
        // String timestampStr = tokens[0].trim();
        // String customerIdStr = ....
        // String costStr = ....
        // int cost = .... // convert to actual int

        // don't do this...
        // we will be creating a lot of temp. objects (think millions or billions)
        // and increasing Java GC pressure
        // Text keyOutCustomer = new Text(customerIdStr);
        // IntWritable valueOutCost = new IntWritable(cost);

        /// TODO : create output key / value pair
        // do this : re-use k,v pairs --> very efficient
        // keyOutCustomer.set(?);
        // valueOutCost.set(?);
        // context.write(keyOutCustomer, valueOutCost);

      } catch (Exception e) {
        System.out.println("*** exception:");
        e.printStackTrace();
      }
    }
  }

  public static class MyReducer extends
      Reducer<Text, IntWritable, Text, IntWritable> {

    public void reduce(Text custId, Iterable<IntWritable> allCosts, Context context)
        throws IOException, InterruptedException {
      int total = 0;
      for (IntWritable cost : allCosts) {
        // TODO
        // add up all the costs
      }
      context.write(custId, new IntWritable(total));
    }
  }
}
