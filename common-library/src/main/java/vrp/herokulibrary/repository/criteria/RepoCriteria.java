package vrp.herokulibrary.repository.criteria;

import org.springframework.data.r2dbc.query.Criteria;

public interface RepoCriteria {
    Criteria constructCriteria();
}
