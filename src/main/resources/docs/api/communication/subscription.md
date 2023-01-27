# Бизнес требования

- Подписываться и отписываться от других юзеров.
- Получать список своих подписок и кто подписан на вас.
- Получать фразы ваших подписок.

# Функциональные требования

## 1.1 Метод subscription (auth) POST

**Входящие данные**<br/>

```json
{
  "pubUserId": 10
}
```

**Валидация**<br/>
pubUserId > 0

**Логика метода**</br>
Сервис проверяет, что pubUserId не равен id юзера реквеста, в случае равенства возвращает ошибку
SUBSCRIPTION_LOGIC_ERROR, далее сервис по pubUserId ищет юзера в таблице user, если не находит - возвращает ошибку
PUBLISHER_USER_NOT_FOUND, если находит - проверяет наличие такой подписки в таблице subscription, если находит -
возвращает ошибку ALREADY_SUBSCRIBED, если не находит - добавляет подписку.

**Исходящие данные в случае успеха** <br/>статус 200

## 1.2 Метод unsubscription (auth) POST

**Входящие данные**<br/>

```json
{
  "pubUserId": 10
}
```

**Валидация**<br/>
pubUserId > 0

**Логика метода**</br>
Сервис удаляет подписку по пришедшим данным.

**Исходящие данные в случае успеха** <br/>статус 200

## 2.1 Метод publishers (auth) GET

**Входящие данные**<br/>

**Логика метода**<br/>
Сервис возвращает подписки юзера

**Исходящие данные в случае успеха** <br/>статус 200

```json
{
  "data": {
    "publishers": [
      {
        "userId": 9,
        "nickname": "Some nickname"
      },
      {
        "userId": 8,
        "nickname": "Some nickname - 2"
      },
      {
        "userId": 7,
        "nickname": "Some nickname - 3"
      }
    ]
  }
}
```

## 2.2 Метод subscribers GET

**Входящие данные**<br/>

**Логика метода**<br/>
Сервис возвращает подписчиков юзера

**Исходящие данные в случае успеха** <br/>статус 200

```json
{
  "data": {
    "subscribers": [
      {
        "userId": 9,
        "nickname": "Some nickname"
      },
      {
        "userId": 8,
        "nickname": "Some nickname - 2"
      }
    ]
  }
}
```

## 3 Метод publishers-phrases (auth) GET

**Входящие данные**<br/>

/publishers-phrases/{from}/{limit}

**Валидация**<br/>
from >= 0, limit >= 1

**Логика метода**<br/>
Сервис возвращает фразы пользователей на которых подписан юзер, сортировка от новых к старым.

**Исходящие данные в случае успеха** <br/>статус 200

```json
{
  "data": {
    "phrases": [
      {
        "phraseId": 32,
        "userId": 9,
        "nickname": "Some nickname",
        "text": "Some text",
        "timeInsert": "2022-11-21 10:42:15",
        "tags": [
          {
            "tagId": 15,
            "text": "погода"
          },
          {
            "tagId": 17,
            "text": "солнце"
          }
        ],
        "countLikes": 0,
        "comments": [
          {
            "userId": 9,
            "nickname": "арбузо",
            "commentId": 6,
            "text": "вот это великая фраза!",
            "timeInsert": "2022-12-13 14:52:22"
          }
        ]
      },
      {
        "phraseId": 20,
        "userId": 4,
        "nickname": "Some nickname - 2",
        "text": "Some text - 2",
        "timeInsert": "2022-11-17 16:42:15",
        "tags": [
          {
            "tagId": 16,
            "text": "непогожий"
          }
        ],
        "countLikes": 2,
        "comments": [
          {
            "userId": 1,
            "nickname": "barabashka",
            "commentId": 1,
            "text": "В моем городе тоже отличная погода.",
            "timeInsert": "2023-01-17 11:41:57"
          }
        ]
      }
    ]
  }
}
```