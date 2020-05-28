package services;

import com.sun.mail.smtp.SMTPTransport;
import javax.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SendEmailServiceTest {

    private transient SendEmailService sendEmailService;
    private transient SMTPTransport transport;

    @BeforeEach
    public void before() {
        transport = Mockito.mock(SMTPTransport.class);
        sendEmailService = new SendEmailService(transport);
    }

    @Test
    public void sendEmailTransportTest() throws Exception {
        sendEmailService.sendForgotPassEmail("test@gmail.com", "testUser", "pass");
        Mockito.verify(transport)
                .connect("smtp.gmail.com", "gamesnakegroup5@gmail.com", "Sn@keGame123");
        Mockito.verify(transport).sendMessage(Mockito.any(MimeMessage.class), Mockito.any());
        Mockito.verify(transport).close();
    }

    @Test
    public void sendActualEmailTest() throws Exception {
        sendEmailService = new SendEmailService();
        sendEmailService
                .sendForgotPassEmail("gamesnakegroup5@gmail.com", "test", "pass\n(not really)");
    }
}
