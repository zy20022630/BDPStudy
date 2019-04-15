package com.zy.study.etl;

import org.apache.hadoop.util.ToolRunner;

public class HDFSToHBaseMRJobStarter1 {

    public static void main(String[] args) throws Exception {
        int run = ToolRunner.run(new HDFSToHBaseMRJob1(), args);
        System.exit(run);
    }

}
