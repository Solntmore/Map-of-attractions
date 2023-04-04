package ru.digitalchief.Map.of.attractions;

public class StaticMethodsAndConstantsForTests {

    public static final String CREATE_CITIES = "INSERT INTO cities (name, population, area, website) " +
            "VALUES ('Москва', 13097539, 2561, 'mos.ru'), " +
            "       ('Санкт-Петербург', 5898486, 1439, 'spb.ru'), " +
            "       ('Краснодар', 1204878, 841, 'krd.ru'), " +
            "       ('Казань', 1259173, 425, 'kzn.ru'), " +
            "       ('Вологда', 313944, 116, 'вологда.рф'), " +
            "       ('Томск', 556478, 294, 'tomsk.ru')";

    public static final String CREATE_ATTRACTIONS = "INSERT INTO attractions (name, city_id, status, website) " +
            "VALUES ('Парк зарядье', 1, 'AVAILABLE', 'zar.ru'), " +
            "       ('Московский кремль', 1, 'AVAILABLE', 'krml.ru'), " +
            "       ('Кунсткамера', 2, 'NOT_AVAILABLE', 'kunskt.ru'), " +
            "       ('Кул-Шариф', 4, 'AVAILABLE', 'coolSharif.ru'), " +
            "       ('Храм всех религий', 4, 'AVAILABLE', 'вселенскийхрам.рф'), " +
            "       ('Центр семьи Казан', 4, 'NOT_AVAILABLE', 'centerkazan.ru')";

    public static final String RESET_CITIES_IDS = "alter table cities alter column id restart with 1";
    public static final String RESET_ATTRACTIONS_IDS = "alter table attractions alter column id restart with 1";

}
