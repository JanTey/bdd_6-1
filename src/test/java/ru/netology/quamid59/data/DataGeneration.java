package ru.netology.quamid59.data;

import lombok.Value;

public class DataGeneration {
    private DataGeneration() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    public static String getFirstCard() {
        return "5559 0000 0000 0001";
    }

    public static String getSecondCard() {
        return "5559 0000 0000 0002";
    }

}