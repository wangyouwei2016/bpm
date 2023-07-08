package com.dstz.base.common.utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

/**
 * @author jinxia.hou
 * @Name AbXmlCovertUtil
 * @description: xml pojo转换工具类
 * @date 2022/8/1516:33
 */
public class AbXmlCovertUtil {
    /**
     * XML转换为POJO类型
     */
    @SafeVarargs
    public static <T> T covert2Object(String xml,Class<? extends Object>... classes) throws JAXBException, UnsupportedEncodingException {
        JAXBContext jAXBContext = JAXBContext.newInstance(classes);

        Unmarshaller unmarshaller = jAXBContext.createUnmarshaller();
        InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
        return (T)unmarshaller.unmarshal(is);
    }

    /**
     * POJO类型转换为XML
     */
    public static String covert2Xml(Object serObj) throws JAXBException  {
        JAXBContext jc = JAXBContext.newInstance(serObj.getClass());

        StringWriter out = new StringWriter();
        Marshaller m = jc.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
        m.marshal(serObj, out);
        String tmp = out.toString();
        return tmp;
    }
}
