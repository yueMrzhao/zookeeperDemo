package com.zy.mpdev;

import org.apache.hadoop.mapreduce.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LoggingIndentityMapper<mi,mo,ri,ro> extends Mapper<mi,mo,ri,ro>{

    private static Logger log = LoggerFactory.getLogger(LoggingIndentityMapper.class);

    @Override
    protected void map(mi key, mo value, Context context) throws IOException, InterruptedException {
        log.info("map key is {}", key);
        log.debug("map value is {}", value);
        context.write((ri)key,(ro)value);
    }
}
