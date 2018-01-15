package coffee.synyx.autoconfigure.navigation;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * @author  Tobias Schneider
 */
@RunWith(MockitoJUnitRunner.class)
public class CoffeeNetNavigationInformationMvcConfigurerAdapterTest {

    private CoffeeNetWebMvcConfigurerAdapter sut;

    @Mock
    private CoffeeNetNavigationInterceptor coffeeNetNavigationInterceptorMock;

    @Before
    public void setup() {

        sut = new CoffeeNetWebMvcConfigurerAdapter(coffeeNetNavigationInterceptorMock);
    }


    @Test
    public void addInterceptors() throws Exception {

        InterceptorRegistry interceptorRegistryMock = mock(InterceptorRegistry.class);
        InterceptorRegistration interceptorRegistrationMock = mock(InterceptorRegistration.class);
        when(interceptorRegistryMock.addInterceptor(coffeeNetNavigationInterceptorMock)).thenReturn(
            interceptorRegistrationMock);

        sut.addInterceptors(interceptorRegistryMock);

        verify(interceptorRegistryMock).addInterceptor(coffeeNetNavigationInterceptorMock);
        verify(interceptorRegistrationMock).addPathPatterns("/**");
    }
}
