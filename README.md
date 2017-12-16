Bases de données avancées
Big Data: Hadoop/MapReduce

# Prerequisites

There are many issues to build and run this code (and Hadoop in general) in Windows so please use a recent linux system to develop the TP or
even better work with the recommended VM.

- Verify you have a correct Java installation with the JAVA_HOME variable configured.
- Install a Java IDE (IntelliJ or Eclipse), or a decent editor
- Install Maven in case you don't have it, if you are using Eclipse verify that you have the latest
  version or install the m2e plugin to import the maven project
- Clone this repo.

## Recommended Virtual Machine

Install [Virtualbox](https://www.virtualbox.org/wiki/Downloads) and the provided Virtual Machine.

You can download the Virtual Machine also from this link (5.3 GB), remember that you need to
allocate at least 4GB to the VM:
https://downloads.cloudera.com/demo_vm/virtualbox/cloudera-quickstart-vm-5.8.0-0-virtualbox.zip

# Exercises:

1. Finish the implementation of Wordcount as seen in the class and validate that it works well.

You can use a file of your own or download a book from the gutenberg project, you find also the example
file dataset/hamlet.txt

Mapper

```
@Override
public void map(LongWritable key, Text value, Mapper.Context context) throws IOException, InterruptedException {
        String line = value.toString();
        StringTokenizer tokenizer = new StringTokenizer(line);
        while(tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            word.set(token);
            context.write(word, one);
        }
}
```

Reducer

```
@Override
public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
    int i=0;
    while (values.iterator().hasNext()) {
        i+=values.iterator().next().get();
    }
    context.write(key, new IntWritable(i));
}
```

1. Modify the implementation to return only the words that start with the letter 'm'.

- Where did you change it, in the mapper or in the reducer ?

  I changed it in the mapper.

- What are the consequences of changing the code in each one ?

  The size that will be outputed differs. The bottleneck of Hadoop being the disk write, it has consequences on performance.

- Compare the Hadoop counters and explain which one is the best strategy.

  Reducer modification

  		Map input records=7062
  		Map output records=33579
  		Map output bytes=317907
  Mapper modification

  	Map input records=7062
  	Map output records=1835
  	Map output bytes=16725


3. Use the GDELT dataset and Implement a Map Reduce job the top 10 countries that have more
  relevance in the news for a given time period (one week, one day, one month).

For more info about gdelt column format you can find more info at:
http://data.gdeltproject.org/documentation/GDELT-Data_Format_Codebook.pdf

You can find a small test sample of the dataset at tp/dataset/gdeltmini/20160529.export.csv
that you can use to test your implementation.

You can also download the full datasets from:
http://data.gdeltproject.org/events/index.html

We will consider the country code as the three letter identifier represented as Actor1CountryCode, we
will count the relevance of an eent based on its NumMentions column. So this become likes a
WordCount where we count the NumMentions of each event per country to determine the Top 20.

Add a combiner to the job? Do you see any improvement in the counters ? Explain.

The combiner will reduce the number of counters. It is an optimization.

4. Do an interesting analysis from the GDELT dataset.
Ó
Take a look at the code book format, in particular the Actor/CAMEO codes, and find interesting analysis, some ideas:

Done on the small dataset

It seems that CHR gets a lower tone on average (-1.463528) than MOS (-1.2605042). BUD is best (1.6203706)

CHR have a lot of mentions in the small dataset(232). The MOS only has 1, therefore the low tone (-1.2605042) can be ignored. BUD has 40.

# Some instructions

This assumes that your virtual machine IP is 172.17.0.2. You can try to access it from inside the
virtual machine at 127.0.0.1

# Cluster Instructions

Remember to replace ec2-IP.eu-west-1.compute.amazonaws.com for the given IP (published in private).

1. Connect to the machine/cluster via ssh

Use the given authorization credentials (with the user/password discussed in class):

Linux

    ssh gcn01@ec2-54-171-169-69.eu-west-1.compute.amazonaws.com

Windows

- Download PuTTY.exe to your computer from:
  http://www.chiark.greenend.org.uk/~sgtatham/putty/download.html
- Start PuTTY.
- In the Category list, click Session.
- In the Host Name field, type hadoop@ec2-IP.eu-west-1.compute.amazonaws.com
- In the Category list, expand Connection > SSH, and then click Auth.
- For Private key file for authentication, click Browse and select the private key file (gcn5.ppk) used to launch the cluster.
- Click Open.
- Click Yes to dismiss the security alert.

If everything is OK you must see the command prompt and the EMR symbols.

2. Copy your jar file from your machine to the cluster

    scp JARFILE login@server:

e.g.

    scp target/hadoop-tps-1.0.jar gcn00@ec2-IP.compute-1.amazonaws.com:

Remember to update the address with the one given in the course.

3. Playing with HDFS

We are going to run the wordcount example of the course, so we need first to add the file to the distributed file system.
So first we must connect to the master server (ssh like in 1) and do:

    hadoop fs -mkdir hdfs:///user/hadoop/id##

or

    hadoop fs -mkdir hdfs:///user/hadoop/gcn##

depending on your master and group (##).

Upload the file

    hadoop fs -put LICENSE.txt hdfs://172.17.0.2:9000/tp/

Verify the upload

    hadoop fs -ls hdfs://172.17.0.2:9000/tp/

This one for remote IPs (port 8020 or 9000)

    hadoop fs -ls hdfs://172.17.0.2/tp1/

Advanced:

Created a ssh proxy to see the Web Interface:


## Part 2 - Running a mapreduce job in YARN

Copy the program to the cluster

The structure is:

    hadoop jar JAR_FILE CLASS input output

    hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples*.jar wordcount hdfs://172.17.0.2:9000/tp1/LICENSE.txt hdfs://172.17.0.2:9000/tp1/output/$(date +%Y%m%d%H%M%S)

    hadoop jar share/hadoop/mapreduce/hadoop-mapreduce-examples*.jar wordcount hdfs://172.17.0.2:9000/tp1/LICENSE.txt hdfs://172.17.0.2:9000/tp1/output

Verify the output

    hadoop fs -cat "hdfs://172.17.0.2:9000/tp1/output/part-*"

For example if the IP is 172.17.0.2 You find the web interfaces (this is for people with the vm or in the cluster):

- NameNode http://172.17.0.2:50070/
- ResourceManager http://172.17.0.2:8088/
- MapReduce JobHistory Server http://172.17.0.2:19888/s
- Hue http://172.17.0.2:8888/

# References

https://developer.yahoo.com/hadoop/tutorial/
http://blog.cloudera.com/blog/2009/08/hadoop-default-ports-quick-reference/

# Extra

If you are running in AWS the events are also available in Amazon's S3:

    s3://gdelt-open-data/events/20170108.export.csv
