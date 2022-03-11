package com.pg.ks;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Headers {

    private int imageNameColumn;
    private List<Integer> columnOrder;
}
