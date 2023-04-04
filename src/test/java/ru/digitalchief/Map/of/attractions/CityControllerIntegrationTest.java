package ru.digitalchief.Map.of.attractions;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalchief.Map.of.attractions.dto.RequestCityDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.digitalchief.Map.of.attractions.StaticMethodsAndConstantsForTests.CREATE_CITIES;
import static ru.digitalchief.Map.of.attractions.StaticMethodsAndConstantsForTests.RESET_CITIES_IDS;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(statements = {RESET_CITIES_IDS, CREATE_CITIES})
class CityControllerIntegrationTest {
    private static ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public static void makeObjects() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Успешное создание города")
    public void createCityGetStatus201() throws Exception {
        RequestCityDto requestCityDto = RequestCityDto.builder()
                .name("Можайск")
                .population(33880L)
                .area(18)
                .website("mozhaysk.ru").build();

        mockMvc.perform(
                        post("/cities")
                                .content(objectMapper.writeValueAsString(requestCityDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Можайск"))
                .andExpect(jsonPath("$.population").value(33880L))
                .andExpect(jsonPath("$.area").value(18))
                .andExpect(jsonPath("$.website").value("mozhaysk.ru"));
    }

    @Test
    @DisplayName("Ошибка при создании города из-за дублирования названия")
    public void createCityWithExistNameGetStatus409() throws Exception {
        RequestCityDto requestCityDto = RequestCityDto.builder()
                .name("Москва")
                .population(33880L)
                .area(18)
                .website("mozhaysk.ru").build();

        mockMvc.perform(
                        post("/cities")
                                .content(objectMapper.writeValueAsString(requestCityDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Ошибка при создании города из-за пустого названия")
    public void createCityWithEmptyNameGetStatus400() throws Exception {
        RequestCityDto requestCityDto = RequestCityDto.builder()
                .name(" ")
                .population(33880L)
                .area(18)
                .website("mozhaysk.ru").build();

        mockMvc.perform(
                        post("/cities")
                                .content(objectMapper.writeValueAsString(requestCityDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Ошибка при создании города из-за отрицательного населения")
    public void createCityWithNegativePopulationGetStatus400() throws Exception {
        RequestCityDto requestCityDto = RequestCityDto.builder()
                .name("Москва")
                .population(-100L)
                .area(18)
                .website("mozhaysk.ru").build();

        mockMvc.perform(
                        post("/cities")
                                .content(objectMapper.writeValueAsString(requestCityDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Ошибка при создании города из-за отрицательной площади")
    public void createCityWithNegativeAreaGetStatus400() throws Exception {
        RequestCityDto requestCityDto = RequestCityDto.builder()
                .name("Москва")
                .population(33880L)
                .area(-18)
                .website("mozhaysk.ru").build();

        mockMvc.perform(
                        post("/cities")
                                .content(objectMapper.writeValueAsString(requestCityDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Ошибка при создании города из-за пустого сайта")
    public void createCityWithEmptyWebsiteGetStatus400() throws Exception {
        RequestCityDto requestCityDto = RequestCityDto.builder()
                .name("Москва")
                .population(33880L)
                .area(18)
                .website(" ").build();

        mockMvc.perform(
                        post("/cities")
                                .content(objectMapper.writeValueAsString(requestCityDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Успешное удаление города")
    public void deleteCityGetStatus204() throws Exception {

        mockMvc.perform(
                        delete("/cities/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());

        mockMvc.perform(
                        get("/cities/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Попытка удаления не существующего города")
    public void deleteCityIsNotExistGetStatus404() throws Exception {

        mockMvc.perform(
                        delete("/cities/88")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Успешное обновление города")
    public void updateCityGetStatus200() throws Exception {
        RequestCityDto requestCityDto = RequestCityDto.builder()
                .name("Можайск")
                .population(33880L)
                .area(18)
                .website("mozhaysk.ru").build();

        mockMvc.perform(
                        put("/cities/1")
                                .content(objectMapper.writeValueAsString(requestCityDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Можайск"))
                .andExpect(jsonPath("$.population").value(33880L))
                .andExpect(jsonPath("$.area").value(18))
                .andExpect(jsonPath("$.website").value("mozhaysk.ru"));
    }

    @Test
    @DisplayName("Успешное получение города по id")
    public void getCityGetStatus200() throws Exception {

        mockMvc.perform(
                        get("/cities/2")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Санкт-Петербург"))
                .andExpect(jsonPath("$.population").value(5898486L))
                .andExpect(jsonPath("$.area").value(1439))
                .andExpect(jsonPath("$.website").value("spb.ru"));
    }

    @Test
    @DisplayName("Запрос несуществующего города")
    public void getCityGetStatus404() throws Exception {

        mockMvc.perform(
                        get("/cities/88")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Успешное получение всех городов")
    public void getAllCitiesStatus200() throws Exception {

        mockMvc.perform(
                        get("/cities")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Вологда"))
                .andExpect(jsonPath("$[1].name").value("Казань"))
                .andExpect(jsonPath("$[2].name").value("Краснодар"))
                .andExpect(jsonPath("$[3].name").value("Москва"))
                .andExpect(jsonPath("$[4].name").value("Санкт-Петербург"))
                .andExpect(jsonPath("$[5].name").value("Томск"));
    }

    @Test
    @DisplayName("Успешное получение всех машин с пагинацией")
    public void getAllCitiesWithPaginationStatus200() throws Exception {

        mockMvc.perform(
                        get("/cities?from=0&size=3")
                )
                .andExpect(jsonPath("$[0].name").value("Вологда"))
                .andExpect(jsonPath("$[1].name").value("Казань"))
                .andExpect(jsonPath("$[2].name").value("Краснодар"));
    }

    @Test
    @DisplayName("Успешное получение всех машин с пагинацией и с нисходящей сортировкой по населению")
    public void getAllCitiesWithPaginationAndDescendingSort() throws Exception {

        mockMvc.perform(
                        get("/cities?sort=POPULATION&direction=DESC&from=0&size=3")
                )
                .andExpect(jsonPath("$[0].name").value("Москва"))
                .andExpect(jsonPath("$[1].name").value("Санкт-Петербург"))
                .andExpect(jsonPath("$[2].name").value("Казань"));
    }

}
