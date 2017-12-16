Homework
========

Due Date: TBD

You must finish the GDELT exercise to calculate the top most relevant countries given their mentions from the TP and send me the following:

1. The source code of your GDELT analysis classes.

2. The output of the execution (part-1-00000, part-2-00000, ...) for the month corresponding to your user account. e.g. if your user account is id05 or gcn05 you must calculate the query for the month 201705. (If your group is 11 or 12 you must take the files for the previous year e.g. 201611 or 201612).

    Remember that the GDELT dataset is available here:
    http://data.gdeltproject.org/events/index.html

 ```
  UKH9    1204285
  UK      1242391
  USFL    1243171
  RS48    1438866
  USTX    1562490
  US      1815906
  USNY    2380895
  USCA    3006628
          5743734
  USDC    6765343
```

3. The metrics result after an execution in the cluster, this is the counter information that the job prints when you execute it (for example):
```
File System Counters
               FILE: Number of bytes read=60665700
               FILE: Number of bytes written=124555042
               FILE: Number of read operations=0
               FILE: Number of large read operations=0
               FILE: Number of write operations=0
               HDFS: Number of bytes read=2225459129
               HDFS: Number of bytes written=122
               HDFS: Number of read operations=84
               HDFS: Number of large read operations=0
               HDFS: Number of write operations=2
       Job Counters
               Failed map tasks=1
               Launched map tasks=28
               Launched reduce tasks=1
               Other local map tasks=1
               Data-local map tasks=27
               Total time spent by all maps in occupied slots (ms)=750992
               Total time spent by all reduces in occupied slots (ms)=86048
               Total time spent by all map tasks (ms)=750992
               Total time spent by all reduce tasks (ms)=86048
               Total vcore-seconds taken by all map tasks=750992
               Total vcore-seconds taken by all reduce tasks=86048
               Total megabyte-seconds taken by all map tasks=769015808
               Total megabyte-seconds taken by all reduce tasks=88113152
       Map-Reduce Framework
               Map input records=5824262
               Map output records=5824262
               Map output bytes=49017170
               Map output materialized bytes=60665856
               Input split bytes=2943
               Combine input records=0
               Combine output records=0
               Reduce input groups=3744
               Reduce shuffle bytes=60665856
               Reduce input records=5824262
               Reduce output records=10
               Spilled Records=11648524
               Shuffled Maps =27
               Failed Shuffles=0
               Merged Map outputs=27
               GC time elapsed (ms)=13936
               CPU time spent (ms)=137260
               Physical memory (bytes) snapshot=7446953984
               Virtual memory (bytes) snapshot=20442714112
               Total committed heap usage (bytes)=5729943552
       Shuffle Errors
               BAD_ID=0
               CONNECTION=0
               IO_ERROR=0
               WRONG_LENGTH=0
               WRONG_MAP=0
               WRONG_REDUCE=0
       File Input Format Counters
               Bytes Read=2225456186
       File Output Format Counters
               Bytes Written=122
               ```

4. The metrics result after running the same program using as input Amazon's S3 GDELT repository. DON'T FORGET TO FILTER FOR THE EXACT MONTH or the job will take longer.

  Had issues with cluster (permission) ... skipped

5. Finally you must do a small report (a txt or doc file) explaining your approach and what are the differences (if there are any) between executing the job locally vs in the cluster with the input on HDFS vs in the cluster with the input in S3.

Executing the job locally will run faster on very small datasets, considering the network latency is close to 0. Executing the job on a cluster allows to use more power and to parallelize the tasks. In the end the task will be faster on the cluster.

The difference between using HDFS and S3 will likely lie in the latency. S3 is a service provided by Amazon and the data needs to be transferred to another server which might be located in a different place. HDFS is more likely to be installed directly on the machine running. On the other hand, S3 provides low costs, high scalability and availability.
