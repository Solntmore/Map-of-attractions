# **Тестовое задание для Digitalchief**

## Приложение для поиска и добавления городов и их достопримечательностей.

### **Функциональные возможности.**

Приложение позволяет создавать записывать и удалять города и достопримечательности в них,
запрашивать списки городов и их достопримечательности, редактировать ранее записанную информацию.

### **Технические особенности.**

Микросервисный проект на Java 11 + Spring Boot, RESTful API, сборщик Maven. Hibernate,
JPA repository, PostgreSQL, MapStruct. Контейнеризация через Docker. Кэширование на Redis. Авторизация с JWT токеном.

### **Инструкция по сборке:**

1) Вы можете скачать проект в zip архиве нажав на главной странице проекта зеленую кнопку code -> download zip
2) Откройте проект с помощью своей IDE и дождитесь, пока загрузятся все необходимые данные. В случае,
   если вы используете IntelliJ IDEA значки файлов "посинеют" после загрузки всего необходимого.
3) Для запуска проекта требуется JDK не ниже версии 11.
4) Для того, чтобы запустить локально данную программу, вам потребуется настроить Redis на своем устройстве, для корректной
   работы кэша. Для запуска в docker контейнере вам не понадобиться этого делать. Вы можете настроить Redis по
   инструкции с официального сайта: https://redis.io/docs/getting-started/installation/install-redis-on-windows/
5) Локально программа работает с H2 Database, а в Docker с PostgreSQL, просто запустите класс
   MapOfAttractionsApplication.
6) Если вы хотите воспользоваться БД PostgreSQL без установки на ПК, то сгенерируйте образ Docker зайдя в файл
   docker-compose.yml и нажав на двойной зеленый треугольник рядом с названием "services".
7) В проекте используется авторизация с JWT токеном. Вы можете отключить ее, для упрощенного тестирования, для этого в 
файле по пути MainServer/src/main/resources/application.properties установите auth.enabled=true для любого из профилей 
запуска, который хотите использовать.
8) Также доступен swagger, достаточно просто перейти по ссылке: http://localhost:8080/swagger-ui/index.html#, 
предварительно отключив авторизацию в соответствии с пунктом 7

### **Описание зависимостей:**

- spring-boot-starter-data-jpa - стартер для доступа к базе данных
- spring-boot-starter-web - стартер для создания веб-приложений и RESTful приложений
- spring-boot-starter-validation - стартер для валидации в контроллере
- spring-boot-starter-test - стартер для тестирования spring boot приложений
- spring-boot-starter-data-redis, jedis - стартер необходимый для подключения Redis
- springdoc-openapi-ui - необходимо для автоматической генерации документации swagger
- h2 - драйвер для работы БД H2
- postgresql - драйвер для работы БД PostgreSQL
- lombok - библиотека, позволяющая уменьшить количество кода с помощью аннотаций
- mapstruct - библиотека, позволяющая генерировать мапперы для DTO