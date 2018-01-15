package coffee.synyx.autoconfigure.discovery.config;

import coffee.synyx.autoconfigure.CoffeeNetConfigurationProperties;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.commons.util.InetUtilsProperties;

import org.springframework.mock.env.MockEnvironment;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.is;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class CoffeeNetDiscoveryInstancePropertiesTest {

    @Test
    public void defaultValues() {

        CoffeeNetDiscoveryInstanceProperties sut = new CoffeeNetDiscoveryInstanceProperties(new InetUtils(
                    new InetUtilsProperties()), new CoffeeNetConfigurationProperties());

        assertThat(sut.getHostname(), is("localhost"));
    }


    @Test
    public void applicationName() {

        String brandNewApplicationName = "BrandNewApplicationName";

        CoffeeNetConfigurationProperties coffeeNetConfigurationProperties = new CoffeeNetConfigurationProperties();
        coffeeNetConfigurationProperties.setApplicationName(brandNewApplicationName);

        CoffeeNetDiscoveryInstanceProperties sut = new CoffeeNetDiscoveryInstanceProperties(new InetUtils(
                    new InetUtilsProperties()), coffeeNetConfigurationProperties);

        sut.setEnvironment(new MockEnvironment());

        assertThat(sut.getAppname(), is(brandNewApplicationName));
        assertThat(sut.getVirtualHostName(), is(brandNewApplicationName));
        assertThat(sut.getSecureVirtualHostName(), is(brandNewApplicationName));
    }


    @Test
    public void applicationNameIsUnknown() {

        CoffeeNetConfigurationProperties coffeeNetConfigurationProperties = new CoffeeNetConfigurationProperties();
        coffeeNetConfigurationProperties.setApplicationName("");

        CoffeeNetDiscoveryInstanceProperties sut = new CoffeeNetDiscoveryInstanceProperties(new InetUtils(
                    new InetUtilsProperties()), coffeeNetConfigurationProperties);

        sut.setEnvironment(new MockEnvironment());

        String unknownApplicationName = "unknown";
        assertThat(sut.getAppname(), is(unknownApplicationName));
        assertThat(sut.getVirtualHostName(), is(unknownApplicationName));
        assertThat(sut.getSecureVirtualHostName(), is(unknownApplicationName));
    }
}
