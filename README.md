# Бизнес требования

1. Юзер должен иметь возможность зарегистрироваться и залогиниться.
2. Публиковать фразы с тегами.
3. Получать свои фразы.
4. Подписываться на других пользователей.
5. Читать фразы других пользователей.
6. Оценивать фразы других пользователей.
7. Совершать поиск по тегам.
8. Совершать поиск по словам.
9. Совершать поиск по нику.

# Модель данных

## user

id, nickname, password, access_token, time_insert

## phrase

id, user_id, text, time_insert

## tag

id, text, time_insert

## phrase_tag

id, phrase_id, tag_id, time_insert

# Функциональные требования

## 1.1 Метод registration

**Входящие данные**

```json
{
  "authorization": {
    "nickname": "login_example",
    "password": "password_example"
  }
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
    "userMessage": "Некорректный nickname. Некорректный password."
  }
}
```

```json
{
  "error": {
    "code": "NICKNAME_BUSY",
    "userMessage": "Этот ник уже занят, придумайте другой."
  }
}
```

## 1.2 Метод login

**Входящие данные**

```json
{
  "authorization": {
    "nickname": "barabashka",
    "password": "barabashka1980"
  }
}
```

**Валидация**<br/>
nickname >= 4 символа, <= 15 символов, разрешенные символы a-zA-Z0-9а-яА-Я. _-<br/>
password >= 8 символов, <= 100 символов, разрешенные символы a-zA-Z0-9а-яА-Я.,:; _?!+=/'\"*(){}[]-

**Логика метода**<br/>
Сервис шифрует password таким же образом, как и метод registration в пункте 1.1. Совершает поиск в таблице user по
столбцам
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
    "techMessage": "Некорректный nickname. Некорректный password."
  }
}
```

```json
{
  "error": {
    "code": "USER_NOT_FOUND",
    "userMessage": "Пользователь не найден."
  }
}
```

## 2 Метод publish-phrase

**Входящие данные**<br/>
`AccessToken: 8e043390d24642a9ae64e303568c27d61666011676410`

```json
{
  "text": "Сегодня солнечный день",
  "tags": [
    "погода",
    "солнце",
    "хорошее настроение"
  ]
}
```

**Валидация**<br/>
text >= 1 символов, <= 140, разрешенные символы a-zA-Z0-9а-яА-Я.,:; _?!+=/'\"*(){}[]-<br/>
tags от 0 до 5, значение от >= 3 символов, <= 25, разрешенные символы a-zA-Z0-9а-яА-Я.,:; _?!+=/'\"*(){}[]-

**Логика метода**<br/>
Сервис проверяет наличие accessToken в таблице user столбце access_token, если не находит - возвращает ошибку
AUTHORIZATION_ERROR, иначе получает id юзера по accessToken.
Записывает фразу в таблицу phrase, по каждому тегу проверяет есть ли в таблице tag такой тег, если нет - записывает.
Получает id всех тегов, записывает id фразы и тегов в таблицу phrase_tag.

## 3 Метод my-phrases

**Входящие данные**<br/>
`AccessToken: 8e043390d24642a9ae64e303568c27d61666011676410`

**Логика метода**<br/>
Сервис получает фразы юзера по его id, получает теги к каждой фразе по её id, упорядочивает по времени от новым к старым
и отсылает юзеру.

**Исходящие данные в случае успеха** <br/>статус 200

```json
{
  "data": {
    "phrases": [
      {
        "id": 9,
        "text": "Сегодня солнечный день",
        "timeInsert": "2023-01-03 16:12:15",
        "tags": [
          {
            "tagId": 1,
            "text": "погода"
          },
          {
            "tagId": 2,
            "text": "день"
          }
        ]
      },
      {
        "id": 8,
        "text": "Сегодня солнечный день - 2",
        "timeInsert": "2023-01-03 16:09:15",
        "tags": [
          {
            "tagId": 1,
            "text": "погода"
          },
          {
            "tagId": 3,
            "text": "солнце"
          },
          {
            "tagId": 1,
            "text": "хороший день"
          }
        ]
      }
    ]
  }
}
```

## 4 Метод search-tags

**Входящие данные**<br/>
`AccessToken: 8e043390d24642a9ae64e303568c27d61666011676410`

```json
{
  "partTag": "пог"
}
```

**Валидация**<br/>
partTag >= 3 символов, <= 25, разрешённые символы a-zA-Z0-9а-яА-Я.,:; _?!+=/'\"*(){}[]-

**Логика метода**<br/>
Сервис ищет теги в таблице tag по partTag и отсылает юзеру найденные теги с их id.
Сортировка - сначала должны идти теги, которые начинаются со значения partTag в порядке убывания популярности, затем
должны идти теги, которые не начинаются со значения partTag в порядке убывания популярности.

**Исходящие данные в случае успеха** <br/>статус 200

```json
{
  "data": {
    "tags": [
      {
        "id": 15,
        "text": "погожий"
      },
      {
        "id": 2,
        "text": "погода"
      },
      {
        "id": 16,
        "text": "непогожий"
      },
      {
        "id": 18,
        "text": "непогода"
      }
    ]
  }
}
```

## 5 Метод  search-by-tag

**Входящие данные**<br/>
`AccessToken: 8e043390d24642a9ae64e303568c27d61666011676410`

```json
{
  "tagId": 3,
  "sort": "RANDOM"
}
```

**Валидация**<br/>
tagId > 0<br/>
sort: RANDOM, TIME_INSERT

**Логика метода**<br/>
Сервис ищет фразы по tagId и возвращает юзеру массив: id фразы, id юзера, ник юзера, текст фразы, время создания фразы,
теги фразы. Сортировка от новых к страрым или случайным образом.

**Исходящие данные в случае успеха** <br/>статус 200

```json
{
  "data": {
    "phrases": [
      {
        "phraseId": 15,
        "userId": 5,
        "nickname": "Some Nickname",
        "text": "Хорошая погода сегодня",
        "timeInsert": "2023-01-03 16:09:15",
        "tags": [
          {
            "id": 2,
            "text": "погода"
          },
          {
            "id": 16,
            "text": "непогожий"
          }
        ]
      },
      {
        "phraseId": 16,
        "userId": 6,
        "nickname": "Some Nickname - 2",
        "text": "Хорошая погода сегодня - 2",
        "timeInsert": "2023-01-03 16:09:15",
        "tags": [
          {
            "id": 1,
            "text": "море"
          },
          {
            "id": 15,
            "text": "погожий"
          }
        ]
      }
    ]
  }
}
```