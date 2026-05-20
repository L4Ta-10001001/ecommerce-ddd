package com.expo.catalog.application.port.in;

import com.expo.catalog.application.command.ChangeProductPriceCommand;

public interface ChangeProductPriceUseCase {
    void changePrice(ChangeProductPriceCommand command);
}
