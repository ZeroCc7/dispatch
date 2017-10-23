package com.wlwx.dispatch.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//@Alias("user")
public class User {
    private int user_id;
    private int cust_id;
    private String user_name;
    private String passwd;
    private String nick_name;
    private String allow_edit;
    private String last_login_time;
    private String valid_time_begin;
    private String valid_time_end;
    private String email;
    private String telephone;
    private String im;
    private String address;
    private String status;
    private String note;
    private String create_time;
    private String create_by;
    private String smsverification;
    private String current_login_ip;
    private String last_login_ip;
    private String current_login_time;
    private String passwd_expire_time;
    private String workflow_user_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCust_id() {
        return cust_id;
    }

    public void setCust_id(int cust_id) {
        this.cust_id = cust_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getAllow_edit() {
        return allow_edit;
    }

    public void setAllow_edit(String allow_edit) {
        this.allow_edit = allow_edit;
    }

    public String getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }

    public String getValid_time_begin() {
        return valid_time_begin;
    }

    public void setValid_time_begin(String valid_time_begin) {
        this.valid_time_begin = valid_time_begin;
    }

    public String getValid_time_end() {
        return valid_time_end;
    }

    public void setValid_time_end(String valid_time_end) {
        this.valid_time_end = valid_time_end;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getIm() {
        return im;
    }

    public void setIm(String im) {
        this.im = im;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCreate_by() {
        return create_by;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public String getSmsverification() {
        return smsverification;
    }

    public void setSmsverification(String smsverification) {
        this.smsverification = smsverification;
    }

    public String getCurrent_login_ip() {
        return current_login_ip;
    }

    public void setCurrent_login_ip(String current_login_ip) {
        this.current_login_ip = current_login_ip;
    }

    public String getLast_login_ip() {
        return last_login_ip;
    }

    public void setLast_login_ip(String last_login_ip) {
        this.last_login_ip = last_login_ip;
    }

    public String getCurrent_login_time() {
        return current_login_time;
    }

    public void setCurrent_login_time(String current_login_time) {
        this.current_login_time = current_login_time;
    }

    public String getPasswd_expire_time() {
        return passwd_expire_time;
    }

    public void setPasswd_expire_time(String passwd_expire_time) {
        this.passwd_expire_time = passwd_expire_time;
    }

    public String getWorkflow_user_id() {
        return workflow_user_id;
    }

    public void setWorkflow_user_id(String workflow_user_id) {
        this.workflow_user_id = workflow_user_id;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", cust_id=" + cust_id +
                ", user_name='" + user_name + '\'' +
                ", passwd='" + passwd + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", allow_edit='" + allow_edit + '\'' +
                ", last_login_time='" + last_login_time + '\'' +
                ", valid_time_begin='" + valid_time_begin + '\'' +
                ", valid_time_end='" + valid_time_end + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", im='" + im + '\'' +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                ", note='" + note + '\'' +
                ", create_time='" + create_time + '\'' +
                ", create_by='" + create_by + '\'' +
                ", smsverification='" + smsverification + '\'' +
                ", current_login_ip='" + current_login_ip + '\'' +
                ", last_login_ip='" + last_login_ip + '\'' +
                ", current_login_time='" + current_login_time + '\'' +
                ", passwd_expire_time='" + passwd_expire_time + '\'' +
                ", workflow_user_id='" + workflow_user_id + '\'' +
                '}';
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return authorities;
    }

}
