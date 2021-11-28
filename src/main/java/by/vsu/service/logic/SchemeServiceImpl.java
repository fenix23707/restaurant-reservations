package by.vsu.service.logic;

import by.vsu.dao.SchemeDao;
import by.vsu.model.Comparison;
import by.vsu.model.Scheme;
import by.vsu.service.SchemeService;
import by.vsu.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchemeServiceImpl implements SchemeService {
    private SchemeDao schemeDao;

    @Autowired
    public void setSchemeDao(SchemeDao schemeDao) {
        this.schemeDao = schemeDao;
    }

    @Override
    public List<Scheme> getAll(int pageSize, int pageNum) {
        return schemeDao.findAll(pageSize, pageSize * pageNum);
    }

    @Override
    public List<Scheme> getAllByWidthHeight(int width, Comparison compWidth, int height, Comparison compHeight, int pageSize, int pageNum) {
        return schemeDao.findAllByWidthHeight(width, height, compWidth, compWidth, pageSize, pageSize * pageNum);
    }

    @Override
    public List<Scheme> getAllBySquare(int square, Comparison comparison, int pageSize, int pageNum) {
        return schemeDao.findAllBySquare(square, comparison, pageSize, pageSize * pageNum);
    }

    @Override
    public Scheme getById(Integer id) {
        return schemeDao.findById(id);
    }

    @Override
    public Scheme getByRestaurantId(Integer restaurantId) {
        return schemeDao.findByRestaurantId(restaurantId);
    }

    @Override
    public Scheme save(Scheme scheme) {
        if (scheme.getId() == null) {
            schemeDao.create(scheme);
        } else {
            Scheme old = schemeDao.findById(scheme.getId());
            if (old == null) {
                throw new NotFoundException("Scheme with id = " + scheme.getId() + " not found.");
            }
            schemeDao.update(scheme);
        }

        return scheme;
    }

    @Override
    public boolean delete(Integer id) {
        return schemeDao.delete(id) != 0;
    }
}
