import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Map;

public class Loader {
    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        jedis.zadd("CITY", 19997, "Moscow");
        jedis.zadd("CITY", 22148, "Sochi");
        jedis.zadd("CITY", 21184, "Saint Petersburg");
        jedis.zadd("CITY", 15966, "Novosibirsk");
        jedis.zadd("CITY", 20998, "Krasnodar");
        jedis.zadd("CITY", 21004, "Kazan");
        jedis.zadd("CITY", 16048, "Yekaterinburg");
        jedis.zadd("CITY", 25472, "Vladivostok");
        jedis.zadd("CITY", 27364, "Voronezh");
        jedis.zadd("CITY", 21058, "Anapa");

        System.out.println(jedis.zrevrange("CITY", 0, 2));
        System.out.println(jedis.zrevrange("CITY", -3, -1));


    }
}
