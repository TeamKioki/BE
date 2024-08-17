package com.dev.kioki.global.sms;

import com.dev.kioki.global.error.handler.SmsException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

import static com.dev.kioki.global.common.code.status.ErrorStatus.SMS_CODE_SEND_FAIL;

@Slf4j
@Component
@RequiredArgsConstructor
public class SmsUtil {

    @Value("${sms.api.key}")
    private String apiKey;
    @Value("${sms.api.secret}")
    private String apiSecretKey;
    @Value("${sms.api.sender}")
    private String sender;

    private DefaultMessageService messageService;

    @PostConstruct
    private void init(){
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecretKey, "https://api.coolsms.co.kr");
    }

    public void sendCode(String to, String code) {
        try {
            Message message = new Message();
            message.setFrom(sender);
            message.setTo(to);
            message.setText("[KIOKI] 인증번호를 입력해주세요. \n" + code);

            this.messageService.sendOne(new SingleMessageSendingRequest(message));
        } catch (Exception e) {
            throw new SmsException(SMS_CODE_SEND_FAIL);
        }
    }

    public String createCode() {
        StringBuffer code = new StringBuffer();
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(3);

            switch (index) {
                case 0:
                    code.append((char) (random.nextInt(26) + 'A'));
                    break;
                case 1:
                    code.append((char) (random.nextInt(26) + 'a'));
                    break;
                case 2:
                    code.append((random.nextInt(10)));
                    break;
            }
        }
        return code.toString();
    }
}
