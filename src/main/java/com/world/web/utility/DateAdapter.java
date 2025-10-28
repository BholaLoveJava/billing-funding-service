package com.world.web.utility;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAdapter extends XmlAdapter<String, Date> {


    private static final ThreadLocal<DateFormat> dateFormat = new ThreadLocal<>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    /**
     * @param v The value to be converted. Can be null.
     * @return Date
     * @throws Exception for error scenarios
     */
    @Override
    public Date unmarshal(String v) throws Exception {
        return dateFormat.get().parse(v);
    }

    /**
     * @param v The value to be converted. Can be null.
     * @return String
     * @throws Exception for error scenarios
     */
    @Override
    public String marshal(Date v) throws Exception {
        return dateFormat.get().format(v);
    }
}
