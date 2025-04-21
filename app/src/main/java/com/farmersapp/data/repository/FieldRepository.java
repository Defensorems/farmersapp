package com.farmersapp.data.repository;

import com.farmersapp.data.model.Field;
import com.farmersapp.data.model.GeoPoint;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FieldRepository {

    // TODO: Implement data sources (local database, remote API)

    public FieldRepository() {
        // Временная реализация без реальных источников данных
    }

    public List<Field> getFields() {
        // Временная реализация с фиктивными данными
        return getDummyFields();
    }

    public Field getField(int fieldId) {
        // Временная реализация
        List<Field> fields = getDummyFields();
        for (Field field : fields) {
            if (field.getId() == fieldId) {
                return field;
            }
        }
        return null;
    }

    // Вспомогательный метод для создания фиктивных данных
    private List<Field> getDummyFields() {
        List<Field> fields = new ArrayList<>();

        // Создаем границы для поля 1
        List<GeoPoint> boundaries1 = new ArrayList<>();
        boundaries1.add(new GeoPoint(51.5074, -0.1278));
        boundaries1.add(new GeoPoint(51.5075, -0.1279));
        boundaries1.add(new GeoPoint(51.5076, -0.1280));
        boundaries1.add(new GeoPoint(51.5077, -0.1281));

        // Создаем границы для поля 2
        List<GeoPoint> boundaries2 = new ArrayList<>();
        boundaries2.add(new GeoPoint(51.5084, -0.1288));
        boundaries2.add(new GeoPoint(51.5085, -0.1289));
        boundaries2.add(new GeoPoint(51.5086, -0.1290));
        boundaries2.add(new GeoPoint(51.5087, -0.1291));

        fields.add(new Field(1, "Поле 1", 10.5, "Чернозем", new Date(), boundaries1));
        fields.add(new Field(2, "Поле 2", 8.3, "Суглинок", new Date(), boundaries2));

        return fields;
    }
}