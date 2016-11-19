package coffee.synyx.autoconfigure.discovery.config.development;

import coffee.synyx.autoconfigure.discovery.config.CoffeeNetServiceDiscoveryConfiguration;
import coffee.synyx.autoconfigure.discovery.endpoint.CoffeeNetAppsEndpoint;
import coffee.synyx.autoconfigure.discovery.service.AppService;
import coffee.synyx.autoconfigure.discovery.service.MockAppService;
import coffee.synyx.autoconfigure.security.user.CoffeeNetCurrentUserService;

import com.netflix.discovery.DiscoveryClient;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static coffee.synyx.autoconfigure.CoffeeNetConfigurationProperties.DEVELOPMENT;


/**
 * Development service discovery mock bean instantiation.
 *
 * @author  David Schilling - schilling@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
@Configuration
@ConditionalOnClass(DiscoveryClient.class)
@ConditionalOnMissingBean(CoffeeNetServiceDiscoveryConfiguration.class)
@ConditionalOnProperty(prefix = "coffeenet", name = "profile", havingValue = DEVELOPMENT, matchIfMissing = true)
public class DevelopmentCoffeeNetServiceDiscoveryConfiguration implements CoffeeNetServiceDiscoveryConfiguration {

    @Bean
    @Override
    public AppService coffeeNetAppService() {

        return new MockAppService();
    }


    @Bean
    @Override
    @Autowired
    public CoffeeNetAppsEndpoint coffeeNetAppsEndpoint(CoffeeNetCurrentUserService coffeeNetCurrentUserService) {

        return new CoffeeNetAppsEndpoint(coffeeNetAppService(), coffeeNetCurrentUserService);
    }
}
