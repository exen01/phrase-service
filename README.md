# Бизнес требования
1. Юзер должен иметь возможность зарегистрироваться.
2. Публиковать фразы.
3. Прикреплять к фразе теги.
4. Подписываться на других пользователей.
5. Читать фразы других пользователей.
6. Оценивать фразы других пользователей.
7. Совершать поиск по тегам.
8. Совершать поиск по словам.
9. Совершать поиск по нику.

# Модель данных
## user
id, nickname, password, access_token, time_insert

# Функциональные требования
## 1.1 Метод registration
**Входящие данные**
```json
{
  "nickname": "barabashka",
  "password": "barabashka1980"
}
```
**Валидация**<br/>
nickname >= 4 символа, <= 15 символов, разрешенные символы a-zA-Z0-9а-яА-Я. _-<br/>
password >= 8 символов, <= 100 символов, разрешенные символы a-zA-Z0-9а-яА-Я.,:; _?!+=/'\"*(){}[]-

**Логика метода**<br/>
Cервис совершает поиск входящего nickname в таблице user, если находит - возвращает ошибку NICKNAME_TAKEN,
иначе генерирует accessToken. Шифрует входящий password, записывает в таблицу user nickname, password, accessToken.
Возвращает клиенту accessToken.

**Исходящие данные в случае успеха** <br/>статус 200
```json
{
  "data": {
    "accessToken": "d4a76068f5104f26975499d22bcd11cc1665995491673"
  }
}
```

**Исходящие данные в случае ошибки**<br/>статус 400
```json
{
  "error": {
    "code": "REQUEST_VALIDATION_ERROR",
    "message": "Некорректный nickname. Некорректный password."
  }
}
```
```json
{
  "error": {
    "code": "NICKNAME_BUSY",
    "message": "Этот ник уже занят, придумайте другой."
  }
}
```

## 1.1 Метод login
**Входящие данные**
```json
{
  "nickname": "barabashka",
  "password": "barabashka1980"
}
```

**Валидация**<br/>
nickname >= 4 символа, <= 15 символов, разрешенные символы a-zA-Z0-9а-яА-Я. _-<br/>
password >= 8 символов, <= 100 символов, разрешенные символы a-zA-Z0-9а-яА-Я.,:; _?!+=/'\"*(){}[]-

**Логика метода**<br/>
Сервис шифрует password таким же образом, как и метод registration в пункте 1.1. Совершает поиск в таблице user по столбцам
nickname и password, если находит - возвращает accessToken, иначе возвращает ошибку USER_NOT_FOUND.

**Исходящие данные в случае успеха** <br/>статус 200
```json
{
  "data": {
    "accessToken": "d4a76068f5104f26975499d22bcd11cc1665995491673"
  }
}
```

**Исходящие данные в случае ошибки**<br/>статус 400
```json
{
  "error": {
    "code": "REQUEST_VALIDATION_ERROR",
    "message": "Некорректный nickname. Некорректный password."
  }
}
```
```json
{
  "error": {
    "code": "USER_NOT_FOUND",
    "message": "Пользователь не найден."
  }
}
```