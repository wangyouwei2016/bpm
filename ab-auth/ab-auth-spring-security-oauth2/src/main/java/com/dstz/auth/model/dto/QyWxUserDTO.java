package com.dstz.auth.model.dto;

import java.util.List;

/**
 * <pre>
 * 描述：企业微信成员详情
 * </pre>
 *
 * @author lightning
 */
public class QyWxUserDTO {

    private String userid; //成员UserID。对应管理端的帐号
    private String name;  //成员名称；第三方不可获取，调用时返回userid以代替name；对于非第三方创建的成员，第三方通讯录应用也不可获取；第三方页面需要通过通讯录展示组件来展示名字
    private String[] department; //成员所属部门id列表，仅返回该应用有查看权限的部门id
    private String[] order; //部门内的排序值，默认为0。数量必须和department一致，数值越大排序越前面。值范围是[0, 2^32)
    private String position; //职务信息；第三方仅通讯录应用可获取；对于非第三方创建的成员，第三方通讯录应用也不可获取
    private String mobile; //手机号码
    private String gender; //性别。0表示未定义，1表示男性，2表示女性
    private String email; //邮箱，第三方仅通讯录应用可获取；对于非第三方创建的成员，第三方通讯录应用也不可获取
    private List<Integer> is_leader_in_dept; //表示在所在的部门内是否为上级。0-否；1-是。是一个列表，数量必须与department一致。第三方仅通讯录应用可获取；对于非第三方创建的成员，第三方通讯录应用也不可获取
    private String avatar; //头像url。 第三方仅通讯录应用可获取；对于非第三方创建的成员，第三方通讯录应用也不可获取
    private String thumb_avatar; //头像缩略图url。第三方仅通讯录应用可获取；对于非第三方创建的成员，第三方通讯录应用也不可获取
    private String telephone;  //座机。
    private String alias; //别名；
    private int status; //激活状态: 1=已激活，2=已禁用，4=未激活，5=退出企业。
    private String address; //地址。
    private int hide_mobile;
    private String english_name;
    private String open_userid; //全局唯一。对于同一个服务商，不同应用获取到企业内同一个成员的open_userid是相同的，最多64个字节。仅第三方应用可获取
    private int main_department; //主部门

    private String qr_code; //员工个人二维码，扫描可添加为外部联系人(注意返回的是一个url，可在浏览器上打开该url以展示二维码)；第三方仅通讯录应用可获取；对于非第三方创建的成员，第三方通讯录应用也不可获取
    private String external_position; //对外职务，如果设置了该值，则以此作为对外展示的职务，否则以position来展示。第三方仅通讯录应用可获取；对于非第三方创建的成员，第三方通讯录应用也不可获取

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getDepartment() {
        return department;
    }

    public void setDepartment(String[] department) {
        this.department = department;
    }

    public String[] getOrder() {
        return order;
    }

    public void setOrder(String[] order) {
        this.order = order;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Integer> getIs_leader_in_dept() {
        return is_leader_in_dept;
    }

    public void setIs_leader_in_dept(List<Integer> is_leader_in_dept) {
        this.is_leader_in_dept = is_leader_in_dept;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getThumb_avatar() {
        return thumb_avatar;
    }

    public void setThumb_avatar(String thumb_avatar) {
        this.thumb_avatar = thumb_avatar;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getHide_mobile() {
        return hide_mobile;
    }

    public void setHide_mobile(int hide_mobile) {
        this.hide_mobile = hide_mobile;
    }

    public String getEnglish_name() {
        return english_name;
    }

    public void setEnglish_name(String english_name) {
        this.english_name = english_name;
    }

    public String getOpen_userid() {
        return open_userid;
    }

    public void setOpen_userid(String open_userid) {
        this.open_userid = open_userid;
    }

    public int getMain_department() {
        return main_department;
    }

    public void setMain_department(int main_department) {
        this.main_department = main_department;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public String getExternal_position() {
        return external_position;
    }

    public void setExternal_position(String external_position) {
        this.external_position = external_position;
    }
}
