package cn.xdl.dom4j;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;import java.util.Map.Entry;

public class Dom4jTest {
    public static List<Bean> beans=new ArrayList<>();
    public static Map<String,Object> beanList=new HashMap<>();
    public static Class cls=null;
    public static Object obj=null;
    public static void printUserMap(Map<String,Object> beanList) {
        for (Entry<String, Object> user : beanList.entrySet()) {
            System.out.println("Key = " + user.getKey() + ", Value = " + user.getValue());
        }
    }
    public static List<Bean> parseXml() throws Exception {
        // 创建一个指向XML文档的输入流
        InputStream is=new FileInputStream("D:\\Git\\java7.1\\src\\Resources\\Books.xml");
        // 创建SAXReader解析器
        SAXReader sr=new SAXReader();
        // 读取XML文档输入流，得到文档对象
        Document doc=sr.read(is);
        // 通过文档对象，获取根节点
        Element root=doc.getRootElement();
        // 根据根节点解析整个文档
        //System.out.println("整个文档:\n"+root.asXML());
        //获取根节点的所有子节点
        List<Element> es=root.elements();
        //循环遍历子节点集合
        for(Element element: es){
            Bean bean=new Bean();
            //获取节点id属性
            String id=element.attributeValue("id");
            bean.setId(id);
            //获取节点class属性
            String classname=element.attributeValue("class");
            bean.setClazz(classname);
            //获取property节点集合
            Map<String,String > property=new HashMap<>();
            List<Element> els=element.elements();
            for(Element e:els){
                String name=e.attributeValue("name");
                String value=e.attributeValue("value");
                //System.out.println(name+":"+value);
                property.put(name,value);
            }
            //获取节点property属性
            bean.setProperties(property);
            beans.add(bean);
        }
        for(Bean bean:beans){
            System.out.println(bean.getId());
            System.out.println(bean.getClazz());
            System.out.println(bean.getProperties());
            System.out.println("=================================");
        }
        return beans;
    }
    /***
     * 根据解析结果创建对象
     */
    public static Map<String,Object> createBeans() throws Exception{
        for(int i=0;i<beans.size();i++){
            String id=beans.get(i).getId();
            String classname=beans.get(i).getClazz();
            //根据classname获取类
            cls=Class.forName(classname);
            //创建反射获取类构建对象
            obj=cls.newInstance();
            //重新定义一个map集合
            Map<String,String> properties=new HashMap<>();

            properties=beans.get(i).getProperties();

            for(Entry<String,String> prop:properties.entrySet()){
                String name=prop.getKey();
                //   substring 截取   toUpperCase 转换大小写
                String methodName = "set"+name.substring(0,1).toUpperCase()+name.substring(1);
                String value=prop.getValue();
                //获取类中name的类型
                Field f=cls.getDeclaredField(name);
                //若当前属性有签名属性就返回  否则返回这个变量的类型
                Type type=f.getGenericType();
                Method method=cls.getMethod(methodName,(Class)type);
                Setter.setValue(type,value,obj,method);
            }
            beanList.put(id,obj);
        }
        return beanList;
    }
    //根据id查找bean对象
    public static Object getBean(String id){
        for (Entry<String, Object> object : beanList.entrySet()) {
            if(object.getKey().equals(id)){
                obj = object.getValue();
                System.out.println("=================================");
                System.out.println("Value = " + object.getValue());
            }
        }
        return obj;
    }
    public static void main(String[] args)throws Exception{
        //解析为对应的XML
        beans = parseXml();
        //将创建对象注入
        beanList = createBeans();
        //打印注入对象
        printUserMap(beanList);
        //获取id为1001的对象
        getBean("1001");
    }

}
