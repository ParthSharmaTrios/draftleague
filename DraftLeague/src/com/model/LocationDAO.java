package com.model;

import com.common.CountryDTO;
import com.common.State;
import java.util.List;

public interface LocationDAO {
    
    public List<State> getStateList(int country, boolean includeAll);
    public List<CountryDTO> getCountryList(boolean includeAll);
}
