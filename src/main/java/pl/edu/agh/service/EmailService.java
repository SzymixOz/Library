package pl.edu.agh.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.edu.agh.repository.loans.LoanRepository;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;

@Service
public class EmailService {

    private final JavaMailSender emailSender;
    private final LoanRepository loanRepository;

    private List<String> emailsAndBooks;

    @Autowired
    public EmailService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
        this.emailSender = new JavaMailSender() {
            @Override
            public MimeMessage createMimeMessage() {
                return null;
            }

            @Override
            public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
                return null;
            }

            @Override
            public void send(MimeMessage... mimeMessages) throws MailException {

            }

            @Override
            public void send(SimpleMailMessage simpleMailMessage) {

            }

            @Override
            public void send(SimpleMailMessage... simpleMailMessages) throws org.springframework.mail.MailException {

            }
        };


    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@baeldung.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        emailSender.send(message);
    }

    public List<String> getEmailsForEmailNotifications(Timestamp endDate){
        return loanRepository.findEmailsAndBooksForEmailNotification(endDate);
    }


    public void sendEmails() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Timestamp endDateTimestamp = new Timestamp(timestamp.getTime() + 172800000);
//        LocalDate currentDate = LocalDate.now();
//        LocalDate endDate = currentDate.plusDays(2);
        System.out.println(endDateTimestamp);
        emailsAndBooks = getEmailsForEmailNotifications(endDateTimestamp);
        System.out.println(emailsAndBooks);
        for (String emailAndBook : emailsAndBooks) {
            System.out.println(emailAndBook);
//            this.sendEmail(emailAndBook.split(''), "Przypomnienie o zwrocie książki",
//                    "Przypominamy o zwrocie książki: .");
        }
    }
}
