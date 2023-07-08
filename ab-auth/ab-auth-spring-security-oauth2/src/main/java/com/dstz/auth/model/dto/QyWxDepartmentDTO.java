package com.dstz.auth.model.dto;

/**
 * <pre>
 * 描述：企业微信部门
 * </pre>
 *
 * @author lightning
 */
public class QyWxDepartmentDTO {


    private int id; //创建的部门id
    private String name; //部门名称
    private String name_en; //英文名称
    private int parentid; //父部门id
    private int order; //在父部门中的次序值。order值大的排序靠前。值范围是[0, 2^32)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
