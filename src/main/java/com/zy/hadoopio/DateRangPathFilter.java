package com.zy.hadoopio;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateRangPathFilter implements PathFilter {


    private final static Pattern PATTERN = Pattern.compile("^.*/(\\d\\d\\d\\d/\\d\\d/\\d\\d).*$");
    private final Date start, end;

    public DateRangPathFilter(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public boolean isInterval(Date date) {
        return !date.before(start) && !date.after(date);
    }

    public boolean accept(Path path) {

        Matcher matcher = PATTERN.matcher(path.toString());
        if (matcher.matches()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            try {
                return isInterval(sdf.parse(matcher.group(0)));
            } catch (ParseException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }


}
