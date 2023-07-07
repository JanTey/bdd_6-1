package ru.netology.quamid59.test;

import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.quamid59.data.DataGeneration;
import ru.netology.quamid59.page.LoginPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


class TransferTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @AfterEach
    void memoryClear() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
    }

    @Test
    void transferFromSecondToFirstCard() {

        val amount = 100;
        val loginPage = new LoginPage();
        val authInfo = DataGeneration.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataGeneration.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.checkHeadingYourCards();
        val initialBalanceToCard = dashboardPage.getFirstCardBalance();
        val initialBalanceFromCard = dashboardPage.getSecondCardBalance();

        val transferPage = dashboardPage.validChoosePayFirst();
        transferPage.checkHeadingPaymentCards();
        transferPage.setPayCardNumber(DataGeneration.getSecondCard(), amount);
        val dashboardPage1 = transferPage.validPayCard();
        val actual = dashboardPage1.getFirstCardBalance();
        val expected = initialBalanceToCard + amount;
        val actual2 = dashboardPage1.getSecondCardBalance();
        val expected2 = initialBalanceFromCard - amount;
        assertEquals(expected, actual);
        assertEquals(expected2, actual2);
    }

    @Test
    void transferFromFirstToSecondCard() {
        val amount = 100;
        val loginPage = new LoginPage();
        val authInfo = DataGeneration.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataGeneration.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.checkHeadingYourCards();
        val initialBalanceToCard = dashboardPage.getSecondCardBalance();
        val initialBalanceFromCard = dashboardPage.getFirstCardBalance();

        val transferPage = dashboardPage.validChoosePaySecond();
        transferPage.checkHeadingPaymentCards();
        transferPage.setPayCardNumber(DataGeneration.getFirstCard(), amount);
        val dashboardPage1 = transferPage.validPayCard();
        val actual1 = dashboardPage1.getSecondCardBalance();
        val expected1 = initialBalanceToCard + amount;
        val actual2 = dashboardPage1.getFirstCardBalance();
        val expected2 = initialBalanceFromCard - amount;
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @Test
    void transferIfAmountGreaterThanLimitSecondCard() {
        val amount = 12_000;
        val loginPage = new LoginPage();
        val authInfo = DataGeneration.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataGeneration.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.checkHeadingYourCards();

        val transferPage = dashboardPage.validChoosePayFirst();
        transferPage.checkHeadingPaymentCards();
        transferPage.setPayCardNumber(DataGeneration.getSecondCard(), amount);
        transferPage.validPayExtendAmount();
    }
    @Test
    void transferIfAmountGreaterThanLimitFirstCard() {
        val amount = 30_000;
        val loginPage = new LoginPage();
        val authInfo = DataGeneration.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataGeneration.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.checkHeadingYourCards();

        val transferPage = dashboardPage.validChoosePaySecond();
        transferPage.checkHeadingPaymentCards();
        transferPage.setPayCardNumber(DataGeneration.getFirstCard(), amount);
        transferPage.validPayExtendAmount();
    }

}

