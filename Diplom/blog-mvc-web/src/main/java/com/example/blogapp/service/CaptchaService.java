package com.example.blogapp.service;

import com.example.blogapp.api.response.CaptchaResponse;
import com.example.blogapp.entity.CaptchaCode;
import com.example.blogapp.repository.CaptchaRepo;
import com.github.cage.GCage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;

@Service
@EnableScheduling
public class CaptchaService {

    @Autowired
    private CaptchaRepo captchaRepo;

    @Value("${captcha.age}")
    private long CAPTCHA_AGE;

    private final GCage cage = new GCage();

    public CaptchaService() {
    }

    public ArrayList<CaptchaCode> getAllCaptcha() {
        return (ArrayList<CaptchaCode>) captchaRepo.findAll();
    }

    public void saveCaptcha(CaptchaCode captchaCode) {
        captchaRepo.save(captchaCode);
    }

    public CaptchaCode findByCode(String code) {
        Optional<CaptchaCode> result = captchaRepo.findCaptchaCodeByCode(code);
        return result.orElse(null);
    }

    @Scheduled(fixedRate = 3_600_000)
    public void scheduleDelete() {
        captchaRepo.deleteCaptcha(CAPTCHA_AGE);
    }

    public CaptchaCode generateCaptcha() {
        CaptchaCode result = new CaptchaCode();
        String token = cage.getTokenGenerator().next();
        result.setTime(LocalDateTime.now());
        result.setCode(getStringImage(token));
        result.setSecretCode(token);

        return result;
    }

    public String getStringImage(String secret) {
        StringBuilder stringBuilder = new StringBuilder();
        byte[] imgContent = cage.draw(secret);
        BufferedImage bufferedImage = createImageFromBytes(imgContent);
        bufferedImage = resize(bufferedImage, 100, 35);
        try {
            imgContent = toByteArray(bufferedImage, "png");
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        String encodedString = Base64.getEncoder().encodeToString(imgContent);
        String STRING_HEADER = "data:image/png;base64, ";
        return stringBuilder.append(STRING_HEADER)
                .append(encodedString)
                .toString();
    }

    public CaptchaResponse generateCaptchaResponse() {
        CaptchaResponse captchaResponse = new CaptchaResponse();
        String secret = generateCaptcha().getSecretCode();
        captchaResponse.setSecret(secret);
        captchaResponse.setImage(getStringImage(secret));
        CaptchaCode captchaCode = new CaptchaCode(captchaResponse);
        //captchaCode.setSecretCode(getStringImage(secret));
        //captchaRepo.save(captchaCode);
        return captchaResponse;
    }

    private BufferedImage createImageFromBytes(byte[] imageData) {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        try {
            return ImageIO.read(bais);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] toByteArray(BufferedImage bi, String format)
            throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, format, baos);
        return baos.toByteArray();

    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}
