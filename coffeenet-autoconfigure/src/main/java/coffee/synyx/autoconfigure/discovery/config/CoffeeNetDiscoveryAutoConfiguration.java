package coffee.synyx.autoconfigure.discovery.config;

import coffee.synyx.autoconfigure.CoffeeNetConfigurationProperties;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetAppService;
import coffee.synyx.autoconfigure.discovery.service.DevelopmentCoffeeNetAppService;
import coffee.synyx.autoconfigure.discovery.service.IntegrationCoffeeNetAppService;

import com.netflix.discovery.EurekaClientConfig;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.env.ConfigurableEnvironment;

import org.springframework.util.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;

import static coffee.synyx.autoconfigure.CoffeeNetConfigurationProperties.DEVELOPMENT;
import static coffee.synyx.autoconfigure.CoffeeNetConfigurationProperties.INTEGRATION;

import static org.springframework.cloud.commons.util.IdUtils.getDefaultInstanceId;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;


/**
 * Discovery Configuration.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
@Configuration
@AutoConfigureBefore(EurekaClientAutoConfiguration.class)
@ConditionalOnProperty(prefix = "coffeenet.discovery", name = "enabled", havingValue = "true", matchIfMissing = true)
public class CoffeeNetDiscoveryAutoConfiguration {

    @Configuration
    @ConditionalOnClass(EurekaClientConfig.class)
    @ConditionalOnProperty(prefix = "coffeenet", name = "profile", havingValue = DEVELOPMENT, matchIfMissing = true)
    static class DevelopmentCoffeeNetServiceDiscoveryConfiguration {

        @Bean
        @ConditionalOnMissingBean(CoffeeNetAppService.class)
        CoffeeNetAppService coffeeNetAppService() {

            return new DevelopmentCoffeeNetAppService();
        }
    }

    @Configuration
    @EnableDiscoveryClient
    @ConditionalOnClass(EurekaClientConfig.class)
    @ConditionalOnProperty(prefix = "coffeenet", name = "profile", havingValue = INTEGRATION)
    static class IntegrationCoffeeNetServiceDiscoveryConfiguration {

        private final DiscoveryClient discoveryClient;

        @Autowired
        public IntegrationCoffeeNetServiceDiscoveryConfiguration(DiscoveryClient discoveryClient) {

            this.discoveryClient = discoveryClient;
        }

        @Bean
        @ConditionalOnMissingBean(CoffeeNetAppService.class)
        CoffeeNetAppService coffeeNetAppService() {

            return new IntegrationCoffeeNetAppService(discoveryClient);
        }
    }

    @Configuration
    @ConditionalOnClass(EurekaDiscoveryClient.class)
    @ConditionalOnProperty(prefix = "coffeenet", name = "profile", havingValue = INTEGRATION)
    @EnableConfigurationProperties({ CoffeeNetConfigurationProperties.class, CoffeeNetDiscoveryProperties.class })
    static class CoffeeNetDiscoveryPropertiesConfiguration {

        private CoffeeNetConfigurationProperties coffeeNetConfigurationProperties;

        @Autowired
        CoffeeNetDiscoveryPropertiesConfiguration(CoffeeNetConfigurationProperties coffeeNetConfigurationProperties) {

            this.coffeeNetConfigurationProperties = coffeeNetConfigurationProperties;
        }

        @Bean
        CoffeeNetDiscoveryInstanceProperties eurekaInstanceConfigBean(InetUtils inetUtils, ConfigurableEnvironment env)
            throws MalformedURLException {

            String hostname = env.getProperty("coffeenet.discovery.instance.hostname");
            boolean preferIpAddress = parseBoolean(env.getProperty("coffeenet.discovery.instance.preferIpAddress"));
            int nonSecurePort = parseInt(env.getProperty("server.port", env.getProperty("port", "8080")));
            int managementPort = parseInt(env.getProperty("management.port", String.valueOf(nonSecurePort)));
            String managementContextPath = env.getProperty("management.contextPath",
                    env.getProperty("server.contextPath", "/"));

            CoffeeNetDiscoveryInstanceProperties instance = new CoffeeNetDiscoveryInstanceProperties(inetUtils,
                    coffeeNetConfigurationProperties);
            instance.setNonSecurePort(nonSecurePort);
            instance.setInstanceId(getDefaultInstanceId(env));
            instance.setPreferIpAddress(preferIpAddress);

            if (managementPort != nonSecurePort && managementPort != 0) {
                if (StringUtils.hasText(hostname)) {
                    instance.setHostname(hostname);
                }

                String statusPageUrlPath = env.getProperty("coffeenet.discovery.instance.statusPageUrlPath");
                String healthCheckUrlPath = env.getProperty("coffeenet.discovery.instance.healthCheckUrlPath");

                if (!managementContextPath.endsWith("/")) {
                    managementContextPath = managementContextPath + "/";
                }

                if (StringUtils.hasText(statusPageUrlPath)) {
                    instance.setStatusPageUrlPath(statusPageUrlPath);
                }

                if (StringUtils.hasText(healthCheckUrlPath)) {
                    instance.setHealthCheckUrlPath(healthCheckUrlPath);
                }

                String scheme = instance.getSecurePortEnabled() ? "https" : "http";
                URL base = new URL(scheme, instance.getHostname(), managementPort, managementContextPath);
                instance.setStatusPageUrl(
                    new URL(base, StringUtils.trimLeadingCharacter(instance.getStatusPageUrlPath(), '/')).toString());
                instance.setHealthCheckUrl(
                    new URL(base, StringUtils.trimLeadingCharacter(instance.getHealthCheckUrlPath(), '/')).toString());
            }

            String allowedAuthorities = coffeeNetConfigurationProperties.getAllowedAuthorities();

            if (allowedAuthorities != null) {
                instance.getMetadataMap().put("allowedAuthorities", allowedAuthorities);
            }

            return instance;
        }


        @Bean
        CoffeeNetDiscoveryClientProperties eurekaClientConfigBean() {

            return new CoffeeNetDiscoveryClientProperties();
        }
    }
}
