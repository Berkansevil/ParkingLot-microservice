package RateLimitGatewayFilter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitGatewayFilterFactory extends AbstractGatewayFilterFactory<RateLimitGatewayFilterFactory.Config> {

    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    public RateLimitGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String key = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
            Bucket bucket = buckets.computeIfAbsent(key, k -> createNewBucket(config));

            if (bucket.tryConsume(1)) {
                return chain.filter(exchange);
            } else {
                exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                byte[] bytes = "Too many requests. Please try again later.".getBytes();
                exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
                exchange.getResponse().getHeaders().setContentLength(bytes.length);
                return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes)));
            }
        };
    }

    private Bucket createNewBucket(Config config) {
        Refill refill = Refill.greedy(config.getLimit(), Duration.ofMinutes(config.getDuration()));
        Bandwidth limit = Bandwidth.classic(config.getLimit(), refill);
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @Override
    public Class<Config> getConfigClass() {
        return Config.class;
    }


    public static class Config {
        private int limit;
        private int duration;

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }
    }
}