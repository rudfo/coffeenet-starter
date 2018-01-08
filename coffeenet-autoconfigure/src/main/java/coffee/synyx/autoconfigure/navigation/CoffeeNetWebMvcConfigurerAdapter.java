package coffee.synyx.autoconfigure.navigation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static java.lang.invoke.MethodHandles.lookup;


/**
 * Register the {@link CoffeeNetNavigationInterceptor} as {@link HandlerInterceptor} for all requests.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.15.0
 */
public class CoffeeNetWebMvcConfigurerAdapter implements WebMvcConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(lookup().lookupClass());

    private CoffeeNetNavigationInterceptor coffeeNetNavigationInterceptor;

    CoffeeNetWebMvcConfigurerAdapter(CoffeeNetNavigationInterceptor coffeeNetNavigationInterceptor) {

        this.coffeeNetNavigationInterceptor = coffeeNetNavigationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(coffeeNetNavigationInterceptor).addPathPatterns("/**");

        LOGGER.info("//> Added the CoffeeNetWebInterceptor");
    }
}
