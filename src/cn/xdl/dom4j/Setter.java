package cn.xdl.dom4j;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class Setter {

    public static void setValue(Type type, String value, Object obj,Method method) throws Exception {
        if(type == Integer.TYPE){
            method.invoke(obj, new Integer(value));
        }else if(type==Long.TYPE){
            method.invoke(obj,new Long(value));
        }else if(type==Float.TYPE){
            method.invoke(obj,new Float(value));
        }else if(type==Double.TYPE){
            method.invoke(obj,new Double(value));
        }else if(type==Boolean.TYPE){
            method.invoke(obj,new Boolean(value));
        }else if(type==Integer.class){
            method.invoke(obj,new Integer(value));
        }
        else{
            method.invoke(obj,value);
        }
    }
}