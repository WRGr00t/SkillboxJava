import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.System.out;

public class Loader {

    private static final int SLEEP = 1000;
    private static final RedisService redis = new RedisService();
    private static final SimpleDateFormat DF = new SimpleDateFormat("HH:mm:ss");

    public static void main(String[] args) throws InterruptedException {

        redis.init();
        int i = 1;
        redis.listKeys();
        while (!RedisService.getRegisteredUsers().isEmpty()) {
            i = i % 10;
            if (i == 0){
                RedisService.extraordinaryShow();
            }
            String first = RedisService.getRegisteredUsers().get(0);
            String log = String.format("[%s] - На главной странице показываем пользователя %s",
                    DF.format(new Date()), RedisService.getRegisteredUsers().get(0));
            RedisService.getRegisteredUsers().fastRemove(0);
            out.println(log);
            RedisService.getRegisteredUsers().add(first);
            i++;
            Thread.sleep(SLEEP);
        }
        redis.shutdown();
    }
}
