# Translator
Translator - это веб-приложение на языке Java для перевода набора слов на другой язык с использованием стороннего сервиса перевода (Яндекс).

Приложение принимает на вход строку, состоящую из набора слов, исходный язык и целевой язык в качестве параметров для перевода. В ответ программа возвращает переведенную строку.

![translate](https://github.com/user-attachments/assets/6be936a5-a1e2-4907-8d03-e20bd4a41317)

Translator сохраняет в реляционную базу данных (PostgreSQL) информацию о запросе: IP-адрес пользователя, входную строку для перевода и результат перевода.

![pg](https://github.com/user-attachments/assets/dac4c4d4-4ff6-47fc-89e9-7ddfd27fd94c)


## Начало работы
1. Установите на свой компьютер [JDK](https://www.oracle.com/cis/java/technologies/downloads/), среду разработки [IntelliJ IDEA](https://www.jetbrains.com/ru-ru/idea/download/?section=windows) и [Docker](https://www.docker.com/products/docker-desktop/), если они ещё не установлены.
2. Загрузите проект-заготовку из Git-репозитория.
3. Запустите базу данных Postgres, выполнив в терминале команду `docker compose up`.
5. Запустите `LabApplication`.

После запуска приложения документацию по API можно увидеть в [Swagger](http://localhost:8080/swagger-ui/index.html).  
Для тестирования REST-API можно использовать [Postman](https://www.postman.com/downloads/).
