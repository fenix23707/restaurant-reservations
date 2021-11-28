package by.vsu.service;

import by.vsu.model.Comparison;
import by.vsu.model.Position;
import by.vsu.model.Table;

import java.util.List;

public interface TableService {
    List<Table> getAll(int pageSize, int pageNum);

    List<Table> getAllByCapacity(int capacity, Comparison comparison, int pageSize, int pageNum);

    List<Table> getAllByWidthHeight(int width, Comparison compWidth,
                                    int height, Comparison compHeight,
                                    int pageSize, int pageNum);

    List<Table> getAllByPosition(Position position,
                                 Comparison compX, Comparison compY,
                                 int pageSize, int pageNum);


    List<Table> getAllBySchemeId(Integer schemeId,int pageSize, int pageNum);

    Table getById(Integer id);

    Table save(Table table);

    boolean delete(Integer id);
}
