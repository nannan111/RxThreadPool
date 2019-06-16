package model;

import com.alibaba.fastjson.annotation.JSONField;

public class User {


    /**
     * name : Ravi Tamada
     * email : ravi8x@gmail.com
     * phone : {"home":"08947 000000","mobile":"9999999999"}
     */

    @JSONField(name = "name")
    private String name;
    @JSONField(name = "email")
    private String email;
    @JSONField(name = "phone")
    private PhoneBean phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PhoneBean getPhone() {
        return phone;
    }

    public void setPhone(PhoneBean phone) {
        this.phone = phone;
    }

    public static class PhoneBean {
        /**
         * home : 08947 000000
         * mobile : 9999999999
         */

        @JSONField(name = "home")
        private String home;
        @JSONField(name = "mobile")
        private String mobile;

        public String getHome() {
            return home;
        }

        public void setHome(String home) {
            this.home = home;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                '}';
    }
}
