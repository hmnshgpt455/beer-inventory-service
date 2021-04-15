package io.github.hmnshgpt455.brewery.events;

import io.github.hmnshgpt455.brewery.model.BeerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BeerEvent implements Serializable {

    static final long serialVersionUID = -962275658863907314L;

    private BeerDTO beerDTO;
}
