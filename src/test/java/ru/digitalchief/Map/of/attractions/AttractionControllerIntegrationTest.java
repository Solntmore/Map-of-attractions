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
import ru.digitalchief.Map.of.attractions.client.dto.RequestAttractionDto;
import ru.digitalchief.Map.of.attractions.client.model.Status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.digitalchief.Map.of.attractions.StaticMethodsAndConstantsForTests.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(statements = {RESET_CITIES_IDS, RESET_ATTRACTIONS_IDS, CREATE_CITIES, CREATE_ATTRACTIONS})
class AttractionControllerIntegrationTest {
    private static ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public static void makeObjects() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Успешное создание достопримечательности")
    public void createAttractionGetStatus201() throws Exception {
        RequestAttractionDto requestAttractionDto = RequestAttractionDto.builder()
                .name("Морской храм")
                .cityId(2L)
                .status(Status.AVAILABLE)
                .website("mor.ru").build();

        mockMvc.perform(
                        post("/attractions")
                                .content(objectMapper.writeValueAsString(requestAttractionDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Морской храм"))
                .andExpect(jsonPath("$.city.name").value("Санкт-Петербург"))
                .andExpect(jsonPath("$.status").value(Status.AVAILABLE.name()))
                .andExpect(jsonPath("$.website").value("mor.ru"));
    }

    @Test
    @DisplayName("Ошибка при создании достопримечательности из-за дублирования названия")
    public void createAttractionAttractionExistNameGetStatus409() throws Exception {
        RequestAttractionDto requestAttractionDto = RequestAttractionDto.builder()
                .name("Кунсткамера")
                .cityId(2L)
                .status(Status.AVAILABLE)
                .website("mor.ru").build();

        mockMvc.perform(
                        post("/attractions")
                                .content(objectMapper.writeValueAsString(requestAttractionDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Ошибка при создании достопримечательности из-за пустого названия")
    public void createAttractionWithEmptyNameGetStatus400() throws Exception {
        RequestAttractionDto requestAttractionDto = RequestAttractionDto.builder()
                .name(" ")
                .cityId(2L)
                .status(Status.AVAILABLE)
                .website("mor.ru").build();

        mockMvc.perform(
                        post("/attractions")
                                .content(objectMapper.writeValueAsString(requestAttractionDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Ошибка при создании достопримечательности из-за несуществующего города")
    public void createAttractionWithWithCityIsNotExistGetStatus404() throws Exception {
        RequestAttractionDto requestAttractionDto = RequestAttractionDto.builder()
                .name("Морской храм")
                .cityId(88L)
                .status(Status.AVAILABLE)
                .website("mor.ru").build();

        mockMvc.perform(
                        post("/attractions")
                                .content(objectMapper.writeValueAsString(requestAttractionDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Ошибка при создании достопримечательности из-за пустого сайта")
    public void createAttractionWithNegativeAreaGetStatus400() throws Exception {
        RequestAttractionDto requestAttractionDto = RequestAttractionDto.builder()
                .name("Морской храм")
                .cityId(2L)
                .status(Status.AVAILABLE)
                .website(" ").build();

        mockMvc.perform(
                        post("/attractions")
                                .content(objectMapper.writeValueAsString(requestAttractionDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Успешное удаление достопримечательности")
    public void deleteAttractionGetStatus204() throws Exception {

        mockMvc.perform(
                        delete("/attractions/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());

        mockMvc.perform(
                        get("/attractions/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Попытка удаления не существующей достопримечательности")
    public void deleteAttractionIsNotExistGetStatus404() throws Exception {

        mockMvc.perform(
                        delete("/attractions/88")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Успешное обновление достопримечательности")
    public void updateAttractionGetStatus200() throws Exception {
        RequestAttractionDto requestAttractionDto = RequestAttractionDto.builder()
                .name("Морской храм")
                .cityId(1L)
                .status(Status.AVAILABLE)
                .website("mor.ru").build();

        mockMvc.perform(
                        put("/attractions/1")
                                .content(objectMapper.writeValueAsString(requestAttractionDto))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Морской храм"))
                .andExpect(jsonPath("$.city.name").value("Москва"))
                .andExpect(jsonPath("$.status").value(Status.AVAILABLE.name()))
                .andExpect(jsonPath("$.website").value("mor.ru"));
    }

    @Test
    @DisplayName("Успешное получение достопримечательности по id")
    public void getAttractionGetStatus200() throws Exception {

        mockMvc.perform(
                        get("/attractions/2")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Московский кремль"))
                .andExpect(jsonPath("$.city.name").value("Москва"))
                .andExpect(jsonPath("$.status").value(Status.AVAILABLE.name()))
                .andExpect(jsonPath("$.website").value("krml.ru"));
    }

    @Test
    @DisplayName("Запрос несуществующей достопримечательности")
    public void getAttractionGetStatus404() throws Exception {

        mockMvc.perform(
                        get("/attractions/88")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Успешное получение всех достопримечательностей")
    public void getAllAttractionStatus200() throws Exception {

        mockMvc.perform(
                        get("/attractions")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Парк зарядье"))
                .andExpect(jsonPath("$[1].name").value("Московский кремль"))
                .andExpect(jsonPath("$[2].name").value("Кул-Шариф"))
                .andExpect(jsonPath("$[3].name").value("Храм всех религий"));
    }

    @Test
    @DisplayName("Успешное получение всех достопримечательностей с пагинацией")
    public void getAllAttractionWithPaginationStatus200() throws Exception {

        mockMvc.perform(
                        get("/attractions?from=0&size=3")
                )
                .andExpect(jsonPath("$[0].name").value("Парк зарядье"))
                .andExpect(jsonPath("$[1].name").value("Московский кремль"))
                .andExpect(jsonPath("$[2].name").value("Кул-Шариф"));
    }
}
