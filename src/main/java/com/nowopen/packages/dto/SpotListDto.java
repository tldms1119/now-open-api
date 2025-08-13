package com.nowopen.packages.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SpotListDto {

    public String type; // MAP or LIST
    public double latitude;
    public double longitude;
    public double radius;

    public int page = 0;
    public int size = 10;
    public List<SpotsDto> list;


}
