package com.oopporject.railsheba.email;

public class OTPMail {
    public static void sendOTPReg(String to, int OTP) {
        EmailBrevoSMTP ebs = new EmailBrevoSMTP();
        String body = "Welcome to RailSheba Platform. <br>Here is your 6 digit otp: <b>" + OTP + "</b>"
                + "<br>Please do not share your OTP with anybody.";
        ebs.sendMail(to, "i.mominulislam001@gmail.com", "RailSheba: Verification Code", body);
    }

    public static void sendOTPForgot(String to, int OTP) {
        EmailBrevoSMTP ebs = new EmailBrevoSMTP();
        String body = "Here is your otp to reset your password: <b>" + OTP + "</b>"
                + "<br>Please do not share your OTP with anybody.";
        ebs.sendMail(to, "i.mominulislam001@gmail.com", "RailSheba: Password Reset Code", body);
    }

    public static void main(String[] args) {
        sendOTPReg("fahimaislammomo@gmail.com", 123456);
    }
}
