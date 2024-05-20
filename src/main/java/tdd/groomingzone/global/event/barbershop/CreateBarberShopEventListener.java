package tdd.groomingzone.global.event.barbershop;

import com.slack.api.Slack;
import com.slack.api.model.Attachment;
import com.slack.api.model.Field;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.io.IOException;
import java.util.List;

import static com.slack.api.webhook.WebhookPayloads.payload;

@Component
@Slf4j
public class CreateBarberShopEventListener {
    public static final String SLACK_ATTACHMENT_COLOR_CODE = "ff0000";
    @Value("${slack.url}")
    private String webHookUrl;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendSlackAlert(CreateBarberShopEvent event) {
        Slack slack = Slack.getInstance();
        String eventMessage = event.message();
        String barberShopName = event.barberShopName();
        String barberShopPhoneNumber = event.barberShopNumber();
        String ownerNickName = event.barberShopOwnerNickName();
        String ownerPhoneNumber = event.barberShopOwnerPhoneNumber();

        try {
            slack.send(webHookUrl, payload(p -> p.text(event.message())
                    .attachments(List.of(generateSlackAttachments(eventMessage, barberShopName, barberShopPhoneNumber, ownerNickName, ownerPhoneNumber)))));
            log.info(eventMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Attachment generateSlackAttachments(String eventMessage, String barberShopName, String barberShopPhoneNumber, String ownerNickName, String ownerPhoneNumber) {
        return Attachment.builder()
                .color(SLACK_ATTACHMENT_COLOR_CODE)
                .title(eventMessage)
                .fields(List.of(
                        generateSlackField("바버샵 이름", barberShopName),
                        generateSlackField("바버샵 전화번호", barberShopPhoneNumber),
                        generateSlackField("원장 바버 닉네임", ownerNickName),
                        generateSlackField("원장 바버 휴대폰 번호", ownerPhoneNumber)
                ))
                .build();
    }

    private Field generateSlackField(String title, String value) {
        return Field.builder()
                .title(title)
                .value(value)
                .build();
    }
}