package cn.xdl.dom4j;

public class Property {
    private String name;
    private String info;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Property() {
        super();
    }

    public Property(String name, String info) {
        this.name = name;
        this.info = info;
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
