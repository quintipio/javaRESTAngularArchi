package fr.quintipio.javarestangulararchi.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public  class TestUtil {

    private TestUtil() {}

    public static String convertObjectToJsonString(Object object)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    public static<T> T convertJsonAsObject(String data, Class<T> outputType)  throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(data,outputType);
    }
}
