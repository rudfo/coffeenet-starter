package coffee.synyx.autoconfigure.navigation;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;


/**
 * Provides information that are needed.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.15.0
 */
@Endpoint(id = "coffeenet/navigation")
public class CoffeeNetNavigationEndpoint {

    private final CoffeeNetNavigationService coffeeNetNavigationService;

    CoffeeNetNavigationEndpoint(CoffeeNetNavigationService coffeeNetNavigationService) {

        this.coffeeNetNavigationService = coffeeNetNavigationService;
    }

    @ReadOperation
    public CoffeeNetNavigationInformation getNavigationInformation() {

        return coffeeNetNavigationService.get();
    }
}
