package sk.stuba.fei.uim.vsa.pr2.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import sk.stuba.fei.uim.vsa.pr2.CarParkService;

public class AbstractResource {
    protected static final String EMPTY_RESPONSE = "{}";
    protected final ObjectMapper json = new ObjectMapper();
    protected final CarParkService cps = new CarParkService();
}
