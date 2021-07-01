import org.redisson.Redisson;
import org.redisson.api.RKeys;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;

import java.util.Date;
import java.util.Random;

import static java.lang.System.out;

public class RedisService {

    private static final int USERS = 20;
    private static RList<String> registeredUsers;

    // Объект для работы с Redis
    private RedissonClient redisson;

    // Объект для работы с ключами
    private RKeys rKeys;

    private final static String KEY = "REGISTERED_USERS";

    private double getTs() {
        return new Date().getTime() / 1000;
    }

    public static int getUSERS() {
        return USERS;
    }

    public static RList<String> getRegisteredUsers() {
        return registeredUsers;
    }

    public void listKeys() {
        Iterable<String> keys = rKeys.getKeys();
        for(String key: keys) {
            out.println("KEY: " + key + ", type:" + rKeys.getType(key));
        }
    }

    void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        try {
            redisson = Redisson.create(config);
        } catch (RedisConnectionException Exc) {
            out.println("Не удалось подключиться к Redis");
            out.println(Exc.getMessage());
        }
        rKeys = redisson.getKeys();
        registeredUsers = redisson.getList(KEY);
        rKeys.delete(KEY);
        for(int recNo = 1; recNo <= USERS; recNo++) {
            RecUsers(recNo);
        }
    }

    void RecUsers(int user_id)
    {
        //ZADD REGISTERED_USERS
        registeredUsers.add(user_id + " user");
    }

    void shutdown() {
        redisson.shutdown();
    }

    public static void extraordinaryShow() {
        int user_id = new Random().nextInt(USERS);
        String tempUser = registeredUsers.get(user_id);
        out.println("> Пользователь " + tempUser + " оплатил платную услугу");
        registeredUsers.fastRemove(user_id);
        registeredUsers.add(0, tempUser);

    }
}
