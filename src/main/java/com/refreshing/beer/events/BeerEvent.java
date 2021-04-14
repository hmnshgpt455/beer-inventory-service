package com.refreshing.beer.events;

import com.refreshing.beer.web.model.BeerDTO;
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
