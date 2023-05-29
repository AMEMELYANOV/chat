# Chat

# **Приложение - Чат** 

## <p id="contents">Оглавление</p>

<ul>
<li><a href="#01">Описание проекта</a></li>
<li><a href="#02">Стек технологий</a></li>
<li><a href="#03">Требования к окружению</a></li>
<li><a href="#04">Сборка и запуск проекта</a>
    <ol type="1">
        <li><a href="#0401">Сборка проекта</a></li>
        <li><a href="#0402">Запуск проекта</a></li>
    </ol>
</li>
<li><a href="#05">Взаимодействие с приложением</a>
    <ol  type="1">
        <li><a href="#0501">Создание учетной записи</a></li>
        <li><a href="#0502">Получение JWT токена</a></li>
        <li><a href="#0503">Получение списка всех пользователей</a></li>
        <li><a href="#0504">Получение списка всех ролей</a></li>
        <li><a href="#0505">Получение списка всех сообщений</a></li>
        <li><a href="#0506">Добавление сообщения</a></li>
        <li><a href="#0507">Получение сообщения по id</a></li>
    </ol>
</li>
<li><a href="#contacts">Контакты</a></li>
</ul>

## <p id="01">Описание проекта</p>

Spring Boot приложение с демонстрацией работы Rest сервиса, реализующее функционал простого чата.

Функционал:

* Создание пользователей/сообщений/ролей/комнат.
* Получение списка пользователей/сообщений/ролей/комнат.
* Получение пользователя/сообщения/роли/комнаты по id.
* Редактирование пользователя/сообщения/роли/комнаты.
* Удаление пользователей/сообщений/ролей/комнат.

<p><a href="#contents">К оглавлению</a></p>

## <p id="02">Стек технологий</p>

- Java 14
- Spring Boot 2.7, Spring Security, Spring Validation
- PosgreSQL 14, Spring Data
- JUnit 5, Mockito
- Maven 3.8

<p><a href="#contents">К оглавлению</a></p>

# Применяемые инструменты:

- Javadoc, JaCoCo, Checkstyle

<p><a href="#contents">К оглавлению</a></p>

## <p id="03">Требования к окружению</p>

- Java 14, Maven 3.8, PostgreSQL 14

<p><a href="#contents">К оглавлению</a></p>

## <p id="04">Сборка и запуск проекта</p>

Для выполнения действий данного раздела необходимо установить
и настроить систему сборки проектов Maven.

По умолчании проект компилируется и собирается в директорию target.

<p><a href="#contents">К оглавлению</a></p>

### <p id="0401">1. Сборка проекта</p>

Команда для сборки в jar.
`mvn clean package -DskipTests`

<p><a href="#contents">К оглавлению</a></p>

### <p id="0402">2. Запуск проекта</p>

Перед запуском проекта необходимо создать базу данных chat
в PostgreSQL, команда для создания базы данных:
`create database chat;`
Средство миграции Liquibase автоматически создаст структуру
базы данных и наполнит ее предустановленными данными.
Команда для запуска приложения:
`mvn spring-boot:run`

<p><a href="#contents">К оглавлению</a></p>

## <p id="05">Взаимодействие с приложением</p>

Взаимодействие демонстрируется с помощью приложения Postman.

<p><a href="#contents">К оглавлению</a></p>

### <p id="0501">1. Создание учетной записи</p>

* `POST/users/sign-up`

![alt text](images/chat_image_1.jpg)

<p><a href="#contents">К оглавлению</a></p>

### <p id="0502">2. Получение JWT токена</p>

* `POST/login`

В теле запроса необходимо указать полученного пользователя
из тела ответа при регистрации.
Полученный JWT необходимо добавить в разделе Authorization.
Далее JWT используется для получения доступа к сервису.

![alt text](images/chat_image_2.jpg)

<p><a href="#contents">К оглавлению</a></p>

### <p id="0503">3. Получение списка всех пользователей</p>

* `GET/users/all`

![alt text](images/chat_image_3.jpg)

<p><a href="#contents">К оглавлению</a></p>

### <p id="0504">4. Получение списка всех ролей</p>

* `GET/role/`

![alt text](images/chat_image_4.jpg)

<p><a href="#contents">К оглавлению</a></p>

### <p id="0505">5. Получение списка всех сообщений</p>

* `GET/message/`

![alt text](images/chat_image_5.jpg)

<p><a href="#contents">К оглавлению</a></p>

### <p id="0506">6. Добавление сообщения</p>

* `POST/message/`

![alt text](images/chat_image_6.jpg)

<p><a href="#contents">К оглавлению</a></p>

### <p id="0507">7. Получение сообщения по id</p>

* `GET/message/id`

![alt text](images/chat_image_7.jpg)

<p><a href="#contents">К оглавлению</a></p>

## <p id="contacts">Контакты</p>

[![alt-text](https://img.shields.io/badge/-telegram-grey?style=flat&logo=telegram&logoColor=white)](https://t.me/T_AlexME)&nbsp;&nbsp;
[![alt-text](https://img.shields.io/badge/@%20email-005FED?style=flat&logo=mail&logoColor=white)](mailto:amemelyanov@yandex.ru)&nbsp;&nbsp;

<p><a href="#contents">К оглавлению</a></p>