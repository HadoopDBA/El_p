Pig Lab : join
project dir : pig-join

Note that this is the same lab as the pig-billing example.

STEP 1) Edit the file join.pig


STEP 2) complete the TODO items
Answer : pig-billing/join-answer.pig

STEP 3) Run the pig file using pig:
  $ pig ./join.pig

STEP 4)
Browse HDFS file system.  Navigate to 'MYNAME/billing/out' dir
(see ../getting-started.txt for detailed instructions)


STEP 5) examine the job stats from job tracker UI
go to  http://<job tracker>:50030
       (e.g. http://localhost:50030)

Find the job under 'completed jobs' section
how many mr jobs were run?
