package by.vsu.service.logic;

import by.vsu.dao.TableDao;
import by.vsu.model.Comparison;
import by.vsu.model.Position;
import by.vsu.model.Table;
import by.vsu.service.TableService;
import by.vsu.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableServiceImpl implements TableService {
    private TableDao tableDao;

    @Autowired
    public void setTableDao(TableDao tableDao) {
        this.tableDao = tableDao;
    }

    @Override
    public List<Table> getAll(int pageSize, int pageNum) {
        return tableDao.findAll(pageSize, pageSize * pageNum);
    }

    @Override
    public List<Table> getAllByCapacity(int capacity, Comparison comparison, int pageSize, int pageNum) {
        return tableDao.findAllByCapacity(capacity, comparison, pageSize, pageSize * pageNum);
    }

    @Override
    public List<Table> getAllByWidthHeight(int width, Comparison compWidth, int height, Comparison compHeight, int pageSize, int pageNum) {
        return tableDao.findAllByWidthHeight(width, height, compWidth, compHeight, pageSize, pageSize * pageNum);
    }

    @Override
    public List<Table> getAllByPosition(Position position, Comparison compX, Comparison compY, int pageSize, int pageNum) {
        return tableDao.findAllByPosition(position, compX, compY, pageSize, pageSize * pageNum);
    }

    @Override
    public List<Table> getAllBySchemeId(Integer schemeId, int pageSize, int pageNum) {
        return tableDao.findAllBySchemeId(schemeId, pageSize, pageSize * pageNum);
    }

    @Override
    public Table getById(Integer id) {
        return tableDao.findById(id);
    }

    @Override
    public Table save(Table table) {
        if (table.getId() == null) {
            tableDao.create(table);
        } else {
            Table old = tableDao.findById(table.getId());
            if (old == null) {
                throw new NotFoundException("Table with id = " + table.getId() + " not found.");
            }
            tableDao.update(old);
        }
        return table;
    }

    @Override
    public boolean delete(Integer id) {
        return tableDao.delete(id) != 0;
    }
}
