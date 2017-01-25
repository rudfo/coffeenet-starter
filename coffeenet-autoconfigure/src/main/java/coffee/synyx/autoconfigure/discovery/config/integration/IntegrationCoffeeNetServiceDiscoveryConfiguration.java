package coffee.synyx.autoconfigure.discovery.config.integration;

import coffee.synyx.autoconfigure.discovery.service.CoffeeNetAppService;
import coffee.synyx.autoconfigure.discovery.service.IntegrationEurekaCoffeeNetAppService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static coffee.synyx.autoconfigure.CoffeeNetConfigurationProperties.INTEGRATION;


/**
 * Production service discovery bean instantiation.
 *
 * @author  David Schilling - schilling@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
@Configuration
@EnableEurekaClient
@ConditionalOnClass(DiscoveryClient.class)
@ConditionalOnProperty(prefix = "coffeenet", name = "profile", havingValue = INTEGRATION)
public class IntegrationCoffeeNetServiceDiscoveryConfiguration {

    private final DiscoveryClient discoveryClient;

    @Autowired
    public IntegrationCoffeeNetServiceDiscoveryConfiguration(DiscoveryClient discoveryClient) {

        this.discoveryClient = discoveryClient;
    }

    @Bean
    @ConditionalOnMissingBean(CoffeeNetAppService.class)
    public CoffeeNetAppService coffeeNetAppService() {

        return new IntegrationEurekaCoffeeNetAppService(discoveryClient);
    }
}
